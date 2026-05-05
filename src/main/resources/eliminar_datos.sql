USE projectroom;

SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE detalle_integrante_permiso;
TRUNCATE TABLE detalle_integrante_etiqueta;
TRUNCATE TABLE detalle_rol_permiso;
TRUNCATE TABLE detalle_etiqueta_rol;
TRUNCATE TABLE detalle_proyecto_rol;
TRUNCATE TABLE detalle_proyecto_etiqueta;
TRUNCATE TABLE tarea;
TRUNCATE TABLE integrante;
TRUNCATE TABLE quatrimestre;
TRUNCATE TABLE proyecto;
TRUNCATE TABLE rol;
TRUNCATE TABLE etiqueta;
TRUNCATE TABLE permiso;
TRUNCATE TABLE prioridad;
TRUNCATE TABLE estado;
TRUNCATE TABLE usuario;

SET FOREIGN_KEY_CHECKS = 1;