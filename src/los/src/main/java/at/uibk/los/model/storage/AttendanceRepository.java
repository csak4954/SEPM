package at.uibk.los.model.storage;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

interface AttendanceRepository extends MongoRepository<Attendance, String> {
    public List<Attendance> findById(String id);
    public List<Attendance> findAll();
    public List<Attendance> findByLecture(Lecture lecture);
    public List<Attendance> findByUser(User user);
    public Attendance findByUserAndLecture(User user, Lecture lecture);
}