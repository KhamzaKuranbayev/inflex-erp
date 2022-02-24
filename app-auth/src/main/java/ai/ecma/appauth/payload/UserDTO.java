package ai.ecma.appauth.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class UserDTO {
    private UUID id;

    //ISMI
    private String firstName;

    //FAMILYASI
    private String lastName;

    //OTASINING ISMI
    private String patron;

    private String password;

    //TELEFON RAQAMI
    private String phoneNumber;

    private RoleDTO role;

    //AVATARDAGI RASM ID SI
    private UUID photoId;

    //TELEGRAMADGI RAQAMI (FAQAT TELEGRAMDA BO'LISHI MUMKIN)
    private String telegramNumber;

    //USHBU USERING QAYSI LAVOZIMDA ISHLASHI (EPAMDA SENIOR JAVA DASTURCHI...)
    private String position;

    //QAYIS COMPANYDA ISHAYDI (UZCARD, EPAM, ...)
    private UUID companyId;

    //MENTORLAR RO'YXATI SO'RALGANDA TARTIBLASH UCHUN
    private Integer ordMentor;

    //MENTORLAR RO'YXATIDA KO'RINISHLIGI (HOME PAGE UCHUN)
    private Boolean showMentor;

    //USERNING PERMISSIONLAR RO`YXATI
    private List<PermissionDTO> permissions;

    private List<AuthPageDTO> pages;

    private Boolean enabled = true;

    private UUID createdById;

    private UUID updatedById;

    private Timestamp createdAt;

    private Timestamp updatedAt;

}
