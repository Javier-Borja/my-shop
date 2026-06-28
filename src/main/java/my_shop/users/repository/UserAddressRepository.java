package my_shop.users.repository;

import my_shop.users.model.User;
import my_shop.users.model.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {

    List<UserAddress> findByUser(User user);
    Optional<UserAddress> findByExternalIdAndUser(UUID externalId, User user);
    long countByUser(User user);
}
