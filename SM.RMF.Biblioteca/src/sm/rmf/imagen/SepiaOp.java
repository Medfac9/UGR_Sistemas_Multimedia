package sm.rmf.imagen;

import java.awt.Color;
import java.awt.image.BufferedImage;
import sm.image.BufferedImageOpAdapter;

/**
 * En esta clase se obtiene una foto con filtro sepia. Para ello cogemos la foto seleccionada, se recorren todos sus pixeles 
 * y con una fórmula se le da una tonalidad sepia a todos sus pixeles
 */

public class SepiaOp extends BufferedImageOpAdapter{
    
    /**
     * Constructor de la clase SepiaOp
     */
    
    public SepiaOp () {
    }

    /**
     * Función para cada pixel se le aplica una tonalidad sepia
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
                
                int sepiaR = (int) Math.min(255, 0.393*color.getRed() + 0.769*color.getGreen() + 0.189*color.getBlue());
                int sepiaG = (int) Math.min(255, 0.349*color.getRed() + 0.686*color.getGreen() + 0.168*color.getBlue());
                int sepiaB = (int) Math.min(255, 0.272*color.getRed() + 0.534*color.getGreen() + 0.131*color.getBlue());
                
                Color c = new Color(sepiaR, sepiaG, sepiaB);
                src.setRGB(x, y, c.getRGB());
            }
        }
        return dest;
    }
}
