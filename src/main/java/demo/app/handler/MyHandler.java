package demo.app.handler;

import javax.inject.Inject;
import javax.inject.Singleton;

import demo.app.service.MyService;
import demo.app.guice.MyModule;
import ratpack.handling.Context;
import ratpack.handling.Handler;

/**
 * A handler implementation that is created via dependency injection.
 *
 * @see MyModule
 */
@Singleton
public class MyHandler implements Handler {

  private MyService myService;

  @Inject
  public MyHandler(MyService myService) {
    this.myService = myService;
  }

  @Override
  public void handle(Context context) {
    context.render("this is path2, service data " + myService.getDb());
  }
}
