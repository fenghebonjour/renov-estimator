-- phpMyAdmin SQL Dump
-- version 4.5.4.1
-- http://www.phpmyadmin.net
--
-- Client :  localhost
-- Généré le :  Sam 23 Octobre 2021 à 01:06
-- Version du serveur :  5.7.11
-- Version de PHP :  5.6.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `renovdb`
--

-- --------------------------------------------------------

--
-- Structure de la table `adresse`
--

CREATE TABLE `adresse` (
  `adresse_id` bigint(20) NOT NULL,
  `appartement` text,
  `code_postal` text,
  `numero_civ` text NOT NULL,
  `pays` text NOT NULL,
  `province` text,
  `rue` text NOT NULL,
  `ville` text NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `adresse`
--

INSERT INTO `adresse` (`adresse_id`, `appartement`, `code_postal`, `numero_civ`, `pays`, `province`, `rue`, `ville`) VALUES
(1, '06', 'H1K 2X8', '7595', 'Canada', 'QC', 'Des ormeaux', 'Anjou'),
(2, '04', 'H1H 2X2', '12000', 'Canada', 'QC', 'Morfin', 'Lava');

-- --------------------------------------------------------

--
-- Structure de la table `adresse_sequence`
--

CREATE TABLE `adresse_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `adresse_sequence`
--

INSERT INTO `adresse_sequence` (`next_val`) VALUES
(3);

-- --------------------------------------------------------

--
-- Structure de la table `adresse_utilisateur`
--

CREATE TABLE `adresse_utilisateur` (
  `adresse_id` bigint(20) NOT NULL,
  `utilisateur_id` bigint(20) NOT NULL,
  `type_adresse` text NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `appeldoffre_sequence`
--

CREATE TABLE `appeldoffre_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `appeldoffre_sequence`
--

INSERT INTO `appeldoffre_sequence` (`next_val`) VALUES
(9);

-- --------------------------------------------------------

--
-- Structure de la table `appel_offre`
--

CREATE TABLE `appel_offre` (
  `appel_offre_id` bigint(20) NOT NULL,
  `date_debut_trav` date NOT NULL,
  `date_demande` date NOT NULL,
  `date_fin_trav` date NOT NULL,
  `date_limite` date NOT NULL,
  `statut` text NOT NULL,
  `type` text NOT NULL,
  `utilisateur_id` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `appel_offre`
--

INSERT INTO `appel_offre` (`appel_offre_id`, `date_debut_trav`, `date_demande`, `date_fin_trav`, `date_limite`, `statut`, `type`, `utilisateur_id`) VALUES
(6, '2021-10-25', '2021-10-04', '2021-10-29', '2021-10-22', 'Ouvert', 'Platre', NULL),
(5, '2021-10-25', '2021-10-11', '2021-10-29', '2021-10-15', 'Ouvert', 'Charpente', 11),
(4, '2021-10-18', '2021-10-04', '2021-10-22', '2021-10-08', 'Ouvert', 'Peinture', 11),
(7, '2021-10-20', '2021-10-06', '2021-10-27', '2021-10-13', 'Ouvert', 'Peinture', 11);

-- --------------------------------------------------------

--
-- Structure de la table `client`
--

CREATE TABLE `client` (
  `courriel` text NOT NULL,
  `nom` text NOT NULL,
  `prenom` text NOT NULL,
  `telephone` text,
  `utilisateur_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `client`
--

INSERT INTO `client` (`courriel`, `nom`, `prenom`, `telephone`, `utilisateur_id`) VALUES
('abdel.alain@bar.com', 'Alain', 'Abdel', '514 234 1231', 11),
('benjamin@bar.com', 'Birdman', 'Benjamin', '514 234 1232', 12),
('carl@bar.com', 'Claire', 'Carl', '5142341003', 13),
('david@bar.com', 'Denis', 'David', '514 676 004', 14);

-- --------------------------------------------------------

--
-- Structure de la table `contracteur`
--

CREATE TABLE `contracteur` (
  `activite` text NOT NULL,
  `annee_experience` int(11) NOT NULL,
  `courriel` text NOT NULL,
  `note` int(11) DEFAULT NULL,
  `telephone` text NOT NULL,
  `utilisateur_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `contracteur`
--

INSERT INTO `contracteur` (`activite`, `annee_experience`, `courriel`, `note`, `telephone`, `utilisateur_id`) VALUES
('peinture', 5, 'henry.smart@bar.com', 0, '514 432 3001', 4),
('Charpente', 15, 'pacific@tmd.com', 0, '5140073002', 5),
('Peinture', 8, 'stm@boobar.com', 0, '438 999 0209', 15),
('Charpente', 10, 'kevin@boobar.com', 0, '4380074356', 16);

-- --------------------------------------------------------

--
-- Structure de la table `entreprise`
--

CREATE TABLE `entreprise` (
  `contact` text NOT NULL,
  `nom` text NOT NULL,
  `utilisateur_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `individu`
--

CREATE TABLE `individu` (
  `certificat` text NOT NULL,
  `nom` text NOT NULL,
  `prenom` text NOT NULL,
  `utilisateur_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `individu`
--

INSERT INTO `individu` (`certificat`, `nom`, `prenom`, `utilisateur_id`) VALUES
('Numero2021AlainPeintre', 'Flouflou', 'Alain', 2);

-- --------------------------------------------------------

--
-- Structure de la table `individus`
--

CREATE TABLE `individus` (
  `prenom` text NOT NULL,
  `certificat` text NOT NULL,
  `nom` text NOT NULL,
  `utilisateur_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `individus`
--

INSERT INTO `individus` (`prenom`, `certificat`, `nom`, `utilisateur_id`) VALUES
('Henry', 'P123450001', 'Smart', 4),
('Pacific', 'P123450007', 'TMD Co Ltd.', 5),
('Sycamore', 'P12340009', 'ATM Group', 15),
('Kevin', 'P12340011', 'Kelly', 16);

-- --------------------------------------------------------

--
-- Structure de la table `main_oeuvre`
--

CREATE TABLE `main_oeuvre` (
  `main_oeuvre_id` bigint(20) NOT NULL,
  `description` text NOT NULL,
  `grade` int(11) DEFAULT NULL,
  `salaire` double DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `main_oeuvre`
--

INSERT INTO `main_oeuvre` (`main_oeuvre_id`, `description`, `grade`, `salaire`) VALUES
(1, 'peintre', 3, 35),
(2, 'plombier', 2, 40);

-- --------------------------------------------------------

--
-- Structure de la table `main_oeuvre_sequence`
--

CREATE TABLE `main_oeuvre_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `main_oeuvre_sequence`
--

INSERT INTO `main_oeuvre_sequence` (`next_val`) VALUES
(3);

-- --------------------------------------------------------

--
-- Structure de la table `materiaux`
--

CREATE TABLE `materiaux` (
  `materiaux_id` bigint(20) NOT NULL,
  `description` text,
  `prix_unitaire` double DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `materiaux`
--

INSERT INTO `materiaux` (`materiaux_id`, `description`, `prix_unitaire`) VALUES
(1, 'peinture Alpha', 45),
(2, 'peinture Beta', 33);

-- --------------------------------------------------------

--
-- Structure de la table `materiaux_sequence`
--

CREATE TABLE `materiaux_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `materiaux_sequence`
--

INSERT INTO `materiaux_sequence` (`next_val`) VALUES
(3);

-- --------------------------------------------------------

--
-- Structure de la table `ods_main_oeuvre`
--

CREATE TABLE `ods_main_oeuvre` (
  `main_oeuvre_id` bigint(20) NOT NULL,
  `offre_service_id` bigint(20) NOT NULL,
  `prix_unitaire` double DEFAULT NULL,
  `quantite` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `ods_materiaux`
--

CREATE TABLE `ods_materiaux` (
  `materiaux_id` bigint(20) NOT NULL,
  `offre_service_id` bigint(20) NOT NULL,
  `prix_unitaire` double DEFAULT NULL,
  `quantite` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `offre_de_service`
--

CREATE TABLE `offre_de_service` (
  `offre_de_service_id` bigint(20) NOT NULL,
  `contracteur` tinyblob,
  `date_offre` date NOT NULL,
  `date_valide` date NOT NULL,
  `montant` double NOT NULL,
  `statut` text NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `offre_de_service_sequence`
--

CREATE TABLE `offre_de_service_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `offre_de_service_sequence`
--

INSERT INTO `offre_de_service_sequence` (`next_val`) VALUES
(1);

-- --------------------------------------------------------

--
-- Structure de la table `offre_service`
--

CREATE TABLE `offre_service` (
  `offre_service_id` bigint(20) NOT NULL,
  `date_offre` date DEFAULT NULL,
  `date_valide` date DEFAULT NULL,
  `montant` double DEFAULT NULL,
  `statut` text,
  `appel_offre_id` bigint(20) DEFAULT NULL,
  `utilisateur_id` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `offre_service`
--

INSERT INTO `offre_service` (`offre_service_id`, `date_offre`, `date_valide`, `montant`, `statut`, `appel_offre_id`, `utilisateur_id`) VALUES
(7, '2021-10-04', NULL, 15000, 'Ouvert2222222222', NULL, NULL),
(8, '2021-10-04', NULL, 15000, 'Ouvert', 4, 4);

-- --------------------------------------------------------

--
-- Structure de la table `offre_service_sequence`
--

CREATE TABLE `offre_service_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `offre_service_sequence`
--

INSERT INTO `offre_service_sequence` (`next_val`) VALUES
(9);

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

CREATE TABLE `utilisateur` (
  `utilisateur_id` bigint(20) NOT NULL,
  `date_inscription` date NOT NULL,
  `password` text NOT NULL,
  `type` text NOT NULL,
  `username` text NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `utilisateur`
--

INSERT INTO `utilisateur` (`utilisateur_id`, `date_inscription`, `password`, `type`, `username`) VALUES
(4, '2021-10-12', 'contracteur001', 'Individus', 'contracteur001'),
(5, '2021-10-20', 'contracteur002', 'Entreprise', 'contracteur002'),
(11, '2021-10-10', 'client001', 'Client', 'client001'),
(12, '2021-10-06', 'client002', 'Client', 'client002'),
(13, '2021-10-22', 'client3', 'Client', 'client3'),
(14, '2021-10-22', 'client4', 'Client', 'client4'),
(15, '2021-10-22', 'contracteur003', 'Individus', 'contracteur003'),
(16, '2021-10-22', 'contracteur004', 'Individus', 'contracteur004');

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur_sequence`
--

CREATE TABLE `utilisateur_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `utilisateur_sequence`
--

INSERT INTO `utilisateur_sequence` (`next_val`) VALUES
(21);

--
-- Index pour les tables exportées
--

--
-- Index pour la table `adresse`
--
ALTER TABLE `adresse`
  ADD PRIMARY KEY (`adresse_id`);

--
-- Index pour la table `adresse_utilisateur`
--
ALTER TABLE `adresse_utilisateur`
  ADD PRIMARY KEY (`adresse_id`,`utilisateur_id`),
  ADD KEY `adresseUtilisateur_utilisateur_fk` (`utilisateur_id`);

--
-- Index pour la table `appel_offre`
--
ALTER TABLE `appel_offre`
  ADD PRIMARY KEY (`appel_offre_id`),
  ADD KEY `appel_offre_client_fk` (`utilisateur_id`);

--
-- Index pour la table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`utilisateur_id`);

--
-- Index pour la table `contracteur`
--
ALTER TABLE `contracteur`
  ADD PRIMARY KEY (`utilisateur_id`);

--
-- Index pour la table `entreprise`
--
ALTER TABLE `entreprise`
  ADD PRIMARY KEY (`utilisateur_id`);

--
-- Index pour la table `individu`
--
ALTER TABLE `individu`
  ADD PRIMARY KEY (`utilisateur_id`);

--
-- Index pour la table `individus`
--
ALTER TABLE `individus`
  ADD PRIMARY KEY (`utilisateur_id`);

--
-- Index pour la table `main_oeuvre`
--
ALTER TABLE `main_oeuvre`
  ADD PRIMARY KEY (`main_oeuvre_id`);

--
-- Index pour la table `materiaux`
--
ALTER TABLE `materiaux`
  ADD PRIMARY KEY (`materiaux_id`);

--
-- Index pour la table `ods_main_oeuvre`
--
ALTER TABLE `ods_main_oeuvre`
  ADD PRIMARY KEY (`main_oeuvre_id`,`offre_service_id`),
  ADD KEY `ods_main_oeuvre_service_fk` (`offre_service_id`);

--
-- Index pour la table `ods_materiaux`
--
ALTER TABLE `ods_materiaux`
  ADD PRIMARY KEY (`materiaux_id`,`offre_service_id`),
  ADD KEY `ods_materiaux_offre_service_fk` (`offre_service_id`);

--
-- Index pour la table `offre_de_service`
--
ALTER TABLE `offre_de_service`
  ADD PRIMARY KEY (`offre_de_service_id`);

--
-- Index pour la table `offre_service`
--
ALTER TABLE `offre_service`
  ADD PRIMARY KEY (`offre_service_id`),
  ADD KEY `offre_service_appel_offre_fk` (`appel_offre_id`),
  ADD KEY `offre_service_contracteur_fk` (`utilisateur_id`);

--
-- Index pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD PRIMARY KEY (`utilisateur_id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
