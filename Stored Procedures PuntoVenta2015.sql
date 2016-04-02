USE PuntoVenta2015
GO
--PROCEDIMIENTOS TABLA CATEGORIAS
CREATE PROCEDURE sp_AgregarCategoria @descripcion VARCHAR (128)
AS
INSERT INTO Categorias (descripcion) VALUES (@descripcion)
GO
CREATE PROCEDURE sp_EliminarCategoria @idCategoria INT
AS
DELETE FROM Categorias WHERE idCategoria = @idCategoria
GO
CREATE PROCEDURE sp_ModificarCategoria @idCategoria INT, @descripcion VARCHAR(128)
AS
UPDATE Categorias SET descripcion = @descripcion WHERE idCategoria = @idCategoria 
GO
CREATE PROCEDURE sp_ListarCategorias
AS
SELECT idCategoria, descripcion FROM Categorias ORDER BY descripcion
GO
--PROCEDIMIENTOS TABLA PRODUCTOS
CREATE PROCEDURE sp_ListarProducto 
AS
SELECT idProducto, nombre, descripcion, precioUnitario, precioPorDocena, precioPorMayor, idEmpaque, idCategoria, idStock FROM Productos ORDER BY descripcion
GO
CREATE PROCEDURE sp_AgregarProducto @nombre VARCHAR(128), @descripcion VARCHAR(128), @idCategoria INT, @idEmpaque INT, @idStock INT
AS
INSERT INTO Productos(nombre, descripcion, idCategoria, idEmpaque,idStock) VALUES (@nombre, @descripcion, @idCategoria, @idEmpaque, @idStock)
GO
CREATE PROCEDURE sp_EliminarProducto @idProducto INT
AS
DELETE FROM Productos WHERE idProducto = @idProducto
GO
CREATE PROCEDURE sp_ModificarProducto @idProducto INT, @nombre VARCHAR(128), @descripcion VARCHAR(128), @precioUnitario DECIMAL(10,2), @precioPorDocena DECIMAL(10,2), @precioPorMayor DECIMAL(10,2), @idEmpaque INT, @idStock INT, @idCategoria INT
AS
UPDATE Productos SET nombre = @nombre, descripcion = @descripcion, precioUnitario = @precioUnitario, precioPorDocena = @precioPorDocena, precioPorMayor = @precioPorMayor, idEmpaque = @idEmpaque, idCategoria = @idCategoria, idStock = @idStock WHERE idProducto = @idProducto
GO
--PROCEDIMIENTOS TABLA PROVEEDORES
CREATE PROCEDURE sp_AgregarProveedor @nit VARCHAR(128), @nombre VARCHAR(128), @paginaWeb VARCHAR(128), @contacto VARCHAR(128)
AS
INSERT INTO Proveedores(nit, nombre, paginaWeb, contacto) VALUES (@nit, @nombre, @paginaWeb, @contacto)
GO
CREATE PROCEDURE sp_EliminarProveedor @idProveedor INT
AS
DELETE FROM Proveedores WHERE idProveedor = @idProveedor
GO
CREATE PROCEDURE sp_ModificarProveedor @idProveedor INT, @nit VARCHAR(128), @nombre VARCHAR(128), @paginaWeb VARCHAR(128), @contacto VARCHAR(128)
AS
UPDATE Proveedores SET nit = @nit, nombre = @nombre, paginaWeb = @paginaWeb, contacto = @contacto WHERE idProveedor = @idProveedor
GO
CREATE PROCEDURE sp_ListarProveedores 
AS
SELECT idProveedor, nit, nombre, paginaWeb, contacto FROM Proveedores ORDER BY nit 
GO
--PROCEDIMIENTOS TABLA CLIENTES
CREATE PROCEDURE sp_AgregarCliente @nit VARCHAR(128), @dpi VARCHAR(128), @nombre VARCHAR(128)
AS
INSERT INTO Clientes(nit, dpi, nombre) VALUES(@nit, @dpi, @nombre)
GO
CREATE PROCEDURE sp_EliminarCliente @idCliente INT
AS
DELETE FROM Clientes WHERE idCliente = @idCliente
GO
CREATE PROCEDURE sp_ModificarCliente @idCliente INT,  @nit VARCHAR(128), @dpi VARCHAR(128), @nombre VARCHAR(128)
AS
UPDATE Clientes SET nit = @nit, dpi = @dpi, nombre = @nombre WHERE idCliente = @idCliente
GO
CREATE PROCEDURE sp_ListarClientes
AS
SELECT idCliente, nit, dpi, nombre FROM Clientes ORDER BY nit
GO
--PROCEDIMIENTOS TABLA EMPAQUES
CREATE PROCEDURE sp_AgregarEmpaque @descripcion VARCHAR(128) 
AS
INSERT INTO Empaques(descripcion) VALUES (@descripcion)
GO
CREATE PROCEDURE sp_EliminarEmpaque @idEmpaque INT
AS
DELETE FROM Empaques WHERE idEmpaque = @idEmpaque
GO
CREATE PROCEDURE sp_ModificarEmpaque @descripcion VARCHAR(128)
AS
UPDATE Empaques SET descripcion = @descripcion 
GO
CREATE PROCEDURE sp_ListarEmpaque
AS
SELECT idEmpaque, descripcion FROM Empaques ORDER BY descripcion
GO
--PROCEDIMIENTOS TABLA COMPRAS
CREATE PROCEDURE sp_ListarCompras
AS
SELECT idCompra, numeroDeCompra, fecha, descripcion, total, idProveedor FROM Compras ORDER BY descripcion
GO
CREATE PROCEDURE sp_AgregarCompras @numeroDeCompra VARCHAR(128), @fecha DATE, @descripcion VARCHAR(128), @total REAL, @idProveedor INT
AS
INSERT INTO Compras(numeroDeCompra, fecha, descripcion, total, idProveedor) VALUES(@numeroDeCompra, @fecha,@descripcion,@total,@idProveedor) 
GO
CREATE PROCEDURE sp_EliminarCompra @idCompra INT
AS
DELETE FROM Compras WHERE idCompra = @idCompra
GO
ALTER PROCEDURE sp_ModificarCompra @idCompra INT, @numeroDeCompra VARCHAR(128), @fecha DATE, @descripcion VARCHAR(128), @total REAL, @idProveedor INT
AS
UPDATE Compras SET numeroDeCompra = @numeroDeCompra, fecha = @fecha, descripcion = @descripcion, total = @total, idProveedor = @idProveedor WHERE idCompra = @idCompra
GO
--PROCEDIMIENTOS TABLA FACTURAS
CREATE PROCEDURE sp_ListarFacturas
AS
SELECT idFactura, numeroDeFactura, fecha, descripcion, total, idCliente FROM Facturas ORDER BY descripcion
GO
CREATE PROCEDURE sp_AgregarFacturas @numeroDeFactura VARCHAR(128), @fecha DATE, @descripcion VARCHAR(128), @total REAL, @idCliente INT
AS
INSERT INTO Facturas(numeroDeFactura, fecha, descripcion, total, idCliente) VALUES(@numeroDeFactura, @fecha, @descripcion, @total, @idCliente)
GO
CREATE PROCEDURE sp_EliminarFacturas @idFactura INT
AS
DELETE FROM Facturas WHERE idFactura = @idFactura
GO
CREATE PROCEDURE sp_ModificarFacturas @idFactura INT, @numeroDeFactura VARCHAR(128), @fecha DATE, @descripcion VARCHAR(128), @total REAL, @idCliente INT
AS
UPDATE Facturas SET numeroDeFactura = @numeroDeFactura, fecha = @fecha, descripcion = @descripcion, total = @total, idCliente = @idCliente WHERE idFactura = @idFactura
GO
--PROCEDIMIENTOS TABLA STOCK
CREATE PROCEDURE sp_ListarStocks
AS
SELECT idStock, stock FROM Stocks ORDER BY idStock
GO
--NUEVO PROCEDIMIENTO LISTAR COMPRAS
CREATE PROCEDURE sp_ComprasListar
AS
SELECT c.idCompra, c.numeroDeCompra, c.fecha, c.descripcion, c.total, c.idProveedor, p.nombre, p.paginaWeb, p.nit, p.contacto FROM Compras c INNER JOIN Proveedores p ON c.idProveedor = p.idProveedor
GO
--PROCEDIMIENTO TABLA USUARIO
CREATE PROCEDURE sp_AutenticarUsuario @usuario VARCHAR(128), @contrasena VARCHAR(128)
AS
SELECT Usuario.idUsuario, Usuario.usuario, Usuario.contrasena from Usuario WHERE Usuario.usuario = @usuario AND Usuario.contrasena = @contrasena
GO
--NUEVO PROCEDIMIENTO LISTAR PRODUCTO
CREATE PROCEDURE sp_ProductoListar
AS
SELECT p.idProducto, p.nombre, p.descripcion, p.precioUnitario, p.precioPorDocena, p.precioPorMayor, p.idEmpaque,
e.descripcion as empaque, p.idCategoria, c.descripcion as categoria, p.idStock, s.stock
FROM Productos p inner join Empaques e on p.idEmpaque = e.idEmpaque inner join Categorias c on p.idCategoria = c.idCategoria
inner join Stocks s on p.idStock = s.idStock 
GO
--NUEVO PROCEDIMIENTO LISTAR FACTURAS
CREATE PROCEDURE sp_FacturasListar
AS
SELECT f.idFactura, f.numeroDeFactura, f.fecha, f.descripcion, f.total, f.idCliente, c.nit, c.dpi, c.nombre
FROM Facturas f INNER JOIN Clientes c on f.idCliente = c.idCliente
GO
--execute sp_ProductoListar

--PROCEDIMIENTO DETALLE COMPRAS
--CREATE PROCEDURE sp_ListarDetalle
--AS
--SELECT d.idDetalle, d.idCompra, c.descripcion, c.fecha, c.idProveedor, c.numeroDeCompra as compra, c.total, d.idProducto, p.descripcion, p.idCategoria, p.idEmpaque, 
--p.idStock, p.nombre as producto, p.precioPorDocena, p.precioPorMayor, p.precioUnitario, d.descripcion, d.cantidad, d.precio, d.totalLinea
--FROM DetalleCompras AS d INNER JOIN Compras c ON d.idCompra = c.idCompra INNER JOIN Productos p ON d.idProducto = p.idProducto
--GO

--SELECT d.idDetalle, d.idFactura, d.lineaNo, d.idProducto, p.nombre, d.cantidad, d.precio, d.totalLinea
--FROM DetalleFacturas as d INNER JOIN Productos as p ON d.idProducto = p.idProducto WHERE d.idFactura = 1
