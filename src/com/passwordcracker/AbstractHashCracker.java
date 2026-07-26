package com.passwordcracker;

/**
 * Classe abstraite de base pour les stratégies de cassage.
 *
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
