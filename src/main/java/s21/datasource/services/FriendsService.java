package s21.datasource.services;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import s21.datasource.FriendsDAO;
import s21.datasource.FriendshipStates;
import s21.datasource.repository.FriendsRepository;
import s21.web.model.FriendDTO;

@Service
@RequiredArgsConstructor
public class FriendsService {
    private final FriendsRepository friendsRepository;
    private final UserService userService;

    public List<FriendDTO> getUserFriendsNames(String username) {
        List<FriendsDAO> friends = friendsRepository.getFriends(userService.getUuidByUsername(username));

        return friends.stream().map(friend -> {
            return new FriendDTO(userService.getUserByUUID(friend.getFriend()).getLogin(), friend.getState());
        }).toList();
    }
    public List<FriendDTO> getUserFriendInvites(String username) {
        List<FriendsDAO> invites = friendsRepository.getInvites(userService.getUuidByUsername(username));

        return invites.stream().map(invite -> {
            return new FriendDTO(userService.getUserByUUID(invite.getUuid()).getLogin(), invite.getState());
        }).toList();
    }
    public void inviteToFriend(String name, String friendName) {
        FriendsDAO friend = new FriendsDAO(0, 
                                            userService.getUuidByUsername(name), 
                                            userService.getUuidByUsername(friendName), 
                                            FriendshipStates.INVITE.name());
        friendsRepository.save(friend);
    }
    public void friendRequestAnswer(String name, String friendName, FriendshipStates state) {
        friendsRepository.updateFriendInvite(userService.getUuidByUsername(name), 
                                             userService.getUuidByUsername(friendName), state.name());
    }
}
