package ai.ecma.appauth.entity;

import ai.ecma.appauth.entity.template.AbsLongEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(callSuper = true)
@Entity(name = "module")
public class Module extends AbsLongEntity {

    //CLIENTGA KO'RINADIGAN QISM
    @Column(nullable = false, unique = true)
    private String title;

    //FRONTENDCHI SHU NAME BILAN ISHLAYDI
    @Column(nullable = false, unique = true)
    private String name;

    //USERLARGA QANDAY CHAP TOMON MENUDA KETMA-KETLIKDA CHIQISHI
    private Integer orderIndex;

    public Module(String title, String name) {
        this.title = title;
        this.name = name;
    }
}
