package ai.ecma.appauth.entity.enums;

public enum AuthPermissionEnum {
    ADD_STUDENT("", "", null, false),
    EDIT_STUDENT("", "", null, false),
    DELETE_STUDENT("", "", null, false),
    ADD_STAFF("", "", null, false),
    EDIT_STAFF("", "", null, false),
    DELETE_STAFF("", "", null, false),

    GET_ROLE("Rolelar ro'yxatini olish", "GET_ROLE", AuthPageEnum.ROLE_PAGE, true),
    ADD_ROLE("Role qo'shish", "ADD_ROLE", AuthPageEnum.ROLE_PAGE, true),
    EDIT_ROLE("Roleni tahrirlash", "EDIT_ROLE", AuthPageEnum.ROLE_PAGE, true),
    DELETE_ROLE("Roleni o'chirish", "DELETE_ROLE", AuthPageEnum.ROLE_PAGE, true),

    GET_MODULES("Modulelar ro'yxaitni olish", "GET_MODULES", AuthPageEnum.ROLE_PAGE, true),

    GET_QUESTION("Xavfsizlik savollarini ro'yxatini olish", "GET_QUESTIONS", AuthPageEnum.QUESTION, true),
    ADD_QUESTION("Xavfsizlik savollarini qo'shish", "ADD_QUESTION", AuthPageEnum.QUESTION, true),
    EDIT_QUESTION("Xavfsizlik savollarini tahrirlash", "EDIT_QUESTION", AuthPageEnum.QUESTION, true),
    DELETE_QUESTION("Xavfsizlik savollarini o'chirish", "DELETE_QUESTION", AuthPageEnum.QUESTION, true),
    ;

    //NOMI
    private final String title;

    private final String name;

    private final AuthPageEnum page;

    //FAQAT USHBU MODULE UCHUN TEGISHLI PERMISSION
    private final boolean thisModulePermission;


    AuthPermissionEnum(String title, String name, AuthPageEnum page, boolean thisModulePermission) {
        this.title = title;
        this.name = name;
        this.page = page;
        this.thisModulePermission = thisModulePermission;
    }

    public String getTitle() {
        return title;
    }

    public String getName() {
        return name;
    }

    public AuthPageEnum getPage() {
        return page;
    }

    public boolean isThisModulePermission() {
        return thisModulePermission;
    }
}
