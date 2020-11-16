package br.bancoeveris.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.bancoeveris.app.model.Cliente;
import br.bancoeveris.app.request.ClienteList;
import br.bancoeveris.app.request.ClienteRequest;
import br.bancoeveris.app.response.BaseResponse;
import br.bancoeveris.app.service.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController extends BaseController {
	
	private final ClienteService _service;
	
	@Autowired
	public ClienteController(ClienteService service) {
		_service = service;
	}
	
	@PostMapping
    public ResponseEntity inserir(@RequestBody ClienteRequest clienteRequest) {
		try {
			BaseResponse response = _service.inserir(clienteRequest);
			return ResponseEntity.status(response.getStatusCode()).body(response);			
		} catch (Exception e) {
			return ResponseEntity.status(errorBase.getStatusCode()).body(errorBase);
		}
    }
	
	@GetMapping(path = "/{id}")
    public ResponseEntity obter(@PathVariable Long id) {		
		try {
			Cliente response = _service.obter(id);
			return ResponseEntity.status(response.getStatusCode()).body(response);	
		} catch (Exception e) {
			return ResponseEntity.status(errorBase.getStatusCode()).body(errorBase);
		}   	
    }

	@GetMapping
    public ResponseEntity listar() {		
		try {
			ClienteList clientes = _service.listar();  		
	    	return ResponseEntity.status(HttpStatus.OK).body(clientes);			
		} catch (Exception e) {
			return ResponseEntity.status(errorBase.getStatusCode()).body(errorBase);			
		}		
    }
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity deletar(@PathVariable Long id) {
		try {
			BaseResponse response = _service.deletar(id);
			return ResponseEntity.status(response.getStatusCode()).build(); 
		} catch (Exception e) {
			return ResponseEntity.status(errorBase.getStatusCode()).body(errorBase);
		}
	}
	
	@PutMapping(path = "/{id}")
	public ResponseEntity atualizar(@RequestBody ClienteRequest clienteRequest, @PathVariable Long id) {
		try {
			BaseResponse response = _service.atualizar(id, clienteRequest);
			return ResponseEntity.status(response.getStatusCode()).body(response);
		} catch (Exception e) {
			return ResponseEntity.status(errorBase.getStatusCode()).body(errorBase);
		}
	}

}