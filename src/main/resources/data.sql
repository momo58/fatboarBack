--Insertion des gains
INSERT IGNORE INTO gains (id, label) VALUES
(1, 'Entrée ou dessert au choix'),
(2, 'Burger au choix'),
(3, 'Menu du jour'),
(4, 'Menu au choix'),
(5, '70% de réduction'),
(6, 'Range Rover Evoque');

--Insertion de l'administrateur du jeu concours
INSERT IGNORE INTO utilisateurs values (1, 'superadmin', null, '$2a$10$nUPHp/wkeKy1P20lnF3y4uFFlh8soXSkk2tqK9cnnPX4MkggsQAsG', 0, null, 'superadmin');

--Insertion de la présentation du jeu concours
INSERT IGNORE INTO game (id, content) values (1, 'Jeu concours fatboar à administrer');