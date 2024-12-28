package vn.edu.hust.soict.japango.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vn.edu.hust.soict.japango.dto.saved_result.SavedResultDTO;
import vn.edu.hust.soict.japango.entity.SavedResult;

@Mapper(componentModel = "spring")
public interface SavedResultMapper {
    @Mapping(target = "timestamp", source = "createdAt")
    SavedResultDTO toDTO(SavedResult savedResult);
}
