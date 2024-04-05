
CREATE TABLE bug (
                     id SERIAL PRIMARY KEY,
                     title TEXT NOT NULL,
                     description TEXT,
                     severity VARCHAR(50) NOT NULL,  -- e.g., "Low", "Medium", "High"
                     status VARCHAR(50) NOT NULL,   -- e.g., "Open", "In Progress", "Fixed"
                     assignee_id INTEGER REFERENCES user(id),   -- Foreign key to user table (optional)
                     created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE user (  -- For user accounts (if using Spring Security)
                      id SERIAL PRIMARY KEY,
                      username VARCHAR(255) NOT NULL UNIQUE,
                      password VARCHAR(255) NOT NULL,
                      first_name TEXT NOT NULL,
                      last_name TEXT NOT NULL,
                      email VARCHAR(255) NOT NULL UNIQUE
);