CREATE DATABASE  IF NOT EXISTS `casabaju` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `casabaju`;
-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: localhost    Database: casabaju
-- ------------------------------------------------------
-- Server version	8.0.44

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `alojamiento_servicio`
--

DROP TABLE IF EXISTS `alojamiento_servicio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alojamiento_servicio` (
  `alojamiento_id` int unsigned NOT NULL,
  `servicio_id` int unsigned NOT NULL,
  PRIMARY KEY (`alojamiento_id`,`servicio_id`),
  KEY `servicio_id` (`servicio_id`),
  CONSTRAINT `alojamiento_servicio_ibfk_1` FOREIGN KEY (`alojamiento_id`) REFERENCES `alojamientos` (`id`) ON DELETE CASCADE,
  CONSTRAINT `alojamiento_servicio_ibfk_2` FOREIGN KEY (`servicio_id`) REFERENCES `servicios` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alojamiento_servicio`
--

LOCK TABLES `alojamiento_servicio` WRITE;
/*!40000 ALTER TABLE `alojamiento_servicio` DISABLE KEYS */;
INSERT INTO `alojamiento_servicio` VALUES (1,1),(2,1),(3,1),(1,2),(2,2),(3,2),(1,3),(2,3),(3,3),(1,4),(1,5),(1,6),(3,6);
/*!40000 ALTER TABLE `alojamiento_servicio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `alojamientos`
--

DROP TABLE IF EXISTS `alojamientos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alojamientos` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) COLLATE utf8mb4_spanish_ci NOT NULL,
  `descripcion` text COLLATE utf8mb4_spanish_ci,
  `capacidad` tinyint unsigned NOT NULL,
  `num_camas` tinyint unsigned NOT NULL DEFAULT '1',
  `metros2` decimal(6,2) DEFAULT NULL,
  `precio_noche` decimal(8,2) NOT NULL,
  `activo` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alojamientos`
--

LOCK TABLES `alojamientos` WRITE;
/*!40000 ALTER TABLE `alojamientos` DISABLE KEYS */;
INSERT INTO `alojamientos` VALUES (1,'Casa Completa','La casa al completo con todas las instalaciones',10,5,250.00,350.00,1),(2,'Habitación Roble','Habitación doble con vistas al jardín',2,1,25.00,75.00,1),(3,'Habitación Pino','Habitación familiar con baño privado',4,2,40.00,110.00,1);
/*!40000 ALTER TABLE `alojamientos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contacto_mensajes`
--

DROP TABLE IF EXISTS `contacto_mensajes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contacto_mensajes` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `nombre` varchar(120) COLLATE utf8mb4_spanish_ci NOT NULL,
  `email` varchar(150) COLLATE utf8mb4_spanish_ci NOT NULL,
  `telefono` varchar(20) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `asunto` varchar(200) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `mensaje` text COLLATE utf8mb4_spanish_ci NOT NULL,
  `leido` tinyint(1) NOT NULL DEFAULT '0',
  `respondido` tinyint(1) NOT NULL DEFAULT '0',
  `creado_en` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contacto_mensajes`
--

