package com.example.demo.validator;

import lombok.Getter;

@Getter
public enum EducationWord {
    ELEMENTARY_SCHOOL("Elementary School (SD)"),
    JUNIOR_HIGH_SCHOOL("Junior High School (SMP)"),
    HIGH_SCHOOL("High School (SMA)"),
    UNIVERSITY_BACHELOR("University – Bachelor (Kuliah – lulusan S1)"),
    UNIVERSITY_MASTER("University – Master (Kuliah – lulusan S2)"),
    UNIVERSITY_DOCTORATE("University – Doctorate (Kuliah – lulusan S3)");

    private final String value;

    EducationWord(String value) {
        this.value = value;
    }
}
