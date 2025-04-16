package s21.datasource.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import s21.datasource.model.friends.FriendsDAO;


@Repository
public interface FriendsRepository extends CrudRepository<FriendsDAO, Integer> {
    List<FriendsDAO> findByUuid(UUID uuid);
    List<FriendsDAO> findByFriend(UUID friend);
    @Query(value="SELECT * FROM friends WHERE friend_uuid = :uuid AND state = 'INVITE'",
           nativeQuery=true)
    List<FriendsDAO> getInvites(@Param("uuid") UUID uuid);
    @Query(nativeQuery=true,
           value="SELECT * FROM friends WHERE (user_uuid = :uuid  OR friend_uuid = :uuid) AND state = 'FRIEND'")
    List<FriendsDAO> getFriends(@Param("uuid") UUID uuid);
    @Transactional
    @Modifying
    @Query(nativeQuery=true,
           value="UPDATE friends SET state = :newState WHERE friend_uuid = :uuid AND user_uuid = :friendUUID AND state = 'INVITE'")
    void updateFriendInvite(@Param("uuid") UUID uuid, 
                            @Param("friendUUID") UUID friendUUID, 
                            @Param("newState") String newState);
//     void addUserToBlacklist();
    @Query(nativeQuery=true,
           value="SELECT * FROM friends WHERE state = 'FRIEND' " + 
           "AND ((user_uuid = :uuid AND friend_uuid = :friendUUID) " + 
           "OR (user_uuid = :friendUUID AND friend_uuid = :uuid))")
    FriendsDAO getFriendRecord(@Param("uuid") UUID uuid, @Param("friendUUID") UUID friendUUID);

    @Transactional
    @Modifying
    @Query(nativeQuery=true,
           value="UPDATE friends SET chat_uuid = :chat_uuid " + 
           "WHERE state = 'FRIEND' " + 
           "AND ((user_uuid = :uuid AND friend_uuid = :friendUUID) " + 
           "OR (user_uuid = :friendUUID AND friend_uuid = :uuid))")
    void setChat(@Param("uuid") UUID uuid, 
                 @Param("driendUUID") UUID friendUUID, 
                 @Param("chat_uuid") UUID chatUUID);
}