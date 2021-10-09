package br.com.mv.breakfast.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.mv.breakfast.domain.Colaborador;
import br.com.mv.breakfast.repository.ColaboradorRepository;

@Service
public class ColaboradorService {
	@Autowired
	private  ColaboradorRepository repository;
	
	public List<Colaborador> findAll() {
		return repository.findAll();
	}
}
