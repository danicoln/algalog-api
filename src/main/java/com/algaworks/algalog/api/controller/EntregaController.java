package com.algaworks.algalog.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algalog.api.dto.DestinatarioDto;
import com.algaworks.algalog.api.dto.EntregaDto;
import com.algaworks.algalog.domain.model.Entrega;
import com.algaworks.algalog.domain.repository.EntregaRepository;
import com.algaworks.algalog.domain.service.SolicitacaoEntregaService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/entregas")
public class EntregaController {

	private EntregaRepository entregaRepository;
	private SolicitacaoEntregaService solicitacaoEntregaService;
	private ModelMapper modelMapper;
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Entrega solicitar(@Valid @RequestBody Entrega entrega) {
		return solicitacaoEntregaService.solicitar(entrega);
	}
	
	@GetMapping
	public List<Entrega> listar(){
		return entregaRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<EntregaDto> buscar(@PathVariable Long id){
		return entregaRepository.findById(id)
				.map(entrega -> {
					EntregaDto entregaDto = new EntregaDto();
					entregaDto.setId(entrega.getId());
					entregaDto.setNomeCliente(entrega.getCliente().getNome());
					
					entregaDto.setDestinatario(new DestinatarioDto());
					entregaDto.getDestinatario().setNome(entrega.getDestinatario().getNome());
					entregaDto.getDestinatario().setLogradouro(entrega.getDestinatario().getLogradouro());
					entregaDto.getDestinatario().setNumero(entrega.getDestinatario().getNumero());
					entregaDto.getDestinatario().setComplemento(entrega.getDestinatario().getComplemento());
					entregaDto.getDestinatario().setBairro(entrega.getDestinatario().getBairro());
					
					entregaDto.setTaxa(entrega.getTaxa());
					entregaDto.setStatus(entrega.getStatus());
					entregaDto.setDataPedido(entrega.getDataPedido());
					entregaDto.setDataFinalizacao(entrega.getDataFinalizacao());
					
					return ResponseEntity.ok(entregaDto);
				}).orElse(ResponseEntity.notFound().build());
				
	}
	
}
