package com.passwordcracker;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Classe utilitaire chargée du calcul d'empreintes MD5.
 *
 */
public final class Md5Utils {

    private Md5Utils() {
        // Classe utilitaire : instanciation interdite.
    }

    /**
     * Calcule l'empreinte MD5 d'une chaîne et la retourne en hexadécimal minuscule.
     *
     * @param input le texte en clair à hacher
     * @return l'empreinte MD5 sur 32 caractères hexadécimaux
     */
    public static String md5(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] bytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hex = new StringBuilder(bytes.length * 2);
            for (byte b : bytes) {
                hex.append(Character.forDigit((b >> 4) & 0xF, 16));
                hex.append(Character.forDigit(b & 0xF, 16));
            }
            return hex.toString();
        } catch (NoSuchAlgorithmException e) {
            // MD5 est garanti présent sur toute JVM standard : cas théorique.
            throw new IllegalStateException("Algorithme MD5 indisponible sur cette JVM", e);
        }
    }
}
