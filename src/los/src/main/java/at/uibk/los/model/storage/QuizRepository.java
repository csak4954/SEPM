package at.uibk.los.model.storage;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

interface QuizRepository extends MongoRepository<Quiz, String> {
    public List<Quiz> findById(String id);
    public List<Quiz> findByLecture(Lecture lecture);
    public List<Quiz> findAll();
}