package modelo;

public record LineaPedido(String numeroPedido, int platoId, int cantidad, double precioUnitario, String estado) {}