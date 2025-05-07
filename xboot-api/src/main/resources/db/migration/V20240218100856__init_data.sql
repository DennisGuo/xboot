INSERT INTO t_role
    (id, create_time, "name", code, home_page)
VALUES ('admin', '2024-02-18 10:09:44.437', '超级管理员', 'admin', '/dashboard');

INSERT INTO t_menu
(id, create_time, "name", "type", "path", code, target, component, pid, sort, icon)
VALUES ('home', '2024-02-18 10:12:03.922', '首页', 0, '/', 'HOME', NULL, NULL, null, 1, 'HomeOutlined'),
       ('dashboard', '2024-02-18 10:12:03.922', '概览', 2, '/dashboard', 'M01', NULL, 'pages/dashboard', 'home', 1,
        'HomeOutlined'),
       ('setting', '2024-02-18 10:12:03.922', '系统设置', 0, '/setting', 'M02', NULL, NULL, NULL, 25,
        'SettingOutlined'),
       ('user', '2024-02-18 10:12:03.922', '用户管理', 2, '/setting/user', 'M0201', NULL, 'pages/setting/user',
        'setting', 1, 'UserOutlined'),
       ('menu', '2024-02-18 10:12:03.922', '菜单管理', 2, '/setting/menu', 'M0202', NULL, 'pages/setting/menu',
        'setting', 2, 'MenuOutlined'),
       ('group', '2024-02-18 10:12:03.922', '机构部门', 2, '/setting/group', 'M0206', 'self', 'pages/setting/group',
        'setting', 2, 'ApartmentOutlined'),
       ('role', '2024-02-18 10:12:03.922', '角色管理', 2, '/setting/role', 'M0203', NULL, 'pages/setting/role',
        'setting', 3, 'IdcardOutlined'),
       ('dict', '2024-02-18 10:12:03.922', '字典管理', 2, '/setting/dict', 'M0204', NULL, 'pages/setting/dict',
        'setting', 4, 'BuildOutlined'),
       ('config', '2024-02-18 10:12:03.922', '参数配置', 2, '/setting/config', 'M0205', NULL, 'pages/setting/config',
        'setting', 5, 'ExperimentOutlined')
;

