package com.algaworks.algalog.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.algalog.api.dto.OcorrenciaDto;
import com.algaworks.algalog.domain.model.Ocorrencia;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class OcorrenciaMapper {

	private ModelMapper modelMapper;
	
	public OcorrenciaDto toDto(Ocorrencia ocorrencia) {
		return modelMapper.map(ocorrencia, OcorrenciaDto.class);
	}
	
	public List<OcorrenciaDto> toCollectionDto(List<Ocorrencia> ocorrencias){
		return ocorrencias.stream()
				.map(this::toDto)
				.collect(Collectors.toList());
	}
	
}
