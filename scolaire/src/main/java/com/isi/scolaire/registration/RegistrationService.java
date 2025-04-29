package com.isi.scolaire.registration;


import com.isi.scolaire.common.PageResponse;

import java.util.List;

public interface RegistrationService {

    RegistrationResponse addRegistration(RegistrationRequest request);
    RegistrationResponse getRegistrationById(Long id);
    List<RegistrationResponse> getAllRegistrations();
    RegistrationResponse updateRegistration(RegistrationRequest request);
    void deleteRegistrationById(Long id);
    PageResponse<RegistrationResponse> listAllRegistrations(int page, int size);
}
