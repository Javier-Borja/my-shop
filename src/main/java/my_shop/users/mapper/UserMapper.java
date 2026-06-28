package my_shop.users.mapper;

import my_shop.users.dto.UserProfileResponseDto;
import my_shop.users.dto.UserUpdateRequestDto;
import my_shop.users.model.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "active", source = "active")
    @Mapping(target = "userId", source = "externalId")
    @Mapping(target = "fullName", source = ".", qualifiedByName = "joinNames")
    UserProfileResponseDto toUserProfileDto(User user);

    @Named("joinNames")
    default String joinNames(User user) {
        if (user == null) return null;
        return (user.getFirstName() + " " + user.getLastName()).trim();
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDto(UserUpdateRequestDto userUpdateRequestDto, @MappingTarget User user);
}
