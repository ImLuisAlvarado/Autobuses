package mx.itson.autobuses.ui;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
import javax.swing.*;
import mx.itson.autobuses.entidades.Boleto;


/**
 * Clase AutobusesFrame que extiende JFrame y representa la interfaz gráfica
 * para la gestión de reservas de asientos en un autobús.
 * @author ImLuisAlvarado
 */

public class AutobusesFrame extends javax.swing.JFrame {
    
    private boolean[] asientosDisponibles = new boolean[20]; 
    private String[] estaciones = {"NAVOJOA", "OBREGÓN", "EMPALME", "GUAYMAS", "HERMOSILLO","SANTA ANA","MAGDALENA","IMURIS","NOGALES"};
    private int estacionActual = 0;
    private int[] pasajerosPorEstacion = new int[estaciones.length]; 
    private String[] pasajeros = new String [20];
    private String[] destinos = new String[20];
    private String[] origenes = new String[20];
    private double[] precios = new double[20];
    private double totalIngresos = 0.0;

    /**
     * Constructor de la clase AutobusesFrame.
     * Inicializa los componentes de la interfaz y configura los botones.
     */
    public AutobusesFrame() {
        initComponents();
        configurarBotones();
        for (int i = 0; i < asientosDisponibles.length; i++) {
        asientosDisponibles[i] = true;
    }
    }
    /**
     * Verifica si el destino seleccionado ya ha sido alcanzado.
     * 
     * @param destinoSeleccionado El destino a verificar.
     * @return true si el destino ya ha sido alcanzado, false en caso contrario.
     */
    private boolean destinoYaAlcanzado(String destinoSeleccionado) {
    for (int i = 0; i < estacionActual; i++) {
        if (estaciones[i].equals(destinoSeleccionado)) {
            return true;
        }
    }
    return false;
}

    /**
     * Calcula el precio del boleto basado en el origen y el destino.
     * 
     * @param origen La estación de origen.
     * @param destino La estación de destino.
     * @return El precio calculado del boleto.
     */
    private double calcularPrecio(String origen, String destino) {
    int indiceOrigen = -1;
    int indiceDestino = -1;

    for (int i = 0; i < estaciones.length; i++) {
        if (estaciones[i].equals(origen)) {
            indiceOrigen = i;
        }
        if (estaciones[i].equals(destino)) {
            indiceDestino = i;
        }
    }

        return (indiceDestino - indiceOrigen) * 50.0;
}

    
     /**
     * Reserva un asiento para un pasajero.
     * 
     * @param numeroAsiento El número del asiento a reservar.
     * @param boton El botón correspondiente al asiento.
     */
    private void reservarAsiento(int numeroAsiento, JButton boton) {
    if (!asientosDisponibles[numeroAsiento - 1]) {
        JOptionPane.showMessageDialog(this, "El asiento " + numeroAsiento + " ya está reservado.");
    } else {
        String pasajero = JOptionPane.showInputDialog(this, "Ingresa el nombre del pasajero:");
        if (pasajero != null && !pasajero.isBlank()) {

            String origen = estaciones[estacionActual];
            
            String destino = (String) JOptionPane.showInputDialog(this, "Selecciona el destino del pasajero:",
                    "Destino", JOptionPane.QUESTION_MESSAGE, null, estaciones, estaciones[0]);

            if (origen != null && destino != null) {
                if (destinoYaAlcanzado(destino)) {
                    JOptionPane.showMessageDialog(this, "El destino " + destino + " ya ha sido alcanzado.");
                } else if (destino.equals(origen)) {
                    JOptionPane.showMessageDialog(this, "El origen y destino no pueden ser iguales.");
                } else {
                    double precio = calcularPrecio(origen, destino);
                    asientosDisponibles[numeroAsiento - 1] = false;
                    boton.setEnabled(false);
                    pasajeros[numeroAsiento -1] = pasajero;
                    destinos[numeroAsiento - 1] = destino;
                    origenes[numeroAsiento - 1] = origen;
                    precios[numeroAsiento - 1] = precio;
                    totalIngresos += precio;

                    JOptionPane.showMessageDialog(this, "Asiento " + numeroAsiento + " reservado para " + pasajero +
                            "\nOrigen: " + origen + "\nDestino: " + destino + "\nPrecio: $" + precio);
                }
            }
        }
    }
}


