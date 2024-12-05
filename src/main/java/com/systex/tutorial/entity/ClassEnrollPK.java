package com.systex.tutorial.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Embeddable
public class ClassEnrollPK {
    private Integer classroomId;

    private Integer scheduleId;
}
