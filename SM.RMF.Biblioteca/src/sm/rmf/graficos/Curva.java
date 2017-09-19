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
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;

/**
 * En esta clase se implementa todo lo necesario para poder dibujar una curva. Para ello
 * usaremos tres puntos, el primero indica donde comienza a dibujarse la curva, el segundo
 * donde finaliza la curva y el tercero el grado de curvatura.
 * @author: Rafael Medina Facal
 */

public class Curva extends Figuras{
    
    /**
     * Constructor de Curva
     * @param cBorde Color seleccionado para dibujar la curva
     * @param n Tamaño seleccionado para el grosor de la curva
     */
    
    public Curva(Color cBorde, int n) {        
        colorBorde = cBorde;
        numero = n;
    }
    
    private QuadCurve2D curva = new QuadCurve2D.Float();
    private Rectangle curvaEditar = new Rectangle();
    
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
        double dx = pos.getX() - curva.getX1();
        double dy = pos.getY() - curva.getY1();
        Point2D p2nuevo = new Point2D.Double(curva.getX2() + dx, curva.getY2() + dy);
        Point2D p3nuevo = new Point2D.Double(curva.getCtrlX() + dx, curva.getCtrlY() + dy);
        curva.setCurve(pos, p3nuevo, p2nuevo);
    }

    /**
     * Dibujar. Gracias a este método podremos dibujar una curva con un determinado color, grosor
     * y discontinuidad. También dibuja un rectángulo al rededor de la figura cuando se selecciona
     * el modo editar
     * @param g2d Parametro de tipo Graphics2D el cual nos permite dibujar diversas figuras
     */
    
    @Override
    public void draw(Graphics2D g2d) {
        //Edición
        if(editar){
            curvaEditar = curva.getBounds();
            curvaEditar.height = curvaEditar.height + 6;
            curvaEditar.width = curvaEditar.width + 6;
            curvaEditar.x = curvaEditar.x - 3;
            curvaEditar.y = curvaEditar.y - 3;
            patronDiscontinuidad[0] = 10.0f;
            trazo = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 1.0f, patronDiscontinuidad, 0.0f);
            g2d.setStroke(trazo);
            g2d.draw(curvaEditar);
        }
        //Color
        g2d.setColor(colorBorde);
        //Trazo
        if(continuo){
            trazo = new BasicStroke(numero);
        }
        else{
            patronDiscontinuidad[0] = 5.0f;
            trazo = new BasicStroke(numero, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 1.0f, patronDiscontinuidad, 0.0f);
        }
        g2d.setStroke(trazo);
        g2d.setStroke(trazo);
        //Transparencia
        comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, trans);
        g2d.setComposite(comp);
        //Relleno y alisado
        if(alisado)
            g2d.setRenderingHints(render);
        g2d.draw(curva);
    }

    /**
     * Contiene la figura. Este método nos devuelve un booleano diciendonos si donde pincha el ratón 
     * contiene la figura para poder moverla o no.
     * @param p Posición del ratón
     * @return Devuelve true si pincha en la figura y false si pincha fuera
     */
    
    @Override
    public boolean getSelectedForma(Point2D p) {
        return curvaEditar.contains(p);       
    }

    /**
     * Devuelve un rectángulo. Esta función nos devuelve un rectángulo que contiene la figura
     * @return Devuelve un rectángulo que contiene la figura
     */
    
    @Override
    public Rectangle getBounds() {
        return curva.getBounds();
    }

    /**
     * Creación de la curva. Esta función nos crea la curva pasandole tres puntos por parámetros
     * @param p1 Inicio de la curva
     * @param p2 Fin de la curva
     * @param p3 Grado de la curvatura
     */
    
    @Override
    public void setPosicion(Point p1, Point p2, Point p3) {
        curva.setCurve(p1, p3, p2);
    }
}
