# RestaurantePedidosCompleto

Proyecto Java en memoria basado en el diagrama de pedidos. Incluye las interfaces DAO y sus implementaciones para Usuario, Categoría, Producto, Mesa, Pedido, DetallePedido e HistorialEstado. `Domiciliario` hereda de `Usuario`, por lo que se registra mediante `UsuarioDAO` con el rol `DOMICILIARIO`.

El proyecto se divide en `UsuariosManager`, `CatalogoManager`, `MesasManager`, `PedidosManager` y `ReportesManager`. Todos comparten `DatosRestaurante`, por lo que están integrados en un único programa. El menú de consola está en `RestauranteApp`.

## Ejecución

Requiere Java 11 o superior y Maven. Desde la carpeta del proyecto:

```bash
mvn compile exec:java
```

## Datos de prueba

Al iniciar se cargan datos locales con IDs fijos (`DatosPrueba`), y el programa muestra una guía con ellos:

- **Usuarios** (contraseña `1234` para todos): 1 `mesero1` (Ana, mesero), 2 `mesero2` (David, mesero), 3 `cocina` (Luis), 4 `domiciliario` (Sofía), 5 `admin` (Carlos).
- **Categorías:** 1 Entradas, 2 Platos fuertes, 3 Bebidas.
- **Productos:** 1 Empanadas, 2 Nachos, 3 Hamburguesa, 4 Pizza, 5 Pasta, 6 Limonada, 7 Gaseosa, 8 Agua, 9 Café.
- **Mesas:** IDs 1 a 5.
- **Pedidos:** 1 pendiente (salón), 2 listo (salón), 3 en camino (domicilio), 4 entregado (para llevar), 5 cancelado.

## Flujo de un pedido

`PENDIENTE` → `EN_PREPARACION` → `LISTO` → `ENTREGADO`. Los pedidos a domicilio pasan por `EN_CAMINO` al asignar un domiciliario. Un pedido solo se puede cancelar en `PENDIENTE` o `EN_PREPARACION`, y solo admite productos nuevos en esos mismos estados. No puede pasar a preparación sin productos.
