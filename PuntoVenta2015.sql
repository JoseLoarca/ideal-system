CREATE DATABASE PuntoVenta2015
GO
USE PuntoVenta2015
GO
--#1
CREATE TABLE Categorias
(
	idCategoria INT PRIMARY KEY NOT NULL IDENTITY(1,1),
	descripcion VARCHAR(128) NOT NULL
)
GO
--#2
CREATE TABLE Productos
(
	idProducto INT PRIMARY KEY NOT NULL IDENTITY(1,1),
	nombre VARCHAR(128) NOT NULL,
	precioUnitario DECIMAL(10,2) NOT NULL DEFAULT (0.0),
	precioPorDocena DECIMAL(10,2) NOT NULL DEFAULT (0.0),
	precioPorMayor DECIMAL(10,2) NOT NULL DEFAULT (0.0),
	idEmpaque INT NOT NULL FOREIGN KEY (idEmpaque) REFERENCES Empaques,
	idStock INT NOT NULL FOREIGN KEY(idStock) REFERENCES Stocks,
	descripcion VARCHAR(128) NOT NULL,
	idCategoria INT NOT NULL FOREIGN KEY (idCategoria) REFERENCES Categorias
)
GO
--#3
CREATE TABLE Proveedores
(
	idProveedor INT PRIMARY KEY NOT NULL IDENTITY(1,1),
	nit VARCHAR(128) NOT NULL,
	nombre VARCHAR(128) NOT NULL,
	paginaWeb VARCHAR(128) NOT NULL,
	contacto VARCHAR(128) NOT NULL
)
GO
--#4
CREATE TABLE Empaques
(
	idEmpaque INT PRIMARY KEY NOT NULL IDENTITY (1,1),
	descripcion VARCHAR(128) NOT NULL
)
GO
--#5
CREATE TABLE Clientes
(
	idCliente INT PRIMARY KEY NOT NULL IDENTITY (1,1),
	nit VARCHAR(128) NOT NULL,
	dpi VARCHAR(128) NOT NULL,
	nombre VARCHAR (128) NOT NULL,
)
GO
--#6
CREATE TABLE Facturas
(
	idFactura INT PRIMARY KEY NOT NULL IDENTITY(1,1),
	numeroDeFactura VARCHAR(128) NOT NULL,
	fecha DATE NOT NULL,
	descripcion VARCHAR (128) NOT NULL,
	total REAL NOT NULL,
	idCliente INT NOT NULL FOREIGN KEY (idCliente) REFERENCES Clientes
)
GO
--#7
CREATE TABLE Compras
(
	idCompra INT PRIMARY KEY NOT NULL IDENTITY(1,1),
	numeroDeCompra VARCHAR(128) NOT NULL,
	fecha DATE NOT NULL,
	descripcion VARCHAR(128) NOT NULL,
	total REAL NOT NULL,
	idProveedor INT NOT NULL FOREIGN KEY (idProveedor) REFERENCES Proveedores
)
GO
--#8
CREATE TABLE TipoMovimientos
(
	idTipoMovimientos INT PRIMARY KEY NOT NULL IDENTITY(1,1),
	descripcion VARCHAR(128) NOT NULL,
)
GO
--#9
CREATE TABLE Inventarios
(
	idMovimiento INT PRIMARY KEY NOT NULL IDENTITY(1,1),
	fecha DATE NOT NULL,
	lineaNo INT NOT NULL,
	cantidad INT NOT NULL,
	precio REAL NOT NULL,
	idProducto INT NOT NULL FOREIGN KEY (idProducto) REFERENCES Productos,
	idTipoMovimientos INT NOT NULL FOREIGN KEY (idTipoMovimientos) REFERENCES TipoMovimientos,
	idFactura INT NOT NULL FOREIGN KEY (idFactura) REFERENCES Facturas,
	idCompra INT NOT NULL FOREIGN KEY (idCompra) REFERENCES Compras
)
GO
--#10
CREATE TABLE Stocks
(
	idStock INT PRIMARY KEY NOT NULL IDENTITY(1,1),
	stock REAL NOT NULL
)
GO
--#11
CREATE TABLE TipoTelefonoProveedores
(
	idTipo INT PRIMARY KEY NOT NULL IDENTITY (1,1),
	descripcion VARCHAR(128) NOT NULL
)
GO
--#12
CREATE TABLE TipoTelefonoClientes
(
	idTipo INT PRIMARY KEY NOT NULL IDENTITY(1,1),
	descripcion VARCHAR(128) NOT NULL,
)
GO
--#13
CREATE TABLE TipoEmailProveedores
(
	idTipo INT PRIMARY KEY IDENTITY(1,1) NOT NULL,
	descripcion VARCHAR(128) NOT NULL
)
GO
--#14
CREATE TABLE TipoEmailClientes
(
	idTipo INT PRIMARY KEY IDENTITY(1,1) NOT NULL,
	descripcion VARCHAR(128) NOT NULL,
)
GO
--#15
CREATE TABLE TipoDireccionProveedores
(
	idTipo INT PRIMARY KEY IDENTITY(1,1) NOT NULL,
	descripcion VARCHAR(128) NOT NULL
)
GO
--#16
CREATE TABLE TipoDireccionClientes
(
	idTipo INT PRIMARY KEY IDENTITY(1,1) NOT NULL,
	descripcion VARCHAR(128) NOT NULL
)
GO
--#17
CREATE TABLE TelefonoProveedores
(
	idTelefono INT PRIMARY KEY IDENTITY(1,1) NOT NULL,
	telefono VARCHAR (128) NOT NULL,
	idProveedor INT NOT NULL FOREIGN KEY (idProveedor) REFERENCES Proveedores,
	idTipo INT NOT NULL FOREIGN KEY (idTipo) REFERENCES TipoTelefonoProveedores
)
GO
--#18
CREATE TABLE TelefonoClientes
(
	idTelefono INT PRIMARY KEY IDENTITY(1,1) NOT NULL,
	telefono VARCHAR(128) NOT NULL,
	idCliente INT NOT NULL FOREIGN KEY (idCliente) REFERENCES Clientes,
	idTipo INT NOT NULL FOREIGN KEY (idTipo) REFERENCES TipoTelefonoClientes
)
GO
--#19
CREATE TABLE EmailProveedores
(
	idEmail INT PRIMARY KEY IDENTITY(1,1) NOT NULL,
	idProveedor INT NOT NULL FOREIGN KEY (idProveedor) REFERENCES Proveedores,
	idTipo INT NOT NULL FOREIGN KEY (idTipo) REFERENCES TipoEmailProveedores,
	email VARCHAR(128) NOT NULL
)
GO
--#20
CREATE TABLE EmailClientes
(
	idEmail INT PRIMARY KEY IDENTITY(1,1) NOT NULL,
	idCliente INT NOT NULL FOREIGN KEY(idCliente) REFERENCES Clientes,
	idTipo INT NOT NULL FOREIGN KEY (idTipo) REFERENCES TipoEmailClientes,
	email VARCHAR(128) NOT NULL
)
GO
--#21
CREATE TABLE DireccionProveedores
(
	idDireccion INT PRIMARY KEY IDENTITY(1,1) NOT NULL,
	idProveedor INT NOT NULL FOREIGN KEY(idProveedor) REFERENCES Proveedores,
	idTipo INT NOT NULL FOREIGN KEY(idTipo) REFERENCES TipoDireccionProveedores,
	direccion VARCHAR(128) NOT NULL
)
GO
--#22
CREATE TABLE DireccionClientes
(
	idDireccion INT PRIMARY KEY IDENTITY(1,1) NOT NULL,
	idCliente INT NOT NULL FOREIGN KEY (idCliente) REFERENCES Clientes,
	idTipo INT NOT NULL FOREIGN KEY (idTipo) REFERENCES TipoDireccionClientes,
	direccion VARCHAR(128) NOT NULL
)
GO
--#23
CREATE TABLE DetalleFacturas
(
	idDetalle INT PRIMARY KEY IDENTITY(1,1) NOT NULL,
	idFactura INT NOT NULL FOREIGN KEY (idFactura) REFERENCES Facturas,
	idProducto INT NOT NULL FOREIGN KEY (idProducto) REFERENCES Productos,
	descripcion INT NOT NULL,
	cantidad INT NOT NULL,
	precio REAL NOT NULL,
	totalLinea REAL NOT NULL
)
GO
--ALTER TABLE DetalleFacturas
--ALTER COLUMN descripcion VARCHAR(128)
--GO
--INSERT INTO DetalleFacturas(idFactura, lineaNo, idProducto, descripcion, cantidad, precio, totalLinea)
--VALUES(1, 123, 3, 'descripcion', 2, 180.00, 360.00)
--GO
--#24
CREATE TABLE DetalleCompras
(
	idDetalle INT PRIMARY KEY IDENTITY(1,1) NOT NULL,
	idCompra INT NOT NULL FOREIGN KEY (idCompra) REFERENCES Compras,
	idProducto INT NOT NULL FOREIGN KEY (idProducto) REFERENCES Productos,
	descripcion INT NOT NULL,
	cantidad INT NOT NULL,
	precio REAL NOT NULL,
	totalLinea REAL NOT NULL
)
GO
--#25
CREATE TABLE Usuario
(
	idUsuario INT PRIMARY KEY IDENTITY(1,1) NOT NULL,
	usuario VARCHAR(128),
	contrasena VARCHAR(128)
)
GO
INSERT INTO Usuario(usuario,contrasena)
VALUES('joseloarca','admin')
GO
--INSERT INTO Categorias (descripcion)
--VALUES ('Joyas'), ('Utiles Escolares'), ('Cosmeticos'), ('Computo'), ('Golosinas'), ('Juguetes'),
--('Ropa'), ('Comida'), ('Utensilios de cocina'), ('Accesorios')
--GO
--INSERT INTO Clientes(nit,dpi,nombre)
--VALUES('123456-7', '234235435345', 'Juan Perez'), ('345935-k', '4234234234', 'Edgar Villatoro'), 
--('435344-3', '6534534534', 'Martin Guzman'), ('234452-2', '234134123', 'Angel Paz'),
--('795908-0', '54543453453', 'Carlos Grajeda')
--GO
--INSERT INTO Proveedores(nit, nombre, paginaWeb, contacto)
--VALUES('131233-2', 'Bosch', 'www.bosch.com', '24356789'), ('3245233-2', 'BIC', 'www.bic.com', '79685612'),
--('653345-2', 'Adidas', 'www.adidas.com', '23330000'), ('7943470-k', 'Jugueton', 'www.jugueton.com', '24569012'),
--('674590-0', 'Intelaf', 'www.intelaf.com', '24567890')
--GO
--INSERT INTO Empaques(descripcion)
--VALUES('empaque1'), ('empaque2'), ('empaque3'), ('empaque4'), ('empaque5'), ('empaque6'), ('empaque7'), ('empaque8')
--GO
--INSERT INTO Stocks(stock)
--VALUES(10), (8), (10), (15), (9), (13), (1), (10), (20)
--GO
--INSERT INTO Productos(nombre, descripcion, precioUnitario, precioPorDocena, precioPorMayor, idEmpaque, idCategoria, idStock)
--VALUES('Collar Perlas', 'Collar de 16 perlas blancas', 100.00, 90.00, 75.00, 1, 1, 1), ('Laptop', 'Laptop pantalla diez pulgs, 2gbs ram, 160gbs HDD', 2600.00, 2400.00, 2000.00, 3, 3, 3),
--('Transformer', 'Figura Bumblebee transformer edicion especial', 300.00, 275.00, 150.00, 6,6,6), ('Playera', 'Playera Adidas talla M', 150.00, 125.00, 75.00, 7, 7, 7),
--('Pizza', 'Pizza mediana suprema extra queso', 145.00, 135.00, 100.00, 8,8,8)
--GO
--INSERT INTO Facturas(numeroDeFactura, fecha, idCliente, descripcion, total)
--VALUES(1234, '2015-01-01', 1, '3 playeras Adidas talla M', 450.00), (1235, '2015-01-02',2, '12 pizzas medianas suprema extra queso', 1620.00),
--(1236, '2015-01-03',3, 'Laptop pantalla diez pulgs, 2gbs ram, 160gbs HDD', 2600.00), (1237, '2015-01-04', 4,'2 figuras bumblebee edicion especial', 600.00),
--(1238, '2015-01-05', 5, 'Collar de 16 perlas blancas', 100.00)
--GO
--INSERT INTO Compras(numeroDeCompra, fecha, descripcion, total, idProveedor)
--VALUES(14532, '2015-02-10', 'Compra de 20 collares de 16 perlas blancas', 1400.00, 1), (12983, '2015-02-15', 'Compra de 6 Laptops', 12000.00, 2),
--(18622, '2015-03-03', 'Compra de 25 figuras de Bumblebee', 3750.00, 3), (19877, '2015-03-15', 'Compra de 100 playeras Adidas talla M', 75000.00, 4),
--(20000, '2015-04-01', 'Compra de 50 pizzas medianas suprema extra queso', 50000.00, 5)
--GO