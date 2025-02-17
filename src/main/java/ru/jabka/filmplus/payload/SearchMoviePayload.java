package ru.jabka.filmplus.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import ru.jabka.filmplus.model.Genre;

import java.time.LocalDate;
@AllArgsConstructor
@Getter
@Builder
public class SearchMoviePayload {
    private LocalDate beginDate;
    private LocalDate endDate;
    private String title;
    private Long duration;
    private Genre genre;

    @Override
    public String toString() {
        return "SearchMoviePayload{" +
                ", beginDate=" + beginDate +
                ", endDate=" + endDate +
                ", title='" + title + '\'' +
                ", duration=" + duration +
                ", genre=" + genre +
                '}';
    }
}