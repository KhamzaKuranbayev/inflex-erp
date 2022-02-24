package ai.ecma.appauth.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RoleType {
    ADMIN("Tizim boshqaruvchisi", "Tizimni boshqaruvchi"),
    OTHER("Boshqa", "Tizimning boshqa foydalanuvchilari"),
    MENTOR("Mentor", "Tizimda dars o'tadi"),
    ASSISTANT("Assistent", "Mentorga yordamchi"),
    STUDENT("Student", "Tizimda ta'lim oluvchi"),
    OPERATOR("Operator", "Telefonda mijozlar bilan gaplashadi"),

    //BU FAQAT MODULE LAR UCHUN KERAK XOLOS. BUNI BOSHQA USERLARGA BERISH MUMKIN EMAS
    SYSTEM_ROLE("Boshqa modullar", "Boshqa service lar uchun");

    private final String title;
    private final String description;


}
