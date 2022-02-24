package ai.ecma.appauth.service;

import ai.ecma.appauth.entity.Module;
import ai.ecma.appauth.entity.*;
import ai.ecma.appauth.entity.enums.RoleType;
import ai.ecma.appauth.exceptions.RestException;
import ai.ecma.appauth.mapper.MainMapper;
import ai.ecma.appauth.payload.*;
import ai.ecma.appauth.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static ai.ecma.appauth.entity.enums.RoleType.ADMIN;
import static ai.ecma.appauth.entity.enums.RoleType.SYSTEM_ROLE;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final MainMapper mainMapper;
    private final RolePageRepository rolePageRepository;
    private final RolePagePermissionRepository rolePagePermissionRepository;
    private final DepartmentRepository departmentRepository;
    private final PermissionRepository permissionRepository;
    private final ModuleRepository moduleRepository;
    private final MainService mainService;
    private final UserRepository userRepository;
    private final AuthServiceImpl authServiceImpl;
    private final UserRoleRepository userRoleRepository;


    @Override
    @Transactional
    public ApiResult<String> addRole(RoleDTO roleDTO) {

        //USHBU ROLEGA BIRIKTIRILGAN DEFAULT PAGENING ID SINI OLISH
        Long defaultRoleId = getDefaultPageIdFromModuleDepartment(roleDTO.getModules());

        //DB DAN ROLE NI NAME BO'YICHA TEKSHIRISH. AGAR SHUNDAY BO'LSA THROW QILAMIZ. BUNDA EDIT DA ID HAM TEKSHIRILADI
        existRoleByNameAndIdIfExistThrow(roleDTO.getName(), null);

        //QO'SHMOQCHI BO'LGAN ROLE NING ROLE_TYPE OTHER BO'LMASA VA SHUNDAY ROLE_TYPE LI ROLE BO'LADIGAN BO'LSA THROW QILAMIZ
        existRoleByRoleTypeIfExistThrow(roleDTO.getRoleType());

        //ROLE_DTO DAN ROLE YASAB OLYAPMIZ
        Role role = mainMapper.toRole(roleDTO);

        //ROLE GA YUQORIDA OLGAN defaultRoleId NI O'RNATYAPMIZ
        role.setDefaultPageId(defaultRoleId);

        roleRepository.save(role);

        //ROLE GA BIRIKTORILGAN PAGE LARNI SAQLAB OLYAPMIZ
        saveRolePageList(roleDTO.getModules(), role);

        return ApiResult.successResponse(mainService.getMessageByKey("ROLE_SUCCESSFULLY_SAVED"));
    }

    @Override
    public ApiResult<String> editRole(Long id, RoleDTO roleDTO) {

        //TAHRIRLAMOQCHI BO'LGAN ROLENI DB DAN OLYAPMIZ, AGAR BO'LMASA THROW QILAMIZ
        Role role = roleRepository.findById(id).orElseThrow(() -> RestException.restThrow("Role", "id", id, mainService.getMessageByKey("ROLE_NOT_FOUND")));

        //USHBU ROLEGA BIRIKTIRILGAN DEFAULT PAGENING ID SINI OLISH
        Long defaultPageId = getDefaultPageIdFromModuleDepartment(roleDTO.getModules());

        //DB DAN ROLE NI NAME BO'YICHA TEKSHIRISH. AGAR SHUNDAY BO'LSA THROW QILAMIZ. BUNDA EDIT DA ID HAM TEKSHIRILADI
        existRoleByNameAndIdIfExistThrow(roleDTO.getName(), id);

        //QO'SHMOQCHI BO'LGAN ROLE NING ROLE_TYPE OTHER BO'LMASA VA SHUNDAY ROLE_TYPE LI ROLE BO'LADIGAN BO'LSA THROW QILAMIZ
        existRoleByRoleTypeIfExistThrow(roleDTO.getRoleType());

        roleDTO.setId(id);
        roleDTO.setDefaultPageId(defaultPageId);

        //MAPPER ORQALI ROLE NI UPDATE QILAMIZ
        mainMapper.updateRole(roleDTO, role);

        roleRepository.save(role);

        //ROLE GA BIRIKTORILGAN PAGE LARNI SAQLAB OLYAPMIZ, AGAR O'ZGARMAGAN BO'LSA HECH QANDAY O'ZGARISH BO'LMAYDI
        saveRolePageList(roleDTO.getModules(), role);

        return ApiResult.successResponse(mainService.getMessageByKey("ROLE_SUCCESSFULLY_EDITED"));
    }

    @Override
    public ApiResult<RoleDTO> getModuleList() {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setRoleTypes(mapRoleTypeDTOList());
        roleDTO.setModules(mapToModuleDTOList());
        return ApiResult.successResponse(roleDTO);
    }

    @Override
    public ApiResult<RoleDTO> getRole(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> RestException.restThrow("role", "id", id, mainService.getMessageByKey("ROLE_NOT_FOUND")));

        //MAPSTRUCT YORDAMIDA ROLE DAN ROLE_DTO YASAB OLDIK
        RoleDTO roleDTO = mainMapper.toRoleDTO(role);

        //ROLE_DTO GA ROLE_TYPE LARNI SET QILDIK
        roleDTO.setRoleTypes(mapRoleTypeDTOList());

        //ROLE_DTO GA MODULLAR LISTINI LARNI SET QILDIK
        roleDTO.setModules(mapToModuleDTOList());

        //BERILGAN ROLE ID LI ROLE NING HAMMA PERMISSIONLARINI OLDIK
        List<Permission> rolePermissionList = permissionRepository.getPermissionByRoleId(id);

        //YUQORIDAGI PERMISSION LISTIDA BOR PERMISSIONLARNI CHECKED FIELDINI TRUE QILIB ROLE_DTO GA SET QILDIK
        changeCheckedIfPermissionGivenThisRole(roleDTO, rolePermissionList);

        return ApiResult.successResponse(roleDTO);
    }

    @Override
    public ApiResult<List<RoleDTO>> getRoles() {

        //SYSTEM_ROLE DAN BOSHQA BARCHA ROLE LARNI OLISH
        List<Role> roleList = roleRepository.findAllByRoleTypeIsNotIn(List.of(SYSTEM_ROLE));

        //ROLE LISTINI DTO GA BERAMIZ
        List<RoleDTO> roleDTOList = mainMapper.toRoleDTOList(roleList);

        return ApiResult.successResponse(roleDTOList);
    }

    @Override
    public ApiResult<?> deleteRole(Long id, Long insteadOfRoleId) {

        //ROLE ID BERILSA BIZ UNGA O'SHA ROLE ENUMLIK ROLE NI QAYTARAMIZ, TOPOLMASAK THROW QILAMIZ
        Role deletingRole = authServiceImpl.getRoleByRoleId(id);

        //O'CHIRILAYOTGAN ROLE ADMIN ROLE EMASLIGINI TEKSHIRAMIZ
        if (deletingRole.getRoleType().equals(ADMIN))
            throw RestException.restThrow(mainService.getMessageByKey("ROLE_CAN_NOT_DELETE"), HttpStatus.BAD_REQUEST);

        //O'CHIRMOQCHI BO'LGAN ROLE USERLARGA BERLGANMI?
        boolean existsByRoleId = userRepository.existsUserRoleByRoleId(id);

        //O'CHIRMOQCHI BO'LGAN ROLE USERLARGA BIRIKTIRILMAGAN BO'LSA YOKI O'CHIRMOQCHI BO'LGAN ROLE O'RNIGA BOSHQA ROLE ID KELAYTOGANDA
        if (!existsByRoleId || insteadOfRoleId != null) {

            //O'CHIRMOQCHI BO'LGAN ROLE GA EGA BO'LGAN USER LARGA O'CHIRILAYOTGAN ROLE O'RNIGA QO'YAYOTGAN ROLE NI OLYAPMIZ
            Role insteadOfRole = authServiceImpl.getRoleByRoleId(insteadOfRoleId);

            //O'CHIRMOQCHI BO'LINGAN ROLE GA EGA BO'LGAN USER LARGA O'CHIRILAYOTGAN ROLE O'RNIGA BOSHQA ROLE QO'YAMIZ
            new Thread(() -> removeRoleFromUserAndAddOtherRoleToUsers(deletingRole, insteadOfRole)).start();

            roleRepository.deleteById(id);

            return ApiResult.successResponse(mainService.getMessageByKey("SUCCESSFULLY_ROLE_DELETED"));

        } else {

            //O'CHIRMOQCHI BO'LGAN ROLE O'RNIGA BOSHQA ROLE LARNI TANLASH UCHUN ROLE LAR LISTI BERILADI
            List<RoleDTO> roleDTOList = getAllRolesMapToRoleDTO();

            //O'CHIRMOQCHI BO'LGAN ROLE O'RNIGA BOSHQA ROLE LARNI TANLASH UCHUN ROLE LAR LISTI DAN OC'HIRMOQCHI BO'LGAN ROLE ID SIDAN BOSHQASI BERILADI
            roleDTOList = roleDTOList.stream().filter(roleDTO -> !roleDTO.getId().equals(id)).collect(Collectors.toList());

            return ApiResult.successResponse(roleDTOList);
        }
    }

    ///-------------USHBU CLASSGA TEGISHHLI METHODLAR--------------
    //ROLEPAGE LISNI NI SAQLOVCHI METHOD

    private List<RoleDTO> getAllRolesMapToRoleDTO() {
        //SYSTEM_ROLE DAN BOSHQA BARCHA ROLE LARNI OLISH
        List<Role> roleList = roleRepository.findAllByRoleTypeIsNotIn(List.of(SYSTEM_ROLE));

        //ROLE LISTINI DTO GA BERAMIZ
        return mainMapper.toRoleDTOList(roleList);
    }

    //ROLE GA BIRIKTORILGAN PAGE LARNI SAQLAB OLYAPMIZ
    private void saveRolePageList(List<ModuleDTO> moduleDTOList, Role role) {

        //MODULE LIST NING ICHIDAGI DEPARTMENT LISTNING ICHIDAN AUTH_PAGE LISTLARNI YIG'IB OLYAPMIZ
        List<AuthPageDTO> authPageDTOList = moduleDTOList
                .stream()
                .flatMap(moduleDTO -> moduleDTO.getDepartments()
                        .stream()
                        .flatMap(departmentDTO -> departmentDTO.getPages()
                                .stream())).collect(Collectors.toList());

        //AUTH_PAGE_DTO LISTLARNI AYLANIB CHIQIB, ROLE GA BIRIKTIRLGAN PAGE LAR UCHUN ROLE_PAGE NI SAQLAYMIZ VA ROLE_PAGE_PERMISSION LARNI SAQLAYMIZ
        authPageDTOList.forEach(authPageDTO -> {

            //ROLEGA BIRIKTIRILGAN PAGELARNI NI ROLE_PAGE ENTITY SI ORQALI SAQLOVCHI METHOD
            RolePage rolePage = saveRolePageOrGetFromDB(role, authPageDTO);

            //ROLE GA BIRIKTIRLGAN PAGE LARDA RUXSAT ETILGAN PERMISSIONLARNI ROLE_PAGE_PERMISSIONNI NI SAQLOVCHI METHOD
            saveRolePagePermissions(rolePage, authPageDTO.getPermissions());
        });
    }

    //ROLEGA BIRIKTIRILGAN PAGELARNI NI ROLE_PAGE ENTITY SI ORQALI SAQLOVCHI METHOD
    private RolePage saveRolePageOrGetFromDB(Role role, AuthPageDTO authPageDTO) {

        //BAZADAN ROLE_ID VA AUTH_PAGE_DTO ICHIDAGI ID ORQALI ROLE_PAGE OLINADI
        Optional<RolePage> optionalRolePage = rolePageRepository.findByRoleIdAndPageId(role.getId(), authPageDTO.getId());

        //optionalRolePage  BO'SH BO'LSA, YANGI ROLE_PAGE OCHILADI
        RolePage rolePage = optionalRolePage.orElseGet(() -> new RolePage(role, authPageDTO.getId()));

        //AGAR ROLE_PAGE BO'LMASA DB GA SAQLAYMIZ
        if (optionalRolePage.isEmpty())
            rolePageRepository.save(rolePage);

        return rolePage;
    }

    //ROLE GA BIRIKTIRLGAN PAGE LARDA RUXSAT ETILGAN PERMISSIONLARNI ROLE_PAGE_PERMISSIONNI NI SAQLOVCHI METHOD
    private void saveRolePagePermissions(RolePage rolePage, List<PermissionDTO> permissionDTOList) {

        //permissionDTOList AYLANIB CHIQILIB, DB DAN RolePagePermission OLADI YOKI YANGI RolePagePermission YASAB LISTGA SOLADI
        List<RolePagePermission> rolePagePermissions = permissionDTOList.stream().map(permissionDTO ->
                makeNewRolePagePermissionOrGetFromDB(rolePage, permissionDTO)).collect(Collectors.toList());

        //YIG'ILGAN RolePagePermission LISTINI SAQLAYMIZ
        rolePagePermissionRepository.saveAll(rolePagePermissions);
    }


    //DB DAN ROLE_PAGE_ID VA PERMISSION ID SI ORQALI RolePagePermission OLAMIZ, AGAR BO'LMASA YANGI RolePagePermission YASAB QAYATRMIZ
    private RolePagePermission makeNewRolePagePermissionOrGetFromDB(RolePage rolePage, PermissionDTO permissionDTO) {

        //DB DAN ROLE_PAGE_ID VA PERMISSION ID SI ORQALI RolePagePermission OLAMIZ
        Optional<RolePagePermission> optionalRolePagePermission = rolePagePermissionRepository.findByRolePageIdAndPermissionId(rolePage.getId(), permissionDTO.getId());

        //AGAR optionalRolePagePermission BO'LSA O'ZINI, AKS HOLDA YANGI RolePagePermission YASAB QAYTARAMIZ
        return optionalRolePagePermission.orElseGet(() -> new RolePagePermission(rolePage, permissionDTO.getId()));
    }

    // MODULENING ICHIDAGI DEPARTMENT_DTO LISTINING ICHIDAGI DEPARTMENT_DTO NING
    // PAGES LISTINING ICHIDA QAYSI PAGE DEFAULT_PAGE SIFATIDA BELGILANGAN PAGE NING ID SINI OLISH.
    // AGAR DEFAULT PAGE UMUMAN BO'LMASA, THROW QILAMIZ
    private Long getDefaultPageIdFromModuleDepartment(List<ModuleDTO> moduleDTOList) {

        //DEAFULT_PAGE QIDIRILYAPTI VA DEFAULT_PAGE TRUE BO'LGAN PAGE LAR FILTER QILINIB LISTGA OLINMOQDA
        List<AuthPageDTO> defaultPageList = moduleDTOList
                .stream()
                .flatMap(moduleDTO -> moduleDTO.getDepartments()
                        .stream()
                        .flatMap(departmentDTO -> departmentDTO.getPages()
                                .stream()
                                .filter(authPageDTO -> authPageDTO.getDefaultPage() != null && authPageDTO.getDefaultPage())))
                .collect(Collectors.toList());

        //DEGAULT_PAGE TRUE BO'LGAN AUTH_PAGE_DTO LISTI 1 GA TENG BO'LISHI SHART.
        // SABABI BITA ROLEDA FAQAT BITTA DEFAULT PAGE BO'LISHI SHART
        if (defaultPageList.size() != 1)
            throw RestException.restThrow(mainService.getMessageByKey("DEFAULT_PAGE_FOR_ROLE_MUST_BE_ONE"), HttpStatus.BAD_REQUEST);

        //THROW GA TUSHMASA LISTNING 0 NING ID SINI QAYTARAMIZ
        return defaultPageList.get(0).getId();
    }

    //TIZIMDA MAVJUD BO'LGAN ROLE_TYPE LARINI OLISH. SYSTEM_ROLE DAN BOSHQALARINI OLISH
    private List<RoleTypeDTO> mapRoleTypeDTOList() {
        return Arrays.stream(RoleType.values()).filter(roleType -> !roleType.equals(SYSTEM_ROLE)).map(this::mapRoleTypeDTO).collect(Collectors.toList());
    }

    // ROLE_TYPE NI DTO GA MAP QILUVCHI METHOD
    private RoleTypeDTO mapRoleTypeDTO(RoleType roleType) {
        return new RoleTypeDTO(roleType.getTitle(), roleType.getDescription(), roleType);
    }

    private List<ModuleDTO> mapToModuleDTOList() {
        return moduleRepository.findAll().stream().map(this::mapToModuleDTO).collect(Collectors.toList());
    }

    private ModuleDTO mapToModuleDTO(Module module) {
        ModuleDTO moduleDTO = mainMapper.toModuleDTO(module);
        moduleDTO.setDepartments(mapToDepartmentDTOList(module));
        return moduleDTO;
    }

    private List<DepartmentDTO> mapToDepartmentDTOList(Module module) {

        List<Department> departmentList = departmentRepository.findAllByModuleId(module.getId());

        return mainMapper.toDepartmentDTOList(departmentList);

    }

    //YUQORIDAGI PERMISSION LISTIDA BOR PERMISSIONLARNI CHECKED FIELDINI TRUE QILIB ROLE_DTO GA SET QILDIK
    private void changeCheckedIfPermissionGivenThisRole(RoleDTO roleDTO, List<Permission> permissions) {
        for (ModuleDTO module : roleDTO.getModules()) {
            for (DepartmentDTO department : module.getDepartments()) {
                for (AuthPageDTO page : department.getPages()) {
                    for (PermissionDTO permissionDTO : page.getPermissions()) {
                        for (Permission permission : permissions) {
                            if (permission.getId().equals(permissionDTO.getId())) {
                                permissionDTO.setChecked(true);
                            }
                        }
                    }
                }
            }
        }
    }

    //O'CHIRMOQCHI BO'LGAN ROLE GA EGA BO'LGAN USER LARGA O'CHIRILAYOTGAN ROLE O'RNIGA BOSHQA ROLE QO'YAMIZ
    private void removeRoleFromUserAndAddOtherRoleToUsers(Role deletingRole, Role insteadOfRole) {

        //O'CHIRILAYOTGAN ROLE BERILGAN USERLAR OLINYAPTI 100 DAN
        List<UUID> allUsersIdByRoleId = userRepository.findAllUsersIdByRoleId(deletingRole.getId(), insteadOfRole.getId());

        userRoleRepository.deleteAllByRoleId(deletingRole.getId());
        List<UserRole> userRoles = new ArrayList<>();

        int i = 0;
        for (UUID userId : allUsersIdByRoleId) {
            userRoles.add(new UserRole(
                    insteadOfRole.getId(),
                    userId,
                    false
            ));
            i++;
            if (i == 10) {
                userRoleRepository.saveAll(userRoles);
                i = 0;
                userRoles = new ArrayList<>();
            }
        }

    }

    //DB DAN ROLE NI NAME BO'YICHA TEKSHIRISH. AGAR SHUNDAY BO'LSA THROW QILAMIZ. BUNDA EDIT DA ID HAM TEKSHIRILADI
    private void existRoleByNameAndIdIfExistThrow(String roleName, Long id) {
        //ID NULL BO'LSA, ROLE ADD QILINAYOTGAN BO'LADI, AKS HOLDA EDIT QILINAYOTGAN BO'LADI
        if ((id == null && roleRepository.existsByName(roleName)) || (id != null && roleRepository.existsByIdNotAndName(id, roleName))) {
            throw RestException.restThrow(mainService.getMessageByKey("ROLE_ALREADY_EXIST"), HttpStatus.BAD_REQUEST);
        }
    }

    //ROLE_TYPE OTHER BO'LMASA VA SHUNDAY ROLE_TYPE LI ROLE BO'LADIGAN BO'LSA THROW QILAMIZ
    private void existRoleByRoleTypeIfExistThrow(RoleType roleType) {
        //ROLE_TYPE OTHER BO'LMASA VA SHUNDAY ROLE_TYPE LI ROLE BO'LADIGAN BO'LSA THROW QILAMIZ
        if (!roleType.equals(RoleType.OTHER) && roleRepository.existsByRoleType(roleType)) {
            throw RestException.restThrow(mainService.getMessageByKey("ROLE_TYPE_ALREADY_EXIST"), HttpStatus.BAD_REQUEST);
        }
    }
}

