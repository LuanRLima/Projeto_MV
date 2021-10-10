package br.com.mv.breakfast.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.mv.breakfast.domain.Colaborador;
import br.com.mv.breakfast.domain.Item;
import br.com.mv.breakfast.service.ColaboradorService;
import br.com.mv.breakfast.service.ItemService;

@RestController
@RequestMapping("/colaborador")
public class ColaboradorController {

	@Autowired
	private ColaboradorService service;
	@Autowired
	private ItemService itemService;
	
	@RequestMapping(path ="/find-all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
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
	public ResponseEntity<?> update(@PathVariable long id, @Validated @RequestBody Colaborador colaborador) {
		return service.findById(id).map(record -> {
			record.setCpf(colaborador.getCpf());
			record.setNome(colaborador.getNome());
			record.setListaItem(colaborador.getListaItem());

			Colaborador update = service.save(record);
			
			if(record.getListaItem() != null)
				record.getListaItem().forEach(item -> {
					item.setColaborador(update);
					this.itemService.save(item);
				});
			
			return ResponseEntity.ok().body(update);
		}).orElse(ResponseEntity.notFound().build());
		
	}
	
}
