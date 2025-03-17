package s21.datasource.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import s21.datasource.ServerMembersDAO;

@Repository
public interface ServerMembersRepository extends CrudRepository<ServerMembersDAO, Integer> {
    public List<ServerMembersDAO> findByUser(UUID user);
}
