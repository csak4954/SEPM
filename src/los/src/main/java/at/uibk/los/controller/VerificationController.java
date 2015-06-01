package at.uibk.los.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import at.uibk.los.viewmodel.VerificationViewModel;

/**
 * Responds with a ViewModel as JSON.
 */
@Controller
@RequestMapping("/verification")
public class VerificationController{

	@RequestMapping(value="/{lectureId}", method = RequestMethod.GET)
	public @ResponseBody VerificationViewModel returnVerificationCode(@PathVariable int lectureId){
		return new VerificationViewModel("Code4lecture"+lectureId);
		
	}
	
}
