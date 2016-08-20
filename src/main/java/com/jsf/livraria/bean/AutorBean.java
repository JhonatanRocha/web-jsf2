package com.jsf.livraria.bean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.jsf.livraria.dao.DAO;
import com.jsf.livraria.model.Autor;
import com.jsf.livraria.model.Livro;

@ManagedBean
@ViewScoped
public class AutorBean {

	private Autor autor = new Autor();
	private Integer autorId;
	
	public Autor getAutor() {
		return autor;
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
		System.out.println("Alterando livro " + autor.getNome());
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
