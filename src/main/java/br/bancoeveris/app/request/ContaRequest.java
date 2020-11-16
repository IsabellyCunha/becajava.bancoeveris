package br.bancoeveris.app.request;

import java.util.UUID;

public class ContaRequest {

	private String hash;
	private String numConta;
	private String agencia;
	private ClienteRequest cliente;
	
	public String getHash() {
		hash = UUID.randomUUID().toString().substring(0, 16);
		return hash.replace("-", "");
	}
	public String setHash() {
		return hash;
	}
	public String getNumConta() {
		return numConta;
	}
	public void setNumConta(String numConta) {
		this.numConta = numConta;
	}
	public String getAgencia() {
		return agencia;
	}
	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}
	public ClienteRequest getCliente() {
		return cliente;
	}
	public void setCliente(ClienteRequest cliente) {
		this.cliente = cliente;
	}
	
	
	
}
