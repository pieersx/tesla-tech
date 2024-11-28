package teslatech;

import java.util.Date;

public class Cliente {
    private Integer id;
    private Integer customerID;
    private Double total;
    private Date fecha;
    private String emUsername;

    public Cliente(Integer id, Integer customerID, Double total, Date fecha, String emUsername) {
        this.id = id;
        this.customerID = customerID;
        this.total = total;
        this.fecha = fecha;
        this.emUsername = emUsername;
    }

    public Integer getId() {
        return id;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public Double getTotal() {
        return total;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getEmUsername() {
        return emUsername;
    }
}
