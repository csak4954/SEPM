package at.uibk.los.model.storage;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

interface AdministrationRepository extends MongoRepository<Administration, String> {
    public List<Administration> findById(String id);
    public List<Administration> findByLecture(Lecture lecture);
    public List<Administration> findByUser(User user);
    public Administration findByUserAndLecture(User user, Lecture lecture);
    public List<Administration> findAll();
}