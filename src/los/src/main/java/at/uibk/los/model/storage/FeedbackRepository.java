package at.uibk.los.model.storage;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

interface FeedbackRepository extends MongoRepository<Feedback, String> {
    public List<Feedback> findById(String id);
    public List<Feedback> findAll();
    public List<Feedback> findByLecture(Lecture lecture);
}