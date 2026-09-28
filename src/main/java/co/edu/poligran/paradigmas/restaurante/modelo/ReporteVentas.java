package co.edu.poligran.paradigmas.restaurante.modelo;
import java.time.LocalDate;
/** DTO de salida para el reporte de ventas; no requiere DAO porque se calcula. */
public class ReporteVentas { private final LocalDate fechaInicio,fechaFin; private final double ventasTotales; public ReporteVentas(LocalDate i,LocalDate f,double total){fechaInicio=i;fechaFin=f;ventasTotales=total;} public LocalDate getFechaInicio(){return fechaInicio;} public LocalDate getFechaFin(){return fechaFin;} public double getVentasTotales(){return ventasTotales;} }
