package tools.collections;

/**
 * Created by sircodesalot on 14-4-26.
 */
public class QueryException extends RuntimeException {
    public QueryException(String format, String... args) {
        super(String.format(format, args));
    }
}
