package com.algaworks.algalog.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.algalog.api.dto.ClienteDto;
import com.algaworks.algalog.api.dto.input.ClienteInput;
import com.algaworks.algalog.domain.model.Cliente;
import com.algaworks.algalog.domain.repository.ClienteRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class ClienteMapper {

	private ClienteRepository clienteRepository;
	private ModelMapper modelMapper;

	public ClienteDto toDto(Cliente cliente) {
		return modelMapper.map(cliente, ClienteDto.class);
	}

	public List<ClienteDto> toCollectionDto(List<Cliente> clientes) {
		return clientes.stream().map(this::toDto).collect(Collectors.toList());
	}

	public Cliente toEntity(ClienteInput input) {
		return modelMapper.map(input, Cliente.class);
	}

	public ClienteDto adicionar(ClienteInput input) {
		Cliente cliente = modelMapper.map(input, Cliente.class);
		clienteRepository.save(cliente);
		return modelMapper.map(cliente, ClienteDto.class);
	}

	public ClienteDto atualizar(Long id, ClienteInput input) {
		Cliente cliente = modelMapper.map(input, Cliente.class);
		clienteRepository.save(cliente);
		return modelMapper.map(cliente, ClienteDto.class);
//		cliente.setId(id);

	}
}
