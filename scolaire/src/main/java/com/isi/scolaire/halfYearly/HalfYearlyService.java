package com.isi.scolaire.halfYearly;

import com.isi.scolaire.common.PageResponse;

import java.util.List;

public interface HalfYearlyService {

    HalfYearlyResponse newHalfYearly(HalfYearlyRequest request);
    HalfYearlyResponse getHalfYearlyById(Long id);
    List<HalfYearlyResponse> getAllHalfYearly();
    HalfYearlyResponse updateHalfYearly(HalfYearlyRequest request);
    void deleteHalfYearlyById(Long id);
    PageResponse<HalfYearlyResponse> listAllHalfYearly(int page, int size);
}
