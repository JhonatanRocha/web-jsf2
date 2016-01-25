package com.jsf.livraria.bean;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class LivroBean {
	
	private Livro livro = new Livro();

	public Livro getLivro(){
		return livro;
	}
	
	public void gravar() {
		System.out.println("Gravando Livro... " + getLivro().getTitulo());
	}
}

