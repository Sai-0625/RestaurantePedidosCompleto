package co.edu.poligran.paradigmas.restaurante.modelo;
import java.time.LocalDate;
/** DTO para métricas de preparación; no requiere DAO porque se calcula desde el historial. */
public class ReporteTiempos { private final LocalDate fechaInicio,fechaFin; private final int numeroPedidos; public ReporteTiempos(LocalDate i,LocalDate f,int n){fechaInicio=i;fechaFin=f;numeroPedidos=n;} public LocalDate getFechaInicio(){return fechaInicio;} public LocalDate getFechaFin(){return fechaFin;} public int getNumeroPedidos(){return numeroPedidos;} }
