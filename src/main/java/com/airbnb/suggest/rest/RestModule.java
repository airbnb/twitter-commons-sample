package com.airbnb.suggest.rest;

import com.airbnb.suggest.rest.util.JsonExceptionMapper;
import com.airbnb.suggest.rest.util.RequestStatsFilter;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.google.inject.servlet.ServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import java.util.HashSet;
import java.util.Set;

/**
 * Configures our REST service
 *
 * @author Tobi Knaup
 */
public class RestModule extends ServletModule {

  @Override
  protected void configureServlets() {
    super.configureServlets();

    // JSON mapper, maps JSON to/from POJOs
    bind(JacksonJsonProvider.class).in(Singleton.class);
    // Turns exceptions into JSON responses
    bind(JsonExceptionMapper.class).in(Singleton.class);

    // Serve all URLs through Guice
    serve("/*").with(GuiceContainer.class);

    // The actual REST Endpoints
    bind(SuggestionResource.class).in(Singleton.class);

    // Stats
    filter("/suggest/v1/like").through(new RequestStatsFilter("suggest_v1_like"));
    filter("/suggest/v1/all_places").through(new RequestStatsFilter("suggest_v1_all_places"));
  }

}
