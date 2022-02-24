//package ai.ecma.appauth.entity2;
//
//import ai.ecma.appauth.component.SpringConfiguration;
//import ai.ecma.appauth.entity.Role;
//import ai.ecma.appauth.entity.template.AbsUUIDEntity;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.NoArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.persistence.*;
//import javax.servlet.http.HttpServletRequest;
//import java.nio.charset.StandardCharsets;
//import java.sql.Timestamp;
//import java.util.Base64;
//import java.util.Collection;
//import java.util.HashSet;
//import java.util.UUID;
//
///**
// * PDP.UZ PLATFORMASINING FOYDALNUVCHISI
// */
//
//@EqualsAndHashCode(callSuper = true)
//@Data
//@Entity(name = "users2")
//@NoArgsConstructor
//public class AbsUser extends AbsUUIDEntity implements UserDetails {
//    @Autowired
//    PasswordEncoder passwordEncoder;
//
//    @Column(name = "phoneNumber", unique = true)
//    private String phoneNumber;
//
//    @JsonIgnore
//    @Column(name = "password")
//    private String password;
//
//    @JsonIgnore
//    private String paymePassword2;
//
//    @Column(name = "email")
//    private String email;
//
//    @Column(name = "last_name")
//    private String lastName;
//
//    @Column(name = "first_name")
//    private String firstName;
//
//    private String patron;
//
//    private Boolean agreement;
//
//    private Timestamp issuedAtJwt;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    private Role role;
//
//    private UUID photoId;
//
//    private Integer tgId;
//
//    private String telegramNumber;
//
//    private String position;
//
//    private UUID companyId;
//
//    private Integer ordMentor;
//
//    private Boolean showMentor;
//
//    private Long accessHash;//OLINMADI
//
//    private Boolean showMentorReport;
//
//    private UUID botCode;
//
//    private UUID registrationLink;//OLINMADI
//
//    private String leaveStatus;//OLINMADI
//
//    private boolean accountNonExpired = true;
//
//    private boolean accountNonLocked = true;
//
//    private boolean credentialsNonExpired = true;
//
//    private boolean enabled = true;
//
//    public AbsUser(String lastName, String firstName, String phoneNumber, UUID photo, UUID registrationLink) {
//        this.lastName = lastName;
//        this.firstName = firstName;
//        this.phoneNumber = phoneNumber;
//        this.photoId = photo;
//        this.registrationLink = registrationLink;
//    }
//
//    public AbsUser(String phoneNumber, String password, Role role, String firstName, String lastName) {
//        this.phoneNumber = phoneNumber;
//        this.password = password;
//        this.role = role;
//        this.firstName = firstName;
//        this.lastName = lastName;
//    }
//
//    public AbsUser(String phoneNumber, String password, Role role, String firstName, String lastName, UUID registrationLink) {
//        this.phoneNumber = phoneNumber;
//        this.password = password;
//        this.role = role;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.registrationLink = registrationLink;
//    }
//
//    public AbsUser(String phoneNumber, String password, String email, String lastName, String firstName, Role role) {
//        this.phoneNumber = phoneNumber;
//        this.password = password;
//        this.email = email;
//        this.lastName = lastName;
//        this.firstName = firstName;
//        this.role = role;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return new HashSet<>();
//    }
//
//    @Override
//    public String getPassword() {
//        try {
//            HttpServletRequest request =
//                    ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
//                            .getRequest();
//            String base64Credentials = request.getHeader("Authorization").substring("Basic".length()).trim();
//            byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
//            String credentials = new String(credDecoded, StandardCharsets.UTF_8);
//            // credentials = username:password
//            final String[] values = credentials.split(":", 2);
//            if (values[0] != null && values[1] != null) {
//                String phoneNumber = values[0];
//                String password = values[1];
//                PasswordEncoder passwordEncoder = SpringConfiguration.contextProvider().getApplicationContext().getBean(PasswordEncoder.class);
//                if (phoneNumber.equals("Paycom") && passwordEncoder.matches(password, this.paymePassword2)) {
//                    return this.paymePassword2;
//                }
//
//            }
//        } catch (Exception ignored) {
//        }
//
//        return this.password;
//    }
//
//    @Override
//    public String getUsername() {
//        return this.phoneNumber;
//    }
//
//    @PreUpdate
//    public void update() {
//        setUpdatedById(null);
//    }
//
//}