    /**
     * Configura los botones de los asientos y les asigna la acción de reservar.
     * Este método itera sobre un arreglo de botones que representan los asientos
     * y les asigna un ActionListener que llama al método reservarAsiento
     * cuando se hace clic en el botón correspondiente.
     */
    private void configurarBotones() {
        JButton[] botones = {btnAsiento1, btnAsiento2, btnAsiento3, btnAsiento4, btnAsiento5,
            btnAsiento6, btnAsiento7, btnAsiento8, btnAsiento9, btnAsiento10,
            btnAsiento11, btnAsiento12, btnAsiento13, btnAsiento14, btnAsiento15,
            btnAsiento16, btnAsiento17, btnAsiento18, btnAsiento19, btnAsiento20};
        
        for (int i = 0; i < botones.length; i++) {
            final int numeroAsiento = i + 1;
            final JButton boton = botones[i];
            boton.addActionListener(evt -> reservarAsiento(numeroAsiento, boton));
    }
}
    /**
     * Avanza a la siguiente estación y actualiza la interfaz.
     * Este método incrementa el índice de la estación actual, actualiza la etiqueta
     * que muestra la estación actual y habilita los asientos de los pasajeros que
     * bajan en la nueva estación. Si se alcanza la última estación, se genera un
     * reporte final de las reservas.
     */
    private void avanzarEstacion() {
    if (estacionActual < estaciones.length - 1) {
        estacionActual++;
        lblEstacionActual.setText("Estación Actual: " + estaciones[estacionActual]);
        int pasajerosBajaron = 0;
        for (int i = 0; i < destinos.length; i++) {
            if (destinos[i] != null && destinos[i].equals(estaciones[estacionActual])) {
                pasajerosBajaron++;

                asientosDisponibles[i] = true;

                JButton boton = obtenerBotonPorNumero(i + 1);
                if (boton != null) {
                    boton.setEnabled(true);
                }
            }
        }

        JOptionPane.showMessageDialog(this, "En la estación " + estaciones[estacionActual] +
                ", " + pasajerosBajaron + " pasajeros bajaron.");

        pasajerosPorEstacion[estacionActual] = pasajerosBajaron;
    } else {
        JOptionPane.showMessageDialog(this, "Has llegado a la última estación.");
        if (estacionActual == estaciones.length - 1) {
            generarReporteFinal();
}

    }
}
    
    /**
     * Genera un reporte final de las reservas realizadas y muestra los ingresos totales.
     * Este método compila la información de los pasajeros, sus orígenes, destinos, precios
     * y el total de ingresos generados, y muestra esta información en un cuadro de diálogo.
     */
    private void generarReporteFinal() {
    StringBuilder reporte = new StringBuilder("Reporte General:\n\n");
    reporte.append(String.format("%-15s %-15s %-15s %-10s %-10s\n", "Nombre", "Origen", "Destino", "Precio", "Asiento"));

    for (int i = 0; i < destinos.length; i++) {
        if (destinos[i] != null) {
            reporte.append(String.format("%-15s %-15s %-15s $%-9.2f %-10d\n", 
                    pasajeros[i],
                    origenes[i], 
                    destinos[i], 
                    precios[i], 
                    i + 1));
        }
    }

    reporte.append("\nTotal Ingresos: $" + totalIngresos);
    JOptionPane.showMessageDialog(this, reporte.toString());
}
    
