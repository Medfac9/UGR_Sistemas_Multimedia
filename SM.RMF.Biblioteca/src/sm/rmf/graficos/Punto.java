package sm.rmf.graficos;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 * En esta clase se implementa todo lo necesario para poder dibujar un punto. Para ello
 * usaremos un punto, el cual indica tanto donde inicia como donde finaliza el punto.
 * @author: Rafael Medina Facal
 */

public class Punto extends Figuras{
    
    /**
     * Constructor de Punto
     * @param cBorde Color seleccionado para dibujar el punto
     * @param n Tamaño seleccionado para el grosor del punto
     */
    
    public Punto(Color cBorde, int n) {        
        colorBorde = cBorde;
        numero = n;
    }
    
    private Line2D punto = new Line2D.Float();
    private Rectangle puntoEditar = new Rectangle();
    
    private RenderingHints render = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //Inicializar renderizado
    private Stroke trazo; //Grosor
    private Composite comp; //Transparencia
    
    float discon = 0;
    float patronDiscontinuidad[] = {discon};
    
    /**
     * Localización de donde esta el ratón. Este método se utiliza a la hora de mover las figuras.
     * @param pos Posición donde se encuentra actualmente el ratón
     */
    
    @Override
    public void setLocation(Point2D pos){
        double dx = pos.getX() - punto.getX1();
        double dy = pos.getY() - punto.getY1();
        Point2D newp2 = new Point2D.Double(punto.getX2() + dx, punto.getY2() + dy); 
        punto.setLine(pos,newp2);
    }
    
    /**
     * Dibujar. Gracias a este método podremos dibujar un punto con un determinado color y grosor. También 
     * dibuja un rectángulo al rededor de la figura cuando se selecciona el modo editar
     * @param g2d Parametro de tipo Graphics2D el cual nos permite dibujar diversas figuras
     */

    @Override
    public void draw(Graphics2D g2d) {
        //Edición
        if(editar){
            puntoEditar = punto.getBounds();
            puntoEditar.height = puntoEditar.height + 6;
            puntoEditar.width = puntoEditar.width + 6;
            puntoEditar.x = puntoEditar.x - 3;
            puntoEditar.y = puntoEditar.y - 3;
            patronDiscontinuidad[0] = 10.0f;
            trazo = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 1.0f, patronDiscontinuidad, 0.0f);
            g2d.setStroke(trazo);
            g2d.draw(puntoEditar);
        }
        //Color
        g2d.setColor(colorBorde);
        //Trazo
        trazo = new BasicStroke(numero);
        g2d.setStroke(trazo);
        //Transparencia
        comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, trans);
        g2d.setComposite(comp);
        //Relleno y alisado
        if(alisado)
            g2d.setRenderingHints(render);
        g2d.draw(punto);
    }
    
    /**
     * Contiene la figura. Este método nos devuelve un booleano diciendonos si donde pincha el ratón 
     * contiene la figura para poder moverla o no.
     * @param p Posición del ratón
     * @return Devuelve true si pincha en la figura y false si pincha fuera
     */
    
    @Override
    public boolean getSelectedForma(Point2D p){
        return puntoEditar.contains(p);
    }
    
    /**
     * Devuelve un rectángulo. Esta función nos devuelve un rectángulo que contiene la figura
     * @return Devuelve un rectángulo que contiene la figura
     */

    @Override
    public Rectangle getBounds() {
        return punto.getBounds();
    }

    /**
     * Creación del rectángulo. Esta función no es necesaria ya que el punto se dibuja donde
     * se cliquea el ratón.
     * @param p1 No se utiliza 
     * @param p2 No se utiliza
     * @param p3 No se utiliza
     */
    
    @Override
    public void setPosicion(Point p1, Point p2, Point p3) {
        
    }
}