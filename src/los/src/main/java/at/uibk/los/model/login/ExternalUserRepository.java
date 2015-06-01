package at.uibk.los.model.login;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExternalUserRepository extends MongoRepository<ExternalUser, String> {
    public List<ExternalUser> findById(String id);
    public List<ExternalUser> findByMatId(String matId);
    public List<ExternalUser> findAll();
}
