package ai.ecma.academic.entity.enums;

public enum BotOption {

    PAYMENT("To'lovlar"),
    SIGN_UP("Yangi foydalanuvchi ro'yxatdan o'tganligi to'g'risidagi xabar jo'natilishi"),
    BUY_COURSE("Kurs sotib olganligi to'g'risida xabar jo'natilishi"),
    NOT_READ_MESSAGE_COUNT("O'qilmagan xabarlar sonini ko'rish"),
    STUDENT_NEW_LESSON_OPENED("O'quvchi yangi dars boshlaganligi to'g'risidagi xabar jo'natilishi"),
    MENTOR_ANSWER_TO_TASK("Mentor yoki taskga javob berganligi to'g'risidagi xabar jo'natilishi"),
    MENTOR_ANSWER_TO_QUESTION("Mentor yoki asistent o'quvchining savoli javob bergani tog'risidagi xabar jo'natilishi"),
    STUDENT_SEND_TASK("Oquvchininig asistent va mentorga vazifa jo'natganligi to'g'risidagi xabar jo'natilishi"),
    STUDENT_SEND_QUESTION("O'quvchining asistent va mentorga xabar yozilganligi to'g'risidagi xabar jo'natilishi"),
    VOUCHER_CREATED("Yangi voucher yasalganligi to'g'risida xabar jo'natilishi"),
    VOUCHER_EDITED("Mavjud bo'lgan voucher o'zgartirilganligi to'g'risidagi xabar jo'natilishi"),
    VOUCHER_USED("Voucher ishlatilganligi tog'risida xabar jonatilishi"),
    EVENT_CREATED("Yangi tadbir qo'shilganligi to'g'risidagi xabar jo'natilshi"),
    EVENT_REGISTER_STUDENT("Tadbirga o'quvchi ro'yxatdan o'tganligi to'grisidagi xabar jo'natilishi"),
    TASK_RATED_BY_MENTOR("Vazifa mentor tomonidan baholanganligi to'g'risidagi xabar jo'natilishi"),
    RATES_BY_STUDENTS("O'quvchi tomonidan baholanganlik to'g'risida xabar jo'natishi"),
    EVENT_ACTIVATED("Tadbir aktivlashganligi to'g'risidagi xabar jo'natilishi"),
    MODULE_STARTED("O'quvchi modulga start berganligi to'g'risidagi xabar jo'natilishi"),
    TRY_MONTH_STARTED("Sinov oyi boshlanganligi to'g'risidagi xabar jo'natilishi"),
    MODULE_COMPLETED("O'quvchining modul tugatganligi haqidagi xabar jo'natilishi"),
    STUDENT_SEND_PROBLEM_OR_OFFER("O'quvchi shikoyat yoki fikr bildirganligi to'g'risidagi xabar jo'natilishi"),
    CANT_ADD_TO_GROUP_TELEGRAM_USER("Telegram foydalanuvchisini gruppaga qo'sha olmayotganligi to'g'risidagi xabar jo'natilishi"),
    VOUCHER_GROUP_CREATED("Massivniy voucherlar yaratilgani haqida xabar jo'natilishi"),
    CANT_REGISTER_TELEGRAM_USER("Telegram foydalanuvchisini ro'yxatdan o'tkaza olmaganligi haqida xabar jo'natish"),
    ZOOM_MEETINGS("Zoom meetinglar tog'risida xabar jo'natilishi"),
    HELP_FINISHED("Support tugaganligi to'g'risida xabar jo'natish"),
    STUDENT_WITHOUT_GROUP("Guruhga qo'shilmagan o'quvchi"),
    SUPPORT_CREATED("Admin tomonidan support o'quvchiga qo'shib berilgan"),
    SUBSCRIBERS_ADMIN_BUTTON("Subscriberlarni korish uchun botda buttonni yoqish"),
    ACTIVES_ADMIN_BUTTON("Botda Active buttonni admin uchun yoqish"),
    ALL_AMOUNT_ADMIN_BUTTON("To'lov hisobotini admin uchun botda chiqarib beradigan buttonni yoqish"),
    BOOTCAMP_DOC_ADMIN_BUTTON("Bootcamplarni statuslarini ko'rish uchun buttonni botda yoqish"),
    ACTIVE_LOST_ADMIN_BUTTON("Botda Active lost buttonni admin uchun yoqish"),
    LESSON_QUALITY_RATED_BY_MENTOR("Darsni baholanganligi tog'risida xabar jo'natilishi"),
    STUDENT_DIGEST_RATED_BY_MENTOR("O'quvchini o'zlashtirishni baholanganligi to'g'risida xabar jo'natilishi");

    private String description;

    public String getDescription() {
        return description;
    }

    BotOption(String description) {
        this.description = description;
    }
}
