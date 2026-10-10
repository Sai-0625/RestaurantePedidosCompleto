# RestaurantePedidosCompleto

Proyecto Java en memoria basado en el diagrama de pedidos. Incluye las interfaces DAO y sus implementaciones para Usuario, Categoría, Producto, Mesa, Pedido, DetallePedido e HistorialEstado. `Domiciliario` hereda de `Usuario`, por lo que se registra mediante `UsuarioDAO` con el rol `DOMICILIARIO`.

El proyecto se divide en `UsuariosManager`, `CatalogoManager`, `MesasManager`, `PedidosManager` y `ReportesManager`. Todos comparten `DatosRestaurante`, por lo que están integrados en un único programa. El menú de consola está en `RestauranteApp`.

Ejecuta `mvn compile exec:java` desde la carpeta del proyecto.
