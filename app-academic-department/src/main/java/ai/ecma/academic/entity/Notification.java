package ai.ecma.academic.entity;

import ai.ecma.academic.entity.template.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.util.UUID;

/**
 * PLATFORMANING FOYDALNUVCHISI UCHUN YUBORILADIGAN BILDIRISHNOMALAR
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Notification extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private NotificationType notificationType;//BILDIRISHNOMANING QAYSI TURGA MANSUB EKANLIGI

    private String title;//NOMI

    @Column(length = 1000)
    private String description;//IZOH

    @Column(length = 1000)
    private String content;//BILDIRISHNOMAGA BERILGAN MAQOLA

    @ManyToOne(fetch = FetchType.LAZY)
    private Attachment file;//BILDIRISHNOMAGA TEGISHLI BOLGAN FAYL

    private UUID userToId;//BILDIRISHNOMA KIMGA YUBORLISHI KERAK EKANLIGI

    private UUID userFromId;//BILDIRISHNOMA KIM TOMONIDAN EKANLIGI

    private String link;//BILDIRISHNOMA GA BERILAGAN LINK, YA'NI BUTTON BOSILGANDA OTIB KETILADIGAN LINK

    private Boolean active;//AKTIVLIGI

    private Boolean looked = false;//O'QILGANLIGI

    private UUID actionId;  //

    public Notification(NotificationType notificationType, String title, String description, String content,
                        Attachment file, UUID userFromId, String link, Boolean active) {
        this.notificationType = notificationType;
        this.title = title;
        this.description = description;
        this.content = content;
        this.file = file;
        this.userFromId = userFromId;
        this.link = link;
        this.active = active;
    }

    public Notification(NotificationType notificationType, String title, String description, String content,
                        Attachment file, UUID userToId, UUID userFromId, String link, Boolean active, Boolean looked) {
        this.notificationType = notificationType;
        this.title = title;
        this.description = description;
        this.content = content;
        this.file = file;
        this.userToId = userToId;
        this.userFromId = userFromId;
        this.link = link;
        this.active = active;
        this.looked = looked;
    }
}
