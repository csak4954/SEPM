package at.uibk.los.model.storage;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

interface UserRepository extends MongoRepository<User, String> {
    public List<User> findById(String id);
    public User findByMatId(String matId);
    public List<User> findAll();
}