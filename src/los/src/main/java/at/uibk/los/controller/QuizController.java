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
import at.uibk.los.model.EntityNotFoundException;
import at.uibk.los.model.authorization.LOSAccessDeniedException;
import at.uibk.los.model.interfaces.ILectureView;
import at.uibk.los.model.interfaces.IModel;
import at.uibk.los.model.interfaces.IQuestion;
import at.uibk.los.model.interfaces.IQuiz;
import at.uibk.los.model.interfaces.IQuizView;
import at.uibk.los.viewmodel.AnswerViewModel;
import at.uibk.los.viewmodel.ErrorViewModel;
import at.uibk.los.viewmodel.QuestionViewModel;
import at.uibk.los.viewmodel.QuizViewModel;

@Controller
@RequestMapping("")
public class QuizController{
	
	@RequestMapping(value="/quiz/active", method = RequestMethod.GET)
	public @ResponseBody Object getActiveQuiz(HttpServletResponse response) {
		
		IModel model = AppDomain.get();
		
		try 
		{
			List<IQuizView> quizViews =  model.getActiveQuiz();
			List<QuizViewModel> quiz = new LinkedList<QuizViewModel>();
			
			for(IQuizView quizView : quizViews) {
				quiz.add(new QuizViewModel(quizView));
			}
			
			return quiz;
			
		} catch (LOSAccessDeniedException e) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return new ErrorViewModel(e);
		}
	}
	
	@RequestMapping(value="/quiz/my", method = RequestMethod.GET)
	public @ResponseBody Object getMyQuiz(HttpServletResponse response) {
		
		IModel model = AppDomain.get();
				
		try 
		{
			List<ILectureView> lectures = model.getAssociatedLectures();
			List<QuizViewModel> quiz = new LinkedList<QuizViewModel>();
			
			for(ILectureView lecture : lectures) {
				
				List<IQuizView> quizViews = lecture.getQuizView();
				
				for(IQuizView quizView : quizViews) {
					quiz.add(new QuizViewModel(quizView));
				}
			}
			
			return quiz;
			
		} catch (LOSAccessDeniedException e) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return new ErrorViewModel(e);
		}
	}
	
	@RequestMapping(value="/lecture/{id}/quiz", method = RequestMethod.PUT)
	public @ResponseBody Object addQuiz(@RequestBody QuizViewModel quizVM,
										@PathVariable String id,
									    HttpServletResponse response) {
		
		IModel model = AppDomain.get();
		
		// verify quiz
		if(quizVM == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return new ErrorViewModel("Invalid data");
		}
		
		if(quizVM.getTitle() == null || quizVM.getTitle().isEmpty()) {
			response.setStatus(ErrorViewModel.SC_UNPROCESSABLE_ENTITY);
			return new ErrorViewModel("title missing");
		}
		
		if(quizVM.getQuestions() == null || quizVM.getQuestions().isEmpty()) {
			response.setStatus(ErrorViewModel.SC_UNPROCESSABLE_ENTITY);
			return new ErrorViewModel("Questions missing");
		}

		for(QuestionViewModel question : quizVM.getQuestions()) 
		{
			if(question.getAnswers() == null || question.getAnswers().isEmpty()) {
				response.setStatus(ErrorViewModel.SC_UNPROCESSABLE_ENTITY);	
				return new ErrorViewModel("Answers missing");
			}
			
			if(question.getText() == null || question.getText().isEmpty()) {
				response.setStatus(ErrorViewModel.SC_UNPROCESSABLE_ENTITY);	
				return new ErrorViewModel("Question text missing");
			}
			
			boolean solution = false;
			for(AnswerViewModel answer : question.getAnswers()) 
			{
				if(answer.getText() == null || answer.getText().isEmpty()) {
					response.setStatus(ErrorViewModel.SC_UNPROCESSABLE_ENTITY);	
					return new ErrorViewModel("Answer text missing");
				}
				
				if(answer.isSolution()) solution = true;
			}
			
			if(!solution) {
				response.setStatus(ErrorViewModel.SC_UNPROCESSABLE_ENTITY);	
				return new ErrorViewModel("Question solution missing");
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
			
			response.setStatus(HttpServletResponse.SC_CREATED);
			
			return null;
			
		} catch (LOSAccessDeniedException e) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return new ErrorViewModel(e);
		} catch (EntityNotFoundException e) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return new ErrorViewModel(e);
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
		}
		catch (LOSAccessDeniedException e) 
		{ 
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return new ErrorViewModel(e);
		}
		catch (EntityNotFoundException e)
		{
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return new ErrorViewModel(e);
		}
		
		return null;
	}

	@RequestMapping(value = "/lecture/{lectureId}/quiz/{quizId}", method = RequestMethod.POST)
	public @ResponseBody Object controlQuiz(@PathVariable String lectureId, 
										    @PathVariable String quizId,
										    @RequestParam("active") boolean active,
										    HttpServletResponse response)
	{
		IModel model = AppDomain.get();
		
		try {
			if(active) {
				model.startQuiz(lectureId, quizId);
			}
			else {
				model.endQuiz(lectureId, quizId);
			}
			
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
		}
		catch(LOSAccessDeniedException e) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return new ErrorViewModel(e);
		}
		catch(EntityNotFoundException e) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return new ErrorViewModel(e);
		}
		
		return null;
	}
}
