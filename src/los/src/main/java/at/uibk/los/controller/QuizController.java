package at.uibk.los.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import at.uibk.los.AppDomain;
import at.uibk.los.model.authorization.LOSAccessDeniedException;
import at.uibk.los.model.interfaces.ILectureView;
import at.uibk.los.model.interfaces.IQuizView;
import at.uibk.los.viewmodel.QuizViewModel;

/**
 * Responds with a ViewModel as JSON.
 */
@Controller
@RequestMapping("/quiz")
public class QuizController{
	
	@RequestMapping(value="/{lectureId}", method = RequestMethod.GET)
	public @ResponseBody List<QuizViewModel> returnQuizByLectureId(@PathVariable String lectureId){
		
		List<QuizViewModel> quiz = new LinkedList<QuizViewModel>(); 
		
		try 
		{
			List<ILectureView> lectures = AppDomain.get().getAssociatedLectures();
			
			for(ILectureView lecture : lectures)
			{
				if(lecture.getId().equals(lectureId))
				{
					
					List<IQuizView> quizViews = lecture.getQuizView();
					
					if(quiz != null) {
						
						for(IQuizView quizView : quizViews) {
							quiz.add(new QuizViewModel(quizView));
						}
					}
				}
			}
			
		} catch (LOSAccessDeniedException e) {
			e.printStackTrace();
		}
		
		return quiz;
		
		/*IAnswer answer = mock(IAnswer.class);
		@SuppressWarnings("unchecked")
		List<IAnswer> answers = mock(List.class);
		@SuppressWarnings("unchecked")
		Iterator<IAnswer> ianswers = mock(Iterator.class);
		when(answers.iterator()).thenReturn(ianswers);
        when(ianswers.hasNext()).thenReturn(true); 
        when(ianswers.next()).thenReturn(answer); 
        when(ianswers.hasNext()).thenReturn(false);
		when(answer.getId()).thenReturn("1");
		when(answer.getText()).thenReturn("answerText1");
		when(answer.isSolution()).thenReturn(true);	
		IQuestion question = mock(IQuestion.class);
		@SuppressWarnings("unchecked")
		List<IQuestion> questions = mock(List.class);
		@SuppressWarnings("unchecked")
		Iterator<IQuestion> iquestions = mock(Iterator.class);
		when(questions.iterator()).thenReturn(iquestions);
        when(iquestions.hasNext()).thenReturn(true); 
        when(iquestions.next()).thenReturn(question); 
        when(iquestions.hasNext()).thenReturn(false);
		when(question.getId()).thenReturn("10");
		when(question.getText()).thenReturn("questionText");
		when(question.getAnswers()).thenReturn(answers);		
		IQuiz quiz = mock(IQuiz.class);
		when(quiz.getId()).thenReturn("2345");
		when(quiz.getQuestions()).thenReturn(questions);
		return new QuizViewModel(quiz);*/
	}
}
