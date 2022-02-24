package ai.ecma.academic.entity.enums;

public enum PermissionEnum {
    ADD_COURSE("Kurs qo'shish", "O'quv kurslarini qo'sha oladi");

    private final String name;
    private final String description;

    PermissionEnum(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
