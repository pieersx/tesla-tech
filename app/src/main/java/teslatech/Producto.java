package teslatech;

import java.sql.Date;

public class Producto {
    private Integer id;
    private String productId;
    private String productName;
    private String type;
    private Integer stock;
    private Double price;
    private String status;
    private String image;
    private Date fecha;
    private Integer quantity;

    public Producto(Integer id, String productId, String productName, String type, Integer stock, Double price, String status, String image, Date fecha) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.type = type;
        this.stock = stock;
        this.price = price;
        this.status = status;
        this.image = image;
        this.fecha = fecha;
    }

    public Producto(Integer id, String productId, String productName, String type, Integer quantity, Double price, String image, Date fecha){
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.type = type;
        this.price = price;
        this.image = image;
        this.fecha = fecha;
        this.quantity = quantity;
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

    public String getType(){
        return type;
    }

    public Integer getStock() {
        return stock;
    }

    public Double getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public String getImage() {
        return image;
    }

    public Date getFecha() {
        return fecha;
    }

    public Integer getQuantity(){
        return quantity;
    }
}
