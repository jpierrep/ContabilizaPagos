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
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author jpierre
 */
public class SystemMannager {
    
   
    
    int empresa;
    GetData data=new GetData();
    Transacciones transInicial= new Transacciones();
    List<FacturaXC> facturasConSaldo= new ArrayList();
     List<Pago> pagos= new ArrayList();
    Transacciones transVer= new Transacciones();
    PanelOpciones panelOpciones; //=new PanelOpciones("aa");
    
    
   public List<FacturaXC> getFacturasConSaldo(){
            
     facturasConSaldo=data.getFacturasConSaldo(empresa);
       return  facturasConSaldo; 
        
   }
   
   public List<Pago> getPagos(){
        
       pagos= data.getPagosSoft(empresa);

       return pagos;
      
     
   }
   
   public String[] getTitulos(String objeto){
     String[] titulos = {"Nº Documento", "Saldo", "Cant. Movim.", "Fecha Emisión", "Cod. Auxiliar", "Rut Auxiliar","Nombre Auxiliar"};
     
     if (objeto.equals("Pago")){  
      titulos= new  String []{"Id Doc.", "Numero Doc.", "Cant. Mov.","Saldo Doc.","Fecha Doc.", "Fecha Pago", "Monto Pago","Marca Id","Marca Desc"};
     }
      return titulos;
   }
   
  public void transaccionInicial(){
      
     // transInicial.setEmpresa(0);
     empresa=0; 
    
      transInicial.setTitulos(getTitulos("Facturas"));
      transInicial.setLista(getFacturasConSaldo()); // aca se llenan las facturas
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
             transInicial.setLista(getFacturasConSaldo());
             transInicial.llenarTabla();
     }
     
     // Action Performed del botón de la tabla principal 
     
