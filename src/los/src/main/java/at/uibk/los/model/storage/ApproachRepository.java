package at.uibk.los.model.storage;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

interface ApproachRepository extends MongoRepository<Approach, String> {
    public List<Approach> findById(String id);
    public List<Approach> findAll();
	public List<Approach> findByQuiz(Quiz quiz);
	public List<Approach> findByUser(User user);
	public Approach findByUserAndQuestion(User user, Question question);
}