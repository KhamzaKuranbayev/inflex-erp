package ai.ecma.appauth.controller;

import ai.ecma.appauth.payload.*;
import ai.ecma.appauth.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {


    private final UserService userService;

    @Override
    public ApiResult<UserDTO> checkUser(List<String> permissions) {
        log.info("checkUser method entered: {}", permissions);
        ApiResult<UserDTO> result = userService.checkUser(permissions);
        log.info("checkUser method exited: {}", result);
        return result;
    }

    @Override
    public ApiResult<UserDTO> getUserMe() {
        log.info("GetUserMe method entered");
        ApiResult<UserDTO> result = userService.getUserMe();
        log.info("GetUserMe method exited: {}", result);
        return result;
    }

    @Override
    public ApiResult<List<String>> getPagePermission(Long pageId) {
        log.info("GetPagePermission method entered");
        ApiResult<List<String>> result = userService.getPagePermission(pageId);
        log.info("GetPagePermission method exited: {}", result);
        return result;
    }

    @Override
    public ApiResult<String> addStudent(StudentDTO studentDTO) {
        log.info("addStudent method entered");
        ApiResult<String> result = userService.addStudent(studentDTO);
        log.info("addStudent method exited: {}", result);
        return result;
    }

    @Override
    public ApiResult<String> editStudent(StudentDTO studentDTO, UUID id) {
        log.info("editStudent method entered");
        ApiResult<String> result = userService.editStudent(studentDTO, id);
        log.info("editStudent method exited: {}", result);
        return result;
    }

    @Override
    public ApiResult<String> deleteStudent(UUID id) {
        log.info("deleteStudent method entered");
        ApiResult<String> result = userService.deleteStudent(id);
        log.info("deleteStudent method entered");
        return result;
    }

    @Override
    public ApiResult<String> addStaff(StaffDTO staffDto) {
        log.info("addStaff method entered");
        ApiResult<String> result = userService.addStaff(staffDto);
        log.info("addStaff method exited: {}", result);
        return result;
    }

    @Override
    public ApiResult<String> editStaff(StaffDTO staffDto, UUID id) {
        log.info("editStaff method entered");
        ApiResult<String> result = userService.editStaff(staffDto, id);
        log.info("editStaff method exited: {}", result);
        return result;
    }

    @Override
    public ApiResult<String> deleteStaff(UUID id) {
        log.info("deleteStaff method entered");
        ApiResult<String> result = userService.deleteStaff(id);
        log.info("deleteStaff method entered");
        return result;
    }

    @Override
    public ApiResult<String> editUser(UserEditDTO userEditDTO) {
        log.info("editUser method entered");
        ApiResult<String> result = userService.editUser(userEditDTO);
        log.info("editUser method entered");
        return result;
    }

    @Override
    public ApiResult<String> editUserPassword(UserEditDTO userEditDTO) {
        log.info("editUserPassword method entered");
        ApiResult<String> result = userService.editUserPassword(userEditDTO);
        log.info("editUserPassword method entered");
        return result;
    }

    @Override
    public ApiResult<String> setTwoStepVerification(TwoStepVerificationDTO twoStepVerificationDTO) {
        log.info("setTwoStepVerification method entered");
        ApiResult<String> apiResult = userService.setTwoStepVerification(twoStepVerificationDTO);
        log.info("setTwoStepVerification method exited");
        return apiResult;
    }

    @Override
    public ApiResult<String> verifyTwoStepVerification(String code) {
        log.info("verifyTwoStepVerification method entered");
        ApiResult<String> apiResult = userService.verifyTwoStepVerification(code);
        log.info("verifyTwoStepVerification method exited");
        return apiResult;
    }

    @Override
    public ApiResult<String> changeTwoStepVerificationPassword(TwoStepVerificationDTO twoStepVerificationDTO) {
        log.info("changeTwoStepVerificationPassword method entered");
        ApiResult<String> apiResult = userService.changeTwoStepVerificationPassword(twoStepVerificationDTO);
        log.info("changeTwoStepVerificationPassword method exited");
        return apiResult;
    }

    @Override
    public ApiResult<String> turnOffTwoStepVerification(TwoStepVerificationDTO twoStepVerificationDTO) {
        log.info("turnOffTwoStepVerification method entered");
        ApiResult<String> apiResult = userService.turnOffTwoStepVerification(twoStepVerificationDTO);
        log.info("turnOffTwoStepVerification method exited");
        return apiResult;
    }

    @Override
    public ApiResult<String> changeEmailTwoStepVerification(TwoStepVerificationDTO twoStepVerificationDTO) {
        log.info("changeEmailTwoStepVerification method entered");
        ApiResult<String> apiResult = userService.changeEmailTwoStepVerification(twoStepVerificationDTO);
        log.info("changeEmailTwoStepVerification method exited");
        return apiResult;
    }

//    @Override
//    public ApiResult<String> forgotTwoStepVerificationPassword() {
//        log.info("forgotTwoStepVerificationPassword method entered");
//        ApiResult<String> apiResult = userService.forgotTwoStepVerificationPassword();
//        log.info("forgotTwoStepVerificationPassword method exited");
//        return apiResult;
//    }
}
