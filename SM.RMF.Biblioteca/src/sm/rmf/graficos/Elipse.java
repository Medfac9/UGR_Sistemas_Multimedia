package sm.rmf.graficos;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

/**
 * En esta clase se implementa todo lo necesario para poder dibujar una elipse. Para ello
 * usaremos 2 puntos, el primero indica donde comienza a dibujarse la elipse y el segundo
 * donde finaliza.
 * @author: Rafael Medina Facal
 */

public class Elipse extends Figuras{
    
    /**
     * Constructor de Elipse
     * @param cBorde Color seleccionado para dibujar la elipse
     * @param n Tamaño seleccionado para el grosor de la elipse
     */
    public Elipse(Color cBorde, int n) {        
        colorBorde = cBorde;
        numero = n;
    }
    
    private Ellipse2D elipse = new Ellipse2D.Float();
    private Rectangle elipseEditar = new Rectangle();
    
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
        elipse.setFrame(pos, new Dimension(elipse.getBounds().width, elipse.getBounds().height));
    }

    /**
     * Dibujar. Gracias a este método podremos dibujar una elipse con un determinado color, grosor,
     * discontinuidad y degradado. También dibuja un rectángulo al rededor de la figura cuando se selecciona
     * el modo editar
     * @param g2d Parametro de tipo Graphics2D el cual nos permite dibujar diversas figuras
     */
    
    @Override
    public void draw(Graphics2D g2d) {
        //Edición
        if(editar){
            elipseEditar = elipse.getBounds();
            elipseEditar.height = elipseEditar.height + 6;
            elipseEditar.width = elipseEditar.width + 6;
            elipseEditar.x = elipseEditar.x - 3;
            elipseEditar.y = elipseEditar.y - 3;
            patronDiscontinuidad[0] = 10.0f;
            trazo = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 1.0f, patronDiscontinuidad, 0.0f);
            g2d.setStroke(trazo);
            g2d.draw(elipseEditar);
        }
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
        //Color relleno
        g2d.setColor(colorFondo);
        if(fill) 
            g2d.fill(elipse);
        if(degradadoVertical){
            g2d.setPaint(new GradientPaint((int)Math.floor(elipse.getX()), (int)Math.floor(elipse.getY()), colorFondo, (int)Math.floor(elipse.getX()), (int)Math.floor(elipse.getY() + (elipse.getHeight() / 2)), colorFondoSecundario));
            g2d.fill(elipse);
        }
        if(degradadoHorizontal){
            g2d.setPaint(new GradientPaint((int)Math.floor(elipse.getX()), (int)Math.floor(elipse.getY()), colorFondo, (int)Math.floor(elipse.getX() + (elipse.getWidth() / 2)), (int)Math.floor(elipse.getY()), colorFondoSecundario));
            g2d.fill(elipse);
        }
        //Color borde
        g2d.setColor(colorBorde);
        g2d.draw(elipse);
    }

    /**
     * Contiene la figura. Este método nos devuelve un booleano diciendonos si donde pincha el ratón 
     * contiene la figura para poder moverla o no.
     * @param p Posición del ratón
     * @return Devuelve true si pincha en la figura y false si pincha fuera
     */
    
    @Override
    public boolean getSelectedForma(Point2D p) {
        return elipseEditar.contains(p);
    }

    /**
     * Devuelve un rectángulo. Esta función nos devuelve un rectángulo que contiene la figura
     * @return Devuelve un rectángulo que contiene la figura
     */
    
    @Override
    public Rectangle getBounds() {
        return elipse.getBounds();
    }

    /**
     * Creación de la elipse. Esta función nos crea la elipse pasandole dos puntos por parámetros
     * @param p1 Inicio de la elipse
     * @param p2 Fin de la elipse
     * @param p3 No es utilizado
     */
    
    @Override
    public void setPosicion(Point p1, Point p2, Point p3) {
        elipse.setFrameFromDiagonal(p1, p2);
    }
}
