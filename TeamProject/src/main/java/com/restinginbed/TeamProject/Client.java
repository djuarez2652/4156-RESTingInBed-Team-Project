package com.restinginbed.TeamProject;

import java.io.Serial;
import java.io.Serializable;

import jakarta.persistence.*;

/**
 * Represents a User that is browsing the website
 */
@Entity
@Table(name = "client")
public class Client implements Serializable {

  @Serial
  private static final long serialVersionUID = 123456L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int client_id;

  @Column(name = "client_name")
  private String client_name;

  @Column(name = "client_location")
  private String client_location;

  /**
   * Constructs a Client using the parameters
   * Initial location set to blank string
   *
   * @param name   name associated with Client
   */
  public Client(String name) {
    this.client_name = name;
  }

  /**
   * Constructs a Client using the parameters
   *
   * @param name          name associated with Client
   * @param location      location associated with Client
   */
  public Client(String name, String location) {
    this.client_name = name;
    this.client_location = location;
  }

  // no-argument constructor for JPA
  public Client() {
    this.client_location = "";
  }

  public String getName() {
    return this.client_name;
  }

  public void setName(String name) {
    this.client_name = name;
  }

  public String getLocation() { return this.client_location; }

  public void setLocation(String location) {
    this.client_location = location;
  }

  public double getLongitude() {
    if (this.client_location != null && !this.client_location.isEmpty()) {
      String[] coordinates = this.client_location.split(",\\s*"); // Split by comma and optional space
      if (coordinates.length == 2) {
        try {
          return Double.parseDouble(coordinates[1].trim()); // Longitude is the second part
        } catch (NumberFormatException e) {
          throw new IllegalArgumentException("Invalid longitude value in client_location");
        }
      }
    }
    throw new IllegalArgumentException("Invalid client_location format");
  }

  public double getLatitude() {
    if (this.client_location != null && !this.client_location.isEmpty()) {
      String[] coordinates = this.client_location.split(",\\s*"); // Split by comma and optional space
      if (coordinates.length == 2) {
        try {
          return Double.parseDouble(coordinates[0].trim()); // Latitude is the first part
        } catch (NumberFormatException e) {
          throw new IllegalArgumentException("Invalid latitude value in client_location");
        }
      }
    }
    throw new IllegalArgumentException("Invalid client_location format");
  }

  public void setLongitude(double longitude) {
    // Update client_location string
    if (this.client_location != null && !this.client_location.isEmpty()) {
      String[] coordinates = this.client_location.split(",\\s*");
      if (coordinates.length == 2) {
        this.client_location = coordinates[0] + ", " + longitude;
      } else {
        throw new IllegalArgumentException("Invalid client_location format");
      }
    } else {
      // If client_location is empty, initialize with default latitude
      this.client_location = "0.0, " + longitude;
    }
  }

  public void setLatitude(double latitude) {
    // Update client_location string
    if (this.client_location != null && !this.client_location.isEmpty()) {
      String[] coordinates = this.client_location.split(",\\s*");
      if (coordinates.length == 2) {
        this.client_location = latitude + ", " + coordinates[1];
      } else {
        throw new IllegalArgumentException("Invalid client_location format");
      }
    } else {
      // If client_location is empty, initialize with default longitude
      this.client_location = latitude + ", 0.0";
    }
  }
}
