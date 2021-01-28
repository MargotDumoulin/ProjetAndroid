-- phpMyAdmin SQL Dump
-- version 4.4.15.10
-- https://www.phpmyadmin.net
--
-- Host: devbdd.iutmetz.univ-lorraine.fr
-- Generation Time: Jan 28, 2021 at 07:12 PM
-- Server version: 10.3.27-MariaDB
-- PHP Version: 7.1.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dumouli15u_android`
--

-- --------------------------------------------------------

--
-- Table structure for table `Categorie`
--

DROP TABLE IF EXISTS `Categorie`;
CREATE TABLE IF NOT EXISTS `Categorie` (
  `id_categorie` int(3) NOT NULL,
  `titre` varchar(30) NOT NULL,
  `visuel` varchar(30) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Categorie`
--

INSERT INTO `Categorie` (`id_categorie`, `titre`, `visuel`) VALUES
(1, 'Pulls', 'pull0.jpg'),
(2, 'Bonnets', 'bonnet0.jpg'),
(3, 'Casquettes', 'casquette0.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `Client`
--

DROP TABLE IF EXISTS `Client`;
CREATE TABLE IF NOT EXISTS `Client` (
  `id_client` int(255) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `prenom` varchar(255) NOT NULL,
  `identifiant` varchar(255) NOT NULL,
  `mot_de_passe` varchar(255) NOT NULL,
  `adr_numero` varchar(255) NOT NULL,
  `adr_voie` varchar(255) NOT NULL,
  `adr_code_postal` varchar(255) NOT NULL,
  `adr_ville` varchar(255) NOT NULL,
  `adr_pays` varchar(255) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

--
-- Dumping data for table `Client`
--

INSERT INTO `Client` (`id_client`, `nom`, `prenom`, `identifiant`, `mot_de_passe`, `adr_numero`, `adr_voie`, `adr_code_postal`, `adr_ville`, `adr_pays`) VALUES
(37, 'MACKOW', 'Kamil', 'mackow1u', '$2y$10$3C6aGo8aR3ksKuB4polHZOniQNm6rBIzLThApHDXFGrMkRwH.94XW', '11', 'Rue Lucie Aubrac', '57525', 'Talange', 'France'),
(38, 'DUMOULIN', 'Margot', 'dumouli15u', '$2y$10$fqDfWv1XiKn7bFBpaUFlfeVAw7D3dJL6zLGXf57RX32OS9pY2YPHm', '11', 'Avenue Foch', '57000', 'Metz', 'France');

-- --------------------------------------------------------

--
-- Table structure for table `Commande`
--

DROP TABLE IF EXISTS `Commande`;
CREATE TABLE IF NOT EXISTS `Commande` (
  `id_commande` int(255) NOT NULL,
  `date_commande` datetime NOT NULL,
  `id_client` int(255) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- --------------------------------------------------------

--
-- Table structure for table `Favori`
--

DROP TABLE IF EXISTS `Favori`;
CREATE TABLE IF NOT EXISTS `Favori` (
  `id_client` int(255) NOT NULL,
  `id_produit` int(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- --------------------------------------------------------

--
-- Table structure for table `Ligne_Commande`
--

DROP TABLE IF EXISTS `Ligne_Commande`;
CREATE TABLE IF NOT EXISTS `Ligne_Commande` (
  `id_commande` int(255) NOT NULL,
  `id_produit` int(255) NOT NULL,
  `id_taille` int(255) NOT NULL,
  `quantite` int(255) NOT NULL,
  `tarif` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- --------------------------------------------------------

--
-- Table structure for table `Magasin`
--

DROP TABLE IF EXISTS `Magasin`;
CREATE TABLE IF NOT EXISTS `Magasin` (
  `id_magasin` int(255) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `latitude` decimal(10,8) NOT NULL,
  `longitude` decimal(11,8) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

--
-- Dumping data for table `Magasin`
--

INSERT INTO `Magasin` (`id_magasin`, `nom`, `latitude`, `longitude`) VALUES
(1, 'Metz', 49.11931623, 6.16274581),
(2, 'Nancy', 48.69249320, 6.18219004),
(3, 'Paris', 48.85764418, 2.34326832),
(4, 'Lille', 50.62953781, 3.05557148),
(5, 'Lyon', 45.76381841, 4.83237396),
(6, 'Strasbourg', 48.57159126, 7.75110612);

-- --------------------------------------------------------

--
-- Table structure for table `Mentions_legales`
--

DROP TABLE IF EXISTS `Mentions_legales`;
CREATE TABLE IF NOT EXISTS `Mentions_legales` (
  `langue` varchar(255) NOT NULL,
  `mentions` longtext NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

--
-- Dumping data for table `Mentions_legales`
--

INSERT INTO `Mentions_legales` (`langue`, `mentions`) VALUES
('en', 'In accordance with the provisions of Law No. 2004-575 of June 21, 2004 for confidence in the digital economy, readers of the MonPullMoche application are informed of the identity of the various stakeholders in the context of its implementation and monitoring.###Editing the application###MonPullMoche is published by LegalPlace, a simplified joint stock company with capital of 10 051.50 euros, whose registered office is located at 47 rue Marcel Dassault, 57000 Metz, registered in the trade and companies register under unique identification number 814 428 785 RCS Metz###Responsible for publication###Erwin MOREL / Margot DUMOULIN / Kamil MAĆKÓW###Host###MonPullMoche is hosted on Amazon Web Services LLC Address: P.O. Box 81226, Seattle, WA 98108-1226 The storage of users personal data is carried out exclusively on data centers ("clusters") located in Member States of the European Union of the company Amazon Web Services LLC###Contact us###support@monpullmoche.fr###CNIL###LegalPlace will keep in its computer systems and under reasonable security conditions proof of the transaction including the order form and invoice. LegalPlace has been declared to the CNIL under number 1986932. In accordance with the provisions of Law 78-17 of 6 January 1978 as amended, the user has the right to access, modify and delete the information collected by LegalPlace. It will be up to the user to send a message to the following address if he wishes to use this right: support@monpullmoche.fr###Disputes###Any dispute relating to the application or to this legal notice will be brought before the court of Metz. Use of the MonPullMoche application signifies your agreement to the application of this jurisdictional clause. In the event that any of the provisions of this legal notice would be deemed illegal, void or inapplicable for any reason whatsoever, it would be considered not to be part of this legal notice and would not affect the validity or application of other provisions.###Intellectual property###The content (in particular data, soundtracks, information, illustrations, logos, brands, etc.) that appear or are available in MonPullMoche are protected by copyright and other intellectual property rights and are the exclusive property of their respective publishers. Any copy, reproduction, representation, adaptation, alteration, modification, distribution, in whole or in part, of the content of the application in general, whether it belongs to it or to a third party, by any means whatsoever, is illegal except of a single copy, on a single computer and reserved for the exclusive private use of the copyist. The elements presented in the application are subject to change without notice and are made available without any guarantee of any kind, express or implied, may not give rise to any right to compensation. Any information or images found in the application are protected by copyright © CAPTAIN LEGAL or copyright of its partners###Services provided###We provide a sale service and an online store with different kinds of clothes.'),
('fr', 'Conformément aux dispositions de la loi n° 2004-575 du 21 juin 2004 pour la confiance en l''économie numérique, il est précisé aux utilisateurs de l''application MonPullMoche l’identité des différents intervenants dans le cadre de sa réalisation et de son suivi.###Edition de l''application###L''application MonPullMoche est éditée par la société LegalPlace, société par actions simplifiée au capital de 10.051,50 euros, dont le siège social est situé 47 rue Marcel Dassault, 57000 Metz, immatriculée au registre du commerce et des sociétés sous le numéro d’identification unique 814 428 785 RCS Metz.###Responsable de publication###Erwin MOREL / Margot DUMOULIN / Kamil MAĆKÓW###Hébergeur###L''application MonPullMoche est hébergée par la société Amazon Web Services LLC Adresse: P.O. Box 81226, Seattle, WA 98108-1226. Le stockage des données personnelles des utilisateurs est exclusivement réalisé sur les centre de données (« clusters ») localisés dans des Etats membres de l’Union Européenne de la société Amazon Web Services LLC.###\nNous contacter###Par mail: support@monpullmoche.fr.###CNIL###La société LegalPlace conservera dans ses systèmes informatiques une preuve de la transaction comprenant le bon de commande et la facture. La société LegalPlace a fait l’objet d’une déclaration à la CNIL sous le numéro 1986932. Conformément aux dispositions de la loi 78-17 du 6 janvier 1978, l’utilisateur dispose d’un droit d’accès, de modification et de suppression des informations collectées par LegalPlace. Pour exercer ce droit, il reviendra à l’Utilisateur d’envoyer un message à l’adresse suivante : support@monpullmoche.fr.###Litiges###Tout litige relatif à l''application ou à la présente notice légale sera porté devant le tribunal de Metz. L’utilisation de l''application MonPullMoche signifie votre accord à l’application de la présente clause juridictionnelle. Dans le cas où l’une des dispositions de cette notice légale serait jugée illégale, nulle ou inapplicable pour quelque raison que ce soit, elle serait considérée comme ne faisant pas partie de cette notice légale et n’affecterait pas la validité ni l’application des autres dispositions.###Propriété intellectuelle\n###Les contenus (notamment données, bandes sons, informations, illustrations, logos, marques, etc.) qui apparaissent ou sont disponibles sur l''aplication MonPullMoche sont protégés au titre du droit d’auteur et autres droits de propriété intellectuelle et sont la propriété exclusive de leurs éditeurs respectifs. Toute copie, reproduction, représentation, adaptation, altération, modification, diffusion, intégrale ou partielle, du contenu de l''aplication en général qu’il lui appartienne ou à un tiers, par quelque procédé que ce soit, est illicite à l’exception d’une unique copie, sur un seul ordinateur et réservée à l’usage exclusivement privé du copiste. Les éléments présentés dans l''aplication sont susceptibles de modification sans préavis et sont mis à disposition sans aucune garantie d’aucune sorte, expresse ou tacite. Les informations et les images contenues dans l''aplication sont protégées par copyright © CAPTAIN LEGAL ou copyright de ses partenaires###Services fournis###Nous proposons l''achat de vêtements dans une boutique en ligne.\n');

-- --------------------------------------------------------

--
-- Table structure for table `Produit`
--

DROP TABLE IF EXISTS `Produit`;
CREATE TABLE IF NOT EXISTS `Produit` (
  `id_produit` int(5) NOT NULL,
  `titre` varchar(50) NOT NULL,
  `description` varchar(500) NOT NULL,
  `tarif` float NOT NULL,
  `visuel` varchar(30) NOT NULL,
  `id_categorie` int(3) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Produit`
--

INSERT INTO `Produit` (`id_produit`, `titre`, `description`, `tarif`, `visuel`, `id_categorie`) VALUES
(1, 'A Noël c''est moi qui tient les rennes', 'Un pull qui ravira les petits et les grands. Tricoté par d''authentiques grand-mères Australiennes, en laine 100% coton naturel issue d''une filière agriculture durable certifiée ISO-2560.', 52, 'pull0.jpg', 1),
(2, 'Sonic te kiffe', 'Inspiré par la saga Séga (c''est plus fort que toi !), un pull 100% gamer qui te permettra de faire baver d''envie tes petits camarades de jeu.', 41.5, 'pull1.jpg', 1),
(3, 'Mon renne a les boules', 'Un grand classique revisité, à la fois renne et sapin de Noël, c''est toi le plus beau quand tu es décoré par ce pull !', 44, 'pull2.jpg', 1),
(4, 'Le père Noël a une gastro', 'Ah, le chic à la Française. Parce que les stars aussi vont sur le pot de temps en temps...', 35.2, 'pull3.jpg', 1),
(5, 'Une guirlande de pères Noël', 'Ils sont tous en rang, ils te regardent, à toi d''être bien sage pour mériter ce pull de fête !', 38, 'pull4.jpg', 1),
(6, 'La chaleur des rennes', 'Classique mais efficace, un bonnet dont l''élégance n''est pas à souligner, il vous grattera comme il faut !', 15, 'bonnet0.jpg', 2),
(7, 'Noël Lorientais', 'Idéal pour tous les fans du FC Lorient, mais pas que !', 22, 'bonnet1.jpg', 2),
(8, 'Beau comme un Elfe', 'HE bonnet à oreilles, couleurs indémodables pour cette création inspirée de l''univers de Tolkien.', 17, 'bonnet2.jpg', 2),
(9, 'Traineau de rennes', 'Un grand classique, ce magnifique bonnet vous sierra en toutes circonstances, et s''adaptera à toutes vos tenues hivernales.', 15, 'bonnet3.jpg', 2),
(10, 'Real Santa', 'En direct de NYC, avec bois de rennes télescopiques !!!', 20, 'bonnet4.jpg', 2),
(11, 'Les trois amis', 'Un trio féérique prendra soin de votre calvitie naissante, vous en serez ravi !', 30, 'casquette0.jpg', 3),
(12, 'Dall', 'Joyeux Noël avec nos petits lutins dansants !', 35, 'casquette1.jpg', 3),
(13, 'Magie de Noël', 'Quoi de plus beau qu''un bonhomme de neige avec les enfants quand les premiers flocons tombent du ciel ?', 26, 'casquette2.jpg', 3),
(14, 'Santa Hangover', 'Le Père Noël est bien fatigué sur cette magnifique casquette, mais n''est-ce pas notre lot à tous une fois les fêtes passées ?', 30, 'casquette3.jpg', 3);

-- --------------------------------------------------------

--
-- Table structure for table `Produit_taille`
--

DROP TABLE IF EXISTS `Produit_taille`;
CREATE TABLE IF NOT EXISTS `Produit_taille` (
  `id_produit` int(5) NOT NULL,
  `id_taille` int(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Produit_taille`
--

INSERT INTO `Produit_taille` (`id_produit`, `id_taille`) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 6),
(2, 1),
(2, 2),
(2, 3),
(2, 4),
(2, 5),
(2, 6),
(3, 2),
(3, 3),
(3, 4),
(3, 5),
(4, 1),
(4, 2),
(4, 3),
(4, 4),
(4, 5),
(4, 6),
(5, 1),
(5, 2),
(5, 3),
(5, 4),
(5, 5),
(5, 6),
(6, 7),
(7, 7),
(8, 7),
(9, 7),
(10, 7),
(11, 8),
(11, 9),
(11, 10),
(11, 11),
(11, 12),
(11, 13),
(12, 9),
(12, 11),
(12, 13),
(13, 8),
(13, 9),
(13, 10),
(13, 11),
(13, 12),
(13, 13),
(14, 8),
(14, 10),
(14, 11),
(14, 13);

-- --------------------------------------------------------

--
-- Table structure for table `Taille`
--

DROP TABLE IF EXISTS `Taille`;
CREATE TABLE IF NOT EXISTS `Taille` (
  `id_taille` int(2) NOT NULL,
  `libelle` varchar(5) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Taille`
--

INSERT INTO `Taille` (`id_taille`, `libelle`) VALUES
(1, 'XS'),
(2, 'S'),
(3, 'M'),
(4, 'L'),
(5, 'XL'),
(6, 'XXL'),
(7, 'TU'),
(8, '52'),
(9, '54'),
(10, '56'),
(11, '58'),
(12, '60'),
(13, '62');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Categorie`
--
ALTER TABLE `Categorie`
  ADD PRIMARY KEY (`id_categorie`);

--
-- Indexes for table `Client`
--
ALTER TABLE `Client`
  ADD PRIMARY KEY (`id_client`);

--
-- Indexes for table `Commande`
--
ALTER TABLE `Commande`
  ADD PRIMARY KEY (`id_commande`),
  ADD KEY `id_client` (`id_client`);

--
-- Indexes for table `Favori`
--
ALTER TABLE `Favori`
  ADD PRIMARY KEY (`id_client`,`id_produit`),
  ADD KEY `id_produit` (`id_produit`),
  ADD KEY `id_produit_2` (`id_produit`);

--
-- Indexes for table `Ligne_Commande`
--
ALTER TABLE `Ligne_Commande`
  ADD PRIMARY KEY (`id_commande`,`id_produit`,`id_taille`),
  ADD KEY `id_produit_ligne_commande` (`id_produit`),
  ADD KEY `id_taille_ligne_commande` (`id_taille`);

--
-- Indexes for table `Magasin`
--
ALTER TABLE `Magasin`
  ADD PRIMARY KEY (`id_magasin`);

--
-- Indexes for table `Mentions_legales`
--
ALTER TABLE `Mentions_legales`
  ADD PRIMARY KEY (`langue`);

--
-- Indexes for table `Produit`
--
ALTER TABLE `Produit`
  ADD PRIMARY KEY (`id_produit`),
  ADD KEY `id_categorie` (`id_categorie`);

--
-- Indexes for table `Produit_taille`
--
ALTER TABLE `Produit_taille`
  ADD PRIMARY KEY (`id_produit`,`id_taille`),
  ADD KEY `id_taille_produit_taille` (`id_taille`);

--
-- Indexes for table `Taille`
--
ALTER TABLE `Taille`
  ADD PRIMARY KEY (`id_taille`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `Categorie`
--
ALTER TABLE `Categorie`
  MODIFY `id_categorie` int(3) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `Client`
--
ALTER TABLE `Client`
  MODIFY `id_client` int(255) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=39;
--
-- AUTO_INCREMENT for table `Commande`
--
ALTER TABLE `Commande`
  MODIFY `id_commande` int(255) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=101;
--
-- AUTO_INCREMENT for table `Magasin`
--
ALTER TABLE `Magasin`
  MODIFY `id_magasin` int(255) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `Produit`
--
ALTER TABLE `Produit`
  MODIFY `id_produit` int(5) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=15;
--
-- AUTO_INCREMENT for table `Taille`
--
ALTER TABLE `Taille`
  MODIFY `id_taille` int(2) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=14;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `Commande`
--
ALTER TABLE `Commande`
  ADD CONSTRAINT `id_client_commande` FOREIGN KEY (`id_client`) REFERENCES `Client` (`id_client`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `Favori`
--
ALTER TABLE `Favori`
  ADD CONSTRAINT `id_client` FOREIGN KEY (`id_client`) REFERENCES `Client` (`id_client`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `id_produit` FOREIGN KEY (`id_produit`) REFERENCES `Produit` (`id_produit`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `Ligne_Commande`
--
ALTER TABLE `Ligne_Commande`
  ADD CONSTRAINT `id_commande_ligne_commande` FOREIGN KEY (`id_commande`) REFERENCES `Commande` (`id_commande`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `id_produit_ligne_commande` FOREIGN KEY (`id_produit`) REFERENCES `Produit` (`id_produit`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `id_taille_ligne_commande` FOREIGN KEY (`id_taille`) REFERENCES `Taille` (`id_taille`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `Produit`
--
ALTER TABLE `Produit`
  ADD CONSTRAINT `id_categorie` FOREIGN KEY (`id_categorie`) REFERENCES `Categorie` (`id_categorie`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `Produit_taille`
--
ALTER TABLE `Produit_taille`
  ADD CONSTRAINT `id_produit_produit_taille` FOREIGN KEY (`id_produit`) REFERENCES `Produit` (`id_produit`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `id_taille_produit_taille` FOREIGN KEY (`id_taille`) REFERENCES `Taille` (`id_taille`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
