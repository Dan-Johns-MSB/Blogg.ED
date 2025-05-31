CREATE DATABASE IF NOT EXISTS blogged CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE blogged;

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    secret_question VARCHAR(255),
    secret_answer VARCHAR(255)
);

CREATE TABLE post (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    created_at DATETIME,
    author_id BIGINT,
    FOREIGN KEY (author_id) REFERENCES users(id) ON DELETE SET NULL
);

CREATE TABLE comment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    content TEXT NOT NULL,
    created_at DATETIME,
    author_id BIGINT,
    post_id BIGINT,
    FOREIGN KEY (author_id) REFERENCES users(id) ON DELETE SET NULL,
    FOREIGN KEY (post_id) REFERENCES post(id) ON DELETE CASCADE
);

CREATE TABLE profile (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    bio TEXT,
    avatar_url VARCHAR(512),
    user_id BIGINT UNIQUE,
    full_name VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
