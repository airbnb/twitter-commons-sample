package com.airbnb.suggest;

import com.airbnb.suggest.rest.RestModule;
import com.google.inject.Module;
import com.google.inject.servlet.GuiceFilter;
import com.twitter.common.application.AbstractApplication;
import com.twitter.common.application.Lifecycle;
import com.twitter.common.application.modules.HttpModule;
import com.twitter.common.application.modules.LogModule;
import com.twitter.common.application.modules.StatsModule;
import com.twitter.common.args.Arg;
import com.twitter.common.args.CmdLine;
import com.twitter.common.args.constraints.NotNull;
import com.twitter.common.net.http.GuiceServletConfig;
import com.twitter.common.net.http.HttpServerDispatch;
import org.mortbay.jetty.servlet.Context;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.logging.Logger;

/**
 * @author Tobi Knaup
 */
public final class SuggestionServiceMain extends AbstractApplication {

  @CmdLine(name = "server_set_path", help = "Joins the set of nodes located under this path in ZK")
  public static final Arg<String> SERVER_SET_PATH = Arg.create("/airbnb/service/suggest");

  @NotNull
  @CmdLine(name = "register_service", help = "Whether this instance should register itself in ZK")
  public static final Arg<Boolean> REGISTER_SERVICE = Arg.create(true);

  @Inject
  private Logger logger;

  @Inject
  private Lifecycle lifecycle;

  @Inject
  private HttpServerDispatch httpServer;

  @Inject
  private GuiceServletConfig servletConfig;


  @Override
  public void run() {
    logger.info("Service started");

    addRestSupport();

    lifecycle.awaitShutdown();
  }

  @Override
  public Iterable<? extends Module> getModules() {
    return Arrays.asList(
        new HttpModule(),
        new LogModule(),
        new RestModule(),
        new StatsModule()
    );
  }

  private void addRestSupport() {
    Context context = httpServer.getRootContext();
    context.addFilter(GuiceFilter.class, "/suggest/*", 0);
    context.addEventListener(servletConfig);
  }

}
