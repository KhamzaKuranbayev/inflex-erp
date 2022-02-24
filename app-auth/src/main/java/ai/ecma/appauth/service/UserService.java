package ai.ecma.appauth.service;

import ai.ecma.appauth.payload.*;

import java.util.List;
import java.util.UUID;

public interface UserService {
    ApiResult<String> addStudent(StudentDTO studentDTO);

    ApiResult<String> editStudent(StudentDTO studentDTO, UUID id);

    ApiResult<String> deleteStudent(UUID id);

    ApiResult<UserDTO> checkUser(List<String> permissions);

    ApiResult<UserDTO> getUserMe();

    ApiResult<List<String>> getPagePermission(Long pageId);

    ApiResult<String> addStaff(StaffDTO staffDto);

    ApiResult<String> editStaff(StaffDTO staffDto, UUID id);

    ApiResult<String> deleteStaff(UUID id);

    ApiResult<String> editUser(UserEditDTO userEditDTO);

    ApiResult<String> editUserPassword(UserEditDTO userEditDTO);

    ApiResult<String> setTwoStepVerification(TwoStepVerificationDTO twoStepVerificationDTO);

    ApiResult<String> verifyTwoStepVerification(String code);

    ApiResult<String> changeTwoStepVerificationPassword(TwoStepVerificationDTO twoStepVerificationDTO);

    ApiResult<String> turnOffTwoStepVerification(TwoStepVerificationDTO twoStepVerificationDTO);

    ApiResult<String> changeEmailTwoStepVerification(TwoStepVerificationDTO twoStepVerificationDTO);


}
