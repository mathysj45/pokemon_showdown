-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : jeu. 12 mars 2026 à 16:20
-- Version du serveur : 10.4.32-MariaDB
-- Version de PHP : 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `showdown`
--

-- --------------------------------------------------------

--
-- Structure de la table `pokemon`
--

CREATE TABLE `pokemon` (
                           `id` int(11) NOT NULL,
                           `sprite` varchar(255) DEFAULT NULL,
                           `name` varchar(255) NOT NULL,
                           `type` int(11) NOT NULL,
                           `type2` int(11) DEFAULT NULL,
                           `pv` int(11) NOT NULL,
                           `attack` int(11) NOT NULL,
                           `defense` int(11) NOT NULL,
                           `spe attack` int(11) NOT NULL,
                           `spe defense` int(11) NOT NULL,
                           `speed` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `pokemon`
--

INSERT INTO `pokemon` (`id`, `sprite`, `name`, `type`, `type2`, `pv`, `attack`, `defense`, `spe attack`, `spe defense`, `speed`) VALUES
    (1, NULL, 'rathalos', 2, 10, 60, 50, 30, 70, 40, 90);

-- --------------------------------------------------------

--
-- Structure de la table `type`
--

CREATE TABLE `type` (
                        `id` int(11) NOT NULL,
                        `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `type`
--

INSERT INTO `type` (`id`, `name`) VALUES
                                      (1, 'normal'),
                                      (2, 'fire'),
                                      (3, 'water'),
                                      (4, 'grass'),
                                      (5, 'electric'),
                                      (6, 'ice'),
                                      (7, 'fighting'),
                                      (8, 'poison'),
                                      (9, 'ground'),
                                      (10, 'flying'),
                                      (11, 'psychic'),
                                      (12, 'bug'),
                                      (13, 'rock'),
                                      (14, 'ghost'),
                                      (15, 'dragon'),
                                      (16, 'dark'),
                                      (17, 'steel'),
                                      (18, 'fairy');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `pokemon`
--
ALTER TABLE `pokemon`
    ADD PRIMARY KEY (`id`),
  ADD KEY `type` (`type`),
  ADD KEY `type2` (`type2`);

--
-- Index pour la table `type`
--
ALTER TABLE `type`
    ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `pokemon`
--
ALTER TABLE `pokemon`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `type`
--
ALTER TABLE `type`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `pokemon`
--
ALTER TABLE `pokemon`
    ADD CONSTRAINT `pokemon_type` FOREIGN KEY (`type`) REFERENCES `type` (`id`),
  ADD CONSTRAINT `pokemon_type2` FOREIGN KEY (`type2`) REFERENCES `type` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;