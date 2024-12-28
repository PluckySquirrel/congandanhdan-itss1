package vn.edu.hust.soict.japango.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vn.edu.hust.soict.japango.dto.history.HistoryDTO;
import vn.edu.hust.soict.japango.entity.History;

@Mapper(componentModel = "spring")
public interface HistoryMapper {
    @Mapping(target = "timestamp", source = "createdAt")
    HistoryDTO toDTO(History history);
}
