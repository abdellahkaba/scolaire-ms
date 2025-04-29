package com.isi.scolaire.sessions;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SessionResponse {

    private Long id;
    private String name;
    private LocalDateTime bigInDate;
    private LocalDateTime endDate;
    private String description;
    private Long teacherId;
    private Long courseId;
    private String courseName;
    private String teacherFirstName;
    private String teacherLastName;
    private boolean archive;
}
