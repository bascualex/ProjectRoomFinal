USE projectroom;

SET FOREIGN_KEY_CHECKS = 0;

-- ======== USUARIOS ========
-- Contraseña de todos: 12345678 (cifrada con BCrypt)
INSERT INTO usuario (email, nombre, apellido, password, foto) VALUES
('admin@projectroom.com',  'Admin',  'ProjectRoom', '$2a$10$lH.4BgitLkJeB7uf9s56pu5QuW0ZuPw7N57Pb/6cZ0YGuchCz5e.O', 'uploads/default.jpg'),
('carlos@projectroom.com', 'Carlos', 'Ruiz',        '$2a$10$lH.4BgitLkJeB7uf9s56pu5QuW0ZuPw7N57Pb/6cZ0YGuchCz5e.O', 'uploads/default.jpg'),
('maria@projectroom.com',  'María',  'López',       '$2a$10$lH.4BgitLkJeB7uf9s56pu5QuW0ZuPw7N57Pb/6cZ0YGuchCz5e.O', 'uploads/default.jpg'),
('juan@projectroom.com',   'Juan',   'García',      '$2a$10$lH.4BgitLkJeB7uf9s56pu5QuW0ZuPw7N57Pb/6cZ0YGuchCz5e.O', 'uploads/default.jpg'),
('lucia@projectroom.com',  'Lucía',  'Martínez',   '$2a$10$lH.4BgitLkJeB7uf9s56pu5QuW0ZuPw7N57Pb/6cZ0YGuchCz5e.O', 'uploads/default.jpg');

-- ======== PERMISOS ========
INSERT INTO permiso (idPermiso, nombre) VALUES
(1,  'CREAR_PROYECTO'),
(2,  'EDITAR_PROYECTO'),
(3,  'ELIMINAR_PROYECTO'),
(4,  'VER_PROYECTO'),
(5,  'GESTIONAR_INTEGRANTES'),
(6,  'GESTIONAR_ROLES'),
(7,  'GESTIONAR_ETIQUETAS'),
(8,  'CREAR_TAREA'),
(9,  'EDITAR_TAREA'),
(10, 'ELIMINAR_TAREA');

-- ======== PRIORIDADES ========
INSERT INTO prioridad (idPrioridad, nombre, color) VALUES
(1, 'Baja',    '#22c55e'),
(2, 'Media',   '#f59e0b'),
(3, 'Alta',    '#ef4444'),
(4, 'Crítica', '#7c3aed');

-- ======== ESTADOS ========
INSERT INTO estado (idEstado, nombre, color) VALUES
(1, 'Pendiente',   '#94a3b8'),
(2, 'En progreso', '#3b82f6'),
(3, 'En revisión', '#f59e0b'),
(4, 'Completada',  '#22c55e'),
(5, 'Bloqueada',   '#ef4444');

-- ======== ETIQUETAS ========
INSERT INTO etiqueta (idEtiqueta, nombre, color, creador) VALUES
(1, 'Admin',    '#2563eb', 'admin@projectroom.com'),
(2, 'Frontend', '#8b5cf6', 'admin@projectroom.com'),
(3, 'Backend',  '#10b981', 'admin@projectroom.com'),
(4, 'Urgente',  '#ef4444', 'admin@projectroom.com'),
(5, 'Revisión', '#f59e0b', 'carlos@projectroom.com');

-- ======== ROLES ========
INSERT INTO rol (idRol, nombre, color, creador) VALUES
(1, 'Admin',         '#2563eb', 'admin@projectroom.com'),
(2, 'Encargado',     '#8b5cf6', 'admin@projectroom.com'),
(3, 'Desarrollador', '#10b981', 'admin@projectroom.com'),
(4, 'Diseñador',     '#f59e0b', 'admin@projectroom.com'),
(5, 'Tester',        '#ef4444', 'admin@projectroom.com');

-- ======== DETALLE ROL - PERMISO ========
INSERT INTO detalle_rol_permiso (idRol, idPermiso) VALUES
-- Admin: todos los permisos
(1, 1),(1, 2),(1, 3),(1, 4),(1, 5),(1, 6),(1, 7),(1, 8),(1, 9),(1, 10),
-- Encargado
(2, 1),(2, 2),(2, 4),(2, 5),(2, 8),(2, 9),
-- Desarrollador
(3, 4),(3, 8),(3, 9),
-- Diseñador
(4, 4),(4, 8),(4, 9),
-- Tester
(5, 4),(5, 8),(5, 9),(5, 10);

-- ======== DETALLE ETIQUETA - ROL ========
INSERT INTO detalle_etiqueta_rol (idEtiqueta, idRol) VALUES
(1, 1),
(2, 3),(2, 4),
(3, 3),
(4, 1),(4, 2),
(5, 2),(5, 5);

-- ======== PROYECTOS ========
INSERT INTO proyecto (idProyecto, nombre, color, tipo, fecha_inicial, fecha_final, presupuesto, archivado, idEtiqueta) VALUES
(1, 'Rediseño Web Corporativo', '#2563eb', 'Diseño',     '2025-01-01', '2025-09-30', 50000, false, 2),
(2, 'App Móvil de Ventas',      '#10b981', 'Desarrollo', '2025-02-01', '2025-11-30', 80000, false, 3),
(3, 'Portal de RRHH',           '#8b5cf6', 'Desarrollo', '2025-03-01', '2025-12-31', 60000, false, 3),
(4, 'Campaña Marketing 2025',   '#f59e0b', 'Marketing',  '2025-01-15', '2025-06-30', 30000, true,  1);

