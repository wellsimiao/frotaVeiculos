package br.com.frontaOnline.controller;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Optional;

import javax.persistence.Id;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import br.com.frontaOnline.service.RestService;

public abstract class RestController<ID, T> {

	@GetMapping
	public Page<T> listarTodos(Integer pageNumber, Integer pageSize) {
		PageRequest pg = PageRequest.of(pageNumber, pageSize);
		return this.getService().getRepository().findAll(pg);

	}

	@GetMapping("/{id}")
	public T buscarPorcodigo(@PathVariable ID id) {

		Optional<T> t;
		try {
			t = this.getService().getRepository().findById(id);
		} catch (IllegalArgumentException i) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Codigo não pode ser nulo!");

		}
		return t.get();
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<T> salvar(@Valid @RequestBody T t, HttpServletResponse response) {
		T t2 = this.getService().getRepository().save(t);

		return ResponseEntity.status(HttpStatus.CREATED).body(t2);
	}

	@PutMapping
	public ResponseEntity<T> atualizar(@Valid @RequestBody T t, HttpServletResponse response) {
		T t2 = this.getService().getRepository().save(t);
		return ResponseEntity.status(HttpStatus.CREATED).body(t2);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable ID id) {
		this.getService().getRepository().deleteById(id);
	}

	/**
	 * Retorna um Objeto ID, que sera retirado de um objeto generico passado por
	 * parametro na assinatura do metodo, dessa forma é possivel passar qualquer
	 * model e retornar seu Id gerado apos salvar essa entidade no banco de dados. É
	 * necessario que se faça um casting para {@link Long} nesse contexto.
	 * 
	 * @param obj - objeto generico
	 * @return Object - id referente ao objeto passado por parametro
	 * @author silvio
	 * @exception IllegalArgumentException - Caso seja passado um objeto Nulo
	 */
	@SuppressWarnings("unused")
	private Object getRowKey(Object obj) {
		Object id = null;
		Class<?> type = obj.getClass();

		for (Field f : type.getDeclaredFields()) {
			if (f.isAnnotationPresent((Class<? extends Annotation>) Id.class)) {
				String name = f.getName();
				String method = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
				try {
					Method m = type.getDeclaredMethod(method);
					id = (Object) m.invoke(obj);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return id;
	}

	public abstract RestService<T, ID> getService();
}
