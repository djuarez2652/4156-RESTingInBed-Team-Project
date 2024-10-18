package com.restinginbed.TeamProject;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import com.restinginbed.TeamProject.jpa_repositories.ItemRepository;
import com.restinginbed.TeamProject.jpa_repositories.OrganizationRepository;
import com.restinginbed.TeamProject.jpa_repositories.ClientRepository;

@RestController
public class RouteController {

  /**
   * rn assuming a postgreSQL db, correct db configuration, 
   * Spring Data JPA to interact with db via repositories,
   * and entities to map classes to db tables.
   */

  @Autowired
  private OrganizationRepository organizationRepository;

  @Autowired
  private ClientRepository clientRepository;

  @Autowired
  private ItemRepository itemRepository;

  /**
   * Redirects to the homepage.
   *
   * @return A String containing the name of the html file to be loaded.
   */
  @GetMapping({"/", "/index", "/home"})
  public String index() {
    return "Welcome, in order to make an API call direct your browser or Postman to an endpoint "
        + "\n\n This can be done using the following format: \n\n http:127.0.0"
        + ".1:8080/endpoint?arg=value";
  }

    
  /**
   * Create a new user in the database.
   */
  @PostMapping(value = "/createUser", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> createUser(@RequestBody Client client) {
    try {
      Client savedClient = clientRepository.save(client);
      return new ResponseEntity<>(savedClient, HttpStatus.CREATED);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Create a new item in the database.
   */
  @PostMapping(value = "/createItem", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> createItem(@RequestBody Item item) {
    try {
      Item savedItem = itemRepository.save(item);
      return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Create a new organization in the database.
   *
   * @param organization the organization to create
   * @return ResponseEntity containing the created organization and HTTP status
   */
  @PostMapping(value = "/createOrganization", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> createOrganization(@RequestBody Organization organization) {
    try {
      Organization savedOrganization = organizationRepository.save(organization);
      return new ResponseEntity<>(savedOrganization, HttpStatus.CREATED);
    } catch (Exception e) {
      // Handle the exception appropriately
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

//  /**
//   * Create a new organization in the database using raw SQL.
//   *
//   * @param organization the organization to create
//   * @return ResponseEntity containing HTTP status
//   */
//  @PostMapping(value = "/createOrganizationNative", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//  public ResponseEntity<?> createOrganizationNative(@RequestBody Organization organization) {
//    try {
//      // Call the native query method to insert with the given ID
//      organizationRepository.insertOrganization(organization.getId(), organization.getName(), organization.getLocation());
//      return new ResponseEntity<>(HttpStatus.CREATED);
//    } catch (Exception e) {
//      // Log the error and return an error response
//      e.printStackTrace();
//      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//  }

  /**
   * Returns the details of the specified user.
   *
   * @param userID A {@code Integer} representing the user.
   *
   * @return A {@code ResponseEntity} object containing either the details of the User and
   *         an HTTP 200 response or, an appropriate message indicating the proper response.
   */
  @GetMapping(value = "/retrieveUser", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> retrieveUser(@RequestParam(value = "userID") Integer userID) {
    try {
      Optional<Client> client = clientRepository.findById(userID); // Use userID directly
      return client.map(value -> new ResponseEntity<Object>(value, HttpStatus.OK))
        .orElse(new ResponseEntity<Object>("User not found", HttpStatus.NOT_FOUND));
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Returns the details of the specified item.
   *
   * @param itemID A {@code Integer} representing the item.
   *
   * @return A {@code ResponseEntity} object containing either the details of the Item and
   *         an HTTP 200 response or, an appropriate message indicating the proper response.
   */
  @GetMapping(value = "/retrieveItem", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> retrieveItem(@RequestParam(value = "itemID") Integer itemID) {
    try {
      Optional<Item> item = itemRepository.findById(itemID);
      return item.map(value -> new ResponseEntity<Object>(value, HttpStatus.OK))
        .orElse(new ResponseEntity<Object>("Item not found", HttpStatus.NOT_FOUND));
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Returns the details of the specified organization.
   *
   * @param organizationID A {@code Long} representing the organization.
   *
   * @return A {@code ResponseEntity} object containing either the details of the Organization and
   *         an HTTP 200 response or, an appropriate message indicating the proper response.
   */
  @GetMapping(value = "/retrieveOrganization", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> retrieveOrganization(@RequestParam(value = "organizationID") Integer organizationID) {
    try {
      Optional<Organization> organization = organizationRepository.findById(organizationID);
      return organization.map(value -> new ResponseEntity<Object>(value, HttpStatus.OK))
        .orElse(new ResponseEntity<Object>("Organization not found", HttpStatus.NOT_FOUND));
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Updates an existing user in the database.
   *
   * @param client_id A {@code Long} representing the user ID.
   * @param client A {@code User} object containing updated user details.
   * @return A {@code ResponseEntity} object containing the updated User or an error message.
   */
  @PatchMapping(value = "/updateUser/{client_id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> updateUser(@PathVariable Long client_id, @RequestBody Client client) {
    try {
      if (!clientRepository.existsById(client_id.intValue())) {
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
      }

      Client existingClient = clientRepository.findById(client_id.intValue())
              .orElseThrow(() -> new IllegalArgumentException("User not found"));

      existingClient.setName(client.getName());
      existingClient.setLocation(client.getLocation());

      Client updatedClient = clientRepository.save(existingClient);

      return new ResponseEntity<>(updatedClient, HttpStatus.OK);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Deletes a user from the database.
   *
   * @param client_id A {@code Long} representing the user ID.
   * @return A {@code ResponseEntity} indicating the result of the deletion.
   */
  @DeleteMapping(value = "/deleteUser/{client_id}")
  public ResponseEntity<?> deleteUser(@PathVariable Long client_id) {
    try {
      if (!clientRepository.existsById(client_id.intValue())) {
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
      }
      clientRepository.deleteById(client_id.intValue());
      return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Updates an existing item in the database.
   *
   * @param itemID A {@code Integer} representing the item ID.
   * @param item A {@code Item} object containing updated item details.
   * @return A {@code ResponseEntity} object containing the updated Item or an error message.
   */
  @PatchMapping(value = "/updateItem/{itemID}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> updateItem(@PathVariable Integer itemID, @RequestBody Item item) {
    try {
      if (!itemRepository.existsById(itemID)) {
        return new ResponseEntity<>("Item not found", HttpStatus.NOT_FOUND);
      }
      item.setId(itemID);
      Item updatedItem = itemRepository.save(item);
      return new ResponseEntity<>(updatedItem, HttpStatus.OK);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Deletes an item from the database.
   *
   * @param itemID A {@code Integer} representing the item ID.
   * @return A {@code ResponseEntity} indicating the result of the deletion.
   */
  @DeleteMapping(value = "/deleteItem/{itemID}")
  public ResponseEntity<?> deleteItem(@PathVariable Integer itemID) {
    try {
      if (!itemRepository.existsById(itemID)) {
        return new ResponseEntity<>("Item not found", HttpStatus.NOT_FOUND);
      }
      itemRepository.deleteById(itemID);
      return new ResponseEntity<>("Item deleted successfully", HttpStatus.OK);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Updates an existing organization in the database.
   *
   * @param organizationID A {@code Long} representing the organization ID.
   * @param organization A {@code Organization} object containing updated organization details.
   * @return A {@code ResponseEntity} object containing the updated Organization or an error message.
   */
  @PatchMapping(value = "/updateOrganization/{organizationID}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> updateOrganization(@PathVariable Integer organizationID, @RequestBody Organization organization) {
    try {
      if (!organizationRepository.existsById(organizationID)) {
        return new ResponseEntity<>("Organization not found", HttpStatus.NOT_FOUND);
      }
      Organization updatedOrganization = organizationRepository.save(organization);
      return new ResponseEntity<>(updatedOrganization, HttpStatus.OK);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Deletes an organization from the database.
   *
   * @param organizationID A {@code Long} representing the organization ID.
   * @return A {@code ResponseEntity} indicating the result of the deletion.
   */
  @DeleteMapping(value = "/deleteOrganization/{organizationID}")
  public ResponseEntity<?> deleteOrganization(@PathVariable Integer organizationID) {
    try {
      if (!organizationRepository.existsById(organizationID)) {
        return new ResponseEntity<>("Organization not found", HttpStatus.NOT_FOUND);
      }
      organizationRepository.deleteById(organizationID);
      return new ResponseEntity<>("Organization deleted successfully", HttpStatus.OK);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getAllUsers() {
    try {
      List<Client> users = clientRepository.findAll();
      return new ResponseEntity<>(users, HttpStatus.OK);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  @GetMapping(value = "/organizations", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getAllOrganizations() {
    try {
      List<Organization> organizations = organizationRepository.findAll();
      return new ResponseEntity<>(organizations, HttpStatus.OK);
    } catch (Exception e) {
      return handleException(e);
    }
  }

//  @GetMapping(value = "/organizations/{organizationID}/items", produces = MediaType.APPLICATION_JSON_VALUE)
//  public ResponseEntity<?> getItemsByOrganization(@PathVariable Long organizationID) {
//    try {
//      Optional<Organization> organization = organizationRepository.findById(organizationID);
//      if (organization.isPresent()) {
//        return new ResponseEntity<>(organization.get().getItems(), HttpStatus.OK);
//      } else {
//        return new ResponseEntity<>("Organization not found", HttpStatus.NOT_FOUND);
//      }
//    } catch (Exception e) {
//      return handleException(e);
//    }
//  }

  // @GetMapping(value = "/searchItems", produces = MediaType.APPLICATION_JSON_VALUE)
  // public ResponseEntity<?> searchItems(@RequestParam String name) {
  //   try {
  //     List<Item> items = itemRepository.findByNameContaining(name);
  //     return new ResponseEntity<>(items, HttpStatus.OK);
  //   } catch (Exception e) {
  //     return handleException(e);
  //   }
  // }

  private ResponseEntity<?> handleException(Exception e) {
    System.out.println(e.toString());
    return new ResponseEntity<>("An error has occurred", HttpStatus.INTERNAL_SERVER_ERROR);
  }
  
}
