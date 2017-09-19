package sm.rmf.iu;

import sm.rmf.graficos.Elipse;
import sm.rmf.graficos.Punto;
import sm.rmf.graficos.Figuras;
import sm.rmf.graficos.Linea;
import sm.rmf.graficos.Texto;
import sm.rmf.graficos.Rectangulo;
import sm.rmf.graficos.Curva;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.image.BufferedImage;

/**
 * Clase donde se dibujan todas las figuras y fotos. En esta clase se podrán dibujar todas las formas con sus respectivos colores,
 * además se podrán abrir imagenes y pintar sobre ellas
 * @author: Rafael Medina Facal
 */

public class Lienzo2DFinal extends javax.swing.JPanel {
    
    private Figuras forma;

    private int seleccionada; //Para saber que herramienta esta seleccionada
    private Point punto;
    private Point distancia;
    private Rectangle clipArea = new Rectangle(0, 0, 100, 100);
    private BufferedImage img;
    private int cont = 0;
    private Point p1 = new Point(-10,-10); //Inicializar un punto
    private Point p2 = new Point(-10,-10); //Inicializar otro punto
    private Point p3 = new Point(-10,-10); //Inicializar otro punto
     
    public Color colorBorde = Color.BLACK; //Inicializar otro color
    public int numero = 1; //Inicializar tamaño borde
    public int tamLetra = 12; //inicializar tamaño letra
    public String tipoFuente = "";//Inicializar el tipo de fuente
    public boolean negrita = false; //Negrita
    public boolean cursiva = false; //Cursiva
    public boolean subrayado = false; //Subrayado
    public String escrito = ""; //Inicializar el texto
    
    private boolean mover = false; //Editar
    private boolean marcado; // Figura pinchada
    
    /**
     * Constructor de la clase Lienzo2dFinal
     */
    
    public Lienzo2DFinal() {
        initComponents();
        forma = new Punto(colorBorde, numero);
    }
    
    /**
     * Metodo para dibujar. En el se podrá dibujar tanto figuras como fotos. Además tiene un marco
     * en el que no se podrá dibujar mas alla de el
     * @param g Variable de tipo Graphics la cual nos permite dibujar
     */
    
    @Override
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;
        
        //Marco
        marcoClipArea(g2d);
        //Recorte
        g2d.draw(clipArea);
        g2d.clip(clipArea);
        
        if(img != null) 
            g.drawImage(img, 0, 0, this);
           
