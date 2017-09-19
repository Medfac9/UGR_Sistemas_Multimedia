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
 * En esta clase se implementa todo lo necesario para poder dibujar una línea. Para ello
 * usaremos dos puntos, el primero indica donde comienza a dibujarse la línea y el segundo
 * donde finaliza.
 * @author: Rafael Medina Facal
 */

public class Linea extends Figuras{
    
    /**
     * Constructor de Linea
     * @param cBorde Color seleccionado para dibujar la línea
     * @param n Tamaño seleccionado para el grosor de la línea
     */
    
    public Linea(Color cBorde, int n) {        
        colorBorde = cBorde;
        numero = n;
    }
    
    private Line2D linea = new Line2D.Float();
    private Rectangle lineaEditar = new Rectangle();
    
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
        double dx = pos.getX() - linea.getX1();
        double dy = pos.getY() - linea.getY1();
        Point2D p2nuevo = new Point2D.Double(linea.getX2() + dx, linea.getY2() + dy); 
        linea.setLine(pos,p2nuevo);
    }

    /**
     * Dibujar. Gracias a este método podremos dibujar una linea con un determinado color, grosor y
     * discontinuidad. También dibuja un rectángulo al rededor de la figura cuando se selecciona
     * el modo editar
     * @param g2d Parametro de tipo Graphics2D el cual nos permite dibujar diversas figuras
     */
    
    @Override
    public void draw(Graphics2D g2d) {
        //Edición
        if(editar){
            lineaEditar = linea.getBounds();
            lineaEditar.height = lineaEditar.height + 6;
            lineaEditar.width = lineaEditar.width + 6;
            lineaEditar.x = lineaEditar.x - 3;
            lineaEditar.y = lineaEditar.y - 3;
            patronDiscontinuidad[0] = 10.0f;
            trazo = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 1.0f, patronDiscontinuidad, 0.0f);
            g2d.setStroke(trazo);
            g2d.draw(lineaEditar);
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
        //Transparencia
        comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, trans);
        g2d.setComposite(comp);
        //Relleno y alisado
        if(alisado)
            g2d.setRenderingHints(render);
        g2d.draw(linea);
    }

    /**
     * Contiene la figura. Este método nos devuelve un booleano diciendonos si donde pincha el ratón 
     * contiene la figura para poder moverla o no.
     * @param p Posición del ratón
     * @return Devuelve true si pincha en la figura y false si pincha fuera
     */
    
    @Override
    public boolean getSelectedForma(Point2D p) {
        return lineaEditar.contains(p);
    }

    /**
     * Devuelve un rectángulo. Esta función nos devuelve un rectángulo que contiene la figura
     * @return Devuelve un rectángulo que contiene la figura
     */
    
    @Override
    public Rectangle getBounds() {
        return linea.getBounds();
    }
    
    /**
     * Creación de la línea. Esta función nos crea la línea pasandole dos puntos por parámetros
     * @param p1 Inicio de la línea 
     * @param p2 Fin de la línea
     * @param p3 No se utiliza
     */

    @Override
    public void setPosicion(Point p1, Point p2, Point p3) {
        linea.setLine(p1, p2);
    }
}
