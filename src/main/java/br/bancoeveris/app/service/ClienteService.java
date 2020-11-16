package br.bancoeveris.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.bancoeveris.app.model.Cliente;
import br.bancoeveris.app.repository.ClienteRepository;
import br.bancoeveris.app.request.ClienteList;
import br.bancoeveris.app.request.ClienteRequest;
import br.bancoeveris.app.response.BaseResponse;


@Service
public class ClienteService {

	final ClienteRepository _repository;

	@Autowired
	public ClienteService(ClienteRepository repository) {
		_repository = repository;
	}

	public BaseResponse inserir(ClienteRequest clienteRequest) {
		Cliente cliente = new Cliente();
		BaseResponse base = new BaseResponse();
		base.setStatusCode(400);

		if (clienteRequest.getNome() == "") {
			base.setMessage("O nome do cliente não foi preenchido.");
			return base;
		}

		if (clienteRequest.getTelefone() == "") {
			base.setMessage("O telefone do cliente não foi preenchido.");
			return base;
		}
		if (clienteRequest.getCpf() == "") {
			base.setMessage("O Cpf do cliente não foi preenchido.");
			return base;
		}
		if (clienteRequest.getDataNasc() == "") {
			base.setMessage("A data de nascimento do cliente não foi preenchido.");
			return base;
		}
		if (clienteRequest.getEndereco() == "") {
			base.setMessage("O endereço do cliente não foi preenchido");
			return base;
		}

		cliente.setNome(clienteRequest.getNome());
		cliente.setTel(clienteRequest.getTelefone());
		cliente.setCpf(clienteRequest.getCpf());
		cliente.setDataNasc(clienteRequest.getDataNasc());
		cliente.setEndereco(clienteRequest.getEndereco());

		_repository.save(cliente);
		base.setStatusCode(201);
		base.setMessage("Cliente inserido com sucesso.");
		return base;
	}

	public Cliente obter(Long id) {
		Optional<Cliente> response = _repository.findById(id);		

		if (response == null) {
			response.get().setMessage("Cliente não encontrado");
			response.get().setStatusCode(404);
			return response.get();
		}

		response.get().setMessage("Cliente obtido com sucesso");
		response.get().setStatusCode(200);
		return response.get();
	}

	public Cliente obterByCpf(String cpf) {
		Cliente response = _repository.findByCpf(cpf);		

		if (response == null) {
			response = new Cliente();
			response.setMessage("Cliente não encontrado");
			response.setStatusCode(404);
			return response;
		}

		response.setMessage("Cliente obtido com sucesso");
		response.setStatusCode(200);
		return response;
	}

	public ClienteList listar() {

		List<Cliente> lista = _repository.findAll();

		ClienteList response = new ClienteList();
		response.setClientes(lista);
		response.setStatusCode(200);
		response.setMessage("Clientes obtidos com sucesso.");

		return response;
	}

	public BaseResponse atualizar(Long id, ClienteRequest clienteRequest) {
		Cliente cliente = new Cliente();
		BaseResponse base = new BaseResponse();
		base.setStatusCode(400);

		if (clienteRequest.getNome() == "") {
			base.setMessage("O nome do cliente não foi preenchido.");
			return base;
		}

		if (clienteRequest.getTelefone() == "") {
			base.setMessage("O telefone do cliente não foi preenchido.");
			return base;
		}
		if (clienteRequest.getCpf() == "") {
			base.setMessage("O Cpf do cliente não foi preenchido.");
			return base;
		}
		if (clienteRequest.getDataNasc() == "") {
			base.setMessage("A data de nascimento do cliente não foi preenchido.");
			return base;
		}
		if (clienteRequest.getEndereco() == "") {
			base.setMessage("O endereço do cliente não foi preenchido");
			return base;
		}
		cliente.setId(id);
		cliente.setNome(clienteRequest.getNome());
		cliente.setTel(clienteRequest.getTelefone());
		cliente.setCpf(clienteRequest.getCpf());
		cliente.setDataNasc(clienteRequest.getDataNasc());
		cliente.setEndereco(clienteRequest.getEndereco());

		_repository.save(cliente);
		base.setStatusCode(200);
		base.setMessage("Cliente atualizado com sucesso.");
		return base;
	}

	public BaseResponse deletar(Long id) {
		BaseResponse response = new BaseResponse();

		if (id == null || id == 0) {
			response.setStatusCode(400);
			return response;
		}

		_repository.deleteById(id);
		response.setStatusCode(200);
		return response;
	}

}
