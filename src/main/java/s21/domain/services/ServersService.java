package s21.domain.services;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import s21.datasource.model.servers.ServerDAO;
import s21.datasource.model.servers.ServerMembersDAO;
import s21.datasource.repository.ServerMembersRepository;
import s21.datasource.repository.ServersRepository;

@Service
@RequiredArgsConstructor
public class ServersService {
    private final ServersRepository serversRepository;
    private final ServerMembersRepository serverMembersRepository;
    private final UserService userService;

    public List<String> getUserServersNames(String username) {
        List<ServerMembersDAO> servers = serverMembersRepository.findByUser(userService.getUuidByUsername(username));

        return servers.stream().map(server -> {
            ServerDAO serv = serversRepository.findByUuid(server.getServer()).orElse(null);
            if(serv == null) return null;
            return serv.getName();
        }).toList();

    }
}
