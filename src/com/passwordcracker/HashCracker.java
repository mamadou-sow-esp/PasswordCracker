package com.passwordcracker;

/**
 * Interface commune à toutes les stratégies de cassage de hash MD5.
 *
 * <p>Cette interface est imposée par le sujet : elle définit le contrat que
 * chaque stratégie concrète doit respecter. Grâce au polymorphisme, le
 * programme principal manipule uniquement ce type, sans connaître la classe
 * concrète réellement instanciée par la fabrique.</p>
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
