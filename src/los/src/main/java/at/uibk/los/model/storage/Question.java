package at.uibk.los.model.storage;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import at.uibk.los.model.interfaces.IAnswer;
import at.uibk.los.model.interfaces.IAnswerView;
import at.uibk.los.model.interfaces.IQuestion;
import at.uibk.los.model.interfaces.IQuizView;

class Question implements IQuestion {

	@Id
	ObjectId id;
	
	private String text;
	
	@DBRef 
	private Quiz quiz;
		
	public Question(Quiz quiz, String text) {
		this.quiz = quiz;
		this.id = ObjectId.get();
		setText(text);
	}
	
	@Override
	public String getId() {
		return id.toString();
	}

	@Override
	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String getText() {
		return text;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IAnswer> getAnswers() {
		return (List<IAnswer>)(List<?>)Answer.Repo.findByQuestion(this);
	}

	@Override
	public IQuestion addAnswer(String text, boolean solution) {
		Answer.Repo.save(new Answer(this, text, solution));
		return this;
	}

	@Override
	public void addApproach(String userId, List<String> answerIds) {
		
		User user = User.Repo.findById(userId);
				
		Approach approach = Approach.Repo.findByUserAndQuestion(user, this);
		if(approach == null) {
			approach = new Approach(user, quiz, this, answerIds);
		} 
		else
		{
			approach.setAnswers(answerIds);
		}
	
		Approach.Repo.save(approach);
	}
	
	@Override
	public void removeAnswer(String answerId) {
		Answer.Repo.delete(answerId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IAnswerView> getAnswerView() {
		return (List<IAnswerView>)(List<?>)getAnswers();
	}

	@Override
	public IQuizView getQuizView() {
		return this.quiz;
	}
	
	@Override
	public boolean equals(Object o) {
		
		if(this == o) return true;
		
		if(this.getClass() != o.getClass()) return false;
		
		Question other = (Question)o;
		
		return this.id.equals(other.id);
	}
	
	public int hashCode() {
		return id.hashCode();
	}
	
	static QuestionRepository Repo;


}
