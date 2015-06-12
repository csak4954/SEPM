package at.uibk.los.viewmodel;

import java.util.List;

import at.uibk.los.model.interfaces.ILectureView;
import at.uibk.los.viewmodel.ViewModelConverter.Instantiator;

public class LectureViewModel
{
	private String id;
	private String title;
	private String description;
	
	public LectureViewModel(ILectureView lecture) {
        this.id = lecture.getId();
        this.title = lecture.getTitle();
        this.description = lecture.getDescription();
	}
	
	public LectureViewModel(){}  // needed by jackson to rebuild from json
	
	public String getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public static List<LectureViewModel> convert(List<ILectureView> src) 
	{
		return ViewModelConverter.<LectureViewModel, ILectureView>convert(src, new Instantiator<LectureViewModel, ILectureView>() {
			@Override
			public LectureViewModel create(ILectureView data) {
				return new LectureViewModel(data);
			}
		});
	}
}