package at.uibk.los.controller;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import at.uibk.los.AppDomain;
import at.uibk.los.login.LoginProvider;
import at.uibk.los.model.authorization.LOSAccessDeniedException;
import at.uibk.los.viewmodel.LoginViewModel;
import at.uibk.los.viewmodel.StatusViewModel;
import at.uibk.los.viewmodel.UserViewModel;

@Controller
@RequestMapping()
public class AuthenticationController{

	
	@RequestMapping(value = "/authentication/login", method = RequestMethod.POST)
	public @ResponseBody Object answerQuiz(@RequestBody LoginViewModel login,			 
										   HttpServletResponse response)
	{
		LoginProvider provider = AppDomain.get().getServiceProvider().getLoginProvider();
			
		if(provider.login(login.getUsername(), login.getPassword())) 
		{
			return StatusViewModel.onSuccess(response, new UserViewModel(provider.getUser()));
		}
		
		return StatusViewModel.onSuccessNoContent(response);
	}
	
	@RequestMapping(value = "/authentication/logout", method = RequestMethod.POST)
	public @ResponseBody Object logout(HttpServletResponse response) 
	{
		LoginProvider provider = AppDomain.get().getServiceProvider().getLoginProvider();
		provider.logout();
		return StatusViewModel.onSuccessNoContent(response);
	}
	
	@RequestMapping(value = "/authentication", method = RequestMethod.GET)
	public @ResponseBody Object isIsProfessor(HttpServletResponse response)
	{
		try
		{
			return StatusViewModel.onSuccess(response, new UserViewModel(AppDomain.get().getUser()));
		}
		catch (LOSAccessDeniedException e) 
		{ 
			return StatusViewModel.onException(e, response);
		}
		catch (Exception e)
		{
			return StatusViewModel.onException(new LOSAccessDeniedException(), response);
		}
	}
}
