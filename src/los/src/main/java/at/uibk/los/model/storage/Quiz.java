package at.uibk.los.model.storage;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;

import at.uibk.los.model.interfaces.IAnswer;
import at.uibk.los.model.interfaces.IQuestion;
import at.uibk.los.model.interfaces.IQuestionView;
import at.uibk.los.model.interfaces.IQuiz;

class Quiz implements IQuiz {

	@Id
	ObjectId id;
	
	private String title;
	
	@DBRef
	private Lecture lecture;
	
	private boolean active;
	
	@PersistenceConstructor
	public Quiz(Lecture lecture) {
		this.id = ObjectId.get();
		this.lecture = lecture;
		this.active = false;
	}
	
	@Override
	public String getId() {
		return id.toString();
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
		Repo.save(this);
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<IQuestion> getQuestions() {
		return (List<IQuestion>)(List<?>)Question.Repo.findByQuiz(this);
	}

	@Override
	public IQuestion addQuestion(String text) {
		Question question = new Question(this, text);
		Question.Repo.save(question);
		return question;
	}

	@Override
	public IQuestion getQuestion(String questionId) {
		return Question.Repo.findOne(questionId);
	}

	@Override
	public void RemoveQuestion(String questionId) {
		IQuestion question = Question.Repo.findOne(questionId);
		
		List<IAnswer> answers = question.getAnswers();
		
		if(answers != null) 
		{
			for(IAnswer answer : answers) 
			{
				question.removeAnswer(answer.getId());
			}
		}

		Question.Repo.delete(questionId);
	}

	@Override
	public void start() {
		active = true;	
		Repo.save(this);
	}

	@Override
	public void stop() {
		active = false;
		Repo.save(this);
	}

	@Override
	public boolean isActive() {
		return active;
	}
	
	static QuizRepository Repo;

	@SuppressWarnings("unchecked")
	@Override
	public List<IQuestionView> getQuestionView() {
		return (List<IQuestionView>)(List<?>) getQuestions();
	}

	@Override
	public IQuestionView getQuestionView(String questionId) {
		return getQuestion(questionId);
	}
	
	@Override
	public boolean equals(Object o) {
		
		if(this == o) return true;
		
		if(this.getClass() != o.getClass()) return false;
		
		Quiz other = (Quiz)o;
		
		return other.id.equals(id);
	}
	
	public int hashCode() {
		return id.hashCode();
	}
}
