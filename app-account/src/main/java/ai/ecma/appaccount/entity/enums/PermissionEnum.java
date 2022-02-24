package ai.ecma.appaccount.entity.enums;

public enum PermissionEnum {
    ADD_CURRENCY("Valyuta qo'shish", "Valyuta qo'sha olish huquqi");

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
