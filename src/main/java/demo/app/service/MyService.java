package demo.app.service;



import com.google.common.collect.Maps;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import demo.app.handler.MyHandler;

/**
 * The service implementation.
 *
 * @see MyHandler
 */
@Singleton
public class MyService {

    @Inject
    @Named("db.password")
    private String password;
    @Inject
    @Named("db.user")
    private String user;

    public Map<String,String> getDb() {
        Map<String, String> result = Maps.newLinkedHashMap();
        result.put("user",user);
        result.put("password",password);
        return result;
    }

}
