package ai.ecma.appauth.entity;

import ai.ecma.appauth.entity.template.AbsUUIDUserAuditEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Table(name = "users", indexes = {
        @Index(name = "idx_user_issued_at_jwt", columnList = "issued_at_jwt")
})
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicInsert
@DynamicUpdate
@Entity(name = "users")
@SQLDelete(sql = "UPDATE users SET enabled=false WHERE id=?")
public class User extends AbsUUIDUserAuditEntity implements UserDetails {

    //ISMI
    @Column(name = "first_name")
    private String firstName;

    //FAMILYASI
    @Column(name = "last_name")
    private String lastName;

    //OTASINING ISMI
    private String patron;

    //TIZIMGA KIRUVCHI PAROLI
    private String password;

    //TELEFON RAQAMI
    @Column(nullable = false, name = "phone_number")
    private String phoneNumber;

    //ASOSIY EMAILI
    private String email;

    //ASOSIY EMAILNI O'ZGATIROMOQCHI BO'LGANDA, YANGI EMAILNI TASDIQLAGUNCHA USHBU COLUMNGA YOZAMIZ
    @Column(name = "temp_email")
    private String tempEmail;

    //ASOSIY EMAILIGA YUBORILGAN TASDQILASH KODI
    @Column(name = "email_code")
    private String emailCode;

    //2 BOSQICHLI XAVFSIZLIK PAROLI
    @Column(name = "two_step_password")
    private String twoStepPassword;

    //2 BOSQICHLI XAVFSIZLIK PAROLI NI ESLASH UCHUN YORDAMCHI SO'Z
    @Column(name = "two_step_password_hint")
    private String twoStepPasswordHint;

    //2 BOSQICHLI XAVFSIZLIK YOQILGANLIGI
    @Column(name = "two_step_verification")
    private Boolean twoStepVerification;

    //IKKI BOSQICHLI XAVFSIZLIK UCHUN EMAIL
    @Column(name = "email_two_step_verification")
    private String emailTwoStepVerification;

    //2 BOSQICHLI XAVFSIZLIK VAQTINCHALIK EMAIL. MAVJUD EMAILNI O'ZGATIROMOQCHI BO'LGANDA YANGISI EMAILNI TASDIQLAGUNCHA USHBU COLUMNGA YOZAMIZ
    @Column(name = "temp_email_two_step_verification")
    private String tempEmailTwoStepVerification;

    //2 BOSQICHLI XAVFSIZLIKNI YOQISH UCHUN TASDIQLASH KODI
    @Column(name = "two_step_verfication_code")
    private String twoStepVerficationCode;

    //TOKENNI SHU USER UCHUN GENERATE QILINGAN VAQTI
    // (BITTA USER IKKITA DEVICEDAN BIR VAQTDA FOYDALAN OLMASLIGI UCHUN YECHIM)
    @Column(name = "issued_at_jwt")
    private Timestamp issuedAtJwt;

    //AVATARDAGI RASM ID SI
    @Column(name = "photo_id")
    private UUID photoId;

    //TELEGRAM IDSI
    @Column(name = "tg_id")
    private Integer tgId;

    //TELEGRAMADGI RAQAMI (FAQAT TELEGRAMDA BO'LISHI MUMKIN)
    @Column(name = "telegram_number")
    private String telegramNumber;

    //USHBU USERING QAYSI LAVOZIMDA ISHLASHI (EPAMDA SENIOR JAVA DASTURCHI...)
    private String position;

    //QAYIS COMPANYDA ISHAYDI (UZCARD, EPAM, ...)
    @Column(name = "company_id")
    private UUID companyId;

    //MENTORLAR RO'YXATI SO'RALGANDA TARTIBLASH UCHUN
    @Column(name = "ord_mentor")
    private Integer ordMentor;

    //MENTORLAR RO'YXATIDA KO'RINISHLIGI (HOME PAGE UCHUN)
    @Column(name = "show_mentor")
    private Boolean showMentor;

    //BOTNI AKTIVLASHTIRISH UCHUN CODE
    @Column(name = "bot_code")
    private UUID botCode;

    @Column(name = "account_non_expired")
    private boolean accountNonExpired = true;

    @Column(name = "account_non_locked")
    private boolean accountNonLocked = true;

    @Column(name = "credentials_non_expired")
    private boolean credentialsNonExpired = true;

    private boolean enabled = true;

    public User(String firstName, String password, String phoneNumber) {
        this.firstName = firstName;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public String getUsername() {
        return this.phoneNumber;
    }
}
