package com.fraddy.goldenbanana.service.Impl;


import com.fraddy.goldenbanana.domain.QUserLevelProgress;
import com.fraddy.goldenbanana.domain.User;
import com.fraddy.goldenbanana.domain.UserLevelProgress;
import com.fraddy.goldenbanana.domain.base.ComplexValidationException;
import com.fraddy.goldenbanana.domain.criteria.UserLevelProgressCriteria;
import com.fraddy.goldenbanana.enums.Status;
import com.fraddy.goldenbanana.repository.UserLevelProgressRepository;
import com.fraddy.goldenbanana.service.UserLevelProgressService;
import com.querydsl.core.BooleanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class UserLevelProgressServiceImpl implements UserLevelProgressService {

    @Autowired
    private UserLevelProgressRepository userLevelProgressRepository;


    @Transactional
    @Override
    public UserLevelProgress save(UserLevelProgress userLevelProgress) {
        userLevelProgress.setStatus(Status.ACTIVE);
        return userLevelProgressRepository.save(userLevelProgress);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<UserLevelProgress> search(UserLevelProgressCriteria criteria) {

        PageRequest page = PageRequest.of(criteria.getPageNumber() - 1, criteria.getPageSize(),
                Sort.by(Sort.Direction.fromOptionalString(criteria.getSortDirection()).orElse(Sort.Direction.DESC),
                        criteria.getSortProperty()));

        Page<UserLevelProgress> userLevelProgresses = null;

        BooleanBuilder booleanBuilder = new BooleanBuilder(QUserLevelProgress.userLevelProgress.status.ne(Status.DELETED));

        if (criteria.getLevelId() != null ) {
            booleanBuilder.and(QUserLevelProgress.userLevelProgress.level().id.eq(criteria.getLevelId()));
        }
        if (criteria.getUserId() != null ) {
            booleanBuilder.and(QUserLevelProgress.userLevelProgress.level().id.eq(criteria.getUserId()));
        }

        if (booleanBuilder.hasValue()){
            userLevelProgresses = userLevelProgressRepository.findAll(booleanBuilder, page);
        } else {
            userLevelProgresses = userLevelProgressRepository.findAll(page);
        }
        return userLevelProgresses;
    }

    @Transactional(readOnly = true)
    @Override
    public UserLevelProgress retrieve(Long id) {
        Optional<UserLevelProgress> userLevelProgress = userLevelProgressRepository.findById(id);
        if (!userLevelProgress.isPresent()) {
            throw new ComplexValidationException("user level progress retrieval","User level progress (%s) not found  ");
        }
        return userLevelProgress.get();
    }


    @Transactional
    @Override
    public UserLevelProgress update(UserLevelProgress userLevelProgress) {
        Optional<UserLevelProgress> userLevelProgressPersisted = userLevelProgressRepository.findById(userLevelProgress.getId());
        if (!userLevelProgressPersisted.isPresent()) {
            throw new ComplexValidationException("user level progress retrieval","User level progress (%s) not found  ");
        } else {
            UserLevelProgress userLevelProgressDb = userLevelProgressPersisted.get();
            updateFields(userLevelProgress, userLevelProgressDb);
            return userLevelProgressRepository.save(userLevelProgressDb);
        }
    }

    private void updateFields(UserLevelProgress userLevelProgress, UserLevelProgress userLevelProgressDb) {
        if (userLevelProgress.getUser() != null) userLevelProgressDb.setUser(userLevelProgress.getUser());
        if (userLevelProgress.getLevel() != null) userLevelProgressDb.setLevel(userLevelProgress.getLevel());
        if (userLevelProgress.getMarks() != null) userLevelProgressDb.setMarks(userLevelProgress.getMarks());
        if (userLevelProgress.getTimeInSeconds() != null) userLevelProgressDb.setTimeInSeconds(userLevelProgress.getTimeInSeconds());

    }

    @Transactional
    @Override
    public UserLevelProgress delete(Long id) {
        UserLevelProgress userLevelProgress = userLevelProgressRepository.getReferenceById(id);
        if (userLevelProgress != null) {
            userLevelProgress.setStatus(Status.DELETED);
            return userLevelProgressRepository.save(userLevelProgress);
        } else {
            throw new ComplexValidationException("user level progress retrieval","User level progress (%s) not found  ");
        }
    }

    @Transactional
    @Override
    public List<UserLevelProgress> leaderBoard() {
        List<UserLevelProgress> userLevelProgressList = userLevelProgressRepository.findAll();

        if (userLevelProgressList != null && !userLevelProgressList.isEmpty()) {
            // Group by user and sum the marks
            Map<User, BigInteger> userMarksMap = userLevelProgressList.stream()
                    .collect(Collectors.groupingBy(
                            UserLevelProgress::getUser,
                            Collectors.mapping(UserLevelProgress::getMarks, Collectors.reducing(BigInteger.ZERO, BigInteger::add))
                    ));

            List<UserLevelProgress> sortedList = userMarksMap.entrySet().stream()
                    .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                    .map(entry -> {
                        UserLevelProgress ulp = new UserLevelProgress();
                        ulp.setUser(entry.getKey());
                        ulp.setMarks(entry.getValue());
                        return ulp;
                    })
                    .collect(Collectors.toList());

            return sortedList;
        } else {
            throw new ComplexValidationException("user level progress retrieval", "User level progress not found.");
        }
    }

}
