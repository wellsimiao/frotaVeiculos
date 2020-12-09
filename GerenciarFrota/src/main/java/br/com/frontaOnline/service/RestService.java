package br.com.frontaOnline.service;

import org.springframework.data.jpa.repository.JpaRepository;

public abstract class RestService<E, ID> {
	
	public abstract JpaRepository<E, ID> getRepository();
}
