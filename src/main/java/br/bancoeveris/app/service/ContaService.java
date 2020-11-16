package br.bancoeveris.app.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.bancoeveris.app.model.Cliente;
import br.bancoeveris.app.model.Conta;
import br.bancoeveris.app.repository.ContaRepository;
import br.bancoeveris.app.request.ContaRequest;
import br.bancoeveris.app.response.BaseResponse;

@Service
public class ContaService {

	final ContaRepository _repository;
	final OperacaoService _operacaoService;
	final ClienteService _clienteService;

	public ContaService(ContaRepository repository, OperacaoService operacaoService, ClienteService clienteService) {
		_repository = repository;
		_operacaoService = operacaoService;
		_clienteService = clienteService;
	}

	public Conta saldo(String hash) {
		Conta conta = new Conta();
		conta.setStatusCode(400);
		
		conta = _repository.findByHash(hash);

		if (conta == null)
			conta.setMessage("Conta não encontrada!");
		
		
		double saldo =  _operacaoService.saldo(conta.getId());
		conta.setSaldo(saldo);
		conta.setHash(conta.getHash());
		conta.setAgencia(conta.getAgencia());
		conta.setNumConta(conta.getNumConta());
		conta.setCliente(conta.getCliente());
		
		conta.setStatusCode(200);
		conta.setMessage("Consulta de saldo ok!");
		return conta;
	}

	public BaseResponse inserir(ContaRequest contaRequest) {
		Conta conta = new Conta();
		BaseResponse base = new BaseResponse();
		base.setStatusCode(400);
		boolean existe = true; 
		
		while(existe == true) {
			String randomHash = conta.getHash();
			Conta contaExiste = _repository.findByHash(randomHash);
			
			if (contaExiste != null)
				existe = true;
			else
				existe = false;
		}
		
		if (contaRequest.getHash() == "") {
			base.setMessage("O Hash do cliente não foi preenchido.");
			return base;
		}
		if (contaRequest.getNumConta() == "") {
			base.setMessage("O Número da conta do cliente não foi preenchido.");
			return base;
		}
		if (contaRequest.getAgencia() == "") {
			base.setMessage("A agencia do cliente não foi preenchido.");
			return base;
		}
		

		conta.setHash(contaRequest.getHash());
		conta.setNumConta(contaRequest.getNumConta());
		conta.setAgencia(contaRequest.getAgencia());
		Cliente cliente = _clienteService.obterByCpf(contaRequest.getCliente().getCpf());

		if (cliente.getStatusCode() == 404) {
			_clienteService.inserir(contaRequest.getCliente());
			cliente = _clienteService.obterByCpf(contaRequest.getCliente().getCpf());

		}
		conta.setCliente(cliente);

		_repository.save(conta);
		base.setStatusCode(201);
		base.setMessage("Conta inserida com sucesso.");
		return base;
	}

	public Conta obter(Long id) {
		Optional<Conta> conta = _repository.findById(id);
		Conta response = new Conta();

		if (conta == null) {
			response.setMessage("Conta não encontrada");
			response.setStatusCode(404);
			return response;
		}

		response.setMessage("Conta obtida com sucesso");
		response.setStatusCode(200);
		response.setAgencia(conta.get().getAgencia());
		response.setNumConta(conta.get().getNumConta());
		response.setHash(conta.get().getHash());
		return response;
	}

//	public ContaList listar() {
//
//		List<Conta> lista = _repository.findAll();
//
//		ContaList response = new ContaList();
//		response.setContas(lista);
//		response.StatusCode = 200;
//		response.Message = "Contas obtidas com sucesso.";
//
//		return response;
//	}

	public BaseResponse atualizar(Long id, ContaRequest contaRequest) {
		Conta conta = new Conta();
		BaseResponse base = new BaseResponse();
		base.setStatusCode(400);

		if (contaRequest.getHash() == "") {
			base.setMessage("O Hash da conta não foi preenchido.");
			return base;
		}
		if (contaRequest.getNumConta() == "") {
			base.setMessage("O número da conta não foi preenchido");
			return base;
		}
		if (contaRequest.getAgencia() == "") {
			base.setMessage("A agencia da conta não foi preenchida");
			return base;
		}

		conta.setId(id);
		conta.setHash(contaRequest.getHash());
		conta.setAgencia(contaRequest.getAgencia());
		conta.setNumConta(contaRequest.getNumConta());
		_clienteService.inserir(contaRequest.getCliente());

		_repository.save(conta);
		base.setStatusCode(200);
		base.setMessage("Conta atualizada com sucesso.");
		return base;
	}

//	public BaseResponse deletar(Long id) {
//		BaseResponse response = new BaseResponse();
//
//		if (id == null || id == 0) {
//			response.StatusCode = 400;
//			return response;
//		}
//
//		_repository.deleteById(id);
//		response.StatusCode = 200;
//		return response;
//	}

}
