package com.goetz.fileupload.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.Strings;
import com.google.common.io.Files;

/**
 * Helper class
 * 
 * @author agoetz
 */
public class FileHelper {
    private final Logger LOGGER = LoggerFactory.getLogger(FileHelper.class);

    /**
     * Checks if fileName is a .csv file
     * 
     * @param fileName
     * @return
     */
    public boolean validFileExtension(String fileName) {
        if (Strings.isNullOrEmpty(fileName)) {
            return false;
        }
        String ext = Files.getFileExtension(fileName);
        if (Strings.isNullOrEmpty(ext)) {
            LOGGER.error(Constants.INVALID_EXTENSION);
            return false;
        } else if (!ext.equalsIgnoreCase(Constants.CSV_EXTENSION)) {
            LOGGER.error(Constants.INVALID_EXTENSION + ext);
            return false;
        }
        return true;
    }

    /**
     * Check the content-type of a file
     * 
     * @param filePath
     * @return
     */
    public boolean validContentType(String filePath) {
        try (InputStream inputStream = new FileInputStream(filePath)) {
            Tika tika = new Tika();
            String type = tika.detect(inputStream, filePath);
            LOGGER.info(Constants.MSG_REAL_TYPE + type);
            if (!Strings.isNullOrEmpty(type) && Constants.CSV_CONTENT_TYPE.equalsIgnoreCase(type)) {
                return true;
            }
        } catch (IOException e) {
            LOGGER.error(String.format("IOException: %s", e.getMessage()));
        }
        return false;
    }

    /**
     * Save a file
     * 
     * @param file
     * @param filePath
     * @return file save destination
     * @throws IOException
     */
    public String save(MultipartFile file, String filePath) {
        try {
            String destFilename = randomFilename();
            File destination = new File(Paths.get(filePath, destFilename).toString());
            FileUtils.copyInputStreamToFile(file.getInputStream(), destination);
            LOGGER.info(Constants.MSG_SAVED_AT + Paths.get(filePath, destFilename).toString());
            return destFilename;
        } catch (IOException e) {
            LOGGER.error(String.format("IOException: %s", e.getMessage()));
            return null;
        }
    }

    /**
     * Delete a file
     * 
     * @param path
     */
    public void delete(String path) {
        LOGGER.info(path + Constants.MSG_DELETED + (new File(path).delete()));
    }

    /**
     * Return a randomized filename
     * 
     * @return
     */
    private String randomFilename() {
        return (new StringBuilder())
                .append(RandomStringUtils.randomAlphabetic(Constants.MAX_FILENAME_CHARS).toLowerCase())
                .append(Constants.DOT_CSV_EXTENSION).toString();
    }
}
