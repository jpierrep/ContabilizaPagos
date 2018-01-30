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
public class FacturaXC {
    
    String rutAux;
    String nomAux;
    String codAux;
    String cpbNum;
    String pctCod;
    String cpbFec;
    String ttdCod; //Codigo Tipo
    String movFe;  //Fecha Movimiento
    String movFv; //Fecha Vencimiento 
    String movTipDocRef; //Tipo Movimiento documento
    String movNumDocRef; //Numero Documento referenciado
    String movDebe; //Debe 
    String movHaber; //Haber
    String movGlosa; //Glosa
    String saldo; //Saldo Factura
    String cantMov; //cantidad de movimientos de la factura

    public FacturaXC(String rutAux, String nomAux,String codAux, String movFe, String movNumDocRef, String saldo, String cantMov) {
        this.rutAux = rutAux;
        this.nomAux = nomAux;
        this.codAux=codAux;
        this.movFe = movFe;
        this.movNumDocRef = movNumDocRef;
        this.saldo = saldo;
        this.cantMov = cantMov;
    }

    
    
    public String getRutAux() {
        return rutAux;
    }

    public void setRutAux(String rutAux) {
        this.rutAux = rutAux;
    }

    public String getNomAux() {
        return nomAux;
    }
    
     public String getNomAuxSinComas() {
        return nomAux.replace(",", "");
    }

    public void setNomAux(String nomAux) {
        this.nomAux = nomAux;
    }

    public String getCodAux() {
        return codAux;
    }

    public void setCodAux(String codAux) {
        this.codAux = codAux;
    }

    public String getCpbNum() {
        return cpbNum;
    }

    public void setCpbNum(String cpbNum) {
        this.cpbNum = cpbNum;
    }

    public String getPctCod() {
        return pctCod;
    }

    public void setPctCod(String pctCod) {
        this.pctCod = pctCod;
    }

    public String getCpbFec() {
        return cpbFec;
    }

    public void setCpbFec(String cpbFec) {
        this.cpbFec = cpbFec;
    }

    public String getTtdCod() {
        return ttdCod;
    }

    public void setTtdCod(String ttdCod) {
        this.ttdCod = ttdCod;
    }

    public String getMovFe() {
        return movFe;
    }

    public void setMovFe(String movFe) {
        this.movFe = movFe;
    }

    public String getMovFv() {
        return movFv;
    }

    public void setMovFv(String movFv) {
        this.movFv = movFv;
    }

    public String getMovTipDocRef() {
        return movTipDocRef;
    }

    public void setMovTipDocRef(String movTipDocRef) {
        this.movTipDocRef = movTipDocRef;
    }

    public String getMovNumDocRef() {
        return movNumDocRef;
    }

    public void setMovNumDocRef(String movNumDocRef) {
        this.movNumDocRef = movNumDocRef;
    }

    public String getMovDebe() {
        return movDebe;
    }

    public void setMovDebe(String movDebe) {
        this.movDebe = movDebe;
    }

    public String getMovHaber() {
        return movHaber;
    }

    public void setMovHaber(String movHaber) {
        this.movHaber = movHaber;
    }

    public String getMovGlosa() {
        return movGlosa;
    }

    public void setMovGlosa(String movGlosa) {
        this.movGlosa = movGlosa;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getCantMov() {
        return cantMov;
    }

    public void setCantMov(String cantMov) {
        this.cantMov = cantMov;
    }

    
    public String getAnio(){
        return movFe.substring(1,4);
    }
       
    public String getMes(){
        return movFe.substring(6,2);
    }
    
        public int getDocInt() {
        return Integer.parseInt(movNumDocRef);
    }

}
