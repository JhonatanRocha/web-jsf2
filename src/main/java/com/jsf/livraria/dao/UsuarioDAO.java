package com.jsf.livraria.dao;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.jsf.livraria.model.Usuario;


public class UsuarioDAO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
    EntityManager em;
	
    public boolean existe(Usuario usuario) {

        TypedQuery<Usuario> query = em
                .createQuery(
                        "select u from Usuario u where u.email = :pEmail and u.senha = :pSenha",
                        Usuario.class);

        query.setParameter("pEmail", usuario.getEmail());
        query.setParameter("pSenha", usuario.getSenha());

        try {
            Usuario resultado = query.getSingleResult();
        } catch (NoResultException ex) {
            return false;
        }

        return true;
    }
}