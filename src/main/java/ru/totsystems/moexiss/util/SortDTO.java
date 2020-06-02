package ru.totsystems.moexiss.util;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class SortDTO {
    private final String sortDirection;
    private final String sortAttribute;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String emitentTitle;
}
