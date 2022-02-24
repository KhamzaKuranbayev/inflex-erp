package ai.ecma.academic.entity;

import ai.ecma.academic.entity.template.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * FAYLLAR, RASMLAR BILAN ISHLASH UCHUN ISHLATILGAN CLASS.
 */

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Attachment extends AbstractEntity {
    /**
     * AttachmentTypeName ENUM CLASSI ULANGAN.
     * UNDA 2XIL TYPE BOR, PROFILE PHOTO VA COURSE PHOTO
     */
//    @Enumerated(EnumType.STRING)
//    private AttachmentTypeName attachmentType;
    /**
     * FOYDALANUVCHI TOMONIDAN YUKLANGAN FAYLLARNING OZINI NOMI
     */
    private String originalName;
    /**
     * FOYDALANUVCHI TOMONIDAN YUKLANGAN FAYLNING HAJMI
     */
    private Long fileSize;
    /**
     * FOYDALANUVCHI TOMONIDAN YUKLANGAN FAYLNING TURI //audio/mpeg
     */
    private String contentType;
    /**
     * YUKLANGAN FAYLNI BAZAMIZGA SAQLAGAN NOMIMINZ
     */
    private String name;//2021/05/19/dsdjskjs.jpg

    private Boolean file;//BU FAYLMI
}
