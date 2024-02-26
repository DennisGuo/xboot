create table t_group
(
    id          varchar not null
        constraint t_group_pk
            primary key,
    name        varchar,
    code        varchar,
    remark      varchar,
    sort      integer default 0,
    create_time timestamp default CURRENT_TIMESTAMP,
    pid   varchar
        constraint t_group_fk
            references t_group
            on delete cascade
);

comment on table t_group is '机构部门';
comment on column t_group.id is 'id';
comment on column t_group.name is '名称';
comment on column t_group.code is '编码';
comment on column t_group.sort is '排序';
comment on column t_group.remark is '备注';
comment on column t_group.create_time is '创建时间';
comment on column t_group.pid is '上级ID';


create table t_role
(
    id          varchar not null
        constraint t_role_pk
            primary key,
    create_time timestamp default CURRENT_TIMESTAMP,
    name        varchar,
    code        varchar
        constraint t_role_un
            unique,
    home_page   varchar
);

comment on table t_role is '角色';

comment on column t_role.id is 'id';

comment on column t_role.create_time is '创建时间';

comment on column t_role.name is '角色名称';

comment on column t_role.code is '角色编码';

comment on column t_role.home_page is '首页路径';

create table t_user
(
    id          varchar not null
        constraint t_user_pk
            primary key,
    create_time timestamp default CURRENT_TIMESTAMP,
    name        varchar,
    phone       varchar,
    username    varchar not null
        constraint t_user_un
            unique,
    remark      varchar,
    sex         integer   default 0,
    password    varchar,
    role_id     varchar
        constraint t_user_fk
            references t_role
            on delete set null
);

comment on table t_user is '用户表';

comment on column t_user.id is 'id';

comment on column t_user.create_time is '创建时间';

comment on column t_user.name is '姓名';

comment on column t_user.phone is '手机号';

comment on column t_user.username is '用户名';

comment on column t_user.remark is '备注';

comment on column t_user.sex is '性别: 0=男,1=女';

comment on column t_user.password is '密码';

comment on column t_user.role_id is '角色ID';


create table t_group_user
(
    user_id     varchar not null
        constraint t_group_user_fk_user
            references t_user
            on delete cascade,
    group_id    varchar not null
        constraint t_group_user_fk_group
            references t_group
            on delete cascade,
    create_time timestamp default CURRENT_TIMESTAMP,
    constraint t_group_user_un
        unique (user_id, group_id)
);

comment on table t_group_user is '机构部门用户关系表';

comment on column t_group_user.user_id is '用户ID';

comment on column t_group_user.group_id is '部门ID';

comment on column t_group_user.create_time is '创建时间';


create table t_dict
(
    id          varchar not null
        constraint t_dict_pk
            primary key,
    create_time timestamp default CURRENT_TIMESTAMP,
    code        varchar not null,
    name        varchar
);

comment on table t_dict is '字典表';

comment on column t_dict.id is 'id';

comment on column t_dict.create_time is '创建时间';

comment on column t_dict.code is '编码';

comment on column t_dict.name is '字典名称';

create table t_dict_value
(
    id          varchar not null
        constraint t_dict_value_pk
            primary key,
    create_time timestamp default CURRENT_TIMESTAMP,
    dict_id     varchar not null
        constraint t_dict_value_fk
            references t_dict
            on delete cascade,
    dict_code   varchar,
    code        varchar,
    sort        int       default 0,
    value       text
);

comment on table t_dict_value is '字典项表';

comment on column t_dict_value.id is 'id';

comment on column t_dict_value.create_time is '创建时间';

comment on column t_dict_value.dict_id is '关联字典ID';

comment on column t_dict_value.dict_code is '关联字典编码';

comment on column t_dict_value.code is '字典项值编码';

comment on column t_dict_value.value is '字典项值';
comment on column t_dict_value.sort is '排序值';


create table t_attachment
(
    id          varchar not null
        constraint t_attachment_pk
            primary key,
    create_time timestamp default CURRENT_TIMESTAMP,
    path        varchar,
    mime        varchar,
    file_size   numeric,
    name        varchar
);

comment on table t_attachment is '附件';

comment on column t_attachment.id is 'id';

comment on column t_attachment.create_time is '创建时间';

comment on column t_attachment.path is '相对路径';

comment on column t_attachment.mime is 'MIME类型';

comment on column t_attachment.file_size is '文件大小';

comment on column t_attachment.name is '文件名称';

create table t_menu
(
    id          varchar not null
        constraint t_menu_pk
            primary key,
    create_time timestamp default CURRENT_TIMESTAMP,
    name        varchar,
    type        integer   default 0,
    path        varchar,
    code        varchar,
    target      varchar   default 'self',
    pid         varchar,
    component   varchar,
    icon        varchar,
    sort        integer   default 0,
    CONSTRAINT t_menu_fk FOREIGN KEY (pid) REFERENCES t_menu (id) ON DELETE CASCADE
);

comment on table t_menu is '菜单';

comment on column t_menu.id is 'id';

comment on column t_menu.create_time is '创建时间';

comment on column t_menu.name is '名称';

comment on column t_menu.type is '类型：0=菜单,1=页面,2=按钮';

comment on column t_menu.path is '路径';

comment on column t_menu.code is '编码';

comment on column t_menu.target is '打开页面方式: self,blank';

comment on column t_menu.component is '前端组件路径';
comment on column t_menu.pid is '上级ID';
comment on column t_menu.icon is '图标';
comment on column t_menu.sort is '排序';

create table t_role_menu
(
    id          varchar not null
        constraint t_role_menu_pk
            primary key,
    create_time timestamp default CURRENT_TIMESTAMP,
    role_id     varchar
        constraint t_role_menu_fk_role
            references t_role
            on delete cascade,
    menu_id     varchar
        constraint t_role_menu_fk
            references t_menu
            on delete cascade,
    constraint t_role_menu_un
        unique (role_id, menu_id)
);

comment on table t_role_menu is '角色菜单';

comment on column t_role_menu.id is 'id';

comment on column t_role_menu.create_time is '创建时间';

comment on column t_role_menu.role_id is '角色ID';

comment on column t_role_menu.menu_id is '菜单ID';

create table t_setting
(
    id          varchar not null
        constraint t_setting_pk
            primary key,
    create_time timestamp default CURRENT_TIMESTAMP,
    code        varchar not null
        constraint t_setting_un
            unique,
    name        varchar,
    content     varchar,
    is_sys      boolean   default false
);

comment on table t_setting is '配置内容';

comment on column t_setting.id is 'id';

comment on column t_setting.create_time is '创建时间';

comment on column t_setting.code is '编码';

comment on column t_setting.name is '名称';

comment on column t_setting.content is '配置内容';

comment on column t_setting.is_sys is '系统必须';