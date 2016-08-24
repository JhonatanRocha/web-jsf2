package com.jsf.livraria.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.jsf.livraria.dao.DAO;
import com.jsf.livraria.model.Autor;

@Named
@ViewScoped
public class AutorBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Autor autor = new Autor();
	private Integer autorId;
	
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
		this.autor = new DAO<Autor>(Autor.class).buscaPorId(autorId);
	}

	public void remove(Autor autor){
		System.out.println("Removendo autor " + autor.getNome());
		
		new DAO<Autor>(Autor.class).remove(autor);
	}
	
	public void edit(Autor autor){
		System.out.println("Alterando autor " + autor.getNome());
		this.autor = autor;
	}
	
	public String gravar() {
		System.out.println("Gravando autor " + this.autor.getNome());

		if(this.autor.getId() == null)
			new DAO<Autor>(Autor.class).adiciona(this.autor);
		else
			new DAO<Autor>(Autor.class).atualiza(this.autor);
		this.autor = new Autor();
		return "livro?faces-redirect=true";
	}
	
	public List<Autor> getAutores() {
		return new DAO<Autor>(Autor.class).listaTodos();
	}
}
