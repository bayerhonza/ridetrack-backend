
    create table client (
       id_client bigint not null auto_increment,
        client_name varchar(255),
        created_at datetime,
        full_name varchar(255),
        updated_at datetime,
        primary key (id_client)
    ) engine=InnoDB;

    create table device (
       id_device bigint not null auto_increment,
        created_at datetime,
        device_uid varchar(255) not null,
        updated_at datetime,
        id_device_group bigint,
        id_sensor bigint,
        primary key (id_device)
    ) engine=InnoDB;

    create table device_data (
       id_device_data bigint not null auto_increment,
        created_at datetime,
        updated_at datetime,
        x_coord bigint,
        y_coord bigint,
        z_coord bigint,
        id_device bigint,
        primary key (id_device_data)
    ) engine=InnoDB;

    create table device_group (
       id_dev_group bigint not null auto_increment,
        created_at datetime,
        name varchar(255),
        updated_at datetime,
        id_space bigint not null,
        primary key (id_dev_group)
    ) engine=InnoDB;

    create table operation (
       id_privilege bigint not null auto_increment,
        operation_name varchar(255),
        primary key (id_privilege)
    ) engine=InnoDB;

    create table role (
       id_role bigint not null auto_increment,
        role_name varchar(255),
        primary key (id_role)
    ) engine=InnoDB;

    create table role_privilege (
       id_role bigint not null,
        id_privilege bigint not null,
        primary key (id_role, id_privilege)
    ) engine=InnoDB;

    create table sensors (
       id_sensor bigint not null auto_increment,
        created_at datetime,
        device_uid varchar(255),
        updated_at datetime,
        primary key (id_sensor)
    ) engine=InnoDB;

    create table space (
       id_space bigint not null auto_increment,
        created_at datetime,
        name varchar(255),
        updated_at datetime,
        client_id bigint not null,
        primary key (id_space)
    ) engine=InnoDB;

    create table user (
       id_user bigint not null auto_increment,
        created_at datetime,
        email varchar(255),
        enabled bit,
        name varchar(100),
        password varchar(255),
        surname varchar(100),
        updated_at datetime,
        username varchar(100),
        id_space bigint,
        id_user_configuration bigint,
        primary key (id_user)
    ) engine=InnoDB;

    create table user_configuration (
       id_configuration bigint not null auto_increment,
        created_at datetime,
        updated_at datetime,
        primary key (id_configuration)
    ) engine=InnoDB;

    create table user_role (
       id_user bigint not null,
        id_role bigint not null,
        primary key (id_user, id_role)
    ) engine=InnoDB;

    create table user_dev_group_config (
       id_device_group bigint not null,
        id_user_config bigint not null,
        created_at datetime,
        updated_at datetime,
        primary key (id_device_group, id_user_config)
    ) engine=InnoDB;

    alter table client 
       add constraint uq_client_client_name unique (client_name);

    alter table device 
       add constraint uq_device_device_uid unique (device_uid);

    alter table device_group 
       add constraint uq_device_group_space_group_name unique (name, id_space);

    alter table operation 
       add constraint UQ_PRIVILEGE_OPERATION_NAME unique (operation_name);

    alter table role 
       add constraint UQ_ROLE_ROLE_NAME unique (role_name);

    alter table space 
       add constraint uq_space_client_space_name unique (name, client_id);

    alter table user 
       add constraint uq_user_username unique (username);

    alter table device 
       add constraint fk_device_id_device_group 
       foreign key (id_device_group) 
       references device_group (id_dev_group);

    alter table device 
       add constraint fk_device_sensor 
       foreign key (id_sensor) 
       references sensors (id_sensor);

    alter table device_data 
       add constraint fk_device_data_device_id 
       foreign key (id_device) 
       references device (id_device);

    alter table device_group 
       add constraint fk_devgroup_id_space 
       foreign key (id_space) 
       references space (id_space);

    alter table role_privilege 
       add constraint FK_ROLE_PRIVILEGE_ID_PRIVILEGE 
       foreign key (id_privilege) 
       references operation (id_privilege);

    alter table role_privilege 
       add constraint FK_ROLE_PRIVILEGE_ID_ROLE 
       foreign key (id_role)
       references role (id_role);

    alter table space 
       add constraint fk_space_client_id 
       foreign key (client_id) 
       references client (id_client);

    alter table user 
       add constraint fk_user_id_space 
       foreign key (id_space) 
       references space (id_space);

    alter table user 
       add constraint fk_user_user_config 
       foreign key (id_user_configuration) 
       references user_configuration (id_configuration);

    alter table user_role 
       add constraint FK_USER_ROLE_ID_ROLE 
       foreign key (id_role) 
       references role (id_role);

    alter table user_role 
       add constraint FK_USER_ROLE_ID_USER 
       foreign key (id_user) 
       references user (id_user);

    alter table user_dev_group_config 
       add constraint FKgd4u11kxebtemom7c399ml9id 
       foreign key (id_device_group) 
       references device_group (id_dev_group);

    alter table user_dev_group_config 
       add constraint FK55vgkrw8r32swqw4m5j0wexnx 
       foreign key (id_user_config) 
       references user_configuration (id_configuration);
