package course.java.sdm.exceptions;

public class DuplicateItemIDException extends Exception {

    public final long id;

    public DuplicateItemIDException(long id) {
        this.id = id;
    }

    public DuplicateItemIDException(String message, long id) {
        super(message);
        this.id = id;
    }
}
