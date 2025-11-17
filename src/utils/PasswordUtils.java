package utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {

    // 加密密码
    public static String hashPassword(String plainTextPassword) {
        // 生成盐并哈希密码
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt(12));
    }

    // 验证密码
    public static boolean checkPassword(String plainTextPassword, String hashedPassword) {
        if (hashedPassword == null || !hashedPassword.startsWith("$2a$")) {
            throw new IllegalArgumentException("Invalid hash provided for comparison");
        }
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }
}