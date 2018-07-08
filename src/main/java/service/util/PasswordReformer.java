package service.util;

import entity.User;
import org.apache.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordReformer {
    private static final Logger LOG = Logger.getLogger(PasswordReformer.class);

    public String reform(String password, User user) {
        MessageDigest messageDigest = null;

        try {
             messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            LOG.error("Error for password reformer", e);
        }

        String salt = new String(messageDigest.digest(user.getEmail().getBytes()));
        password = new String(messageDigest.digest(user.getPassword().getBytes()));

        user.setSalt(salt);

        StringBuilder builder = new StringBuilder(password);
        builder.insert(0, salt);
        builder.append(salt);

        return builder.toString();
    }
}
