package at.uibk.los.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import at.uibk.los.viewmodel.AuthenticationViewModel;

/**
 * Responds with a ViewModel as JSON.
 */
@Controller
@RequestMapping("/authentication")
public class AuthenticationController{

	@RequestMapping(value="/{username}/{password}", method = RequestMethod.GET)
	public @ResponseBody AuthenticationViewModel returnAffiliation(@PathVariable String username, @PathVariable String password){
		if(username.equals("student")) return new AuthenticationViewModel("student");
		if(username.equals("professor")) return new AuthenticationViewModel("professor");
		return new AuthenticationViewModel(null);
		
	}
	
}
