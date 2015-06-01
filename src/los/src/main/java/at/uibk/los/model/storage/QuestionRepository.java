package at.uibk.los.model.storage;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

interface QuestionRepository extends MongoRepository<Question, String> {
    public List<Question> findById(String id);
    public List<Question> findByQuiz(Quiz quiz);
    public List<Question> findAll();
}