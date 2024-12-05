package com.systex.tutorial.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Embeddable
public class CourseEnrollPK {
    private Integer studentId;

    private Integer schedule;
}
