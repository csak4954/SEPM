package at.uibk.los.model.storage;

import java.util.LinkedList;
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
		
		User user = User.Repo.findByMatId(userId);
		List<Answer> answers = new LinkedList<Answer>();
						
		Approach approach = new Approach(user, quiz, this, answerIds);
		Approach.Repo.save(approach);
	}
	
	
	static QuestionRepository Repo;

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
}
