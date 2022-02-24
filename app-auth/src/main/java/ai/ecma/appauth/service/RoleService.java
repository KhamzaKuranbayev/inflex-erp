package ai.ecma.appauth.service;

import ai.ecma.appauth.payload.ApiResult;
import ai.ecma.appauth.payload.RoleDTO;

import java.util.List;


public interface RoleService {
    ApiResult<String> addRole(RoleDTO roleDTO);

    ApiResult<RoleDTO> getModuleList();

    ApiResult<String> editRole(Long id, RoleDTO roleDTO);

    ApiResult<RoleDTO> getRole(Long id);

    ApiResult<List<RoleDTO>> getRoles();

    ApiResult<?> deleteRole(Long id, Long insteadOfRoleId);
}
