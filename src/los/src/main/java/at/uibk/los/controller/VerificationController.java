package at.uibk.los.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import at.uibk.los.AppDomain;
import at.uibk.los.model.authorization.LOSAccessDeniedException;
import at.uibk.los.model.exceptions.EntityNotFoundException;
import at.uibk.los.model.exceptions.InvalidVerificationKeyException;
import at.uibk.los.model.exceptions.VerificationInactiveException;
import at.uibk.los.viewmodel.StatusViewModel;

@Controller
@RequestMapping("lecture/{lectureId}/verification")
public class VerificationController
{
	@RequestMapping(method = RequestMethod.PUT)
	public @ResponseBody Object getVerificationCode(@PathVariable String lectureId,
													HttpServletResponse response)
	{
		try 
		{
			String key = AppDomain.get().renewAttendanceVerification(lectureId);
			return StatusViewModel.onSuccessCreated(response, key);		
		} 
		catch (LOSAccessDeniedException e) {
			return StatusViewModel.onException(e, response);			
		} 
		catch (EntityNotFoundException e) {
			return StatusViewModel.onException(e, response);
		}
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	public @ResponseBody Object endVerification(@PathVariable String lectureId,
												HttpServletResponse response){
	
		try 
		{
			AppDomain.get().endAttendanceVerification(lectureId);
			return StatusViewModel.onSuccessNoContent(response);		
		} 
		catch (LOSAccessDeniedException e) {
			return StatusViewModel.onException(e, response);
			
		} 
		catch (EntityNotFoundException e) {
			return StatusViewModel.onException(e, response);
		}
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody Object verify(@PathVariable String lectureId,
									   @RequestParam String key,
									   HttpServletResponse response)
	{
	
		try 
		{
			AppDomain.get().confirmAttendance(lectureId, key);
			return StatusViewModel.onSuccessNoContent(response);			
		} 
		catch (LOSAccessDeniedException e) {
			return StatusViewModel.onException(e, response);
		} 
		catch (EntityNotFoundException e) {
			return StatusViewModel.onException(e, response);
		} 
		catch (VerificationInactiveException e) {
			return StatusViewModel.onException(e, response);
		} 
		catch (InvalidVerificationKeyException e) {
			return StatusViewModel.onException(e, response);
		}
	}
}
