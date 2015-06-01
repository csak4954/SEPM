package at.uibk.los.viewmodel;

import at.uibk.los.model.interfaces.IFeedBack;

public class FeedbackViewModel
{
	private int id;
	private String title;
	private String date;
	private String from;
	private String message; 
	private int rating;
	
	public FeedbackViewModel(IFeedBack feedback){
        this.id = feedback.getId();
        this.title = feedback.getTitle();
        this.date = feedback.getDate();
        this.from = feedback.getFrom();
        this.message = feedback.getMessage();
        this.rating = feedback.getRating();
	}
	
	public FeedbackViewModel(){}  // needed by jackson to rebuild from json
	
	// getter needed by jackson to build json representation

	public int getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public String getDate() {
		return date;
	}

	public String getFrom() {
		return from;
	}

	public String getMessage() {
		return message;
	}

	public int getRating() {
		return rating;
	}
	
}