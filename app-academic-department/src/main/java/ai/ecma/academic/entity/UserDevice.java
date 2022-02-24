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
 * FOYDALANUVCHI QAYSI QURILMALAR ORQALI PLATFORMAGA KIRGANLIGI HAQIDA TARIX
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UserDevice extends AbstractEntity {
    /**
     * FOYDALANUVCHI QURILMASINING NOMI (WIN: X64, iPhone; CPU iPhone O , VA BOSHQALAR )
     */
    private String userAgent;
    @Column(unique = true, nullable = false)

    /**
      FOYDALANUCHI QURILMASINING KALIT SO'ZI
     */
    private UUID key;

    /**
    FOYDALANUCHI
     */
    @Column(nullable = false)
    private UUID userID;
}
