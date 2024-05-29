package com.aditya_shrikhande.electronic_store.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Service interface for handling file-related operations.
 */
public interface FileService {

    /**
     * Uploads an image file to the specified path.
     *
     * @param file The image file to be uploaded.
     * @param path The destination path for the upload.
     * @return The filename of the uploaded image.
     * @throws IOException If an I/O error occurs during the upload.
     */
    String uploadImage(MultipartFile file, String path) throws IOException;

    /**
     * Retrieves an input stream for accessing a resource.
     *
     * @param path The path to the resource.
     * @param name The name of the resource.
     * @return An InputStream for the requested resource.
     * @throws FileNotFoundException If the requested resource is not found.
     */
    InputStream getResource(String path, String name) throws FileNotFoundException;
}
