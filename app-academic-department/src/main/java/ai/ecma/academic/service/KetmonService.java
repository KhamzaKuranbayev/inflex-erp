package ai.ecma.academic.service;

import ai.ecma.academic.entity.Ketmon;
import ai.ecma.academic.entity.KetmonRepository;
import ai.ecma.academic.payload.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class KetmonService {

    @Autowired
    KetmonRepository ketmonRepository;


    public ApiResponse addKetmon(Ketmon ketmon) {
        Ketmon current=new Ketmon(ketmon.getName());
        ketmonRepository.save(current);
        return new ApiResponse(true,"Ketmon qo'shildi");
    }

    public ApiResponse getKetmons() {
        List<Ketmon> ketmons = ketmonRepository.findAll();

        return new ApiResponse(true,"Ketmonlar",ketmons);
    }

    public ApiResponse editKetmon(UUID id, Ketmon ketmon) {
        Optional<Ketmon> optionalKetmon = ketmonRepository.findById(id);
        if (optionalKetmon.isEmpty()){
            return new ApiResponse(false,"Bunday ketmon mavjud emas");
        }
        Ketmon current = optionalKetmon.get();
        current.setName(ketmon.getName());
        ketmonRepository.save(current);
        return new ApiResponse(true,"Ketmon edited");
    }


    public ApiResponse getKetmonById(UUID id) {
        Optional<Ketmon> optionalKetmon = ketmonRepository.findById(id);
        if (optionalKetmon.isEmpty()){
            return new ApiResponse(false,"Bunday ketmon mavjud emas");
        }
        return new ApiResponse(true,"=>",optionalKetmon.get());
    }

    public ApiResponse deleteKetmon(UUID id) {
        if (ketmonRepository.findById(id).isEmpty()){
            return new ApiResponse(false,"Bunday ketmon mavjud emas");
        }
        ketmonRepository.deleteById(id);
        return new ApiResponse(true,"Ketmon o'chirildi");
    }
}
