package ai.ecma.appauth.service;

import ai.ecma.appauth.entity.Module;
import ai.ecma.appauth.exceptions.RestException;
import ai.ecma.appauth.payload.ApiResult;
import ai.ecma.appauth.payload.ModuleDTO;
import ai.ecma.appauth.repository.ModuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ModuleService {

    private final ModuleRepository moduleRepository;
    private final MainService mainService;


    public ApiResult<String> editModule(List<ModuleDTO> moduleDTOList) {
        List<Module> modules = new ArrayList<>();
        moduleDTOList.forEach(moduleDTO -> {
            Module module = moduleRepository.findById(moduleDTO.getId()).orElseThrow(() -> RestException.restThrow("module", "id", moduleDTO.getId(), mainService.getMessageByKey("MODULE_NOT_FOUND")));
            module.setName(moduleDTO.getName());
            module.setOrderIndex(moduleDTO.getOrderIndex());
        });
        moduleRepository.saveAll(modules);
        return ApiResult.successResponse(mainService.getMessageByKey("MODULE_EITED"));
    }
}
