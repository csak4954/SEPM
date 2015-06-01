package at.uibk.los.viewmodel;

import at.uibk.los.model.interfaces.ILectureView;

public class LectureViewModel
{
	private String id;
	private String title;
	private String description;
	
	public LectureViewModel(ILectureView lecture){
        this.id = lecture.getId();
        this.title = lecture.getTitle();
        this.description = lecture.getDescription();
	}
	
	public LectureViewModel(){}  // needed by jackson to rebuild from json
	
	// getter needed by jackson to build json representation

	public String getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public String getDescription() {
		return description;
	}
	
}