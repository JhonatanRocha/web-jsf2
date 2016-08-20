package com.jsf.livraria.util;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import com.jsf.livraria.model.Usuario;

public class Authenticator implements PhaseListener{

	private static final long serialVersionUID = 1L;

	@Override
	public void afterPhase(PhaseEvent event) {
		
		FacesContext context = event.getFacesContext();
		
		String pageName = context.getViewRoot().getViewId();
		
		System.out.println(pageName);
		
		if("/login.xhtml".equals(pageName))
			return;

		
		Usuario user  = (Usuario) context.getExternalContext().getSessionMap().get("loggedUser");
		
		if(user != null)
			return;
		
		NavigationHandler navigationHandler = context.getApplication().getNavigationHandler();
		navigationHandler.handleNavigation(context, null, "/login?faces-redirect=true");
		context.renderResponse();
	}

	@Override
	public void beforePhase(PhaseEvent event) {
	
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}
