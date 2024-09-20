-- Insert PERMISSIONS
INSERT INTO api.permissions (name)
VALUES ('CREATE');
INSERT INTO api.permissions (name)
VALUES ('READ');
INSERT INTO api.permissions (name)
VALUES ('UPDATE');
INSERT INTO api.permissions (name)
VALUES ('DELETE');
INSERT INTO api.permissions (name)
VALUES ('REFACTOR');

-- Insert ROLES
INSERT INTO api.roles (role_name)
VALUES ('ADMIN');
INSERT INTO api.roles (role_name)
VALUES ('USER');
INSERT INTO api.roles (role_name)
VALUES ('INVITED');
INSERT INTO api.roles (role_name)
VALUES ('DEVELOPER');

-- Insert ROLE_PERMISSIONS
INSERT INTO api.role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM api.roles r,
     api.permissions p
WHERE r.role_name = 'ADMIN'
  AND p.name IN ('CREATE', 'READ', 'UPDATE', 'DELETE');

INSERT INTO api.role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM api.roles r,
     api.permissions p
WHERE r.role_name = 'USER'
  AND p.name IN ('CREATE', 'READ');

INSERT INTO api.role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM api.roles r,
     api.permissions p
WHERE r.role_name = 'INVITED'
  AND p.name = 'READ';

INSERT INTO api.role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM api.roles r,
     api.permissions p
WHERE r.role_name = 'DEVELOPER'
  AND p.name IN ('CREATE', 'READ', 'UPDATE', 'DELETE', 'REFACTOR');

-- Insert USERS 9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08
INSERT INTO api.users (name, email, password, is_enabled, account_non_expired, account_non_locked,
                       credentials_non_expired)
VALUES ('admin', 'admin@test.com', '$2a$10$/R06Zz2awp87D/iFBD.cH.QoMZUAJEd59fsZOgK668tujGem1IEuy', true, true, true,
        true);

INSERT INTO api.users (name, email, password, is_enabled, account_non_expired, account_non_locked,
                       credentials_non_expired)
VALUES ('sofia', 'sofia@mail.com', '$2a$10$cMY29RPYoIHMJSuwRfoD3eQxU1J5Rww4VnNOUOAEPqCBshkNfrEf6', true, true, true,
        true);

INSERT INTO api.users (name, email, password, is_enabled, account_non_expired, account_non_locked,
                       credentials_non_expired)
VALUES ('user', 'user@mail.com', '$2a$10$cMY29RPYoIHMJSuwRfoD3eQxU1J5Rww4VnNOUOAEPqCBshkNfrEf6', true, true,
        true, true);

INSERT INTO api.users (name, email, password, is_enabled, account_non_expired, account_non_locked,
                       credentials_non_expired)
VALUES ('alice', 'alice@mail.com', '$2a$10$cMY29RPYoIHMJSuwRfoD3eQxU1J5Rww4VnNOUOAEPqCBshkNfrEf6', true, true, true,
        true);

INSERT INTO api.users (name, email, password, is_enabled, account_non_expired, account_non_locked,
                       credentials_non_expired)
VALUES ('bob', 'bob@mail.com', '$2a$10$cMY29RPYoIHMJSuwRfoD3eQxU1J5Rww4VnNOUOAEPqCBshkNfrEf6', true, true, true, true);

INSERT INTO api.users (name, email, password, is_enabled, account_non_expired, account_non_locked,
                       credentials_non_expired)
VALUES ('invited', 'invited@mail.com', '$2a$10$cMY29RPYoIHMJSuwRfoD3eQxU1J5Rww4VnNOUOAEPqCBshkNfrEf6', true, true,
        true, true);

INSERT INTO api.users (name, email, password, is_enabled, account_non_expired, account_non_locked,
                       credentials_non_expired)
VALUES ('andrea', 'andrea@mail.com', '$2a$10$cMY29RPYoIHMJSuwRfoD3eQxU1J5Rww4VnNOUOAEPqCBshkNfrEf6', true, true, true,
        true);

INSERT INTO api.users (name, email, password, is_enabled, account_non_expired, account_non_locked,
                       credentials_non_expired)
VALUES ('jane', 'jane@mail.com', '$2a$10$cMY29RPYoIHMJSuwRfoD3eQxU1J5Rww4VnNOUOAEPqCBshkNfrEf6', true, true, true,
        true);

INSERT INTO api.users (name, email, password, is_enabled, account_non_expired, account_non_locked,
                       credentials_non_expired)
VALUES ('developer', 'developer@mail.com', '$2a$10$cMY29RPYoIHMJSuwRfoD3eQxU1J5Rww4VnNOUOAEPqCBshkNfrEf6', true, true,
        true,
        true);

INSERT INTO api.users (name, email, password, is_enabled, account_non_expired, account_non_locked,
                       credentials_non_expired)
VALUES ('mateo', 'mateo@mail.com', '$2a$10$cMY29RPYoIHMJSuwRfoD3eQxU1J5Rww4VnNOUOAEPqCBshkNfrEf6', true, true, true,
        true);

-- Insert USER_ROLES
INSERT INTO api.user_roles (user_id, role_id)
SELECT u.id, r.id
FROM api.users u,
     api.roles r
WHERE u.name = 'admin'
  AND r.role_name = 'ADMIN';

INSERT INTO api.user_roles (user_id, role_id)
SELECT u.id, r.id
FROM api.users u,
     api.roles r
WHERE u.name = 'sofia'
  AND r.role_name = 'ADMIN';

INSERT INTO api.user_roles (user_id, role_id)
SELECT u.id, r.id
FROM api.users u,
     api.roles r
WHERE u.name = 'daniel'
  AND r.role_name = 'USER';

INSERT INTO api.user_roles (user_id, role_id)
SELECT u.id, r.id
FROM api.users u,
     api.roles r
WHERE u.name = 'alice'
  AND r.role_name = 'USER';

INSERT INTO api.user_roles (user_id, role_id)
SELECT u.id, r.id
FROM api.users u,
     api.roles r
WHERE u.name = 'bob'
  AND r.role_name = 'USER';

INSERT INTO api.user_roles (user_id, role_id)
SELECT u.id, r.id
FROM api.users u,
     api.roles r
WHERE u.name = 'invited'
  AND r.role_name = 'INVITED';

INSERT INTO api.user_roles (user_id, role_id)
SELECT u.id, r.id
FROM api.users u,
     api.roles r
WHERE u.name = 'andrea'
  AND r.role_name = 'INVITED';

INSERT INTO api.user_roles (user_id, role_id)
SELECT u.id, r.id
FROM api.users u,
     api.roles r
WHERE u.name = 'jane'
  AND r.role_name = 'INVITED';

INSERT INTO api.user_roles (user_id, role_id)
SELECT u.id, r.id
FROM api.users u,
     api.roles r
WHERE u.name = 'developer'
  AND r.role_name = 'DEVELOPER';

INSERT INTO api.user_roles (user_id, role_id)
SELECT u.id, r.id
FROM api.users u,
     api.roles r
WHERE u.name = 'mateo'
  AND r.role_name = 'DEVELOPER';
