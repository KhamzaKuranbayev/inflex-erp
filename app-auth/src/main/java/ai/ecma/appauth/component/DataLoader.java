package ai.ecma.appauth.component;

import ai.ecma.appauth.entity.Module;
import ai.ecma.appauth.entity.*;
import ai.ecma.appauth.entity.enums.AuthPageEnum;
import ai.ecma.appauth.entity.enums.AuthPermissionEnum;
import ai.ecma.appauth.entity.enums.DepartmentEnum;
import ai.ecma.appauth.entity.enums.RoleType;
import ai.ecma.appauth.repository.*;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
//@AllArgsConstructor
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ModuleRepository moduleRepository;
    private final DepartmentRepository departmentRepository;
    private final AuthPageRepository authPageRepository;
    private final PermissionRepository permissionRepository;
    private final UserRoleRepository userRoleRepository;

    @Value("${spring.sql.init.mode}")
    private String initialMode;

    @Value("${service.academic.username}")
    private String academicUsername;

    @Value("${service.academic.password}")
    private String academicPassword;

    @Value("${service.account.username}")
    private String accountUsername;

    @Value("${service.account.password}")
    private String accountPassword;

    @Value("${service.sales.username}")
    private String salesUsername;

    @Value("${service.sales.password}")
    private String salesPassword;

    @Value("${service.hrms.username}")
    private String hrmsUsername;

    @Value("${service.hrms.password}")
    private String hrmsPassword;

    @Value("${service.project.username}")
    private String projectUsername;

    @Value("${service.project.password}")
    private String projectPassword;

    @Value("${service.docs.username}")
    private String docsUsername;

    @Value("${service.docs.password}")
    private String docsPassword;

    @Value("${service.reports.username}")
    private String reportsUsername;

    @Value("${service.reports.password}")
    private String reportsPassword;


    @Override
    public void run(String... strings) {
        if (initialMode.equals("always")) {
            //PROGRAMMA UMRIDA BIRINCHI MARTA RUN BO'LGANDA
            // BOSHLANG'ICH ROLE VA USERLARNI SAQLAB OLADI
            saveInitialRoleAndUser();
        }

        //ROLE_TYPE NI OTHERDAN BOSHQASINI UNIQUE QILISH UCHUN
        roleRepository.executingQueryEveryRunning();
    }


    //PROGRAMMA UMRIDA BIRINCHI MARTA RUN BO'LGANDA
    // BOSHLANG'ICH ROLE VA USERLARNI SAQLAB OLADI
    private void saveInitialRoleAndUser() {
        Role adminRole = Role.builder()
                .name("Admin")
                .description("Tizim boshqaruvchisi")
                .roleType(RoleType.OTHER)
                .build();
        Role studentRole = Role.builder()
                .name("Student")
                .description("Ta'lim oluvchi")
                .roleType(RoleType.STUDENT)
                .build();

        Role systemRole = Role.builder()
                .name("SystemRole")
                .description("Tizim uchun")
                .roleType(RoleType.SYSTEM_ROLE)
                .build();

        roleRepository.saveAll(Arrays.asList(adminRole, studentRole, systemRole));

        User adminUser = User.builder()
                .phoneNumber("+998996791136")
                .password(passwordEncoder.encode("Root_123"))
                .firstName("Admin")
                .lastName("Admin")
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();

        User academicService = User.builder()
                .phoneNumber(academicUsername)
                .password(passwordEncoder.encode(academicPassword))
                .firstName(academicUsername.toUpperCase())
                .lastName(academicUsername.toUpperCase())
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();

        User accountService = User.builder()
                .phoneNumber(accountUsername)
                .password(passwordEncoder.encode(accountPassword))
                .firstName(accountUsername.toUpperCase())
                .lastName(accountUsername.toUpperCase())
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();


        User salesService = User.builder()
                .phoneNumber(salesUsername)
                .password(passwordEncoder.encode(salesPassword))
                .firstName(salesUsername.toUpperCase())
                .lastName(salesUsername.toUpperCase())
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();


        User hrmsService = User.builder()
                .phoneNumber(hrmsUsername)
                .password(passwordEncoder.encode(hrmsPassword))
                .firstName(hrmsUsername.toUpperCase())
                .lastName(hrmsUsername.toUpperCase())
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();

        User projectService = User.builder()
                .phoneNumber(projectUsername)
                .password(passwordEncoder.encode(projectPassword))
                .firstName(projectUsername.toUpperCase())
                .lastName(projectUsername.toUpperCase())
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();

        User docsService = User.builder()
                .phoneNumber(docsUsername)
                .password(passwordEncoder.encode(docsPassword))
                .firstName(docsUsername.toUpperCase())
                .lastName(docsUsername.toUpperCase())
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();

        User reportsService = User.builder()
                .phoneNumber(reportsUsername)
                .password(passwordEncoder.encode(reportsPassword))
                .firstName(reportsUsername.toUpperCase())
                .lastName(reportsUsername.toUpperCase())
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();

        List<User> users = Arrays.asList(adminUser, academicService,
                accountService, salesService,
                hrmsService, projectService, docsService, reportsService);

        userRepository.saveAll(users);

        List<User> adminList = users.stream().filter(user -> user.getPhoneNumber().startsWith("+")).collect(Collectors.toList());

        List<UserRole> userRoles = new ArrayList<>();

        if (adminList.isEmpty())
            userRoles.add(new UserRole(
                    adminRole.getId(),
                    adminUser.getId(),
                    true));

        users = users.stream().filter(user -> !user.getPhoneNumber().startsWith("+")).collect(Collectors.toList());

        users.forEach(user -> userRoles.add(
                new UserRole(
                        systemRole.getId(),
                        user.getId(),
                        true
                )
        ));

        moduleRepository.saveAll(users.stream().map(user -> new Module(user.getPhoneNumber().toUpperCase(),
                user.getPhoneNumber().toUpperCase())).collect(Collectors.toList()));

        Module authModule = moduleRepository.save(new Module("Setting", "AUTH"));

        DepartmentEnum[] departmentEnums = DepartmentEnum.values();

        List<Department> departments = Arrays.stream(departmentEnums).map(departmentEnum -> new Department(departmentEnum.getTitle(), departmentEnum.getName(), authModule)).collect(Collectors.toList());
        departmentRepository.saveAll(departments);


        AuthPageEnum[] authPageEnums = AuthPageEnum.values();

        List<AuthPage> authPages = Arrays.stream(authPageEnums).map(authPageEnum ->
                new AuthPage(
                        authPageEnum.getTitle(),
                        authPageEnum.getName(),
                        departments.stream().filter(department -> department.getName().equals(authPageEnum.getDepartment().getName())).collect(Collectors.toList()).get(0))).collect(Collectors.toList());
        authPageRepository.saveAll(authPages);


        AuthPermissionEnum[] authPermissionEnums = AuthPermissionEnum.values();

        List<Permission> permissions = Arrays.stream(authPermissionEnums).filter(AuthPermissionEnum::isThisModulePermission).map(authPermissionEnum -> new Permission(
                        authPermissionEnum.getTitle(),
                        authPermissionEnum.getName(),
                        authPages.stream().filter(authPage -> authPage.getName().equals(authPermissionEnum.getPage().getName())).collect(Collectors.toList()).get(0)))
                .collect(Collectors.toList());
        permissionRepository.saveAll(permissions);

    }
}