LOCK TABLES `contacto_mensajes` WRITE;
/*!40000 ALTER TABLE `contacto_mensajes` DISABLE KEYS */;
INSERT INTO `contacto_mensajes` VALUES (1,'Pedro Sánchez','pedro@correo.es','611222333','Duda sobre la piscina','Hola, ¿la piscina está abierta en la primera semana de mayo?',1,0,'2026-06-03 09:15:00'),(2,'Ana López','ana.lopez@correo.es','622333444','Reserva grupo grande','Queríamos saber si hay algún descuento para estancias de 8 personas durante una semana.',1,1,'2026-06-01 14:20:00'),(3,'a','a@gmail.com','13451235','Consulta general','a',1,0,'2026-06-09 17:40:15'),(4,'a','davidgarciasuarez99@gmail.com','13451235','Consulta general','asdf',1,0,'2026-06-09 18:04:57'),(5,'a','a@gmail.com','qwe3rq32','Consulta general','q',0,0,'2026-06-10 20:05:11'),(6,'asdf','aedfsdf','','Consulta general','asdf',0,0,'2026-06-10 20:05:30');
/*!40000 ALTER TABLE `contacto_mensajes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `galeria`
--

DROP TABLE IF EXISTS `galeria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `galeria` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `alojamiento_id` int unsigned DEFAULT NULL,
  `url_imagen` varchar(500) COLLATE utf8mb4_spanish_ci NOT NULL,
  `alt_texto` varchar(200) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `es_portada` tinyint(1) NOT NULL DEFAULT '0',
  `orden` tinyint unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `alojamiento_id` (`alojamiento_id`),
  KEY `idx_galeria_portada` (`es_portada`),
  CONSTRAINT `galeria_ibfk_1` FOREIGN KEY (`alojamiento_id`) REFERENCES `alojamientos` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `galeria`
--

LOCK TABLES `galeria` WRITE;
/*!40000 ALTER TABLE `galeria` DISABLE KEYS */;
INSERT INTO `galeria` VALUES (1,1,'https://images.unsplash.com/photo-1518780664697-55e3ad937233','Vista exterior de La Casa Baju',1,1),(2,1,'https://images.unsplash.com/photo-1560448204-e02f11c3d0e2','Salón principal con chimenea',0,2),(3,2,'https://images.unsplash.com/photo-1522771731470-3134de2143b2','Cama doble de la Habitación Roble',1,1),(4,3,'https://images.unsplash.com/photo-1590490360182-c33d57733427','Interior acogedor Habitación Pino',1,1);
/*!40000 ALTER TABLE `galeria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pagos`
--

DROP TABLE IF EXISTS `pagos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pagos` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `reserva_id` int unsigned NOT NULL,
  `metodo` enum('STRIPE','PAYPAL','TRANSFERENCIA') COLLATE utf8mb4_spanish_ci NOT NULL,
  `id_transaccion` varchar(255) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `importe` decimal(10,2) NOT NULL,
  `estado` enum('PENDIENTE','COMPLETADO','FALLIDO','REEMBOLSADO') COLLATE utf8mb4_spanish_ci NOT NULL DEFAULT 'PENDIENTE',
  `fecha_pago` datetime DEFAULT NULL,
  `creado_en` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `reserva_id` (`reserva_id`),
  CONSTRAINT `pagos_ibfk_1` FOREIGN KEY (`reserva_id`) REFERENCES `reservas` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pagos`
--

LOCK TABLES `pagos` WRITE;
/*!40000 ALTER TABLE `pagos` DISABLE KEYS */;
INSERT INTO `pagos` VALUES (1,1,'STRIPE','pi_123456789_test',1750.00,'COMPLETADO','2025-07-01 10:00:00','2026-06-04 21:19:35'),(2,7,'PAYPAL','PAY-987654321_test',300.00,'COMPLETADO','2026-06-03 10:05:00','2026-06-04 21:19:35'),(3,8,'TRANSFERENCIA','TRF-112233_test',220.00,'COMPLETADO','2026-06-04 09:15:00','2026-06-04 21:19:35');
/*!40000 ALTER TABLE `pagos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `refresh_tokens`
--

DROP TABLE IF EXISTS `refresh_tokens`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `refresh_tokens` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `usuario_id` int unsigned NOT NULL,
  `token` varchar(512) COLLATE utf8mb4_spanish_ci NOT NULL,
  `expira_en` datetime NOT NULL,
  `revocado` tinyint(1) NOT NULL DEFAULT '0',
  `creado_en` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `token` (`token`),
  KEY `usuario_id` (`usuario_id`),
  CONSTRAINT `refresh_tokens_ibfk_1` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `refresh_tokens`
--

LOCK TABLES `refresh_tokens` WRITE;
/*!40000 ALTER TABLE `refresh_tokens` DISABLE KEYS */;
INSERT INTO `refresh_tokens` VALUES (1,2,'dUmMy_rEfrEsh_tOkEn_LauRa_789','2026-12-31 23:59:59',0,'2026-06-04 21:19:35');
/*!40000 ALTER TABLE `refresh_tokens` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resenas`
--

DROP TABLE IF EXISTS `resenas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `resenas` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `usuario_id` int unsigned NOT NULL,
  `reserva_id` int unsigned NOT NULL,
  `puntuacion` tinyint unsigned NOT NULL,
  `titulo` varchar(120) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `comentario` text COLLATE utf8mb4_spanish_ci,
  `aprobada` tinyint(1) NOT NULL DEFAULT '0',
  `creada_en` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `reserva_id` (`reserva_id`),
  KEY `usuario_id` (`usuario_id`),
  KEY `idx_resenas_aprobada` (`aprobada`),
  CONSTRAINT `resenas_ibfk_1` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`),
  CONSTRAINT `resenas_ibfk_2` FOREIGN KEY (`reserva_id`) REFERENCES `reservas` (`id`),
  CONSTRAINT `resenas_chk_1` CHECK ((`puntuacion` between 1 and 5))
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resenas`
--

LOCK TABLES `resenas` WRITE;
/*!40000 ALTER TABLE `resenas` DISABLE KEYS */;
INSERT INTO `resenas` VALUES (1,1,1,5,'Unas vacaciones perfectas','La casa es maravillosa, muy espaciosa y limpia. Repetiremos sin duda.',1,'2025-07-16 12:00:00'),(2,3,8,4,'Muy acogedor','Todo genial y el entorno precioso. La cama un pelín blanda para mi gusto, pero todo lo demás de 10.',1,'2026-09-13 18:30:00'),(4,5,11,3,'el que sea','aqswefsadf',1,'2026-06-09 17:14:10');
/*!40000 ALTER TABLE `resenas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reserva_servicio`
--

DROP TABLE IF EXISTS `reserva_servicio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reserva_servicio` (
  `reserva_id` int unsigned NOT NULL,
  `servicio_id` int unsigned NOT NULL,
  `cantidad` tinyint unsigned NOT NULL DEFAULT '1',
  PRIMARY KEY (`reserva_id`,`servicio_id`),
  KEY `servicio_id` (`servicio_id`),
  CONSTRAINT `reserva_servicio_ibfk_1` FOREIGN KEY (`reserva_id`) REFERENCES `reservas` (`id`) ON DELETE CASCADE,
  CONSTRAINT `reserva_servicio_ibfk_2` FOREIGN KEY (`servicio_id`) REFERENCES `servicios` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reserva_servicio`
--

LOCK TABLES `reserva_servicio` WRITE;
/*!40000 ALTER TABLE `reserva_servicio` DISABLE KEYS */;
INSERT INTO `reserva_servicio` VALUES (1,5,1),(7,3,2),(8,6,1);
/*!40000 ALTER TABLE `reserva_servicio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservas`
--

DROP TABLE IF EXISTS `reservas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reservas` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `usuario_id` int unsigned NOT NULL,
  `alojamiento_id` int unsigned NOT NULL,
  `fecha_entrada` date NOT NULL,
  `fecha_salida` date NOT NULL,
  `num_personas` tinyint unsigned NOT NULL,
  `precio_total` decimal(10,2) NOT NULL,
  `estado` enum('PENDIENTE','CONFIRMADA','CANCELADA','COMPLETADA') COLLATE utf8mb4_spanish_ci NOT NULL DEFAULT 'PENDIENTE',
  `notas_cliente` text COLLATE utf8mb4_spanish_ci,
  `creada_en` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modificada_en` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `alojamiento_id` (`alojamiento_id`),
  KEY `idx_reservas_fechas` (`fecha_entrada`,`fecha_salida`),
  KEY `idx_reservas_estado` (`estado`),
  KEY `idx_reservas_usuario` (`usuario_id`),
  CONSTRAINT `reservas_ibfk_1` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`),
  CONSTRAINT `reservas_ibfk_2` FOREIGN KEY (`alojamiento_id`) REFERENCES `alojamientos` (`id`),
  CONSTRAINT `reservas_chk_1` CHECK ((`fecha_salida` > `fecha_entrada`)),
  CONSTRAINT `reservas_chk_2` CHECK ((`num_personas` > 0))
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservas`
--

LOCK TABLES `reservas` WRITE;
/*!40000 ALTER TABLE `reservas` DISABLE KEYS */;
INSERT INTO `reservas` VALUES (1,1,1,'2025-07-10','2025-07-15',4,1750.00,'COMPLETADA','Llegamos sobre las 16:00','2026-05-24 22:03:41',NULL),(2,1,1,'2025-07-10','2025-07-15',4,1750.00,'CANCELADA','Llegamos sobre las 16:00','2026-05-24 22:18:39',NULL),(3,1,1,'2025-07-10','2025-07-15',4,1750.00,'CANCELADA','Llegamos sobre las 16:00','2026-05-24 22:20:13',NULL),(4,1,1,'2026-07-10','2026-07-15',4,1750.00,'COMPLETADA','Llegamos sobre las 16:00','2026-05-24 22:24:59',NULL),(5,1,2,'2026-05-24','2026-05-26',1,150.00,'CANCELADA','','2026-05-24 22:30:12',NULL),(6,1,1,'2026-07-16','2026-07-18',1,760.00,'CANCELADA','','2026-06-01 22:44:53',NULL),(7,2,2,'2026-08-01','2026-08-05',2,300.00,'CANCELADA','Llegaremos tarde, sobre las 21:00h.','2026-06-03 10:00:00',NULL),(8,3,3,'2026-09-10','2026-09-12',3,220.00,'COMPLETADA','Necesitamos la cuna montada, por favor.','2026-06-04 08:00:00',NULL),(9,1,1,'2026-06-06','2026-06-07',1,350.00,'CANCELADA','','2026-06-06 17:09:58',NULL),(10,1,1,'2026-06-09','2026-06-10',1,350.00,'COMPLETADA','qwr1234','2026-06-09 16:51:47',NULL),(11,5,1,'2026-06-11','2026-06-12',1,350.00,'COMPLETADA','qwerqw','2026-06-09 17:04:50',NULL),(12,4,1,'2026-06-13','2026-06-14',1,350.00,'CONFIRMADA','asdf','2026-06-09 18:04:47',NULL),(13,5,1,'2026-06-23','2026-06-24',1,350.00,'PENDIENTE','1234','2026-06-10 17:14:50',NULL),(14,5,2,'2026-07-10','2026-07-15',1,375.00,'CANCELADA','qwer','2026-06-10 20:07:22',NULL);
/*!40000 ALTER TABLE `reservas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `servicios`
--

DROP TABLE IF EXISTS `servicios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `servicios` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `nombre` varchar(80) COLLATE utf8mb4_spanish_ci NOT NULL,
  `descripcion` varchar(255) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `precio_extra` decimal(8,2) NOT NULL DEFAULT '0.00',
  `icono` varchar(60) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `servicios`
--

LOCK TABLES `servicios` WRITE;
/*!40000 ALTER TABLE `servicios` DISABLE KEYS */;
INSERT INTO `servicios` VALUES (1,'WiFi','Conexión a internet de alta velocidad',0.00,'fa-wifi'),(2,'Parking','Plaza de aparcamiento privada',0.00,'fa-parking'),(3,'Desayuno','Desayuno completo incluido',15.00,'fa-coffee'),(4,'Piscina','Acceso a piscina exterior (temporada)',0.00,'fa-swimming-pool'),(5,'Barbacoa','Uso de zona de barbacoa',10.00,'fa-fire'),(6,'Cuna','Cuna para bebés bajo petición',5.00,'fa-baby');
/*!40000 ALTER TABLE `servicios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tarifas`
--

DROP TABLE IF EXISTS `tarifas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tarifas` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `alojamiento_id` int unsigned NOT NULL,
  `nombre_temporada` varchar(60) COLLATE utf8mb4_spanish_ci NOT NULL,
  `fecha_inicio` date NOT NULL,
  `fecha_fin` date NOT NULL,
  `precio_noche` decimal(8,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_tarifas_aloj_fecha` (`alojamiento_id`,`fecha_inicio`,`fecha_fin`),
  CONSTRAINT `tarifas_ibfk_1` FOREIGN KEY (`alojamiento_id`) REFERENCES `alojamientos` (`id`) ON DELETE CASCADE,
  CONSTRAINT `tarifas_chk_1` CHECK ((`fecha_fin` > `fecha_inicio`))
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tarifas`
--

LOCK TABLES `tarifas` WRITE;
/*!40000 ALTER TABLE `tarifas` DISABLE KEYS */;
INSERT INTO `tarifas` VALUES (1,1,'Temporada Alta','2025-06-15','2025-09-15',420.00),(2,1,'Semana Santa','2025-04-13','2025-04-21',400.00),(3,1,'Temporada Baja','2025-01-01','2025-06-14',320.00),(4,2,'Temporada Alta','2025-06-15','2025-09-15',95.00),(5,2,'Temporada Baja','2025-01-01','2025-06-14',65.00),(6,3,'Temporada Alta','2025-06-15','2025-09-15',140.00),(7,3,'Temporada Baja','2025-01-01','2025-06-14',100.00),(8,1,'Temporada Alta 2026','2026-06-15','2026-09-15',450.00),(9,2,'Temporada Alta 2026','2026-06-15','2026-09-15',105.00);
/*!40000 ALTER TABLE `tarifas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `nombre` varchar(80) COLLATE utf8mb4_spanish_ci NOT NULL,
  `apellidos` varchar(120) COLLATE utf8mb4_spanish_ci NOT NULL,
  `email` varchar(150) COLLATE utf8mb4_spanish_ci NOT NULL,
  `password_hash` varchar(255) COLLATE utf8mb4_spanish_ci NOT NULL,
  `telefono` varchar(20) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `rol` enum('CLIENTE','ADMIN') COLLATE utf8mb4_spanish_ci NOT NULL DEFAULT 'CLIENTE',
  `activo` tinyint(1) NOT NULL DEFAULT '1',
  `creado_en` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ultimo_acceso` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'Admin','CasaBaju','admin@casabaju.com','$2b$12$Dmxiw2x6QwN/XX9lafMSmuTRDA7QiiOok430.ubnHJTuxc4R94ZBe',NULL,'ADMIN',1,'2026-05-17 16:19:05',NULL),(2,'Laura','Gómez','laura.gomez@ejemplo.com','$2b$12$Dmxiw2x6QwN/XX9lafMSmuTRDA7QiiOok430.ubnHJTuxc4R94ZBe','+34600111222','CLIENTE',1,'2026-06-01 10:00:00',NULL),(3,'Carlos','Martínez','carlos.m@ejemplo.com','$2b$12$Dmxiw2x6QwN/XX9lafMSmuTRDA7QiiOok430.ubnHJTuxc4R94ZBe','+34600333444','CLIENTE',1,'2026-06-02 11:30:00',NULL),(4,'David','Garcia','davidgarciasuarez99@gmail.com','$2a$10$9NZLE5y/CAL.sJ7GcA5kt.IjO2t/sQZEnuXfJwIbAavYrZtdI7XOy','+34 829374923','CLIENTE',1,'2026-06-06 17:53:37',NULL),(5,'a','f','a@gmail.com','$2a$10$cyYn1UF5PiBXV.4EPgb4veFfxYOBoUQB0f/ZJT4r.HMwelOpbijMC','213451235','CLIENTE',1,'2026-06-09 16:19:37',NULL);
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-06-11 14:21:05
