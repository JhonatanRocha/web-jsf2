package com.jsf.livraria.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.jsf.livraria.dao.AutorDAO;
import com.jsf.livraria.model.Autor;
import com.jsf.livraria.tx.Transacional;

@Named
@ViewScoped
public class AutorBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Autor autor = new Autor();
	private Integer autorId;

	@Inject
	AutorDAO dao;
	
	public Autor getAutor() {
		return autor;
	}
	
	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}
	
	public void carregaAutorId() {
		this.autor = this.dao.buscaPorId(autorId);
	}

	@Transacional
	public void remove(Autor autor){
		System.out.println("Removendo autor " + autor.getNome());
		this.dao.remove(autor);
	}
	
	public void edit(Autor autor){
		System.out.println("Alterando autor " + autor.getNome());
		this.autor = autor;
	}
	
	@Transacional
	public String gravar() {
		System.out.println("Gravando autor " + this.autor.getNome());

		if(this.autor.getId() == null)
			this.dao.adiciona(this.autor);
		else
			this.dao.atualiza(this.autor);
		this.autor = new Autor();
		return "livro?faces-redirect=true";
	}
	
	public List<Autor> getAutores() {
		return this.dao.listaTodos();
	}
}
