/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturasporcobrar;


import Dao.GetData;
import Model.FacturaXC;
import Model.Pago;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jpierre
 */
public class Transacciones extends javax.swing.JFrame {

    int empresa;
    String[] titulos;
  Object lista;
    /**
     * Creates new form Transacciones
     */
   // SystemMannager facturaMannager = new SystemMannager();

    public Transacciones() {

        //       super( "JButtonTable Example" );
        initComponents();
     jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "GuardService Seguridad S.A.","GS Tecnologías S.A.","GS Outsourcing S.A.","Inversiones Odin Ltda."  }));
     jProgressBar1.setVisible(false);
     jTable1.setAutoCreateRowSorter(true);
     // llenarTabla();
//    DefaultTableModel dm = new DefaultTableModel();
//    dm.setDataVector(new Object[][]{{"button 1","foo"},
//                                    {"button 2","bar"}},
//                     new Object[]{"Button","String"});
//                      
//    jTable1.setModel(dm);
//    jTable1.getColumn("Button").setCellRenderer(new ButtonRenderer());
//    jTable1.getColumn("Button").setCellEditor(new ButtonEditor(new JCheckBox()));

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jProgressBar1 = new javax.swing.JProgressBar();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Listado de Transacciones Anteriores");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jButton1.setText("Contabilizar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(261, 261, 261)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(94, 94, 94)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(454, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addGap(18, 18, 18))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        System.out.println("se cambió");
    }//GEN-LAST:event_jComboBox1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Transacciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Transacciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Transacciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Transacciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Transacciones transacciones= new Transacciones();
                String[] titulos = {"Nº Transacción", "Fecha Datos", "Nombre Archivo", "Fecha Trans", "Proceso", "Accion"};
                GetData data = new GetData();
                List<Pago> listaTrans = data.getPagosSoft(0);
                transacciones.llenarTabla();
                transacciones.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

    public void llenarTabla() {
        
        System.out.println("dentro llenar tabla");

        
        String[] datos = new String[titulos.length];
         DefaultTableModel tableModel=new DefaultTableModel(null, titulos) ;
        
        for (Object trans : (List) lista) {
            if (trans instanceof Pago){
              tableModel = new DefaultTableModel(null, titulos){   
            @Override
            public Class getColumnClass(int column) {
                 switch (column) {
                    case 0:
                        return Integer.class;
                    case 1:
                        return Integer.class;
                    case 2:
                        return Integer.class;
                        case 3:
                        return Integer.class;
                        case 6:
                        return Integer.class;
                    default:
                        return String.class;
                }
            }
          };
                System.out.println("es pago");
              break;
             }
            
              if (trans instanceof FacturaXC){
              tableModel = new DefaultTableModel(null, titulos){   
            @Override
            public Class getColumnClass(int column) {
                 switch (column) {
                    case 0:
                        return Integer.class;
                    case 1:
                        return Integer.class;
                    case 2:
                        return Integer.class;
                    default:
                        return String.class;
                }
            }
          };
                  System.out.println(" es factura");
              break;
             }
                     }
   
        
       
        for (Object trans : (List) lista) {
            if (trans instanceof Pago){
          
            datos[0] = Integer.toString( ((Pago) trans).getIdDocumento());
            datos[1]= Integer.toString(((Pago) trans).getNumDocumento()); 
            datos[2] = Integer.toString(((Pago) trans).getSoftCantMovim());
            datos[3] = Integer.toString(((Pago) trans).getSoftSaldo());  
            datos[4]=((Pago) trans).getSoftMinFecha();
            
            datos[5]=((Pago) trans).getFecha();
            datos[6] = ((Pago) trans).getMonto();

datos[7] = Integer.toString( ((Pago) trans).getMarca());
datos[8] = ((Pago) trans).getMarcaDesc();
            tableModel.addRow(datos);
            }
              if (trans instanceof FacturaXC){
          
            datos[0] =  ((FacturaXC) trans).getMovNumDocRef();
            datos[1] = ((FacturaXC) trans).getSaldo();
            datos[2] = ((FacturaXC) trans).getCantMov();
          datos[3]=((FacturaXC) trans).getMovFe();
          datos[4] = ((FacturaXC) trans).getCodAux();
       
            datos[5] = ((FacturaXC) trans).getRutAux();
            datos[6] = ((FacturaXC) trans).getNomAux();
            tableModel.addRow(datos);
            }
           

        }

      jTable1.setModel(tableModel);

    }

    public int getEmpresa() {
        return empresa;
    }

    public void setEmpresa(int empresa) {
        this.empresa = empresa;
    }

    public JComboBox<String> getjComboBox1() {
        return jComboBox1;
    }

    public void setjComboBox1(JComboBox<String> jComboBox1) {
        this.jComboBox1 = jComboBox1;
    }

    public String[] getTitulos() {
        return titulos;
    }

    public void setTitulos(String[] titulos) {
        this.titulos = titulos;
    }

    public Object getLista() {
        return lista;
    }

    public void setLista(Object lista) {
        this.lista = lista;
    }

    public JButton getjButton1() {
        return jButton1;
    }

    public void setjButton1(JButton jButton1) {
        this.jButton1 = jButton1;
    }

    public JLabel getjLabel1() {
        return jLabel1;
    }

    public void setjLabel1(JLabel jLabel1) {
        this.jLabel1 = jLabel1;
    }

    public JProgressBar getjProgressBar1() {
        return jProgressBar1;
    }

    public void setjProgressBar1(JProgressBar jProgressBar1) {
        this.jProgressBar1 = jProgressBar1;
    }

    

}
