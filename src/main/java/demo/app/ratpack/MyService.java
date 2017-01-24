package demo.app.ratpack;

import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 * The service implementation.
 *
 * @see MyHandler
 */
public class MyService {

    @Inject
    @Named("db.user")
    private String value;

    public String getValue() {
        return value;
    }

}
