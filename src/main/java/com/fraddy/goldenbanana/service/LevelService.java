package com.fraddy.goldenbanana.service;


import com.fraddy.goldenbanana.domain.Level;

import java.util.List;

public interface LevelService {

    Level save(Level level);

    List<Level> search();

    Level retrieve(Long id);

    Level update(Level level);

    Level delete(Long id);
}
