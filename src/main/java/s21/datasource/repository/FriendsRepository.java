package s21.datasource.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import s21.datasource.FriendsDAO;


@Repository
public interface FriendsRepository extends CrudRepository<FriendsDAO, Integer> {
    List<FriendsDAO> findByUuid(UUID uuid);
    List<FriendsDAO> findByFriend(UUID friend);
    @Query(value="SELECT * FROM friends WHERE friend_uuid = :uuid AND state = 'INVITE'",
           nativeQuery=true)
    List<FriendsDAO> getInvites(@Param("uuid") UUID uuid);
    @Query(nativeQuery=true,
           value="SELECT * FROM friends WHERE uuid = :uuid AND state = 'FRIEND'")
    List<FriendsDAO> getFriends(@Param("uuid") UUID uuid);
    @Query(nativeQuery=true,
           value="UPDATE friends SET state = :newState WHERE friendUUID = :uuid AND uuid = :friendUUID")
    void updateFriendInvite(@Param("uuid") UUID uuid, 
                            @Param("friendUUID") UUID friendUUID, 
                            @Param("newState") String newState);
}
