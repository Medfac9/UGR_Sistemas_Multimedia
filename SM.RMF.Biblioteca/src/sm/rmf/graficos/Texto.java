package sm.rmf.graficos;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.font.TextAttribute;
import java.awt.geom.Point2D;
import java.util.Map;

/**
 * En esta clase se implementa todo lo necesario para poder escribir un texto. Para ello
 * usaremos un punto el cual nos indicará donde queremos colocar el texto.
 * @author: Rafael Medina Facal
 */

public class Texto extends Figuras{
    
    /**
     * Constructor de Texto
     * @param cBorde Color seleccionado para dibujar el texto
     * @param n Tamaño seleccionado para el grosor del texto
     * @param ne El texto está en negrita
     * @param cu El texto está en cursiva
     * @param su El texto está subrayado
     * @param s  Tipo de fuente
     * @param es Texto a escribir
     */
    
    public Texto(Color cBorde, int n, Boolean ne, Boolean cu, Boolean su, String s, String es) {        
        colorBorde = cBorde;
        tamLetra = n;
        negrita = ne;
        cursiva = cu;
        subrayado = su;
        tipoFuente = s;
        escrito = es;
    }
    
    private Point posicion = new Point(-100, -100);
    private Rectangle textoEditar = new Rectangle();
    
    private RenderingHints render = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //Inicializar renderizado
    private Stroke trazo; //Grosor
    private Composite comp; //Transparencia
    
    private Font fuente;
    
    float discon = 0;
    float patronDiscontinuidad[] = {discon};
    
    /**
     * Localización de donde esta el ratón. Este método se utiliza a la hora de mover las figuras.
     * @param pos Posición donde se encuentra actualmente el ratón
     */
    
    @Override
    public void setLocation(Point2D pos){
        posicion.setLocation(pos);
    }

    /**
     * Dibujar. Gracias a este método podremos dibujar el texto con un determinado color, grosor
     * y características (negrita, cursiva, subrayado y tipo de letra). También dibuja un rectángulo 
     * al rededor de la figura cuando se selecciona el modo editar
     * @param g2d Parametro de tipo Graphics2D el cual nos permite dibujar diversas figuras
     */
    
    @Override
    public void draw(Graphics2D g2d) {
        //Color
        g2d.setColor(colorBorde);
        //Editar
        if(editar){
            Rectangle r = new Rectangle(posicion.x , posicion.y - 50, 70, 50);
            textoEditar = r.getBounds();
            textoEditar.height = textoEditar.height + 6;
            textoEditar.width = textoEditar.width + 6;
            textoEditar.x = textoEditar.x - 3;
            textoEditar.y = textoEditar.y - 3;
            patronDiscontinuidad[0] = 10.0f;
            trazo = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 1.0f, patronDiscontinuidad, 0.0f);
            g2d.setStroke(trazo);
            g2d.draw(textoEditar);
        } 
        //Fuente
        if(negrita && !cursiva && !subrayado){
            fuente = new Font(tipoFuente, Font.BOLD, getTamLetra());
            g2d.setFont(fuente);
        }
        else if(cursiva && !negrita && !subrayado){
            fuente = new Font(tipoFuente, Font.ITALIC, getTamLetra());
            g2d.setFont(fuente);
        }
        else if(!cursiva && !negrita && subrayado){
            fuente = new Font(tipoFuente, Font.PLAIN, getTamLetra());
            Map atributosTexto = fuente.getAttributes();
            atributosTexto.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
            g2d.setFont(fuente.deriveFont(atributosTexto));
        }
        else if(negrita && cursiva && !subrayado){
            fuente = new Font(tipoFuente, Font.BOLD | Font.ITALIC, getTamLetra());
            g2d.setFont(fuente);
        }
        else if(negrita && subrayado && !cursiva){
            fuente = new Font(tipoFuente, Font.BOLD, getTamLetra());
            Map atributosTexto = fuente.getAttributes();
            atributosTexto.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
            g2d.setFont(fuente.deriveFont(atributosTexto));
        }
        else if(cursiva && subrayado && ! negrita){
            fuente = new Font(tipoFuente, Font.ITALIC, getTamLetra());
            Map atributosTexto = fuente.getAttributes();
            atributosTexto.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
            g2d.setFont(fuente.deriveFont(atributosTexto));
        }
        else if(cursiva && subrayado && negrita){
            fuente = new Font(tipoFuente, Font.BOLD | Font.ITALIC, getTamLetra());
            Map atributosTexto = fuente.getAttributes();
            atributosTexto.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
            g2d.setFont(fuente.deriveFont(atributosTexto));
        }
        else if(!negrita && !cursiva && !subrayado){
            fuente = new Font(tipoFuente, Font.PLAIN, getTamLetra());
            g2d.setFont(fuente);     
        }
        //Transparencia
        comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, trans);
        g2d.setComposite(comp);
        //Relleno y alisado
        if(alisado)
            g2d.setRenderingHints(render);
        g2d.drawString(getTexto(), posicion.x, posicion.y);
    }

    /**
     * Contiene la figura. Este método nos devuelve un booleano diciendonos si donde pincha el ratón 
     * contiene la figura para poder moverla o no.
     * @param p Posición del ratón
     * @return Devuelve true si pincha en la figura y false si pincha fuera
     */
    
    @Override
    public boolean getSelectedForma(Point2D p) {
        return textoEditar.contains(p);
    }
    /**
     * Devuelve un rectángulo. Esta función nos devuelve un rectángulo que contiene la figura
     * @return Devuelve un rectángulo que contiene la figura
     */

    @Override
    public Rectangle getBounds() {
        Rectangle r = new Rectangle(posicion.x, posicion.y, 15, 30);
        return r.getBounds();
    }

    /**
     * Creación del texto. Esta función nos crea un texto pasandole un punto por parámetros
     * @param p1 Inicio de la curva
     * @param p2 No se usa
     * @param p3 No se usa
     */
    
    @Override
    public void setPosicion(Point p1, Point p2, Point p3) {
        posicion.setLocation(p1);
    }
}
