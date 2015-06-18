package at.uibk.los.controller;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import at.uibk.los.AppDomain;
import at.uibk.los.model.authorization.LOSAccessDeniedException;
import at.uibk.los.model.exceptions.EntityNotFoundException;
import at.uibk.los.model.exceptions.QuizInactiveException;
import at.uibk.los.model.interfaces.ILectureView;
import at.uibk.los.model.interfaces.IModel;
import at.uibk.los.model.interfaces.IQuestion;
import at.uibk.los.model.interfaces.IQuiz;
import at.uibk.los.model.interfaces.IQuizView;
import at.uibk.los.viewmodel.AnswerViewModel;
import at.uibk.los.viewmodel.QuestionAttemptViewModel;
import at.uibk.los.viewmodel.QuestionViewModel;
import at.uibk.los.viewmodel.QuizAttemptViewModel;
import at.uibk.los.viewmodel.QuizViewModel;
import at.uibk.los.viewmodel.StatusViewModel;

@Controller
@RequestMapping("")
public class QuizController {
	
	@RequestMapping(value="/quiz/active", method = RequestMethod.GET)
	public @ResponseBody Object getActiveQuiz(HttpServletResponse response) {
		
		IModel model = AppDomain.get();
		
		try 
		{
			List<IQuizView> quiz =  model.getActiveQuiz();
			return StatusViewModel.onSuccess(response, QuizViewModel.get(quiz));
			
		} 
		catch (LOSAccessDeniedException e) {
			return StatusViewModel.onException(e, response);
		}
	}
	
	@RequestMapping(value="/quiz/my", method = RequestMethod.GET)
	public @ResponseBody Object getMyQuiz(HttpServletResponse response) {
		
		IModel model = AppDomain.get();
				
		try 
		{
			List<ILectureView> lectures = model.getAssociatedLectures();
			List<IQuizView> quiz = new LinkedList<IQuizView>();
			
			for(ILectureView lecture : lectures) {
				
				List<IQuizView> quizViews = lecture.getQuizView();
				
				for(IQuizView quizView : quizViews) {
					quiz.add(quizView);
				}
			}
			
			return StatusViewModel.onSuccess(response, QuizViewModel.get(quiz));
		} 
		catch (LOSAccessDeniedException e) {
			return StatusViewModel.onException(e, response);
		}
	}
	
	@RequestMapping(value="/lecture/{id}/quiz", method = RequestMethod.PUT)
	public @ResponseBody Object addQuiz(@RequestBody QuizViewModel quizVM,
										@PathVariable String id,
									    HttpServletResponse response) {
		
		IModel model = AppDomain.get();
		
		// verify quiz
		if(quizVM == null) {
			return StatusViewModel.onParamNull(response, "Quiz null");
		}
		
		if(quizVM.getTitle() == null || quizVM.getTitle().isEmpty()) {
			return StatusViewModel.onParamInvalid(response, "Title missing");
		}
		
		if(quizVM.getQuestions() == null || quizVM.getQuestions().isEmpty()) {
			return StatusViewModel.onParamInvalid(response, "Questions missing");
		}

		for(QuestionViewModel question : quizVM.getQuestions()) 
		{			
			if(question == null) {
				return StatusViewModel.onParamInvalid(response, "Invalid question");
			}
			
			if(question.getText() == null || question.getText().isEmpty()) {
				return StatusViewModel.onParamInvalid(response, "Question text missing");
			}
			
			if(question.getAnswers() == null || question.getAnswers().isEmpty()) {
				return StatusViewModel.onParamInvalid(response, "\"" + question.getText() +  "\": Answers missing");
			}
			
			boolean solution = false;
			for(AnswerViewModel answer : question.getAnswers()) 
			{
				if(answer.getText() == null || answer.getText().isEmpty()) {
					return StatusViewModel.onParamInvalid(response, "\"" + question.getText() +  "\": Answer text missing");
				}
				
				if(answer.isSolution()) solution = true;
			}
			
			if(!solution) {
				return StatusViewModel.onParamInvalid(response, "\"" + question.getText() +  "\": Solution missing");
			}
		}
				
		try 
		{
			IQuiz quiz =  model.createQuiz(id, quizVM.getTitle());
			
			for(QuestionViewModel questionVM : quizVM.getQuestions()) 
			{
				IQuestion question = quiz.addQuestion(questionVM.getText());
				
				for(AnswerViewModel answerVM : questionVM.getAnswers()) 
				{
					question.addAnswer(answerVM.getText(), answerVM.isSolution());
				}
			}
			
			return StatusViewModel.onSuccessNoContent(response);
		} 
		catch (LOSAccessDeniedException e) {
			return StatusViewModel.onException(e, response);
		} 
		catch (EntityNotFoundException e) {
			return StatusViewModel.onException(e, response);
		}
	}
	
