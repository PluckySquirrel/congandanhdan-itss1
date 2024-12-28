package vn.edu.hust.soict.japango.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import vn.edu.hust.soict.japango.common.enums.ActionType;
import vn.edu.hust.soict.japango.common.utils.SecurityUtils;
import vn.edu.hust.soict.japango.dto.history.GetHistoryRequest;
import vn.edu.hust.soict.japango.dto.history.HistoryDTO;
import vn.edu.hust.soict.japango.exception.CustomExceptions;
import vn.edu.hust.soict.japango.repository.HistoryRepository;
import vn.edu.hust.soict.japango.service.HistoryService;
import vn.edu.hust.soict.japango.service.mapper.HistoryMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HistoryServiceImpl implements HistoryService {
    private final SecurityUtils securityUtils;
    private final HistoryRepository historyRepository;
    private final HistoryMapper historyMapper;

    @Override
    public Page<HistoryDTO> getHistory(GetHistoryRequest request) {
        Long userId;
        if ((userId = securityUtils.getUserId()) == null) {
            throw CustomExceptions.LOGIN_REQUIRED_EXCEPTION;
        }
        List<ActionType> actionTypes;
        if (request.getActionType() == null) {
            actionTypes = new ArrayList<>(Arrays.asList(ActionType.values()));
        } else {
            actionTypes = List.of(request.getActionType());
        }
        return historyRepository
                .findByUserIdAndActionInAndCreatedAtBetween(userId, actionTypes, request.getFromDateTime(), request.getToDateTime(), request.getPageable())
                .map(historyMapper::toDTO);
    }
}
