package com.aditya_shrikhande.electronic_store.services;

import com.aditya_shrikhande.electronic_store.dtos.PageableResponse;
import com.aditya_shrikhande.electronic_store.dtos.UserDto;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

/**
 * Service interface for managing user-related operations.
 */
public interface UserService {

    /**
     * Creates a new user.
     *
     * @param userDto The user data for creation.
     * @return The created user's details.
     */
    UserDto createUser(UserDto userDto);

    /**
     * Updates user information.
     *
     * @param userDto The updated user data.
     * @param userId  The ID of the user to update.
     * @return The updated user's details.
     */
    UserDto updateUser(UserDto userDto, String userId);

    /**
     * Deletes a user.
     *
     * @param userId The ID of the user to delete.
     * @throws IOException If there's an error during deletion.
     */
    void deleteUser(String userId) throws IOException;

    /**
     * Retrieves a pageable list of users.
     *
     * @param pageNumber The page number.
     * @param pageSize   The page size.
     * @param sortBy     The sorting field.
     * @param sortDir    The sorting direction.
     * @return A pageable response containing user details.
     */
    PageableResponse<UserDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir);

    /**
     * Retrieves a user by ID.
     *
     * @param userId The ID of the user to retrieve.
     * @return The user's details.
     */
    UserDto getUserById(String userId);

    /**
     * Retrieves a user by email.
     *
     * @param email The email address of the user.
     * @return The user's details.
     */
    UserDto getUserByEmail(String email);

    /**
     * Searches for users based on a keyword.
     *
     * @param keyword The keyword to search for in user names or emails.
     * @return A list of users matching the search.
     */
    List<UserDto> searchUsers(String keyword);

    ByteArrayInputStream getDataInExcel();
}
