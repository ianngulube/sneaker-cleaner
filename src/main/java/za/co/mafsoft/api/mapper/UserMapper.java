package za.co.mafsoft.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import za.co.mafsoft.api.entity.UserEntity;
import za.co.mafsoft.api.model.User;

@Mapper(componentModel = "cdi")
public interface UserMapper {

    @Mapping(source = "pin", target = "pin")
    UserEntity modelToEntity(User user);
    @Mapping(source = "pin", target = "pin")
    User entityToModel(UserEntity entity);
}
