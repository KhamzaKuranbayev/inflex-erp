package ai.ecma.appauth.controller;

import ai.ecma.appauth.component.aop.other.annotation.CheckPermission;
import ai.ecma.appauth.payload.ApiResult;
import ai.ecma.appauth.payload.RoleDTO;
import ai.ecma.appauth.utils.RestConstants;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static ai.ecma.appauth.entity.enums.AuthPermissionEnum.*;

@RequestMapping(value = RoleController.ROLE_CONTROLLER_PATH)
public interface RoleController {
    String ROLE_CONTROLLER_PATH = RestConstants.BASE_PATH_V1 + "/role";
    String GET_MODULE_LIST_PATH = "/module-list";


    //TIZIMDAGI HAMMA ROLE'LARNI QAYTARADI
    @CheckPermission(permissions = {GET_ROLE})
    @GetMapping
    ApiResult<List<RoleDTO>> getRoles();

    //ROLE NI QO'SHAYOTGANDA TIZIMDAGI BARCHA MODULE VA
    @CheckPermission(permissions = {GET_MODULES})
    @GetMapping(GET_MODULE_LIST_PATH)
    ApiResult<RoleDTO> getModuleList();

    //ADMIN TOMONIDAN TIZIMGA YANGI ROLE(LAVOZIM) QO'SHILGANDA
    @CheckPermission(permissions = {ADD_ROLE})
    @PostMapping
    ApiResult<String> addRole(@Valid @RequestBody RoleDTO roleDTO);

    //ROLE ID KELADI, BIZ UNGA USHBU ID ORQALI ROLE_DTO OBJECTINI YASAB QAYTARAMIZ
    @CheckPermission(permissions = {GET_ROLE})
    @GetMapping("/{id}")
    ApiResult<RoleDTO> getRole(@PathVariable Long id);

    //ROLE EDIT QILISH
    @CheckPermission(permissions = {EDIT_ROLE})
    @PutMapping("/{id}")
    ApiResult<String> editRole(@PathVariable Long id, @RequestBody RoleDTO roleDTO);

    //ROLE NI O'CHIRISH. AGAR ROLE USGA BIRIKTIRILGAN BO'LSA,
    // SHU REQUESTGA RESPONSE SIFATIDA TANLSHI KERAK BO'LGAN ROLE LAR LISTI TANLAB BERILADI,
    //KEYINGI REQUESTDA O'CHIIRILAYOTGAN ROLE VA USHBU ROLE ISHLATILGAN USERLARGA QAYSI ROLENI
    // O'RNATMOQCHI BO'LSAK UNI ID SI KELADI
    @CheckPermission(permissions = {DELETE_ROLE})
    @DeleteMapping("/{id}/{insteadOfRoleId}")
    ApiResult<?> deleteRole(@PathVariable Long id,
                            @PathVariable(required = false) Long insteadOfRoleId);

}
