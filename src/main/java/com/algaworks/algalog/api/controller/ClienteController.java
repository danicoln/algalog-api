package com.algaworks.algalog.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algalog.domain.model.Cliente;
import com.algaworks.algalog.domain.repository.ClienteRepository;
import com.algaworks.algalog.domain.service.CatalogoClienteService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	private ClienteRepository repository;
	private CatalogoClienteService catalogoClienteService;
	
	@GetMapping
	public List<Cliente> listar() {
		return repository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Cliente> buscar(@PathVariable Long id) {
		return repository.findById(id)
//				.map(c -> ResponseEntity.ok(c))
				.map(ResponseEntity::ok) //esta linha substitui a anterior
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Cliente adicionar(@Valid @RequestBody Cliente cliente) {
		return catalogoClienteService.salvar(cliente);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Cliente> atualizar(@Valid @PathVariable Long id, @RequestBody Cliente cliente){
		if(!repository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		/* para que o método não crie um novo cliente, é necessário atribuir o id ao
		 * objeto cliente, para forçar uma atualização */
		cliente.setId(id); 
		
		catalogoClienteService.salvar(cliente);
		return ResponseEntity.ok(cliente);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id){
		if(!repository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		catalogoClienteService.excluir(id);
		
		/*noContent é o codigo 204 para quando for sucesso e n existir um corpo na resposta*/
		return ResponseEntity.noContent().build();
	}
	
	
	
	
	
	
	
	
	
	
}
