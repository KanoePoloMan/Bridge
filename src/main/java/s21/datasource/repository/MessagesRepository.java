package s21.datasource.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import s21.datasource.messages.ChatMessageDAO;


@Repository
public interface MessagesRepository extends CrudRepository<ChatMessageDAO, Long> {
    List<ChatMessageDAO> findByChat(UUID chat);
}