-- ======== QUATRIMESTRES ========
INSERT INTO quatrimestre (idQuatrimestre, porcentaje, gasto, idProyecto) VALUES
-- Proyecto 1 (3 cuatrimestres)
(1,  34, 12000, 1),
(2,  33,  8000, 1),
(3,  33,  5000, 1),
-- Proyecto 2 (3 cuatrimestres)
(4,  34, 20000, 2),
(5,  33, 15000, 2),
(6,  33,  8000, 2),
-- Proyecto 3 (3 cuatrimestres)
(7,  34, 18000, 3),
(8,  33, 10000, 3),
(9,  33,  4000, 3),
-- Proyecto 4 (2 cuatrimestres)
(10, 50, 15000, 4),
(11, 50, 10000, 4);

-- ======== INTEGRANTES ========
INSERT INTO integrante (idIntegrante, email, idRol, idProyecto) VALUES
-- Proyecto 1
(1,  'admin@projectroom.com',  1, 1),
(2,  'carlos@projectroom.com', 2, 1),
(3,  'maria@projectroom.com',  4, 1),
(4,  'juan@projectroom.com',   5, 1),
-- Proyecto 2
(5,  'admin@projectroom.com',  1, 2),
(6,  'carlos@projectroom.com', 2, 2),
(7,  'juan@projectroom.com',   3, 2),
(8,  'lucia@projectroom.com',  3, 2),
-- Proyecto 3
(9,  'admin@projectroom.com',  1, 3),
(10, 'maria@projectroom.com',  2, 3),
(11, 'lucia@projectroom.com',  3, 3),
(12, 'juan@projectroom.com',   5, 3),
-- Proyecto 4
(13, 'admin@projectroom.com',  1, 4),
(14, 'carlos@projectroom.com', 2, 4);

-- ======== DETALLE INTEGRANTE - PERMISO ========
INSERT INTO detalle_integrante_permiso (idIntegrante, idPermiso) VALUES
-- Admin en todos los proyectos: todos los permisos
(1,  1),(1,  2),(1,  3),(1,  4),(1,  5),(1,  6),(1,  7),(1,  8),(1,  9),(1,  10),
(5,  1),(5,  2),(5,  3),(5,  4),(5,  5),(5,  6),(5,  7),(5,  8),(5,  9),(5,  10),
(9,  1),(9,  2),(9,  3),(9,  4),(9,  5),(9,  6),(9,  7),(9,  8),(9,  9),(9,  10),
(13, 1),(13, 2),(13, 3),(13, 4),(13, 5),(13, 6),(13, 7),(13, 8),(13, 9),(13, 10),
-- Encargados
(2,  1),(2,  2),(2,  4),(2,  5),(2,  8),(2,  9),
(6,  1),(6,  2),(6,  4),(6,  5),(6,  8),(6,  9),
(10, 1),(10, 2),(10, 4),(10, 5),(10, 8),(10, 9),
(14, 1),(14, 2),(14, 4),(14, 5),(14, 8),(14, 9),
-- Resto
(3,  4),(3,  8),(3,  9),
(4,  4),(4,  8),(4,  9),(4,  10),
(7,  4),(7,  8),(7,  9),
(8,  4),(8,  8),(8,  9),
(11, 4),(11, 8),(11, 9),
(12, 4),(12, 8),(12, 9),(12, 10);

-- ======== DETALLE INTEGRANTE - ETIQUETA ========
INSERT INTO detalle_integrante_etiqueta (idIntegrante, idEtiqueta) VALUES
(1,  1),(1,  2),(1,  3),
(2,  2),(2,  5),
(3,  2),
(4,  5),
(5,  1),(5,  3),
(6,  3),(6,  5),
(7,  3),
(8,  3),
(9,  1),(9,  3),
(10, 2),(10, 5),
(11, 3),
(12, 5);

-- ======== DETALLE PROYECTO - ROL ========
INSERT INTO detalle_proyecto_rol (idProyecto, idRol) VALUES
(1, 1),(1, 2),(1, 4),(1, 5),
(2, 1),(2, 2),(2, 3),(2, 5),
(3, 1),(3, 2),(3, 3),(3, 5),
(4, 1),(4, 2);

-- ======== DETALLE PROYECTO - ETIQUETA ========
INSERT INTO detalle_proyecto_etiqueta (idProyecto, idEtiqueta) VALUES
(1, 1),(1, 2),
(2, 1),(2, 3),
(3, 1),(3, 3),
(4, 1),(4, 4);

-- ======== TAREAS ========
INSERT INTO tarea (idTarea, nombre, idEtiqueta, idProyecto, idPrioridad, idIntegrante, idEstado) VALUES
-- Proyecto 1
(1,  'Diseño de wireframes',         2, 1, 2,  3,  2),
(2,  'Maquetación página principal', 2, 1, 2,  3,  2),
(3,  'Revisión de accesibilidad',    5, 1, 1,  4,  1),
(4,  'Testing de UI',                5, 1, 2,  4,  1),
-- Proyecto 2
(5,  'Diseño de base de datos',      3, 2, 3,  7,  4),
(6,  'API REST de productos',        3, 2, 3,  7,  2),
(7,  'Módulo de autenticación',      3, 2, 4,  8,  2),
(8,  'Testing de endpoints',         5, 2, 2,  8,  1),
-- Proyecto 3
(9,  'Análisis de requisitos',       3, 3, 2,  11, 4),
(10, 'Diseño de módulo de nóminas',  3, 3, 3,  11, 2),
(11, 'Integración con AD',           4, 3, 4,  11, 3),
(12, 'QA y pruebas de regresión',    5, 3, 2,  12, 1),
-- Proyecto 4 (archivado)
(13, 'Estrategia de contenido',      1, 4, 2,  14, 4),
(14, 'Diseño de creatividades',      2, 4, 1,  14, 4);

SET FOREIGN_KEY_CHECKS = 1;