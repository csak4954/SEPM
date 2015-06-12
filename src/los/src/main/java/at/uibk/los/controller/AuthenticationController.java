package at.uibk.los.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import at.uibk.los.AppDomain;
import at.uibk.los.login.LoginProvider;
import at.uibk.los.viewmodel.StatusViewModel;
import at.uibk.los.viewmodel.UserViewModel;

@Controller
@RequestMapping("/authentication")
public class AuthenticationController{

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody Object login(@RequestParam("username") String username, 
									  @RequestParam("password") String password, 
									  HttpServletResponse response) 
	{
		LoginProvider provider = AppDomain.get().getServiceProvider().getLoginProvider();
			
		if(provider.login(username, password)) 
		{
			return StatusViewModel.onSuccess(response, new UserViewModel(provider.getUser()));
		}
		
		return StatusViewModel.onLoginFailed(response);
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public @ResponseBody Object logout(HttpServletResponse response) 
	{
		LoginProvider provider = AppDomain.get().getServiceProvider().getLoginProvider();
		provider.logout();
		return StatusViewModel.onSuccessNoContent(response);
	}
}
