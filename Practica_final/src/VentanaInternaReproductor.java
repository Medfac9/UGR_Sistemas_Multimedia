import java.io.File;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import sm.sound.SMClipPlayer;
import sm.sound.SMPlayer;

public class VentanaInternaReproductor extends javax.swing.JInternalFrame {

    SMPlayer player;
    
    public VentanaInternaReproductor(File f) {
        initComponents();
        grupoBotones.add(play);
        grupoBotones.add(stop);
        player = new SMClipPlayer(f);
        ((SMClipPlayer)player).addLineListener(new ManejadorAudio());
    }
    
    class ManejadorAudio implements LineListener {
        @Override
        public void update(LineEvent event) {
            if (event.getType() == LineEvent.Type.STOP) {
                stop.setSelected(true);
            }
            if (event.getType() == LineEvent.Type.CLOSE) {
                play.setSelected(false);
            } 
        }   
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grupoBotones = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        play = new javax.swing.JToggleButton();
        stop = new javax.swing.JToggleButton();

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

        play.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/PlayPressed_48x48.png"))); // NOI18N
        play.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/PlayPressed_48x48.png"))); // NOI18N
        play.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/PlayDisabled_48x48.png"))); // NOI18N
        play.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playActionPerformed(evt);
            }
        });
        jPanel1.add(play);

        stop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/StopNormalRed_48x48.png"))); // NOI18N
        stop.setSelected(true);
        stop.setPreferredSize(new java.awt.Dimension(50, 50));
        stop.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/StopNormalRed_48x48.png"))); // NOI18N
        stop.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/StopDisabled_48x48.png"))); // NOI18N
        stop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopActionPerformed(evt);
            }
        });
        jPanel1.add(stop);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void playActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playActionPerformed
        // TODO add your handling code here:
        if(player!=null)
            player.play();
    }//GEN-LAST:event_playActionPerformed

    private void stopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopActionPerformed
        // TODO add your handling code here:
        if(player!=null)
            player.stop();
    }//GEN-LAST:event_stopActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
        player.stop();
    }//GEN-LAST:event_formInternalFrameClosing
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup grupoBotones;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JToggleButton play;
    private javax.swing.JToggleButton stop;
    // End of variables declaration//GEN-END:variables
}
