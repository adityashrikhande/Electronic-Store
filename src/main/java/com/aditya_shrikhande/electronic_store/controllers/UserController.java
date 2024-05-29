package com.aditya_shrikhande.electronic_store.controllers;

import com.aditya_shrikhande.electronic_store.dtos.ApiResponseMessage;
import com.aditya_shrikhande.electronic_store.dtos.ImageResponse;
import com.aditya_shrikhande.electronic_store.dtos.PageableResponse;
import com.aditya_shrikhande.electronic_store.dtos.UserDto;
import com.aditya_shrikhande.electronic_store.services.FileService;
import com.aditya_shrikhande.electronic_store.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    @Value("${user.profile.image.path}")
    private String imageUploadPath;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * Controller method to create a new user.
     *
     * @param userDto The user data to be created.
     * @return ResponseEntity containing the created UserDto and HTTP status.
     */
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        logger.info("Creating user: {}", userDto.getName());
        UserDto user = userService.createUser(userDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    /**
     * Controller method to update an existing user by their user ID.
     *
     * @param userId   The ID of the user to be updated.
     * @param userDto  The updated user data.
     * @return ResponseEntity containing the updated UserDto and HTTP status.
     */
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @PathVariable("userId") String userId, @RequestBody UserDto userDto) {
        logger.info("Updating user with ID: {}", userId);
        UserDto updatedUserDto = userService.updateUser(userDto, userId);
        return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
    }


    /**
     * Controller method to delete a user by their user ID.
     *
     * @param userId The ID of the user to be deleted.
     * @return ResponseEntity containing an ApiResponseMessage and HTTP status.
     * @throws IOException If an error occurs while deleting the user.
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable("userId") String userId) throws IOException {
        logger.info("Deleting user with ID: {}", userId);
        userService.deleteUser(userId);
        ApiResponseMessage message = ApiResponseMessage.builder()
                .message("User is deleted successfully")
                .success(true)
                .status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * Controller method to retrieve a pageable list of all users.
     *
     * @param pageNumber The page number to retrieve (default is 0).
     * @param pageSize   The number of users per page (default is 10).
     * @param sortBy     The field to sort users by (default is "name").
     * @param sortDir    The direction of sorting ("asc" or "desc", default is "asc").
     * @return ResponseEntity containing a PageableResponse of UserDto objects and HTTP status.
     */
    @GetMapping
    public ResponseEntity<PageableResponse<UserDto>> getAllUsers(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        logger.info("Retrieving all users - Page: {}, PageSize: {}, SortBy: {}, SortDir: {}",
                pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(userService.getAllUsers(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
    }


    @GetMapping("/excel")
    public ResponseEntity<InputStreamResource> download() {
        String filename = "users.xlsx";
        ByteArrayInputStream actualData = userService.getDataInExcel();
        InputStreamResource file = new InputStreamResource(actualData);

        ResponseEntity<InputStreamResource> body = ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);

        return body;

    }


    /**
     * Controller method to retrieve a user by their user ID.
     *
     * @param userId The ID of the user to retrieve.
     * @return ResponseEntity containing the UserDto and HTTP status.
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String userId) {
        logger.info("Retrieving user by ID: {}", userId);
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.FOUND);
    }

    /**
     * Controller method to retrieve a user by their email address.
     *
     * @param email The email address of the user to retrieve.
     * @return ResponseEntity containing the UserDto and HTTP status.
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        logger.info("Retrieving user by email: {}", email);
        return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.FOUND);
    }

    /**
     * Controller method to search for users by a keyword.
     *
     * @param keyword The keyword to search for.
     * @return ResponseEntity containing a List of UserDto objects and HTTP status.
     */
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keyword) {
        logger.info("Searching for users with keyword: {}", keyword);
        return new ResponseEntity<>(userService.searchUsers(keyword), HttpStatus.FOUND);
    }


    /**
     * Controller method to upload a user image.
     *
     * @param image   The image file to be uploaded.
     * @param userId  The ID of the user for whom the image is being uploaded.
     * @return ResponseEntity containing an ImageResponse and HTTP status.
     * @throws IOException If an error occurs while uploading the image.
     */
    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse> uploadUserImage(
            @RequestParam("userImage") MultipartFile image,
            @PathVariable String userId) throws IOException {
        logger.info("Uploading image for user with ID: {}", userId);

        String imageName = fileService.uploadImage(image, imageUploadPath);

        UserDto user = userService.getUserById(userId);
        user.setImageName(imageName);
        UserDto updatedUserDto = userService.updateUser(user, userId);

        ImageResponse imageResponse = ImageResponse.builder()
                .imageName(imageName)
                .message("Image uploaded successfully!")
                .success(true)
                .status(HttpStatus.CREATED)
                .build();

        return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
    }

    /**
     * Controller method to serve a user's image.
     *
     * @param userId   The ID of the user whose image is being served.
     * @param response The HttpServletResponse used to serve the image.
     * @throws IOException If an error occurs while serving the image.
     */
    @GetMapping("/image/{userId}")
    public void serveUserImage(@PathVariable String userId, HttpServletResponse response) throws IOException {
        UserDto user = userService.getUserById(userId);
        logger.info("Serving image for user: {} - Image name: {}", user.getName(), user.getImageName());

        InputStream resource = fileService.getResource(imageUploadPath, user.getImageName());

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }




}
