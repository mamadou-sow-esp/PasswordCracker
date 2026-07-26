package com.passwordcracker;

/**
 * Fabrique simple (patron <em>Simple Factory</em>).
 */
public class HashCrackerFactory {

    private HashCrackerFactory() {
        // Fabrique statique : instanciation inutile.
    }

    /**
     * Crée la stratégie de cassage correspondant à la méthode demandée.
     *
     * @param method la méthode souhaitée : {@code "BRUTE"} ou {@code "DICO"}
     *               (insensible à la casse)
     * @return une instance de {@link HashCracker} adaptée
     * @throws IllegalArgumentException si la méthode est nulle ou inconnue
     */
    public static HashCracker create(String method) {
        if (method == null) {
            throw new IllegalArgumentException("La methode ne peut pas etre nulle.");
        }
        switch (method.trim().toUpperCase()) {
            case "DICO":
                return new DictionaryHashCracker();
            case "BRUTE":
                return new BruteForceHashCracker();
            default:
                throw new IllegalArgumentException(
                        "Methode inconnue : \"" + method + "\" (attendu : BRUTE ou DICO)");
        }
    }
}
