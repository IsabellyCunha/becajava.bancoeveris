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
public interface OperacaoService {

	BaseResponse inserir(OperacaoRequest request);
	double saldo(Long id);
	BaseResponse atualizar(Long id, OperacaoRequest operacaoRequest);
	BaseResponse transferencia(TransferenciaRequest request);
	
}