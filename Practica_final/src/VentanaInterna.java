import java.awt.Color;
import sm.rmf.iu.Lienzo2DFinal;

public class VentanaInterna extends javax.swing.JInternalFrame {

    public VentanaPrincipal parent = null;
    
    public VentanaInterna(VentanaPrincipal vp) {
        initComponents();
        parent = vp;
    }
    
    public Lienzo2DFinal getLienzo(){
        return lienzo;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        lienzo = new sm.rmf.iu.Lienzo2DFinal();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setPreferredSize(new java.awt.Dimension(500, 500));
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                formMouseMoved(evt);
            }
        });
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameActivated(evt);
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                formMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                formMouseExited(evt);
            }
        });

        jScrollPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.CROSSHAIR_CURSOR));

        lienzo.setPreferredSize(new java.awt.Dimension(300, 300));
        lienzo.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                lienzoMouseMoved(evt);
            }
        });
        lienzo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lienzoMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lienzoMouseExited(evt);
            }
        });
        lienzo.setLayout(new java.awt.BorderLayout());
        jScrollPane1.setViewportView(lienzo);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        // TODO add your handling code here:
        parent.setSeleccionadaHerramienta(getLienzo().getSeleccionado());
        
        parent.setRelleno(getLienzo().getFigura().getRelleno());
        parent.setAlisar(getLienzo().getFigura().getAlisado());
        parent.setEditar(getLienzo().getMover());
        
        parent.setContador(getLienzo().getContador());
        
        parent.setColorBorde(getLienzo().getColorBorde());
    }//GEN-LAST:event_formInternalFrameActivated

    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        // TODO add your handling code here:
        parent.getCoordenadas().setText("(" + evt.getPoint().x + "," + evt.getPoint().y + ") ");
    }//GEN-LAST:event_formMouseMoved

    private void formMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseExited
        // TODO add your handling code here:
        parent.getCoordenadas().setText("");
    }//GEN-LAST:event_formMouseExited

    private void formMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseEntered
        // TODO add your handling code here:
        parent.getCoordenadas().setText("");
    }//GEN-LAST:event_formMouseEntered

    private void lienzoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lienzoMouseExited
        // TODO add your handling code here:
        parent.getCoordenadas().setText("");
    }//GEN-LAST:event_lienzoMouseExited

    private void lienzoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lienzoMouseClicked

    }//GEN-LAST:event_lienzoMouseClicked

    private void lienzoMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lienzoMouseMoved
        // TODO add your handling code here:

        if(getLienzo().getImage() != null){
            Color color = new Color(getLienzo().getImage().getRGB(evt.getPoint().x, evt.getPoint().y));

            int rojo = color.getRed();
            int verde = color.getGreen();
            int azul = color.getBlue();

            parent.getCoordenadas().setText("(" + evt.getPoint().x + "," + evt.getPoint().y + ") = ["+ rojo + "," + verde + "," + azul + "] ");
        }
    }//GEN-LAST:event_lienzoMouseMoved
 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private sm.rmf.iu.Lienzo2DFinal lienzo;
    // End of variables declaration//GEN-END:variables
}
