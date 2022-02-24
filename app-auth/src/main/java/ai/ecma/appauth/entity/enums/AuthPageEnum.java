package ai.ecma.appauth.entity.enums;

//FAQAT USHBU MODULEGA TEGISHLI BO'LGAN PAGE LAR UCHUN
public enum AuthPageEnum {
    ROLE_PAGE("Role uchun", "role", DepartmentEnum.AUTH),
    QUESTION("Xavfsizlik savollari", "securityQuestion", DepartmentEnum.AUTH),
    ;

    //NOMI
    private final String title;

    private final String name;

    private final DepartmentEnum department;


    AuthPageEnum(String title, String name, DepartmentEnum department) {
        this.title = title;
        this.name = name;
        this.department = department;
    }

    public String getTitle() {
        return title;
    }

    public String getName() {
        return name;
    }

    public DepartmentEnum getDepartment() {
        return department;
    }
}
