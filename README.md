# RestaurantePedidosCompleto

Proyecto Java en memoria basado en el diagrama de pedidos. Incluye las interfaces DAO y sus implementaciones para Usuario, Categoría, Producto, Mesa, Pedido, DetallePedido e HistorialEstado. `Domiciliario` hereda de `Usuario`, por lo que se registra mediante `UsuarioDAO` con el rol `DOMICILIARIO`.

El proyecto se divide en `UsuariosManager`, `CatalogoManager`, `MesasManager`, `PedidosManager` y `ReportesManager`. Todos comparten `DatosRestaurante`, por lo que están integrados en un único programa. El menú de consola está en `RestauranteApp`.

Ejecuta `mvn compile exec:java` desde la carpeta del proyecto.

Al iniciar, se cargan datos locales de prueba: 3 categorías, 6 productos, 3 mesas y usuarios de cada rol. Credenciales de ejemplo: `mesero` / `1234`, `cocina` / `1234`, `domiciliario` / `1234` y `admin` / `1234`.
