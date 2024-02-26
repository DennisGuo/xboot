INSERT INTO t_role
    (id, create_time, "name", code, home_page)
VALUES ('admin', '2024-02-18 10:09:44.437', '超级管理员', 'admin', '/dashboard');

INSERT INTO t_menu
(id, create_time, "name", "type", "path", code, target, component, pid, sort)
VALUES ('dashboard', '2024-02-18 10:12:03.922', '概览', 1, '/dashboard', 'M01', NULL, 'pages/dashboard', null, 1),
       ('setting', '2024-02-18 10:12:03.922', '系统设置', 0, '/setting', 'M02', NULL, NULL, null, 25),
       ('user', '2024-02-18 10:12:03.922', '用户管理', 1, '/setting/user', 'M0201', NULL, 'pages/setting/user',
        'setting', 1),
       ('menu', '2024-02-18 10:12:03.922', '菜单管理', 1, '/setting/menu', 'M0202', NULL, 'pages/setting/menu',
        'setting', 2),
       ('group', '2024-02-18 10:12:03.922', '机构部门', 1, '/setting/group', 'M0206', 'self', 'pages/setting/group',
        'setting', 2),
       ('role', '2024-02-18 10:12:03.922', '角色管理', 1, '/setting/role', 'M0203', NULL, 'pages/setting/role',
        'setting', 3),
       ('dict', '2024-02-18 10:12:03.922', '字典管理', 1, '/setting/dict', 'M0204', NULL, 'pages/setting/dict',
        'setting', 4),
       ('config', '2024-02-18 10:12:03.922', '参数配置', 1, '/setting/config', 'M0205', NULL, 'pages/setting/config',
        'setting', 5)
;

INSERT INTO t_role_menu (id, create_time, role_id, menu_id)
VALUES ('1', '2024-02-18 10:12:50.919', 'admin', 'dashboard'),
       ('2', '2024-02-18 10:12:50.919', 'admin', 'setting'),
       ('3', '2024-02-18 10:12:50.919', 'admin', 'user'),
       ('4', '2024-02-18 10:12:50.919', 'admin', 'menu'),
       ('5', '2024-02-18 10:12:50.919', 'admin', 'role'),
       ('6', '2024-02-18 10:12:50.919', 'admin', 'dict'),
       ('7', '2024-02-18 10:12:50.919', 'admin', 'config');

INSERT INTO t_user
(id, create_time, "name", phone, username, remark, sex, "password", role_id)
VALUES ('a86f31e692437acc1a94cea26866927e', '2024-01-20 10:25:41.858', '管理员', NULL, 'admin', NULL, 0,
        '$2a$10$9BnkNaB8US3GV3O0BtqVDeNs9pXwliSD.jJbd2qigq3pbqyNIUi76', 'admin');