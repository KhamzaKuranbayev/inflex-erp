package ai.ecma.appauth.mapper;

import ai.ecma.appauth.entity.User;
import ai.ecma.appauth.payload.StaffDTO;
import ai.ecma.appauth.payload.StudentDTO;
import ai.ecma.appauth.payload.UserDTO;
import ai.ecma.appauth.payload.UserEditDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    UserDTO toUserDTO(User user);

    User toStudent(StudentDTO studentDTO);

    User toStaff(StaffDTO staffDto);

    void updateStudent(StudentDTO studentDTO, @MappingTarget User user);

    void updateStaff(StaffDTO staffDto, @MappingTarget User user);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "phoneNumber", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateUser(UserEditDTO userEditDTO, @MappingTarget User user);

}
