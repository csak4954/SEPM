package at.uibk.los.model.storage;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

interface ActiveQuizRepository extends MongoRepository<Answer, String> {
    public List<Answer> findById(String id);
    public List<Answer> findByQuestion(Question question);
    public List<Answer> findAll();
}