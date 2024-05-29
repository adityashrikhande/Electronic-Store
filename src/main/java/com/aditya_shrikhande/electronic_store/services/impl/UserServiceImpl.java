package com.aditya_shrikhande.electronic_store.services.impl;

import com.aditya_shrikhande.electronic_store.exceptions.ResourceNotFoundException;
import com.aditya_shrikhande.electronic_store.repositories.CartItemRepository;
import com.aditya_shrikhande.electronic_store.repositories.UserRepository;
import com.aditya_shrikhande.electronic_store.dtos.PageableResponse;
import com.aditya_shrikhande.electronic_store.dtos.UserDto;
import com.aditya_shrikhande.electronic_store.entities.User;
import com.aditya_shrikhande.electronic_store.helper.DataToExcelHelper;
import com.aditya_shrikhande.electronic_store.helper.Helper;
import com.aditya_shrikhande.electronic_store.services.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementation of the UserService interface for managing user-related operations.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ModelMapper mapper;

    String[] userHeaders = {"userId", "name", "email", "password", "gender", "about", "imageName"};

    @Value("${user.profile.image.path}")
    private String imagePath;

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    /**
     * Create a new user.
     *
     * @param userDto The UserDto containing user details.
     * @return The created UserDto.
     */
    @Override
    public UserDto createUser(UserDto userDto) {
        logger.info("Creating user: {}", userDto.getName());

        // Generate a unique user ID
        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);

        //encoding password
        userDto.setPassword(userDto.getPassword());
        System.out.println("Password ---  " +userDto.getPassword());
        // Convert DTO to Entity
        User user = dtoToEntity(userDto);
        User savedUser = userRepository.save(user);

        logger.info("User created: {}", savedUser.getUserId());

        // Convert Entity back to DTO for response
        return entityToDto(savedUser);
    }

    /**
     * Update an existing user's information.
     *
     * @param userDto The UserDto containing updated user details.
     * @param userId  The ID of the user to update.
     * @return The updated UserDto.
     */
    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        logger.info("Updating user: {}", userId);

        // Find the user by ID or throw a ResourceNotFoundException
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with given id: " + userId));

        // Update user properties
        user.setName(userDto.getName());
        user.setAbout(userDto.getAbout());
        user.setGender(userDto.getGender());
        user.setPassword(userDto.getPassword());
        user.setImageName(userDto.getImageName());

        // Save the updated user
        User updatedUser = userRepository.save(user);

        logger.info("User updated: {}", updatedUser.getUserId());

        // Convert Entity back to DTO for response
        return entityToDto(updatedUser);
    }

    /**
     * Delete a user by ID.
     *
     * @param userId The ID of the user to delete.
     */
    @Override
    public void deleteUser(String userId) {
        logger.info("Deleting user: {}", userId);

        // Find the user by ID or throw a ResourceNotFoundException
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with given id: " + userId));

        String fullPath = imagePath + user.getImageName();

        try {
            // Delete the user's profile image
            Path path = Paths.get(fullPath);
            Files.delete(path);
            logger.info("User image deleted: {}", fullPath);
        } catch (NoSuchFileException ex) {
            logger.info("User image not found in folder: {}", fullPath);
        } catch (IOException e) {
            logger.error("IOException while deleting user image: {}", e.getMessage());
            e.printStackTrace();
        }

        // Delete the user from the repository
        userRepository.delete(user);

        logger.info("User deleted: {}", userId);
    }

    /**
     * Get a paginated list of all users.
     *
     * @param pageNumber The page number to retrieve.
     * @param pageSize   The number of users per page.
     * @param sortBy     The field to sort by.
     * @param sortDir    The sort direction (asc or desc).
     * @return A PageableResponse containing a list of UserDto objects.
     */
    @Override
    public PageableResponse<UserDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ?
                (Sort.by(sortBy).descending()) :
                (Sort.by(sortBy).ascending());

        // By default, page number starts from 0
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<User> page = userRepository.findAll(pageable);
        PageableResponse<UserDto> response = Helper.getPageableResponse(page, UserDto.class);

        return response;
    }

    /**
     * Get a user by ID.
     *
     * @param userId The ID of the user to retrieve.
     * @return The UserDto representing the user.
     */
    @Override
    public UserDto getUserById(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with given id: " + userId));
        return entityToDto(user);
    }
    /**
     * Get a user by email.
     *
     * @param email The email of the user to retrieve.
     * @return The UserDto representing the user.
     */
    @Override
    public UserDto getUserByEmail(String email) {

        //User user = userRepository.findUserWithCartItemsAndProducts(email);


        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with given email: " + email));

        return entityToDto(user);
    }

    /**
     * Search for users by name containing a specified keyword.
     *
     * @param keyword The keyword to search for in user names.
     * @return A list of UserDto objects that match the search criteria.
     */
    @Override
    public List<UserDto> searchUsers(String keyword) {
        List<User> users = userRepository.findByNameContaining(keyword);
        List<UserDto> usersDtoList = users.stream().map(this::entityToDto).collect(Collectors.toList());
        return usersDtoList;
    }

    @Override
    public ByteArrayInputStream getDataInExcel() {
        List<?> userList = userRepository.findAll();
        ByteArrayInputStream excelFile = DataToExcelHelper.createExcelFile(userList, userHeaders, "Users", "C:\\Users\\NIKHIL\\OneDrive\\Desktop");
        return excelFile;
    }

    /**
     * Converts a User entity to a UserDto.
     *
     * @param user The User entity to convert.
     * @return The corresponding UserDto.
     */
    private UserDto entityToDto(User user) {
        return mapper.map(user, UserDto.class);
    }

    /**
     * Converts a UserDto to a User entity.
     *
     * @param userDto The UserDto to convert.
     * @return The corresponding User entity.
     */
    private User dtoToEntity(UserDto userDto) {
        return mapper.map(userDto, User.class);
    }

}
