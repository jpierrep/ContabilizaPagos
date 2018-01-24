/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturasporcobrar;

import Dao.GetData;
import Model.FacturaXC;
import Model.Opciones;
import Model.Pago;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author jpierre
 */
public class SystemMannager {
    
   
    
    int empresa;
    GetData data=new GetData();
    Transacciones transInicial= new Transacciones();
    Transacciones transVer= new Transacciones();
    PanelOpciones panelOpciones=new PanelOpciones("aa");
    
    
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
   
     transInicial.getjComboBox1().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }

        });
         transInicial.setVisible(true);
         
     transInicial.getjButton1().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }

        });
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
     
     private void jButton1ActionPerformed(ActionEvent evt) {
         System.out.println(" se presionó el botón");
      int i=0;
        for(JButton button: panelOpciones.getJbuttons()){
        
            button.addActionListener(listener);  
        i++;
        }
         panelOpciones.setVisible(true);
         
     }
     
     
          ActionListener listener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() instanceof JButton) {
                String text = ((JButton) e.getSource()).getName();
                System.out.println("hola"+ text);
            
            }
        }
    };

   public List<Opciones> generaOpciones(String proceso){
   long  cantContabilizar = ((List<Pago>)transInicial.getLista()).stream().filter(
   a -> Objects.equals(a.getMarca(), 1)||Objects.equals(a.getMarca(), 2)

   ).count();
       List<Opciones> lista=new ArrayList();
       if (proceso.equals("PreContabiliza")){
          Opciones op1=new Opciones("Total Pagos",cantContabilizar+"",true);
          
          
      }
       return lista;       
   }
            
          
   public static void main(String args[]) {
  
     SystemMannager sys= new SystemMannager();
     sys.transaccionInicial();
         
    }
  
  
   
}
