package com.isi.scolaire.kind;

import com.isi.scolaire.common.PageResponse;
import com.isi.scolaire.registration.RegistrationResponse;

import java.util.List;

public interface KindService {
    KindResponse addKind(KindRequest request);
    KindResponse getKind(Long id);
    List<KindResponse> getAllKinds();
    KindResponse updateKind(KindRequest request);
    void deleteKindById(Long id);
    PageResponse<KindResponse> listAllKinds(int page, int size);
}
