package at.uibk.los.viewmodel;

public class ErrorViewModel {

	private String message;

	public ErrorViewModel(String message) {
		this.message = message;
	}
	
	public ErrorViewModel(Exception exception) {
		this.message  = exception.getMessage();
	}
	
	public String getMessage() {
		return message;
	}
	
	public static ErrorViewModel invalidLogin = new ErrorViewModel("user/password combination invalid");
	
	public static final int SC_UNPROCESSABLE_ENTITY = 422;
	
}
