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
import java.util.stream.Collectors;
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
    PanelOpciones panelOpciones; //=new PanelOpciones("aa");
    
    
   public List<FacturaXC> getFacturasConSaldo(){
            
     return  data.getResumenCuentasMes("",0);  
        
   }
     
   public List<Pago> getPagos(){
        List<Pago> pagos=new ArrayList();
        GetData getdata= new GetData();
       pagos= getdata.getPagosSoft(empresa);

       return pagos;
      
     
   }
   
   public String[] getTitulos(String objeto){
     String[] titulos = {"Nº Transacción", "Fecha Datos", "Nombre Archivo", "Fecha Trans", "Proceso", "Accion"};
     
     if (objeto.equals("Pago")){  
      titulos= new  String []{"Nº Transacción", "Fecha Datos", "Nombre Archivo", "Fecha Trans", "Proceso", "Accion"};
     }
      return titulos;
   }
   
  public void transaccionInicial(){
      
     // transInicial.setEmpresa(0);
     empresa=0; 
    
      transInicial.setTitulos(getTitulos("Facturas"));
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
  
     //ActionPerformed del label de empresa de la tabla principal
     
     private void jComboBox2ActionPerformed(ActionEvent evt) {
             // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
              System.out.println("siiiiii");
             // empresa= jComboBox3.getSelectedIndex();
          // transInicial.setEmpresa(transInicial.getjComboBox1().getSelectedIndex());
             System.out.println(transInicial.getjComboBox1().getSelectedIndex());   
        // cambia la empresa, tiene  que cambiaar la data de la tabla
             empresa=transInicial.getjComboBox1().getSelectedIndex();
             //pasamos la data segun la empresa y llenamos la tabla
             transInicial.setTitulos(getTitulos("Facturas"));
             transInicial.setLista(getPagos());
             transInicial.llenarTabla();
     }
     
     // Action Performed de la tabla principal 
     
     private void jButton1ActionPerformed(ActionEvent evt) {
         List<Opciones> listaOpciones=generaOpciones("PreContabiliza");
         panelOpciones=new PanelOpciones("PreContabiliza", listaOpciones);
         
         System.out.println(" se presionó el botón");
      int i=0;
        for(JButton button: panelOpciones.getJbuttons()){
        
            button.addActionListener(listener);  
        i++;
        }
         panelOpciones.setVisible(true);
         
     }
     
      // Listener de botones en PanelOpciones
          ActionListener listener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() instanceof JButton) {
                //el campo text traerá el id del botón y el proceso, con eso sabremos a que proceso estamos llamando
                String text = ((JButton) e.getSource()).getName();
                System.out.println("hola"+ text);
               // si presionan un boton ver mostramos la tabla que corresponda según el id del boton y el proceso
                muestraTabla(text);
                            
            }
        }
    };
      
          // seguun el nombre del proceso y el id del botón se muestra la tabla correspondiente
          public void  muestraTabla(String id){
              String[] newId=id.split(" ");
              String idBoton=newId[0];      
              String proceso=newId[1];           
                        
              
              if(proceso.equals("PreContabiliza")){
                 List<Pago> cantContab;
                  //las opciones  de idBoton son:
                   //0 total pagos // no trae el boton activado
                   //1 registros contabilizados
                   //2 registros no contabilizados
                   switch (idBoton){
                       case "1":
                           cantContab = ((List<Pago>)transInicial.getLista()).stream().filter(
   a -> Objects.equals(a.getMarca(),1)||Objects.equals(a.getMarca(),4)).collect(Collectors.toList()); 
               transVer.setVisible(false);        
                transVer.setTitulos(getTitulos("Pago"));
             transVer.setLista(getPagos());
             transVer.llenarTabla(); 
             transVer.setVisible(true);
               break;
                       case "2":
                           
      cantContab = ((List<Pago>)transInicial.getLista()).stream().filter(
   a -> Objects.equals(a.getMarca(),3)).collect(Collectors.toList()); 
               transVer.setVisible(false);        
                transVer.setTitulos(getTitulos("Pago"));
             transVer.setLista(getPagos());
             transVer.llenarTabla(); 
             transVer.setVisible(true);
               break;
                        
                       
                   }
                   
                  
              }
              
              
          }
          
   
          
          
          public List<Opciones> generaOpciones(String proceso){

       List<Opciones> lista=new ArrayList();
       if (proceso.equals("PreContabiliza")){
      
           long  cantTotal = ((List<Pago>)transInicial.getLista()).stream().filter(
   a -> Objects.equals(a.getMarca(), 1)||Objects.equals(a.getMarca(), 3)||Objects.equals(a.getMarca(), 4)).count();
    long  cantContab = ((List<Pago>)transInicial.getLista()).stream().filter(
   a -> Objects.equals(a.getMarca(),1)||Objects.equals(a.getMarca(),4)).count();
    long cantNoContab=cantTotal-cantContab;
      Opciones op1=new Opciones("Total Pagos: ",cantTotal+"",false);
      Opciones op2=new Opciones("Cantidad Contabilizar:",cantContab+"",true);
      Opciones op3=new Opciones("Cantidad No Contabilizar:",cantNoContab+"",true);
      lista.add(op1);
       lista.add(op2);
        lista.add(op3);
          
      }
       return lista;       
   }
            
          
   public static void main(String args[]) {
  
     SystemMannager sys= new SystemMannager();
     sys.transaccionInicial();
         
    }
  
  
   
}
