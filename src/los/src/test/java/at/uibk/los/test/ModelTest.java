package at.uibk.los.test;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import at.uibk.los.AppDomain;
import at.uibk.los.model.authorization.LOSAccessDeniedException;
import at.uibk.los.model.authorization.StaffGroupPolicy;
import at.uibk.los.model.authorization.StudentGroupPolicy;
import at.uibk.los.model.exceptions.QuizInactiveException;
import at.uibk.los.model.interfaces.IAnswerView;
import at.uibk.los.model.interfaces.IDay;
import at.uibk.los.model.interfaces.IFeedback;
import at.uibk.los.model.interfaces.ILectureView;
import at.uibk.los.model.interfaces.IModel;
import at.uibk.los.model.interfaces.IQuestionView;
import at.uibk.los.model.interfaces.IQuiz;
import at.uibk.los.model.interfaces.IQuizResult;
import at.uibk.los.model.interfaces.IQuizView;
import at.uibk.los.model.interfaces.IStatistics;
import at.uibk.los.model.interfaces.IUser;
import at.uibk.los.test.config.TestAppConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestAppConfig.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ModelTest
{	
	private static IModel testAsStudent() throws Exception 
	{	
		final String username = "csaq5126";
		final String password = generateHash("secret");	
		
		Assert.assertTrue(AppDomain.get().getServiceProvider().getLoginProvider().login(username, password));

		return AppDomain.get();
	}
	
	private static IModel testAsAnotherStudent() throws Exception
	{
		final String username = "csaq5244";
		final String password = generateHash("secret");	
		
		Assert.assertTrue(AppDomain.get().getServiceProvider().getLoginProvider().login(username, password));

		return AppDomain.get();
	}
	
	private static IModel testAsStaff() throws Exception 
	{		
		final String username = "12345";
		final String password = generateHash("secret");	
		
		Assert.assertTrue(AppDomain.get().getServiceProvider().getLoginProvider().login(username, password));

		return AppDomain.get();
	}
	
	@Test
	public void t1_permissions() throws Exception 
	{		
		final String title = "";
		final String description = "";
		final String lectureId = "";
		final String quizId = "";
		final String userId = "";
		final String quizTitle = "";
		
		IModel model = testAsStudent();
		
		Assert.assertEquals(model.getUser().getGroupPolicy(), StudentGroupPolicy.id);
		
		try 
		{
			model.addLecture(title, description);
			Assert.fail();
		} catch(LOSAccessDeniedException e) {	}
		
		try 
		{
			model.addAdmin(lectureId);
			Assert.fail();
		} catch(LOSAccessDeniedException e) {	}
		
		try 
		{
			model.createQuiz(lectureId, quizTitle);
			Assert.fail();
		} catch(LOSAccessDeniedException e) {	}
	
		try 
		{
			model.startQuiz(lectureId, quizId);
			Assert.fail();
		} catch(LOSAccessDeniedException e) {	}
		
		try 
		{
			model.endQuiz(lectureId, quizId);
			Assert.fail();
		} catch(LOSAccessDeniedException e) {	}
		
		try 
		{
			model.renewAttendanceVerification(lectureId);
			Assert.fail();
		} catch(LOSAccessDeniedException e) { 	}
		
		try 
		{
			model.endAttendanceVerification(lectureId);
			Assert.fail();
		} catch(LOSAccessDeniedException e) {	}
		
		try 
		{
			model.getFeedback(lectureId);
			Assert.fail();
		} catch(LOSAccessDeniedException e) {	}
		
		try 
		{
			model.getQuizResults(userId);
			Assert.fail();
		} catch(LOSAccessDeniedException e) {	}
		
		try 
		{
			model.getScores(userId);
			Assert.fail();
		} catch(LOSAccessDeniedException e) {	}
		
		try 
		{
			model.getStatistics(lectureId);
			Assert.fail();
		} catch(LOSAccessDeniedException e) {	}
	}
	
	@Test
	public void t2_lectureAdd() throws Exception 
	{
		for(int i = 0; i < 1; i++) 
		{
			IModel model = testAsStaff();
			
			Assert.assertEquals(model.getUser().getGroupPolicy(), StaffGroupPolicy.id);
		
			final String title = "LV 1105-2";
			final String description = "description";
			
			Assert.assertTrue(model.getAvailableLectures().isEmpty());
			
			// add lecture
			final String lectureId = model.addLecture(title, description).getId();	
			
			model.addAdmin(lectureId);
			
			// verify lecture count
			List<ILectureView> lectures = model.getAvailableLectures();
			Assert.assertEquals(1, lectures.size());	
			
			ILectureView lecture = lectures.get(0);
			
			// verify lecture
			Assert.assertEquals(lecture.getId(), lectureId);
			Assert.assertEquals(lecture.getTitle(), title);
			Assert.assertEquals(lecture.getDescription(), description);
			
			// delete lecture
			model.removeLecture(lectureId);
			Assert.assertTrue(model.getAvailableLectures().isEmpty());
		}
	}
	
	@Test
	public void t3_quizAdd() throws Exception
	{
		IModel model = testAsStaff();
		
		final String title = "LV 1105-1";
		final String description = "description";
		
		Assert.assertTrue(model.getAvailableLectures().isEmpty());
		
		// create lecture
		ILectureView lecture = model.addLecture(title, description);
		
		model.addAdmin(lecture.getId());
		
		final String quizTitle = "example quiz";
		final String questionText = "what is the best source of fun?";
		final String answerA = "9gag";
		final boolean answerASolution = true;
		final String answerB = "facebook";
		final boolean answerBSolution = false;
		
		// create quiz
		IQuiz quiz = model.createQuiz(lecture.getId(), quizTitle);
		quiz.addQuestion(questionText)
			.addAnswer(answerA, answerASolution)
			.addAnswer(answerB, answerBSolution);
						
		List<ILectureView> lectures = model.getAssociatedLectures();
		Assert.assertEquals(1, lectures.size());

		lecture = lectures.get(0);
				
		List<IQuizView> quizList = lecture.getQuizView();
		Assert.assertEquals(1, quizList.size());
		
		// verify quiz
		
		// get quiz
		IQuizView quizView = quizList.iterator().next();
		Assert.assertEquals(quizTitle, quiz.getTitle());
		
		// get question
		List<IQuestionView> questions = quizView.getQuestionView();
		Assert.assertEquals(1, questions.size());
		
		// verify question
		IQuestionView questionViews = questions.get(0);
		Assert.assertEquals(questionText, questionViews.getText());
		
		// get answers
		List<IAnswerView> answers = questionViews.getAnswerView();
		Assert.assertEquals(2, answers.size());
		
		// verify answers
		IAnswerView answer = answers.get(0);
		Assert.assertEquals(answerA, answer.getText());
		
		answer = answers.get(1);
		Assert.assertEquals(answerB, answer.getText());
				
		// delete lecture recursive
		model.removeLecture(lecture.getId());
		
		Assert.assertTrue(model.getAvailableLectures().isEmpty());
	}
	
	@Test
	public void t4_quizActivation() throws Exception
	{
		IModel model = testAsStaff();
		
		final String title = "LV 1105-2";
		final String description = "description";
		
		Assert.assertTrue(model.getAvailableLectures().isEmpty());
		
		// add lecture
		ILectureView lecture = model.addLecture(title, description);	
		
		model.addAdmin(lecture.getId());
		
		final String key = model.renewAttendanceVerification(lecture.getId());
		
		// create quiz
		final String quizTitle = "example quiz";
		final String questionText = "what is the best source of fun?";
		final String answerA = "9gag";
		final boolean answerASolution = true;
		final String answerB = "facebook";
		final boolean answerBSolution = false;
		
		IQuiz quiz = model.createQuiz(lecture.getId(), quizTitle);
		quiz.addQuestion(questionText)
			.addAnswer(answerA, answerASolution)
			.addAnswer(answerB, answerBSolution);
						
		testAsStudent();
		
		model.confirmAttendance(lecture.getId(), key);
		
		List<IQuizView> quizList = model.getActiveQuiz();
		Assert.assertTrue(quizList.isEmpty());
		
		testAsStaff();
				
		model.startQuiz(lecture.getId(), quiz.getId());
		
		testAsStudent();
		
		quizList = model.getActiveQuiz();
		Assert.assertEquals(1, quizList.size());
		
		testAsStaff();
		
		model.endQuiz(lecture.getId(), quiz.getId());
		
		testAsStudent();
		
		quizList = model.getActiveQuiz();
		Assert.assertTrue(quizList.isEmpty());
		
		testAsStaff();
		
		model.removeLecture(lecture.getId());
	}
	
	@Test
	public void t5_quizAttempt() throws Exception {
		
		IModel model = testAsStaff();
		
		final String title = "LV 1105-2";
		final String description = "description";
		
		Assert.assertTrue(model.getAvailableLectures().isEmpty());
		
		// add lecture
		ILectureView lecture = model.addLecture(title, description);	
		
		model.addAdmin(lecture.getId());
		
		final String key = model.renewAttendanceVerification(lecture.getId());
		
		// create quiz
		final String quizTitle = "example quiz";
		final String questionText = "what is the best source of fun?";
		final String answerA = "9gag";
		final boolean answerASolution = true;
		final String answerB = "facebook";
		final boolean answerBSolution = false;
		
		IQuiz quiz = model.createQuiz(lecture.getId(), quizTitle);
		quiz.addQuestion(questionText)
			.addAnswer(answerA, answerASolution)
			.addAnswer(answerB, answerBSolution);
						
		model.startQuiz(lecture.getId(), quiz.getId());
	
		testAsStudent();
				
		model.confirmAttendance(lecture.getId(), key);
		
		List<IQuizView> quizList = model.getActiveQuiz();
		Assert.assertEquals(1, quizList.size());
		
		IQuizView quizView = quizList.get(0);
		IQuestionView questionView = quizView.getQuestionView().get(0);
		
		List<String> answers = new LinkedList<String>();
		List<IAnswerView> answersView = questionView.getAnswerView();
		
		answers.add(answersView.get(0).getId());
		
		model.submitAnswer(lecture.getId(), quiz.getId(), questionView.getId(), answers);
		
		List<IQuizResult> scores = model.getQuizResults();
		Assert.assertTrue(scores.isEmpty());
		
		testAsStaff();
		
		model.endQuiz(lecture.getId(), quiz.getId());

		testAsStudent();
		
		answers.add(answersView.get(1).getId());
		
		try
		{
			model.submitAnswer(lecture.getId(), quiz.getId(), questionView.getId(), answers);
			Assert.fail();
		}
		catch(QuizInactiveException e)
		{
			
		}
		
		scores = model.getQuizResults();
		
		Assert.assertFalse(scores.isEmpty());
		Assert.assertFalse(scores.get(0).getQuestionScores().isEmpty());

		Assert.assertEquals(100, scores.get(0).getScore(), 0.001);
		Assert.assertEquals(100, scores.get(0).getQuestionScores().get(0).getScore(), 0.001);
		
		IUser user = model.getUser();
		
		testAsStaff();
		
		scores = model.getQuizResults(user.getId());
		
		Assert.assertFalse(scores.isEmpty());
		Assert.assertFalse(scores.get(0).getQuestionScores().isEmpty());

		Assert.assertEquals(100, scores.get(0).getScore(), 0.001);
		Assert.assertEquals(100, scores.get(0).getQuestionScores().get(0).getScore(), 0.001);
		
		model.removeLecture(lecture.getId());
	}

	@Test
	public void t6_feedback() throws Exception
	{
		IModel model = testAsStaff();
		
		final String title = "LV 1105-2";
		final String description = "description";
		
		Assert.assertTrue(model.getAvailableLectures().isEmpty());
		
		// add lecture
		ILectureView lecture = model.addLecture(title, description);	
		
		model.addAdmin(lecture.getId());
				
		final String key = model.renewAttendanceVerification(lecture.getId());
		
		testAsStudent();
			
		List<ILectureView> lectures = model.getAvailableLectures();
		Assert.assertEquals(lectures.size(), 1);
		lecture = lectures.get(0);

		model.confirmAttendance(lecture.getId(), key);
		
		lectures = model.getAssociatedLectures();
		Assert.assertEquals(1, lectures.size());
		lecture = lectures.get(0);
		
		// add feedback
		final int rating = 50;
		final String msg = "Booooring";
		
		model.submitFeedback(lecture.getId(), rating, msg);
		
		final String lectureId = lecture.getId();
		
		testAsStaff();
		
		// verify
		model.addAdmin(lectureId);
		
		List<IFeedback> feedback = model.getFeedback(lectureId);	
		Assert.assertEquals(1, feedback.size());
		
		IFeedback fb = feedback.get(0);
		Assert.assertEquals(rating, fb.getRating());
		Assert.assertEquals(msg, fb.getMessage());
		
		model.removeLecture(lecture.getId());
	}
	
	@Test
	public void t7_statistics() throws Exception {
		
		IModel model = testAsStaff();
		
		final String title = "LV 1105-2";
		final String description = "description";
		
		Assert.assertTrue(model.getAvailableLectures().isEmpty());
		
		// add lecture
		ILectureView lecture = model.addLecture(title, description);	
		
		model.addAdmin(lecture.getId());
		
		final String key = model.renewAttendanceVerification(lecture.getId());
		
		// create quiz
		final String quizTitle = "example quiz";
		final String questionText = "what is the best source of fun?";
		final String answerA = "9gag";
		final boolean answerASolution = true;
		final String answerB = "facebook";
		final boolean answerBSolution = false;
		
		IQuiz quiz = model.createQuiz(lecture.getId(), quizTitle);
		quiz.addQuestion(questionText)
			.addAnswer(answerA, answerASolution)
			.addAnswer(answerB, answerBSolution);
						
		model.startQuiz(lecture.getId(), quiz.getId());
	
		// continue as student
		testAsStudent();
				
		// confirm attendance
		model.confirmAttendance(lecture.getId(), key);
		
		// submit answers
		List<IQuizView> quizList = model.getActiveQuiz();
		Assert.assertEquals(1, quizList.size());
		
		IQuizView quizView = quizList.get(0);
		IQuestionView questionView = quizView.getQuestionView().get(0);
		
		List<String> answers = new LinkedList<String>();
		List<IAnswerView> answersView = questionView.getAnswerView();
		
		answers.add(answersView.get(0).getId());
		
		model.submitAnswer(lecture.getId(), quiz.getId(), questionView.getId(), answers);
		
		// continue as another student
		testAsAnotherStudent();
				
		// confirm attendance
		model.confirmAttendance(lecture.getId(), key);
		
		// submit answers
		quizList = model.getActiveQuiz();
		Assert.assertEquals(1, quizList.size());
		
		quizView = quizList.get(0);
		questionView = quizView.getQuestionView().get(0);
		
		answers = new LinkedList<String>();
		answersView = questionView.getAnswerView();
		
		answers.add(answersView.get(1).getId());
		
		model.submitAnswer(lecture.getId(), quiz.getId(), questionView.getId(), answers);
		
		// continue as staff
		testAsStaff();
		
		model.endQuiz(lecture.getId(), quiz.getId());

		// verify statistics
		IStatistics statistics = model.getStatistics(lecture.getId());
		
		Assert.assertEquals(2, statistics.getNumRegistratrions());

		Map<IDay, Double> attendanceRates = statistics.getAttendancePerDay();
		Assert.assertFalse(attendanceRates.isEmpty());
		Double rate = attendanceRates.entrySet().iterator().next().getValue();
		Assert.assertEquals(100, rate, 0.001);
		
		Map<IQuizView, Double> averageQuizScore = statistics.getQuizAverageScore();
		Assert.assertFalse(averageQuizScore.isEmpty());
		rate = averageQuizScore.entrySet().iterator().next().getValue();
		Assert.assertEquals(50, rate, 0.001);
		
		model.removeLecture(lecture.getId());
	}
	
	public static String generateHash(String pwd) throws NoSuchAlgorithmException, UnsupportedEncodingException 
	{
	    MessageDigest md = null;
	    byte[] hash = null;
        md = MessageDigest.getInstance("SHA-512");
        hash = md.digest(pwd.getBytes("UTF-8"));
	     
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            sb.append(Integer.toString((hash[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
	}
}



































