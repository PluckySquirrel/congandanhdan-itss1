package vn.edu.hust.soict.japango.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import vn.edu.hust.soict.japango.common.utils.SecurityUtils;
import vn.edu.hust.soict.japango.dto.history.DeleteHistoryResponse;
import vn.edu.hust.soict.japango.dto.saved_result.DeleteSavedResultsResponse;
import vn.edu.hust.soict.japango.dto.saved_result.SavedResultDTO;
import vn.edu.hust.soict.japango.exception.CustomExceptions;
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

    @Override
    public Page<SavedResultDTO> getSavedResults(int page, int size) {
        Long userId;
        if ((userId = securityUtils.getUserId()) == null) {
            throw CustomExceptions.LOGIN_REQUIRED_EXCEPTION;
        }

        return savedResultRepository
                .findByUserIdOrderByCreatedAtDesc(userId, PageRequest.of(page, size))
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
}
