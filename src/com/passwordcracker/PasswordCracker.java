package com.passwordcracker;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/**
 * Application console {@code passwordCracker}.
 *
 * <p>Point d'entrée de l'outil. Analyse les arguments de la ligne de commande,
 * demande à la fabrique la stratégie adaptée, lance le cassage et affiche le
 * résultat ainsi que quelques informations utiles (temps d'exécution, nombre
 * de tentatives).</p>
 *
 * <p>Usage :</p>
 * <pre>
 *   passwordCracker -m BRUTE -h &lt;hashMD5&gt;
 *   passwordCracker -m DICO  -h &lt;hashMD5&gt;
 * </pre>
 */
public class PasswordCracker {

    public static void main(String[] args) {
        // Force un affichage UTF-8 quelle que soit la locale du système.
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        System.setErr(new PrintStream(System.err, true, StandardCharsets.UTF_8));

        String method = getArg(args, "-m");
        String hash = getArg(args, "-h");

        if (method == null || hash == null) {
            printUsage();
            return;
        }

        if (!isValidMd5(hash)) {
            System.out.println("Hash MD5 invalide : un hash MD5 comporte 32 caractères hexadécimaux.");
            return;
        }

        // La création passe UNIQUEMENT par la fabrique (patron Simple Factory).
        final HashCracker cracker;
        try {
            cracker = HashCrackerFactory.create(method);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.println("Méthode : " + method.toUpperCase() + "  |  Hash cible : " + hash);
        System.out.println("Recherche en cours...");

        long start = System.nanoTime();
        String password = cracker.crack(hash);
        double seconds = (System.nanoTime() - start) / 1_000_000_000.0;

        System.out.println("--------------------------------------------------");
        if (password != null) {
            System.out.println("Password found: " + password);
        } else {
            System.out.println("Password not found");
        }

        if (cracker instanceof AbstractHashCracker base) {
            System.out.printf("Nombre de tentatives : %,d%n", base.getAttempts());
        }
        System.out.printf("Temps d'exécution   : %.3f s%n", seconds);
    }

    /**
     * Récupère la valeur associée à une option (ex. {@code -m}) dans les arguments.
     *
     * @return la valeur, ou {@code null} si l'option est absente
     */
    private static String getArg(String[] args, String flag) {
        for (int i = 0; i < args.length - 1; i++) {
            if (flag.equals(args[i])) {
                return args[i + 1];
            }
        }
        return null;
    }

    /** Vérifie qu'une chaîne a la forme d'un hash MD5 (32 caractères hexadécimaux). */
    private static boolean isValidMd5(String hash) {
        return hash != null && hash.matches("(?i)[0-9a-f]{32}");
    }

    private static void printUsage() {
        System.out.println("Usage :");
        System.out.println("  passwordCracker -m <BRUTE|DICO> -h <hashMD5>");
        System.out.println();
        System.out.println("Exemples :");
        System.out.println("  passwordCracker -m DICO  -h 098f6bcd4621d373cade4e832627b4f6");
        System.out.println("  passwordCracker -m BRUTE -h 098f6bcd4621d373cade4e832627b4f6");
    }
}
