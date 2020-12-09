package br.com.frontaOnline.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.frontaOnline.model.Veiculo;
import br.com.frontaOnline.service.VeiculoService;
import io.swagger.annotations.Api;

@RestController
@RequestMapping(value="/veiculos")
@Api(value="API REST Veiculos")
@CrossOrigin(origins = "*")
public class VeiculoController{

	@Autowired
	private VeiculoService veiculoService;

	@GetMapping("/placa/{placa}")
	public Veiculo BuscarPorPlaca(@PathVariable String placa) {

		return this.veiculoService.BuscarPorPlaca(placa);

	}

	@GetMapping("/marca/{marca}")
	public List<Veiculo> buscaPorMarca(@PathVariable String marca) {

		return this.veiculoService.buscaPorMarca(marca);
	}
		
}
