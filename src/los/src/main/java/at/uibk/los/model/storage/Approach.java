package at.uibk.los.model.storage;

import java.util.LinkedList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import at.uibk.los.model.interfaces.IAnswer;
import at.uibk.los.model.interfaces.IApproach;
import at.uibk.los.model.interfaces.IQuestion;
import at.uibk.los.model.interfaces.IScore;

class Approach implements IApproach {
	
	@Id
	ObjectId id;
	
	@DBRef
	private User user;
	
	@DBRef
	private Quiz quiz;
	
	@DBRef
	private Question question;
	
	private List<String> answers;
	
	public Approach(User user, Quiz quiz, Question question, List<String> answers) {
		this.user = user;
		this.quiz = quiz;
		this.question = question;
		this.answers = answers;
		this.id = ObjectId.get();
	}
	
	@Override
	public List<IAnswer> getAnswers() {
		
		List<IAnswer> out = new LinkedList<IAnswer>();
		for(String id : answers) {
			out.add(Answer.Repo.findOne(id));
		}
		
		return out;
	}
	
	@Override
	public IQuestion getQuestion() {
		return question;
	}
	
	static ApproachRepository Repo;

	@Override
	public IScore getScore() {
		return new Score(this);
	}
}
