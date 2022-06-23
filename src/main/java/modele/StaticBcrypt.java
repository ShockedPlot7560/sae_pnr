package modele;

public class StaticBcrypt {
    private static final Bcrypt bcrypt = new Bcrypt(11);

    public static String hash(String password) {
        return bcrypt.hash(password);
    }

    public static boolean verifyHash(String password, String hash) {
        return bcrypt.verifyHash(password, hash);
    }
}