import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;

public class VentanaInternaCamara extends javax.swing.JInternalFrame {

    private Webcam camara = null;
    
    public VentanaInternaCamara() {
        initComponents();
        camara = Webcam.getDefault();
        Dimension resoluciones[] = camara.getViewSizes();
        Dimension maxRes = resoluciones[resoluciones.length-1]; 
        camara.setViewSize(maxRes);
        if (camara != null) {
            areaVisual = new WebcamPanel(camara);
            if (areaVisual!= null) {
                getContentPane().add(areaVisual, BorderLayout.CENTER);
                pack(); 
            }
        } 
    }
    
    public static VentanaInternaCamara getInstance(){
        VentanaInternaCamara v = new VentanaInternaCamara();
        return (v.camara!=null?v:null);
    }
    
    public Webcam getCamera(){
        return camara;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        areaVisual = new javax.swing.JPanel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        areaVisual.setLayout(new java.awt.BorderLayout());
        getContentPane().add(areaVisual, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
        camara.close();
    }//GEN-LAST:event_formInternalFrameClosing

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel areaVisual;
    // End of variables declaration//GEN-END:variables
}
