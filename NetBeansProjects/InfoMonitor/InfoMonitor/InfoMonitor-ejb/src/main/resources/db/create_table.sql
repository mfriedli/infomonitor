create table BREAKINGNEWS
(
	ID INTEGER AUTO_INCREMENT,
	TEXT VARCHAR(150),
	DATE DATE not null,
	AUTHOR VARCHAR(35),
	IS_ACTIVE SMALLINT default 0 not null,
	IS_BLINKING SMALLINT default 1 not null,
        PRIMARY KEY (ID)
);

create table SEASON
(
	ID BIGINT AUTO_INCREMENT,
	SEASON_ID INTEGER not null,
	SEASON_NAME VARCHAR(60) not null,
	IS_ACTIVE SMALLINT default 0 not null,
        PRIMARY KEY (ID)
);

create table NEWS
(
	ID INTEGER AUTO_INCREMENT,
	TITLE VARCHAR(60),
	TEXT LONG VARCHAR not null,
	DATE DATE,
	PICTURE BLOB(50000),
	SHOW_INTERVAL INTEGER default 10 not null,
	IS_HIDE SMALLINT default 0 not null,
         PRIMARY KEY (ID)
);

create table LEAGUE
(
	ID BIGINT AUTO_INCREMENT,
	LEAGUE_ID INTEGER not null,
	LEAGUE_NAME VARCHAR(50) not null,
	LEAGUE_SHORT_NAME VARCHAR(15),
        PRIMARY KEY (ID)
);

create table GAME
(
	ID INTEGER AUTO_INCREMENT,
	IHS_GAME_NBR INTEGER not null,
	HOME VARCHAR(60) not null,
	GUEST VARCHAR(60) not null,
	SCORE_HOME SMALLINT,
	SCORE_GUEST SMALLINT,
	DATE DATE not null,
	TIME TIME not null,
	LOCATION VARCHAR(80) not null,
	IS_OVERTIME BOOLEAN default false not null,
	IS_PENALTY_SHOOT_OUT BOOLEAN default false not null,
	IS_COMPLETED BOOLEAN default false not null,
        PRIMARY KEY (ID)
);

create table CONTENT
(
	ID INTEGER AUTO_INCREMENT,
	IS_ACTIVE SMALLINT default 1 not null,
	CONTENT_TYPE VARCHAR(60),
	CREATION_TIME TIMESTAMP not null,
	INTERVAL_SHOW INTEGER default 10000 not null,
	WIDTH INTEGER,
	HEIGHT INTEGER,
	PROTOCOL VARCHAR(15),
	CONTENT_URI VARCHAR(150),
	EXT_WEB_URL VARCHAR(250),
	SORT_ORDER INTEGER default 0 not null,
        PRIMARY KEY (ID)
);
