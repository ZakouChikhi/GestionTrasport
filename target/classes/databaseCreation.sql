CREATE DATABASE IF NOT EXISTS gesttrans;

CREATE TABLE utilisateur (
  id smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  nom varchar(100) NOT NULL,
  prenom varchar(60) NOT NULL,
  adresse varchar(200) DEFAULT NULL,
  username varchar (20) DEFAULT NULL,
  email varbinary(100) DEFAULT NULL,
  password varchar (20) DEFAULT NULL,
  date_naissance date DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY ind_uni_email (email)
) ENGINE=InnoDB AUTO_INCREMENT=17;