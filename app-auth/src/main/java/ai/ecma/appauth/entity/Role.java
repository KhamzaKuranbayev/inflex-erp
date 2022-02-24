package ai.ecma.appauth.entity;

import ai.ecma.appauth.entity.enums.RoleType;
import ai.ecma.appauth.entity.template.AbsLongEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(callSuper = true)
@Entity(name = "role")
@SQLDelete(sql = "UPDATE role SET deleted=true WHERE id=?")
public class Role extends AbsLongEntity {

    //MIJOZGA KO'RINADIGAN NOMI
    @Column(nullable = false, unique = true)
    private String name;

    //ADMINGA KO'RINADI. BU ROLE HAQIDA IZOH YOZISHI MUMKIN
    @Column(length = 500)
    private String description;

    //TIZIMDA MENTORNI, ASSISTENTNI, STUDENTNI, OPERATORNI
    // SO'RGANDA TOPISHIMIZ UCHUN ZARUR CONSTANTLAR
    //(DROP INDEX if exists uk_role_role_enum;
    // CREATE UNIQUE INDEX uk_role_role_enum ON role (role_enum) WHERE role_enum<>'OTHER';)
    @Column(nullable = false, name = "role_type")
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    //USHBU ROLE NI OLGAN USER TIZIMGA LOGIN YOKI REGISTER
    // QILGANDA USHBU NOMLIK SAHIFA OCILADI
    @Column(name = "default_page_id")
    private Long defaultPageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(insertable = false, updatable = false)
    @ToString.Exclude
    private AuthPage defaultPage;
}
