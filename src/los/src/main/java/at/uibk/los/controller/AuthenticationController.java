package at.uibk.los.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import at.uibk.los.AppDomain;
import at.uibk.los.login.ExternalUser;
import at.uibk.los.login.LoginProvider;
import at.uibk.los.model.exceptions.EntityNotFoundException;
import at.uibk.los.model.interfaces.IUser;
import at.uibk.los.viewmodel.LoginViewModel;
import at.uibk.los.viewmodel.RegisterViewModel;
import at.uibk.los.viewmodel.StatusViewModel;
import at.uibk.los.viewmodel.UserViewModel;

@Controller
@RequestMapping()
public class AuthenticationController{

	private void verifyRegistration(HttpServletResponse response, RegisterViewModel registerVM) throws ViewModelVerificationException
	{
		if(registerVM == null) {
			throw new ViewModelVerificationException(StatusViewModel.onParamNull(response, "Registration null"));
		}
		
		if(registerVM.getId() == null) {
			throw new ViewModelVerificationException(StatusViewModel.onParamInvalid(response, "Username missing"));
		}
		else if(registerVM.getId().length() < RegisterViewModel.minUsernameLength) {
			throw new ViewModelVerificationException(StatusViewModel.onParamInvalid(response, "Username invalid: minimum length: " + RegisterViewModel.minUsernameLength));
		}
		
		if(registerVM.getPassword() == null) {
			throw new ViewModelVerificationException(StatusViewModel.onParamInvalid(response, "Password missing"));
		}
		else if(registerVM.getPassword().length() < RegisterViewModel.minPasswordLength) {
			throw new ViewModelVerificationException(StatusViewModel.onParamInvalid(response, "Password invalid: minimum length: " + RegisterViewModel.minPasswordLength));
		}
		
		if(registerVM.getEmail() == null) {
			throw new ViewModelVerificationException(StatusViewModel.onParamInvalid(response, "Email missing"));
		}
		else if(!RegisterViewModel.validateEmail(registerVM.getEmail())) {
			throw new ViewModelVerificationException(StatusViewModel.onParamInvalid(response, "Email invalid"));
		}

		if(registerVM.getName() == null) {
			registerVM.setName("");
		}
		
		if(registerVM.getSurname() == null) {
			registerVM.setSurname("");
		}
	}
	
	@RequestMapping(value = "/authentication/register/student", method = RequestMethod.PUT)
	public @ResponseBody Object registerStudent(@RequestBody RegisterViewModel registerVM,			 
										 		HttpServletResponse response)
	{
		try 
		{
			verifyRegistration(response, registerVM);
		} 
		catch (ViewModelVerificationException e) {
			return e.getResponse();
		}
		
		LoginProvider provider = AppDomain.get().getServiceProvider().getLoginProvider();
		
		if(provider.existsUser(registerVM.getId())) {
			return StatusViewModel.onParamInvalid(response, "Username already taken");
		}

		ExternalUser user = new ExternalUser(registerVM.getName(), registerVM.getSurname(), registerVM.getId(), registerVM.getPassword(), registerVM.getEmail(), "student");
		provider.addUser(user);

		return StatusViewModel.onSuccessCreated(response, new UserViewModel(user));
	}	
	
	@RequestMapping(value = "/authentication/register/professor", method = RequestMethod.PUT)
	public @ResponseBody Object registerProfessor(@RequestBody RegisterViewModel registerVM,			 
										   		  HttpServletResponse response)
	{
		IUser user = AppDomain.get().getUser();
		if(user == null || !user.getAffilation().equals(ExternalUser.adminAffiliation)) {
			return StatusViewModel.onParamInvalid(response, "Must be logged in as admin");
		}
		
		try 
		{
			verifyRegistration(response, registerVM);
		} 
		catch (ViewModelVerificationException e) {
			return e.getResponse();
		}
		
		LoginProvider provider = AppDomain.get().getServiceProvider().getLoginProvider();
		
		if(provider.existsUser(registerVM.getId())) {
			return StatusViewModel.onParamInvalid(response, "Username already taken");
		}
		
		ExternalUser newUser = new ExternalUser(registerVM.getName(), registerVM.getSurname(), registerVM.getId(), registerVM.getPassword(), registerVM.getEmail(), "staff");
		provider.addUser(newUser);

		return StatusViewModel.onSuccessCreated(response, new UserViewModel(newUser));
	}	
	
	@RequestMapping(value = "/authentication/login", method = RequestMethod.POST)
	public @ResponseBody Object login(@RequestBody LoginViewModel login,			 
										   HttpServletResponse response)
	{
		LoginProvider provider = AppDomain.get().getServiceProvider().getLoginProvider();
			
		if(provider.login(login.getUsername(), login.getPassword())) 
		{
			return StatusViewModel.onSuccess(response, new UserViewModel(provider.getUser()));
		}
		
		return StatusViewModel.onLoginFailed(response);
	}
	
	@RequestMapping(value = "/authentication/logout", method = RequestMethod.POST)
	public @ResponseBody Object logout(HttpServletResponse response) 
	{
		LoginProvider provider = AppDomain.get().getServiceProvider().getLoginProvider();
		provider.logout();
		return StatusViewModel.onSuccessNoContent(response);
	}
	
	@RequestMapping(value = "/authentication", method = RequestMethod.GET)
	public @ResponseBody Object getUser(HttpServletResponse response)
	{
		IUser user = AppDomain.get().getUser();
		if(user != null) {
			return StatusViewModel.onSuccess(response, new UserViewModel(user));
		}
		else {
			return StatusViewModel.onParamInvalid(response, "Not logged in");
		}
	}
}
