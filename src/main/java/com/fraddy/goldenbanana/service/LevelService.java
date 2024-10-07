package com.fraddy.goldenbanana.service;


import com.fraddy.goldenbanana.domain.Level;
import com.fraddy.goldenbanana.domain.User;
import com.fraddy.goldenbanana.domain.criteria.UserCriteria;
import org.springframework.data.domain.Page;

import java.util.List;

public interface LevelService {

    Level save(Level level);

    List<Level> search(Level level);

    Level retrieve(Long id);

    Level update(Level level);

    Level delete(Long id);
}