	@RequestMapping(value = "/lecture/{lectureId}/quiz/{quizId}", method = RequestMethod.DELETE)
	public @ResponseBody Object removeQuiz(@PathVariable String lectureId, 
										   @PathVariable String quizId,
									   	   HttpServletResponse response) 
	{
		IModel model = AppDomain.get();
		
		try
		{
			model.removeQuiz(lectureId, quizId);
			return StatusViewModel.onSuccessNoContent(response);
		}
		catch (LOSAccessDeniedException e) 
		{ 
			return StatusViewModel.onException(e, response);
		}
		catch (EntityNotFoundException e)
		{
			return StatusViewModel.onException(e, response);
		}
	}

	@RequestMapping(value = "/lecture/{lectureId}/quiz/{quizId}", method = RequestMethod.POST)
	public @ResponseBody Object controlQuiz(@PathVariable String lectureId, 
										    @PathVariable String quizId,
										    @RequestParam("active") boolean active,
										    HttpServletResponse response)
	{
		IModel model = AppDomain.get();
		
		try 
		{
			if(active) {
				model.startQuiz(lectureId, quizId);
			}
			else {
				model.endQuiz(lectureId, quizId);
			}
			
			return StatusViewModel.onSuccessNoContent(response);
		}
		catch(LOSAccessDeniedException e) {
			return StatusViewModel.onException(e, response);
		}
		catch(EntityNotFoundException e) {
			return StatusViewModel.onException(e, response);
		}
	}

	@RequestMapping(value = "/lecture/{lectureId}/quiz/{quizId}/answer", method = RequestMethod.POST)
	public @ResponseBody Object answerQuiz(@RequestBody QuizAttemptViewModel attempt,
										   @PathVariable String lectureId, 
										   @PathVariable String quizId,
										   HttpServletResponse response)
	{
		IModel model = AppDomain.get();
		
		if(attempt == null) {
			return StatusViewModel.onParamNull(response, "Attempt null");
		}
		
		if(attempt.getQuestions() == null || attempt.getQuestions().isEmpty()) {
			return StatusViewModel.onParamInvalid(response, "Questions missing");
		}
		
		for(QuestionAttemptViewModel question : attempt.getQuestions()) {
			
			if(question == null) {
				return StatusViewModel.onParamInvalid(response, "Invalid Question");
			}
			
			if(question.getQuestionId() == null || question.getQuestionId().isEmpty()) {
				return StatusViewModel.onParamInvalid(response, "Question id missing");
			}
			
			if(question.getAnswers() == null || question.getAnswers().isEmpty()) {
				return StatusViewModel.onParamInvalid(response, "\"" + question.getQuestionId() + "\": Answers missing");
			}
			
			for(String answer : question.getAnswers()) {
				if(answer == null || answer.isEmpty()) {
					return StatusViewModel.onParamInvalid(response, "\"" + question.getQuestionId() + "\": invalid Answer");
				}
			}
		}
		
		try {

			for(QuestionAttemptViewModel question : attempt.getQuestions()) 
			{
				model.submitAnswer(lectureId, quizId, question.getQuestionId(), question.getAnswers());
			}
			
			return StatusViewModel.onSuccessNoContent(response);
		}
		catch(LOSAccessDeniedException e) {
			return StatusViewModel.onException(e, response);
		}
		catch(EntityNotFoundException e) {
			return StatusViewModel.onException(e, response);
		} catch (QuizInactiveException e) {
			return StatusViewModel.onException(e, response);
		}
	}
}