    /**
     * Obtiene el botón correspondiente al número de asiento especificado.
     * Este método busca en el arreglo de botones y devuelve el botón
     * que corresponde al número de asiento proporcionado.
     *
     * @param numeroAsiento El número del asiento (1-20) para el cual se desea obtener el botón.
     * @return El JButton correspondiente al número de asiento, o null si no se encuentra.
     */
    private JButton obtenerBotonPorNumero(int numeroAsiento) {
        switch (numeroAsiento) {
            case 1: return btnAsiento1;
            case 2: return btnAsiento2;
            case 3: return btnAsiento3;
            case 4: return btnAsiento4;
            case 5: return btnAsiento5;
            case 6: return btnAsiento6;
            case 7: return btnAsiento7;
            case 8: return btnAsiento8;
            case 9: return btnAsiento9;
            case 10: return btnAsiento10;
            case 11: return btnAsiento11;
            case 12: return btnAsiento12;
            case 13: return btnAsiento13;
            case 14: return btnAsiento14;
            case 15: return btnAsiento15;
            case 16: return btnAsiento16;
            case 17: return btnAsiento17;
            case 18: return btnAsiento18;
            case 19: return btnAsiento19;
            case 20: return btnAsiento20;
            default: return null;
    }
}

    
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnAsiento1 = new javax.swing.JButton();
        btnAsiento2 = new javax.swing.JButton();
        btnAsiento3 = new javax.swing.JButton();
        btnAsiento4 = new javax.swing.JButton();
        btnAsiento5 = new javax.swing.JButton();
        btnAsiento6 = new javax.swing.JButton();
        btnAsiento7 = new javax.swing.JButton();
        btnAsiento8 = new javax.swing.JButton();
        btnAsiento9 = new javax.swing.JButton();
        btnAsiento10 = new javax.swing.JButton();
        btnAsiento11 = new javax.swing.JButton();
        btnAsiento12 = new javax.swing.JButton();
        btnAsiento14 = new javax.swing.JButton();
        btnAsiento13 = new javax.swing.JButton();
        btnAsiento15 = new javax.swing.JButton();
        btnAsiento16 = new javax.swing.JButton();
        btnAsiento17 = new javax.swing.JButton();
        btnAsiento18 = new javax.swing.JButton();
        btnAsiento19 = new javax.swing.JButton();
        btnAvanzar = new javax.swing.JButton();
        btnAsiento20 = new javax.swing.JButton();
        lblEstacionActual = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnAsiento1.setFont(new java.awt.Font("Liberation Sans", 1, 14)); // NOI18N
        btnAsiento1.setText("Asiento 1");

