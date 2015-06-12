package at.uibk.los.viewmodel;

import java.util.LinkedList;
import java.util.List;

import at.uibk.los.model.interfaces.IQuizResult;
import at.uibk.los.model.interfaces.IScore;
import at.uibk.los.viewmodel.ViewModelConverter.Instantiator;
import at.uibk.los.viewmodel.ViewModelConverter.Predicate;

public class QuizResultViewModel {

	private LectureViewModel lecture;
	private QuizViewModel quiz;
	private double score;
	private List<QuestionResultViewModel> questionResults;
	
	public QuizResultViewModel(IQuizResult result) {
		
		this.score = result.getScore();
		this.quiz = new QuizViewModel(result.getQuizView());
		this.lecture = new LectureViewModel(result.getQuizView().getLectureView());
		
		questionResults = new LinkedList<QuestionResultViewModel>();
		
		for(IScore score : result.getQuestionScores()) {
			questionResults.add(new QuestionResultViewModel(score));
		}
	}
	
	public List<QuestionResultViewModel> getQuestionResults() {
		return questionResults;
	}
	
	public LectureViewModel getLecture() {
		return lecture;
	}
	
	public QuizViewModel getQuiz() {
		return quiz;
	}
	
	public double getScore() {
		return score;
	}
	
	public static List<QuizResultViewModel> get(List<IQuizResult> src) 
	{
		return ViewModelConverter.<QuizResultViewModel, IQuizResult>convert(src, 
		new Instantiator<QuizResultViewModel, IQuizResult>() {
			@Override
			public QuizResultViewModel create(IQuizResult data) {
				return new QuizResultViewModel(data);
			}
		});
	}
	
	public static List<QuizResultViewModel> getForLecture(List<IQuizResult> src, final String lectureId) 
	{
		return ViewModelConverter.<QuizResultViewModel, IQuizResult>convert(src,
		new Instantiator<QuizResultViewModel, IQuizResult>() {
			@Override
			public QuizResultViewModel create(IQuizResult data) {
				return new QuizResultViewModel(data);
			}
		}, 
		new Predicate<IQuizResult>() {
			@Override
			public boolean isValid(IQuizResult o) {
				return o.getQuizView().getLectureView().getId().equals(lectureId);
			}
		});
	}
}
