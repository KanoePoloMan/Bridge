package s21.datasource.mapper;

import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import s21.datasource.UserDAO;
import s21.domain.model.Role;
import s21.web.model.UserDTO;

@Mapper
public interface UserWebDatasourceMapper {
    UserWebDatasourceMapper INSTANCE = Mappers.getMapper(UserWebDatasourceMapper.class);

    default UserDAO webToDatasource(UserDTO web) {
        if(web == null) return null;
        return new UserDAO(web.getUuid(), 
                           web.getUsername(), 
                           web.getPassword(), 
                           web.getAuthorities().stream().map(Role::name).collect(Collectors.toSet()));
    }
}
