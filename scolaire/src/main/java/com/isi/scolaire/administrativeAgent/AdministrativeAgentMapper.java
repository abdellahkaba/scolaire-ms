package com.isi.scolaire.administrativeAgent;

import org.mapstruct.Mapper;

import java.util.List;
@Mapper
public interface AdministrativeAgentMapper {

    AdministrativeAgent toAdministrativeAgent( AdministrativeAgentRequest request);
    AdministrativeAgentResponse toAdministrativeAgentResponse( AdministrativeAgent administrativeAgent);
    List<AdministrativeAgentResponse> toAdministrativeAgentResponseList( List<AdministrativeAgent> administrativeAgents);

}
