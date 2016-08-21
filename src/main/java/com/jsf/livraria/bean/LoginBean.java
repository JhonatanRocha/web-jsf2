package com.jsf.livraria.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.jsf.livraria.dao.UsuarioDAO;
import com.jsf.livraria.model.Usuario;

@ManagedBean
@ViewScoped
public class LoginBean {

	private Usuario usuario = new Usuario();

    public Usuario getUsuario() {
        return usuario;
    }
    
    public String access() {
        System.out.println("Fazendo login do usuário " + this.usuario.getEmail());
        
       FacesContext context = FacesContext.getCurrentInstance();
       boolean alreadyExists = new UsuarioDAO().existe(this.usuario);

        if (alreadyExists) {
        	
        	context.getExternalContext().getSessionMap().put("loggedUser", this.usuario);
            
        	return "livro?faces-redirect=true";
        }
        
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage("Usuário não encontrado ou Senha incorreta"));
        
        return "login?faces-redirect=true";
    }
    
    public String logout() {

        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().remove("loggedUser");

        return "login?faces-redirect=true";
    }
}
