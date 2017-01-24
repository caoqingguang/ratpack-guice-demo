package demo.app;

import java.util.Map;
import java.util.Properties;

import demo.app.handler.MyHandler;
import demo.app.guice.MyModule;
import ratpack.guice.Guice;
import ratpack.handling.Chain;
import ratpack.server.BaseDir;
import ratpack.server.RatpackServer;

public class App {

  public static void main(String[] args) throws Exception {
    Properties props = MyModule.tvmModuleInit("/conf.properties");
    Integer port = Integer.valueOf(props.getProperty("app.port"));
    RatpackServer.start(s -> s
        .serverConfig(c -> c.baseDir(BaseDir.find()).port(port))
        .registry(Guice.registry(b -> b.module(MyModule.class)))
        .handlers(App::dispatcher));
  }

  static void dispatcher(Chain chain) throws Exception{
    chain
      .path("path1", ctx -> ctx.render("from the path1 handler")) // Map to /bar
      .path("path2", MyHandler.class) // Map to a dependency injected handler
      .prefix("path3", nested -> { // Set up a nested routing block, which is delegated to `nestedHandler`
        nested.path(":var1/:var2?", ctx -> { // The path tokens are the :var1 and :var2 path components above
          Map<String, String> pathTokens = ctx.getPathTokens();
          ctx.render(
            "from the nested handler, var1: " + pathTokens.get("var1") +
              ", var2: " + pathTokens.get("var2")
          );
        });
      })
      .prefix("static", nested -> nested.fileSystem("assets/images", Chain::files)) // Bind the /static app path to the src/ratpack/assets/images dir
      .all(ctx -> ctx.render("unknown page!"));
  }
}
