package br.bancoeveris.app.request;

import br.bancoeveris.app.model.BaseResponse;

import java.util.List;

import br.bancoeveris.app.model.*;

public class ClienteList extends BaseResponse {
	
	private List<Cliente> Clientes;

	public List<Cliente> getClientes() {
		return Clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		Clientes = clientes;
	}

}