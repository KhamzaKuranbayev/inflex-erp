package ai.ecma.academic.entity;


import ai.ecma.academic.entity.template.AbstractEntityLong;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.Table;

//CLICK TO'LOV TURI UCHUN
@Entity
@Table(name = "complete_object")
public class CompleteObject extends AbstractEntityLong {

    private Integer click_trans_id;

    private String merchant_trans_id;

    private Integer merchant_confirm_id;
    private Integer merchant_prepare_id;

    private Integer error;

    private String error_note;

    //complete method, payment status, boolean,  true paid
    @JsonIgnore
    private boolean status = false;

    //payment cancelled-true
    @JsonIgnore
    private boolean statusCancelled = false;




    //click - click_trans_id for JPA Repos
    @JsonIgnore
    private Integer click;

    public boolean isStatusCancelled() {
        return statusCancelled;
    }

    public void setStatusCancelled(boolean statusCancelled) {
        this.statusCancelled = statusCancelled;
    }

    public Integer getClick() {
        return click;
    }

    public void setClick(Integer click) {
        this.click = click;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Integer getClick_trans_id() {
        return click_trans_id;
    }

    public void setClick_trans_id(Integer click_trans_id) {
        this.click_trans_id = click_trans_id;
    }

    public String getMerchant_trans_id() {
        return merchant_trans_id;
    }

    public void setMerchant_trans_id(String merchant_trans_id) {
        this.merchant_trans_id = merchant_trans_id;
    }

    public Integer getMerchant_confirm_id() {
        return merchant_confirm_id;
    }
    public Integer getMerchant_prepare_id() {
        return merchant_prepare_id;
    }

    public void setMerchant_confirm_id(Integer merchant_confirm_id) {
        this.merchant_confirm_id = merchant_confirm_id;
    }
    public void setMerchant_prepare_id(Integer merchant_prepare_id) {
        this.merchant_prepare_id = merchant_prepare_id;
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

    @Override
    public String toString() {
        return "CompleteObject{" +
                "click_trans_id=" + click_trans_id +
                ", merchant_trans_id='" + merchant_trans_id + '\'' +
                ", merchant_confirm_id=" + merchant_confirm_id +
                ", error=" + error +
                ", error_note='" + error_note + '\'' +
                ", status=" + status +
                ", statusCancelled=" + statusCancelled +
                ", click=" + click +
                '}';
    }
}
