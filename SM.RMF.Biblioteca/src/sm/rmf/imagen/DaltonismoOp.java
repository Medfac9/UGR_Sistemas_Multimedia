package sm.rmf.imagen;

import java.awt.Color;
import java.awt.image.BufferedImage;
import sm.image.BufferedImageOpAdapter;

/**
 * En esta clase se obtiene una foto como sería vista por un daltónico. Para ello
 * cogemos la foto seleccionada, se recorren todos sus pixeles y las tonalidades
 * rojas se cambian por verdes y veceversa.
 * @author: Rafael Medina Facal
 */

public class DaltonismoOp extends BufferedImageOpAdapter{
    
    /**
     * Constructor de la clase DaltonismoOp
     */
    
    public DaltonismoOp () {
    }

    /**
     * Función para cambiar los colores rojos por verdes y viceversa
     * @param src Foto original
     * @param dest Foto destino
     * @return Devuelve la foto con los colores rojos y verdes cambiados entre si
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
                
                int R = color.getRed();
                int G = color.getGreen();
                int B = color.getBlue();
                
                Color c = new Color(G, R, B);
                src.setRGB(x, y, c.getRGB());
            }
        }
        return dest;
    }
}
