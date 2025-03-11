package s21.web.mapper;

import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import s21.datasource.UserDAO;
import s21.domain.model.Role;
import s21.web.model.UserDTO;

@Mapper
public interface UserDatasourceWebMapper {
    UserDatasourceWebMapper INSTANCE = Mappers.getMapper(UserDatasourceWebMapper.class);

    default UserDTO datasourceToWeb(UserDAO datasource) {
        if(datasource == null) return null;
        return new UserDTO(datasource.getUuid(), 
                           datasource.getLogin(), 
                           datasource.getPassword(),
                           datasource.getRoles().stream().map(Role::valueOf).collect(Collectors.toSet()));
    }
}
