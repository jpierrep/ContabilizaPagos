/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturasporcobrar;

import Model.Opciones;
import Model.Pago;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author jpierre
 */
public class PanelOpciones extends JFrame {

    String[] cabeceraArchivo;
    int cantidadColumnas;
    List<Opciones> listaOpciones=new ArrayList<>();

    
    JButton button1 = new JButton("Mapear Variables");
    JLabel[] jlabels;
    JTextField[] jtext;
    JButton[] jbuttons;


    

    public PanelOpciones(String Proceso,List<Opciones> lista ) {
        
       
        super("JPanel Demo Program");

             this.listaOpciones=lista;
         cantidadColumnas=listaOpciones.size();
//         cabeceraArchivo=new String[3];
//         cabeceraArchivo[0]="1";
//        cabeceraArchivo[1]="2";
//        cabeceraArchivo[2]="3";
         
        jlabels = new JLabel[cantidadColumnas];
        jtext = new JTextField[cantidadColumnas];
        jbuttons = new JButton[cantidadColumnas];
        // create a new panel with GridBagLayout manager
        JPanel newPanel = new JPanel(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(4, 4, 4, 4);

        // add components to the panel
        constraints.gridx = 0;
        constraints.gridy = 0;
        /*       newPanel.add(labelUsername, constraints);
 
        constraints.gridx = 1;
        newPanel.add(textUsername, constraints);
         
        constraints.gridx = 0;
        constraints.gridy = 1;     
        newPanel.add(labelPassword, constraints);
        
         constraints.gridx = 1;
        newPanel.add(fieldPassword, constraints);
         */

        for (int i = 0; i < cantidadColumnas; i++) {
            jlabels[i] = new JLabel((i + 1) + ". " + listaOpciones.get(i).getEtiqueta()+" "+listaOpciones.get(i).getValor());
            jtext[i] = new JTextField(5);
            jbuttons[i]= new JButton("Ver");
           
            jbuttons[i].addActionListener(listener);
            jbuttons[i].setName(i+" "+Proceso);
            if(!listaOpciones.get(i).isHasButton()){
              jbuttons[i].setEnabled(false);
            }
            constraints.gridx = 0;
            ++constraints.gridy;

            newPanel.add(jlabels[i], constraints);
            ++constraints.gridx;

            newPanel.add(jbuttons[i], constraints);

        }

        constraints.gridy++;

        constraints.gridx = 0;

        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        newPanel.add(button1, constraints);
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActionPerformed(evt);
            }

            private void jButtonActionPerformed(ActionEvent evt) {
                System.out.println("boton opciones");
             
//                for (int i = 0; i < 7; i++) {
//                    System.out.println(jtext[i].getText());
//
//                }

            }
        });

        // set border for the panel
        newPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Login Panel"));

        // add the panel to this frame
        add(newPanel);

        pack();
        setLocationRelativeTo(null);

    }

    public String[] getTextFieldValue() {
        String[] textFields = new String[cantidadColumnas];

        for (int i = 0; i < cantidadColumnas; i++) {
            textFields[i] = jtext[i].getText().trim();
            //   System.out.println("get"+jtext[i].getText());
        }

        return textFields;

    }

    public String getTextFieldValueString() {

        String[] textFieldValues = getTextFieldValue();
        List<String> textvar = new ArrayList();
        for (String value : textFieldValues) {
            if (!value.equals("")) {
                textvar.add(value);
            }
        }

        return "'" + String.join("','", textvar) + "'";

    }
    
     ActionListener listener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() instanceof JButton) {
                String text = ((JButton) e.getSource()).getName();
          //      JOptionPane.showMessageDialog(null, text);
            }
        }
    };
    
    
    
    public static void main(String[] args) {
        // TODO code application logic here
  //  set look and feel to the system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
         
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
               // new PanelOpciones("").setVisible(true);
            }
        });
    
    }

    public void setJbuttons(JButton[] jbuttons) {
        this.jbuttons = jbuttons;
    }

    public JButton[] getJbuttons() {
        return jbuttons;
    }

    public List<Opciones> getListaOpciones() {
        return listaOpciones;
    }

    public void setListaOpciones(List<Opciones> listaOpciones) {
        this.listaOpciones = listaOpciones;
    }

    public JButton getButton1() {
        return button1;
    }

    public void setButton1(JButton button1) {
        this.button1 = button1;
    }

   


    
    
}
