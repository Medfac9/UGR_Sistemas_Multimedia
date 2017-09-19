import java.awt.image.BufferedImage;
import java.io.File;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

public class VentanaInternaVLCPlayer extends javax.swing.JInternalFrame {
   
    private EmbeddedMediaPlayer vlcplayer = null;
    private File fMedia;
    
    public VentanaInternaVLCPlayer (File f) {
        initComponents();
        grupoBotones.add(play);
        grupoBotones.add(stop);
        fMedia = f;
        EmbeddedMediaPlayerComponent aVisual = new EmbeddedMediaPlayerComponent();
        getContentPane().add(aVisual,java.awt.BorderLayout.CENTER);
        vlcplayer = aVisual.getMediaPlayer();
        vlcplayer.addMediaPlayerEventListener(new VideoListener());
    }
    
    public static VentanaInternaVLCPlayer getInstance(File f){
        VentanaInternaVLCPlayer v = new VentanaInternaVLCPlayer(f);
        return (v.vlcplayer!=null?v:null);
    }
    
    public void play() {
        if (vlcplayer != null) {
            if(vlcplayer.isPlayable()){
                //Si se estaba reproduciendo
                vlcplayer.play();
            } else {
                vlcplayer.playMedia(fMedia.getAbsolutePath());
            }
        } 
    }
    
    public void stop() {
        if (vlcplayer != null) {
            if (vlcplayer.isPlaying()) {
                vlcplayer.pause();
            } else {
                vlcplayer.stop();
            }
        } 
    }
    
    public BufferedImage capturaVideo(){
        BufferedImage img = vlcplayer.getSnapshot();
        return img;
    }

    private class VideoListener extends MediaPlayerEventAdapter {
        @Override
        public void finished(MediaPlayer mediaPlayer) {     
            stop.setSelected(true); 
        } 
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grupoBotones = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        play = new javax.swing.JToggleButton();
        stop = new javax.swing.JToggleButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setPreferredSize(new java.awt.Dimension(300, 300));
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

        jPanel2.setLayout(new java.awt.GridLayout(1, 0));

        play.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/PlayPressed_48x48.png"))); // NOI18N
        play.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/PlayPressed_48x48.png"))); // NOI18N
        play.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/PlayDisabled_48x48.png"))); // NOI18N
        play.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playActionPerformed(evt);
            }
        });
        jPanel2.add(play);

        stop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/StopNormalRed_48x48.png"))); // NOI18N
        stop.setSelected(true);
        stop.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/StopNormalRed_48x48.png"))); // NOI18N
        stop.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/StopDisabled_48x48.png"))); // NOI18N
        stop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopActionPerformed(evt);
            }
        });
        jPanel2.add(stop);

        getContentPane().add(jPanel2, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
        stop();
        vlcplayer = null;
    }//GEN-LAST:event_formInternalFrameClosing

    private void playActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playActionPerformed
        // TODO add your handling code here:
        play();
    }//GEN-LAST:event_playActionPerformed

    private void stopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopActionPerformed
        // TODO add your handling code here:
        stop();
    }//GEN-LAST:event_stopActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup grupoBotones;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JToggleButton play;
    private javax.swing.JToggleButton stop;
    // End of variables declaration//GEN-END:variables
}
