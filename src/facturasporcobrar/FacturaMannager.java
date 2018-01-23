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

/**
 *
 * @author jpierre
 */
public class FacturaMannager {
    
    GetData data=new GetData();
    
    
   public List<FacturaXC> getFacturasConSaldo(){
            
     return  data.getResumenCuentasMes("",0);
     
     
      
   }
    
   
   public List<Pago> getPagos(int empresa){
        List<Pago> pagos=new ArrayList();
        GetData getdata= new GetData();
       pagos= getdata.getPagosSoft(empresa);
       return pagos;
   }
   
    
}
