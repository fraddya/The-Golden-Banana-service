package com.fraddy.goldenbanana.mapper;

import com.fraddy.goldenbanana.domain.Level;
import com.fraddy.goldenbanana.dto.request.level.LevelCreateRequest;
import com.fraddy.goldenbanana.dto.response.level.LevelSearchResponse;
import com.fraddy.goldenbanana.enums.Status;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",imports = {Status.class})
public interface LevelMapper {

    Level mapToLevel(LevelCreateRequest request);

    LevelSearchResponse mapToViewResponse(Level level1);

    List<LevelSearchResponse> mapToSuggestionResponse(List<Level> results);
}
