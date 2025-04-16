package s21.domain.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import s21.datasource.model.exceptions.ChatException;
import s21.datasource.model.exceptions.FriendException;
import s21.datasource.model.friends.FriendsDAO;
import s21.datasource.model.friends.FriendshipStates;
import s21.datasource.repository.FriendsRepository;

@Service
@RequiredArgsConstructor
public class FriendsService {
    private final FriendsRepository friendsRepository;
    private final UserService userService;

    public List<String> getUserFriendsNames(String username) {
        List<FriendsDAO> friends = friendsRepository.getFriends(userService.getUuidByUsername(username));

        return friends.stream().map(friend -> {
            UUID user = userService.getUuidByUsername(username);
            return userService.getUsernameByUUID(friend.getFriend().equals(user) ? friend.getUuid() : friend.getFriend());
        }).toList();
    }
    public List<String> getUserFriendInvites(String username) {
        List<FriendsDAO> invites = friendsRepository.getInvites(userService.getUuidByUsername(username));

        return invites.stream().map(invite -> {
            return userService.getUsernameByUUID(invite.getUuid());
        }).toList();
    }
    public void inviteToFriend(String name, String friendName) {
        FriendsDAO friend = new FriendsDAO(0, 
                                            userService.getUuidByUsername(name), 
                                            userService.getUuidByUsername(friendName), 
                                            FriendshipStates.INVITE.name(),
                                            UUID.randomUUID());
        friendsRepository.save(friend);
    }
    public void friendRequestAnswer(String name, String friendName, FriendshipStates state) {
        friendsRepository.updateFriendInvite(userService.getUuidByUsername(name), 
                                             userService.getUuidByUsername(friendName), state.name());
    }
    public void addUserToBlacklist(String name, String blockedName) {
        
    }
    public UUID getChatUUID(String user, String userFriend) throws FriendException, ChatException {
        if(user.equals(userFriend)) throw new ChatException("Не существует чата с собой.");

        final UUID userUUID = userService.getUuidByUsername(user);
        final UUID userFriendUUID = userService.getUuidByUsername(userFriend);

        FriendsDAO friend = friendsRepository.getFriendRecord(userUUID, userFriendUUID);
        if(friend == null) throw new FriendException("Друг не найден");

        UUID chatUUID = friend.getChat();
        if(chatUUID == null) {
            chatUUID = UUID.randomUUID();
            friendsRepository.setChat(userUUID, userFriendUUID, chatUUID);
        }
        return chatUUID;
    }
}
