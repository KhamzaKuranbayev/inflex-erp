package ai.ecma.appauth.service;

import ai.ecma.appauth.entity.AuthPage;
import ai.ecma.appauth.entity.Permission;
import ai.ecma.appauth.entity.User;
import ai.ecma.appauth.entity.UserRole;
import ai.ecma.appauth.entity.enums.AuthPermissionEnum;
import ai.ecma.appauth.entity.enums.RoleType;
import ai.ecma.appauth.exceptions.RestException;
import ai.ecma.appauth.mapper.MainMapper;
import ai.ecma.appauth.mapper.UserMapper;
import ai.ecma.appauth.payload.*;
import ai.ecma.appauth.repository.AuthPageRepository;
import ai.ecma.appauth.repository.PermissionRepository;
import ai.ecma.appauth.repository.UserRepository;
import ai.ecma.appauth.repository.UserRoleRepository;
import ai.ecma.appauth.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final PermissionRepository permissionRepository;
    private final UserRepository userRepository;
    private final AuthServiceImpl authServiceImpl;
    private final MainService mainService;
    private final PasswordEncoder passwordEncoder;
    private final MainMapper mainMapper;
    private final AuthPageRepository authPageRepository;
    private final UserRoleRepository userRoleRepository;

    @Override
    public ApiResult<UserDTO> checkUser(List<String> permissions) {

        //TIZIMDAGI (SECURITY_CONTEXT DAGI) MAVJUD USERNI ANIQLAB OLAMIZ
        User user = mainService.getUserFromSecurityContextIfNullThrow();

        //USERNI PEMISSIONLARINI TEKSHIRIB VA USERDAGI PERMISSIONLAR LISTINI QAYTARAMIZ
        List<Permission> userPermissions = checkPermissionAndReturnPermissionList(user.getId(), permissions);

        //USERNI USER_DTO GA O`GIRAMIZ
        UserDTO userDTO = userMapper.toUserDTO(user);

        //USERNING PERMISSIONLARINI  PERMESSION_DTO GA O`GIRAMIZ, USER_DTOGA SET QILAMIZ VA NATIJANI QAYTARAMIZ
        userDTO.setPermissions(mainMapper.toPermissionDTOList(userPermissions));

        return ApiResult.successResponse(userDTO);
    }

    @Override
    public ApiResult<UserDTO> getUserMe() {
        //TIZIMDAGI (SECURITY_CONTEXT DAGI) MAVJUD USERNI ANIQLAB OLAMIZ
        User user = mainService.getUserFromSecurityContextIfNullThrow();

        //USERNI USER_DTO GA O`GIRIB NATIJANI QAYTARAMIZ
        UserDTO userDTO = userMapper.toUserDTO(user);

        //DB DAN USERGA BIRIKTIRILGAN ROLE GA TEGISHLI PAGE LAR OLINDI
        List<AuthPage> userAuthPageList = authPageRepository.findAllByUserId(user.getId());

        //userAuthPageList AYLANIB MAPPER ORQALI AUTH_PAGE_DTO GA O'GIRILYAPTI VA authPageDTOList LISTGA JOYLANYAPTI
        List<AuthPageDTO> authPageDTOList = new ArrayList<>();
        userAuthPageList.forEach(authPage -> {

            //AUTH_PAGE_DTO GA PERMISSIONLARNI SET QILMASLIK UCHUN
            authPage.setPermissions(null);

            //authPageDTOList LISTGA JOYLANYAPTI
            authPageDTOList.add(mainMapper.toAuthPageDTO(authPage));
        });

        //USER_DTO GA SET QILINYAPTI AUTH_PAGE_DTO LIST
        userDTO.setPages(authPageDTOList);

        return ApiResult.successResponse(userDTO);
    }

    @Override
    public ApiResult<List<String>> getPagePermission(Long pageId) {
        CommonUtils.setUserToSecurityContext(null);
        User user = mainService.getUserFromSecurityContextIfNullThrow();
        List<String> permissions = permissionRepository.getPermissionsByUserIdAndPageId(user.getId(), pageId);
        return ApiResult.successResponse(permissions);
    }

    @Override
    public ApiResult<String> addStudent(StudentDTO studentDTO) {

        authServiceImpl.checkPhoneIfExistThrow(studentDTO.getPhoneNumber(), null);
        User user = userMapper.toStudent(studentDTO);
        userRepository.save(user);
        userRoleRepository.save(new UserRole(
                authServiceImpl.getRoleIdByRoleType(RoleType.STUDENT),
                user.getId(),
                true
        ));
        return ApiResult.successResponse(mainService.getMessageByKey("SUCCESSFULLY_STUDENT_ADDED"));
    }


    @Override
    public ApiResult<String> editStudent(StudentDTO studentDTO, UUID id) {
        //USERNI ID ORQALI OLISH, AGAR TOPOLMASA THROW QILISH
        User user = getUserByIdAfterThrow(id);

        // O'ZGARTIRMOQCHI BO'LGAN TELEFON RAQAM BAZADA ALLAQACHON BORMI YOKI YO'QMI TEKSHIRILYAPTI
        authServiceImpl.checkPhoneIfExistThrow(studentDTO.getPhoneNumber(), id);

        //STUDENT_DTO ORQALI USER NI UPDATE QILISH
        studentDTO.setId(id);
        userMapper.updateStudent(studentDTO, user);
        userRepository.save(user);

        return ApiResult.successResponse(mainService.getMessageByKey("SUCCESSFULLY_STUDENT_EDITED"));
    }

    @Override
    public ApiResult<String> deleteStudent(UUID id) {
        userRepository.deleteById(id);
        return ApiResult.successResponse(mainService.getMessageByKey("SUCCESSFULLY_STUDENT_DELETED"));
    }

    @Override
    public ApiResult<String> addStaff(StaffDTO staffDTO) {
//        //STAFF_DTO DA KELGAN TELEFON RAQAMLI USER BORLIGINI TEKSHIRAMIZ
//        authServiceImpl.checkPhoneIfExistThrow(staffDTO.getPhoneNumber(), null);
//
//        //STAFF_DTO ICHIDAGI ROLE_ID BORLIGINI TEKSHIRAMIZ
//        roleService.checkRoleIfNotExistThrow(staffDTO.getRoleId());
//
//        //STAFF_DTO ORQALI USER NI YASASH
//        User user = userMapper.toStaff(staffDTO);
//
//        userRepository.save(user);
//
//        userRoleRepository.save(new UserRole(
//                staffDTO.getRoleId(),
//                user.getId(),
//                true
//        ));

        return ApiResult.successResponse(mainService.getMessageByKey("SUCCESSFULLY_STAFF_ADDED"));
    }

    @Override
    public ApiResult<String> editStaff(StaffDTO staffDTO, UUID id) {
//        //USERNI ID ORQALI OLISH, AGAR TOPOLMASA THROW QILISH
//        User user = getUserByIdAfterThrow(id);
//
//        // O'ZGARTIRMOQCHI BO'LGAN TELEFON RAQAM BAZADA ALLAQACHON BORMI YOKI YO'QMI TEKSHIRILYAPTI
//        authServiceImpl.checkPhoneIfExistThrow(staffDTO.getPhoneNumber(), id);
//
//        // O'ZGARTIRMOQCHI BO'LGAN ROLE  BAZADA  BORMI YOKI YO'QMI TEKSHIRILYAPTI
//        roleService.checkRoleIfNotExistThrow(staffDTO.getRoleId());
//
//        //STAFF_DTO ORQALI USER NI UPDATE QILISH
//        staffDTO.setId(id);
//        userMapper.updateStaff(staffDTO, user);
//
//        //STAFFNI SAQLASH
//        userRepository.save(user);
        return ApiResult.successResponse(mainService.getMessageByKey("SUCCESSFULLY_STAFF_EDITED"));
    }

    @Override
    public ApiResult<String> deleteStaff(UUID id) {
        userRepository.deleteById(id);
        return ApiResult.successResponse(mainService.getMessageByKey("SUCCESSFULLY_STAFF_DELETED"));
    }

    @Override
    public ApiResult<String> editUser(UserEditDTO userEditDTO) {

        //TIZIMDAGI (SECURITY_CONTEXT DAGI) MAVJUD USERNI ANIQLAB OLAMIZ
        User currentUser = mainService.getUserFromSecurityContextIfNullThrow();

        //USERNING PAROLI TO`G`RILIGINI TEKSHIRISH
        mainService.checkUserPassword(userEditDTO.getCurrentPassword(), currentUser.getPassword());

        //USER_EDIT_DTO ORQALI USER MALUMOTLARINI UPDATE QILISH
        userMapper.updateUser(userEditDTO, currentUser);
        userRepository.save(currentUser);

        return ApiResult.successResponse(mainService.getMessageByKey("SUCCESSFULLY_STAFF_EDITED"));
    }

    @Override
    public ApiResult<String> editUserPassword(UserEditDTO userEditDTO) {

        //TIZIMDAGI (SECURITY_CONTEXT DAGI) MAVJUD USERNI ANIQLAB OLAMIZ
        User currentUser = mainService.getUserFromSecurityContextIfNullThrow();

        //USERNING PAROLI TO`G`RILIGINI TEKSHIRISH
        mainService.checkUserPassword(userEditDTO.getCurrentPassword(), currentUser.getPassword());

        //NEW_PASSWORD VA PRE_PASSWORD MOSLIGINI TEKSHIRISH
        mainService.checkPasswordsEqualityIfErrorThrow(userEditDTO.getNewPassword(), userEditDTO.getPrePassword());
        currentUser.setPassword(passwordEncoder.encode(userEditDTO.getNewPassword()));

        //BARCHASI TO`G`RI BO`LSA USERNI SAQLASH
        userRepository.save(currentUser);
        return ApiResult.successResponse(mainService.getMessageByKey("PASSWORD_SUCCESSFULLY_CHANGED"));
    }

    @Override
    public ApiResult<String> setTwoStepVerification(TwoStepVerificationDTO twoStepVerificationDTO) {

        //TIZIMDAGI (SECURITY_CONTEXT DAGI) MAVJUD USERNI ANIQLAB OLAMIZ
        User currentUser = mainService.getUserFromSecurityContextIfNullThrow();

        //IKKI BOSQICHLI XAVFSIZLIK OLDIN YOQILGAN BO`LSA THROW
        if (currentUser.getTwoStepVerification() != null && currentUser.getTwoStepVerification())
            throw RestException.restThrow(mainService.getMessageByKey("TWO_STEP_VERIFICATION_ALREADY_ACTIVATED"), HttpStatus.BAD_REQUEST);

        //NEW_PASSWORD VA PRE_PASSWORD MOSLIGINI TEKSHIRISH
        mainService.checkPasswordsEqualityIfErrorThrow(twoStepVerificationDTO.getPassword(), twoStepVerificationDTO.getPrePassword());

        //HINT HAMDA PASSWORD BIR XIL EMASLIGINI TEKSHIRAMIZ. BIR XIL BO`LSA THROW
        mainService.checkHintContainsPassword(twoStepVerificationDTO.getHint(), twoStepVerificationDTO.getPassword());

        currentUser.setTempEmailTwoStepVerification(twoStepVerificationDTO.getEmail());

        //EMAILGA XABAR YUBORADI VA YUBORILADIGAN TASDIQLASH KODINI QAYTARADI
        String verificationCode = mainService.sendVerificationCodeToEmailAndReturnVerificationCode(twoStepVerificationDTO.getEmail(), true);

        currentUser.setTwoStepVerficationCode(verificationCode);
        currentUser.setTwoStepPassword(passwordEncoder.encode(twoStepVerificationDTO.getPassword()));
        currentUser.setTwoStepPasswordHint(twoStepVerificationDTO.getHint());
        userRepository.save(currentUser);

        //TELEFON YOKI EMAIL QISMAN (+99899***1136 YOKI si **** ma@gmail.com) YOPILGAN HOLATDA BERILADI
        String message = mainService.makeMessageSendingPhoneNumberOrEmail(twoStepVerificationDTO.getEmail());

        return ApiResult.successResponse(message);
    }

    @Override
    public ApiResult<String> verifyTwoStepVerification(String code) {

        //TIZIMDAGI (SECURITY_CONTEXT DAGI) MAVJUD USERNI ANIQLAB OLAMIZ
        User currentUser = mainService.getUserFromSecurityContextIfNullThrow();

        //EMAILGA YUBORILGAN TASDIQLOVCHI KODNING MOSLIGINI TEKSHIRISH
        if (!code.equals(currentUser.getTwoStepVerficationCode()))
            throw RestException.restThrow(mainService.getMessageByKey("CONFIRMATION_CODE_INCORRECT"), HttpStatus.BAD_REQUEST);

        currentUser.setTwoStepVerficationCode(null);
        currentUser.setEmailTwoStepVerification(currentUser.getTempEmailTwoStepVerification());
        currentUser.setTempEmailTwoStepVerification(null);
        currentUser.setTwoStepVerification(true);
        userRepository.save(currentUser);

        return ApiResult.successResponse(mainService.getMessageByKey("TWO_STEP_VERIFICATION_ACTIVATED"));
    }

    @Override
    public ApiResult<String> changeTwoStepVerificationPassword(TwoStepVerificationDTO twoStepVerificationDTO) {

        //TIZIMDAGI (SECURITY_CONTEXT DAGI) MAVJUD USERNI ANIQLAB OLAMIZ
        User currentUser = mainService.getUserFromSecurityContextIfNullThrow();

        //USERDA IKKI BOSQICHLI XAVFSIZLIK ILGARI AKTIVLASHTIRILMAGAN BO`LSA THROW
        mainService.checkTwoStepVerificationOnIfOffThrow(currentUser);

        //NEW_PASSWORD VA PRE_PASSWORD MOSLIGINI TEKSHIRISH
        mainService.checkPasswordsEqualityIfErrorThrow(twoStepVerificationDTO.getPassword(), twoStepVerificationDTO.getPrePassword());

        //HINT HAMDA PASSWORD BIR XIL EMASLIGINI TEKSHIRAMIZ. BIR XIL BO`LSA THROW
        mainService.checkHintContainsPassword(twoStepVerificationDTO.getHint(), twoStepVerificationDTO.getPassword());

        //USERNING IKKI BOSQICHLI XAVFSIZLIK PAROLI TO`G`RILIGINI TEKSHIRISH
        mainService.checkUserPassword(twoStepVerificationDTO.getOldPassword(), currentUser.getTwoStepPassword());

        currentUser.setTwoStepPassword(passwordEncoder.encode(twoStepVerificationDTO.getPassword()));
        currentUser.setTwoStepPasswordHint(twoStepVerificationDTO.getHint());
        userRepository.save(currentUser);

        return ApiResult.successResponse(mainService.getMessageByKey("PASSWORD_SUCCESSFULLY_CHANGED"));
    }

    @Override
    public ApiResult<String> turnOffTwoStepVerification(TwoStepVerificationDTO twoStepVerificationDTO) {

        //TIZIMDAGI (SECURITY_CONTEXT DAGI) MAVJUD USERNI ANIQLAB OLAMIZ
        User currentUser = mainService.getUserFromSecurityContextIfNullThrow();

        //USERDA IKKI BOSQICHLI XAVFSIZLIK ILGARI AKTIVLASHTIRILMAGAN BO`LSA THROW
        mainService.checkTwoStepVerificationOnIfOffThrow(currentUser);

        //USERNING IKKI BOSQICHLI XAVFSIZLIK PAROLI TO`G`RILIGINI TEKSHIRISH
        mainService.checkUserPassword(twoStepVerificationDTO.getOldPassword(), currentUser.getTwoStepPassword());

        currentUser.setTwoStepPassword(null);
        currentUser.setTempEmailTwoStepVerification(null);
        currentUser.setTwoStepPasswordHint(null);
        currentUser.setEmailTwoStepVerification(null);
        currentUser.setTwoStepVerification(false);
        userRepository.save(currentUser);

        return ApiResult.successResponse(mainService.getMessageByKey("TWO_STEP_VERIFICATION_DELETED"));
    }

    @Override
    public ApiResult<String> changeEmailTwoStepVerification(TwoStepVerificationDTO twoStepVerificationDTO) {

        //TIZIMDAGI (SECURITY_CONTEXT DAGI) MAVJUD USERNI ANIQLAB OLAMIZ
        User currentUser = mainService.getUserFromSecurityContextIfNullThrow();

        //USERDA IKKI BOSQICHLI XAVFSIZLIK ILGARI AKTIVLASHTIRILMAGAN BO`LSA THROW
        mainService.checkTwoStepVerificationOnIfOffThrow(currentUser);

        //USERNING IKKI BOSQICHLI XAVFSIZLIK PAROLI TO`G`RILIGINI TEKSHIRISH
        mainService.checkUserPassword(twoStepVerificationDTO.getOldPassword(), currentUser.getTwoStepPassword());

        //USERNING TWO STEP VERIFICATION UCHUN AVVALGI QO'YGAN EMAILI YANGISI BILAN BIR XIL BO`LMASLIGINI TEKSHIRISH. AKS HOLDA THROW
        if (twoStepVerificationDTO.getEmail().equals(currentUser.getEmailTwoStepVerification()))
            throw RestException.restThrow(mainService.getMessageByKey("TWO_STEP_VERIFICATION_EMAIL_EXIST"), HttpStatus.BAD_REQUEST);

        //EMAILGA XABAR YUBORADI VA YUBORILADIGAN TASDIQLASH KODINI QAYTARADI
        String verificationCode = mainService.sendVerificationCodeToEmailAndReturnVerificationCode(twoStepVerificationDTO.getEmail(), true);

        currentUser.setTempEmailTwoStepVerification(twoStepVerificationDTO.getEmail());
        currentUser.setTwoStepVerficationCode(verificationCode);
        userRepository.save(currentUser);

        //TELEFON YOKI EMAIL QISMAN (+99899***1136 YOKI si **** ma@gmail.com) YOPILGAN HOLATDA BERILADI
        String message = mainService.makeMessageSendingPhoneNumberOrEmail(twoStepVerificationDTO.getEmail());

        return ApiResult.successResponse(message);
    }

