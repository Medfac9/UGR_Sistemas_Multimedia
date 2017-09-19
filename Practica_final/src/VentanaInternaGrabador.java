import java.io.File;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import sm.sound.SMSoundRecorder;

public class VentanaInternaGrabador extends javax.swing.JInternalFrame {

    SMSoundRecorder recorder;
    
    public VentanaInternaGrabador(File f) {
        initComponents();
        grupoBotones.add(grabar);
        grupoBotones.add(parar);
        recorder = new SMSoundRecorder(f);
        ((SMSoundRecorder)recorder).addLineListener(new ManejadorAudio());
    }
    
    class ManejadorAudio implements LineListener {
        @Override
        public void update(LineEvent event) {
            if (event.getType() == LineEvent.Type.START) {

            }
            if (event.getType() == LineEvent.Type.STOP) {
                parar.setSelected(true);
            }
            if (event.getType() == LineEvent.Type.CLOSE) {
                grabar.setSelected(false);
            } 
        }   
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grupoBotones = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        grabar = new javax.swing.JToggleButton();
        parar = new javax.swing.JToggleButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
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

        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        grabar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/RecordPressed_48x48.png"))); // NOI18N
        grabar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/RecordPressed_48x48.png"))); // NOI18N
        grabar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/RecordDisabled_48x48.png"))); // NOI18N
        grabar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                grabarActionPerformed(evt);
            }
        });
        jPanel1.add(grabar);

        parar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/StopNormalRed_48x48.png"))); // NOI18N
        parar.setSelected(true);
        parar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/StopNormalRed_48x48.png"))); // NOI18N
        parar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/StopDisabled_48x48.png"))); // NOI18N
        parar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pararActionPerformed(evt);
            }
        });
        jPanel1.add(parar);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void grabarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_grabarActionPerformed
        // TODO add your handling code here:
        if(recorder!=null)
            recorder.record();
    }//GEN-LAST:event_grabarActionPerformed

    private void pararActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pararActionPerformed
        // TODO add your handling code here:
        if(recorder!=null)
            recorder.stop();
    }//GEN-LAST:event_pararActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
        recorder.stop();
    }//GEN-LAST:event_formInternalFrameClosing

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton grabar;
    private javax.swing.ButtonGroup grupoBotones;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JToggleButton parar;
    // End of variables declaration//GEN-END:variables
}
