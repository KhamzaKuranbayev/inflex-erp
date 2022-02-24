package ai.ecma.appauth.controller;

import ai.ecma.appauth.payload.ApiResult;
import ai.ecma.appauth.payload.ModuleDTO;
import ai.ecma.appauth.service.ModuleService;
import ai.ecma.appauth.utils.RestConstants;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = ModuleController.MODULE_CONTROLLER_PATH)
@RequiredArgsConstructor
@Slf4j
@Api(tags = "Module")
public class ModuleController {

    static final String MODULE_CONTROLLER_PATH = RestConstants.BASE_PATH_V1 + "module";

    private final ModuleService moduleService;


    //MODULE NI TITLE YOKI ORDER INDEX EDIT QILINGANDA
    @PutMapping
    public ApiResult<String> editModule(@Valid @RequestBody List<ModuleDTO> moduleDTOList) {
        log.info("editModule method entered: {}", moduleDTOList);
        ApiResult<String> result = moduleService.editModule(moduleDTOList);
        log.info("editModule method exited: {}", result);
        return result;
    }

}
