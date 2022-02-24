package ai.ecma.appauth.controller;

import ai.ecma.appauth.payload.ApiResult;
import ai.ecma.appauth.payload.RoleDTO;
import ai.ecma.appauth.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RoleControllerImpl implements RoleController {
    private final RoleService roleService;


    @Override
    public ApiResult<List<RoleDTO>> getRoles() {
        log.info("getRoles method entered ");
        ApiResult<List<RoleDTO>> result = roleService.getRoles();
        log.info("getRoles method exited: {}", result);
        return result;
    }

    @Override
    public ApiResult<RoleDTO> getModuleList() {
        log.info("getModuleList method entered ");
        ApiResult<RoleDTO> result = roleService.getModuleList();
        log.info("getModuleList method exited: {}", result);
        return result;
    }

    @Override
    public ApiResult<String> addRole(@Valid RoleDTO roleDTO) {
        log.info("addRole method entered: {}", roleDTO);
        ApiResult<String> result = roleService.addRole(roleDTO);
        log.info("addRole method exited: {}", result);
        return result;
    }

    @Override
    public ApiResult<RoleDTO> getRole(Long id) {
        log.info("getRole method entered: {}", id);
        ApiResult<RoleDTO> result = roleService.getRole(id);
        log.info("getRole method exited: {}", result);
        return result;
    }

    @Override
    public ApiResult<String> editRole(Long id, RoleDTO roleDTO) {
        log.info("addRole method entered with id: {},{}", id, roleDTO);
        ApiResult<String> result = roleService.editRole(id, roleDTO);
        log.info("addRole method exited: {}", result);
        return result;
    }

    @Override
    public ApiResult<?> deleteRole(Long id, Long insteadOfRoleId) {
        log.info("deleteRole method entered: id: {}, insteadRoleId:{}", id, insteadOfRoleId);
        ApiResult<?> result = roleService.deleteRole(id, insteadOfRoleId);
        log.info("deleteRole method exited: {}", result);
        return result;
    }
}
