package br.com.mv.breakfast.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.mv.breakfast.domain.Colaborador;
import br.com.mv.breakfast.domain.Item;
import br.com.mv.breakfast.repository.ColaboradorRepository;
import br.com.mv.breakfast.repository.ItemRepository;

@Service
public class ColaboradorService {
	@Autowired
	private  ColaboradorRepository repository;
	@Autowired
	private  ItemRepository itemRepository;
	
	public List<Colaborador> findAll() {
		return repository.findAll();
	}
	
	public Optional<Colaborador> findById(Long id) {
		return this.repository.findById(id);
	}

	
	public Colaborador save(Colaborador colaborador) {
		return repository.save(colaborador);
	}
	
	public Colaborador update(Long id, Colaborador colaborador) {
		return repository.save(colaborador);
	}
	
	public String deleteById(Long id) throws Exception {
		this.findById(id).orElseThrow(() -> new NullPointerException("Contato_inexistente"));
		this.repository.deleteById(id);
		Optional<Colaborador> contact = this.findById(id);
		if(contact.isPresent()){
			throw new Exception("Não foi possível deletar contato");
		}
		return "Contato deletado";
	}
	
	
}
