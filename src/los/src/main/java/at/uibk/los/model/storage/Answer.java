package at.uibk.los.model.storage;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;

import at.uibk.los.model.interfaces.IAnswer;

class Answer implements IAnswer {

	@Id
	private ObjectId id;
	
	@DBRef
	private Question question;
	
	private String text;
	private boolean isSolution;
		
	private int rightPointCount;
	private int wrongPointCount;
	
	public Answer(Question question, String text, boolean isSolution) {
		this.question = question;
		this.text = text;
		this.isSolution = isSolution;
		this.id = ObjectId.get();
		this.rightPointCount = 1;
		this.wrongPointCount = 1;
	}
	
	/*@PersistenceConstructor
	public Answer(Question question, String text, boolean isSolution, int rightPoints, int wrongPoints) {
		this.question = question;
		this.text = text;
		this.isSolution = isSolution;
		this.id = ObjectId.get();
		this.rightPointCount = rightPoints;
		this.wrongPointCount = wrongPoints;
	}*/
	
	@Override
	public String getId() {
		return id.toString();
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public boolean isSolution() {
		return isSolution;
	}

	static AnswerRepository Repo;

	@Override
	public void setText(String text) {
		this.text = text;		
	}

	@Override
	public void setIsSolution(boolean solution) {
		this.isSolution = solution;
	}

	@Override
	public int getRightPointCount() {
		// TODO Auto-generated method stub
		return rightPointCount;
	}

	@Override
	public int getWrongPointCount() {
		return wrongPointCount;
	}

	@Override
	public void setRightPointCount(int count) {
		this.rightPointCount = count;
	}

	@Override
	public void setWrongPointCount(int count) {
		this.wrongPointCount = count;
	}
}
