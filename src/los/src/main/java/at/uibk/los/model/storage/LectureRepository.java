package at.uibk.los.model.storage;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import at.uibk.los.model.interfaces.ILecture;

interface LectureRepository extends MongoRepository<Lecture, String> {
    public ILecture findByTitle(String title);
    public List<Lecture> findById(String id);
    public List<Lecture> findAll();
}