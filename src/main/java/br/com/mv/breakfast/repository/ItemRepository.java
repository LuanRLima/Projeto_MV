package br.com.mv.breakfast.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.mv.breakfast.domain.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
	
	void deleteByColaboradorIdIsNull();
}
