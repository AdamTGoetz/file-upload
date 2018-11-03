package com.goetz.fileupload.helper;

/**
 * Constants helper class
 * 
 * @author agoetz
 */
public class Constants {

    public final static String CSV_EXTENSION = "csv";
    public final static String DOT_CSV_EXTENSION = "." + CSV_EXTENSION;
    public final static int MAX_FILENAME_CHARS = 32;
    public static final String INVALID_EXTENSION = "Invalid file extension ";
    public static final String ACCEPTED_FILE_EXTENSION = "validFileExtension";
    public static final String ACCEPTED_FILE_SIZE = "validFileSize";
    public static final String VALID_CONTENT_TYPE = "validContentType";
    public static final String MSG_SAVE_ERROR = " was not saved at ";
    public static final String FAILED = "failed";
    public static final String CSV_CONTENT_TYPE = "text/csv";
    public static final String MSG_REAL_TYPE = "type: ";
    public static final String MSG_DELETED = " deleted? ";
    public static final String MSG_SAVED_AT = "Saved at ";

    private Constants() {
        super();
    }

}
