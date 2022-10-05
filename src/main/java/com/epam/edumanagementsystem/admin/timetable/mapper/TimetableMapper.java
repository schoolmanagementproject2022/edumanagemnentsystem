package com.epam.edumanagementsystem.admin.timetable.mapper;

import com.epam.edumanagementsystem.admin.timetable.model.dto.TimetableDto;
import com.epam.edumanagementsystem.admin.timetable.model.entity.Timetable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TimetableMapper {

    public static Timetable toTimetable(TimetableDto timetableDto) {
        Timetable timetable = new Timetable();
        timetable.setId(timetableDto.getId());
        timetable.setStartDate(timetableDto.getStartDate());
        timetable.setEndDate(timetableDto.getEndDate());
        timetable.setAcademicClass(timetableDto.getAcademicClass());
        return timetable;
    }

    public static TimetableDto toDto(Timetable timetable) {
        TimetableDto timetableDto = new TimetableDto();
        timetableDto.setId(timetable.getId());
        timetableDto.setStartDate(timetable.getStartDate());
        timetableDto.setEndDate(timetable.getEndDate());
        timetableDto.setAcademicClass(timetable.getAcademicClass());
        return timetableDto;
    }

    public static List<TimetableDto> toListOfTimetablesDto(List<Timetable> timetables) {
        List<TimetableDto> timetableDtoList = new ArrayList<>();
        for (Timetable timetable : timetables) {
            timetableDtoList.add(toDto(timetable));
        }
        return timetableDtoList;
    }

    public static List<Timetable> toListOfTimetables(List<TimetableDto> timetableDtoList) {
        List<Timetable> timetables = new ArrayList<>();
        for (TimetableDto timetableDto : timetableDtoList) {
            timetables.add(toTimetable(timetableDto));
        }
        return timetables;
    }
}
