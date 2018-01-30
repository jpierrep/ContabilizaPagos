/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;


import Model.FacturaXC;
import Model.Pago;
import java.sql.BatchUpdateException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator;

/**
 *
 * @author jean
 */
public class GetData extends Dao {
    
    


     

     
     
//         public List<FacturaXC> getResumenCuentasMes(String fecha,int empresa){
//             
//        
//    
//        Statement stmt = null;
//        ResultSet rs = null;      
//      List<FacturaXC> lista= new ArrayList();   
//    try{
//           
//   
//        this.Conectar();
//      
//        stmt = this.con.createStatement();
//      //      rs = stmt.executeQuery("SELECT idTest FROM relato WHERE test_idTest ='"+rutExaminado+"' ORDER BY idTest ASC");
//            // rs = stmt.executeQuery("SELECT idRelato FROM relato WHERE test_idTest =511");
//                String queryString = "   select \n" +
//"  aux.RutAux\n" +
//"  ,aux.NomAux\n" +
//"  ,movNumDocRef\n" +
//"  ,SUM(MovDebe-MovHaber) as Saldo\n" +
//"  ,min(mov.movFe) as Fecha\n" +
//"  ,(select COUNT(*) from  [GUARD].[softland].[cwmovim] as movi left join guard.softland.cwcpbte as compr on compr.CpbNum=movi.CpbNum \n" +
//"   where compr.CpbEst='V' and  movi.CpbMes!='00' and PctCod='10-01-065' and movi.MovNumDocRef=mov.MovNumDocRef) as CantMovimientos\n" +
//"  \n" +
//"    FROM [GUARD].[softland].[cwmovim] as mov\n" +
//"    left join guard.[softland].[cwtauxi] as aux on mov.CodAux=AUX.CodAux\n" +
//" left join guard.softland.cwcpbte as comp on comp.CpbNum=mov.CpbNum\n" +
//"     where \n" +
//"\n" +
//"  --NumDoc=62 and \n" +
//"  mov.CpbMes!='00' and\n" +
//"\n" +
//"  comp.CpbEst='V' and \n" +
//"  PctCod='10-01-065' \n" +
//"    group by  aux.RutAux\n" +
//"  ,aux.NomAux,movNumDocRef\n" +
//"  having  SUM(MovDebe-MovHaber)<>0\n" +
//"  order by aux.NomAux asc";
//                       
//            
//            
//      rs = stmt.executeQuery(queryString);      
//            String rutAux;
//                String nomAux;
//                String movNumDocRef;
//                String saldo;
//                String movFe;
//                String cantMov;
//            while (rs.next()) {
//                rutAux=rs.getObject(1).toString();
//                nomAux=rs.getObject(2).toString();
//                movNumDocRef=rs.getObject(3).toString();
//                saldo=rs.getObject(4).toString();
//               movFe=rs.getObject(5).toString();
//                cantMov=rs.getObject(6).toString();
//                
//              
//                
//              FacturaXC fact=new FacturaXC(rutAux, nomAux, movFe, movNumDocRef, saldo, cantMov);
//                lista.add(fact);
//            } 
//
//       
//     
//            System.out.println("biiieennntoo");
//
//         } catch (SQLException e) {
//           
//        }  finally {
//            try {
//                if (rs != null) {
//                    rs.close();
//                    rs = null;
//                }
//                if (stmt != null) {
//                    stmt.close();
//                    stmt = null;
//                }
//                if (con != null) {
//                    con.close();
//                    con = null;
//                }
//            } catch (SQLException e) {
//            }
//        }
//    
//     return lista;
//    }
         
         
                  // procedimiento que cruza los documentos no saldados con los pagos recibidos 
     
