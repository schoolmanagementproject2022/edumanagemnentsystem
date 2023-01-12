package com.epam.edumanagementsystem.admin.timetable.mapper;

import com.epam.edumanagementsystem.admin.timetable.model.dto.TimetableDto;
import com.epam.edumanagementsystem.admin.timetable.model.entity.Timetable;

import java.util.List;
import java.util.stream.Collectors;

public class TimetableMapper {

    public static Timetable toTimetable(TimetableDto timetableDto) {
        Timetable timetable = new Timetable();

        timetable.setId(timetableDto.getId());
        timetable.setStartDate(timetableDto.getStartDate());
        timetable.setEndDate(timetableDto.getEndDate());
        timetable.setAcademicClass(timetableDto.getAcademicClass());
        timetable.setStatus(timetableDto.getStatus());
        return timetable;
    }

    public static TimetableDto toDto(Timetable timetable) {
        TimetableDto timetableDto = new TimetableDto();

        timetableDto.setId(timetable.getId());
        timetableDto.setStartDate(timetable.getStartDate());
        timetableDto.setEndDate(timetable.getEndDate());
        timetableDto.setAcademicClass(timetable.getAcademicClass());
        timetableDto.setStatus(timetable.getStatus());
        return timetableDto;
    }

    public static List<TimetableDto> toListOfTimetablesDto(List<Timetable> timetables) {
        return timetables.stream()
                .map(TimetableMapper::toDto)
                .collect(Collectors.toList());
    }

    public static List<Timetable> toListOfTimetables(List<TimetableDto> timetableDtoList) {
        return timetableDtoList.stream()
                .map(TimetableMapper::toTimetable)
                .collect(Collectors.toList());
    }

}