INSERT INTO t_menu (id, create_time, "name", "type", "path", code, target, pid, component, icon, sort)
VALUES ('1798657507720294401', '2024-06-06 18:05:41.939', '删除', 3, NULL, 'config-delete', 'self', 'config', NULL,
        NULL, 3),
       ('1798657441886498817', '2024-06-06 18:05:26.242', '编辑', 3, NULL, 'config-edit', 'self', 'config', NULL, NULL,
        2),
       ('1798657389898100737', '2024-06-06 18:05:13.847', '添加', 3, NULL, 'config-add', 'self', 'config', NULL, NULL,
        1),
       ('1798657052529258497', '2024-06-06 18:03:53.413', '删除', 3, NULL, 'dict-delete', 'self', 'dict', NULL, NULL,
        3),
       ('1798656929187360770', '2024-06-06 18:03:24.007', '编辑', 3, NULL, 'dict-edit', 'self', 'dict', NULL, NULL, 2),
       ('1798656840465248257', '2024-06-06 18:03:02.853', '添加', 3, NULL, 'dict-add', 'self', 'dict', NULL, NULL, 1),
       ('1798655549068406786', '2024-06-06 17:57:54.959', '修改权限', 3, NULL, 'role-auth', 'self', 'role', NULL, NULL,
        8),
       ('1798655429979533314', '2024-06-06 17:57:26.567', '移出用户', 3, NULL, 'role-user-remove', 'self', 'role', NULL,
        NULL, 7),
       ('1798655304636952577', '2024-06-06 17:56:56.682', '添加用户', 3, NULL, 'role-user-add', 'self', 'role', NULL,
        NULL, 4),
       ('1798655186768621569', '2024-06-06 17:56:28.580', '删除', 3, NULL, 'role-delete', 'self', 'role', NULL, NULL,
        3),
       ('1798655129864499201', '2024-06-06 17:56:15.013', '编辑', 3, NULL, 'role-edit', 'self', 'role', NULL, NULL, 2),
       ('1798655059882536962', '2024-06-06 17:55:58.329', '添加', 3, NULL, 'role-add', 'self', 'role', NULL, NULL, 1),
       ('1798654936817463298', '2024-06-06 17:55:28.984', '删除', 3, NULL, 'menu-delete', 'self', 'menu', NULL, NULL,
        3),
       ('1798654859378028545', '2024-06-06 17:55:10.522', '编辑', 3, NULL, 'menu-edit', 'self', 'menu', NULL, NULL, 2),
       ('1798654789609975809', '2024-06-06 17:54:53.883', '新增', 3, NULL, 'menu-add', 'self', 'menu', NULL, NULL, 1),
       ('1798654187119177730', '2024-06-06 17:52:30.245', '删除', 3, NULL, 'user-delete', 'self', 'user', NULL, NULL,
        3),
       ('1798654112905162753', '2024-06-06 17:52:12.552', '编辑', 3, NULL, 'user-edit', 'self', 'user', NULL, NULL, 2),
       ('1798654038263328770', '2024-06-06 17:51:54.756', '添加', 3, NULL, 'user-add', 'self', 'user', NULL, NULL, 1),
       ('1798650004307570689', '2024-06-06 17:35:52.986', '成员移除', 3, NULL, 'group-user-remove', 'self', 'group',
        NULL, NULL, 6),
       ('1798649879820627970', '2024-06-06 17:35:23.305', '成员导入导出', 3, NULL, 'group-user-import', 'self', 'group',
        NULL, NULL, 5),
       ('1798649716469264386', '2024-06-06 17:34:44.358', '添加成员', 3, NULL, 'group-user-add', 'self', 'group', NULL,
        NULL, 4),
       ('1798649600895217666', '2024-06-06 17:34:16.803', '删除', 3, NULL, 'group-delete', 'self', 'group', NULL, NULL,
        3),
       ('1798649524189786113', '2024-06-06 17:33:58.516', '编辑', 3, NULL, 'group-edit', 'self', 'group', NULL, NULL,
        2),
       ('1798649437682266113', '2024-06-06 17:33:37.891', '添加', 3, NULL, 'group-add', 'self', 'group', NULL, NULL, 1);

INSERT INTO t_role_menu (id, role_id, menu_id)
select m.id, 'admin', m.id
from t_menu m;

INSERT INTO t_user
    (id, create_time, "name", phone, username, remark, sex, "password")
VALUES ('a86f31e692437acc1a94cea26866927e', '2024-01-20 10:25:41.858', '管理员', NULL, 'admin', NULL, 0,
        '$2a$10$9BnkNaB8US3GV3O0BtqVDeNs9pXwliSD.jJbd2qigq3pbqyNIUi76');
INSERT INTO t_user_role (id, create_time, create_by, user_id, role_id)
VALUES ('1', '2024-11-14 14:27:14.561', NULL, 'a86f31e692437acc1a94cea26866927e', 'admin');


INSERT INTO t_setting (id, create_time, code, "name", "content", is_sys)
VALUES ('1797823457621229569', '2024-06-04 10:58:09.611', 'COPYRIGHT', '版权名称', 'x-xi.cn', true),
       ('1797823799738023937', '2024-06-04 10:58:09.623', 'MAP_CENTER', '地图中心点', '105.59472,29.484125', true),
       ('1797823928901615618', '2024-06-04 10:58:09.625', 'MAP_ZOOM', '地图初始层级', '16', true),
       ('1797823928901615617', '2024-06-04 10:58:09.625', 'SYS_TITLE', '系统名称', 'xboot', true);
