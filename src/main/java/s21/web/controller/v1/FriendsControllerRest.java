package s21.web.controller.v1;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import s21.datasource.model.exceptions.ChatException;
import s21.datasource.model.exceptions.FriendException;
import s21.datasource.model.friends.FriendshipStates;
import s21.domain.services.FriendsService;
import s21.domain.services.MessageService;
import s21.web.model.ChatMessageDTO;



@RestController
@RequestMapping("/v1/friends")
@RequiredArgsConstructor
public class FriendsControllerRest {
    private final FriendsService friendsService;
    private final MessageService messageService;

    @GetMapping("list")
    public List<String> getFriendsList() {
        return friendsService.getUserFriendsNames(SecurityContextHolder.getContext().getAuthentication().getName());
    }
    @GetMapping("invitings")
    public List<String> getFriendsInvitedList() {
        return friendsService.getUserFriendInvites(SecurityContextHolder.getContext().getAuthentication().getName());
    }
    
    @PostMapping("invite")
    public void inviteToFriend(@RequestBody String name) {
        friendsService.inviteToFriend(SecurityContextHolder.getContext().getAuthentication().getName(), name);
    }
    @PostMapping("accept")
    public void acceptFriend(@RequestBody String name) {
        friendsService.friendRequestAnswer(SecurityContextHolder.getContext().getAuthentication().getName(), 
                                           name, 
                                           FriendshipStates.FRIEND);
    }
    @PostMapping("deny")
    public void deniedFriend(@RequestBody String name) {
        friendsService.friendRequestAnswer(SecurityContextHolder.getContext().getAuthentication().getName(), 
                                           name, 
                                           FriendshipStates.DENIED);
    }
    @PostMapping("blacklist")
    public void blackList(@RequestBody String name) {
        friendsService.friendRequestAnswer(SecurityContextHolder.getContext().getAuthentication().getName(), 
                                           name, 
                                           FriendshipStates.BLACKLIST);
    }
    @GetMapping("chatuuid/{nickname}")
    public UUID getChatUUID(@PathVariable("nickname") String name) throws FriendException, ChatException {
        return friendsService.getChatUUID(SecurityContextHolder.getContext().getAuthentication().getName(), 
                                          name);
    }
    
    @GetMapping("messages/{nickname}")
    public List<ChatMessageDTO> getMessagesByName(@PathVariable("nickname") String name) throws FriendException, ChatException {
        return messageService.getMessages(SecurityContextHolder.getContext().getAuthentication().getName(), 
                                          name);
    }
}
