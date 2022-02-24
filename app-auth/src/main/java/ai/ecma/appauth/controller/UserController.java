package ai.ecma.appauth.controller;

import ai.ecma.appauth.component.aop.other.annotation.CheckPermission;
import ai.ecma.appauth.payload.*;
import ai.ecma.appauth.utils.RestConstants;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static ai.ecma.appauth.entity.enums.AuthPermissionEnum.*;

@RequestMapping(path = UserController.USER_CONTROLLER_PATH)
public interface UserController {
    String USER_CONTROLLER_PATH = RestConstants.BASE_PATH_V1 + "user";
    String CHECK_USER_OR_PERMISSION_PATH = "/check-user-or-permission";
    String USER_ME_PATH = "/me";
    String ADD_STUDENT_PATH = "/add-student";
    String EDIT_STUDENT_PATH = "/edit-student";
    String DELETE_STUDENT_PATH = "/delete-student";
    String ADD_STAFF_PATH = "/add-staff";
    String EDIT_STAFF_PATH = "/edit-staff";
    String DELETE_STAFF_PATH = "/delete-staff";
    String EDIT_USER_PATH_PATH = "/edit-user";
    String EDIT_USER_PASSWORD_PATH = "/edit-user-password";
    String TWO_STEP_VERIFICATION_PATH = "/two-step-verification";
    String CHANGE_EMAIL_TWO_STEP_VERIFICATION_PATH = "/change-email-two-step-verification";
    String VERIFY_TWO_STEP_VERIFICATION_PATH = "/verify-two-step-verification";
    String FORGOT_TWO_STEP_VERIFICATION_PASSWORD_PATH = "/forgot-two-step-verification-password";
    String RESET_TWO_STEP_VERIFICATION_PASSWORD_PATH = "/reset-two-step-verification-password";
    String CHECK_TWO_STEP_VERIFICATION_PASWORD_PATH = "check-two-step-verification-password";
    String SET_SECURITY_QUESTION_PATH = "/set-security-question";
    String PAGE_PERMISSION_PATH = "/page-permission";
    String CHECK_EMAIL_CODE_FOR_TWO_STEP_VERIFICATION_FORGOT_PASSWORD_PATH = "check-email-code-two-step-forgot-password";

    // MAS`UL XODIM TOMONIDAN TIZIMGA YANGI STUDENT QO`SHISH
    @CheckPermission(permissions = {ADD_STUDENT})
    @PostMapping(ADD_STUDENT_PATH)
    ApiResult<String> addStudent(@Valid @RequestBody StudentDTO studentDTO);


    // MAS`UL XODIM TOMONIDAN QO`SHILGAN STUDENTNI TAHRIRLASH (EDIT QILISH)
    @CheckPermission(permissions = {EDIT_STUDENT})
    @PutMapping(EDIT_STUDENT_PATH + "/{id}")
    ApiResult<String> editStudent(@Valid @RequestBody StudentDTO studentDTO, @PathVariable UUID id);


    // MAS`UL XODIM TOMONIDAN  STUDENTNI TIZIMDAN O`CHIRISH
    @CheckPermission(permissions = {DELETE_STUDENT})
    @DeleteMapping(DELETE_STUDENT_PATH + "/{id}")
    ApiResult<String> deleteStudent(@PathVariable UUID id);


    // MAS`UL XODIM TOMONIDAN TIZIMGA YANGI XODIM QO`SHISH
    @CheckPermission(permissions = {ADD_STAFF})
    @PostMapping(ADD_STAFF_PATH)
    ApiResult<String> addStaff(@Valid @RequestBody StaffDTO staffDto);


    //TIZIMDAGI MAS`UL XODIM TOMONIDAN QO`SHILGAN XODIMNI TAHRIRLASH (EDIT QILISH)
    @CheckPermission(permissions = {EDIT_STAFF})
    @PutMapping(EDIT_STAFF_PATH + "/{id}")
    ApiResult<String> editStaff(@Valid @RequestBody StaffDTO staffDto, @PathVariable UUID id);


