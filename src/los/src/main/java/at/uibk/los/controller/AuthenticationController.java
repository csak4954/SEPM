package at.uibk.los.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import at.uibk.los.AppDomain;
import at.uibk.los.model.login.LoginProvider;
import at.uibk.los.viewmodel.AuthenticationViewModel;

/**
 * Responds with a ViewModel as JSON.
 */
@Controller
@RequestMapping("/authentication")
public class AuthenticationController{

	@RequestMapping(value="/{username}/{password}", method = RequestMethod.GET)
	public @ResponseBody AuthenticationViewModel returnAffiliation(@PathVariable String username, @PathVariable String password) {
		
		LoginProvider provider = AppDomain.get().getServiceProvider().getLoginProvider();
		if(provider.login(username, password)) {
			if(provider.getUser().getAffilation().equals("student")) return new AuthenticationViewModel("student");
			else if (provider.getUser().getAffilation().equals("staff")) return new AuthenticationViewModel("professor");
		}
		
		return new AuthenticationViewModel(null);		
	}
	
}
