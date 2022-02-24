package ai.ecma.appauth.service;

import ai.ecma.appauth.entity.SmsCode;
import ai.ecma.appauth.exceptions.RestException;
import ai.ecma.appauth.payload.SmsCodeDTO;
import ai.ecma.appauth.repository.SmsCodeRepository;
import ai.ecma.appauth.utils.RestConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SmsCodeService {

    private final SmsCodeRepository smsCodeRepository;
    private final MainService mainService;

    public boolean sendSmsCode(String phoneNumber, String message) {
        //SMS YUBORISH XIZMATIDAN FOYDALANAMIZ
        //UZB NOMER BO'LSA BOSHQA SERVICE ISHALTAMIZ, CHET-EL UCHUN BOSHQA SERVICE
        //TODO PDP.UZ ULAGAN SMS SERVICELARNI ULASH
        return true;
    }

    public SmsCode saveSmsCode(String phoneNumber, String verificationCode) {
        return smsCodeRepository.save(new SmsCode(
                verificationCode,
                phoneNumber
        ));
    }


    //MIJOZGA SMS YUBORADI VA USHBU YUBORILGAN SMS CODE ID SINI SMS_CODE_DTO GA O'RAB QAYATRIB BERADI
    public SmsCodeDTO sendSmsAndReturnSmsCode(String phoneNumber) {

        //BU RAQAM KUNLIK SMS YUBORISH LIMITIDAN OSHGANDA
        if (smsCodeRepository.overLimitSmsCodeByPhoneNumberAndDuration(RestConstants.LIMIT_SMS_CODE, phoneNumber, RestConstants.LIMIT_SMS_CODE_DURATION_HOUR))
            throw RestException.restThrow(mainService.getMessageByKey("MANY_SMS_CODE_SENT"), HttpStatus.TOO_MANY_REQUESTS);

        //SMS CODE GENERATE QILAMIZ
        String verificationCode = mainService.generateVerificationCode(6);

        //SMS CODE NI SMS YUBORISH UCHUN SMS MESSAGE GA AYLANTIRAMIZ
        String smsMessage = mainService.generateVerificationCodeWithMessageToPhone(verificationCode);

        //MIJOZNING TELEFON RAQAMIGA SMS YUBORAMIZ. AGAR YUBORA OLSA TRUE QAYTADI
        boolean send = sendSmsCode(phoneNumber, smsMessage);

        //AGAR SMS YUBORISHDA XATOLIK BO'LSA
        if (!send)
            throw RestException.restThrow(mainService.getMessageByKey("ERROR_IN_SENDING_SMS"), HttpStatus.CONFLICT);

        //SMS CODE SAQLAYAMIZ
        SmsCode smsCode = saveSmsCode(phoneNumber, verificationCode);
        return SmsCodeDTO.builder().smsCodeId(smsCode.getId()).build();
    }


    //SHU RAQAMGA YUBORILGAN ENG OXIRGI SMS CODE SHU EKANLIGINI VA USHBU CODE ISHLATILMAGANLIGINI TEKSHIRAMIZ. resetPassword BU UNUTILGAN PAROLNI O'RNATISHNING OXIRGI QADAMIDA TRUE BO'LADI
    public void checkSmsCodeIfFailedThrow(String phoneNumber, String verificationCode, UUID smsCodeId, boolean resetPassword) {
        //USHBU PHONE_NUMBER GA YUBORILGAN EMG OXIRGI SMS_CODE NI OLYAPMIZ
        Optional<SmsCode> optionalSmsCode = smsCodeRepository.findFirstByPhoneNumberOrderByCreatedAtDesc(phoneNumber);

        //SMS_CODE OBJECTI BO'LMASA THROW
        if (optionalSmsCode.isEmpty())
            throw RestException.restThrow(mainService.getMessageByKey("SMS_CODE_NOT_SENT_THIS_NUMBER"), HttpStatus.BAD_REQUEST);

        //ENG OXIRGI YUBORILGAN SMS_CODE OBJECTINI OLDIK
        SmsCode checkingSmsCode = optionalSmsCode.get();


        if (!checkingSmsCode.getId().equals(smsCodeId))
            throw RestException.restThrow(mainService.getMessageByKey("SMS_CODE_EXPIRED"), HttpStatus.BAD_REQUEST);

        if (resetPassword && !checkingSmsCode.getChecked() || !resetPassword && checkingSmsCode.getChecked())
            throw RestException.restThrow(mainService.getMessageByKey("SMS_CODE_ALREADY_USED"), HttpStatus.BAD_REQUEST);

        if (resetPassword && checkingSmsCode.getCheckedForReset() != null && checkingSmsCode.getCheckedForReset())
            throw RestException.restThrow(mainService.getMessageByKey("SMS_CODE_ALREADY_USED"), HttpStatus.BAD_REQUEST);


        if (!checkingSmsCode.getCode().equals(verificationCode))
            throw RestException.restThrow(mainService.getMessageByKey("CONFIRMATION_CODE_INCORRECT"), HttpStatus.BAD_REQUEST);

        if (!resetPassword)
            checkingSmsCode.setChecked(true);
        else
            checkingSmsCode.setCheckedForReset(true);
        smsCodeRepository.save(checkingSmsCode);
    }


    //SMS_CODE_DTO DA KELAGAN FIELDLAR ORQALI DB DAN TEKSHIRADI VA HAMMASI TO'G'RI BO'LSA SMS CODE OBYEKTINI QAYTARDI
    public SmsCode checkSmsCodeandReturnSmsCode(SmsCodeDTO smsCodeDTO, boolean resetPassword) {
        // SMSCODEDTO DA KELGAN SMS_ID ORQALI SMSCODE OBYEKTI BAZADAN QIDIRILYAPTI
        SmsCode smsCode = smsCodeRepository.findById(smsCodeDTO.getSmsCodeId())
                .orElseThrow(() -> RestException.restThrow("SmsCode", "id", smsCodeDTO.getSmsCodeId(), mainService.getMessageByKey("SMS_CODE_NOT_FOUND")));

        //SHU RAQAMGA YUBORILGAN ENG OXIRGI SMS CODE
        // SHU EKANLIGINI VA USHBU CODE ISHLATILMAGANLIGINI TEKSHIRAMIZ
        checkSmsCodeIfFailedThrow(smsCode.getPhoneNumber(), smsCodeDTO.getSmsCode(), smsCodeDTO.getSmsCodeId(), resetPassword);
        return smsCode;
    }
}
