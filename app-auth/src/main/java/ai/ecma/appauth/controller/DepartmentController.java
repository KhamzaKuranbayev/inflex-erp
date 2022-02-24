package ai.ecma.appauth.controller;

import ai.ecma.appauth.payload.ApiResult;
import ai.ecma.appauth.payload.DepartmentDTO;
import ai.ecma.appauth.service.DepartmentService;
import ai.ecma.appauth.utils.RestConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = DepartmentController.DEPARTMENT_CONTROLLER_PATH)
@RequiredArgsConstructor
@Slf4j
@Api(tags = "Department")
public class DepartmentController {

    static final String DEPARTMENT_CONTROLLER_PATH = RestConstants.BASE_PATH_V1 + "department";

    private final DepartmentService departmentService;

    @ApiOperation(value = "",hidden = true)
    @PostMapping
    public ApiResult<Boolean> addDepartment(@Valid @RequestBody List<DepartmentDTO> departmentDTOList) {
        log.info("addDepartment method entered: {}", departmentDTOList);
        ApiResult<Boolean> result = departmentService.addDepartment(departmentDTOList);
        log.info("addDepartment method exited: {}", result);
        return result;
    }


    @ApiOperation(value = "Edit Department")
    @PutMapping
    public ApiResult<String> editDepartment(@Valid @RequestBody List<DepartmentDTO> departmentDTOList) {
        log.info("editDepartment method entered: {}", departmentDTOList);
        ApiResult<String> result = departmentService.editDepartment(departmentDTOList);
        log.info("editDepartment method exited: {}", result);
        return result;
    }

}