        btnAsiento2.setFont(new java.awt.Font("Liberation Sans", 1, 14)); // NOI18N
        btnAsiento2.setText("Asiento 2");
        btnAsiento2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAsiento2ActionPerformed(evt);
            }
        });

        btnAsiento3.setFont(new java.awt.Font("Liberation Sans", 1, 14)); // NOI18N
        btnAsiento3.setText("Asiento 3");
        btnAsiento3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAsiento3ActionPerformed(evt);
            }
        });

        btnAsiento4.setFont(new java.awt.Font("Liberation Sans", 1, 14)); // NOI18N
        btnAsiento4.setText("Asiento 4");
        btnAsiento4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAsiento4ActionPerformed(evt);
            }
        });

        btnAsiento5.setFont(new java.awt.Font("Liberation Sans", 1, 14)); // NOI18N
        btnAsiento5.setText("Asiento 5");

        btnAsiento6.setFont(new java.awt.Font("Liberation Sans", 1, 14)); // NOI18N
        btnAsiento6.setText("Asiento 6");

        btnAsiento7.setFont(new java.awt.Font("Liberation Sans", 1, 14)); // NOI18N
        btnAsiento7.setText("Asiento 7");

        btnAsiento8.setFont(new java.awt.Font("Liberation Sans", 1, 14)); // NOI18N
        btnAsiento8.setText("Asiento 8");

        btnAsiento9.setFont(new java.awt.Font("Liberation Sans", 1, 14)); // NOI18N
        btnAsiento9.setText("Asiento 9");

        btnAsiento10.setFont(new java.awt.Font("Liberation Sans", 1, 14)); // NOI18N
        btnAsiento10.setText("Asiento 10");

        btnAsiento11.setFont(new java.awt.Font("Liberation Sans", 1, 14)); // NOI18N
        btnAsiento11.setText("Asiento 11");

        btnAsiento12.setFont(new java.awt.Font("Liberation Sans", 1, 14)); // NOI18N
        btnAsiento12.setText("Asiento 12");

        btnAsiento14.setFont(new java.awt.Font("Liberation Sans", 1, 14)); // NOI18N
        btnAsiento14.setText("Asiento 14");

        btnAsiento13.setFont(new java.awt.Font("Liberation Sans", 1, 14)); // NOI18N
        btnAsiento13.setText("Asiento 13");

        btnAsiento15.setFont(new java.awt.Font("Liberation Sans", 1, 14)); // NOI18N
        btnAsiento15.setText("Asiento 15");

        btnAsiento16.setFont(new java.awt.Font("Liberation Sans", 1, 14)); // NOI18N
        btnAsiento16.setText("Asiento 16");

        btnAsiento17.setFont(new java.awt.Font("Liberation Sans", 1, 14)); // NOI18N
        btnAsiento17.setText("Asiento 17");

        btnAsiento18.setFont(new java.awt.Font("Liberation Sans", 1, 14)); // NOI18N
        btnAsiento18.setText("Asiento 18");

        btnAsiento19.setFont(new java.awt.Font("Liberation Sans", 1, 14)); // NOI18N
        btnAsiento19.setText("Asiento 19");

        btnAvanzar.setFont(new java.awt.Font("Liberation Sans", 3, 14)); // NOI18N
        btnAvanzar.setText("Siguiente Estación");
        btnAvanzar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAvanzarActionPerformed(evt);
            }
        });

        btnAsiento20.setFont(new java.awt.Font("Liberation Sans", 1, 14)); // NOI18N
        btnAsiento20.setText("Asiento 20");

        lblEstacionActual.setFont(new java.awt.Font("Liberation Sans", 3, 18)); // NOI18N
        lblEstacionActual.setText("Estacion Actual: NAVOJOA");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAvanzar)
                .addGap(23, 23, 23))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(btnAsiento1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnAsiento5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnAsiento9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnAsiento13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(btnAsiento17))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAsiento18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btnAsiento14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnAsiento10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnAsiento6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnAsiento2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(btnAsiento3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnAsiento7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnAsiento11))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnAsiento12))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(4, 4, 4)
                                        .addComponent(btnAsiento8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnAsiento4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnAsiento15)
                                    .addComponent(btnAsiento19))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnAsiento20)
                                    .addComponent(btnAsiento16)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(lblEstacionActual)))
                .addContainerGap(205, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(lblEstacionActual)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAsiento3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAsiento2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAsiento1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAsiento4, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAsiento5, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAsiento7, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAsiento8, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAsiento6, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAsiento9, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAsiento10, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAsiento11, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAsiento12, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAsiento13, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAsiento14, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAsiento16, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAsiento15, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAsiento18, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAsiento20, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAsiento19, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAsiento17, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAvanzar)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAsiento2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAsiento2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAsiento2ActionPerformed

    private void btnAsiento3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAsiento3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAsiento3ActionPerformed

    private void btnAsiento4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAsiento4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAsiento4ActionPerformed

    private void btnAvanzarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAvanzarActionPerformed
        // TODO add your handling code here:
        avanzarEstacion();
    }//GEN-LAST:event_btnAvanzarActionPerformed

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
            java.util.logging.Logger.getLogger(AutobusesFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AutobusesFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AutobusesFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AutobusesFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AutobusesFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAsiento1;
    private javax.swing.JButton btnAsiento10;
    private javax.swing.JButton btnAsiento11;
    private javax.swing.JButton btnAsiento12;
    private javax.swing.JButton btnAsiento13;
    private javax.swing.JButton btnAsiento14;
    private javax.swing.JButton btnAsiento15;
    private javax.swing.JButton btnAsiento16;
    private javax.swing.JButton btnAsiento17;
    private javax.swing.JButton btnAsiento18;
    private javax.swing.JButton btnAsiento19;
    private javax.swing.JButton btnAsiento2;
    private javax.swing.JButton btnAsiento20;
    private javax.swing.JButton btnAsiento3;
    private javax.swing.JButton btnAsiento4;
    private javax.swing.JButton btnAsiento5;
    private javax.swing.JButton btnAsiento6;
    private javax.swing.JButton btnAsiento7;
    private javax.swing.JButton btnAsiento8;
    private javax.swing.JButton btnAsiento9;
    private javax.swing.JButton btnAvanzar;
    private javax.swing.JLabel lblEstacionActual;
    // End of variables declaration//GEN-END:variables
}
