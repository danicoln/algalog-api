package com.algaworks.algalog.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algalog.api.dto.EntregaDto;
import com.algaworks.algalog.api.dto.input.EntregaInput;
import com.algaworks.algalog.api.mapper.EntregaMapper;
import com.algaworks.algalog.domain.repository.EntregaRepository;
import com.algaworks.algalog.domain.service.FinalizacaoEntregaService;
import com.algaworks.algalog.domain.service.SolicitacaoEntregaService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/entregas")
public class EntregaController {

	private EntregaRepository entregaRepository;
	private SolicitacaoEntregaService solicitacaoEntregaService;
	private EntregaMapper entregaMapper;
	private FinalizacaoEntregaService finalizacaoEntregaService;

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public EntregaDto solicitar(@Valid @RequestBody EntregaInput input) {
//		Entrega novaEntrega = entregaMapper.toEntity(input);	
		return entregaMapper.toDto(solicitacaoEntregaService.solicitar(entregaMapper.toEntity(input)));
	}

	@GetMapping
	public List<EntregaDto> listar() {
		return entregaMapper.toCollectionDto(entregaRepository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<EntregaDto> buscar(@PathVariable Long id) {
		return entregaRepository.findById(id).map(entrega -> ResponseEntity.ok(entregaMapper.toDto(entrega)))
				.orElse(ResponseEntity.notFound().build());

	}

	@PutMapping("/{entregaId}/finalizacao")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void finalizar(@PathVariable Long entregaId) {
		finalizacaoEntregaService.finalizar(entregaId);
	}

}
