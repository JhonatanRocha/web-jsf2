package com.jsf.livraria.bean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import com.jsf.livraria.dao.DAO;
import com.jsf.livraria.model.Autor;
import com.jsf.livraria.model.Livro;

@ManagedBean
@ViewScoped
public class LivroBean {

	private Livro livro = new Livro();
	private int autorId;

	public Livro getLivro() {
		return livro;
	}
	
	public List<Livro> getLivros(){
		return new DAO<Livro>(Livro.class).listaTodos();
	}

	public int getAutorId() {
		return autorId;
	}

	public void setAutorId(int autorId) {
		this.autorId = autorId;
	}
	
	public List<Autor> getAutores(){
		return new DAO<Autor>(Autor.class).listaTodos();
	}
	
	public List<Autor> getAutoresDoLivro(){
		return this.livro.getAutores();
	}

	public void gravarAutor(){
		Autor autor = new DAO<Autor>(Autor.class).buscaPorId(this.autorId);
		this.livro.adicionaAutor(autor);
	}
	
	public void gravar() {
		System.out.println("Gravando livro " + this.livro.getTitulo());

		if (livro.getAutores().isEmpty()) {
			//throw new RuntimeException("Livro deve ter pelo menos um Autor.");
			FacesContext.getCurrentInstance().addMessage("autor", new FacesMessage("Livro deve ter pelo menos um Autor."));
		}

		if(this.livro.getId() == null)
			new DAO<Livro>(Livro.class).adiciona(this.livro);
		else
			new DAO<Livro>(Livro.class).atualiza(this.livro);
		this.livro = new Livro();
	}
	
	public void remove(Livro livro){
		System.out.println("Removendo livro " + livro.getTitulo());
		
		new DAO<Livro>(Livro.class).remove(livro);
	}
	
	public void edit(Livro livro){
		System.out.println("Alterando livro " + livro.getTitulo());
		this.livro = livro;
	}
	
	public void removeAuthorFromBook(Autor autor){
		System.out.println("retirando autor do livro: " + autor.getNome());
		this.livro.removeAutor(autor);
	}
	
	public String formAutor(){
		System.out.println("Chamando o fomulário do Autor");
		return "autor?faces-redirect=true";
	}
	
	public void isbnPattern(FacesContext fc, UIComponent uiComponente, Object obj) throws ValidatorException {
		String valor = obj.toString();
		if(!valor.startsWith("1")){
			throw new ValidatorException(new FacesMessage("Deveria começar com o número 1"));
		}
	}
}
