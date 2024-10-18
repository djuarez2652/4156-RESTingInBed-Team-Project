package com.restinginbed.TeamProject;

import java.io.Serial;
import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Represents an organization entity with an ID, name, and location.
 */
@Entity
public class Organization implements Serializable {

  @Serial
  private static final long serialVersionUID = 234567L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "organization_id", nullable = false)
  private int id;

  @Column(name = "organization_name", nullable = false)
  private String name;

  @Column(name = "organization_location", nullable = false)
  private String location;

  /**
   * Default constructor required by JPA.
   */
  public Organization() {
  }

  /**
   * Constructs an Organization with the specified name and location.
   *
   * @param name     the name of the organization
   * @param location the location of the organization
   */
  public Organization(String name, String location) {
    this.name = name;
    this.location = location;
  }

  /**
   * Constructs an Organization with the specified name, defaulting location to an empty string.
   *
   * @param name the name of the organization
   */
  public Organization(String name) {
    this.name = name;
    this.location = "";
  }

  /**
   * Gets the unique identifier of the organization.
   *
   * @return the organization's ID
   */
  public int getId() {
    return id;
  }

  /**
   * Gets the name of the organization.
   *
   * @return the organization's name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the organization.
   *
   * @param name the new name of the organization
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the location of the organization.
   *
   * @return the organization's location
   */
  public String getLocation() {
    return location;
  }

  /**
   * Sets the location of the organization.
   *
   * @param location the new location of the organization
   */
  public void setLocation(String location) {
    this.location = location;
  }
}