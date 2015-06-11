package at.uibk.los.viewmodel;

//import java.util.List;

import java.util.LinkedList;
import java.util.List;

import at.uibk.los.model.interfaces.IQuestionView;
import at.uibk.los.model.interfaces.IQuizView;

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
        
        questions = new LinkedList<QuestionViewModel>();

        for(IQuestionView question : quiz.getQuestionView()) {
        	questions.add(new QuestionViewModel(question));
        }
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
}