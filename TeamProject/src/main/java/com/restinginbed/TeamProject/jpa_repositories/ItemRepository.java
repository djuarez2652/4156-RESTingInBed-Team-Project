package com.restinginbed.TeamProject.jpa_repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.restinginbed.TeamProject.Item;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {
	List<Item> findByNameContaining(String name);
}
