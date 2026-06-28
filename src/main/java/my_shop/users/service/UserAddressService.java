package my_shop.users.service;

import jakarta.transaction.Transactional;
import my_shop.common.exceptions.MaxAddressesReachedException;
import my_shop.common.exceptions.ResourceNotFoundException;
import my_shop.users.dto.UserAddressRequestDto;
import my_shop.users.dto.UserAddressResponseDto;
import my_shop.users.mapper.UserAddressMapper;
import my_shop.users.model.User;
import my_shop.users.model.UserAddress;
import my_shop.users.repository.UserAddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserAddressService {

    private final UserAddressRepository addressRepository;
    private final UserAddressMapper userAddressMapper;

    public UserAddressService(UserAddressRepository addressRepository, UserAddressMapper userAddressMapper) {
        this.addressRepository = addressRepository;
        this.userAddressMapper = userAddressMapper;
    }

    public List<UserAddressResponseDto> getAllAddress(User user) {
        List<UserAddress> addresses = addressRepository.findByUser(user);
        return userAddressMapper.toUserAddressResponseDtoList(addresses);
    }

    @Transactional
    public List<UserAddressResponseDto> createAddressUser(User user, UserAddressRequestDto request) {
        long count = addressRepository.countByUser(user);
        if (count >= 3) {
            throw new MaxAddressesReachedException("Se ha alcanzado el límite máximo de 3 direcciones.");
        }
        UserAddress userAddress = userAddressMapper.toUserAddress(request);
        userAddress.setUser(user);
        
        if (count == 0 || Boolean.TRUE.equals(userAddress.getIsDefault())) {
            resetDefaultAddress(user);
            userAddress.setIsDefault(true);
        }

        addressRepository.save(userAddress);
        return getAllAddress(user);
    }

    @Transactional
    public List<UserAddressResponseDto> updateAddress(User user, UUID externalId, UserAddressRequestDto userAddress) {
        UserAddress address = addressRepository.findByExternalIdAndUser(externalId, user)
                .orElseThrow(() -> new ResourceNotFoundException("Dirección no encontrada"));

        userAddressMapper.updateAddressToDto(userAddress, address);
        long count = addressRepository.countByUser(user);

        if (count == 1 || Boolean.TRUE.equals(userAddress.getIsDefault())) {
            resetDefaultAddress(user);
            address.setIsDefault(true);
        } else {
            address.setIsDefault(userAddress.getIsDefault());
        }

        addressRepository.save(address);

        return getAllAddress(user);
    }

    @Transactional
    public List<UserAddressResponseDto> setDefaultAddress(User user, UUID externalId) {
        UserAddress address = addressRepository.findByExternalIdAndUser(externalId, user)
                .orElseThrow(() -> new ResourceNotFoundException("Dirección no encontrada"));

        resetDefaultAddress(user);
        address.setIsDefault(true);
        addressRepository.save(address);

        return getAllAddress(user);
    }

    @Transactional
    public List<UserAddressResponseDto> deleteAddress(User user, UUID externalId) {
        UserAddress addressToDelete = addressRepository.findByExternalIdAndUser(externalId, user)
                .orElseThrow(() -> new ResourceNotFoundException("Dirección no encontrada"));

        boolean favoriteAddress = addressToDelete.getIsDefault();
        addressRepository.delete(addressToDelete);
        addressRepository.flush();

        if (favoriteAddress) {
            List<UserAddress> remainingAddresses = addressRepository.findByUser(user);
            if (!remainingAddresses.isEmpty()) {
                UserAddress nuevaFavorita = remainingAddresses.get(0);
                nuevaFavorita.setIsDefault(true);
                addressRepository.save(nuevaFavorita);
            }
        }
        return getAllAddress(user);
    }

    private void resetDefaultAddress(User user) {
        List<UserAddress> addresses = addressRepository.findByUser(user);
        if (!addresses.isEmpty()) {
            addresses.forEach(address -> address.setIsDefault(false));
            addressRepository.saveAll(addresses);
            addressRepository.flush();
        }
    }
}