    //MAS`UL XODIM TOMONIDAN XODIMNI TIZIMDAN O`CHIRISH
    @CheckPermission(permissions = {DELETE_STAFF})
    @DeleteMapping(DELETE_STAFF_PATH + "/{id}")
    ApiResult<String> deleteStaff(@PathVariable UUID id);


    //USER O'ZINI EDIT QILSA SHU YO'LGA KELADI
    @PutMapping(EDIT_USER_PATH_PATH)
    ApiResult<String> editUser(@RequestBody UserEditDTO userEditDTO);

    //USER O'ZINI PAROLINI EDIT QILSA SHU YO'LGA KELADI
    @PutMapping(EDIT_USER_PASSWORD_PATH)
    ApiResult<String> editUserPassword(@RequestBody UserEditDTO userEditDTO);


    //  TIZIMDAGI (YA`NI SECURITY_CONTEXTDAGI) USERDA, BERILGAN PERMISSIONLAR BORLIGINI TEKSHIRISH
    @PostMapping(path = CHECK_USER_OR_PERMISSION_PATH)
    ApiResult<UserDTO> checkUser(@RequestBody(required = false) List<String> permissions);


    //TIZIMDAGI (YA`NI SECURITY_CONTEXTDAGI) USER O`ZINING MALUMOTLARINI OLISHI
    @GetMapping(path = USER_ME_PATH)
    ApiResult<UserDTO> getUserMe();


    //TIZIMDAGI (YA`NI SECURITY_CONTEXTDAGI) USER NING BERILGAN PAGE DA MAVJUD BO'LGAN PERMISSIONLARINI OLISH
    @GetMapping(path = PAGE_PERMISSION_PATH + "/{pageId}")
    ApiResult<List<String>> getPagePermission(@PathVariable Long pageId);

    //FOYDALANUVCHI TWO STEP VERIFICATION QO'YGANDA
    @PostMapping(path = TWO_STEP_VERIFICATION_PATH)
    ApiResult<String> setTwoStepVerification(@Valid @RequestBody TwoStepVerificationDTO twoStepVerificationDTO);

    //FOYDALANUVCHI TWO STEP VERIFICATION O'RNATISH UCHUN EMAILGA YUBORILGAN CODE ORQALI XAVFSIZLIKNI YOQMOQCHI BO'LGANDA
    @GetMapping(path = VERIFY_TWO_STEP_VERIFICATION_PATH)
    ApiResult<String> verifyTwoStepVerification(@RequestParam String code);

    //FOYDALANUVCHI TWO STEP VERIFICATION PAROLINI YANGILASH
    @PutMapping(path = TWO_STEP_VERIFICATION_PATH)
    ApiResult<String> changeTwoStepVerificationPassword(@Valid @RequestBody TwoStepVerificationDTO twoStepVerificationDTO);

    //FOYDALANUVCHI TWO STEP VERIFICATION NI O'CHIRMOQCHI BO'LGANDA
    @PatchMapping(path = TWO_STEP_VERIFICATION_PATH)
    ApiResult<String> turnOffTwoStepVerification(@Valid @RequestBody TwoStepVerificationDTO twoStepVerificationDTO);

    //FOYDALANUVCHI TWO STEP VERIFICATION UCHUN BIRIKTRILGAN EMAILNI ALMASHTIRISH
    @PatchMapping(path = CHANGE_EMAIL_TWO_STEP_VERIFICATION_PATH)
    ApiResult<String> changeEmailTwoStepVerification(@Valid @RequestBody TwoStepVerificationDTO twoStepVerificationDTO);

//    //FOYDALANUVCHI TWO STEP VERIFICATION PAROLNI UNUTGANDA
//    @PatchMapping(path = FORGOT_TWO_STEP_VERIFICATION_PASSWORD_PATH)
//    ApiResult<String> forgotTwoStepVerificationPassword();

}
