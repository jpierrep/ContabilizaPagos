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
public class Opciones {
    
    private String etiqueta;
    private String valor;
    private boolean hasButton;

    public Opciones(String etiqueta, String valor, boolean hasButton) {
        this.etiqueta = etiqueta;
        this.valor = valor;
        this.hasButton = hasButton;
    }

    public Opciones() {
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public boolean isHasButton() {
        return hasButton;
    }

    public void setHasButton(boolean hasButton) {
        this.hasButton = hasButton;
    }
    
    
    
}
