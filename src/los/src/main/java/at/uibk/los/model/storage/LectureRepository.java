package at.uibk.los.model.storage;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import at.uibk.los.model.interfaces.ILecture;

public interface LectureRepository extends MongoRepository<Lecture, Integer> {
    public ILecture findByTitle(String title);
    public List<Lecture> findById(Integer id);
    public List<Lecture> findAll();
}