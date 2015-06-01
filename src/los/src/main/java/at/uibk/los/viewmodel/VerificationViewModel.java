package at.uibk.los.viewmodel;


public class VerificationViewModel
{
	private String verificationCode;
	
	public VerificationViewModel(String verificationCode){
        this.verificationCode = verificationCode;
	}
	
	public VerificationViewModel(){}  // needed by jackson to rebuild from json
	
	// getter needed by jackson to build json representation


	public String getverificationCode() {
		return this.verificationCode;
	}
	
}