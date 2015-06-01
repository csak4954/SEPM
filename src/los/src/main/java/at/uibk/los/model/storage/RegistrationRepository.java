package at.uibk.los.model.storage;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

interface RegistrationRepository extends MongoRepository<Registration, String> {
    public List<Registration> findById(String id);
    public List<Registration> findAll();
    public List<Registration> findByLecture(Lecture lecture);
    public List<Registration> findByUser(User user);
    public Registration findByUserAndLecture(User user, Lecture lecture);
}