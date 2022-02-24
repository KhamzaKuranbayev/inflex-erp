package ai.ecma.appauth.entity;

import ai.ecma.appauth.entity.template.AbsLongEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
@Entity(name = "auth_page")
@EqualsAndHashCode(callSuper = true)
@Table(uniqueConstraints =
@UniqueConstraint(columnNames = {"name", "department_id"}))
public class AuthPage extends AbsLongEntity {

    //CLIENTGA KO'RINADIGAN QISM
    @Column(nullable = false)
    private String title;//(Asosiy sahifa,Dars jadvali, Talabalar ro'yxati)

    //FRONTENDCHI SHU NAME BILAN ISHLAYDI
    @Column(nullable = false)
    private String name;//(studentHome, studentList, mentorHome...)

    //QAYSI BO'LIMGA TEGISHLI SAHIFA EKANLIGI
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ToString.Exclude
    private Department department;//STUDENT, MENTOR, FINANCE

    @OneToMany(mappedBy = "page")
    @ToString.Exclude
    private List<Permission> permissions;

    public AuthPage(String title, String name, Department department) {
        this.title = title;
        this.name = name;
        this.department = department;
    }
}
