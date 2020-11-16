package br.bancoeveris.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.bancoeveris.app.model.Conta;
import br.bancoeveris.app.model.Operacao;
import br.bancoeveris.app.repository.ContaRepository;
import br.bancoeveris.app.repository.OperacaoRepository;
import br.bancoeveris.app.request.OperacaoRequest;
import br.bancoeveris.app.request.TransferenciaRequest;
import br.bancoeveris.app.response.BaseResponse;

@Service
public class OperacaoService {

	final OperacaoRepository _repository;
	final ContaRepository _contaRepository;

	public OperacaoService(OperacaoRepository repository, ContaRepository contaRepository) {
		_repository = repository;
		_contaRepository = contaRepository;
	}

	public BaseResponse inserir(OperacaoRequest operacaoRequest) {
		Operacao operacao = new Operacao();
		BaseResponse base = new BaseResponse();
		base.setStatusCode(400);
		Conta conta = _contaRepository.findByHash(operacaoRequest.getHash());

		if (conta == null) {
			base.setStatusCode(404);
			base.setMessage("Conta não encontrada!");
			return base;
		}

		if (operacaoRequest.getTipo() == "") {
			base.setMessage("O Tipo da operação não foi preenchido.");
			return base;
		}

		if (operacaoRequest.getValor() == 0) {
			base.setMessage("O valor da operação não foi preenchido.");
			return base;
		}

		operacao.setTipo(operacaoRequest.getTipo());
		operacao.setValor(operacaoRequest.getValor());

		switch (operacaoRequest.getTipo()) {

		case "D":
			operacao.setContaDestino(conta);
			break;

		case "S":
			operacao.setContaOrigem(conta);
			break;
		}

		_repository.save(operacao);
		base.setStatusCode(201);
		base.setMessage("Operacão inserida com sucesso.");
		return base;
	}

	public double saldo(Long contaId) {

		double saldo = 0;
		/*
		 * Conta contaOrigem = new Conta(); contaOrigem.setId(contaId);
		 * 
		 * Conta contaDestino = new Conta(); contaDestino.setId(contaId);
		 */

		List<Operacao> lista = _repository.findOperacoesPorConta(contaId);

		for (Operacao o : lista) {
			switch (o.getTipo()) {
			case "D":
				saldo += o.getValor();
				break;
			case "S":
				saldo -= o.getValor();
				break;
			case "T":
				if (contaId == o.getContaOrigem().getId())
					saldo -= o.getValor();

				if (contaId == o.getContaDestino().getId())
					saldo += o.getValor();

				break;
			default:
				break;
			}

		}

		return saldo;
	}

//	public Conta obter(Long id) {		
//		Optional<Conta> conta = _repository.findById(id);
//		Conta response = new Conta();
//		
//		
//		if (conta == null) {
//			response.Message = "Conta não encontrada";
//			response.StatusCode = 404;
//			return response;
//		}						
//		
//		response.Message = "Conta obtida com sucesso";
//		response.StatusCode = 200;		
//		return response;
//	}	
//
//	public OperacaoList listar() {
//
//		List<Operacao> lista = _repository.findAll();
//
//		OperacaoList response = new OperacaoList();
//		response.setOperacoes(lista);
//		response.StatusCode = 200;
//		response.Message = "Operacoes obtidas com sucesso.";
//
//		return response;
//	}

	public BaseResponse atualizar(Long id, OperacaoRequest operacaoRequest) {
		Operacao operacao = new Operacao();
		BaseResponse base = new BaseResponse();
		base.setStatusCode(400);

		if (operacaoRequest.getTipo() == "") {
			base.setMessage("O tipo da Operação não foi preenchido.");
			return base;
		}

		if (operacaoRequest.getValor() == 0) {
			base.setMessage("O Valor da operação não foi preenchido.");
			return base;
		}

		operacao.setId(id);
		operacao.setTipo(operacaoRequest.getTipo());
		operacao.setValor(operacaoRequest.getValor());

		_repository.save(operacao);
		base.setStatusCode(200);
		base.setMessage("Operacao atualizada com sucesso.");
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

	public BaseResponse transferencia(TransferenciaRequest request) {
		BaseResponse response = new BaseResponse();
		Operacao operacao = new Operacao();
		
		if (request.getHashOrigem() == "") {
			response.setStatusCode(400);
			response.setMessage("Conta origem não preenchida.");
			return response;
		}
		
		if (request.getHashDestino() == "") {
			response.setStatusCode(400);
			response.setMessage("Conta destino não preenchida.");
			return response;
		}
		
		if (request.getValor() <= 0) {
			response.setStatusCode(400);
			response.setMessage("Valor para saque inválido.");
			return response;
		}
		
		Conta contaOrigem = _contaRepository.findByHash(request.getHashOrigem());
		
		if (contaOrigem == null) {
			response.setStatusCode(400);
			response.setMessage("Conta origem inexistente.");
			return response;
		}		
		
		Conta contaDestino = _contaRepository.findByHash(request.getHashDestino());
		
		if (contaDestino == null) {
			response.setStatusCode(400);
			response.setMessage("Conta destino inexistente.");
			return response;
		}		
		
		operacao.setTipo("T");
		operacao.setValor(request.getValor());
		operacao.setContaOrigem(contaOrigem);
		operacao.setContaDestino(contaDestino);
		_repository.save(operacao);
		
		response.setStatusCode(201);
		response.setMessage("Transferência realizada com sucesso.");
		return response;		
	}

}
