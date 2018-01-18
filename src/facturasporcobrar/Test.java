/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturasporcobrar;

import Dao.GetData;
import Model.Pago;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jpierre
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
     
        List<Pago> pagos=new ArrayList();
        GetData getdata= new GetData();
       pagos= getdata.getPagosSoft(0);
        System.out.println(pagos.size());
        System.out.println("hola");
       for (Pago p:pagos){
         if (p.marca==3){
           System.out.println("igual monto distintas fechas,"+ p.numDocumento);
         }if (p.marca==2){
           System.out.println("igual monto igual fechas,"+ p.numDocumento);
         }
                 if (p.marca==4){
        System.out.println("mas de un movimiento y contabilizar,"+ p.numDocumento+p.fecha);
                 }
       }
       
        
        
    }
    
}
