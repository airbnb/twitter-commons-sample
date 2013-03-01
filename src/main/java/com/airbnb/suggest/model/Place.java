package com.airbnb.suggest.model;

/**
 * A POJO for a place
 *
 * @author Tobi Knaup
 */
public class Place {

  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return String.format("Place{name='%s'}", name);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Place place = (Place) o;

    if (name != null ? !name.equals(place.name) : place.name != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return name != null ? name.hashCode() : 0;
  }
}
