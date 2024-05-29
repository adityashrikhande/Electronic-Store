package com.aditya_shrikhande.electronic_store.helper;

import com.aditya_shrikhande.electronic_store.dtos.PageableResponse;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A utility class to assist with converting and building PageableResponse objects from Spring Data Page objects.
 */
public class Helper {

    private static final Logger logger = LoggerFactory.getLogger(Helper.class);
    private static final ModelMapper modelMapper = new ModelMapper();

    private Helper() {
        // Private constructor to prevent instantiation
    }

    /**
     * Converts a Spring Data Page to a PageableResponse with the provided DTO type.
     *
     * @param page The Spring Data Page object.
     * @param dtoType The class type of the DTO.
     * @param <U> The type of the entity.
     * @param <V> The type of the DTO.
     * @return PageableResponse containing the mapped DTOs and pagination information.
     */
    public static <U, V> PageableResponse<V> getPageableResponse(Page<U> page, Class<V> dtoType) {
        logger.info("Converting Spring Data Page to PageableResponse with DTO type: {}", dtoType.getSimpleName());

        List<U> entityList = page.getContent();
        List<V> dtoList = entityList.stream()
                .map(entity -> modelMapper.map(entity, dtoType))
                .collect(Collectors.toList());

        PageableResponse<V> response = new PageableResponse<>();
        response.setContent(dtoList);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElement(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLastPage(page.isLast());

        return response;
    }
}
