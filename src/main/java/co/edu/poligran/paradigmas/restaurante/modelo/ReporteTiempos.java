package co.edu.poligran.paradigmas.restaurante.modelo;

import java.time.LocalDate;

/** Resultado del cálculo de tiempos promedio de preparación y entrega. */
public class ReporteTiempos {
    private final LocalDate fechaInicio, fechaFin;
    private final int numeroPedidos;
    private final double promedioPreparacionMinutos, promedioEntregaMinutos;
    public ReporteTiempos(LocalDate inicio, LocalDate fin, int pedidos, double preparacion, double entrega) {
        fechaInicio=inicio; fechaFin=fin; numeroPedidos=pedidos; promedioPreparacionMinutos=preparacion; promedioEntregaMinutos=entrega;
    }
    public LocalDate getFechaInicio(){return fechaInicio;} public LocalDate getFechaFin(){return fechaFin;}
    public int getNumeroPedidos(){return numeroPedidos;} public double getPromedioPreparacionMinutos(){return promedioPreparacionMinutos;} public double getPromedioEntregaMinutos(){return promedioEntregaMinutos;}
}
