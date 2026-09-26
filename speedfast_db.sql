-- Creación de la base de datos.

CREATE DATABASE IF NOT EXISTS speedfast_db;

USE speedfast_db;

-- Limpieza de campos antes de comenzar.

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS entrega;
DROP TABLE IF EXISTS pedido;
DROP TABLE IF EXISTS repartidor;

SET FOREIGN_KEY_CHECKS = 1;

-- Creación de tablas.

-- PEDIDO

CREATE TABLE `pedido` (
  `idpedido` 		int NOT NULL AUTO_INCREMENT,
  `tipoPedido` 		varchar(45) NOT NULL,
  `descripcion` 	varchar(100) NOT NULL,
  `direccionNumero` int NOT NULL,
  `direccionCalle` 	varchar(45) NOT NULL,
  `direccionCiudad` varchar(45) NOT NULL,
  `distanciaKm` 	int NOT NULL,
  `validacion` 		tinyint(1) NOT NULL,
  `prioridadPedido` varchar(45) NOT NULL,
  `estadoPedido` 	varchar(45) NOT NULL,
  PRIMARY KEY (`idpedido`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- REPARTIDOR

CREATE TABLE `repartidor` (
  `idRepartidor` 		int NOT NULL AUTO_INCREMENT,
  `nombreRepartidor` 	varchar(100) NOT NULL,
  PRIMARY KEY (`idRepartidor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ENTREGA

CREATE TABLE `entrega` (
  `idPedido` 		int NOT NULL,
  `idRepartidor` 	int NOT NULL,
  `fecha` 			date NOT NULL,
  `hora` 			time NOT NULL,
  PRIMARY KEY (`idPedido`),
  KEY `idRepartidor_idx` (`idRepartidor`,`idPedido`),
  CONSTRAINT `fk_entrega_pedido` FOREIGN KEY (`idPedido`) REFERENCES `pedido` (`idpedido`),
  CONSTRAINT `fk_entrega_repartidor` FOREIGN KEY (`idRepartidor`) REFERENCES `repartidor` (`idRepartidor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Datos de ejemplo.
INSERT INTO pedido 
(idPedido, tipoPedido, descripcion, direccionNumero, direccionCalle, direccionCiudad, distanciakm, validacion, prioridadPedido, estadoPedido)
VALUES
(101, 'Pedido Express', 'Caja', 235, 'El canelo', 'Temuco', 2, true, 'ALTA', 'PENDIENTE'),
(102, 'Pedido Encomienda', 'Bulto', 546, 'El rosal', 'Santiago', 12, true, 'BAJA', 'PENDIENTE'),
(103, 'Pedido Comida', 'KFC', 25, 'El limonero', 'Concepción', 1, false, 'MEDIA', 'PENDIENTE'),
(104, 'Pedido Express', 'Sobre', 647, 'El damasco', 'Punta Arenas', 5, true, 'ALTA', 'PENDIENTE'),
(105, 'Pedido Encomienda', 'Valija', 986, 'El cerezo', 'Iquique', 34, true, 'BAJA', 'PENDIENTE'),
(106, 'Pedido Comida', 'Supermercado Jumbo', 568, 'El durazno', 'Validivia', 2, false, 'MEDIA', 'PENDIENTE'),
(107, 'Pedido Express', 'Flores', 456, 'El guindo', 'Santiago', 3, true, 'ALTA', 'PENDIENTE'),
(108, 'Pedido Encomienda', 'Bulto', 675, 'El ciruelo', 'Los ángeles', 4, true, 'BAJA', 'PENDIENTE'),
(109, 'Pedido Comida', 'Burguer King', 786, 'El manzano', 'Antofagasta',6,  false, 'MEDIA', 'PENDIENTE'),
(110, 'Pedido Express', 'Maletín', 984, 'El peral', 'Valparaíso',9 , true, 'ALTA', 'PENDIENTE');

INSERT INTO repartidor 
(nombreRepartidor)
VALUES
('Jorge López'),
('Laura Robles'),
('Anita Martínez'),
('José Gómez');





