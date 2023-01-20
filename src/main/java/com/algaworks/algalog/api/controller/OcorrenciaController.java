package com.algaworks.algalog.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algalog.api.dto.OcorrenciaDto;
import com.algaworks.algalog.api.dto.input.OcorrenciaInput;
import com.algaworks.algalog.api.mapper.OcorrenciaMapper;
import com.algaworks.algalog.domain.model.Entrega;
import com.algaworks.algalog.domain.model.Ocorrencia;
import com.algaworks.algalog.domain.service.BuscaEntregaService;
import com.algaworks.algalog.domain.service.RegistroOcorrenciaService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/entregas/{entregaId}/ocorrencias")
public class OcorrenciaController {
	
	private BuscaEntregaService buscaEntregaService;
	private RegistroOcorrenciaService ocorrenciaService;
	private OcorrenciaMapper ocorrenciaMapper;
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public OcorrenciaDto registrar(@PathVariable Long entregaId, 
			@Valid @RequestBody OcorrenciaInput input) {
		
		Ocorrencia ocorrencia = ocorrenciaService
				.registrar(entregaId, input.getDescricao());
		return ocorrenciaMapper.toDto(ocorrencia);
	}
	
	@GetMapping
	public List<OcorrenciaDto> listar(@PathVariable Long entregaId){
		Entrega entrega = buscaEntregaService.buscar(entregaId);
		
		/*O JakartaPersistence faz o select sob demanda (Carregamento preguiçoso)*/
		return ocorrenciaMapper.toCollectionDto(entrega.getOcorrencias()); 
	}

}
