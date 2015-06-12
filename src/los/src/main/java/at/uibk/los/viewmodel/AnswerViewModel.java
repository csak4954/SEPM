package at.uibk.los.viewmodel;

import java.util.List;

import at.uibk.los.model.interfaces.IAnswerView;
import at.uibk.los.viewmodel.ViewModelConverter.Instantiator;

public class AnswerViewModel {

	private String id;
	private String text;
	private boolean solution;
	
	public AnswerViewModel(IAnswerView answer) {
		this.id = answer.getId();
		this.text = answer.getText();
		this.solution = false;
	}
	
	public AnswerViewModel() { }
	
	public String getId()  {
		return id;
	}
	
	public String getText() {
		return text;
	}
	
	public boolean isSolution() {
		return solution;
	}
	
	public static List<AnswerViewModel> get(List<IAnswerView> src) {
		return ViewModelConverter.<AnswerViewModel, IAnswerView>convert(src, 
		new Instantiator<AnswerViewModel, IAnswerView>() {
			@Override
			public AnswerViewModel create(IAnswerView data) {
				return new AnswerViewModel(data);
			}
		});
	}
}
