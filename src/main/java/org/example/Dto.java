package org.example;

import java.util.List;

public class Dto {
    public final String some;
    public final DtoEnum dtoEnum;

    public final InnerDto innerDto;

    public Dto(String some, DtoEnum dtoEnum, InnerDto innerDto) {
        this.some = some;
        this.dtoEnum = dtoEnum;
        this.innerDto = innerDto;
    }

    public enum DtoEnum {
        A,
        B,
        C
    }

    public static class InnerDto {
        public final Long num;
        public final List<String> strings;

        public InnerDto(Long num, List<String> strings) {
            this.num = num;
            this.strings = strings;
        }
    }
}
