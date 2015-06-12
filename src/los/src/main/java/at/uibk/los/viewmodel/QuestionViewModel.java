package at.uibk.los.viewmodel;

import java.util.List;

import at.uibk.los.model.interfaces.IQuestionView;
import at.uibk.los.viewmodel.ViewModelConverter.Instantiator;

public class QuestionViewModel {

	private String id;
	private String text;
	private List<AnswerViewModel> answers;
	
	public QuestionViewModel(IQuestionView question) {
		this.id = question.getId();
		this.text = question.getText();
		this.answers = AnswerViewModel.get(question.getAnswerView());
	}
	
	public QuestionViewModel() { }
	
	public String getId() {
		return id;
	}

	public String getText() {
		return text;
	}
	
	public List<AnswerViewModel> getAnswers() {
		return answers;
	}
	
	public static List<QuestionViewModel> get(List<IQuestionView> src) {
		return ViewModelConverter.<QuestionViewModel, IQuestionView>convert(src, 
		new Instantiator<QuestionViewModel, IQuestionView>() {
			@Override
			public QuestionViewModel create(IQuestionView data) {
				return new QuestionViewModel(data);
			}
		});
	}
}

