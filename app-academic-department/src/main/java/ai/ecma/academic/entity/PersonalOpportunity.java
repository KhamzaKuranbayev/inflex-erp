package ai.ecma.academic.entity;

import ai.ecma.academic.entity.template.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * SHAXSIY IMKONIYATLAR
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonalOpportunity extends AbstractEntity {

    /**
     * Id UUID ga o'zgartirildi.
     * Bu klass userlarga imkoniyatlar qo'shish va imkoniyatlaridan mahrum qilish uchun
     * xizmat qiladi va buni faqat admin bajara oladi.
     */
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;


    /**
     * Attachment o'zgartirilmadi, ko'rib chiqish lozim. Opportunityga attachment nega kk?
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Attachment attachment;//FAYL

    @Column(nullable = false)
    private String title;// userga beriladigan imkoniyat nomi

    @Column(nullable = false)
    private String description;// Imkoniyat haqida ma'lumot

    @Column(unique = true, nullable = false)
    private Integer ord;//TARTIB RAQAMI

//    public PersonalOpportunity(Attachment attachment, String title, String description, Integer ord) {
//        this.attachment = attachment;
//        this.title = title;
//        this.description = description;
//        this.ord = ord;
//    }
}
