package ai.ecma.appauth.entity;

import ai.ecma.appauth.entity.template.AbsLongEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(callSuper = true)
@Entity(name = "department")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name", "module_id"}))
public class Department extends AbsLongEntity {

    //CLIENTGA KO'RINADIGAN QISM
    @Column(nullable = false)
    private String title;

    //FRONTENDCHI SHU NAME BILAN ISHLAYDI
    @Column(nullable = false)
    private String name;

    //MODULE NOMI
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ToString.Exclude
    private Module module;

    //DEPARTMENT'GA TEGISHLI PAGE'LAR RO'YHATI
    @OneToMany(mappedBy = "department")
    @ToString.Exclude
    private List<AuthPage> pages;

    //USERLARGA QANDAY CHAP TOMON MENUDA KETMA-KETLIKDA CHIQISHI
    private Integer orderIndex;

    public Department(String title, String name, Module module) {
        this.title = title;
        this.name = name;
        this.module = module;
    }
}
