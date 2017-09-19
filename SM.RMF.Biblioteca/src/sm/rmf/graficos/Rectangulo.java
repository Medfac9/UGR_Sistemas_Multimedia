package sm.rmf.graficos;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Point2D;

/**
 * En esta clase se implementa todo lo necesario para poder dibujar un rectángulo. Para ello
 * usaremos dos puntos, el primero indica donde comienza a dibujarse el rectángulo y el segundo
 * donde finaliza.
 * @author: Rafael Medina Facal
 */

public class Rectangulo extends Figuras{
   
    /**
     * Constructor de Rectangulo
     * @param cBorde Color seleccionado para dibujar el rectángulo
     * @param n Tamaño seleccionado para el grosor del rectángulo
     */
    
    public Rectangulo(Color cBorde, int n) {        
        colorBorde = cBorde;
        numero = n;
    }
    
    private Rectangle rectangulo = new Rectangle();
    private Rectangle rectanguloEditar = new Rectangle();
    
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
        rectangulo.setLocation((Point)pos);
    }

    /**
     * Dibujar. Gracias a este método podremos dibujar un rectángulo con un determinado color, grosor,
     * discontinuidad y degradado. También dibuja un rectángulo al rededor de la figura cuando se selecciona
     * el modo editar
     * @param g2d Parametro de tipo Graphics2D el cual nos permite dibujar diversas figuras
     */
    
    @Override
    public void draw(Graphics2D g2d) {
        //Edición
        if(editar){
            rectanguloEditar = rectangulo.getBounds();
            rectanguloEditar.height = rectanguloEditar.height + 6;
            rectanguloEditar.width = rectanguloEditar.width + 6;
            rectanguloEditar.x = rectanguloEditar.x - 3;
            rectanguloEditar.y = rectanguloEditar.y - 3;
            patronDiscontinuidad[0] = 10.0f;
            trazo = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 1.0f, patronDiscontinuidad, 0.0f);
            g2d.setStroke(trazo);
            g2d.draw(rectanguloEditar);
        }
        //Trazo
        if(continuo){
            trazo = new BasicStroke(numero);
        }
        else{
            patronDiscontinuidad[0] = 10.0f;
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
            g2d.fill(rectangulo);
        if(degradadoVertical){
            g2d.setPaint(new GradientPaint(rectangulo.x, rectangulo.y, colorFondo, rectangulo.x, rectangulo.y + (rectangulo.height / 2), colorFondoSecundario));
            g2d.fill(rectangulo);
        }
        if(degradadoHorizontal){
            g2d.setPaint(new GradientPaint(rectangulo.x, rectangulo.y, colorFondo, rectangulo.x + (rectangulo.width / 2), rectangulo.y, colorFondoSecundario));
            g2d.fill(rectangulo);
        }
        //Color bordes
        g2d.setColor(getColorBorde());
        g2d.draw(rectangulo);
    }
    
    /**
     * Contiene la figura. Este método nos devuelve un booleano diciendonos si donde pincha el ratón 
     * contiene la figura para poder moverla o no.
     * @param p Posición del ratón
     * @return Devuelve true si pincha en la figura y false si pincha fuera
     */

    @Override
    public boolean getSelectedForma(Point2D p) {
        return rectanguloEditar.contains(p);
    }
    
    /**
     * Devuelve un rectángulo. Esta función nos devuelve un rectángulo que contiene la figura
     * @return Devuelve un rectángulo que contiene la figura
     */
    
    @Override
    public Rectangle getBounds() {
        return rectangulo.getBounds();
    }

    /**
     * Creación del rectángulo. Esta función nos crea el rectángulo pasandole dos puntos por parámetros
     * @param p1 Inicio del rectángulo 
     * @param p2 Fin del rectángulo
     * @param p3 No se utiliza
     */
    
    @Override
    public void setPosicion(Point p1, Point p2, Point p3) {
        rectangulo.setFrameFromDiagonal(p1, p2);
    }
}
