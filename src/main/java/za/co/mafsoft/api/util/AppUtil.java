package za.co.mafsoft.api.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;

@Slf4j
public class AppUtil {
    private AppUtil() {
        throw new UnsupportedOperationException("Cannot be instantiated");
    }

    public static String generateVerificationCode() {
        String verificationCode = RandomStringUtils.secureStrong().nextAlphanumeric(5);
        log.info("Generated verification code {} => ", verificationCode);
        return verificationCode;
    }
}
