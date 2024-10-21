package com.fraddy.goldenbanana.service;


import com.fraddy.goldenbanana.domain.UserLevelProgress;
import com.fraddy.goldenbanana.domain.criteria.UserLevelProgressCriteria;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserLevelProgressService {

    UserLevelProgress save(UserLevelProgress userLevelProgress);

    Page<UserLevelProgress> search(UserLevelProgressCriteria criteria);

    UserLevelProgress retrieve(Long id);

    UserLevelProgress update(UserLevelProgress userLevelProgress);

    UserLevelProgress delete(Long id);

    List<UserLevelProgress> leaderBoard();

}
