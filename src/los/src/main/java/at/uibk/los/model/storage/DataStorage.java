package at.uibk.los.model.storage;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import at.uibk.los.model.interfaces.IApproach;
import at.uibk.los.model.interfaces.IDataStorage;
import at.uibk.los.model.interfaces.ILecture;
import at.uibk.los.model.interfaces.IQuiz;
import at.uibk.los.model.interfaces.IUser;

@Component
public class DataStorage implements IDataStorage {

	public DataStorage() {
				
	}
	
	@Override
	public void addLecture(ILecture lecture) {
		lectures.save(new Lecture(lecture));
	}

	@Override
	public void removeLecture(String lectureId) {
	
		Lecture lecture = lectures.findOne(lectureId);

		List<IQuiz> quiz = lecture.getQuiz();
	
		if(quiz != null) {
			
			for(IQuiz q : quiz) {
				lecture.removeQuiz(q.getId());
			}
			
		}
		
		List<Registration> registrations = Registration.Repo.findByLecture(lecture);
		if(registrations != null) {
			Registration.Repo.delete(registrations);
		}
		
		List<Administration> admins = Administration.Repo.findByLecture(lecture);
		if(admins != null) {
			Administration.Repo.delete(admins);
		}
		
		List<Attendance> attendances = Attendance.Repo.findByLecture(lecture);
		if(attendances != null) {
			Attendance.Repo.delete(attendances);
		}
		
		List<Feedback> feedback = Feedback.Repo.findByLecture(lecture);
		if(feedback != null) {
			Feedback.Repo.delete(feedback);
		}
				
		lectures.delete(lectureId);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<ILecture> getLectures() {
		
		List<ILecture> lectures = (List<ILecture>)(List<?>)Lecture.Repo.findAll();
		
		if(lectures == null) {
			lectures = new LinkedList<ILecture>();
		}
		
		return lectures;
	}

	@Override
	public ILecture getLecture(String id) {
		return lectures.findOne(id);
	}

	@Override
	public List<ILecture> getLecturesForUser(String userId) {
		
		User user = User.Repo.findById(userId);
		
		List<Registration> registrations = Registration.Repo.findByUser(user);

		Set<ILecture> lectures = new HashSet<ILecture>();
		if(registrations != null)
		{
			for(Registration registration : registrations)
			{
				lectures.add(registration.getLecture());
			}
		}
		
		List<Administration> administrations = Administration.Repo.findByUser(user);

		if(administrations != null)
		{
			for(Administration administration : administrations)
			{
				lectures.add(administration.getLecture());
			}
		}
		
		return new LinkedList<ILecture>(lectures);
	}

	@Override
	public void saveUser(IUser user)
	{
		User tmp = User.Repo.findById(user.getId());
		if(tmp == null) {
			tmp = new User();
		}

		tmp.copy(user);
		User.Repo.save(tmp);
	}
	
	@Override
	public ILecture createLecture() {
		return new Lecture(); 
	}
	
	@Override
	public IQuiz getQuiz(String quizId) {
		return Quiz.Repo.findOne(quizId);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<IApproach> getApproaches(String userId) {
		
		User user = User.Repo.findById(userId);
		
		List<IApproach> approaches = (List<IApproach>)(List<?>)Approach.Repo.findByUser(user);
	
		if(approaches == null) {
			approaches = new LinkedList<IApproach>();
		}
		
		return approaches;
	}
	
	@Autowired
	private LectureRepository lectures;
	
	@Autowired
	private QuizRepository quiz;
	
	@Autowired
	private QuestionRepository questions;
	
	@Autowired
	private AnswerRepository answers;
	
	@Autowired
	private ApproachRepository approaches;
	
	@Autowired
	private UserRepository users;
		
	@Autowired
	private FeedbackRepository feedback;
		
	@Autowired 
	private RegistrationRepository registrations;
	
	@Autowired
	private AdministrationRepository admins;
	
	@Autowired
	private AttendanceRepository attendances;
	
	public static DataStorage loadFromContext(ApplicationContext ctx) {
		
		DataStorage instance = ctx.getBean(DataStorage.class);
		
		Lecture.Repo = instance.lectures;
		Quiz.Repo = instance.quiz;
		Question.Repo = instance.questions;
		Answer.Repo = instance.answers;
		Approach.Repo = instance.approaches;
		User.Repo = instance.users;
		Feedback.Repo = instance.feedback;
		Administration.Repo = instance.admins;
		Attendance.Repo = instance.attendances;
		Registration.Repo = instance.registrations;
		
		return instance;
	}
}
