package demo.app.guice;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.name.Names;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.Properties;

import demo.app.handler.LoggingHandler;
import demo.app.handler.MyHandler;
import ratpack.handling.HandlerDecorator;

/**
 * An example Guice module.
 */
public class MyModule extends AbstractModule {

  private static Properties props=new Properties();
  public static Properties tvmModuleInit(String config){
    InputStream is=null;
    Properties props=null;
    try {
      Path path = Paths.get(config);
      System.out.println(path);
      props=new Properties();
      props.load(Class.class.getResourceAsStream(config));
    } catch (IOException e) {
      System.out.println(e);
    }finally {
      if(is!=null){
        try {
          is.close();
        } catch (IOException e) {
        }
      }
    }
    MyModule.props=props;
    return props;
  }


  protected void configure() {
    loadConfig(props);
//    bind(MyService.class).to(MyServiceImpl.class);
    bind(MyHandler.class);
    Multibinder.newSetBinder(binder(), HandlerDecorator.class).addBinding().toInstance(HandlerDecorator.prepend(new LoggingHandler()));
  }

  private void loadConfig(Properties props) {
      Enumeration<Object> keys = props.keys();
      while (keys.hasMoreElements()) {
        Object key = keys.nextElement();
        bindConstant().annotatedWith(Names.named(key.toString())).to(props.getProperty(key.toString()));
      }
  }
}
