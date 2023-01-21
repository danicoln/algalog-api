package com.algaworks.algalog.api.controller;

import java.util.List;

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

import com.algaworks.algalog.api.dto.ClienteDto;
import com.algaworks.algalog.api.dto.input.ClienteInput;
import com.algaworks.algalog.api.mapper.ClienteMapper;
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
	private ClienteMapper clienteMapper;

	@GetMapping
	public List<ClienteDto> listar() {
		return clienteMapper.toCollectionDto(repository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<ClienteDto> buscar(@PathVariable Long id) {
		return repository.findById(id).map(cliente -> ResponseEntity.ok(clienteMapper.toDto(cliente)))
				.orElse(ResponseEntity.notFound().build());

	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public ClienteDto adicionar(@Valid @RequestBody ClienteInput input) {
		return clienteMapper.adicionar(input);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ClienteDto> atualizar(@Valid @PathVariable Long id, @RequestBody ClienteInput input) {
		if (!repository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		/*
		 * para que o método não crie um novo cliente, é necessário atribuir o id ao
		 * objeto cliente, para forçar uma atualização
		 */
		input.setId(id);

		return ResponseEntity.ok(clienteMapper.atualizar(id, input));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		if (!repository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}

		catalogoClienteService.excluir(id);

		/*
		 * noContent é o codigo 204 para quando for sucesso e n existir um corpo na
		 * resposta
		 */
		return ResponseEntity.noContent().build();
	}

}
