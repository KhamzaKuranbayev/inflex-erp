package ai.ecma.appauth.entity;

import ai.ecma.appauth.entity.template.AbsLongEntity;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@DynamicInsert
@DynamicUpdate
@Entity(name = "role_page_permission")
public class RolePagePermission extends AbsLongEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ToString.Exclude
    private RolePage rolePage;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(insertable = false, updatable = false)
    @ToString.Exclude
    private Permission permission;

    @Column(name = "permission_id", nullable = false)
    private Long permissionId;

    public RolePagePermission(RolePage rolePage, Long permissionId) {
        this.rolePage = rolePage;
        this.permissionId = permissionId;
    }
}
