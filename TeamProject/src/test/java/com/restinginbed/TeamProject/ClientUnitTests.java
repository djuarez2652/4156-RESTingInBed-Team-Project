package com.restinginbed.TeamProject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Unit test cases for Client class.
 */
@SpringBootTest
@ContextConfiguration
public class ClientUnitTests {
  /** The test Client instance used for testing. */
  public static Client testClient;

  @BeforeAll
  public static void setupCourseForTesting() {
    testClient = new Client();
  }

  @Test
  public void ClientClassTest() {
    int id = 1;
    String name = "Test Client";
    String location = "1.1, 1.2";

    Client testClient = new Client (name, location);

    assertEquals(testClient.getLocation(), location);
    assertEquals(testClient.getLongitude(), 1.2);
    assertEquals(testClient.getLatitude(), 1.1);
    assertEquals(testClient.getName(), name);
  }

}