package at.uibk.los.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.mockito.Mockito.*;
import at.uibk.los.model.interfaces.IFeedback;
import at.uibk.los.viewmodel.FeedbackViewModel;

/**
 * Responds with a ViewModel as JSON.
 */
@Controller
@RequestMapping("/feedback")
public class FeedbackController{

	@RequestMapping(value="/{lectureId}", method = RequestMethod.GET)
	public @ResponseBody FeedbackViewModel returnVerificationCode(@PathVariable int lectureId){
		IFeedback feedback = mock(IFeedback.class);
		when(feedback.getId()).thenReturn("1");
		/*when(feedback.getTitle()).thenReturn("FeedbackTitle");
		when(feedback.getDate()).thenReturn("FeedbackDate");
		when(feedback.getFrom()).thenReturn("FeedbackFrom");*/
		when(feedback.getMessage()).thenReturn("This is the feedback message.");
		when(feedback.getRating()).thenReturn(5);
		return new FeedbackViewModel(feedback);
		
	}
	
}
