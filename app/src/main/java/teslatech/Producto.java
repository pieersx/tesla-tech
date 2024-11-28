package teslatech;

import java.sql.Date;

public class Producto {
    private Integer id;
    private String productId;
    private String productName;
    private String tipo;
    private Integer stock;
    private Double precio;
    private String estado;
    private String imagen;
    private Date fecha;
    private Integer cantidad;

    public Producto(Integer id, String productId, String productName, String tipo, Integer stock, Double precio, String estado, String imagen, Date fecha) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.tipo = tipo;
        this.stock = stock;
        this.precio = precio;
        this.estado = estado;
        this.imagen = imagen;
        this.fecha = fecha;
    }

    public Producto(Integer id, String productId, String productName, String tipo, Integer cantidad, Double precio, String imagen, Date fecha){
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.tipo = tipo;
        this.precio = precio;
        this.imagen = imagen;
        this.fecha = fecha;
        this.cantidad = cantidad;
    }

    public Integer getId() {
        return id;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getTipo(){
        return tipo;
    }

    public Integer getStock() {
        return stock;
    }

    public Double getPrecio() {
        return precio;
    }

    public String getEstado() {
        return estado;
    }

    public String getImagen() {
        return imagen;
    }

    public Date getFecha() {
        return fecha;
    }

    public Integer getCantidad(){
        return cantidad;
    }
}
