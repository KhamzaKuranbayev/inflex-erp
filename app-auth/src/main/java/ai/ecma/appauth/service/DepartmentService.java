package ai.ecma.appauth.service;

import ai.ecma.appauth.entity.Module;
import ai.ecma.appauth.entity.*;
import ai.ecma.appauth.exceptions.RestException;
import ai.ecma.appauth.payload.ApiResult;
import ai.ecma.appauth.payload.AuthPageDTO;
import ai.ecma.appauth.payload.DepartmentDTO;
import ai.ecma.appauth.payload.PermissionDTO;
import ai.ecma.appauth.repository.AuthPageRepository;
import ai.ecma.appauth.repository.DepartmentRepository;
import ai.ecma.appauth.repository.ModuleRepository;
import ai.ecma.appauth.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final AuthPageRepository authPageRepository;
    private final PermissionRepository permissionRepository;
    private final ModuleRepository moduleRepository;
    private final MainService mainService;


    @Transactional
    public ApiResult<Boolean> addDepartment(List<DepartmentDTO> departmentDTOList) {
        //NULL BO'LISHI MUMKIN BO'LMAGAN MAYDONLARNI TEKSHIRAMIZ
        validateDepartmentDTOListTitleAndName(departmentDTOList);

        //NULL BO'LISHI MUMKIN BO'LMAGAN MAYDONLARNI TEKSHIRAMIZ
        validateDepartmentDTOListTitleAndName(departmentDTOList);

        //SECURITY CONTEXTDA QAYSI SERVICE KIRGANLIGINI BILISH
        User user = mainService.getUserFromSecurityContextIfNullThrow();

        //KIRIB TURGAN USERNING PHONENUMBERINI OLISH (MODULE NAME)
        Module module = moduleRepository.findByName(user.getPhoneNumber().toUpperCase()).orElseThrow(() -> RestException.restThrow("module", "name", user.getPhoneNumber(), mainService.getMessageByKey("USER_NOT_FOUND")));

        //USHBU MODULE GA TEGISHLI BO'LGAN DEPARTMENTLARNI OLISH
        List<Department> departments = departmentRepository.findAllByModuleId(module.getId());

        //AGAR BAZADA USHBU MODULEGA TEGISHLI DEPARTMENT BO'LMASA, KELGAN DEPARTMENT DTO LARNI SAQLASH
        if (departments.isEmpty()) {
            for (DepartmentDTO departmentDTO : departmentDTOList) {
                createUpdateDeleteDepartment(null, departmentDTO, module);
            }
        }
        //DEPARTMENT DTO LIST BO'SH BO'LSA, BAZADAGI USHBU MODULGA TEGISHLI BARCHA DEPARTMENTLARNI O'CHIRISH
        else if (departmentDTOList.isEmpty()) {
            for (Department department : departments) {
                createUpdateDeleteDepartment(department, null, module);
            }
        } else {
            //USHBU MODULE GA TEGISHLI DEPARTMENTLARNI EDIT QILAYOTGANDA, USHBU LISTGA YIG'AMIZ
            List<Department> updatingDepartments = departments.stream().filter(department -> departmentDTOList.stream().
                    anyMatch(departmentDTO -> departmentDTO.getName().equals(department.getName()))).collect(Collectors.toList());

            //USHBU MODULE GA TEGISHLI DEPARTMENTLARNI DTO DA KELMADI, SHU SABAB ULARNI O'CHIRISH LISTGA YIG'AMIZ
            List<Department> removingDepartments = departments.stream().filter(department ->
                    departmentDTOList.stream().noneMatch(departmentDTO -> departmentDTO.getName().equals(department.getName()))).collect(Collectors.toList());

            //USHBU MODULE GA TEGISHLI DEPARTMENTLARDAN TASHQARI DTO DA YANGI DEPARTMENTLAR KELGANDA, ADDING LISTIGA YIG'AMIZ
            List<DepartmentDTO> creatingDepartments = departmentDTOList.stream().filter
                    (departmentDTO -> departments.stream().noneMatch(department -> department.getName().equals(departmentDTO.getName()))).collect(Collectors.toList());

            //DEPARTMENT UPDATE LISTI AYLANIB EDIT QILINYAPTI
            updatingDepartments.forEach(department -> {
                Optional<DepartmentDTO> optionalDepartmentDTO = departmentDTOList.stream()
                        .filter(departmentDTO ->
                                departmentDTO.getName().equals(department.getName())).findFirst();
                optionalDepartmentDTO.ifPresent(departmentDTO -> createUpdateDeleteDepartment(department,
                        departmentDTO,
                        module));
            });

            //DEPARTMENT CREATING LISTI AYLANIB ADD QILINYAPTI
            creatingDepartments.forEach(departmentDTO ->
                    createUpdateDeleteDepartment(null, departmentDTO, module));

            //DEPARTMENT REMOVING LISTI AYLANIB DELETE QILINYAPTI
            removingDepartments.forEach(department ->
                    createUpdateDeleteDepartment(department, null, module));
        }

        return ApiResult.successResponse();
    }


    public ApiResult<String> editDepartment(List<DepartmentDTO> departmentDTOList) {
        List<Department> departmentList = new ArrayList<>();
        departmentDTOList.forEach(departmentDTO -> {
            Department department = departmentRepository.findById(departmentDTO.getId()).orElseThrow(() -> RestException.restThrow("department", "", "", mainService.getMessageByKey("DEPARTMENT_NOT_FOUND")));
            department.setOrderIndex(departmentDTO.getOrderIndex());
            department.setTitle(departmentDTO.getTitle());
            departmentList.add(department);
        });
        departmentRepository.saveAll(departmentList);
        return ApiResult.successResponse(mainService.getMessageByKey("DEPARTMENT_EDITED"));
    }

    //DEPARTMENTNI ADD YOKI EDIT YOKI DELETE QILISH
    private void createUpdateDeleteDepartment(Department department, DepartmentDTO departmentDTO, Module module) {
        if (department == null) {
            //ADD QILISH
            createDepartment(departmentDTO, module);
        } else if (departmentDTO == null) {
            //DELETE QILISH
            deleteDepartment(department);
        } else {
            //EDIT QILISH
            updateDepartment(department, departmentDTO);
        }
    }

    //DEPARTMENTNI EDIT QILISH
    private void updateDepartment(Department department, DepartmentDTO departmentDTO) {
        department.setTitle(departmentDTO.getTitle());
        departmentRepository.save(department);
        addAuthPage(departmentDTO.getPages(), department);
    }

    //DEPARTMENTNI DELETE QILISH
    private void deleteDepartment(Department department) {
        departmentRepository.delete(department);
    }

    //DEPARTMENTNI ADD QILISH
    private void createDepartment(DepartmentDTO departmentDTO, Module module) {
        Department department = new Department();
        department.setName(departmentDTO.getName());
        department.setTitle(departmentDTO.getTitle());
        department.setModule(module);
        departmentRepository.save(department);
        addAuthPage(departmentDTO.getPages(), department);
    }

    //AUTH PAGE ADD QILISH
    private void addAuthPage(List<AuthPageDTO> authPageDTOList, Department department) {
        List<AuthPage> permissions = authPageRepository.findAllByDepartmentId(department.getId());
        if (permissions.isEmpty()) {
            for (AuthPageDTO authPageDTO : authPageDTOList) {
                createUpdateDeleteAuthPage(null, authPageDTO, department);
            }

        } else if (authPageDTOList.isEmpty()) {
            for (AuthPage authPage : permissions) {
                createUpdateDeleteAuthPage(authPage, null, department);
            }
        } else {
            List<AuthPage> updatingAuthPages = permissions.stream().filter(authPage -> authPageDTOList.stream().
                    anyMatch(authPageDTO -> authPageDTO.getName().equals(authPage.getName()))).collect(Collectors.toList());

            List<AuthPage> removingAuthPages = permissions.stream().filter(authPage ->
                    authPageDTOList.stream().noneMatch(authPageDTO -> authPageDTO.getName().equals(authPage.getName()))).collect(Collectors.toList());

            List<AuthPageDTO> creatingAuthPages = authPageDTOList.stream().filter
                    (authPageDTO -> permissions.stream().noneMatch(authPage -> authPage.getName().equals(authPageDTO.getName()))).collect(Collectors.toList());

            updatingAuthPages.forEach(authPage -> {
                Optional<AuthPageDTO> optionalAuthPageDTO = authPageDTOList.stream()
                        .filter(authPageDTO ->
                                authPageDTO.getName().equals(authPage.getName())).findFirst();
                optionalAuthPageDTO.ifPresent(authPageDTO -> createUpdateDeleteAuthPage(authPage,
                        authPageDTO, department));
            });
            creatingAuthPages.forEach(authPageDTO ->
                    createUpdateDeleteAuthPage(null, authPageDTO, department));

            removingAuthPages.forEach(authPage ->
                    createUpdateDeleteAuthPage(authPage, null, department));
        }
    }

    //AUTHPAGE USTIDA CRUD AMALLARI
    private void createUpdateDeleteAuthPage(AuthPage authPage, AuthPageDTO authPageDTO, Department department) {
        if (authPage == null) {
            createAuthPage(authPageDTO, department);
        } else if (authPageDTO == null) {
            deleteAuthPage(authPage);
        } else {
            updateAuthPage(authPage, authPageDTO, department);
        }
    }

    // AUTHPAGENI TAHRIRLASH
    private void updateAuthPage(AuthPage authPage, AuthPageDTO authPageDTO, Department department) {
        authPage.setName(authPageDTO.getName());
        authPage.setTitle(authPageDTO.getTitle());
        authPage.setDepartment(department);
        authPageRepository.save(authPage);
        addPermission(authPageDTO.getPermissions(), authPage);
    }

    //AUTHPAGENI DELETE QILISH
    private void deleteAuthPage(AuthPage authPage) {
        authPageRepository.delete(authPage);
    }

    //AUTHPAGE YASASH
    private void createAuthPage(AuthPageDTO authPageDTO, Department department) {
        AuthPage authPage = new AuthPage();
        authPage.setName(authPageDTO.getName());
        authPage.setTitle(authPageDTO.getTitle());
        authPage.setDepartment(department);
        authPageRepository.save(authPage);
        addPermission(authPageDTO.getPermissions(), authPage);
    }


    ///--------------PERMISISON-----///

    //PERMISSION QO'SHISH
    private void addPermission(List<PermissionDTO> permissionDTOList, AuthPage authPage) {
        List<Permission> permissions = permissionRepository.findAllByPageId(authPage.getId());
        if (permissions.isEmpty()) {
            for (PermissionDTO permissionDTO : permissionDTOList) {
                createUpdateDeletePermission(null, permissionDTO, authPage);
            }

        } else if (permissionDTOList.isEmpty()) {
            for (Permission permission : permissions) {
                createUpdateDeletePermission(permission, null, authPage);
            }
        } else {
            List<Permission> updatingPermissions = permissions.stream().filter(permission -> permissionDTOList.stream().
                    anyMatch(permissionDTO -> permissionDTO.getName().equals(permission.getName()))).collect(Collectors.toList());

            List<Permission> removingPermission = permissions.stream().filter(permission ->
                    permissionDTOList.stream().noneMatch(permissionDTO -> permissionDTO.getName().equals(permission.getName()))).collect(Collectors.toList());

            List<PermissionDTO> creatingPermission = permissionDTOList.stream().filter
                    (permissionDTO -> permissions.stream().noneMatch(permission -> permission.getName().equals(permissionDTO.getName()))).collect(Collectors.toList());

            updatingPermissions.forEach(permission -> {
                Optional<PermissionDTO> optionalPermissionDTO = permissionDTOList.stream()
                        .filter(permissionDTO ->
                                permissionDTO.getName().equals(permission.getName())).findFirst();
                optionalPermissionDTO.ifPresent(permissionDTO -> createUpdateDeletePermission(permission,
                        permissionDTO,
                        authPage));
            });

            creatingPermission.forEach(permissionDTO ->
                    createUpdateDeletePermission(null, permissionDTO, authPage));

            removingPermission.forEach(permission ->
                    createUpdateDeletePermission(permission, null, authPage));
        }
    }

    private void createUpdateDeletePermission(Permission permission, PermissionDTO permissionDTO, AuthPage authPage) {
        if (permission == null) {
            createPermission(permissionDTO, authPage);
        } else if (permissionDTO == null) {
            deletePermission(permission);
        } else {
            updatePermission(permission, permissionDTO, authPage);
        }
    }

    //PERMISSION NI TAHRIRILASH
    private void updatePermission(Permission permission, PermissionDTO permissionDTO, AuthPage authPage) {
        permission.setName(permissionDTO.getName());
        permission.setTitle(permissionDTO.getTitle());
        permission.setPage(authPage);
        permissionRepository.save(permission);
    }

    //PERMISSIONNI DELETE QILISH
    private void deletePermission(Permission permission) {
        permissionRepository.delete(permission);
    }

    //PERMISSION YASASH
    private void createPermission(PermissionDTO permissionDTO, AuthPage authPage) {
        Permission permission = new Permission();
        permission.setName(permissionDTO.getName());
        permission.setTitle(permissionDTO.getTitle());
        permission.setPage(authPage);
        permissionRepository.save(permission);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void validateDepartmentDTOListTitleAndName(List<DepartmentDTO> departmentDTOList) {
        departmentDTOList.forEach(departmentDTO -> {
            if (departmentDTO.getTitle() == null) {
                throw RestException.restThrow(mainService.getMessageByKey("SHOULD_NOT_BE_EMPTY"), HttpStatus.BAD_REQUEST);
            }
            if (departmentDTO.getName() == null) {
                throw RestException.restThrow(mainService.getMessageByKey("SHOULD_NOT_BE_EMPTY"), HttpStatus.BAD_REQUEST);
            }
            validateAuthPageDTOListTitleAndName(departmentDTO.getPages());
        });
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void validateAuthPageDTOListTitleAndName(List<AuthPageDTO> authPageDTOList) {
        if (authPageDTOList == null) {
            throw RestException.restThrow(mainService.getMessageByKey("SHOULD_NOT_BE_EMPTY"), HttpStatus.BAD_REQUEST);
        }
        authPageDTOList.forEach(authPageDTO -> {
            if (authPageDTO.getTitle() == null) {
                throw RestException.restThrow(mainService.getMessageByKey("SHOULD_NOT_BE_EMPTY"), HttpStatus.BAD_REQUEST);
            }
            if (authPageDTO.getName() == null) {
                throw RestException.restThrow(mainService.getMessageByKey("SHOULD_NOT_BE_EMPTY"), HttpStatus.BAD_REQUEST);
            }
//            if (authPageDTO.getDefaultPage() == null) {
//                throw RestException.restThrow(messageByLang.getMessageByKey("SHOULD_NOT_BE_EMPTY"), HttpStatus.BAD_REQUEST);
//            }
            validatePermissionDTOListTitleAndName(authPageDTO.getPermissions());
        });
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void validatePermissionDTOListTitleAndName(List<PermissionDTO> permissionDTOList) {
        if (permissionDTOList == null) {
            throw RestException.restThrow(mainService.getMessageByKey("SHOULD_NOT_BE_EMPTY"), HttpStatus.BAD_REQUEST);
        }
        permissionDTOList.forEach(permissionDTO -> {
            if (permissionDTO.getTitle() == null) {
                throw RestException.restThrow(mainService.getMessageByKey("SHOULD_NOT_BE_EMPTY"), HttpStatus.BAD_REQUEST);
            }
            if (permissionDTO.getName() == null) {
                throw RestException.restThrow(mainService.getMessageByKey("SHOULD_NOT_BE_EMPTY"), HttpStatus.BAD_REQUEST);
            }
        });
    }
}