     private void jButton1ActionPerformed(ActionEvent evt) {
         //obtenemos los pagos y se llenará la variable pagos
         getPagos();
    //aca analizamos los pagos y añadimos las demás marcas
         calculaMarcasSubtotales();
         
         List<Opciones> listaOpciones=generaOpciones("PreContabiliza");
         panelOpciones=new PanelOpciones("PreContabiliza", listaOpciones);
         
         System.out.println(" se presionó el botón");
      int i=0;
        for(JButton button: panelOpciones.getJbuttons()){
        
            button.addActionListener(listener);  
        i++;
        }
         
           panelOpciones.getButton1().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            jButton1PanelOpc(evt);
               
            }

        });
        
        panelOpciones.setVisible(true);
      //  panelOpciones.setAlwaysOnTop(true);
         
    
         
     }
     
     //Accion del botón del panel de opciones
      private void jButton1PanelOpc(ActionEvent evt) {
   
                                      
      JTextField filename = new JTextField(), dir = new JTextField();
       
          System.out.println("dentro panel system");
        
        List<String> listaArea= new ArrayList(); 
       listaArea.add("002");
       listaArea.add("003");
       listaArea.add("012");
       listaArea.add("014");
     
        
        //se deben generar los archivos solo de las areas cuadradas
        
        
        JFileChooser c = new JFileChooser();
      //disableTextField(c);
      c.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      int rVal = c.showOpenDialog(null);
    
      
      if (rVal == JFileChooser.APPROVE_OPTION) {
     filename.setText(c.getSelectedFile().getName());
     
        // disableTextField(c);

 dir.setText(c.getCurrentDirectory().toString());
         System.out.println(filename.getText());
        System.out.println(dir.getText());// TODO add your handling code here:
        String filepath=dir.getText()+"\\"+filename.getText();
          System.out.println(filepath);
          
          File folder=new File(filepath+"\\"+transInicial.getjComboBox1().getSelectedItem().toString());
          if(!folder.exists()){
              folder.mkdir();}
          filepath=filepath+"\\"+transInicial.getjComboBox1().getSelectedItem().toString();
          
       
       
          try {
             
              for (String area:listaArea){
                  System.out.println(""); 
                  exportarMovimientos(getPagosContab(),filepath,Integer.toString(empresa)+"-"+area);
                           
            
             
              } //fin for
              
              
              
         
              JOptionPane.showMessageDialog(null,"Archivos generado correctamente.","Exito",JOptionPane.INFORMATION_MESSAGE);
          } catch (IOException ex) {
              Logger.getLogger(SystemMannager.class.getName()).log(Level.SEVERE, null, ex);
             JOptionPane.showMessageDialog(null, "Error al generar los archivos. "+ ex.toString(),
  "Error", JOptionPane.ERROR_MESSAGE);
          } catch (ClassNotFoundException ex) {
              Logger.getLogger(SystemMannager.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Error al generar los archivos. "+ ex.toString(),
  "Error", JOptionPane.ERROR_MESSAGE);
          }
          
    }
	
    

    
          
          
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
                muestraTablaProceso(text);
                            
            }
        }
    };
      
          // seguun el nombre del proceso y el id del botón se muestra la tabla correspondiente
          public void  muestraTablaProceso(String id){
              String[] newId=id.split(" ");
              String idBoton=newId[0];      
              String proceso=newId[1];           
                        
              
              if(proceso.equals("PreContabiliza")){
                 
                  //las opciones  de idBoton son:
                   //0 total pagos // no trae el boton activado
                   //1 registros contabilizados
                   //2 registros no contabilizados
                   switch (idBoton){
                       case "1":
 
           llenaTablaVista(getPagosContab(),"Listado Pagos a Contabilizar");
               break;
                       case "2":
                           
    llenaTablaVista(getPagosSinContab(),"Listado Pagos no posibles de contabilizar");
               break;
                        
                       
                   }
                   
                  
              }
              
              
          }
          
   
          
          public List<Opciones> generaOpciones(String proceso){


              List<Opciones> lista=new ArrayList();
       if (proceso.equals("PreContabiliza")){
      
           long  cantTotal = getPagosContab().stream().count()+getPagosSinContab().stream().count();
           long  cantContab = getPagosContab().stream().count();
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
          
          public void llenaTablaVista(List<Pago> lista,String tituloTabla){
                             transVer.setVisible(false);
               transVer.getjComboBox1().setVisible(false);
               transVer.getjButton1().setVisible(false);
               transVer.getjLabel1().setText(tituloTabla);
                transVer.setTitulos(getTitulos("Pago"));
             transVer.setLista(lista);
             transVer.llenarTabla(); 
             transVer.setVisible(true);
              
          }
          
              
               public  void calculaMarcasSubtotales(){
                  
                  //agrupamos por documento AÑADIR SOLO LOS DOCUMENTOS A CONTABILIZAR SEGUN LA MARCA 1 Y 4 y ORDENADOS POR FECHA
                  //agrupamos los  montos de pagos para validar si es posible contabilizar segun la suma
                  List<Pago> nuevoPago=new ArrayList<>();
                   Map<Integer, Integer> sum = getPagosContab().stream().collect(
                Collectors.groupingBy(Pago::getNumDocumento, Collectors.summingInt(Pago::getMontoInt)));
            
                   System.out.println(sum);
              //por cada tipo de documento se tiene la suma de los pagos, validar si corresponde contabilizar segun el saldo de cada uno
              sum.forEach((doc,suma)->{
 
                  
           
                  //obtenemos todos los pagos por documento para validar cual de ellos contabilizar y cuales no
                 List<Pago> p= getPagosContab().stream().filter(  a -> Objects.equals(a.getNumDocumento(),doc)).collect(Collectors.toList());
                  
                 //añadimos un acumulador de pagos para validar que de la lista no se pase, si se pasa el monto del pago
                 //del valor del saldo del documento el pago no será posible contabilizarlo, si el saldo es se marcará también
                 //marca 7  el monto total de pagos para el documento es menor o igual al saldo (se contabiliza)
                 //marca 8  cuando el monto total de pago excede el saldo se utilizara un acumulador, los que esten dentro del rango se contabiliza
        
                 //marca 9  aquellos en que el acumulador este fuera de rango no se contabiliza
                
                 int acumuladorSaldo=0;
                  
                 for (Pago pag:p){
   acumuladorSaldo=acumuladorSaldo+pag.getMontoInt(); 
   //          si el saldo del documento es mayor o igual a la suma de la agrupacion entonces se contabiliza
                     if(pag.getSoftSaldo()>=suma){
                         pag.setMarca(7);
                        //  nuevoPago.add(pag);
                         System.out.println("marca 7");
                      System.out.println("doc: "+doc+" saldo:"+pag.getSoftSaldo()+" acum:"+acumuladorSaldo+" suma:"+suma);
                         //si el pago esta detro del acumulador de saldo
                     }else if(pag.getSoftSaldo()>=acumuladorSaldo){
                         pag.setMarca(8);
                         //aumentamos el acumulador segun el monto del pago
                       //  acumuladorSaldo=acumuladorSaldo+pag.getMontoInt();   
                              System.out.println("marca 8");
                                        System.out.println("doc: "+doc+" saldo:"+pag.getSoftSaldo()+" acum:"+acumuladorSaldo+" suma:"+suma);

                        // nuevoPago.add(pag);
                     }else if (pag.getSoftSaldo()<acumuladorSaldo){
                         //si no el pago no es posible de contabilizar porque no alcanza a estar dentro del monto
        System.out.println("doc: "+doc+" saldo:"+pag.getSoftSaldo()+" acum:"+acumuladorSaldo+" suma:"+suma);
                         System.out.println(" marca 9");
                         pag.setMarca(9);
                        // nuevoPago.add(pag);
                  //   acumuladorSaldo=acumuladorSaldo+pag.getMontoInt();  
                     }
                     
                 }
                  
              }
              
              );
                  System.out.println("dentro de marcas");  


             
          }
          
          
  public  void exportarMovimientos(List<Pago> lista,String path,String nombreArchivo) throws IOException, FileNotFoundException, ClassNotFoundException{
      

 java.util.Date utilDate = new java.util.Date();
 DateFormat df = new SimpleDateFormat("dd-MM-YYYY");
 String fecha=df.format(utilDate);
        
        String realPath; 
        String pathLog;
        if (path==null){
    String sys = System.getProperty("user.home");    
    realPath=sys+"\\"+nombreArchivo+"-"+fecha+".txt"; // Sustituye "/" por el directorio ej: "/upload"
    pathLog=sys+"\\"+"LogProceso"+"-"+fecha+".txt";
        }else{
        realPath=path+"\\"+nombreArchivo+"-"+fecha+".txt";
        pathLog=path+"\\"+"LogProceso"+"-"+fecha+".txt";
        }
  
        
  //El diccionario
 

 
 
        FileWriter fichero = null;
        FileWriter ficheroLog=null;
        PrintWriter pw = null;
        PrintWriter pwLog = null;
        try
        {
            fichero = new FileWriter(realPath);//decir si se sobreescribe
            ficheroLog= new FileWriter(pathLog,true);
          
            pw = new PrintWriter(fichero);
            pwLog = new PrintWriter(ficheroLog);
            
  
            String cuentaBanco;
            if (empresa==0||empresa==2){
                cuentaBanco="10-01-003";
            }else if (empresa==1){
                cuentaBanco="10-01-001";
            }else{
                cuentaBanco="N/A";
            }
            
                        
            
               for (Pago termino: lista){        
                   
 
         
             pw.println(cuentaBanco+",0,"+termino.getMonto()+",PAGO NUMERO PAGO"+termino.getIdPago()+",,,,,,,,,,,,,,AUXILIAR,DP,62,FECHA PAGO,FECHA VENCIMIENTO,FV,"+termino.getNumDocumento()+" ,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,");  
              pw.println("10-01-065,"+termino.getMonto()+",PAGO NUMERO PAGO,,,,,,,,,,,DP,62,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,"); 
                             
            }
            
            
            
            
            
 
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           // Nuevamente aprovechamos el finally para 
           // asegurarnos que se cierra el fichero.
           if (null != fichero)
              fichero.close();
              ficheroLog.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
 
        
    }
            
        public List<Pago> getPagosContab(){
        
         return pagos.stream().filter(
   a -> Objects.equals(a.getMarca(),1)||Objects.equals(a.getMarca(),4)||Objects.equals(a.getMarca(),7)||Objects.equals(a.getMarca(),8)).collect(Collectors.toList()); 
    } 
                public List<Pago> getPagosSinContab(){
        
         return pagos.stream().filter(
   a -> Objects.equals(a.getMarca(),0)||Objects.equals(a.getMarca(),2)||Objects.equals(a.getMarca(),3)||Objects.equals(a.getMarca(),9)).collect(Collectors.toList()); 
    }
            
     
                
                
   public static void main(String args[]) {
  
     SystemMannager sys= new SystemMannager();
     sys.transaccionInicial();
         
    }
  
  
   
}
