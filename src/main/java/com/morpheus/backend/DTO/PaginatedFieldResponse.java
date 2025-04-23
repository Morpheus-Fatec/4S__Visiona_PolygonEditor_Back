package com.morpheus.backend.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedFieldResponse<T> {
    private List<T> features;
    private int totalPages;
    private long totalItems;

}
