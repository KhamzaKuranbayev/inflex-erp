package ai.ecma.appauth.entity;

import ai.ecma.appauth.entity.template.AbsLongEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
@Entity(name = "permission")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name", "page_id"}))
public class Permission extends AbsLongEntity {

    //CLIENTGA KO'RINADIGAN QISM
    @Column(nullable = false)
    private String title;//(Talabalar o'chirish, Talaabni telefon raqamini tahrirlasj)

    //FRONTENDCHI SHU NAME BILAN ISHLAYDI
    @Column(nullable = false)
    private String name;//(studentHome, studentList, mentorHome...)

    //QAYSI PAGEGA TEGISHLI PERMISSION EKANLIGI
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ToString.Exclude
    private AuthPage page;//Asosiy sahifa,Dars jadvali, Talabalar ro'yxati
}
