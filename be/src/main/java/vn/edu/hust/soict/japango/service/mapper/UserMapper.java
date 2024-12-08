package vn.edu.hust.soict.japango.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import vn.edu.hust.soict.japango.dto.user.*;
import vn.edu.hust.soict.japango.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(RegisterRequestDTO request);
    RegisterResponseDTO toRegisterResponseDTO(User user);
    void updateEntity(@MappingTarget User user, UpdateProfileRequestDTO request);
    GetProfileResponseDTO toGetProfileResponseDTO(User user);
    UpdateProfileResponseDTO toUpdateProfileResponseDTO(User user);
    ChangePasswordResponseDTO toChangePasswordResponseDTO(User user);
}
