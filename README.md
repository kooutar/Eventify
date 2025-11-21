# Eventify

Eventify est une application de gestion d'événements permettant aux utilisateurs de s’inscrire, aux organisateurs de créer et gérer leurs événements, et à l’administrateur de superviser l’ensemble du système.

Ce projet est sécurisé avec **Spring Security** et utilise l’**authentification Basic**.

---

## Table des matières

- [Fonctionnalités](#fonctionnalités)
- [Technologies utilisées](#technologies-utilisées)
- [Installation](#installation)
- [Exécution](#exécution)
- [Endpoints](#endpoints)
- [Modèle de données](#modèle-de-données)
- [Sécurité](#sécurité)
- [Gestion des erreurs](#gestion-des-erreurs)

---

## Fonctionnalités

- Inscription des utilisateurs (par défaut ROLE_USER)
- Gestion des événements (création, modification, suppression)
- Inscription à un événement pour les utilisateurs
- Gestion des rôles par l’administrateur
- Accès basé sur les rôles : USER, ORGANIZER, ADMIN

---

## Technologies utilisées

- Java 17+
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- mysql et PgSQL
- Maven
- BCryptPasswordEncoder pour le chiffrement des mots de passe

---

## Installation

1. Cloner le dépôt :
```bash
git clone https://github.com/votre-utilisateur/eventify.git
cd eventify
