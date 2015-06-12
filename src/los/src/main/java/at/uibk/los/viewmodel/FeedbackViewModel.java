package at.uibk.los.viewmodel;

import java.util.List;

import at.uibk.los.model.interfaces.IFeedback;
import at.uibk.los.viewmodel.ViewModelConverter.Instantiator;

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
	
	public static List<FeedbackViewModel> get(List<IFeedback> src) {
		return ViewModelConverter.<FeedbackViewModel, IFeedback>convert(src, 
		new Instantiator<FeedbackViewModel, IFeedback>() {
			@Override
			public FeedbackViewModel create(IFeedback data) {
				return new FeedbackViewModel(data);
			}
		});
	}
	
}