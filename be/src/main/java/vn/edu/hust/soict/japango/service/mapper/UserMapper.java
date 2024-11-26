package vn.edu.hust.soict.japango.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import vn.edu.hust.soict.japango.dto.user.RegisterRequestDTO;
import vn.edu.hust.soict.japango.dto.user.RegisterResponseDTO;
import vn.edu.hust.soict.japango.dto.user.UpdateProfileRequestDTO;
import vn.edu.hust.soict.japango.dto.user.UpdateProfileResponseDTO;
import vn.edu.hust.soict.japango.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(RegisterRequestDTO request);
    RegisterResponseDTO toRegisterResponseDTO(User user);
    void updateEntity(@MappingTarget User user, UpdateProfileRequestDTO request);
    UpdateProfileResponseDTO toUpdateProfileResponseDTO(User user);
}