         public List<FacturaXC> getFacturasConSaldo (int empresa){
             
        
    
        PreparedStatement stmt = null;
        ResultSet rs = null; 
         ResultSet rsSoft = null; 
      List<FacturaXC> lista= new ArrayList();   
    try{
           
   
        this.Conectar();
      
                String queryString = 
                        " use Invoicing\n" +
"\n" +
"declare @tabla Table(MovNumDocRef int,saldo int,cantMovim int,fecha datetime,Codaux varchar(255),rutAux varchar(255),NomAux varchar(255))\n" +
"insert  @tabla\n" +
"exec Inteligencias.dbo.softland_get_doctos "+empresa+"\n" +
 "select * from @tabla"
                        + " --where MovNumDocRef<>3391";
                        

             stmt = this.con.prepareStatement(queryString);
           
         Statement st=con.createStatement();
          Statement stSoft=con.createStatement();
        
         st.executeUpdate(queryString);
         
         //getGeberatedKeys devuelve lo que retorna la query en este caso que se obtuvo del SP
         rs=st.getGeneratedKeys();
        
            while (rs.next()) {
           
                
              FacturaXC factura=new FacturaXC(
              rs.getString("rutAux"),
              rs.getString("NomAux"),
              rs.getString("Codaux"),
              rs.getString("Fecha"),
              rs.getString("MovNumDocRef"),
              rs.getString("saldo"),
              rs.getString("cantMovim")        
                      
              );
              
              //Marca 1 indica que se va directo a contabilizacion
              //
              
              
                       
                lista.add(factura);
            } 
     
            System.out.println("biiieennntoo");

            } catch (BatchUpdateException ex) {
                
                //captura cada batch y devuelve si se ejecuto o no cada uno

            int[] updateCount = ex.getUpdateCounts();

             

            int count = 1;

            for (int i : updateCount) {

                if  (i == Statement.EXECUTE_FAILED) {

                    System.out.println("Error on request " + count +": Execute failed");

                } else {

                    System.out.println("Request " + count +": OK");

                }

                count++;

            }
            
           } catch (SQLException e) {
            System.out.println("SQLState: "
                    + e.getSQLState());
            System.out.println("SQLErrorCode: "
                    + e.getErrorCode());
            e.printStackTrace();

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
                if (con != null) {
                    con.close();
                    con = null;
                }
            } catch (SQLException e) {

            }
        }

    
     return lista;
    }
         
         public List<FacturaXC> getFacturasConSaldo2 (int empresa){
             List<FacturaXC> lista = new ArrayList<>();
             
             FacturaXC fact1= new FacturaXC("1001-1", "Juan","1213", "2017-01-01","11", "1000","2");
              FacturaXC fact2= new FacturaXC("1001-1", "Juan","1232", "2017-01-02","22", "2000","1");
              FacturaXC fact3= new FacturaXC("1001-1", "Juan","123", "2017-01-02","33", "-12000","1");
              lista.add(fact1);
              lista.add(fact2);
              lista.add(fact3);
             
             
             return lista;
         }
                 
         

         // procedimiento que cruza los documentos no saldados con los pagos recibidos 
     
         public List<Pago> getPagosSoft(int empresa){
             
        
    
        PreparedStatement stmt = null;
        ResultSet rs = null; 
         ResultSet rsSoft = null; 
      List<Pago> lista= new ArrayList();   
    try{
           
   
        this.Conectar();
      
                String queryString = 
                        " use Invoicing\n" +
"\n" +
"declare @tabla Table(MovNumDocRef int,saldo int,cantMovim int,fecha datetime,Codaux varchar(255),rutAux varchar(255),NomAux varchar(255))\n" +
"insert  @tabla\n" +
"exec Inteligencias.dbo.softland_get_doctos "+empresa+"\n" +
"\n" +
" \n" +
" Select Enlaze.Documento as IdDocumento, CONVERT(int,replace(Documentos.Numero,' ','')) as NumeroDocumento,enlaze.Pago as IdPago, enlaze.Monto as Monto\n" +
",Enlaze.Fecha as 'FechaPago',tabla.cantMovim as SoftCantMovim,tabla.saldo as SoftSaldo,tabla.fecha as SoftMinFecha\n" +
"\n" +
"\n" +
"From \n" +
"\n" +
"\n" +
"Enlaze \n" +
"Left JOIN Documentos \n" +
"ON Documento = Id_Documento \n" +
"LEFT JOIN Rubros \n" +
"ON Documentos.Rubro = IdRubro \n" +
"left Join DocPago \n" +
"ON Enlaze.Pago = DocPago.Id_Pago \n" +
"inner join @tabla as tabla on tabla.MovNumDocRef=CONVERT(int,replace(Documentos.Numero,' ',''))\n" +
"\n" +
"Where  DocPago.Empresa ="+empresa+" And DocPago.Tipo in ( 1, 2, 3, 5, 6, 9, 10 ) --todos los tipos de pago menos castigo nota credito nota debito \n" +
"and Documentos.Tipo  In ( 1)  --solo facturas facturas\n" +
"\n" +
"order by Enlaze.Pago asc";
                        

             stmt = this.con.prepareStatement(queryString);
           
         Statement st=con.createStatement();
          Statement stSoft=con.createStatement();
        
         st.executeUpdate(queryString);
         
         //getGeberatedKeys devuelve lo que retorna la query en este caso que se obtuvo del SP
         rs=st.getGeneratedKeys();
        
            while (rs.next()) {
           
                
              Pago pago=new Pago(
               Integer.parseInt(rs.getObject(1).toString()),
               Integer.parseInt(rs.getObject(2).toString()),
               Integer.parseInt(rs.getObject(3).toString()),
                rs.getObject(4).toString(),
               rs.getObject(5).toString(),
               Integer.parseInt(rs.getObject(6).toString()),
               Integer.parseInt(rs.getObject(7).toString()),
               rs.getObject(8).toString()       
                 
              
              );
              
              //Marca 1 indica que se va directo a contabilizacion
              //
              
                if(pago.SoftSaldo<0){
                   //los negativos no los contabilizamos
                    pago.marca=0;
                }
                else if  (pago.SoftCantMovim==1){
                    
                    pago.marca=1;
                
                }else {
   String querySoft = "select MovFe,convert(int,MovHaber) as MovHaber\n" +
"    FROM "+getNombreEmpresa(empresa)+".[softland].[cwmovim] as mov\n" +
"    left join "+getNombreEmpresa(empresa)+".[softland].[cwtauxi] as aux on mov.CodAux=AUX.CodAux\n" +
" left join "+getNombreEmpresa(empresa)+".softland.cwcpbte as comp on comp.CpbNum=mov.CpbNum\n" +
"     where \n" +
"  mov.CpbMes!='00' and\n" +
"  comp.CpbEst='V' and \n" +
"  PctCod='10-01-065'   \n" +
"  and mov.MovNumDocRef="+pago.numDocumento+"\n" +
"  and MovHaber>0";  
          
    rsSoft =stSoft.executeQuery(querySoft);
      
     List<Pago> pagosSoftland= new ArrayList();
      while(rsSoft.next()){
      Pago pagoSoftland=new Pago ();
       pagoSoftland.fecha=rsSoft.getObject(1).toString();
        pagoSoftland.monto=rsSoft.getObject(2).toString();
       pagosSoftland.add(pagoSoftland);
          //System.out.println("pago:"+pago.numDocumento+" "+pagoSoftland.getMonto());    
    }
      
    
     //comparamos el pago con el listado de movimientos que trae para saber si el pago ya existe o no, por defecto la marca será de contabilizar
     //porque el pago no coincide con ninguno de los registrados para ese documento valor 4
        pago.marca=4;
        for(Pago p:pagosSoftland){
          //  System.out.println("dentro pago"+ p.fecha);
            if (pago.numDocumento==207629){
                System.out.println("aaaaaaaaaaaaa pago softland "+p.fecha+" monto "+p.monto+ " pago ventas "+pago.fecha+" monto"+pago.monto);  
            }
            
           //si dentro del pago existe otro con ese monto y fecha, no contabilizar y marcar con 2
            if (p.fecha.equals(pago.fecha)&&p.monto.equals(pago.monto)){
          pago.marca=2;      
         break ;   
           // si en el pago solo coincide el monto, no contabilizar y marcarlo con 3 
           }else if((!p.fecha.equals(pago.fecha))&&(p.monto.equals(pago.monto))){
                 pago.marca=3;
                 break;
             } //fin if
            
          }     //fin for
      
    
                } // fin else
                    
                       
                lista.add(pago);
            } 
     
            System.out.println("biiieennntoo");

            } catch (BatchUpdateException ex) {
                
                //captura cada batch y devuelve si se ejecuto o no cada uno

            int[] updateCount = ex.getUpdateCounts();

             

            int count = 1;

            for (int i : updateCount) {

                if  (i == Statement.EXECUTE_FAILED) {

                    System.out.println("Error on request " + count +": Execute failed");

                } else {

                    System.out.println("Request " + count +": OK");

                }

                count++;

            }
            
           } catch (SQLException e) {
            System.out.println("SQLState: "
                    + e.getSQLState());
            System.out.println("SQLErrorCode: "
                    + e.getErrorCode());
            e.printStackTrace();

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
                if (con != null) {
                    con.close();
                    con = null;
                }
            } catch (SQLException e) {

            }
        }

    
     return lista;
    }
        
         public List<Pago> getPagosSoft2(int empresa){
             List<Pago> lista = new ArrayList<>();
             Pago p1= new Pago(998,11,888,"500","2017-02-01",2,1000,"2017-01-01");
             p1.marca=1;
             Pago p2= new Pago(997,22,777,"1000","2017-02-01",3,2000,"2017-01-02");
             p2.marca=4;
             Pago p4= new Pago(997,22,777,"1000","2017-02-01",3,2000,"2017-01-02");
             p4.setMarca(1);
             Pago p3= new Pago(997,22,777,"3000","2017-02-01",3,2000,"2017-01-02");
             p3.setMarca(1);
             Pago p5= new Pago(997,33,777,"3000","2017-02-01",3,-12000,"2017-01-02");
             p5.marca=0;
             lista.add(p1);
             lista.add(p2);
             lista.add(p4);
             lista.add(p3);
             lista.add(p5);
             
             return lista;
             
         }
     
        public String getNombreEmpresa(int empresa){
            String nombreEmpresa="";
             switch (empresa) {
                 case 1:
                     return "Tecnologiassa";
                 case 2:
                     return "Outsourcingsa";
                 case 0:
                     return "Guard";
                 default:
                     break;
             }
            return nombreEmpresa;
        } 
         
         
          
    
}