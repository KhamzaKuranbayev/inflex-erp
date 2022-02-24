package ai.ecma.appauth.mapper;

import ai.ecma.appauth.entity.Module;
import ai.ecma.appauth.entity.*;
import ai.ecma.appauth.payload.*;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MainMapper {
    Role toRole(RoleDTO roleDTO);

    RoleDTO toRoleDTO(Role role);

    void updateRole(RoleDTO roleDTO, @MappingTarget Role role);

    List<RoleDTO> toRoleDTOList(List<Role> roles);

    AuthPage toAuthPage(AuthPageDTO authPageDTO);

    Permission toPermission(PermissionDTO permissionDTO);

    DepartmentDTO toDepartmentDTO(Department department);

    AuthPageDTO toAuthPageDTO(AuthPage authPage);

    PermissionDTO toPermissionDTO(Permission permission);

    List<AuthPageDTO> toAuthPageDTOList(List<AuthPage> authPages);

    List<PermissionDTO> toPermissionDTOList(List<Permission> permissions);

    List<DepartmentDTO> toDepartmentDTOList(List<Department> departments);

    Module toModule(ModuleDTO moduleDTO);

    ModuleDTO toModuleDTO(Module module);

    UserQuestion mapToUserQuestion(UserQuestionDTO userQuestionDTO);

    List<UserQuestion> mapToUserQuestionList(List<UserQuestionDTO> userQuestionDTOList);

    SmsCodeDTO toSmsCodeDTO(ResetPasswordDTO resetPasswordDTO);
}
