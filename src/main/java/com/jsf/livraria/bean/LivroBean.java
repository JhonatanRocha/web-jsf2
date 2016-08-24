package com.jsf.livraria.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.jsf.livraria.dao.DAO;
import com.jsf.livraria.model.Autor;
import com.jsf.livraria.model.Livro;

@Named
@ViewScoped
public class LivroBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Livro livro = new Livro();
	private Integer livroId;
	private int autorId;
	private List<Livro> livros;

	public Livro getLivro() {
		return livro;
	}
	
	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public List<Livro> getLivros(){
		DAO<Livro> dao = new DAO<Livro>(Livro.class);

	    if(this.livros == null)
	        this.livros = dao.listaTodos();      

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
        this.livro = new DAO<Livro>(Livro.class).buscaPorId(this.livroId);
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

		if (livro.getAutores().isEmpty())
			FacesContext.getCurrentInstance().addMessage("autor", new FacesMessage("Livro deve ter pelo menos um Autor."));

		DAO<Livro> dao = new DAO<Livro>(Livro.class);
		
		if(this.livro.getId() == null){
			dao.adiciona(this.livro);
			
			this.livros = dao.listaTodos();
		} else {
			dao.atualiza(this.livro);
		}
		
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
