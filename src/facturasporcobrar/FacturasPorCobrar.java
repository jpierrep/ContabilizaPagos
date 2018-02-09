/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturasporcobrar;

import Dao.GetData;
import Model.FacPago;
import Model.FacturaXC;
import Model.Opciones;
import Model.Pago;
import com.sun.org.apache.bcel.internal.generic.AALOAD;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author jpierre
 */
public class FacturasPorCobrar {
    
   
    
    int empresa;
    GetData data=new GetData();
    Transacciones transInicial= new Transacciones();
    List<FacturaXC> facturasConSaldo= new ArrayList();
     List<Pago> pagos= new ArrayList();
    Transacciones transVer= new Transacciones();
    Transacciones transPagos=new Transacciones();
    PanelOpciones panelOpciones= new PanelOpciones(); //=new PanelOpciones("aa");
    String duplicates="";
    
    
   public List<FacturaXC> getFacturasConSaldo(){
            
     facturasConSaldo=data.getFacturasConSaldo(empresa);
      getFactDuplicates();
    
         //Sólo si hay duplicados, eliminarlos
       if (!duplicates.equals("")){
      String[] lista= duplicates.split(",");
       for (String l: lista){   
               //quitamos las facturas duplicadas
               facturasConSaldo.removeIf(
                item ->{
                   return ((FacturaXC)item).getMovNumDocRef().equals(l); 
               } );
       }
       }
     
     return  facturasConSaldo; 
        
   }
   
   public List<Pago> getPagos(){
        
       pagos= data.getPagosSoft(empresa);
       
        //Sólo si hay duplicados, eliminarlos
             if (!duplicates.equals("")){
                 
        String[] lista= duplicates.split(",");
           
           for (String l: lista){   
               //quitamos los pagos duplicados
               pagos.removeIf(
                item ->{
                   return ((Pago)item).getNumDocumento() == Integer.parseInt(l); 
               } );
           }        
                       
           }
       GetPagosCompletos();
       
       return pagos;
      
     
   }
   
   public String[] getTitulos(String objeto){
     String[] titulos = {"Nº Documento", "Saldo", "Cant. Movim.", "Fecha Emisión", "Cod. Auxiliar", "Rut Auxiliar","Nombre Auxiliar"};
     
     if (objeto.equals("Pago")){  
      titulos= new  String []{ "Id Pago","Id Doc.", "Numero Doc.", "Cant. Mov.","Saldo Doc.","Fecha Doc.", "Fecha Pago", "Monto Pago","Total Pago","Marca Id","Marca Desc",""};
     }
      return titulos;
   }
   
