package com.epam.edumanagementsystem.admin.journal_agenda.model.dto;

import javax.validation.constraints.Size;

public class SaveAgendaDto {

    @Size(max = 255, message = "Symbols can't be more than 255")
    private String classwork;
    @Size(max = 255, message = "Symbols can't be more than 255")
    private String homework;
    @Size(max = 255, message = "Symbols can't be more than 255")
    private String test;
    private String date;
    private Long classId;
    private Long courseId;

    public SaveAgendaDto(String classwork, String homework, String test) {
        this.classwork = classwork;
        this.homework = homework;
        this.test = test;
    }

    public SaveAgendaDto() {
    }

    public String getClasswork() {
        return classwork;
    }

    public void setClasswork(String classwork) {
        this.classwork = classwork;
    }

    public String getHomework() {
        return homework;
    }

    public void setHomework(String homework) {
        this.homework = homework;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "SaveAgendaDto{" +
                "classwork='" + classwork + '\'' +
                ", homework='" + homework + '\'' +
                ", test='" + test + '\'' +
                '}';
    }
}
