/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturasporcobrar;

import Dao.GetData;
import Model.FacturaXC;
import Model.Pago;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jpierre
 */
public class SystemMannager {
    
   
    
    int empresa;
    GetData data=new GetData();
    Transacciones transInicial= new Transacciones();
    Transacciones transVer= new Transacciones();
    
    
   public List<FacturaXC> getFacturasConSaldo(){
            
     return  data.getResumenCuentasMes("",0);  
        
   }
     
   public List<Pago> getPagos(){
        List<Pago> pagos=new ArrayList();
        GetData getdata= new GetData();
       pagos= getdata.getPagosSoft(empresa);
       return pagos;
   }
   
   public String[] getTitulos(){
     String[] titulos = {"Nº Transacción", "Fecha Datos", "Nombre Archivo", "Fecha Trans", "Proceso", "Accion"};
     return titulos;
   }
   
  public void transaccionInicial(){
      
     // transInicial.setEmpresa(0);
     empresa=0; 
    
      transInicial.setTitulos(getTitulos());
      transInicial.setLista(getPagos());
      transInicial.llenarTabla();
     // transInicial.llenarTabla(titulos, data.getPagosSoft(transInicial.empresa));
   
     transInicial.getjComboBox1().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }

        });
         transInicial.setVisible(true);
  }
     
     private void jComboBox2ActionPerformed(ActionEvent evt) {
             // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
              System.out.println("siiiiii");
             // empresa= jComboBox3.getSelectedIndex();
          // transInicial.setEmpresa(transInicial.getjComboBox1().getSelectedIndex());
             System.out.println(transInicial.getjComboBox1().getSelectedIndex());   
        // cambia la empresa, tiene  que cambiaar la data de la tabla
             empresa=transInicial.getjComboBox1().getSelectedIndex();
             //pasamos la data segun la empresa y llenamos la tabla
             transInicial.setTitulos(getTitulos());
             transInicial.setLista(getPagos());
             transInicial.llenarTabla();
     }
      
      
   public static void main(String args[]) {
  
     SystemMannager sys= new SystemMannager();
     sys.transaccionInicial();
         
    }
  
  
   
}
