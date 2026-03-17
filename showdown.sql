-- phpMyAdmin SQL Dump
-- version 5.2.3
-- https://www.phpmyadmin.net/
--
-- Hôte : db
-- Généré le : mar. 17 mars 2026 à 15:35
-- Version du serveur : 5.7.44
-- Version de PHP : 8.3.26

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
-- Structure de la table `attack`
--

CREATE TABLE `attack` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `power` int(11) NOT NULL,
  `type_id` int(11) NOT NULL,
  `category` varchar(255) NOT NULL,
  `secondary_effect_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `attack`
--

INSERT INTO `attack` (`id`, `name`, `power`, `type_id`, `category`, `secondary_effect_id`) VALUES
(1, 'Boutefeu', 120, 2, 'Physical', 1),
(2, 'Giga-Sangsue', 75, 4, 'special', 2),
(3, 'Lance-flamme', 90, 2, 'special', 3),
(4, 'Choc Venimeux', 65, 8, 'special', 4),
(5, 'Tonnerre', 90, 5, 'special', 5),
(6, 'Draco-Choc', 85, 15, 'special', 6),
(7, 'Rapace', 120, 10, 'physical', 7);

-- --------------------------------------------------------

--
-- Structure de la table `item`
--

CREATE TABLE `item` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `affected_stat` varchar(50) DEFAULT NULL,
  `modifier` double DEFAULT '1',
  `effect_type` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `item`
--

INSERT INTO `item` (`id`, `name`, `description`, `affected_stat`, `modifier`, `effect_type`) VALUES
(1, 'Leftovers', 'Heals 1/16 HP at the end of turn', 'hp', 0.0625, 'HEAL_TURN'),
(2, 'Life Orb', 'Boosts damage but costs 10% HP', 'all', 1.3, 'DAMAGE_BOOST_RECOIL'),
(3, 'Sitrus Berry', 'Heals 25% HP when health is low', 'hp', 0.25, 'HEAL_ONCE'),
(4, 'Rocky Helmet', 'Damages attacker on physical contact', 'hp', 0.16, 'REFLECT'),
(5, 'Focus Sash', 'Prevents 1-hit KO if HP is full', 'hp', 1, 'ENDURE');

-- --------------------------------------------------------

--
-- Structure de la table `pokemon`
--

CREATE TABLE `pokemon` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `type` int(11) NOT NULL,
  `type2` int(11) DEFAULT NULL,
  `hp` int(11) NOT NULL,
  `attack` int(11) NOT NULL,
  `defense` int(11) NOT NULL,
  `spe_attack` int(11) NOT NULL,
  `spe_defense` int(11) NOT NULL,
  `speed` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `pokemon`
--

INSERT INTO `pokemon` (`id`, `name`, `type`, `type2`, `hp`, `attack`, `defense`, `spe_attack`, `spe_defense`, `speed`) VALUES
(1, 'Rathalos', 2, 10, 360, 267, 255, 317, 269, 299),
(2, 'Fatalis', 14, 15, 504, 339, 299, 339, 299, 279),
(3, 'Rey Dau', 15, 5, 404, 399, 339, 339, 299, 279),
(4, 'Hermitaur daimyo', 3, NULL, 346, 245, 275, 339, 277, 217),
(5, 'Alatreon', 2, 6, 404, 339, 299, 399, 339, 279),
(6, 'Zinogre', 5, NULL, 362, 309, 239, 389, 259, 301),
(7, 'Mizutsune', 3, 2, 364, 319, 339, 359, 279, 239),
(8, 'Nergigante', 7, 1, 334, 371, 287, 207, 291, 370),
(9, 'Brachydios', 2, 7, 424, 395, 249, 319, 319, 249),
(10, 'Arkveld', 5, 15, 404, 269, 299, 369, 329, 369);

-- --------------------------------------------------------

--
-- Structure de la table `pokemon_attacks`
--

CREATE TABLE `pokemon_attacks` (
  `id_attacks` int(11) NOT NULL,
  `id_pokemon` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `pokemon_attacks`
--

INSERT INTO `pokemon_attacks` (`id_attacks`, `id_pokemon`) VALUES
(1, 1),
(3, 1),
(7, 1),
(6, 2),
(5, 3),
(3, 5),
(6, 5),
(5, 6),
(2, 7),
(3, 7),
(6, 3),
(5, 3),
(2, 4),
(7, 8),
(1, 8),
(1, 9),
(3, 9),
(5, 10),
(6, 10);

-- --------------------------------------------------------

--
-- Structure de la table `type`
--

CREATE TABLE `type` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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
-- Index pour la table `attack`
--
ALTER TABLE `attack`
  ADD PRIMARY KEY (`id`),
  ADD KEY `type_id` (`type_id`);

--
-- Index pour la table `item`
--
ALTER TABLE `item`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `pokemon`
--
ALTER TABLE `pokemon`
  ADD PRIMARY KEY (`id`),
  ADD KEY `type` (`type`),
  ADD KEY `type2` (`type2`);

--
-- Index pour la table `pokemon_attacks`
--
ALTER TABLE `pokemon_attacks`
  ADD KEY `id_attacks` (`id_attacks`),
  ADD KEY `id_pokemon` (`id_pokemon`);

--
-- Index pour la table `type`
--
ALTER TABLE `type`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `attack`
--
ALTER TABLE `attack`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT pour la table `item`
--
ALTER TABLE `item`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT pour la table `pokemon`
--
ALTER TABLE `pokemon`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT pour la table `type`
--
ALTER TABLE `type`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `attack`
--
ALTER TABLE `attack`
  ADD CONSTRAINT `attack_ibfk_1` FOREIGN KEY (`type_id`) REFERENCES `type` (`id`);

--
-- Contraintes pour la table `pokemon`
--
ALTER TABLE `pokemon`
  ADD CONSTRAINT `pokemon_type` FOREIGN KEY (`type`) REFERENCES `type` (`id`),
  ADD CONSTRAINT `pokemon_type2` FOREIGN KEY (`type2`) REFERENCES `type` (`id`);

--
-- Contraintes pour la table `pokemon_attacks`
--
ALTER TABLE `pokemon_attacks`
  ADD CONSTRAINT `pokemon_attacks_ibfk_1` FOREIGN KEY (`id_attacks`) REFERENCES `attack` (`id`),
  ADD CONSTRAINT `pokemon_attacks_ibfk_2` FOREIGN KEY (`id_pokemon`) REFERENCES `pokemon` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
