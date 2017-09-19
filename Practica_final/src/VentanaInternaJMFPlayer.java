import java.awt.Component;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.media.Buffer;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.Player;
import javax.media.control.FrameGrabbingControl;
import javax.media.format.VideoFormat;
import javax.media.util.BufferToImage;

public class VentanaInternaJMFPlayer extends javax.swing.JInternalFrame {

    private Player player = null;
    
    public VentanaInternaJMFPlayer(File f) {
        initComponents();
        String sfichero = "file:" + f.getAbsolutePath();
        MediaLocator ml = new MediaLocator(sfichero);
        try {
            player = Manager.createRealizedPlayer(ml);
            Component vc = player.getVisualComponent();
            if(vc!=null)add(vc, java.awt.BorderLayout.CENTER);
            Component cpc = player.getControlPanelComponent();
            if(cpc!=null)add(cpc, java.awt.BorderLayout.SOUTH);
            this.pack();
        }
        catch(Exception e) {
            System.err.println("VentanaInternaJMFPlayer: "+e);
            player = null;
        }
    }

    public static VentanaInternaJMFPlayer getInstance(File f){
        VentanaInternaJMFPlayer v = new VentanaInternaJMFPlayer(f);
        if(v.player!=null) 
            return v;
        else 
            return null;
    }
    
    public BufferedImage getFrame(Player player){
        FrameGrabbingControl fgc;
        String claseCtr = "javax.media.control.FrameGrabbingControl"; 
        fgc = (FrameGrabbingControl)player.getControl(claseCtr); 
        Buffer bufferFrame = fgc.grabFrame();
        BufferToImage bti;
        bti=new BufferToImage((VideoFormat)bufferFrame.getFormat()); 
        Image img = bti.createImage(bufferFrame);
        return (BufferedImage)img;
    }
    
    public BufferedImage capturaVideo(){
        BufferedImage img = getFrame(player);
        return img;
    }
    
    public void play() {
        if (player != null) {
            try {
                player.start();
            } 
            catch (Exception e) {
                System.err.println("VentanaInternaJMFPlayer: "+e);
            }
        } 
    }
    
    public void close() {
        if (player != null) {
            try {
                player.close();
            } 
            catch (Exception e) {
                System.err.println("VentanaInternaJMFPlayer: "+e);
            }
        } 
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

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

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
        close();
    }//GEN-LAST:event_formInternalFrameClosing

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
