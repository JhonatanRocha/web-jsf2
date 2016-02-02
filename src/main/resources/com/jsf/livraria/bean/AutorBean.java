package com.jsf.livraria.bean;

import javax.faces.bean.ManagedBean;

import com.jsf.livraria.dao.DAO;
import com.jsf.livraria.model.Autor;

@ManagedBean
public class AutorBean {

	private Autor autor = new Autor();

	public Autor getAutor() {
		return autor;
	}

	public void gravar() {
		System.out.println("Gravando autor " + this.autor.getNome());

		new DAO<Autor>(Autor.class).adiciona(this.autor);
	}
}
