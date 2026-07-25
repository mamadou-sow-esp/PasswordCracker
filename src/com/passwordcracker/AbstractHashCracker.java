package com.passwordcracker;

/**
 * Classe abstraite de base pour les stratégies de cassage.
 *
 * <p>Elle implémente {@link HashCracker} et factorise le comportement commun à
 * toutes les stratégies afin d'éviter la duplication de code (contrainte du
 * sujet) :</p>
 * <ul>
 *   <li>le comptage du nombre de tentatives effectuées ;</li>
 *   <li>la comparaison d'un candidat avec le hash recherché, via
 *       {@link Md5Utils}.</li>
 * </ul>
 *
 * <p>Les stratégies concrètes n'ont plus qu'à décrire <em>comment</em> elles
 * génèrent leurs candidats, et à appeler {@link #matches(String, String)} pour
 * les tester. Le contrat public reste celui de l'interface {@code HashCracker}.</p>
 */
public abstract class AbstractHashCracker implements HashCracker {

    /** Nombre de candidats testés lors de la dernière opération de cassage. */
    private long attempts = 0;

    /**
     * Teste un candidat contre le hash recherché et incrémente le compteur
     * de tentatives.
     *
     * @param candidate   le mot de passe candidat en clair
     * @param targetHash  le hash MD5 recherché
     * @return {@code true} si le hash du candidat correspond au hash recherché
     */
    protected boolean matches(String candidate, String targetHash) {
        attempts++;
        return Md5Utils.md5(candidate).equalsIgnoreCase(targetHash);
    }

    /**
     * Réinitialise le compteur de tentatives. Appelé au début de chaque cassage.
     */
    protected void resetAttempts() {
        attempts = 0;
    }

    /**
     * @return le nombre de tentatives effectuées lors du dernier cassage
     */
    public long getAttempts() {
        return attempts;
    }
}
