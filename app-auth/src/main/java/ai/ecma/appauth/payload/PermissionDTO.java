package ai.ecma.appauth.payload;

import lombok.Data;

@Data
public class PermissionDTO {
    //TODO ABDUROSHID TITLE VA NAME NI NULL EMASLIGINI TEKSHIR

    private Long id;

    private String title;//(Talabalar o'chirish, Talaabni telefon raqamini tahrirlasj)

    private String name;//(studentHome, studentList, mentorHome...)

    private boolean checked;
}
