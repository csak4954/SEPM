package at.uibk.los.model.storage;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

class ActiveQuiz {
	
	@Id
	private ObjectId id;
	
	@DBRef
	private Quiz quiz;
	
	public ActiveQuiz(Quiz quiz) {
		this.id = ObjectId.get();
		this.quiz = quiz;
	}
}
