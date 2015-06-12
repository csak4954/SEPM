package at.uibk.los.viewmodel;

import javax.servlet.http.HttpServletResponse;

import at.uibk.los.model.authorization.LOSAccessDeniedException;
import at.uibk.los.model.authorization.permissions.DefaultPermission;
import at.uibk.los.model.exceptions.EntityNotFoundException;
import at.uibk.los.model.exceptions.InvalidVerificationKeyException;
import at.uibk.los.model.exceptions.QuizInactiveException;
import at.uibk.los.model.exceptions.VerificationInactiveException;

public class StatusViewModel {

	private String message;

	public StatusViewModel(String message) {
		this.message = message;
	}
	
	public StatusViewModel(Exception exception) {
		this.message  = exception.getMessage();
	}
	
	public String getMessage() {
		return message;
	}

	public static final int SC_UNPROCESSABLE_ENTITY = 422;
		
	public static Object onSuccessNoContent(HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_NO_CONTENT);
		return null;
	}
	
	public static Object onSuccessCreated(HttpServletResponse response, Object data) {
		response.setStatus(HttpServletResponse.SC_CREATED);
		return data;
	}
	
	public static Object onSuccess(HttpServletResponse response, Object data) {
		response.setStatus(HttpServletResponse.SC_OK);
		return data;
	}
	
	public static StatusViewModel onException(LOSAccessDeniedException e, HttpServletResponse response) {
		if(e.getPermission().getId() == DefaultPermission.id) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
		else {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		}
		
		return new StatusViewModel(e);
	}
	
	public static Object onException(EntityNotFoundException e, HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		return new StatusViewModel(e);
	}
	
	public static Object onException(QuizInactiveException e, HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_CONFLICT);
		return new StatusViewModel(e);
	}
	
	public static Object onException(VerificationInactiveException e, HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_CONFLICT);
		return new StatusViewModel(e);
	}

	public static Object onException(InvalidVerificationKeyException e, HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_CONFLICT);
		return new StatusViewModel(e);
	}
	
	public static Object onParamNull(HttpServletResponse response, String message) {
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		return new StatusViewModel(message);
	}

	public static Object onParamInvalid(HttpServletResponse response, String message) {
		response.setStatus(StatusViewModel.SC_UNPROCESSABLE_ENTITY);
		return new StatusViewModel(message);
	}
	
	public static Object onLoginFailed(HttpServletResponse response) {
		response.setStatus(StatusViewModel.SC_UNPROCESSABLE_ENTITY);
		return new StatusViewModel("user/password combination invalid");
	}
}
