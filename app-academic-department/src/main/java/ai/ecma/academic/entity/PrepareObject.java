package ai.ecma.academic.entity;


import ai.ecma.academic.entity.template.AbstractEntityLong;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

//CLICK UCHUN
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "prepare_object")
public class PrepareObject extends AbstractEntityLong {


    //ID платежа в системе CLICK
    //KLIK TARMOG'IDAGI TO'LOV ID SI

    private Integer click_trans_id;


    //ID заказа(для Интернет магазинов)/лицевого счета/логина в биллинге поставщика
    //Buyurtma,shaxsiy hisob raqami yoki Sotuvchi billingidagi ID si (Internet magazinlar uchun)

    private String merchant_trans_id;


    //ID платежа в биллинг системе поставщика для подтверждения
    //Sotuvchining alohida sistemasidagi to'lov ID si(tolovtasdiqi uchun)
    private Long merchant_prepare_id;

    //Код статуса запроса. 0 – успешно. В случае ошибки возвращается код ошибки
    //So'rov status kodi. 0 - bo'lsa muvaffaqiyatli, agar xatolik bo'lsa xatolik kodi qaytadi.

    private Integer error;

    //Описание кода завершения платежа.
    //To'lov tasdiqi haqida izoh

    private String error_note;

    //amount
    @JsonIgnore
    private float amount;



//    public float getAmount() {
//        return amount;
//    }
//
//    public void setAmount(float amount) {
//        this.amount = amount;
//    }
//
//    public Integer getClick_trans_id() {
//        return click_trans_id;
//    }
//
//    public void setClick_trans_id(Integer click_trans_id) {
//        this.click_trans_id = click_trans_id;
//    }
//
//    public String getMerchant_trans_id() {
//        return merchant_trans_id;
//    }
//
//    public void setMerchant_trans_id(String merchant_trans_id) {
//        this.merchant_trans_id = merchant_trans_id;
//    }
//
//    public Long getMerchant_prepare_id() {
//        return merchant_prepare_id;
//    }
//
//    public void setMerchant_prepare_id(Long merchant_prepare_id) {
//        this.merchant_prepare_id = merchant_prepare_id;
//    }
//
//    public Integer getError() {
//        return error;
//    }
//
//    public void setError(Integer error) {
//        this.error = error;
//    }
//
//    public String getError_note() {
//        return error_note;
//    }
//
//    public void setError_note(String error_note) {
//        this.error_note = error_note;
//    }
//
//    @Override
//    public String toString() {
//        return "PrepareObject{" +
//                "click_trans_id=" + click_trans_id +
//                ", merchant_trans_id='" + merchant_trans_id + '\'' +
//                ", merchant_prepare_id=" + merchant_prepare_id +
//                ", error=" + error +
//                ", error_note='" + error_note + '\'' +
//                ", amount=" + amount +
//                '}';
//    }
//}
}