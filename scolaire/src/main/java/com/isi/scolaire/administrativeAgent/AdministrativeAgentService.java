package com.isi.scolaire.administrativeAgent;


import com.isi.scolaire.common.PageResponse;

import java.util.List;

public interface AdministrativeAgentService {

    AdministrativeAgentResponse addAgent(AdministrativeAgentRequest request);
    AdministrativeAgentResponse getAgent(Long id);
    List<AdministrativeAgentResponse> getAllAgents();
    AdministrativeAgentResponse updateAgent(AdministrativeAgentRequest request);
    void deleteAgent(Long id);
    PageResponse<AdministrativeAgentResponse> listAllAdminAgents(int page, int size);
}
