package at.uibk.los.viewmodel;

import at.uibk.los.model.interfaces.IFeedback;

public class FeedbackViewModel
{
	private String id;
	private String message; 
	private int rating;
	
	public FeedbackViewModel(IFeedback feedback){
        this.id = feedback.getId();
        this.message = feedback.getMessage();
        this.rating = feedback.getRating();
	}
	
	public FeedbackViewModel(){}  // needed by jackson to rebuild from json

	public String getId() {
		return id;
	}

	public String getMessage() {
		return message;
	}

	public int getRating() {
		return rating;
	}
	
}