        forma.draw(g2d);
    }
    
    /**
     * Metodo dibujar un marco al rededor del lienzo. Dibuja un marco discontinuo al rededor del lienzo
     * @param g2d Variable de tipo Graphics2d la cual nos permite dibujar
     */
    
    public void marcoClipArea(Graphics2D g2d){
        float patronDiscontinuidad[] = {5.0f, 10.0f};
        Stroke discontinuas = new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 1.0f, patronDiscontinuidad, 0.0f);
        g2d.setStroke(discontinuas);
    }
    
    /**
     * Metodo para crea la figura. Dependiendo de la figura seleccionada por el botón, se inicializará la 
     * figura correspondiente
     */
    
    private void createShape(){
        switch (seleccionada) {
            case 0:              
                forma = new Punto(colorBorde, numero);
                break;
            case 1:
                forma = new Linea(colorBorde, numero);
                break;
            case 2:
                forma = new Rectangulo(colorBorde, numero);
                break;
            case 3:
                forma = new Elipse(colorBorde, numero);
                break;
            case 4:
                forma = new Curva(colorBorde, numero);
                break;
            case 5:
                forma = new Texto(colorBorde, tamLetra, negrita, cursiva, subrayado, tipoFuente, escrito);
                break;
            default:
                break;
        }
    }
    
    /**
     * Metodo para actualizar la figura. Tras mover una figura se usará este método
     */
    
    private void updateShape(){
        forma.setPosicion(p1, p2, p3);
    }
    
    /**
     * Metodo volcar la figura en el lienzo o imagen.
     */
    
    private void volcado(){
        getFigura().setEditar(false);
        getImage(true);
    }
    
    /**
     * Metodo obtener la imagen
     * @return Devuelve la imagen
     */
    
    public BufferedImage getImage(){
        return img; 
    }
    
    /**
     * Metodo obtener la imagen
     * @param dibujarForma si esta a true devuelve una nuefa imagen con la figura volcada en la anterior imagen, 
     * si esta a false devuelve la imagen
     * @return Devuelve la imagen
     */
    
    public BufferedImage getImage(boolean dibujarForma)
    {
        if (dibujarForma)
        {
            BufferedImage salida = getImage();
            forma.draw(salida.createGraphics());
            forma = new Punto(colorBorde, numero);
            return salida;
        }
        else
        {
            return getImage();
        }
    }
    
    /**
     * Metodo ponerle el marco a la imagen abierta
     * @param img Imagen a la que se le pone el marco
     */
    
    public void setImage(BufferedImage img){
        this.img = img;
        if(img!=null) {
            setPreferredSize(new Dimension(img.getWidth(),img.getHeight()));
            clipArea = new Rectangle(0, 0, img.getWidth(), img.getHeight());
        }
    }
    
    /**
     * Metodo obtener la figura
     * @return Devuelve figura
     */
    
    public Figuras getFigura(){
        return forma;
    }
    
    /**
     * Metodo obtener si se puede mover o no la figura
     * @return Devuelve si se puede mover la figura
     */
    
    public boolean getMover(){
        return mover;
    }
    
    /**
     * Metodo para cambiar entre mover o no mover una figura
     * @param mover Devuelve figura
     */
    
    public void setMover(Boolean mover){
        this.mover = mover;
    }
    
    /**
     * Metodo para cambiar la figura seleccionada
     * @param seleccionada Figura seleccionada
     */
    
    public void setSeleccionado(int seleccionada){
        this.seleccionada = seleccionada;
    }
    
    /**
     * Metodo obtener la forma seleccionada
     * @return Devuelve forma seleccionada
     */
    
    public int getSeleccionado(){
        return seleccionada;
    }
    
    /**
     * Poner color del borde. Dependiendo si el botón de colorear esta pulsado dibuja el borde
     * @param color  color con el que rellena el borde
     */
    
    public void setColorBorde(Color color){
        this.colorBorde = color;
    }
    
    /**
     * Obtener color del borde. Devuelve el color del borde
     * @return Color del borde
     */
    
    public Color getColorBorde(){
        return colorBorde;
    }
    
    /**
     * Tamaño del borde. Devuelve el tamaño del borde de la figura
     * @return Tamaño del borde
     */
    
    public int getContador(){
        return numero;
    }
    
    /**
     * Poner contador. Dependiendo  número seleccionado modifica el tamaño del borde
     * @param n  Tamaño del borde
     */
    
    public void setContador(int n){
        numero = n;
    }
    
    /**
     * Poner negrita. Dependiendo si el botón este pulsado pone a negrita la letra
     * @param n Verdadero esta pulsado negrita, false no lo esta
     */
    
    public void setNegrita(Boolean n){
        this.negrita = n;
    }
    
    /**
     * Devuelve negrita. Esta función nos devuelve si la letra está a negrita
     * @return Devuelve si está a negrita
     */
    
    public boolean getNegrita(){
        return negrita;
    }
    
    /**
     * Poner cursiva. Dependiendo si el botón este pulsado pone a cursiva la letra
     * @param c Verdadero esta pulsado cursiva, false no lo esta
     */
    
    public void setCursiva(Boolean c){
        this.cursiva = c;
    }
    
    /**
     * Devuelve cursiva. Esta función nos devuelve si la letra está a cursiva
     * @return Devuelve si está a cursiva
     */
    
    public boolean getCursiva(){
        return cursiva;
    }
    
    /**
     * Poner subrayado. Dependiendo si el botón este pulsado subraya la letra
     * @param s Verdadero esta pulsado subrayado, false no lo esta
     */
    
    public void setSubrayado(Boolean s){
        this.subrayado = s;
    }
    
    /**
     * Devuelve subrayado. Esta función nos devuelve si la letra está subrayada
     * @return Devuelve si está subrayado
     */
    
    public boolean getSubrayado(){
        return subrayado;
    }
    
    /**
     * Poner letra. Dependiendo si el tipo de letra seleccionada la cambia
     * @param s Tipo de letra
     */
    
    public void setTipoLetra(String s){
        this.tipoFuente = s;
    }
    
    /**
     * Obtener letra. Devuelve el tipo de letra
     * @return Tipo de letra
     */
    
    public String getTipoLetra(){
        return tipoFuente;
    }
    
    /**
     * Tamaño de la letra. Devuelve el tamaño de la letra 
     * @return Tamaño de la letra
     */
    
    public int getTamLetra(){
        return tamLetra;
    }
    
    /**
     * Poner tamaño de la letra. Dependiendo  número seleccionado modifica el tamaño de la letra
     * @param n  Tamaño de la letra
     */
    
    public void setTamLetra(int n){
        tamLetra = n;
    }
    
    /**
     * Poner texto. Cambia el texto escrito
     * @param s Texto escrito 
     */
    
    public void setTexto(String s){
        this.escrito = s;
    }
    
    /**
     * Obtener texto. Devuelve el texto escrito
     * @return Texto
     */
    
    public String getTexto(){
        return escrito;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(204, 204, 204));
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    
    /**
     * Presionar el ratón. Al presionar el ratón, si mover = true se calcula la distancia entre donde se pincha y el inicio
     * de la figura. Si mover = false si está la curva seleccionada y es el primero paso crea la curva, si es el segundo paso 
     * toma el punto de control. Sino crea la figura que este seleccionada
     * @param evt Evento del ratón
     */
    
    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        // TODO add your handling code here:
        if(mover){
            marcado = forma.getSelectedForma(evt.getPoint());
            if(marcado){
                setCursor(new Cursor(Cursor.MOVE_CURSOR));
                punto = new Point (forma.getBounds().x, forma.getBounds().y);
                distancia = new Point (evt.getPoint().x - punto.x, evt.getPoint().y - punto.y);
            }
        }
        else{
            if(seleccionada == 4)
            {
                if (cont == 0){
                    volcado();
                    p1 = evt.getPoint();
                    createShape();
                    forma.setLocation(p1);
                }
                else{
                    p3 = evt.getPoint();
                    updateShape();
                }
            }
            else{
                volcado();
                p1 = evt.getPoint();
                createShape();
                forma.setLocation(p1);
            }
        }
        repaint();
    }//GEN-LAST:event_formMousePressed

    /**
     * Soltar el ratón. Al soltar el ratón, si mover = true se cambia marcado a false y el cursor. Si mover = false 
     * si el contador es uno lo ponemos a cero y sino lo ponemos a uno 
     * @param evt Evento del ratón
     */
    
    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        // TODO add your handling code here:
        this.formMouseDragged(evt);
        
        if (marcado){
            setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
            marcado = false;
        }
        else{
            if(cont == 1)
                cont = 0;
            else
                cont = 1;
        }
    }//GEN-LAST:event_formMouseReleased

    /**
     * Mover el ratón. Al mover el ratón, si mover = true se calcula la posicion del raton. Si mover = false y esta seleccionada
     * la curva y el contador es cero ponemos el segundo punto de la linea y si el contador es uno ponemos el tercer punto
     * de la linea. Si es cualquier otra figura se actualiza la forma y se pone el segundo punto
     * @param evt Evento del ratón
     */
    
    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        // TODO add your handling code here:
        if(mover){
           if (marcado == true){
                Point ptr = new Point(evt.getPoint().x - distancia.x, evt.getPoint().y - distancia.y);
                forma.setLocation(ptr);
           }
        }
        else{
            if(seleccionada == 4)
            {
                if (cont == 0){
                    p2 = evt.getPoint();
                    updateShape();
                }
                else{
                    p3 = evt.getPoint();
                    updateShape();
                }
            }
            else{
                p2 = evt.getPoint();
                updateShape();
            }
        }
        repaint();
    }//GEN-LAST:event_formMouseDragged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
