CREATE TABLE IF NOT EXISTS specialities (
    speciality_id BINARY(16) PRIMARY KEY,
    speciality_name VARCHAR(255) UNIQUE
    );

CREATE TABLE IF NOT EXISTS modules (
    module_id BINARY(16) PRIMARY KEY,
    module_name VARCHAR(255),
    speciality_id BINARY(16),
    FOREIGN KEY (speciality_id) REFERENCES specialities(speciality_id),
    UNIQUE (module_name, speciality_id)
    );

CREATE TABLE IF NOT EXISTS users (
    user_id VARCHAR(255) PRIMARY KEY,
    last_name VARCHAR(255),
    first_name VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    role VARCHAR(255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS codes (
    code_id BINARY(16),
    qr_code VARCHAR(255),
    readable_code VARCHAR(255),
    beacon_id VARCHAR(255) UNIQUE,
    user_id VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
    );
CREATE TABLE IF NOT EXISTS sessions (
    id BINARY(16) PRIMARY KEY,
    session_name VARCHAR(255),
    module_id BINARY(16),
    start_time BIGINT,
    end_time BIGINT,
    verification_code VARCHAR(255),
    active BOOLEAN,
    FOREIGN KEY (module_id) REFERENCES modules(module_id)
    );
CREATE TABLE IF NOT EXISTS signatures (
    signature_id BINARY(16) PRIMARY KEY,
    student_id VARCHAR(255),
    session_id BINARY(16),
    FOREIGN KEY (session_id) REFERENCES sessions(id),
    FOREIGN KEY (student_id) REFERENCES users(user_id),
    UNIQUE (session_id, student_id)
    );

CREATE TABLE IF NOT EXISTS assigned_modules (
    user_id VARCHAR(255),
    module_id BINARY(16),
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (module_id) REFERENCES modules (module_id)
    );

CREATE TABLE IF NOT EXISTS assigned_specialities (
    user_id VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    speciality_id BINARY(16),
    FOREIGN KEY (speciality_id) REFERENCES specialities(speciality_id)
    );