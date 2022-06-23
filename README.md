<p align="center">
  <img src="https://user-images.githubusercontent.com/66992287/168602112-a43a41a7-3830-4611-9a26-73db3fcfc5b6.png" style="height:150px;">
  <img src="https://user-images.githubusercontent.com/66992287/168603074-2f457d63-6e56-4388-b0b7-ff0d365a4502.png" style="height:150px;"><br>
  <b>SAE Parc Naturel Régional du Morbihan</b>
</p>
<p align="center">
  <img src="https://github.com/ShockedPlot7560/sae_pnr/workflows/CI/badge.svg" alt="CI" />
  <a href="https://shockedplot7560.github.io/sae_pnr/" ><img src="https://img.shields.io/badge/JavaDoc-Online-green" alt="Javadoc" /></a>
</p>

---

# Prérequis

- **Java 17** ou plus. [Disponible ici](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- **Maven 3.6.xx** minimum. [Disponible ici](https://maven.apache.org/download.cgi)
- **JDBC** pour MySQL. [Tutoriel pour windows ici](https://www.tutorialspoint.com/jdbc/jdbc-environment-setup.htm)

> Pour les système sous Windows, suivre [ce lien](https://phoenixnap.com/kb/install-maven-windows)

# Lancement

## Sur Windows

Double-cliquez sur le fichier `start-windows.bat`.

## Sur Linux et MacOS

Lancez la commande suivante dans un terminal situé dans le même dossier que ce fichier :

```bash
$ ./start.sh
```

## Pour le développement (Toutes plateformes)

```bash
mvn javafx:run
```

# Tests

> Utilisation de [Junit](https://junit.org/junit5/) pour les tests unitaires

```bash
mvn test
```
