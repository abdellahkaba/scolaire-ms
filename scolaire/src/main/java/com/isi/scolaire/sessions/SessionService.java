package com.isi.scolaire.sessions;

import com.isi.scolaire.common.PageResponse;

import java.util.List;

public interface SessionService {

    SessionResponse addSession(SessionRequest request);
    SessionResponse getSessionById(Long id);
    List<SessionResponse> getAllSessions();
    SessionResponse updateSession(SessionRequest request);
    void deleteSessionById(Long id);
    PageResponse<SessionResponse> listAllSessions(int page, int size);
}
