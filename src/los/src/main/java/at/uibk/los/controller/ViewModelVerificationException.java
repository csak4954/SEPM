package at.uibk.los.controller;

public class ViewModelVerificationException extends Exception {

	private static final long serialVersionUID = -9062033877892040952L;

	private Object response;

	public ViewModelVerificationException(Object response) {
		super();
		this.response = response;
	}

	public Object getResponse() {
		return response;
	}
}
