package at.uibk.los.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import at.uibk.los.AppDomain;
import at.uibk.los.model.EntityNotFoundException;
import at.uibk.los.model.authorization.LOSAccessDeniedException;
import at.uibk.los.viewmodel.VerificationViewModel;

/**
 * Responds with a ViewModel as JSON.
 */
@Controller
@RequestMapping("/verification")
public class VerificationController{

	@RequestMapping(value="/{lectureId}", method = RequestMethod.GET)
	public @ResponseBody VerificationViewModel returnVerificationCode(@PathVariable String lectureId){
	
		String key = null;
		
		try {
			key = AppDomain.get().renewAttendanceVerification(lectureId);
		} catch (LOSAccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new VerificationViewModel(key);
		
	}
	
}
