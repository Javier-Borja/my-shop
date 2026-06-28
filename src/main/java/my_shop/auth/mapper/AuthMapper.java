package my_shop.auth.mapper;

import my_shop.auth.dto.AuthResponseDto;
import my_shop.auth.dto.RegisterRequestDto;
import my_shop.users.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    AuthMapper INSTANCE = Mappers.getMapper(AuthMapper.class);

    User toUser(RegisterRequestDto request);

    @Mapping(target = "userId", source = "externalId")
    AuthResponseDto toRegisterResponse(User user);

    @Mapping(target = "userId", source = "externalId")
    AuthResponseDto toLoginResponse(User user);
}
