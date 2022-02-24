package ai.ecma.appauth.entity.enums;

//FAQAT USHBU MODULEGA TEGISHLI BO'LGAN DEPARTMENT LAR UCHUN
public enum DepartmentEnum {
    AUTH("Xavfsizlik", "auth");

    private final String title;

    private final String name;

    DepartmentEnum(String title, String name) {
        this.title = title;
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public String getName() {
        return name;
    }
}
