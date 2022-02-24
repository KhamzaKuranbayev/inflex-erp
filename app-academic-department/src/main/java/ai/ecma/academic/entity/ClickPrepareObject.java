package ai.ecma.academic.entity;



import ai.ecma.academic.entity.template.AbstractEntityLong;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * CLICK NI objectlarini tekshirish uchun ishlatiladi
 */

@Entity
@Table(name = "click_prepare_object")
public class ClickPrepareObject extends AbstractEntityLong {

    //CLICK tizimidagi to'lov Id si
    //ID платежа в системе CLICK
    private Integer click_trans_id;

    // Click tizimini servis ID si
    //ID сервиса
    // @JsonProperty("service_id")
    private Integer service_id;


    //CLICK tizimidagi to'lov raqami. To'lovni amalga oshirishda mijozga SMS-xabar yuboriladi.
    //Номер платежа в системе CLICK. Отображается в СМС у клиента при оплате.
    private Integer click_paydoc_id;


    //Buyurtma Id si
    //ID заказа(для Интернет магазинов)/лицевого счета/логина в биллинге поставщика  - varchar
    private String merchant_trans_id;

    // To'lov summasi
    //float	Сумма оплаты (в сумах)
    private float amount;

    //
    //Выполняемое действие. Для Prepare — 0
    private Integer action;


    //To'lovni yakunlash holati kodi. 0 - muvaffaqiyatli. Agar xato bo'lsa, xato kodi qaytariladi.
    //Код статуса завершения платежа. 0 – успешно. В случае ошибки возвращается код ошибки.
    private Integer error;

    // To'lovni yakunlash
    //Описание кода завершения платежа.
    private String error_note;


    // To'lov vaqti
    //Дата платежа. Формат «YYYY-MM-DD HH:mm:ss»
    // @JsonProperty("sign_time")
    private String sign_time;


    // Yuborilayotgan so'rovning haqiqiyligini tasdiqlovchi tasdiqlash satri.
    //Проверочная строка, подтверждающая подлинность отправляемого запроса. ХЭШ MD5 из следующих параметров:
    //md5( click_trans_id + service_id + SECRET_KEY* + merchant_trans_id + amount + action + sign_time)
    //
    //SECRET_KEY – уникальная строка, выдаваемая Поставщику при подключении.
    private String sign_string;

    // Ta'lim turi : Online, Offline, Bootcamp
    private Integer eduType;

    public Integer getEduType() {
        return eduType;
    }

    public void setEduType(Integer eduType) {
        this.eduType = eduType;
    }

    public Integer getClick_trans_id() {
        return click_trans_id;
    }

    public void setClick_trans_id(Integer click_trans_id) {
        this.click_trans_id = click_trans_id;
    }

    public Integer getService_id() {
        return service_id;
    }

    public void setService_id(Integer service_id) {
        this.service_id = service_id;
    }

    public Integer getClick_paydoc_id() {
        return click_paydoc_id;
    }

    public void setClick_paydoc_id(Integer click_paydoc_id) {
        this.click_paydoc_id = click_paydoc_id;
    }

    public String getMerchant_trans_id() {
        return merchant_trans_id;
    }

    public void setMerchant_trans_id(String merchant_trans_id) {
        this.merchant_trans_id = merchant_trans_id;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public String getError_note() {
        return error_note;
    }

    public void setError_note(String error_note) {
        this.error_note = error_note;
    }

    public String getSign_time() {
        return sign_time;
    }

    public void setSign_time(String sign_time) {
        this.sign_time = sign_time;
    }

    public String getSign_string() {
        return sign_string;
    }

    public void setSign_string(String sign_string) {
        this.sign_string = sign_string;
    }

    @Override
    public String toString() {
        return "ClickPrepareObject{" +
                "click_trans_id=" + click_trans_id +
                ", service_id=" + service_id +
                ", click_paydoc_id=" + click_paydoc_id +
                ", merchant_trans_id='" + merchant_trans_id + '\'' +
                ", amount=" + amount +
                ", action=" + action +
                ", error=" + error +
                ", error_note='" + error_note + '\'' +
                ", sign_time='" + sign_time + '\'' +
                ", sign_string='" + sign_string + '\'' +
                ", eduType=" + eduType +
                '}';
    }
}
