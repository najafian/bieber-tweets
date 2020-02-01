SET MODE ORACLE;

create table AUTHOR
(
    USER_ID       NUMBER not NULL primary key,
    CREATION_DATE DATE   not NULL,
    AUTHOR_NAME   VARCHAR2(255),
    SCREEN_NAME   VARCHAR2(255)
);

create table MESSAGE
(
    MESSAGE_ID    NUMBER        not NULL primary key,
    CREATION_DATE DATE          not NULL,
    MESSAGE_TEXT  VARCHAR2(255) NULL,
    USER_ID       NUMBER        not NULL references AUTHOR
   -- ,CONSTRAINT constraint_name UNIQUE (USER_ID)
);

-- create table AUTHOR_MESSAGE(
--     MESSAGE_ID NUMBER not NULL,
--     USER_ID    NUMBER not NULL
-- );
--
-- alter table AUTHOR_MESSAGE
--     add constraint user_role_PK primary key (MESSAGE_ID, USER_ID);

-- alter table AUTHOR
--     add constraint AUTHOR_TABLE_PK primary key (USER_ID);
--
-- alter table MESSAGE
--     add constraint MESSAGE_TABLE_PK primary key (MESSAGE_ID);




