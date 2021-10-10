package br.com.mv.breakfast.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.mv.breakfast.domain.Item;
import br.com.mv.breakfast.repository.ItemRepository;

@Service
public class ItemService {
	@Autowired
	private ItemRepository repository;
	
	public void deleteByColaboradorIdIsNull() {
		this.repository.deleteByColaboradorIdIsNull();
	}
}
