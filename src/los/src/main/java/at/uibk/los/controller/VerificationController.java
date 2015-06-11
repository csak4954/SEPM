package at.uibk.los.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import at.uibk.los.AppDomain;
import at.uibk.los.model.EntityNotFoundException;
import at.uibk.los.model.authorization.LOSAccessDeniedException;
import at.uibk.los.viewmodel.ErrorViewModel;
import at.uibk.los.viewmodel.VerificationViewModel;

/**
 * Responds with a ViewModel as JSON.
 */
@Controller
@RequestMapping("lecture/{lectureId}/verification")
public class VerificationController
{
	@RequestMapping(value="/", method = RequestMethod.GET)
	public @ResponseBody Object getVerificationCode(@PathVariable String lectureId,
													HttpServletResponse response){
	
		try 
		{
			String key = AppDomain.get().renewAttendanceVerification(lectureId);
			return new VerificationViewModel(key);
			
		} catch (LOSAccessDeniedException e)
		{
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return new ErrorViewModel(e);
			
		} catch (EntityNotFoundException e) 
		{
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return new ErrorViewModel(e);
		}
	}
	
	@RequestMapping(value="/", method = RequestMethod.DELETE)
	public @ResponseBody Object endVerification(@PathVariable String lectureId,
												HttpServletResponse response){
	
		try 
		{
			AppDomain.get().endAttendanceVerification(lectureId);
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			return null;
			
		} catch (LOSAccessDeniedException e)
		{
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return new ErrorViewModel(e);
			
		} catch (EntityNotFoundException e) 
		{
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return new ErrorViewModel(e);
		}
	}
	
	@RequestMapping(value="/", method = RequestMethod.POST)
	public @ResponseBody Object verify(@PathVariable String lectureId,
									   @RequestParam String key,
									   HttpServletResponse response){
	
		try 
		{
			AppDomain.get().confirmAttendance(lectureId, key);
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			return null;
			
		} catch (LOSAccessDeniedException e)
		{
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return new ErrorViewModel(e);
			
		} catch (EntityNotFoundException e) 
		{
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return new ErrorViewModel(e);
		}
	}
}
