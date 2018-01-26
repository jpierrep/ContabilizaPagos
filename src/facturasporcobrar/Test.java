/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturasporcobrar;

import Dao.GetData;
import Model.FacturaXC;
import Model.Pago;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author jpierre
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
     
//        List<Pago> pagos=new ArrayList();
//        GetData getdata= new GetData();
//       pagos= getdata.getPagosSoft(0);
//        System.out.println(pagos.size());
//        System.out.println("hola");
//       for (Pago p:pagos){
//         if (p.marca==3){
//           System.out.println("igual monto distintas fechas,"+ p.numDocumento);
//         }if (p.marca==2){
//           System.out.println("igual monto igual fechas,"+ p.numDocumento);
//         }
//                 if (p.marca==4){
//        System.out.println("mas de un movimiento y contabilizar,"+ p.numDocumento+p.fecha);
//                 }
//       }

/*
       List<FacturaXC> pagos=new ArrayList();
      GetData getdata= new GetData();
      pagos=getdata.getFacturasConSaldo(0);
       
      for (FacturaXC fact: pagos){
          System.out.println(fact.getMovNumDocRef());
          
      }
      
*/

  List<Pago> pagos= new ArrayList();
  Pago pago1=new Pago();
  pago1.setNumDocumento(123456);
   pago1.setMonto("1111");
   
    Pago pago2=new Pago();
  pago2.setNumDocumento(11321);
   pago2.setMonto("2222");
        
        pagos.add(pago2);
                pagos.add(pago1);
                Test test=new Test();
           //     test.calculaContab(pagos);
Map<Integer, Integer> sum = pagos.stream().collect(
                Collectors.groupingBy(Pago::getNumDocumento, Collectors.summingInt(Pago::getMontoInt)));
              System.out.println(sum);
    }
    
    
                      public  void calculaContab(List<Pago> pagos){
//              List<Pago> listamov= new ArrayList(); 
//listamov= pagos.stream().filter(
//   a -> Objects.equals(a.getMarca(),1)||Objects.equals(a.getMarca(),4)).collect(Collectors.toList()); 

  //acá ya tengo todos los que se puedan contabilizar
//   Map<String, Integer> sum = pagos.stream().collect(
//                Collectors.groupingBy(Pago::getSoftCantMovim, Collectors.summingInt(Integer.parseInt(Pago::getMonto))));
//              System.out.println(sum);

//Map<String, Integer> sum = pagos.stream().collect(
//                Collectors.groupingBy(Pago::getNumDocumento, Collectors.summingInt(Integer.parseInt(Pago::getMonto))));
//              System.out.println(sum);

              Map<Integer, Integer> sum = pagos.stream().collect(
                Collectors.groupingBy(Pago::getNumDocumento, Collectors.summingInt(Pago::getMontoInt)));
              System.out.println(sum);
          }
    
}
