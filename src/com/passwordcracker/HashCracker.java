package com.passwordcracker;

/**
 * Interface commune à toutes les stratégies de cassage de hash MD5.
 */
public interface HashCracker {

    /**
     * Tente de retrouver le mot de passe correspondant au hash MD5 fourni.
     *
     * @param hash le hash MD5 (32 caractères hexadécimaux) recherché
     * @return le mot de passe trouvé, ou {@code null} si aucun résultat n'est obtenu
     */
    String crack(String hash);
}
