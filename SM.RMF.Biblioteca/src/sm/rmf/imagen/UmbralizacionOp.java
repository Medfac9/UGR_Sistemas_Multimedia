package sm.rmf.imagen;

import java.awt.Color;
import java.awt.image.BufferedImage;
import sm.image.BufferedImageOpAdapter;

/**
 * En esta clase se obtiene una foto en blanco y negro que depende de el umbral. Para ello
 * cogemos la foto seleccionada, se recorren todos sus pixeles y si la suma del RGB del pixel es
 * mayor que el umbral se pinta blanco, sino negro
 * @author: Rafael Medina Facal
 */

public class UmbralizacionOp extends BufferedImageOpAdapter{ 
    
    private int umbral = 0;
    
    /**
     * Constructor de la clase DaltonismoOp
     * @param umbral Grado del umbral
     */
    
    public UmbralizacionOp(int umbral) {
        this.umbral = umbral;
    }
    
    /**
     * Función para cambiar los colores por blanco y negro dependiendo su grado de umbralización
     * @param src Foto original
     * @param dest Foto destino
     * @return Devuelve la foto en blanco y negro
     */
    
    @Override
    public BufferedImage filter(BufferedImage src, BufferedImage dest){
        if (src == null) {
            throw new NullPointerException("src image is null");
        }
        if (dest == null) {
            dest = createCompatibleDestImage(src, null);
        }
        for (int x = 0; x < src.getWidth(); x++) {
            for (int y = 0; y < src.getHeight(); y++) {
                Color color = new Color(src.getRGB(x, y));
                
                int intensidad = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                int bit;
                
                if(intensidad >= umbral)
                    bit = 255;
                else
                    bit = 0;
                
                Color c = new Color(bit, bit, bit);
                dest.setRGB(x, y, c.getRGB());
            }
        } 
        return dest;
    } 
}

