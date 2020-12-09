package br.com.frontaOnline.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import br.com.frontaOnline.filter.VeiculoFilter;
import br.com.frontaOnline.model.Veiculo;
import br.com.frontaOnline.repository.VeiculoRepository;

public class VeiculoService {

	@Autowired
	private VeiculoRepository veiculoRepository;

	public Page<Veiculo> FiltrarPaginacao(VeiculoFilter veiculoFilter, Pageable pageable) {
		return veiculoRepository.filtrar(veiculoFilter, pageable);

	}

	public List<Veiculo> buscaPorMarca(String modelo) {
		List<Veiculo> veiculos = null;
		try {
			veiculos = veiculoRepository.findAllByModelo(modelo.toLowerCase());
		} catch (IllegalArgumentException i) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Modelo não existe na base de dados" + i.getMessage());
		}

		if (veiculos.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT,"Modelo escolhido não esta vinculado ao veiculo salvos!");
		}

		return veiculos;
	}

	public Veiculo BuscarPorPlaca(String placa) {
		List<Veiculo> veiculos = veiculoRepository.findByPlaca(placa);
		if (veiculos.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, "A placa não esta vinculado aos veiculos salvos!");

		}
		return veiculos.get(0);

	}

	public Veiculo BuscarPorID(Long id) {
		Optional<Veiculo> optVei = veiculoRepository.findById(id);
		if (optVei.isPresent()) {
			return optVei.get();
		} else {
			throw new IllegalArgumentException();
		}

	}

	public void deletar(Long id) {
		veiculoRepository.deleteById(id);
	}

	public JpaRepository<Veiculo, Long> getRepository() {
		return this.veiculoRepository;

	}

}
