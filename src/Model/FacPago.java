/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author jpierre
 */
public class FacPago {
   private Pago pago;
   private FacturaXC factura;

    public FacPago(Pago pago, FacturaXC factura) {
        this.pago = pago;
        this.factura = factura;
    }

    public FacPago() {
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    public FacturaXC getFactura() {
        return factura;
    }

    public void setFactura(FacturaXC factura) {
        this.factura = factura;
    }
   
}
