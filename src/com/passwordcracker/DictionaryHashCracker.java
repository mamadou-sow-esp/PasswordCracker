package com.passwordcracker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Stratégie de cassage par <strong>dictionnaire</strong>.
 *
 */
public class DictionaryHashCracker extends AbstractHashCracker {

    /** Nom du dictionnaire utilisé par défaut. */
    public static final String DEFAULT_DICTIONARY = "dictionary.txt";

    private final String dictionaryName;

    /** Construit une stratégie utilisant le dictionnaire par défaut. */
    public DictionaryHashCracker() {
        this(DEFAULT_DICTIONARY);
    }

    /**
     * Construit une stratégie utilisant un dictionnaire précis.
     *
     * @param dictionaryName chemin (ou nom de ressource) du dictionnaire
     */
    public DictionaryHashCracker(String dictionaryName) {
        this.dictionaryName = dictionaryName;
    }

    @Override
    public String crack(String hash) {
        resetAttempts();
        final String target = hash.trim().toLowerCase();

        try (BufferedReader reader = openDictionary()) {
            if (reader == null) {
                System.err.println("Dictionnaire introuvable : " + dictionaryName);
                return null;
            }
            String word;
            while ((word = reader.readLine()) != null) {
                word = word.trim();
                if (word.isEmpty()) {
                    continue;
                }
                if (matches(word, target)) {
                    return word;
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur de lecture du dictionnaire : " + e.getMessage());
        }
        return null;
    }

    /**
     * Ouvre le dictionnaire : d'abord sur le système de fichiers, puis dans le
     * classpath si le fichier n'existe pas sur le disque.
     */
    private BufferedReader openDictionary() throws IOException {
        Path path = Path.of(dictionaryName);
        if (Files.exists(path)) {
            return Files.newBufferedReader(path, StandardCharsets.UTF_8);
        }
        InputStream resource = getClass().getClassLoader()
                .getResourceAsStream(dictionaryName);
        if (resource != null) {
            return new BufferedReader(new InputStreamReader(resource, StandardCharsets.UTF_8));
        }
        return null;
    }
}
