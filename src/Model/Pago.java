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
   //fecha de la tabla registro Enlaze
   public String fecha;
   //fecha de la tabla registro DocPago
   public String fechaGral;
   public String tipo;
   public String numero;
   public String rutCliente;
   public String codigoCliente;
   
   public int SoftCantMovim;
   public int  SoftSaldo;
   public String SoftMinFecha;
   public int marca;
   //check que dice si contabilizar o no realmente
   public boolean ckeck=true;
   //corresponde al monto total segun id pago
   public String montoPagoTotal;
   //corresponde al monto del pago posible de contabilizar
   public String montoPagoPosible;
   public boolean esPagoCompleto=false;
   //Cuando un pago tiene varias facturas, se añade acá el listado separado por coma
   public String ListadoFacturas;
   public String glosa;

    public Pago() {
    }

    public int getMarca() {
        return marca;
    }

    public void setMarca(int marca) {
        this.marca = marca;
    }

    public Pago(int idDocumento, int numDocumento, int idPago, String monto, String fecha, int SoftCantMovim, int SoftSaldo, String SoftUltFecha, String montoPagoTotal) {
       
        this.idDocumento = idDocumento;
        this.numDocumento = numDocumento;
        this.idPago = idPago;
        this.monto = monto;
        this.fecha = fecha;
        this.SoftCantMovim = SoftCantMovim;
        this.SoftSaldo = SoftSaldo;
        this.SoftMinFecha = SoftUltFecha;
        this.montoPagoTotal=montoPagoTotal;
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

    public boolean isCkeck() {
        return ckeck;
    }

    public void setCkeck(boolean ckeck) {
        this.ckeck = ckeck;
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

    public String getMontoPagoTotal() {
        return montoPagoTotal;
    }
    
        public int getMontoPagoTotalInt() {
        return Integer.parseInt(montoPagoTotal);
    }

    public void setMontoPagoTotal(String montoPagoTotal) {
        this.montoPagoTotal = montoPagoTotal;
    }

    public boolean getEsPagoCompleto() {
        return esPagoCompleto;
    }

    public void setEsPagoCompleto(boolean esPagoCompleto) {
        this.esPagoCompleto = esPagoCompleto;
    }

    public String getMontoPagoPosible() {
        return montoPagoPosible;
    }
      public int getMontoPagoPosibleInt() {
        return Integer.parseInt(montoPagoPosible);
    }

    public void setMontoPagoPosible(String montoPagoPosible) {
        this.montoPagoPosible = montoPagoPosible;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String Numero) {
        this.numero = Numero;
    }

    public String getFechaGral() {
        return fechaGral;
    }

    public void setFechaGral(String fechaGral) {
        this.fechaGral = fechaGral;
    }

    public String getRutCliente() {
        return rutCliente;
    }

    public void setRutCliente(String rutCliente) {
        this.rutCliente = rutCliente;
    }

    public String getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    
    public String getTipoPagoDesc(){
        String tipoPagoDesc="";
   int tipoInt=Integer.parseInt(tipo);
        
        switch (tipoInt){
            
          
 case  1:  tipoPagoDesc= "Cheque";
 break;
 case  2:  tipoPagoDesc= "Efectivo";
  break;
 case  3:  tipoPagoDesc= "Tarjeta de Crédito";
  break;
 case  4:  tipoPagoDesc= "Nota de Crédito";
  break;
 case  5:  tipoPagoDesc= "Cheque Fiscal";
  break;
 case  6:  tipoPagoDesc= "Depósito Directo";
  break;
 case  7:  tipoPagoDesc= "Ajuste Positivo";
  break;
 case  8:  tipoPagoDesc= "Castigo";
  break;
 case  9:  tipoPagoDesc= "Vale Vista";
  break;
 case  10:  tipoPagoDesc= "Servipag";
  break;      
        }
        
        
        return tipoPagoDesc;
        
        
    }

    public String getListadoFacturas() {
        return ListadoFacturas;
    }

    public void setListadoFacturas(String ListadoFacturas) {
        this.ListadoFacturas = ListadoFacturas;
    }

    public String getGlosa() {
        return glosa;
    }

    public void setGlosa(String glosa) {
        this.glosa = glosa;
    }


    
    
    
}
