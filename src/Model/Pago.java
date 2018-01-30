/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author jpierre
 */
public class Pago {
   public int idDocumento;
   public int numDocumento; 
   public int idPago;
   public String monto;
   public String fecha;
   public int SoftCantMovim;
   public int  SoftSaldo;
   public String SoftMinFecha;
   public int marca;

    public Pago() {
    }

    public int getMarca() {
        return marca;
    }

    public void setMarca(int marca) {
        this.marca = marca;
    }

    public Pago(int idDocumento, int numDocumento, int idPago, String monto, String fecha, int SoftCantMovim, int SoftSaldo, String SoftUltFecha) {
        this.idDocumento = idDocumento;
        this.numDocumento = numDocumento;
        this.idPago = idPago;
        this.monto = monto;
        this.fecha = fecha;
        this.SoftCantMovim = SoftCantMovim;
        this.SoftSaldo = SoftSaldo;
        this.SoftMinFecha = SoftUltFecha;
    }

    public int getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(int idDocumento) {
        this.idDocumento = idDocumento;
    }

    public int getNumDocumento() {
        return numDocumento;
    }

    public void setNumDocumento(int numDocumento) {
        this.numDocumento = numDocumento;
    }

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public String getMonto() {
        return monto;
    }
    public int getMontoInt() {
        return Integer.parseInt(monto);
    }


    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getSoftCantMovim() {
        return SoftCantMovim;
    }

    public void setSoftCantMovim(int SoftCantMovim) {
        this.SoftCantMovim = SoftCantMovim;
    }

    public int getSoftSaldo() {
        return SoftSaldo;
    }

    public void setSoftSaldo(int SoftSaldo) {
        this.SoftSaldo = SoftSaldo;
    }

    public String getSoftMinFecha() {
        return SoftMinFecha;
    }

    public void setSoftMinFecha(String SoftMinFecha) {
        this.SoftMinFecha = SoftMinFecha;
    }
   
   
   public String getMarcaDesc(){
                    String marcaDesc="";
                    switch (marca){
                        case 0: marcaDesc="Saldo Negativo";
                           break;
                        case 1: marcaDesc="Factura Sin Mov.";
                           break;  
                             case 2: marcaDesc="Pago Existente";
                           break;
                        case 3: marcaDesc="Monto Existente";
                           break;  
                             case 4: marcaDesc="Factura con Mov./No reg. Pago";
                           break;
                        case 7: marcaDesc="Contab/Pagos menor que Saldo";
                           break;  
                        case 8: marcaDesc="Contab/Pago dentro de Saldo";
                           break;   
                        case 9: marcaDesc="Contab/Pago fuera de Saldo";
                           break;       
                                
                        
                    }
                    
                    return marcaDesc;
                    
                }
    
    public String getFechaFormat() throws ParseException{
         java.util.Date utilDate = new java.util.Date();
 DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
 Date fechaFormat=df.parse(fecha);
 
 DateFormat dfResult = new SimpleDateFormat("dd/MM/yyyy");
 String fechaString=dfResult.format(fechaFormat);
 return fechaString;
 
    }

}
