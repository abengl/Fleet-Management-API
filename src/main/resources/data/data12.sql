-- Insert ROLES
INSERT INTO api.roles (role_name)
VALUES ('ADMIN');
INSERT INTO api.roles (role_name)
VALUES ('DEVELOPER');
INSERT INTO api.roles (role_name)
VALUES ('USER');
INSERT INTO api.roles (role_name)
VALUES ('GUEST');

-- Insert USERS
INSERT INTO api.users (name, email, password, is_enabled, account_non_expired, account_non_locked,
                       credentials_non_expired, role_id)
SELECT 'admin',
       'admin@test.com',
       '$2a$10$/R06Zz2awp87D/iFBD.cH.QoMZUAJEd59fsZOgK668tujGem1IEuy',
       true,
       true,
       true,
       true,
       r.id
FROM api.roles r
WHERE r.role_name = 'ADMIN';

INSERT INTO api.users (name, email, password, is_enabled, account_non_expired, account_non_locked,
                       credentials_non_expired, role_id)
SELECT 'developer',
       'developer@mail.com',
       '$2a$10$cMY29RPYoIHMJSuwRfoD3eQxU1J5Rww4VnNOUOAEPqCBshkNfrEf6',
       true,
       true,
       true,
       true,
       r.id
FROM api.roles r
WHERE r.role_name = 'DEVELOPER';

INSERT INTO api.users (name, email, password, is_enabled, account_non_expired, account_non_locked,
                       credentials_non_expired, role_id)
SELECT 'user',
       'user@mail.com',
       '$2a$10$cMY29RPYoIHMJSuwRfoD3eQxU1J5Rww4VnNOUOAEPqCBshkNfrEf6',
       true,
       true,
       true,
       true,
       r.id
FROM api.roles r
WHERE r.role_name = 'USER';

INSERT INTO api.users (name, email, password, is_enabled, account_non_expired, account_non_locked,
                       credentials_non_expired, role_id)
SELECT 'guest',
       'guest@mail.com',
       '$2a$10$cMY29RPYoIHMJSuwRfoD3eQxU1J5Rww4VnNOUOAEPqCBshkNfrEf6',
       true,
       true,
       true,
       true,
       r.id
FROM api.roles r
WHERE r.role_name = 'GUEST';

INSERT INTO api.users (name, email, password, is_enabled, account_non_expired, account_non_locked,
                       credentials_non_expired, role_id)
SELECT 'sofia',
       'sofia@mail.com',
       '$2a$10$cMY29RPYoIHMJSuwRfoD3eQxU1J5Rww4VnNOUOAEPqCBshkNfrEf6',
       true,
       true,
       true,
       true,
       r.id
FROM api.roles r
WHERE r.role_name = 'ADMIN';

INSERT INTO api.users (name, email, password, is_enabled, account_non_expired, account_non_locked,
                       credentials_non_expired, role_id)
SELECT 'alice',
       'alice@mail.com',
       '$2a$10$cMY29RPYoIHMJSuwRfoD3eQxU1J5Rww4VnNOUOAEPqCBshkNfrEf6',
       true,
       true,
       true,
       true,
       r.id
FROM api.roles r
WHERE r.role_name = 'USER';

INSERT INTO api.users (name, email, password, is_enabled, account_non_expired, account_non_locked,
                       credentials_non_expired, role_id)
SELECT 'bob',
       'bob@mail.com',
       '$2a$10$cMY29RPYoIHMJSuwRfoD3eQxU1J5Rww4VnNOUOAEPqCBshkNfrEf6',
       true,
       true,
       true,
       true,
       r.id
FROM api.roles r
WHERE r.role_name = 'USER';

INSERT INTO api.users (name, email, password, is_enabled, account_non_expired, account_non_locked,
                       credentials_non_expired, role_id)
SELECT 'andrea',
       'andrea@mail.com',
       '$2a$10$cMY29RPYoIHMJSuwRfoD3eQxU1J5Rww4VnNOUOAEPqCBshkNfrEf6',
       true,
       true,
       true,
       true,
       r.id
FROM api.roles r
WHERE r.role_name = 'GUEST';

INSERT INTO api.users (name, email, password, is_enabled, account_non_expired, account_non_locked,
                       credentials_non_expired, role_id)
SELECT 'jane',
       'jane@mail.com',
       '$2a$10$cMY29RPYoIHMJSuwRfoD3eQxU1J5Rww4VnNOUOAEPqCBshkNfrEf6',
       true,
       true,
       true,
       true,
       r.id
FROM api.roles r
WHERE r.role_name = 'GUEST';

INSERT INTO api.users (name, email, password, is_enabled, account_non_expired, account_non_locked,
                       credentials_non_expired, role_id)
SELECT 'mateo',
       'mateo@mail.com',
       '$2a$10$cMY29RPYoIHMJSuwRfoD3eQxU1J5Rww4VnNOUOAEPqCBshkNfrEf6',
       true,
       true,
       true,
       true,
       r.id
FROM api.roles r
WHERE r.role_name = 'DEVELOPER';