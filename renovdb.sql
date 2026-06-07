-- renovdb.sql
-- Database dump for renovdb
-- Server version: 5.7
--
-- Table names and column names match the English JPA entities.
-- NOTE: passwords shown here are plain text for reference only;
--       the application stores BCrypt-encoded hashes (seeded via DataLoader).

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

-- Database: `renovdb`

-- --------------------------------------------------------
-- Table structure: address
-- --------------------------------------------------------

CREATE TABLE `address` (
  `address_id` bigint(20) NOT NULL,
  `unit`        text,
  `postal_code` text,
  `street_number` text NOT NULL,
  `country`     text NOT NULL,
  `province`    text,
  `street`      text NOT NULL,
  `city`        text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Data for table: address

INSERT INTO `address` (`address_id`, `unit`, `postal_code`, `street_number`, `country`, `province`, `street`, `city`) VALUES
(1, '06', 'H1K 2X8', '7595',  'Canada', 'QC', 'Des Ormeaux', 'Anjou'),
(2, '04', 'H1H 2X2', '12000', 'Canada', 'QC', 'Morfin',      'Laval');

-- --------------------------------------------------------
-- Table structure: address_sequence
-- --------------------------------------------------------

CREATE TABLE `address_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `address_sequence` (`next_val`) VALUES (3);

-- --------------------------------------------------------
-- Table structure: user_address
-- --------------------------------------------------------

CREATE TABLE `user_address` (
  `address_id`   bigint(20) NOT NULL,
  `user_id`      bigint(20) NOT NULL,
  `address_type` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------
-- Table structure: projectBid_sequence
-- --------------------------------------------------------

CREATE TABLE `projectBid_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `projectBid_sequence` (`next_val`) VALUES (9);

-- --------------------------------------------------------
-- Table structure: project_bid
-- --------------------------------------------------------

CREATE TABLE `project_bid` (
  `project_bid_id` bigint(20) NOT NULL,
  `work_start_date` date NOT NULL,
  `request_date`    date NOT NULL,
  `work_end_date`   date NOT NULL,
  `deadline`        date NOT NULL,
  `status`          text NOT NULL,
  `type`            text NOT NULL,
  `user_id`         bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Data for table: project_bid

INSERT INTO `project_bid` (`project_bid_id`, `work_start_date`, `request_date`, `work_end_date`, `deadline`, `status`, `type`, `user_id`) VALUES
(4, '2021-10-18', '2021-10-04', '2021-10-22', '2021-10-08', 'Open', 'Painting',    11),
(5, '2021-10-25', '2021-10-11', '2021-10-29', '2021-10-15', 'Open', 'Framing',     11),
(6, '2021-10-25', '2021-10-04', '2021-10-29', '2021-10-22', 'Open', 'Plastering', NULL),
(7, '2021-10-20', '2021-10-06', '2021-10-27', '2021-10-13', 'Open', 'Painting',   11);

-- --------------------------------------------------------
-- Table structure: client
-- --------------------------------------------------------

CREATE TABLE `client` (
  `email`      text NOT NULL,
  `last_name`  text NOT NULL,
  `first_name` text NOT NULL,
  `phone`      text,
  `user_id`    bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Data for table: client

INSERT INTO `client` (`email`, `last_name`, `first_name`, `phone`, `user_id`) VALUES
('abdel.alain@bar.com', 'Alain',   'Abdel',    '514 234 1231', 11),
('benjamin@bar.com',    'Birdman', 'Benjamin', '514 234 1232', 12),
('carl@bar.com',        'Claire',  'Carl',     '5142341003',   13),
('david@bar.com',       'Denis',   'David',    '514 676 004',  14);

-- --------------------------------------------------------
-- Table structure: contractor
-- --------------------------------------------------------

CREATE TABLE `contractor` (
  `specialty`          text NOT NULL,
  `years_of_experience` int(11) NOT NULL,
  `email`              text NOT NULL,
  `rating`             int(11) DEFAULT NULL,
  `phone`              text NOT NULL,
  `user_id`            bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Data for table: contractor

INSERT INTO `contractor` (`specialty`, `years_of_experience`, `email`, `rating`, `phone`, `user_id`) VALUES
('Painting', 5,  'henry.smart@bar.com', 0, '514 432 3001', 4),
('Framing',  15, 'pacific@tmd.com',     0, '5140073002',   5),
('Painting', 8,  'stm@boobar.com',      0, '438 999 0209', 15),
('Framing',  10, 'kevin@boobar.com',    0, '4380074356',   16);

-- --------------------------------------------------------
-- Table structure: company
-- --------------------------------------------------------

CREATE TABLE `company` (
  `contact_person` text NOT NULL,
  `name`           text NOT NULL,
  `user_id`        bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------
-- Table structure: individual
-- (merged from the old individu / individus tables)
-- --------------------------------------------------------

CREATE TABLE `individual` (
  `certification` text,
  `last_name`     text NOT NULL,
  `first_name`    text NOT NULL,
  `user_id`       bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Data for table: individual

INSERT INTO `individual` (`certification`, `last_name`, `first_name`, `user_id`) VALUES
('P123450001', 'Smart',       'Henry',    4),
('P123450007', 'TMD Co Ltd.', 'Pacific',  5),
('P12340009',  'ATM Group',   'Sycamore', 15),
('P12340011',  'Kelly',       'Kevin',    16);

-- --------------------------------------------------------
-- Table structure: labor
-- --------------------------------------------------------

CREATE TABLE `labor` (
  `labor_id`    bigint(20) NOT NULL,
  `description` text NOT NULL,
  `grade`       int(11) DEFAULT NULL,
  `hourly_rate` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Data for table: labor

INSERT INTO `labor` (`labor_id`, `description`, `grade`, `hourly_rate`) VALUES
(1, 'Painter', 3, 35),
(2, 'Plumber', 2, 40);

-- --------------------------------------------------------
-- Table structure: labor_sequence
-- --------------------------------------------------------

CREATE TABLE `labor_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `labor_sequence` (`next_val`) VALUES (3);

-- --------------------------------------------------------
-- Table structure: material
-- --------------------------------------------------------

CREATE TABLE `material` (
  `material_id` bigint(20) NOT NULL,
  `description` text,
  `unit_price`  double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Data for table: material

INSERT INTO `material` (`material_id`, `description`, `unit_price`) VALUES
(1, 'Alpha Paint', 45),
(2, 'Beta Paint',  33);

-- --------------------------------------------------------
-- Table structure: material_sequence
-- --------------------------------------------------------

CREATE TABLE `material_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `material_sequence` (`next_val`) VALUES (3);

-- --------------------------------------------------------
-- Table structure: bid_labor
-- --------------------------------------------------------

CREATE TABLE `bid_labor` (
  `labor_id`        bigint(20) NOT NULL,
  `service_offer_id` bigint(20) NOT NULL,
  `unit_price`      double DEFAULT NULL,
  `quantity`        int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------
-- Table structure: bid_material
-- --------------------------------------------------------

CREATE TABLE `bid_material` (
  `material_id`     bigint(20) NOT NULL,
  `service_offer_id` bigint(20) NOT NULL,
  `unit_price`      double DEFAULT NULL,
  `quantity`        int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------
-- Table structure: service_offer
-- --------------------------------------------------------

CREATE TABLE `service_offer` (
  `service_offer_id` bigint(20) NOT NULL,
  `offer_date`       date DEFAULT NULL,
  `valid_until`      date DEFAULT NULL,
  `amount`           double NOT NULL,
  `status`           text NOT NULL,
  `project_bid_id`   bigint(20) DEFAULT NULL,
  `user_id`          bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Data for table: service_offer

INSERT INTO `service_offer` (`service_offer_id`, `offer_date`, `valid_until`, `amount`, `status`, `project_bid_id`, `user_id`) VALUES
(7, '2021-10-04', NULL, 15000, 'Open', NULL, NULL),
(8, '2021-10-04', NULL, 15000, 'Open', 4,    4);

-- --------------------------------------------------------
-- Table structure: service_offer_sequence
-- --------------------------------------------------------

CREATE TABLE `service_offer_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `service_offer_sequence` (`next_val`) VALUES (9);

-- --------------------------------------------------------
-- Table structure: app_user
-- --------------------------------------------------------

CREATE TABLE `app_user` (
  `user_id`           bigint(20) NOT NULL,
  `registration_date` date NOT NULL,
  `password`          text NOT NULL,
  `type`              text NOT NULL,
  `username`          text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Data for table: app_user
-- Passwords are plain text here for reference; the app stores BCrypt hashes.
-- Actual credentials seeded by DataLoader on first startup:
--   client001 / client001  (type: Client)
--   client002 / client002  (type: Client)
--   contracteur001 / contracteur001  (type: Individual)
--   contracteur002 / contracteur002  (type: Company)

INSERT INTO `app_user` (`user_id`, `registration_date`, `password`, `type`, `username`) VALUES
(4,  '2021-10-12', 'contracteur001', 'Individual', 'contracteur001'),
(5,  '2021-10-20', 'contracteur002', 'Company',    'contracteur002'),
(11, '2021-10-10', 'client001',      'Client',     'client001'),
(12, '2021-10-06', 'client002',      'Client',     'client002'),
(13, '2021-10-22', 'client3',        'Client',     'client3'),
(14, '2021-10-22', 'client4',        'Client',     'client4'),
(15, '2021-10-22', 'contracteur003', 'Individual', 'contracteur003'),
(16, '2021-10-22', 'contracteur004', 'Individual', 'contracteur004');

-- --------------------------------------------------------
-- Table structure: user_sequence
-- --------------------------------------------------------

CREATE TABLE `user_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `user_sequence` (`next_val`) VALUES (21);

-- --------------------------------------------------------
-- Primary keys
-- --------------------------------------------------------

ALTER TABLE `address`       ADD PRIMARY KEY (`address_id`);
ALTER TABLE `user_address`  ADD PRIMARY KEY (`address_id`, `user_id`), ADD KEY `user_address_user_fk` (`user_id`);
ALTER TABLE `project_bid`   ADD PRIMARY KEY (`project_bid_id`), ADD KEY `project_bid_client_fk` (`user_id`);
ALTER TABLE `client`        ADD PRIMARY KEY (`user_id`);
ALTER TABLE `contractor`    ADD PRIMARY KEY (`user_id`);
ALTER TABLE `company`       ADD PRIMARY KEY (`user_id`);
ALTER TABLE `individual`    ADD PRIMARY KEY (`user_id`);
ALTER TABLE `labor`         ADD PRIMARY KEY (`labor_id`);
ALTER TABLE `material`      ADD PRIMARY KEY (`material_id`);
ALTER TABLE `bid_labor`     ADD PRIMARY KEY (`labor_id`, `service_offer_id`), ADD KEY `bid_labor_service_offer_fk` (`service_offer_id`);
ALTER TABLE `bid_material`  ADD PRIMARY KEY (`material_id`, `service_offer_id`), ADD KEY `bid_material_service_offer_fk` (`service_offer_id`);
ALTER TABLE `service_offer` ADD PRIMARY KEY (`service_offer_id`), ADD KEY `service_offer_project_bid_fk` (`project_bid_id`), ADD KEY `service_offer_contractor_fk` (`user_id`);
ALTER TABLE `app_user`      ADD PRIMARY KEY (`user_id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
