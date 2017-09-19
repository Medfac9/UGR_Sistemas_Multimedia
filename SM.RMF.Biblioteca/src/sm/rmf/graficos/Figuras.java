package sm.rmf.graficos;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

/**
 * En esta clase se implementa todo lo necesario para poder dibujar una figura.
 * @author: Rafael Medina Facal
 */

public abstract class Figuras {

    public int numero = 1; //Inicializar tamaño borde
    public int tamLetra = 12; //inicializar tamaño letra
    public float trans = 0.99f; //Inicializar transparencia
    public String tipoFuente = "";//Inicializar el tipo de fuente
    public String escrito = ""; //Inicializar el texto
    
    public Color colorFondo = Color.BLACK; //Inicializar color
    public Color colorFondoSecundario = Color.BLACK; //Inicializar color
    public Color colorBorde = Color.BLACK; //Inicializar otro color
    
    public boolean negrita = false; //Negrita
    public boolean cursiva = false; //Cursiva
    public boolean subrayado = false; //Subrayado
    public boolean editar = false; //Mover
    public boolean fill = false; //Relleno
    public boolean alisado = false; //Alisado
    public boolean degradadoVertical = false; //Degradado vertical
    public boolean degradadoHorizontal = false; //Degradado horizontal
    public boolean continuo = true; //Discontinuidad
    
    /**
     * Función para dibujar
     * @param g2d Parametro de tipo Graphics2D el cual nos permite dibujar diversas figuras
     */
    
    public abstract void draw (Graphics2D g2d);
    
   /**
     * Localización de donde esta el ratón. Este método se utiliza a la hora de mover las figuras.
     * @param pos Posición donde se encuentra actualmente el ratón
     */
    
    public abstract void setLocation(Point2D pos);
    
    /**
     * Contiene la figura. Este método nos devuelve un booleano diciendonos si donde pincha el ratón 
     * contiene la figura para poder moverla o no.
     * @param p Posición del ratón
     * @return Devuelve true si pincha en la figura y false si pincha fuera
     */
    
    public abstract boolean getSelectedForma(Point2D p);
    
    /**
     * Devuelve un rectángulo. Esta función nos devuelve un rectángulo que contiene la figura
     * @return Devuelve un rectángulo que contiene la figura
     */
    
    public abstract Rectangle getBounds();
    
    /**
     * Creación de las formas. Esta función nos crea las formas pasandole tres puntos por parámetros
     * @param p1 Inicio de la forma 
     * @param p2 Fin de la forma
     * @param p3 Grado de la curvatura
     */
    
    public abstract void setPosicion(Point p1, Point p2, Point p3);
    
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
     * Poner relleno. Dependiendo si el botón este pulsado rellena la figura
     * @param fill Verdadero esta pulsado subrayado, false no lo esta
     */
    
    public void setFill(Boolean fill){
        this.fill = fill;
    }
    
    /**
     * Devuelve relleno. Esta función nos devuelve si la figura está rellena
     * @return Devuelve si está relleno
     */
    
    public boolean getRelleno(){
        return fill;
    }
    
    /**
     * Poner degradado vertical. Dependiendo si el botón este pulsado rellena la figura con degradado vertical
     * @param degradado  Verdadero esta pulsado degradado, false no lo esta
     */
    
    public void setDegradadoVertical(Boolean degradado){
        this.degradadoVertical = degradado;
    }
    
    /**
     * Poner degradado horizontal. Dependiendo si el botón este pulsado rellena la figura con degradado horizonal
     * @param degradado  Verdadero esta pulsado degradado, false no lo esta
     */
    
    public void setDegradadoHorizontal(Boolean degradado){
        this.degradadoHorizontal = degradado;
    }
    
    /**
     * Poner alisar. Dependiendo si el botón este pulsado alisa la figura
     * @param alisado  Verdadero esta pulsado alisar, false no lo esta
     */
    
    public void setAlisado(Boolean alisado){
        this.alisado = alisado;
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
    
    /**
     * Devuelve alisado. Esta función nos devuelve si la figura está alisada
     * @return Devuelve si está alisada
     */
    
    public boolean getAlisado(){
        return alisado;
    }
    
    /**
     * Obtener editar. Devuelve si la figura puede editarse
     * @param edit Verdadero esta pulsado editar, false no lo está
     */
    
    public void setEditar(Boolean edit){
        this.editar = edit;
    }
    
    /**
     * Devuelve editar. Esta función nos devuelve si la figura puede editarse
     * @return Devuelve si puede editarse
     */
    
    public boolean getEditar(){
        return editar;
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
     * Poner color del fondo. Dependiendo si el botón de colorear esta pulsado dibuja el fondo
     * @param color  color con el que rellena el fondo
     */
    
    public void setColorFondo(Color color){
        this.colorFondo = color;
    }
    
    /**
     * Obtener color del fondo. Devuelve el color del fondo
     * @return Color del fondo
     */
    
    public Color getColorFondo(){
        return colorFondo;
    }
    
    /**
     * Poner color del fondo secundario. Dependiendo si el botón de colorear esta pulsado dibuja el fondo
     * @param color  color con el que rellena el fondo secundario
     */
    
    public void setColorFondoSecundario(Color color){
        this.colorFondoSecundario = color;
    }
    
    /**
     * Obtener color del fondo secundario. Devuelve el color del fondo secundario
     * @return Color del fondo secundario
     */
    
    public Color getColorFondoSecundario(){
        return colorFondoSecundario;
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
    * Devuelve el grado de transparendia. Devuelve el grado de la transparencia de la figura 
    * @return Grado de la transparencia
    */
    
    public float getTransparencia(){
        return trans;
    }
    
    /**
     * Poner transparencia. Dependiendo el grado de la transparencia
     * @param f  Grado de la transparencia
     */
    
    public void setTransparencia(float f){
        trans = f;
    }
    
    /**
     * Devuelve la continuidad. Devuelve si la figura es continua o discontinua
     * @return Devuelve si es continua o discontinua la figura
     */
    
    public boolean getContinuidad(){
        return continuo;
    }
    
    /**
     * Poner discontinuidad. Si el boton de discontinuidad está marcado pone la figura discontinua o continua
     * @param dis Verdadero si la figura es continua y falso si esta discontinua
     */
    
    public void setContinuidad(Boolean dis){
        this.continuo = dis;
    }  
    
}
