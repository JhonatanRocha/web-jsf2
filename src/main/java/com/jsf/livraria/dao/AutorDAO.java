package com.jsf.livraria.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.jsf.livraria.model.Autor;

public class AutorDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
    EntityManager em;
	
	private DAO<Autor> dao;

	@PostConstruct
    void init() {
        this.dao = new DAO<Autor>(em, Autor.class);
    }
	
    public Autor buscaPorId(Integer autorId) {
        return this.dao.buscaPorId(autorId);
    }

    public void adiciona(Autor autor) {
        this.dao.adiciona(autor);
    }

    public void atualiza(Autor autor) {
        this.dao.atualiza(autor);
    }

    public void remove(Autor autor) {
        this.dao.remove(autor);
    }

    public List<Autor> listaTodos() {
        return this.dao.listaTodos();
    }
}
