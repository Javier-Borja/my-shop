package my_shop.users.mapper;

import my_shop.users.dto.UserAddressRequestDto;
import my_shop.users.dto.UserAddressResponseDto;
import my_shop.users.model.UserAddress;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserAddressMapper {

    UserAddressResponseDto toUserAddressResponseDto(UserAddress user);

    List<UserAddressResponseDto> toUserAddressResponseDtoList(List<UserAddress> user);

    UserAddress toUserAddress(UserAddressRequestDto userAddressRequestDto);

    void updateAddressToDto(UserAddressRequestDto userAddressToDto, @MappingTarget UserAddress userAddress);
}
