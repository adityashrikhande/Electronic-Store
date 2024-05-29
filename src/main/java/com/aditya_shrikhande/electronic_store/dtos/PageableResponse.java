package com.aditya_shrikhande.electronic_store.dtos;

import lombok.*;

import java.util.List;

/**
 * A DTO (Data Transfer Object) representing a pageable response.
 * This class is used to encapsulate a list of content along with pagination information.
 *
 * @param <T> The type of content in the pageable response.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageableResponse<T> {
    /**
     * The list of content items.
     */
    private List<T> content;

    /**
     * The current page number.
     */
    private int pageNumber;

    /**
     * The number of items per page.
     */
    private int pageSize;

    /**
     * The total number of elements across all pages.
     */
    private long totalElement;

    /**
     * The total number of pages.
     */
    private int totalPages;

    /**
     * Indicates whether this is the last page.
     */
    private boolean lastPage;
}
