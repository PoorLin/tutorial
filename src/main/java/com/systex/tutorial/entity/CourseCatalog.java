package com.systex.tutorial.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class CourseCatalog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer courseCatalogId;

    private String courseName;
}
