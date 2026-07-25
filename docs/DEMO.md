# Script de démonstration (vidéo ≤ 10 minutes)

Déroulé suggéré pour la vidéo de présentation de l'outil. Objectif : montrer le fonctionnement **et** expliquer le rôle de la fabrique.

## 0. Introduction (≈ 1 min)
- Présenter le projet : retrouver un mot de passe à partir d'un hash MD5.
- Rappeler les deux méthodes : `DICO` (dictionnaire) et `BRUTE` (force brute).
- Montrer rapidement l'arborescence du projet et le diagramme UML (`docs/uml.svg`).

## 1. Compilation (≈ 1 min)
```bash
javac -d out $(find src -name "*.java")
cp dictionary.txt out/
```
Ou simplement montrer le script :
```bash
chmod +x passwordCracker
```

## 2. Attaque par dictionnaire (≈ 2 min)
Générer un hash puis le casser :
```bash
echo -n "test" | md5sum          # 098f6bcd4621d373cade4e832627b4f6
./passwordCracker -m DICO -h 098f6bcd4621d373cade4e832627b4f6
```
Montrer aussi un mot absent du dictionnaire → `Password not found`.
```bash
./passwordCracker -m DICO -h ffffffffffffffffffffffffffffffff
```

## 3. Attaque par force brute (≈ 2 min)
Un mot court, puis un mot de 4 lettres pour montrer l'explosion combinatoire :
```bash
./passwordCracker -m BRUTE -h 900150983cd24fb0d6963f7d28e17f72   # abc
./passwordCracker -m BRUTE -h 098f6bcd4621d373cade4e832627b4f6   # test
```
Commenter le nombre de tentatives et le temps affichés.

## 4. Le patron Simple Factory (≈ 2 min)
- Ouvrir `HashCrackerFactory.java` et montrer la méthode `create`.
- Ouvrir `PasswordCracker.java` : souligner qu'aucun `new DictionaryHashCracker()` n'apparaît dans le `main` — tout passe par la fabrique.
- Montrer la gestion des erreurs :
```bash
./passwordCracker -m FOO -h 098f6bcd4621d373cade4e832627b4f6
```

## 5. Conclusion (≈ 1 min)
- Rappeler l'avantage (centralisation, découplage).
- Rappeler la limite : ajouter une stratégie impose de modifier la fabrique → violation du principe Open/Closed, corrigée au mini-projet suivant.
