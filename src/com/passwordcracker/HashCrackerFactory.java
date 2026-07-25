package com.passwordcracker;

/**
 * Fabrique simple (patron <em>Simple Factory</em>).
 *
 * <p>Elle centralise la création des stratégies de cassage. Le programme
 * principal ne connaît que la chaîne de méthode (« BRUTE » ou « DICO ») et
 * délègue à cette fabrique le choix et l'instanciation de la classe concrète
 * appropriée.</p>
 *
 * <p>Ainsi :</p>
 * <ul>
 *   <li>les classes concrètes ne sont jamais instanciées directement dans le
 *       programme principal (contrainte du sujet) ;</li>
 *   <li>toute la logique de sélection est regroupée à un seul endroit ;</li>
 *   <li>le client dépend de l'abstraction {@link HashCracker}, pas des
 *       implémentations.</li>
 * </ul>
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
