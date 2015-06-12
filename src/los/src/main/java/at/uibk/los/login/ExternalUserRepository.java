package at.uibk.los.login;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

interface ExternalUserRepository extends MongoRepository<ExternalUser, String> {
    public ExternalUser findById(String id);
    public ExternalUser findByIdAndPassword(String id, String password);
    public List<ExternalUser> findAll();
}
