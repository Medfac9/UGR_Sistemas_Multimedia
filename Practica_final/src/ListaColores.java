import java.awt.Color;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

public class ListaColores extends JPanel implements ListCellRenderer{
    
    public ListaColores() {
        initComponents();
        setOpaque(true);
    }
    
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)  
    {   
        b.setText("");
        b.setBackground((Color)value);     
        return this;  
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        b = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(45, 45));
        setLayout(new java.awt.BorderLayout());

        b.setText("Boton");
        b.setPreferredSize(new java.awt.Dimension(30, 30));
        add(b, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton b;
    // End of variables declaration//GEN-END:variables
}