//    @Override
//    public ApiResult<String> forgotTwoStepVerificationPassword() {
//
//        //TIZIMDAGI (SECURITY_CONTEXT DAGI) MAVJUD USERNI ANIQLAB OLAMIZ
//        User currentUser = mainService.getUserFromSecurityContextIfNullThrow();
//
//        //IKKI BOSQICHLI XAVFSIZLIK PAROLI UNUTILGAN BO'LSA SHU METHOD ISHLAYDI VA YUBORGANLIGI HAQIDA
//        // EMAIL QISMAN ( si **** ma@gmail.com) YOPILGAN HOLATDA BERILADI
//        String message = mainService.sendVerificationCodeForgotTwoStepVerificationPasswordAndReturnMessage(currentUser);
//
//        return ApiResult.successResponse(message);
//    }


    ////////////----------------------USHBU CLASSNING PRIVATE METHODLARI----------------------///////////

    //PERMISIONNI TEKSHIRISH
    //todo ko'rib chiq
    public void checkPermission(UUID userId, List<AuthPermissionEnum> permissions) {
        permissionRepository.findFirstByUserIdAndPermissions(userId, permissions.stream().map(AuthPermissionEnum::getName).collect(Collectors.toList()))
                .orElseThrow(() -> RestException.restThrow(mainService.getMessageByKey("PERMISSION_DENIED"), HttpStatus.FORBIDDEN));
    }

    //BERILGAN PERMISSIONLAR USERDA BORLIGINI TEKSHIRISH HAMDA USERDA MAVUD PERMISSIONLARNI QAYTARSIH
    public List<Permission> checkPermissionAndReturnPermissionList(UUID userId, List<String> permissions) {

        //TODO KO'RIB CHIQ
        //USERNING BARCHA PERMESSIONLARINI OLIB KELAMIZ
        List<Permission> userPermissions = permissionRepository.findAllPermissionsByUserId(userId);

        //METHODGA BERIB YUBORILGAN PERMISSIONLARNI USERNING PERMISSIONLARNING ICHIDA BORLARI BILAN MOS KELGANLARINI OLYAPMIZ
        List<String> existPermissions = permissions.stream().filter(s -> userPermissions.stream().anyMatch(permission -> permission.getName().equals(s))).collect(Collectors.toList());

        //METHODGA BERIB YUBORILGAN PERMISSIONLARNI USERNING PERMISSIONLARNING
        // ICHIDA BORLARI BILAN MOS KELGANLARINI OLGAN LISTIMIZ BILAN BIR XILLIGINI TAQQOSLAYAPMIZ. BIR XIL BO'LMASA THROW
        if (existPermissions.size() != permissions.size())
            throw RestException.restThrow(mainService.getMessageByKey("USER_FORBIDDEN"), HttpStatus.FORBIDDEN);

        return userPermissions;
    }

    private User getUserByIdAfterThrow(UUID userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                RestException.restThrow("user", "id", userId, mainService.getMessageByKey("USER_NOT_FOUND_OR_DISABLED")));
    }


}
