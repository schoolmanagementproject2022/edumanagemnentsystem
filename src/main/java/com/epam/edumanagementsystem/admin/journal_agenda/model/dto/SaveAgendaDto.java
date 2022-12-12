package com.epam.edumanagementsystem.admin.journal_agenda.model.dto;

public class SaveAgendaDto {

    private String classwork;
    private String homework;
    private String test;

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

    @Override
    public String toString() {
        return "SaveAgendaDto{" +
                "classwork='" + classwork + '\'' +
                ", homework='" + homework + '\'' +
                ", test='" + test + '\'' +
                '}';
    }
}
