package com.jsf.livraria.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.jsf.livraria.dao.UsuarioDAO;
import com.jsf.livraria.model.Usuario;

@Named
@ViewScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Usuario usuario = new Usuario();
	
	@Inject
	UsuarioDAO usuarioDAO;
	
	@Inject
	FacesContext context;

    public Usuario getUsuario() {
        return usuario;
    }
    
    public String access() {
       System.out.println("Fazendo login do usuário " + this.usuario.getEmail());
       
       boolean alreadyExists = usuarioDAO.existe(this.usuario);

        if (alreadyExists) {
        	context.getExternalContext().getSessionMap().put("loggedUser", this.usuario);
            
        	return "livro?faces-redirect=true";
        }
        
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage("Usuário não encontrado ou Senha incorreta"));
        
        return "login?faces-redirect=true";
    }
    
    public String logout() {
        context.getExternalContext().getSessionMap().remove("loggedUser");

        return "login?faces-redirect=true";
    }
}
