CREATE SCHEMA `mi_muebleria`;

CREATE USER usuarioParaProyecto IDENTIFIED BY '41288320abc';
GRANT USAGE, INSERT, DELETE, UPDATE, SELECT ON mi_muebleria.* TO usuarioParaProyecto;

USE `mi_muebleria`;
CREATE TABLE `mueble` (
`nombre` VARCHAR(100) NOT NULL,
`precio` DECIMAL(10,2) NOT NULL,
PRIMARY KEY (`nombre`)
);
CREATE TABLE `pieza` (
`nombre` VARCHAR(100) NOT NULL,
`precio` DECIMAL(10,2) NOT NULL,
`existencias` INT NOT NULL,
PRIMARY KEY (`nombre`, `precio`)
);
CREATE TABLE `formula` (
`codigo_de_mueble` VARCHAR(100) NOT NULL,
`nombre_de_pieza` VARCHAR(100) NOT NULL,
`precio_de_pieza` DECIMAL(10,2) NOT NULL,
`piezas_necesarias` INT NOT NULL,
PRIMARY KEY (`codigo_de_mueble`,`nombre_de_pieza`,`precio_de_pieza`),
FOREIGN KEY (`codigo_de_mueble`)
REFERENCES `mueble` (`nombre`) ON UPDATE CASCADE ON DELETE CASCADE, 
FOREIGN KEY (`nombre_de_pieza` , `precio_de_pieza`)
REFERENCES `pieza` (`nombre` , `precio`) ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE TABLE `usuario` (
`nombre_de_trabajador` VARCHAR(100) NOT NULL,
`usuario` VARCHAR(100) NOT NULL,
`password` VARCHAR(100) NOT NULL,
`puesto` VARCHAR(100) NOT NULL,
`estado` VARCHAR(100) NOT NULL,
PRIMARY KEY (`usuario`)
);
CREATE TABLE `ensamble` (
`codigo_de_usuario` VARCHAR(100) NOT NULL,
`codigo_de_ensamble` INT NOT NULL AUTO_INCREMENT,
`codigo_de_mueble` VARCHAR(100) NOT NULL,
`fecha_de_ensamble` DATE NOT NULL,
`costo_de_ensamble` DECIMAL(10,2),
`aprobacion` VARCHAR(100) NOT NULL,
PRIMARY KEY (`codigo_de_ensamble`),
FOREIGN KEY (`codigo_de_usuario`)
REFERENCES `usuario` (`usuario`) ON UPDATE CASCADE ON DELETE CASCADE,
FOREIGN KEY (`codigo_de_mueble`)
REFERENCES `mueble` (`nombre`) ON UPDATE CASCADE
);
CREATE TABLE `stock` (
`codigo_de_ensamble` INT NOT NULL,
`estado` VARCHAR(100) NOT NULL,
PRIMARY KEY (`codigo_de_ensamble`),
FOREIGN KEY (`codigo_de_ensamble`)
REFERENCES `ensamble` (`codigo_de_ensamble`)
);
CREATE TABLE `cliente` (
`nit` VARCHAR(100) NOT NULL,
`nombre` VARCHAR(100) NOT NULL,
`direccion` VARCHAR(100) NOT NULL,
PRIMARY KEY (`nit`)
);
CREATE TABLE `compra` (
`codigo_de_factura` INT NOT NULL,
`codigo_de_cliente` VARCHAR(100) NOT NULL,
`codigo_de_ensamble` INT NOT NULL,
`usuario_vendedor` VARCHAR(100) NOT NULL,
`fecha_de_compra` DATE NOT NULL,
PRIMARY KEY (`codigo_de_ensamble`,`codigo_de_factura`),
FOREIGN KEY (`usuario_vendedor`)
REFERENCES `usuario` (`usuario`),
FOREIGN KEY (`codigo_de_ensamble`)
REFERENCES `stock` (`codigo_de_ensamble`),
FOREIGN KEY (`codigo_de_cliente`)
REFERENCES `cliente` (`nit`)
);
CREATE TABLE `devolucion` (
`codigo_de_cliente` VARCHAR(100) NOT NULL,
`codigo_de_ensamble` INT NOT NULL,
`motivo` LONGTEXT NOT NULL,
`perdida` DECIMAL(10,2) NOT NULL,
`fecha_de_devolucion` DATE NOT NULL,
FOREIGN KEY (`codigo_de_ensamble`)
REFERENCES `ensamble` (`codigo_de_ensamble`),
FOREIGN KEY (`codigo_de_cliente`)
REFERENCES `cliente` (`nit`)
);

INSERT INTO usuario VALUES ("Jose", "Financiera", "123", "Area financiera", "Activo");
INSERT INTO usuario VALUES ("Jose", "Fabrica", "123", "Fabrica", "Activo");
INSERT INTO usuario VALUES ("Jose", "Ventas", "123", "Punto de venta", "Activo");
