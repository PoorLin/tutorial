package com.systex.tutorial.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Embeddable
public class CourseEnroll {
    @EmbeddedId
    private CourseEnrollPK courseEnrollPK;

    private Integer courseId;
}
