package ru.jabka.filmplus.validator;

import ru.jabka.filmplus.exception.BadRequestException;
import ru.jabka.filmplus.payload.SearchMoviePayload;

public class SearchMoviePayloadValidator {
    public static void validate(SearchMoviePayload search) {
        if (search.beginDate() != null &&
            search.endDate() != null &&
            search.beginDate().isAfter(search.endDate())) {
            throw BadRequestException.create("Дата начала должна быть больше даты окончания");
        }
    }
}
