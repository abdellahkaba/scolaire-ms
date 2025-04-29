package com.isi.scolaire.halfYearly;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface HalfYearlyMapper {

    HalfYearly toHalfYearly(HalfYearlyRequest request);
    HalfYearlyResponse toHalfYearlyResponse(HalfYearly halfYearly);
    List<HalfYearlyResponse> toHalfYearlyResponsesList(List<HalfYearly> halfYearlies);
}
