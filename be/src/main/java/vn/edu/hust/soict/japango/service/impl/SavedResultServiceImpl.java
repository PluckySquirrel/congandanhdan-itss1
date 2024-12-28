package vn.edu.hust.soict.japango.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import vn.edu.hust.soict.japango.common.enums.SaveType;
import vn.edu.hust.soict.japango.common.utils.SecurityUtils;
import vn.edu.hust.soict.japango.dto.saved_result.DeleteSavedResultsResponse;
import vn.edu.hust.soict.japango.dto.saved_result.GetSavedResultsRequest;
import vn.edu.hust.soict.japango.dto.saved_result.SavedResultDTO;
import vn.edu.hust.soict.japango.entity.SavedResult;
import vn.edu.hust.soict.japango.exception.CustomExceptions;
import vn.edu.hust.soict.japango.repository.HistoryRepository;
import vn.edu.hust.soict.japango.repository.SavedResultRepository;
import vn.edu.hust.soict.japango.service.SavedResultService;
import vn.edu.hust.soict.japango.service.mapper.SavedResultMapper;

@Service
@RequiredArgsConstructor
@Slf4j
public class SavedResultServiceImpl implements SavedResultService {
    private final SecurityUtils securityUtils;
    private final SavedResultRepository savedResultRepository;
    private final SavedResultMapper savedResultMapper;
    private final HistoryRepository historyRepository;

    @Override
    public Page<SavedResultDTO> getSavedResults(GetSavedResultsRequest request) {
        Long userId;
        if ((userId = securityUtils.getUserId()) == null) {
            throw CustomExceptions.LOGIN_REQUIRED_EXCEPTION;
        }

        return savedResultRepository
                .findByUserIdAndKeyword(userId, request.getKeyword(), request.getPageable())
                .map(savedResultMapper::toDTO);
    }

    @Override
    public DeleteSavedResultsResponse deleteSavedResults() {
        Long userId;
        if ((userId = securityUtils.getUserId()) == null) {
            throw CustomExceptions.LOGIN_REQUIRED_EXCEPTION;
        }
        long count = savedResultRepository.deleteAllByUserId(userId);
        return DeleteSavedResultsResponse.builder().numberDeleted(count).build();
    }

    @Override
    public void deleteSavedResult(String uuid) {
        SavedResult savedResult = savedResultRepository.findByUuid(uuid)
                .orElseThrow(() -> CustomExceptions.SAVED_ITEM_NOT_EXISTS_EXCEPTION);

        if (SaveType.LIKED.equals(savedResult.getSaveType())) {
            historyRepository.findByUuid(savedResult.getHistoryUuid())
                    .ifPresent(history -> {
                        history.setLiked(false);
                        historyRepository.save(history);
                    });
        }
        savedResultRepository.delete(savedResult);
    }
}
