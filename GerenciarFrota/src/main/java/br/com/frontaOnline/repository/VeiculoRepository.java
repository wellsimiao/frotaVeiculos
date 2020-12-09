package br.com.frontaOnline.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.frontaOnline.filter.VeiculoFilter;
import br.com.frontaOnline.model.Veiculo;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long>{

	List<Veiculo> findByPlaca(String placa);
	List<Veiculo> findAllByModelo(String modelo);
	List<Veiculo> findAllByMarca(String marca);
	Page<Veiculo> filtrar(VeiculoFilter veiculoFilter, Pageable pageable);
}
