package com.airbnb.suggest.rest;

import com.airbnb.suggest.model.Place;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

@Path("/suggest/v1")
@Produces(MediaType.APPLICATION_JSON)
public class SuggestionResource {

  private final Logger logger;
  private final List<Place> places;

  @Inject
  public SuggestionResource(Logger logger) {
    this.logger = logger;
    this.places = new ArrayList<Place>();
  }

  @POST
  @Path("like")
  public void like(Place place) {
    logger.info(place.toString());

    if (!places.contains(place)) {
      places.add(place);
    }
  }

  @GET
  @Path("suggest")
  public Place suggest() {
    Collections.shuffle(places);
    Place place = places.get(0);
    logger.info(place.toString());
    return place;
  }

}
