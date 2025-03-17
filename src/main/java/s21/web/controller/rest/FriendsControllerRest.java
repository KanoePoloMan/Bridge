package s21.web.controller.rest;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import s21.datasource.FriendshipStates;
import s21.datasource.services.FriendsService;
import s21.web.model.FriendDTO;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendsControllerRest {
    private final FriendsService friendsService;

    @GetMapping("list")
    public List<FriendDTO> getFriendsList() {
        return friendsService.getUserFriendsNames(SecurityContextHolder.getContext().getAuthentication().getName());
    }
    @GetMapping("invitedList")
    public List<FriendDTO> getFriendsInvitedList() {
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
    @PostMapping("denied")
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
    
}
