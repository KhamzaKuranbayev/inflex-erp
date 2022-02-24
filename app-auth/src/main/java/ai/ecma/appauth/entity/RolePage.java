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
@Entity(name = "role_page")
public class RolePage extends AbsLongEntity {

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ToString.Exclude
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(insertable = false, updatable = false)
    @ToString.Exclude
    private AuthPage page;

    @Column(nullable = false, name = "page_id")
    private Long pageId;

    public RolePage(Role role, Long pageId) {
        this.role = role;
        this.pageId = pageId;
    }
}