  public void transaccionInicial(){
      
     // transInicial.setEmpresa(0);
     empresa=0; 
    transInicial.getjProgressBar1().setVisible(false);
      transInicial.setTitulos(getTitulos("Facturas"));
      transInicial.setLista(getFacturasConSaldo()); // aca se llenan las facturas
   
      transInicial.llenarTabla();
     // transInicial.llenarTabla(titulos, data.getPagosSoft(transInicial.empresa));

     transInicial.getjComboBox1().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }

        });
         
         
     transInicial.getjButton1().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }

        });
  
      transInicial.setVisible(true); 
   
  }
  
     //ActionPerformed del label de empresa de la tabla principal
     
     private void jComboBox2ActionPerformed(ActionEvent evt) {
 
             System.out.println(transInicial.getjComboBox1().getSelectedIndex());   
        
           //Si la empresa  
        if(empresa!=transInicial.getjComboBox1().getSelectedIndex()){
            panelOpciones.setVisible(false);
            transVer.setVisible(false);
            
     
        
        // cambia la empresa, tiene  que cambiaar la data de la tabla
        empresa=transInicial.getjComboBox1().getSelectedIndex();
             //pasamos la data segun la empresa y llenamos la tabla
             transInicial.setTitulos(getTitulos("Facturas"));
             transInicial.setLista(getFacturasConSaldo());
             transInicial.llenarTabla();
                }
     }
     
     // Action Performed del botón de la tabla principal 
     
     private void jButton1ActionPerformed(ActionEvent evt) {
         
        
         if (!panelOpciones.isVisible()){
  
        transInicial.getjProgressBar1().setVisible(true);
         //obtenemos los pagos y se llenará la variable pagos
         getPagos(); 
    //aca analizamos los pagos y añadimos las demás marcas
         calculaMarcasSubtotales();         
         
         List<Opciones> listaOpciones=generaOpciones("PreContabiliza");
         panelOpciones=new PanelOpciones("PreContabiliza", listaOpciones);
         
         System.out.println(" se presionó el botón");
        for(JButton button: panelOpciones.getJbuttons()){
        
            button.addActionListener(listener);  
            
      
        }
         
           panelOpciones.getButton1().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            jButton1PanelOpc(evt);
               
            }

        });
          
     transInicial.getjProgressBar1().setVisible(false);
        panelOpciones.setVisible(true);
      //  panelOpciones.setAlwaysOnTop(true);
         
    
         }
     }
     
     //Accion del botón del panel de opciones
      private void jButton1PanelOpc(ActionEvent evt) {
          
    //obtiene los checks desde la vista
          getChecks();                             
      JTextField filename = new JTextField(), dir = new JTextField();
       
          System.out.println("dentro panel system");
        
        List<String> listaArea= getDistinctAreas();
     
        
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
                 List<FacPago> p= getFacPago().stream().filter(  a -> Objects.equals(a.getFactura().getAreaCod(),area)&&(a.getPago().isCkeck())).collect(Collectors.toList());
                 // List<FacPago> p= getFacPago().stream().filter(  a -> Objects.equals(a.getFactura().getAreaCod(),area)).collect(Collectors.toList());
                 // exportarMovimientos(getFacPago(),filepath,Integer.toString(empresa)+"-"+area);
               String nombreArchivo = Integer.toString(empresa)+"-"+area;
               if (area==null)
                   nombreArchivo = Integer.toString(empresa)+"-SinArea"; 
                     
                 if (!p.isEmpty())
                 exportarMovimientos(p,filepath,nombreArchivo) ;        
                 
                
             
              } //fin for
              
              
              
         
              JOptionPane.showMessageDialog(null,"Archivos generado correctamente.","Exito",JOptionPane.INFORMATION_MESSAGE);
          } catch (IOException ex) {
              Logger.getLogger(FacturasPorCobrar.class.getName()).log(Level.SEVERE, null, ex);
             JOptionPane.showMessageDialog(null, "Error al generar los archivos. "+ ex.toString(),
  "Error", JOptionPane.ERROR_MESSAGE);
          } catch (ClassNotFoundException ex) {
              Logger.getLogger(FacturasPorCobrar.class.getName()).log(Level.SEVERE, null, ex);
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
          
          
  //Listener de doble click de la tabla para revisar opciones
   private void rowPressedTable(int row) {
       // en este instante debemos obtener el id del pago mostrado por transVer
       // tiene que estar en la columna 0
      int idPago=Integer.parseInt(transVer.getTableModel().getValueAt(row,0).toString());
       System.out.println("el id pago seleccionado es"+idPago);
         
       //Llenar la tabla con los pagos correspondientes al id Solicitado
       transPagos.setTitulos(getTitulos("Pago"));
      transPagos.setLista(pagos.stream().filter(  a -> a.getIdPago()==idPago).collect(Collectors.toList())); // aca se llenan las facturas
   
      transPagos.llenarTabla();
      transPagos.setVisible(true);
            
       
                   }
          
          
      
          // seguun el nombre del proceso y el id del botón se muestra la tabla correspondiente
          public void  muestraTablaProceso(String id){
              String[] newId=id.split(" ");
              String idBoton=newId[0];      
              String proceso=newId[1];           
                        
              
              if(proceso.equals("PreContabiliza")){
                 
                  //las opciones  de idBoton son:
                   //0 total pagos // no trae el boton activado
                   //1 registros contabilizados91
                   //2 registros no contabilizados
                   switch (idBoton){
                       case "1":
 
           llenaTablaVista(getDistinctPagos(getPagosContab()),"Listado Pagos a Contabilizar","DISTINCT_PAGO");
               break;
                       case "2":
                           
    llenaTablaVista(getDistinctPagos(getPagosSinContab()),"Listado Pagos no posibles de contabilizar","DISTINCT_PAGO");
               break;
                        
                       
                   }
                   
                  
              }
              
              
          }
          
   
          
          public List<Opciones> generaOpciones(String proceso){


              List<Opciones> lista=new ArrayList();
       if (proceso.equals("PreContabiliza")){
      
          long  cantTotalPagos = getDistinctPagos(getPagosContab()).stream().count()+getDistinctPagos(getPagosSinContab()).stream().count();
           long  cantContabPagos = getDistinctPagos(getPagosContab()).stream().count();
           long cantNoContabPagos=cantTotalPagos-cantContabPagos;
           
           long  cantTotalFact = getPagosContab().stream().count()+getPagosSinContab().stream().count();
           long  cantContabFact = getPagosContab().stream().count();
           long cantNoContabFact=cantTotalFact-cantContabFact;
      Opciones op1=new Opciones("Total Pagos: ", cantTotalPagos+"",false);
      Opciones op2=new Opciones("Cantidad Contabilizar:",cantContabPagos+"",true);
      Opciones op3=new Opciones("Cantidad No Contabilizar:",cantNoContabPagos+"",true);
      lista.add(op1);
       lista.add(op2);
        lista.add(op3);
          
      }
       return lista;       
   }
          
          public void llenaTablaVista(List<Pago> lista,String tituloTabla,String identificador){
             
               
               transVer.setVisible(false);
               transVer.getjComboBox1().setVisible(false);
               transVer.getjButton1().setVisible(false);
               transVer.getjLabel1().setText(tituloTabla);
               transVer.setIdentificador(identificador);
               transVer.setTitulos(getTitulos("Pago"));
               transVer.setLista(lista);
               //llenamos con el total
               transVer.getjLabel2().setText("Monto total: "+ Integer.toString((lista.stream().mapToInt(i -> i.getMontoPagoTotalInt()).sum())));
               transVer.getjLabel2().setVisible(true);
               transVer.llenarTabla(); 
               transVer.setVisible(true);
               
               transVer.getjTable1().      //Listener de los pagos de la tabla
           
        addMouseListener(new MouseAdapter() {
    public void mousePressed(MouseEvent mouseEvent) {
        JTable table =(JTable) mouseEvent.getSource();
        Point point = mouseEvent.getPoint();
        int row = table.rowAtPoint(point);
        if (mouseEvent.getClickCount() == 2) {
            // your valueChanged overridden method 
           // System.out.println(" holaa");
          //  System.out.println("row "+row);
          rowPressedTable(row);   
            
        }
    }

               });
              
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
                //Saldo negativo no se contabiliza
                     if (pag.getSoftSaldo()<0){
                      pag.setMarca(0);
                      //          si el saldo del documento es mayor o igual a la suma de la agrupacion entonces se contabiliza
                     } else if (pag.getSoftSaldo()>=suma){
                        
                             pag.setMarca(7);
                        //  nuevoPago.add(pag);
                     //    System.out.println("marca 7");
                     // System.out.println("doc: "+doc+" saldo:"+pag.getSoftSaldo()+" acum:"+acumuladorSaldo+" suma:"+suma);
                         //si el pago esta detro del acumulador de saldo
                     }else if(pag.getSoftSaldo()>=acumuladorSaldo){
                         pag.setMarca(8);
                         //aumentamos el acumulador segun el monto del pago
                       //  acumuladorSaldo=acumuladorSaldo+pag.getMontoInt();   
                      //        System.out.println("marca 8");
                      //                  System.out.println("doc: "+doc+" saldo:"+pag.getSoftSaldo()+" acum:"+acumuladorSaldo+" suma:"+suma);

                        // nuevoPago.add(pag);
                     }else if (pag.getSoftSaldo()<acumuladorSaldo){
                         //si no el pago no es posible de contabilizar porque no alcanza a estar dentro del monto
     //   System.out.println("doc: "+doc+" saldo:"+pag.getSoftSaldo()+" acum:"+acumuladorSaldo+" suma:"+suma);
    //                     System.out.println(" marca 9");
                         pag.setMarca(9);
                        // nuevoPago.add(pag);
                  //   acumuladorSaldo=acumuladorSaldo+pag.getMontoInt();  
                     }
                     
                 }
                  
              }
              
              );
                  System.out.println("dentro de marcas");  


             
          }
          
          
  public  void exportarMovimientos(List<FacPago> lista,String path,String nombreArchivo) throws IOException, FileNotFoundException, ClassNotFoundException{
      

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

            //Obtenemos fecha actual para el log
            
            Calendar cal = Calendar.getInstance();
             SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
             String fechaActual=sdf.format(cal.getTime());
            
             // Si existen duplicados crearemos el log
            if(!duplicates.equals(""))    
            pwLog.println("Fecha:"+fechaActual+", Empresa:"+ empresa+", Facturas duplicadas (Nº): " +duplicates);
        
            String cuentaBanco;
            if (empresa==0||empresa==2){
                cuentaBanco="10-01-003";
            }else if (empresa==1){
                cuentaBanco="10-01-001";
            }else{
                cuentaBanco="N/A";
            }
            
                        
            
               for (FacPago termino: lista){        
                   
 
         
             pw.println("10-01-065,0,"+termino.getPago().getMonto()+","+termino.getFactura().getNomAuxSinComas()+" [P: "+termino.getPago().getIdPago()+" D: "+termino.getPago().getIdDocumento()+"],,,,,,,,,,,,,,,"+termino.getFactura().getCodAux()+",DP,62,"+termino.getPago().getFechaFormat()+ ","+termino.getPago().getFechaFormat()+",FV,"+termino.getPago().getNumDocumento()+" ,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,");  
              pw.println(cuentaBanco+","+termino.getPago().getMonto()+",0,"+termino.getFactura().getNomAuxSinComas()+",,,,,,,,,,,,,,,,DP,62,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,"); 
                             
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
   a -> (Objects.equals(a.getMarca(),1)||Objects.equals(a.getMarca(),4)||Objects.equals(a.getMarca(),7)||Objects.equals(a.getMarca(),8))&&a.getEsPagoCompleto()==true).collect(Collectors.toList()); 
    } 
                public List<Pago> getPagosSinContab(){
        
         return pagos.stream().filter(
                 
                 // el pago con marca 2 no se contabiliza pues ya existe (esta contabilizado, pero no es necesario mostrar)
   a -> Objects.equals(a.getMarca(),0)||Objects.equals(a.getMarca(),3)||Objects.equals(a.getMarca(),9)||(a.getEsPagoCompleto()==false&&!Objects.equals(a.getMarca(),2))).collect(Collectors.toList()); 
    }
      
                        public List<String> getDistinctAreas(){
        
         return facturasConSaldo.stream().map(x -> x.getAreaCod()).distinct().collect(Collectors.toList());
    } 
                
       public List<FacPago> getFacPago(){
          Map<Integer, FacturaXC> ownersById = facturasConSaldo.stream()
   .collect(Collectors.toMap(k -> k.getDocInt(), k -> k)); 
          
          List<FacPago> results = getPagosContab().stream()
   .map(car -> new FacPago(car, ownersById.get(car.getNumDocumento())))
   .collect(Collectors.toList());
      
          return results;
       }
       
       public void getChecks(){
          int a=0;
           for(Pago p:getPagosContab()){
               p.setCkeck((Boolean)transVer.getTableModel().getValueAt(a,9));
               System.out.println((Boolean)transVer.getTableModel().getValueAt(a,9));
            a++;   
           }
           
           
           
       }
       
       public void getFactDuplicates(){
           
//           FacturaXC fact= new FacturaXC("asd", "asd", "asd", "208429", "100", "4");
//           FacturaXC fact2= new FacturaXC("asd", "asd", "asd", "208534", "100", "4");
//           facturasConSaldo.add(fact);
//            facturasConSaldo.add(fact2);
           
              Map<Integer, Long> countIdDocs = facturasConSaldo.stream().collect(
                
                   
Collectors.groupingBy(FacturaXC::getDocInt, Collectors.counting()));
              
         //Entrega un string con todos los documentos duplicados >1, separados por coma  
      duplicates = countIdDocs.entrySet().stream() .filter(entry -> entry.getValue()>1).map(map -> Integer.toString(map.getKey())).collect(Collectors.joining(","));
           System.out.println("Duplicates "+duplicates);
           //encontrados los documentos repetidos en la lista generamos log y eliminamos de las listas de pagos y Facturas
           
       }
       
       
       //Evalua que los pagos por documento coincidan con el monto de los pagos
      public void GetPagosCompletos(){

            

                   Map<Integer,Map<Integer, Integer>> sum = pagos.stream().collect(
                   Collectors.groupingBy(Pago::getIdPago,Collectors.groupingBy(Pago::getMontoPagoTotalInt,
                           Collectors.summingInt(Pago::getMontoInt))));
            

                   sum.forEach((idPago,suma)->{
                       System.out.println(idPago+" map"+suma.toString());
                       int montoTotal= suma.keySet().stream().findFirst().get();
                       int montoPago= suma.values().stream().findFirst().get();
               
                       //seteamos true si el monto del pago esta completo y false si no ademas seteamos
                       //el valor del pago posible según los pagos obtenidos y que aplicarian a contabilizar
                       for(Pago p:pagos){
                           if(p.getIdPago()==idPago){
                               if(montoPago==montoPago){
                                 p.setEsPagoCompleto(true);
                               }
                             
                               p.setMontoPagoPosible(montoPago+""); 
                           }

                       }
                       

             //  if (montoTotal==montoPago)
                           
//                   for(Pago p:  pagos.stream().filter(  a -> Objects.equals(a.getIdPago(),idPago)).collect(Collectors.toList())){
//                            p.setEsPagoCompleto(true);
//                            //seteamos el monto del pago posible
//                            p.setMontoPagoPosible(montoPago+"");
//                    }
                   
                   }
              
              );
//                 for(Pago p: pagos){
//                     System.out.println(p.getIdPago()+" "+  p.getEsPagoCompleto());
//                 } 
          

          
          
          
      } 
      
      public List<Pago> getDistinctPagos(List<Pago> pagos){
              
        
//                           public List<String> getDistinctAreas(){
//        
//         return facturasConSaldo.stream().map(x -> x.getAreaCod()).distinct().collect(Collectors.toList());
//    } 
          
       List<String> distinctPagosString = pagos.stream().map(x -> 
      x.getIdPago()+","+x.getNumero()+ ","+x.getMontoPagoTotal()+","+x.getMontoPagoPosible()+","+x.getFechaGral()+","+x.getTipo()
               +","+x.getCodigoCliente()+","+x.getRutCliente()
       ).distinct().collect(Collectors.toList());
       List<Pago> distinctPagos=new ArrayList<>();
        for (String p: distinctPagosString){
           Pago nuevoPago= new Pago();
           
           nuevoPago.setIdPago( Integer.parseInt(p.split(",")[0]));
           nuevoPago.setNumero(p.split(",")[1]); 
           nuevoPago.setMontoPagoTotal(p.split(",")[2]);
           nuevoPago.setMontoPagoPosible(p.split(",")[3]);
           nuevoPago.setFechaGral(p.split(",")[4]);
           nuevoPago.setTipo(p.split(",")[5]);
           nuevoPago.setCodigoCliente(p.split(",")[6]);
           nuevoPago.setRutCliente(p.split(",")[7]);
           
           distinctPagos.add(nuevoPago);
                  
       }
        
       
       return distinctPagos;
      }
       
       

                
   public static void main(String args[]) {
  
     FacturasPorCobrar sys= new FacturasPorCobrar();
     sys.transaccionInicial(); 
     
    }
  
  
   
}
