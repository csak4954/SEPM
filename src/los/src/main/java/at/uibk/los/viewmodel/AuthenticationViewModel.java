package at.uibk.los.viewmodel;


public class AuthenticationViewModel
{
	private String affiliation;
	
	public AuthenticationViewModel(String affiliation){
        this.affiliation = affiliation;
	}
	
	public AuthenticationViewModel(){}  // needed by jackson to rebuild from json
	
	// getter needed by jackson to build json representation


	public String getAffiliation() {
		return this.affiliation;
	}
	
}