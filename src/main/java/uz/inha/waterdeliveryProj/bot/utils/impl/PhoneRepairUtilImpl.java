package uz.inha.waterdeliveryProj.bot.utils.impl;

import org.springframework.stereotype.Service;
import uz.inha.waterdeliveryProj.bot.utils.PhoneRepairUtil;

@Service
public class PhoneRepairUtilImpl implements PhoneRepairUtil {
    @Override
    public String repairPhone(String phoneNumber) {
        phoneNumber = phoneNumber.replaceAll(" ", "");
        phoneNumber = phoneNumber.startsWith("+") ? phoneNumber : "+".concat(phoneNumber);
        return phoneNumber;
    }
}
