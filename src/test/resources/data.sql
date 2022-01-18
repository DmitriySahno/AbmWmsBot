insert into organizations(id, name, url)
values (1, 'Matlab', 'https://matlab.com'),
       (2, 'OOP', 'https://oop.com');

insert into users(id, name, surname, telegram_id, login, organization_id)
values (1, 'Vasiliy', 'Petrov', 3021541, 'vasiliy_petrov', 1),
       (2, 'Ivan', 'Ivanov', 5214573, '2I', 2),
       (3, 'Evgeniy', 'Shishkin', 1205487, 'e_shishkin', 2),
       (4, 'Alevtina', 'Bulkina', 3265448, 'abu', 1);

insert into keys(id, key, organization_id)
values (1, '_g7DghYp', 1),
       (2, 'KGfd2oAq', 2),
       (3, 'Bfg_4DG1', 2);

insert into reports(id, name, uri, alias, chart_type, description, organization_id)
values (1, 'Warehouse turnower', '/wh_turnover', 'Warehouse turnower', 'LINE',
        'Common information about product turnover at warehouse', 1),
       (2, 'Illiqud inventory', '/illiquids', 'Illiqud inventory', 'PIE', 'Rests of illiqud product', 1),
       (3, 'Warehouse problems', '/wh_problems', 'Warehouse problem situations', 'RING',
        'Problem situatiions that happens at warehouse', 1),
       (4, 'Users speed', '/users_speed', 'Users speed', 'LINE', 'Average users productivity per date for last week',
        1);

insert into user_requests (id, user_id, request, date)
values (1, 1, '/getReports', '2022-01-12 12:15:36'),
       (2, 1, 'https://matlab.com/wh_problems', '2022-01-12 12:16:07'),
       (3, 2, '/getReports', '2022-01-12 12:15:05'),
       (4, 2, 'https://matlab.com/wh_turnover', '2022-01-12 12:15:36'),
       (5, 3, 'https://matlab.com/wh_problems', '2022-01-12 13:41:19'),
       (6, 1, 'https://matlab.com/wh_turnover', '2022-01-12 13:41:22');

