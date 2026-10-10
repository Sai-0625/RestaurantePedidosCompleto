package co.edu.poligran.paradigmas.restaurante.negocio;

import co.edu.poligran.paradigmas.restaurante.modelo.*;
import java.time.Duration; import java.time.LocalDate; import java.time.LocalDateTime; import java.util.List;

/** Genera reportes a partir de los pedidos y de su historial. */
public class ReportesManager {
    private final DatosRestaurante datos;
    public ReportesManager(DatosRestaurante datos) { this.datos=datos; }
    public ReporteVentas generarVentas(LocalDate inicio, LocalDate fin) {
        double total=0; for(Pedido pedido:datos.pedidos.listar()) if(pedido.getEstado()==EstadoPedido.ENTREGADO && enRango(pedido.getFecha(),inicio,fin)) total+=pedido.calcularTotal(datos.detalles.listarPorPedido(pedido.getId()));
        return new ReporteVentas(inicio,fin,total);
    }
    public ReporteTiempos generarTiempos(LocalDate inicio, LocalDate fin) {
        int cantidad=0, conPreparacion=0, conEntrega=0; long preparacionTotal=0, entregaTotal=0;
        for(Pedido pedido:datos.pedidos.listar()) if(pedido.getEstado()==EstadoPedido.ENTREGADO && enRango(pedido.getFecha(),inicio,fin)) {
            cantidad++; LocalDateTime pendiente=null, listo=null, entregado=null;
            for(HistorialEstado cambio:datos.historial.listarPorPedido(pedido.getId())) {
                if(cambio.getEstado()==EstadoPedido.PENDIENTE) pendiente=cambio.getFecha();
                if(cambio.getEstado()==EstadoPedido.LISTO) listo=cambio.getFecha();
                if(cambio.getEstado()==EstadoPedido.ENTREGADO) entregado=cambio.getFecha();
            }
            if(pendiente!=null && listo!=null) { preparacionTotal+=Duration.between(pendiente,listo).toMinutes(); conPreparacion++; }
            if(listo!=null && entregado!=null) { entregaTotal+=Duration.between(listo,entregado).toMinutes(); conEntrega++; }
        }
        return new ReporteTiempos(inicio,fin,cantidad,conPreparacion==0?0:(double)preparacionTotal/conPreparacion,conEntrega==0?0:(double)entregaTotal/conEntrega);
    }
    private boolean enRango(LocalDateTime fecha,LocalDate inicio,LocalDate fin){LocalDate d=fecha.toLocalDate();return !d.isBefore(inicio)&&!d.isAfter(fin);}
}
