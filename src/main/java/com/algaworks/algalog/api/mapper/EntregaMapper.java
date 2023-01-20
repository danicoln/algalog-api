package com.algaworks.algalog.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.algalog.api.dto.EntregaDto;
import com.algaworks.algalog.api.dto.input.EntregaInput;
import com.algaworks.algalog.domain.model.Entrega;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class EntregaMapper {

	private ModelMapper modelMapper;
	
	public EntregaDto toDto(Entrega entrega) {
		return modelMapper.map(entrega, EntregaDto.class);
		
	}
	
	public List<EntregaDto> toCollectionDto(List<Entrega> entregas){
		return entregas.stream()
				.map(this::toDto)
				.collect(Collectors.toList());
	}
	
	public Entrega toEntity(EntregaInput input) {
		return modelMapper.map(input, Entrega.class);
	}
}
