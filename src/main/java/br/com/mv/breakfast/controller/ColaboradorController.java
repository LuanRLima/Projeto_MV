package br.com.mv.breakfast.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.mv.breakfast.domain.Colaborador;
import br.com.mv.breakfast.service.ColaboradorService;
import br.com.mv.breakfast.service.ItemService;

@RestController
@RequestMapping("/colaborador")
public class ColaboradorController {

	@Autowired
	private ColaboradorService service;
	
	@Autowired
	private ItemService itemService;
	
	@GetMapping(value ="/find-all")
	public ResponseEntity<?> findAll() {
		try {
			return new ResponseEntity(service.findAll(), HttpStatus.OK);
		}catch(NullPointerException e) {
			return new ResponseEntity<String>("Error: " + e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
	}
	
	@GetMapping(value = "/find/{id}")
	public ResponseEntity<?> findById(@PathVariable long id) {
		return service.findById(id).map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping(value = "/save")
	public ResponseEntity<?> save(@RequestBody Colaborador colaborador) {
		try {
			return new ResponseEntity(service.save(colaborador), HttpStatus.OK);
		}catch (NullPointerException e) {
			return new ResponseEntity<String>("Error: " + e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> delete(@PathVariable long id) {
		try {
			return new ResponseEntity<String>(this.service.deleteById(id), HttpStatus.OK);
		} catch (NullPointerException e) {
			return new ResponseEntity<String>("Error: " + e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<String>("Error: " + e.getMessage(), HttpStatus.LOCKED);
		}
	}
	
	@PostMapping(value = "/update/{id}")
	@Transactional
	public ResponseEntity<?> update(@PathVariable long id, @Validated @RequestBody Colaborador colaborador) {
		var response = ResponseEntity.ok().body(this.service.update(id, colaborador));
		this.itemService.deleteByColaboradorIdIsNull();
		
		return response;
	}	
}