package com.group.libraryapp.domain.fruit;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FruitRepository extends JpaRepository<Fruit, Long> {
	List<Fruit> findAllByNameAndStatus(String name, boolean status);

	long countByName(String name);

	List<Fruit> findAllByPriceGreaterThanEqualAndStatus(long price, boolean status);
	List<Fruit> findAllByPriceLessThanEqualAndStatus(long price, boolean status);
}
