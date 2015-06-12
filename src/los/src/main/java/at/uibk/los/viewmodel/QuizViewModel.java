package at.uibk.los.viewmodel;

//import java.util.List;

import java.util.List;

import at.uibk.los.model.interfaces.IQuizView;
import at.uibk.los.viewmodel.ViewModelConverter.Instantiator;

public class QuizViewModel
{
	private String id;
	private String title;
	private LectureViewModel lecture;
	private List<QuestionViewModel> questions;
	private boolean active;
	
	public QuizViewModel(IQuizView quiz)
	{
        this.id = quiz.getId();
        this.lecture = new LectureViewModel(quiz.getLectureView());
        this.active = quiz.isActive();
        this.title = quiz.getTitle();
        this.questions = QuestionViewModel.get(quiz.getQuestionView());
	}
	
	public QuizViewModel() { }
	
	public String getQuizId() {
		return this.id;
	}
	
	public List<QuestionViewModel> getQuestions(){
		return this.questions;
	}
	
	public LectureViewModel getLecture() {
		return lecture;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public String getTitle() {
		return title;
	}
	
	public static List<QuizViewModel> get(List<IQuizView> src) {
		return ViewModelConverter.<QuizViewModel, IQuizView>convert(src, 
		new Instantiator<QuizViewModel, IQuizView>() {
			@Override
			public QuizViewModel create(IQuizView data) {
				return new QuizViewModel(data);
			}
		});
	}
}