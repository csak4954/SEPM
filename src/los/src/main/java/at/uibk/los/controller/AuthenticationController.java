package at.uibk.los.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import at.uibk.los.AppDomain;
import at.uibk.los.login.LoginProvider;
import at.uibk.los.viewmodel.AuthenticationViewModel;
import at.uibk.los.viewmodel.ErrorViewModel;

/**
 * Responds with a ViewModel as JSON.
 */
@Controller
@RequestMapping("/authentication")
public class AuthenticationController{

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody Object returnAffiliation(@RequestParam("username") String username, 
												  @RequestParam("password") String password, 
												  HttpServletResponse response) {
		
	
		LoginProvider provider = AppDomain.get().getServiceProvider().getLoginProvider();
		if(provider.login(username, password)) {
			if(provider.getUser().getAffilation().equals("student")) return new AuthenticationViewModel("student");
			else if (provider.getUser().getAffilation().equals("staff")) return new AuthenticationViewModel("professor");
		}
		
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		return ErrorViewModel.invalidLogin;		
	}
	
}
