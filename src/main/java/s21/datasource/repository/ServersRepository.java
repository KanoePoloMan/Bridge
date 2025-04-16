package s21.datasource.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import s21.datasource.model.servers.ServerDAO;

@Repository
public interface ServersRepository extends CrudRepository<ServerDAO, UUID> {
    Optional<ServerDAO> findByUuid(UUID uuid);
}
