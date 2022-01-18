CREATE TABLE organizations
(
    id   INT          NOT NULL,
    name VARCHAR(150) NOT NULL,
    url  VARCHAR(250) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE users
(
    id              INT          NOT NULL,
    organization_id INT          NOT NULL,
    name            VARCHAR(150) NOT NULL,
    surname         VARCHAR(150) NOT NULL,
    telegram_id     VARCHAR(250) NOT NULL,
    login           VARCHAR(250) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (organization_id) REFERENCES organizations (id)
);

CREATE TABLE keys
(
    id              INT NOT NULL,
    key             VARCHAR(50),
    organization_id INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (organization_id) REFERENCES organizations (id)
);

CREATE TABLE reports
(
    id              INT          NOT NULL,
    name            VARCHAR(150) NOT NULL,
    uri             VARCHAR(150) NOT NULL,
    alias           VARCHAR(250) NOT NULL,
    chart_type      VARCHAR(50)  NOT NULL,
    description     VARCHAR(250) NOT NULL,
    organization_id INT          NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (organization_id) REFERENCES organizations (id),
    CHECK (chart_type in
           ('AREA', 'BAR', 'BOX_AND_WHISKER', 'GANTT', 'HIGH_AND_LOW', 'HISTOGRAM', 'LINE', 'PIE', 'POLAR', 'RING',
            'SCATTER_PLOT', 'TIME_SERIES', 'WATERFALL', 'WAFER_MAP', 'NONE'))
);

CREATE TABLE user_requests
(
    id      INT NOT NULL,
    user_id INT NOT NULL,
    request VARCHAR(250),
    date    TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);
