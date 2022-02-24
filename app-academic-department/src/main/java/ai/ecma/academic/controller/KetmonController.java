package ai.ecma.academic.controller;

import ai.ecma.academic.entity.Ketmon;
import ai.ecma.academic.entity.KetmonRepository;
import ai.ecma.academic.payload.ApiResponse;
import ai.ecma.academic.service.KetmonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/ketmon")
public class KetmonController {

    @Autowired
    KetmonService ketmonService;

    @PostMapping
    public HttpEntity<?> addKetmon(@RequestBody Ketmon ketmon) {
        ApiResponse apiResponse = ketmonService.addKetmon(ketmon);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @GetMapping
    public HttpEntity<?> getKetmons() {
        ApiResponse apiResponse = ketmonService.getKetmons();
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editKetmon(@PathVariable UUID id,@RequestBody Ketmon ketmon) {
        ApiResponse apiResponse = ketmonService.editKetmon(id,ketmon);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getKetmonById(@RequestBody UUID id) {
        ApiResponse apiResponse = ketmonService.getKetmonById(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }


//    @GetMapping("/{id}")
//    public HttpEntity<?> deleteKetmon(@RequestBody UUID id) {
//        ApiResponse apiResponse = ketmonService.deleteKetmon(id);
//        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
//    }


}
