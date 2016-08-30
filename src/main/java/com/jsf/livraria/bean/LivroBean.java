package com.jsf.livraria.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.jsf.livraria.dao.AutorDAO;
import com.jsf.livraria.dao.LivroDAO;
import com.jsf.livraria.model.Autor;
import com.jsf.livraria.model.Livro;
import com.jsf.livraria.tx.Transacional;

@Named
@ViewScoped
public class LivroBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Livro livro = new Livro();
	private Integer livroId;
	private int autorId;
	private List<Livro> livros;
	
	@Inject
	LivroDAO dao;
	
	@Inject
	AutorDAO autorDAO;
	
	@Inject
	FacesContext facesContext;

	public Livro getLivro() {
		return livro;
	}
	
	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public List<Livro> getLivros(){
	    if(this.livros == null)
	        this.livros = this.dao.listaTodos();      

	    return livros;
	}
	
	public Integer getLivroId() {
		return livroId;
	}

	public void setLivroId(Integer livroId) {
		this.livroId = livroId;
	}

	public int getAutorId() {
		return autorId;
	}

	public void setAutorId(int autorId) {
		this.autorId = autorId;
	}
	
	public void carregaPelaId() {
        this.livro = this.dao.buscaPorId(this.livroId);
    }
	
	public List<Autor> getAutores(){
		return autorDAO.listaTodos();
	}
	
	public List<Autor> getAutoresDoLivro(){
		return this.livro.getAutores();
	}

	public void gravarAutor(){
		Autor autor = autorDAO.buscaPorId(this.autorId);
		this.livro.adicionaAutor(autor);
	}
	
	@Transacional
	public void gravar() {
		System.out.println("Gravando livro " + this.livro.getTitulo());

		if (livro.getAutores().isEmpty())
			facesContext.addMessage("autor", new FacesMessage("Livro deve ter pelo menos um Autor."));
		
		if(this.livro.getId() == null){
			this.dao.adiciona(this.livro);
			
			this.livros = this.dao.listaTodos();
		} else {
			this.dao.atualiza(this.livro);
		}
		
		this.livro = new Livro();
	}
	
	@Transacional
	public void remove(Livro livro){
		System.out.println("Removendo livro " + livro.getTitulo());
		this.dao.remove(livro);
	}
	
	public void edit(Livro livro){
		System.out.println("Alterando livro " + livro.getTitulo());
		this.livro = this.dao.buscaPorId(livro.getId());
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
