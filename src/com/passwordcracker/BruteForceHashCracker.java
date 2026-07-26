package com.passwordcracker;

/**
 * Stratégie de cassage par <strong>force brute</strong>.
 *
 */
public class BruteForceHashCracker extends AbstractHashCracker {

    /** Alphabet utilisé pour la génération des combinaisons. */
    public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    /** Longueur maximale des mots de passe testés. */
    public static final int DEFAULT_MAX_LENGTH = 4;

    private final int maxLength;

    /** Construit une stratégie avec la longueur maximale par défaut (4). */
    public BruteForceHashCracker() {
        this(DEFAULT_MAX_LENGTH);
    }

    /**
     * Construit une stratégie avec une longueur maximale personnalisée.
     *
     * @param maxLength longueur maximale des combinaisons générées
     */
    public BruteForceHashCracker(int maxLength) {
        if (maxLength < 1) {
            throw new IllegalArgumentException("La longueur maximale doit être >= 1");
        }
        this.maxLength = maxLength;
    }

    @Override
    public String crack(String hash) {
        resetAttempts();
        final String target = hash.trim().toLowerCase();

        for (int length = 1; length <= maxLength; length++) {
            String result = generate(new char[length], 0, target);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    /**
     * Génère récursivement toutes les combinaisons d'une longueur donnée et les
     * teste contre le hash recherché.
     *
     * @param buffer tampon de construction du mot courant
     * @param pos    position courante à remplir dans le tampon
     * @param target hash MD5 recherché
     * @return le mot trouvé, ou {@code null} si aucune combinaison ne correspond
     */
    private String generate(char[] buffer, int pos, String target) {
        if (pos == buffer.length) {
            String candidate = new String(buffer);
            return matches(candidate, target) ? candidate : null;
        }
        for (int i = 0; i < ALPHABET.length(); i++) {
            buffer[pos] = ALPHABET.charAt(i);
            String result = generate(buffer, pos + 1, target);
            if (result != null) {
                return result;
            }
        }
        return null;
    }
}
