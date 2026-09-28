# RestaurantePedidosCompleto

Proyecto Java en memoria basado en el diagrama de pedidos. Incluye las interfaces DAO y sus implementaciones para Usuario, Categoría, Producto, Mesa, Pedido, DetallePedido e HistorialEstado. `Domiciliario` hereda de `Usuario`, por lo que se registra mediante `UsuarioDAO` con el rol `DOMICILIARIO`.

La clase `RestauranteManager` es la capa que debe usar la interfaz de consola o gráfica; evita que la UI llame directamente a los DAO.
