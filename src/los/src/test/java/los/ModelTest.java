package los;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;

import los.config.UnitTestAppConfig;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import at.uibk.los.model.authorization.LOSAccessDeniedException;
import at.uibk.los.model.authorization.StaffGroupPolicy;
import at.uibk.los.model.authorization.StudentGroupPolicy;
import at.uibk.los.model.interfaces.IAnswerView;
import at.uibk.los.model.interfaces.IDataEvaluation;
import at.uibk.los.model.interfaces.IDataManipulation;
import at.uibk.los.model.interfaces.IDataStorage;
import at.uibk.los.model.interfaces.IFeedback;
import at.uibk.los.model.interfaces.ILectureView;
import at.uibk.los.model.interfaces.ILoginProvider;
import at.uibk.los.model.interfaces.IModel;
import at.uibk.los.model.interfaces.IPolicyManager;
import at.uibk.los.model.interfaces.IQuestionView;
import at.uibk.los.model.interfaces.IQuiz;
import at.uibk.los.model.interfaces.IQuizView;
import at.uibk.los.model.interfaces.IScore;
import at.uibk.los.model.interfaces.IServiceProvider;
import at.uibk.los.model.interfaces.IUser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = UnitTestAppConfig.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ModelTest
{	
	private static ILoginProvider loginProvider;
	
	@BeforeClass
	public static void initialize() {
		
		loginProvider = mock(ILoginProvider.class);
		when(loginProvider.isNew()).thenReturn(true);
		when(loginProvider.getUser()).thenReturn(null);
		
		TestAppDomain.provider = new IServiceProvider()
		{
			IServiceProvider provider = null;

			private void ensureProviderState() {
				if(provider == null) provider = new ServiceProvider();
			}
			
			@Override
			public IDataEvaluation getEvaluation() {
				ensureProviderState();
				return provider.getEvaluation();
			}

			@Override
			public IDataManipulation getManipulation() {
				ensureProviderState();
				return provider.getManipulation();
			}

			@Override
			public IDataStorage getStorage() {
				ensureProviderState();
				return provider.getStorage();
			}

			@Override
			public ILoginProvider getLoginProvider() {
				ensureProviderState();
				return loginProvider;
			}

			@Override
			public IPolicyManager getPolicyManager() {
				ensureProviderState();
				return provider.getPolicyManager();
			}
		};	
	}
	
	private static IModel testAsStudent() throws Exception 
	{	
		TestAppDomain.get();
		
		IUser user = mock(IUser.class);
		when(user.getName()).thenReturn("florian");
		when(user.getSurname()).thenReturn("tischler");
		when(user.getEmail()).thenReturn("florian.tischler@outlook.com");
		when(user.getId()).thenReturn("123456788");
		when(user.getAffilation()).thenReturn("student");
		when(user.getGroupPolicy()).thenReturn(StudentGroupPolicy.id);
		
		when(loginProvider.getUser()).thenReturn(user);
		
		return TestAppDomain.get();
	}
	
	private static IModel testAsStaff() throws Exception 
	{		
		TestAppDomain.get();
		
		IUser user = mock(IUser.class);
		when(user.getName()).thenReturn("florian");
		when(user.getSurname()).thenReturn("tischler");
		when(user.getEmail()).thenReturn("florian.tischler@outlook.com");
		when(user.getId()).thenReturn("123456789");
		when(user.getAffilation()).thenReturn("staff");
		when(user.getGroupPolicy()).thenReturn(StaffGroupPolicy.id);
		
		when(loginProvider.getUser()).thenReturn(user);
				
		return TestAppDomain.get();
	}

	@Test(expected = LOSAccessDeniedException.class)
	public void t1_addLectureStudent() throws Exception 
	{
		IModel model = testAsStudent();
		
		Assert.assertEquals(model.getUser().getGroupPolicy(), StudentGroupPolicy.id);
		
		final String title = "LV 1105-1";
		final String description = "description";
		
		model.addLecture(title, description);
	}
	
	@Test
	public void t2_addLectureStaff() throws Exception 
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
	public void t3_addQuiz() throws Exception
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
		IQuiz quiz = model.createQuiz(lecture.getId());
		quiz.setTitle(quizTitle);
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
	public void t4_testActiveQuiz() throws Exception
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
		
		IQuiz quiz = model.createQuiz(lecture.getId());
		quiz.setTitle(quizTitle);
		quiz.addQuestion(questionText)
			.addAnswer(answerA, answerASolution)
			.addAnswer(answerB, answerBSolution);
						
		testAsStudent();
		
		model.confirmAttendance(lecture.getId(), key);
		
		List<IQuizView> quizList = model.getActiveQuiz();
		Assert.assertTrue(quizList.isEmpty());
		
		testAsStaff();
				
		model.startQuiz(lecture.getId(), quiz.getId());
		
		model = testAsStudent();
		
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
	public void t5_addFeedback() throws Exception
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
	public void t6_submitAnswer() throws Exception {
		
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
		
		IQuiz quiz = model.createQuiz(lecture.getId());
		quiz.setTitle(quizTitle);
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
		
		testAsStaff();
		
		model.endQuiz(lecture.getId(), quiz.getId());

		testAsStudent();
		
		answers.add(answersView.get(1).getId());
		
		try
		{
			model.submitAnswer(lecture.getId(), quiz.getId(), questionView.getId(), answers);
			Assert.fail();
		}
		catch(IllegalStateException e)
		{
			
		}
		
		List<IScore> scores = model.getScores();
		Assert.assertFalse(scores.isEmpty());
		
		IScore score = scores.get(0);
		Assert.assertEquals(100, score.getScore(), 0.001);
		
		IUser user = model.getUser();
		
		testAsStaff();
		
		scores = model.getScores(user.getId());
		Assert.assertFalse(scores.isEmpty());
		
		score = scores.get(0);
		Assert.assertEquals(100, score.getScore(), 0.001);
		
		model.removeLecture(lecture.getId());
	}
}



































