package com.isi.scolaire.exception;

import java.util.Map;

public record ErrorResponse(
        Map<String, String> errors
) {
}
