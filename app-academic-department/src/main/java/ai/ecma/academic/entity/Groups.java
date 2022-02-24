package ai.ecma.academic.entity;

import ai.ecma.academic.entity.template.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Time;
import java.util.List;
import java.util.UUID;

/**
 * By: Akbarjon Ahmadjonov on 21/05/2021 at 02:55
 *
 * TELEGRAM GURUH SHAKLLANTIRISH
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "groups")
public class Groups extends AbstractEntity {

    @Column(nullable = false, unique = true)
    private Integer groupId; // telegramdagi guruh Idsi

    @Column(nullable = false, unique = true)
    private String name; // telegram guruh nomi

    @ManyToOne(fetch = FetchType.LAZY)
    private CoursePart coursePart; // qaysi modul uchunligi

    @Column(name = "week_days", columnDefinition = "integer[]")

    @Type(type = "ai.ecma.academic.type.GenericIntArrayType")
    private Integer[] weekDays; // o'qish qaysi hafta kunlari uchunligi

    @ElementCollection
    @CollectionTable(name = "groups_mentors", joinColumns = {@JoinColumn(name = "group_id")})
    @Column(name = "mentor_id")
    private List<UUID> mentors; // biriktirilgan mentorlar

    private Time time; // zoom tashkil qilish vaqtlari

    private String inviteLink; // telegram guruhga qo'shilishi uchun taklif linki

    public Groups(Integer groupId, String name, CoursePart coursePart, Integer[] weekDays, Time time, String inviteLink) {
        this.groupId = groupId;
        this.name = name;
        this.coursePart = coursePart;
        this.weekDays = weekDays;
        this.time = time;
        this.inviteLink = inviteLink;
    }


}
