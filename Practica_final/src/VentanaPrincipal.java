import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.ConvolveOp;
import java.awt.image.DataBuffer;
import java.awt.image.Kernel;
import java.awt.image.LookupOp;
import java.awt.image.LookupTable;
import java.awt.image.RescaleOp;
import java.awt.image.ShortLookupTable;
import java.awt.image.WritableRaster;
import java.io.File;
import static java.lang.Math.abs;
import static java.lang.Math.sin;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.SpinnerNumberModel;
import javax.swing.filechooser.FileNameExtensionFilter;
import sm.image.EqualizationOp;
import sm.image.KernelProducer;
import sm.image.LookupTableProducer;
import sm.rmf.imagen.SepiaOp;
import sm.image.TintOp;
import sm.rmf.imagen.DaltonismoOp;
import sm.rmf.imagen.UmbralizacionOp;

public class VentanaPrincipal extends javax.swing.JFrame {
    
    //Lista de colores
    private Color[] vColores = {Color.black, Color.red, Color.blue, Color.white, Color.yellow, Color.green};
    
    //Imagen
    private BufferedImage imagenFuente;
    
    //Punto
    private Point p;
    
    //Fuentes
    private GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    private String []fuentesSistema = ge.getAvailableFontFamilyNames();
    
    //Filtros para abrir
    private FileNameExtensionFilter filtroFotos = new FileNameExtensionFilter("Imagen [jpg, png, gif]", "jpg", "png","gif");
    private FileNameExtensionFilter filtroVideosJMF = new FileNameExtensionFilter("Video JMF [avi, mpeg, mov]", "avi", "mpeg", "mov");
    private FileNameExtensionFilter filtroAudios = new FileNameExtensionFilter("Audio [au, wav, aif, mp3, mid]", "au", "wav", "aif", "mp3", "mid");
    private FileNameExtensionFilter filtroVideosVLC = new FileNameExtensionFilter("Video VLC [avi, mpg, mpeg, mov, mp4]", "avi", "mp4","mpg", "mpeg", "mov");
    
    //Filtros para guardar imagen
    private FileNameExtensionFilter filtroJPG = new FileNameExtensionFilter("Imagen [jpg]", "jpg");
    private FileNameExtensionFilter filtroPNG = new FileNameExtensionFilter("Imagen [png]", "png");
    private FileNameExtensionFilter filtroGIF = new FileNameExtensionFilter("Imagen [gif]", "gif");
    
    //Filtros para guardar video
    private FileNameExtensionFilter filtroAVI = new FileNameExtensionFilter("Video [avi]", "avi");
    private FileNameExtensionFilter filtroMPG = new FileNameExtensionFilter("Video [mpg]", "mpg");
    private FileNameExtensionFilter filtroMPEG = new FileNameExtensionFilter("Video [mpeg]", "mpeg");
    private FileNameExtensionFilter filtroMOV = new FileNameExtensionFilter("Video [mov]", "mov");
    private FileNameExtensionFilter filtroMP4 = new FileNameExtensionFilter("Video [mp4]", "mp4");
    
    //Filtros para guardar sonido
    private FileNameExtensionFilter filtroAU = new FileNameExtensionFilter("Audio [au]", "au");
    private FileNameExtensionFilter filtroWAB = new FileNameExtensionFilter("Audio [wav]", "wav");
    private FileNameExtensionFilter filtroAIF = new FileNameExtensionFilter("Audio [aif]", "aif");
    private FileNameExtensionFilter filtroMP3 = new FileNameExtensionFilter("Audio [mp3]", "mp3");
    private FileNameExtensionFilter filtroMID = new FileNameExtensionFilter("Audio [mid]", "mid");
    
    //Comprobar extensiones
    private final String[] extensiones = new String[] {"jpg", "png", "gif", "wav", "au", "avi", "mov", "mpg", "mpeg", "mp4", "mpg", "aif", "mp3", "mid"};
    
    public VentanaPrincipal() {
        
        initComponents();
        
        //Agrupar los botones
        botonesHerramientas.add(punto);
        botonesHerramientas.add(linea);
        botonesHerramientas.add(rectangulo);
        botonesHerramientas.add(elipse);
        botonesHerramientas.add(curva);
        botonesHerramientas.add(texto);
        botonesHerramientas.add(editar);
        
        //Agrupar botones de rellenos
        rellenos.add(sinRelleno);
        rellenos.add(conRelleno);
        rellenos.add(degradadoVertical);
        rellenos.add(degradadoHorizontal);
        
        //Agrupar botones de trazos
        trazos.add(continuo);
        trazos.add(discontinuo);
        
        //Valores por defecto
        barraEstado.setText("Punto");
        SpinnerNumberModel nmb = new SpinnerNumberModel();
        SpinnerNumberModel nml = new SpinnerNumberModel();
        nmb.setMinimum(1);
        nml.setMinimum(12);
        contador.setModel(nmb);
        contador.setValue(1);
        tamLetra.setModel(nml);
        tamLetra.setValue(12);
        setTitle("Práctica final SM");
        
        //Lista de colores
        colores.setModel(new DefaultComboBoxModel(vColores));
        colores.setRenderer(new ListaColores());
        
        //Botones deshabilitados
        relleno.setEnabled(false);
        alisar.setEnabled(false);
        contador.setEnabled(false);
        trazo.setEnabled(false);
        colores.setEnabled(false);
        eleccionColores.setEnabled(false);
        
        //Tipo de fuente
        tipoLetra.setModel(new DefaultComboBoxModel(fuentesSistema));
        tipoLetra1.setModel(new DefaultComboBoxModel(fuentesSistema));
    }
    
    public void abrirVentana(){
        
        VentanaInterna vi = new VentanaInterna(this);
        
        escritorio.add(vi);
        vi.setVisible(true);
    }
    
    public void setSeleccionadaHerramienta(int seleccionada){
        
        switch (seleccionada) {
            case 0:
                //El punto esta seleccionado
                punto.setSelected(true);
                break;
                
            case 1:
                //La linea esta seleccionada
                linea.setSelected(true);
                break;
                
            case 2:
                //El rectangulo esta seleccionado
                rectangulo.setSelected(true);
                break;
                
            case 3:
                //La elipse esta seleccionada
                elipse.setSelected(true);
                break;
                
            case 4:
               //La curva esta seleccionada
               curva.setSelected(true);
               break;
               
            case 5:
                //El texto esta seleccionado
                texto.setSelected(true);
                break;
                
        }
    }
    
    public void setRelleno(boolean seleccionada){
        
        if(seleccionada == true)
            relleno.setSelected(true);
        else
            relleno.setSelected(false);
    }
    
    public void setAlisar(boolean seleccionada){
        
        if(seleccionada == true)
            alisar.setSelected(true);
        else
            alisar.setSelected(false);
    }
    
    public void setEditar(boolean seleccionada){
        
        if(seleccionada == true)
            editar.setSelected(true);
        else
            editar.setSelected(false);
    }
    
    public void setContador(int numero){
          contador.setValue(numero);
    }
    
    public void setTamLetra(int numero){
          tamLetra.setValue(numero);
    }
    
    public void setColorBorde(Color c){
        colores.setSelectedItem(c);
    }
    
    
    public void setPunto(Point p){
        this.p = p;
    }
    
    public JLabel getCoordenadas(){
        return coordenadas;
    }
    
    public JSlider getUmbral(){
        return umbral;
    }
    
    //Comprueba que el formato de un archivo seleccionado es correcto
    public boolean formatoCorrecto(File archivo){
        
        String formato = "";
        
        for(String i: extensiones){
            if(archivo.getName().toLowerCase().endsWith(i)){
                formato = i;
                return true;
            }
        }
        return false;
    }
    
    //Muestra mensajes de error
    public void mensaje (int mensaje){
        
        String muestra = "";
        
        switch(mensaje){
            case 1:
                muestra = "Error al abrir la imagen";
                break;
                
            case 2:
                muestra = "Error al guardar la imagen";
                break;
                
            case 3:
                muestra = "La imagen ya está en sRGB";
                break;
                
            case 4:
                muestra = "La imagen ha de ser sRGB";
                break;
                
            case 5:
                muestra = "Error al abrir el audio";
                break;
                
            case 6:
                muestra = "Error al guardar el audio";
                break;
                
            case 7:
                muestra = "Error al abrir el video";
                break;
                
            case 8:
                muestra = "Error al abrir la cam";
                break;
                
            case 9:
                muestra = "No se puede hacer una captura si usted no está viendo un video";
                break;
                
            case 10:
                muestra = "Con esta opción solo puede guardar fotos";
                break;
                
            case 11:
                muestra = "No se puede duplicar la ventana";
                break;
        }
        JOptionPane.showMessageDialog(null, muestra);
    }
    
    public LookupTable seno(double w){
        
        double K = 255.0; 
        double iteraccion = ((Math.PI/2.0/K));
        short valores[] = new short[256];
        
        for(int i = 0; i < 256; i++){
            valores[i] = (short) (K*abs(sin(w*(iteraccion*i))));
        }
        
        LookupTable lt = new ShortLookupTable(0, valores);
        
        return lt;
    }
    
    //Nos indica que tipo de ventana está seleccionada
    public int tipoVentana(){
        int ventana = 0;
        
        if(escritorio.getSelectedFrame() instanceof VentanaInterna)
            ventana = 0;
        else if(escritorio.getSelectedFrame() instanceof VentanaInternaReproductor)
            ventana = 1;
        else if(escritorio.getSelectedFrame() instanceof VentanaInternaGrabador)
            ventana = 2;
        else if(escritorio.getSelectedFrame() instanceof VentanaInternaVLCPlayer)
            ventana = 3;
        else if(escritorio.getSelectedFrame() instanceof VentanaInternaCamara)
            ventana = 4;
        else if(escritorio.getSelectedFrame() instanceof VentanaInternaJMFPlayer)
            ventana = 5;
        
        return ventana;
    }
    
    public void volcado(){
        alisar.setSelected(false);
        contador.setValue(1);
        colores.setSelectedItem(Color.BLACK);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        botonesHerramientas = new javax.swing.ButtonGroup();
        jCheckBoxMenuItem2 = new javax.swing.JCheckBoxMenuItem();
        tamImagen = new javax.swing.JDialog();
        jPanel9 = new javax.swing.JPanel();
        escalar = new javax.swing.JButton();
        redimensionar = new javax.swing.JButton();
        aceptar = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        anchoL = new javax.swing.JLabel();
        ancho = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        altoL = new javax.swing.JLabel();
        alto = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        ayuda = new javax.swing.JDialog();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        tipoRelleno = new javax.swing.JDialog();
        jPanel26 = new javax.swing.JPanel();
        aceptarRelleno = new javax.swing.JButton();
        cancelarRelleno = new javax.swing.JButton();
        jPanel27 = new javax.swing.JPanel();
        jPanel28 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel29 = new javax.swing.JPanel();
        sinRelleno = new javax.swing.JToggleButton();
        conRelleno = new javax.swing.JToggleButton();
        degradadoVertical = new javax.swing.JToggleButton();
        degradadoHorizontal = new javax.swing.JToggleButton();
        rellenos = new javax.swing.ButtonGroup();
        tipoTrazo = new javax.swing.JDialog();
        jPanel25 = new javax.swing.JPanel();
        jPanel30 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel31 = new javax.swing.JPanel();
        jPanel32 = new javax.swing.JPanel();
        aceptarTrazo = new javax.swing.JButton();
        cancelarTrazo = new javax.swing.JButton();
        jPanel33 = new javax.swing.JPanel();
        continuo = new javax.swing.JToggleButton();
        discontinuo = new javax.swing.JToggleButton();
        trazos = new javax.swing.ButtonGroup();
        tipoTexto = new javax.swing.JDialog();
        jPanel35 = new javax.swing.JPanel();
        jPanel36 = new javax.swing.JPanel();
        jPanel39 = new javax.swing.JPanel();
        tipoLetra = new javax.swing.JComboBox<>();
        tamLetra = new javax.swing.JSpinner();
        negrita = new javax.swing.JToggleButton();
        cursiva = new javax.swing.JToggleButton();
        subrayado = new javax.swing.JToggleButton();
        jPanel38 = new javax.swing.JPanel();
        aceptarLetra = new javax.swing.JButton();
        cancelarLetra = new javax.swing.JButton();
        jPanel37 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        escrito = new javax.swing.JTextPane();
        jPanel1 = new javax.swing.JPanel();
        barraFormas = new javax.swing.JToolBar();
        nuevo1 = new javax.swing.JButton();
        abrir1 = new javax.swing.JButton();
        guardar1 = new javax.swing.JButton();
        guardarAudio1 = new javax.swing.JButton();
        camara2 = new javax.swing.JButton();
        separador1 = new javax.swing.JToolBar.Separator();
        punto = new javax.swing.JToggleButton();
        linea = new javax.swing.JToggleButton();
        rectangulo = new javax.swing.JToggleButton();
        elipse = new javax.swing.JToggleButton();
        curva = new javax.swing.JToggleButton();
        texto = new javax.swing.JToggleButton();
        editar = new javax.swing.JToggleButton();
        separador2 = new javax.swing.JToolBar.Separator();
        colores = new javax.swing.JComboBox<>();
        eleccionColores = new javax.swing.JButton();
        separador3 = new javax.swing.JToolBar.Separator();
        contador = new javax.swing.JSpinner();
        trazo = new javax.swing.JButton();
        separador4 = new javax.swing.JToolBar.Separator();
        relleno = new javax.swing.JButton();
        alisar = new javax.swing.JToggleButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        negrita1 = new javax.swing.JToggleButton();
        cursiva1 = new javax.swing.JToggleButton();
        subrayado1 = new javax.swing.JToggleButton();
        tipoLetra1 = new javax.swing.JComboBox<>();
        separador5 = new javax.swing.JToolBar.Separator();
        captura = new javax.swing.JButton();
        duplicar = new javax.swing.JButton();
        barraEdicion = new javax.swing.JToolBar();
        jPanel4 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        filtros = new javax.swing.JComboBox<>();
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        contraste = new javax.swing.JButton();
        iluminar = new javax.swing.JButton();
        oscurecer = new javax.swing.JButton();
        negativo = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        sinusoidal = new javax.swing.JButton();
        sepia = new javax.swing.JButton();
        tintar = new javax.swing.JButton();
        ecualizar = new javax.swing.JButton();
        daltonico = new javax.swing.JButton();
        jPanel20 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        banda = new javax.swing.JButton();
        espacioColor = new javax.swing.JComboBox<>();
        jPanel18 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        aumentar = new javax.swing.JButton();
        disminuir = new javax.swing.JButton();
        barraSlider = new javax.swing.JToolBar();
        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        barraBrillo = new javax.swing.JSlider();
        jPanel5 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        umbral = new javax.swing.JSlider();
        jPanel23 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        gradoTransparencia = new javax.swing.JSlider();
        jPanel16 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        barraRotacion = new javax.swing.JSlider();
        escritorio = new javax.swing.JDesktopPane();
        jPanel2 = new javax.swing.JPanel();
        panelEstado = new javax.swing.JPanel();
        barraEstado = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        coordenadas = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        nuevo = new javax.swing.JMenuItem();
        abrir = new javax.swing.JMenuItem();
        guardar = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        abrirAudio = new javax.swing.JMenuItem();
        guardarAudio = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        abrirVideoJMF = new javax.swing.JMenuItem();
        abrirVideoVLC = new javax.swing.JMenuItem();
        camara = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        verBarraEstado = new javax.swing.JCheckBoxMenuItem();
        verBarraFormas = new javax.swing.JCheckBoxMenuItem();
        verBarraAtributos = new javax.swing.JCheckBoxMenuItem();
        verBarraVideo = new javax.swing.JCheckBoxMenuItem();
        verBarraEdicion = new javax.swing.JCheckBoxMenuItem();
        verBarraSlider = new javax.swing.JCheckBoxMenuItem();
        verBarraTexto = new javax.swing.JCheckBoxMenuItem();
        jMenu3 = new javax.swing.JMenu();
        nuevaImagen = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        acercaDe = new javax.swing.JMenuItem();

        jCheckBoxMenuItem2.setSelected(true);
        jCheckBoxMenuItem2.setText("jCheckBoxMenuItem2");

        tamImagen.setTitle("Tamaño imgen");
        tamImagen.setMinimumSize(new java.awt.Dimension(100, 100));
        tamImagen.setSize(new java.awt.Dimension(350, 200));

        jPanel9.setPreferredSize(new java.awt.Dimension(50, 30));
        jPanel9.setLayout(new java.awt.GridLayout(1, 0));

        escalar.setText("Escalar");
        escalar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                escalarActionPerformed(evt);
            }
        });
        jPanel9.add(escalar);

        redimensionar.setBackground(new java.awt.Color(0, 153, 153));
        redimensionar.setText("Redimensionar");
        redimensionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                redimensionarActionPerformed(evt);
            }
        });
        jPanel9.add(redimensionar);

        aceptar.setText("Aceptar");
        aceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aceptarActionPerformed(evt);
            }
        });
        jPanel9.add(aceptar);

        tamImagen.getContentPane().add(jPanel9, java.awt.BorderLayout.PAGE_END);

        jPanel10.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 45));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Captura de pantalla.png"))); // NOI18N
        jPanel10.add(jLabel1);

        jPanel11.setLayout(new java.awt.GridLayout(2, 2));

        anchoL.setText("Ancho: ");
        jPanel11.add(anchoL);

        ancho.setText("300");
        ancho.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                anchoKeyTyped(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                anchoKeyReleased(evt);
            }
        });
        jPanel11.add(ancho);

        jLabel2.setText("px");
        jPanel11.add(jLabel2);

        altoL.setText("Alto: ");
        jPanel11.add(altoL);

        alto.setText("300");
        alto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                altoKeyTyped(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                altoKeyReleased(evt);
            }
        });
        jPanel11.add(alto);

        jLabel3.setText("px");
        jPanel11.add(jLabel3);

        jPanel10.add(jPanel11);

        tamImagen.getContentPane().add(jPanel10, java.awt.BorderLayout.CENTER);

        ayuda.setTitle("Ayuda");
        ayuda.setMinimumSize(new java.awt.Dimension(400, 150));

        jLabel4.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel4.setText("Nombre del programa:");

        jLabel5.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel5.setText("Versión:");

        jLabel6.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel6.setText("Autor:");

        jLabel7.setText("Práctica final SM");

        jLabel8.setText("1.0");

        jLabel9.setText("Rafael Medina Facal");

        javax.swing.GroupLayout ayudaLayout = new javax.swing.GroupLayout(ayuda.getContentPane());
        ayuda.getContentPane().setLayout(ayudaLayout);
        ayudaLayout.setHorizontalGroup(
            ayudaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ayudaLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(ayudaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addGap(58, 58, 58)
                .addGroup(ayudaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        ayudaLayout.setVerticalGroup(
            ayudaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ayudaLayout.createSequentialGroup()
                .addContainerGap(34, Short.MAX_VALUE)
                .addGroup(ayudaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(ayudaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(ayudaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel9))
                .addGap(32, 32, 32))
        );

        tipoRelleno.setTitle("Tipos de relleno");
        tipoRelleno.setSize(new java.awt.Dimension(750, 200));

        jPanel26.setLayout(new java.awt.GridLayout(1, 3, 5, 0));

        aceptarRelleno.setText("Aceptar");
        aceptarRelleno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aceptarRellenoActionPerformed(evt);
            }
        });
        jPanel26.add(aceptarRelleno);

        cancelarRelleno.setText("Cancelar");
        cancelarRelleno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarRellenoActionPerformed(evt);
            }
        });
        jPanel26.add(cancelarRelleno);

        tipoRelleno.getContentPane().add(jPanel26, java.awt.BorderLayout.PAGE_END);

        jPanel27.setLayout(new java.awt.BorderLayout());

        jPanel28.setLayout(new java.awt.GridLayout(1, 0, 10, 0));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/sinRelleno.png"))); // NOI18N
        jPanel28.add(jLabel14);

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/conRelleno.png"))); // NOI18N
        jPanel28.add(jLabel13);

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/degradadoVertical.jpg"))); // NOI18N
        jPanel28.add(jLabel15);

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/degradadoHorizontal.jpg"))); // NOI18N
        jPanel28.add(jLabel10);

        jPanel27.add(jPanel28, java.awt.BorderLayout.PAGE_START);

        jPanel29.setLayout(new java.awt.GridLayout(1, 0));

        sinRelleno.setSelected(true);
        sinRelleno.setText("Sin relleno");
        sinRelleno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sinRellenoActionPerformed(evt);
            }
        });
        jPanel29.add(sinRelleno);

        conRelleno.setText("Con relleno");
        conRelleno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                conRellenoActionPerformed(evt);
            }
        });
        jPanel29.add(conRelleno);

        degradadoVertical.setText("Degradado vertical");
        degradadoVertical.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                degradadoVerticalActionPerformed(evt);
            }
        });
        jPanel29.add(degradadoVertical);

        degradadoHorizontal.setText("Degradado horizontal");
        degradadoHorizontal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                degradadoHorizontalActionPerformed(evt);
            }
        });
        jPanel29.add(degradadoHorizontal);

        jPanel27.add(jPanel29, java.awt.BorderLayout.CENTER);

        tipoRelleno.getContentPane().add(jPanel27, java.awt.BorderLayout.CENTER);

        tipoTrazo.setTitle("Tipos de trazos");
        tipoTrazo.setSize(new java.awt.Dimension(250, 200));

        jPanel25.setLayout(new java.awt.BorderLayout());

        jPanel30.setLayout(new java.awt.GridLayout(1, 0, 15, 0));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/continuas.png"))); // NOI18N
        jPanel30.add(jLabel11);

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/discontinuas.png"))); // NOI18N
        jPanel30.add(jLabel12);

        jPanel25.add(jPanel30, java.awt.BorderLayout.PAGE_START);

        jPanel31.setLayout(new java.awt.BorderLayout());

        jPanel32.setLayout(new java.awt.GridLayout(1, 0));

        aceptarTrazo.setLabel("Aceptar");
        aceptarTrazo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aceptarTrazoActionPerformed(evt);
            }
        });
        jPanel32.add(aceptarTrazo);

        cancelarTrazo.setLabel("Cancelar");
        cancelarTrazo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarTrazoActionPerformed(evt);
            }
        });
        jPanel32.add(cancelarTrazo);

        jPanel31.add(jPanel32, java.awt.BorderLayout.PAGE_END);

        jPanel33.setLayout(new java.awt.GridLayout(1, 0));

        continuo.setSelected(true);
        continuo.setText("Continuo");
        continuo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                continuoActionPerformed(evt);
            }
        });
        jPanel33.add(continuo);

        discontinuo.setText("Discontinuo");
        discontinuo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                discontinuoActionPerformed(evt);
            }
        });
        jPanel33.add(discontinuo);

        jPanel31.add(jPanel33, java.awt.BorderLayout.CENTER);

        jPanel25.add(jPanel31, java.awt.BorderLayout.CENTER);

        tipoTrazo.getContentPane().add(jPanel25, java.awt.BorderLayout.CENTER);

        tipoTexto.setSize(new java.awt.Dimension(500, 400));

        jPanel35.setLayout(new java.awt.BorderLayout());

        jPanel36.setLayout(new java.awt.BorderLayout());

        jPanel39.setLayout(new java.awt.GridLayout(1, 0));

        tipoLetra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tipoLetraActionPerformed(evt);
            }
        });
        jPanel39.add(tipoLetra);

        tamLetra.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tamLetraStateChanged(evt);
            }
        });
        jPanel39.add(tamLetra);

        negrita.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/negrita.png"))); // NOI18N
        negrita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                negritaActionPerformed(evt);
            }
        });
        jPanel39.add(negrita);

        cursiva.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cursiva.png"))); // NOI18N
        cursiva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cursivaActionPerformed(evt);
            }
        });
        jPanel39.add(cursiva);

        subrayado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/subrayado.png"))); // NOI18N
        subrayado.setToolTipText("Subrayado");
        subrayado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subrayadoActionPerformed(evt);
            }
        });
        jPanel39.add(subrayado);

        jPanel36.add(jPanel39, java.awt.BorderLayout.PAGE_START);

        jPanel38.setLayout(new java.awt.GridLayout(1, 0));

        aceptarLetra.setText("Aceptar");
        aceptarLetra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aceptarLetraActionPerformed(evt);
            }
        });
        jPanel38.add(aceptarLetra);

        cancelarLetra.setText("Cancelar");
        cancelarLetra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarLetraActionPerformed(evt);
            }
        });
        jPanel38.add(cancelarLetra);

        jPanel36.add(jPanel38, java.awt.BorderLayout.CENTER);

        jPanel35.add(jPanel36, java.awt.BorderLayout.PAGE_END);

        jPanel37.setLayout(new java.awt.BorderLayout());

        jScrollPane2.setViewportView(escrito);

        jPanel37.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel35.add(jPanel37, java.awt.BorderLayout.CENTER);

        tipoTexto.getContentPane().add(jPanel35, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1300, 800));

        jPanel1.setPreferredSize(new java.awt.Dimension(800, 540));
        jPanel1.setLayout(new java.awt.BorderLayout());

        barraFormas.setRollover(true);

        nuevo1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/nuevo.png"))); // NOI18N
        nuevo1.setToolTipText("Nuevo");
        nuevo1.setFocusable(false);
        nuevo1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        nuevo1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        nuevo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuevo1ActionPerformed(evt);
            }
        });
        barraFormas.add(nuevo1);

        abrir1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/abrir.png"))); // NOI18N
        abrir1.setToolTipText("Abrir");
        abrir1.setFocusable(false);
        abrir1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        abrir1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        abrir1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                abrir1ActionPerformed(evt);
            }
        });
        barraFormas.add(abrir1);

        guardar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/guardar.png"))); // NOI18N
        guardar1.setToolTipText("Guardar");
        guardar1.setFocusable(false);
        guardar1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        guardar1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        guardar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardar1ActionPerformed(evt);
            }
        });
        barraFormas.add(guardar1);

        guardarAudio1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/record24x24.png"))); // NOI18N
        guardarAudio1.setToolTipText("Guardar audio");
        guardarAudio1.setFocusable(false);
        guardarAudio1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        guardarAudio1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        guardarAudio1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardarAudio1ActionPerformed(evt);
            }
        });
        barraFormas.add(guardarAudio1);

        camara2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Camara.png"))); // NOI18N
        camara2.setToolTipText("Cámara");
        camara2.setFocusable(false);
        camara2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        camara2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        camara2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                camara2ActionPerformed(evt);
            }
        });
        barraFormas.add(camara2);
        barraFormas.add(separador1);

        punto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/punto.png"))); // NOI18N
        punto.setSelected(true);
        punto.setToolTipText("Punto");
        punto.setFocusable(false);
        punto.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        punto.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        punto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                puntoActionPerformed(evt);
            }
        });
        barraFormas.add(punto);

        linea.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/linea.png"))); // NOI18N
        linea.setToolTipText("Línea");
        linea.setFocusable(false);
        linea.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        linea.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        linea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lineaActionPerformed(evt);
            }
        });
        barraFormas.add(linea);

        rectangulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/rectangulo.png"))); // NOI18N
        rectangulo.setToolTipText("Rectánculo");
        rectangulo.setFocusable(false);
        rectangulo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        rectangulo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        rectangulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rectanguloActionPerformed(evt);
            }
        });
        barraFormas.add(rectangulo);

        elipse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/elipse.png"))); // NOI18N
        elipse.setToolTipText("Elipse");
        elipse.setFocusable(false);
        elipse.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        elipse.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        elipse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                elipseActionPerformed(evt);
            }
        });
        barraFormas.add(elipse);

        curva.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/curva.png"))); // NOI18N
        curva.setToolTipText("Curva");
        curva.setFocusable(false);
        curva.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        curva.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        curva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                curvaActionPerformed(evt);
            }
        });
        barraFormas.add(curva);

        texto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/T.png"))); // NOI18N
        texto.setToolTipText("Texto");
        texto.setFocusable(false);
        texto.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        texto.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        texto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textoActionPerformed(evt);
            }
        });
        barraFormas.add(texto);

        editar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/seleccion.png"))); // NOI18N
        editar.setToolTipText("Editar");
        editar.setFocusable(false);
        editar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        editar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        editar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editarActionPerformed(evt);
            }
        });
        barraFormas.add(editar);
        barraFormas.add(separador2);

        colores.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        colores.setToolTipText("Color");
        colores.setPreferredSize(new java.awt.Dimension(50, 30));
        colores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                coloresActionPerformed(evt);
            }
        });
        barraFormas.add(colores);

        eleccionColores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/EleccionColores.png"))); // NOI18N
        eleccionColores.setToolTipText("Color personalizado");
        eleccionColores.setFocusable(false);
        eleccionColores.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        eleccionColores.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        eleccionColores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eleccionColoresActionPerformed(evt);
            }
        });
        barraFormas.add(eleccionColores);
        barraFormas.add(separador3);

        contador.setToolTipText("Grosor");
        contador.setPreferredSize(new java.awt.Dimension(50, 30));
        contador.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                contadorStateChanged(evt);
            }
        });
        barraFormas.add(contador);

        trazo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/trazos.png"))); // NOI18N
        trazo.setToolTipText("Trazos");
        trazo.setFocusable(false);
        trazo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        trazo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        trazo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                trazoActionPerformed(evt);
            }
        });
        barraFormas.add(trazo);
        barraFormas.add(separador4);

        relleno.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/rellenar.png"))); // NOI18N
        relleno.setToolTipText("Relleno");
        relleno.setFocusable(false);
        relleno.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        relleno.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        relleno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rellenoActionPerformed(evt);
            }
        });
        barraFormas.add(relleno);

        alisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/alisar.png"))); // NOI18N
        alisar.setToolTipText("Alisar");
        alisar.setFocusable(false);
        alisar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        alisar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        alisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alisarActionPerformed(evt);
            }
        });
        barraFormas.add(alisar);
        barraFormas.add(jSeparator2);

        negrita1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/negrita.png"))); // NOI18N
        negrita1.setToolTipText("Negrita");
        negrita1.setFocusable(false);
        negrita1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        negrita1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        negrita1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                negrita1ActionPerformed(evt);
            }
        });
        barraFormas.add(negrita1);

        cursiva1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cursiva.png"))); // NOI18N
        cursiva1.setToolTipText("Cursiva");
        cursiva1.setFocusable(false);
        cursiva1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cursiva1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cursiva1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cursiva1ActionPerformed(evt);
            }
        });
        barraFormas.add(cursiva1);

        subrayado1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/subrayado.png"))); // NOI18N
        subrayado1.setToolTipText("Subrayado");
        subrayado1.setFocusable(false);
        subrayado1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        subrayado1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        subrayado1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subrayado1ActionPerformed(evt);
            }
        });
        barraFormas.add(subrayado1);

        tipoLetra1.setToolTipText("Tipo de fuente");
        tipoLetra1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tipoLetra1ActionPerformed(evt);
            }
        });
        barraFormas.add(tipoLetra1);
        barraFormas.add(separador5);

        captura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Capturar.png"))); // NOI18N
        captura.setToolTipText("Captura de pantalla");
        captura.setFocusable(false);
        captura.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        captura.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        captura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                capturaActionPerformed(evt);
            }
        });
        barraFormas.add(captura);

        duplicar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/duplicar.png"))); // NOI18N
        duplicar.setToolTipText("Duplicar");
        duplicar.setFocusable(false);
        duplicar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        duplicar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        duplicar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                duplicarActionPerformed(evt);
            }
        });
        barraFormas.add(duplicar);

        jPanel1.add(barraFormas, java.awt.BorderLayout.PAGE_START);

        barraEdicion.setRollover(true);
        barraEdicion.setMargin(new java.awt.Insets(0, 10, 10, 10));

        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Filtro", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 1, 13))); // NOI18N

        filtros.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Media", "Binomial", "Enfoque", "Relieve", "Laplaciano" }));
        filtros.setToolTipText("Filtros");
        filtros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filtrosActionPerformed(evt);
            }
        });
        jPanel8.add(filtros);

        jPanel4.add(jPanel8, java.awt.BorderLayout.CENTER);

        barraEdicion.add(jPanel4);

        jPanel12.setLayout(new java.awt.BorderLayout());

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Contraste", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 1, 13))); // NOI18N

        contraste.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/contraste.png"))); // NOI18N
        contraste.setToolTipText("Contraste");
        contraste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contrasteActionPerformed(evt);
            }
        });
        jPanel13.add(contraste);

        iluminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/iluminar.png"))); // NOI18N
        iluminar.setToolTipText("Iluminar");
        iluminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                iluminarActionPerformed(evt);
            }
        });
        jPanel13.add(iluminar);

        oscurecer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/oscurecer.png"))); // NOI18N
        oscurecer.setToolTipText("Oscurecer");
        oscurecer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                oscurecerActionPerformed(evt);
            }
        });
        jPanel13.add(oscurecer);

        negativo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/negativo.png"))); // NOI18N
        negativo.setToolTipText("Negativo");
        negativo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                negativoActionPerformed(evt);
            }
        });
        jPanel13.add(negativo);

        jPanel12.add(jPanel13, java.awt.BorderLayout.CENTER);

        barraEdicion.add(jPanel12);

        jPanel14.setLayout(new java.awt.BorderLayout());

        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "    ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 1, 13))); // NOI18N

        sinusoidal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/sinusoidal.png"))); // NOI18N
        sinusoidal.setToolTipText("Sinusoidal");
        sinusoidal.setPreferredSize(new java.awt.Dimension(44, 28));
        sinusoidal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sinusoidalActionPerformed(evt);
            }
        });
        jPanel15.add(sinusoidal);

        sepia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/sepia.png"))); // NOI18N
        sepia.setToolTipText("Sepia");
        sepia.setPreferredSize(new java.awt.Dimension(44, 28));
        sepia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sepiaActionPerformed(evt);
            }
        });
        jPanel15.add(sepia);

        tintar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/tintar.png"))); // NOI18N
        tintar.setToolTipText("Tintar");
        tintar.setPreferredSize(new java.awt.Dimension(44, 28));
        tintar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tintarActionPerformed(evt);
            }
        });
        jPanel15.add(tintar);

        ecualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ecualizar.png"))); // NOI18N
        ecualizar.setToolTipText("Ecualizar");
        ecualizar.setPreferredSize(new java.awt.Dimension(44, 28));
        ecualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ecualizarActionPerformed(evt);
            }
        });
        jPanel15.add(ecualizar);

        daltonico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/daltonico.png"))); // NOI18N
        daltonico.setToolTipText("Daltonismo");
        daltonico.setPreferredSize(new java.awt.Dimension(44, 28));
        daltonico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                daltonicoActionPerformed(evt);
            }
        });
        jPanel15.add(daltonico);

        jPanel14.add(jPanel15, java.awt.BorderLayout.CENTER);

        barraEdicion.add(jPanel14);

        jPanel20.setLayout(new java.awt.BorderLayout());

        jPanel21.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Color", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 1, 13))); // NOI18N

        banda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/bandas.png"))); // NOI18N
        banda.setToolTipText("Bandas");
        banda.setPreferredSize(new java.awt.Dimension(44, 28));
        banda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bandaActionPerformed(evt);
            }
        });
        jPanel21.add(banda);

        espacioColor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "sRGB", "YCC", "Grey", "YCbCr" }));
        espacioColor.setToolTipText("Espacio de color");
        espacioColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                espacioColorActionPerformed(evt);
            }
        });
        jPanel21.add(espacioColor);

        jPanel20.add(jPanel21, java.awt.BorderLayout.CENTER);

        barraEdicion.add(jPanel20);

        jPanel18.setLayout(new java.awt.BorderLayout());

        jPanel19.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Escala", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 1, 13))); // NOI18N

        aumentar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/aumentar.png"))); // NOI18N
        aumentar.setToolTipText("Aumentar");
        aumentar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aumentarActionPerformed(evt);
            }
        });
        jPanel19.add(aumentar);

        disminuir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/disminuir.png"))); // NOI18N
        disminuir.setToolTipText("Disminuir");
        disminuir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disminuirActionPerformed(evt);
            }
        });
        jPanel19.add(disminuir);

        jPanel18.add(jPanel19, java.awt.BorderLayout.CENTER);

        barraEdicion.add(jPanel18);

        jPanel1.add(barraEdicion, java.awt.BorderLayout.PAGE_END);

        barraSlider.setOrientation(javax.swing.SwingConstants.VERTICAL);
        barraSlider.setRollover(true);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Brillo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 1, 13))); // NOI18N

        barraBrillo.setMaximum(250);
        barraBrillo.setMinimum(-250);
        barraBrillo.setToolTipText("Brillo");
        barraBrillo.setValue(0);
        barraBrillo.setPreferredSize(new java.awt.Dimension(150, 29));
        barraBrillo.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                barraBrilloStateChanged(evt);
            }
        });
        barraBrillo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                barraBrilloFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                barraBrilloFocusLost(evt);
            }
        });
        jPanel6.add(barraBrillo);

        jPanel3.add(jPanel6, java.awt.BorderLayout.LINE_START);

        barraSlider.add(jPanel3);

        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel22.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Umbralización", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 1, 13))); // NOI18N

        umbral.setMaximum(255);
        umbral.setToolTipText("Umbralización");
        umbral.setValue(127);
        umbral.setPreferredSize(new java.awt.Dimension(150, 29));
        umbral.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                umbralStateChanged(evt);
            }
        });
        umbral.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                umbralFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                umbralFocusLost(evt);
            }
        });
        jPanel22.add(umbral);

        jPanel5.add(jPanel22, java.awt.BorderLayout.CENTER);

        barraSlider.add(jPanel5);

        jPanel23.setLayout(new java.awt.BorderLayout());

        jPanel24.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Transparencia", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 1, 13))); // NOI18N

        gradoTransparencia.setMaximum(99);
        gradoTransparencia.setToolTipText("Transparencia");
        gradoTransparencia.setValue(99);
        gradoTransparencia.setPreferredSize(new java.awt.Dimension(150, 29));
        gradoTransparencia.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                gradoTransparenciaStateChanged(evt);
            }
        });
        gradoTransparencia.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                gradoTransparenciaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                gradoTransparenciaFocusLost(evt);
            }
        });
        jPanel24.add(gradoTransparencia);

        jPanel23.add(jPanel24, java.awt.BorderLayout.CENTER);

        barraSlider.add(jPanel23);

        jPanel16.setLayout(new java.awt.BorderLayout());

        jPanel17.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Rotación", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 1, 13))); // NOI18N

        barraRotacion.setMaximum(360);
        barraRotacion.setMinorTickSpacing(90);
        barraRotacion.setPaintTicks(true);
        barraRotacion.setToolTipText("Rotación libre");
        barraRotacion.setValue(0);
        barraRotacion.setPreferredSize(new java.awt.Dimension(150, 29));
        barraRotacion.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                barraRotacionStateChanged(evt);
            }
        });
        barraRotacion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                barraRotacionFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                barraRotacionFocusLost(evt);
            }
        });
        jPanel17.add(barraRotacion);

        jPanel16.add(jPanel17, java.awt.BorderLayout.CENTER);

        barraSlider.add(jPanel16);

        jPanel1.add(barraSlider, java.awt.BorderLayout.LINE_END);

        escritorio.setLayout(null);
        jPanel1.add(escritorio, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel2.setLayout(new java.awt.BorderLayout());

        panelEstado.setLayout(new java.awt.BorderLayout());

        barraEstado.setText("Barra de estado");
        panelEstado.add(barraEstado, java.awt.BorderLayout.LINE_START);

        jPanel7.setLayout(new java.awt.GridLayout(1, 0));

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel7.add(jSeparator1);
        jPanel7.add(coordenadas);

        panelEstado.add(jPanel7, java.awt.BorderLayout.LINE_END);

        jPanel2.add(panelEstado, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(jPanel2, java.awt.BorderLayout.PAGE_END);

        jMenu1.setText("Archivo");

        nuevo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.ALT_MASK));
        nuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/nuevo.png"))); // NOI18N
        nuevo.setText("Nuevo");
        nuevo.setToolTipText("Nuevo");
        nuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuevoActionPerformed(evt);
            }
        });
        jMenu1.add(nuevo);

        abrir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.ALT_MASK));
        abrir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/abrir.png"))); // NOI18N
        abrir.setText("Abrir");
        abrir.setToolTipText("Abrir");
        abrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                abrirActionPerformed(evt);
            }
        });
        jMenu1.add(abrir);

        guardar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.ALT_MASK));
        guardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/guardar.png"))); // NOI18N
        guardar.setText("Guardar");
        guardar.setToolTipText("Guardar");
        guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardarActionPerformed(evt);
            }
        });
        jMenu1.add(guardar);
        jMenu1.add(jSeparator3);

        abrirAudio.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK));
        abrirAudio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/openAudio24x24.png"))); // NOI18N
        abrirAudio.setText("Abrir audio");
        abrirAudio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                abrirAudioActionPerformed(evt);
            }
        });
        jMenu1.add(abrirAudio);

        guardarAudio.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.ALT_MASK));
        guardarAudio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/record24x24.png"))); // NOI18N
        guardarAudio.setText("Guardar audio");
        guardarAudio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardarAudioActionPerformed(evt);
            }
        });
        jMenu1.add(guardarAudio);
        jMenu1.add(jSeparator4);

        abrirVideoJMF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/AbrirMedio.png"))); // NOI18N
        abrirVideoJMF.setText("Abrir video JMF");
        abrirVideoJMF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                abrirVideoJMFActionPerformed(evt);
            }
        });
        jMenu1.add(abrirVideoJMF);

        abrirVideoVLC.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.ALT_MASK));
        abrirVideoVLC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/AbrirMedio.png"))); // NOI18N
        abrirVideoVLC.setText("Abrir video VCL");
        abrirVideoVLC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                abrirVideoVLCActionPerformed(evt);
            }
        });
        jMenu1.add(abrirVideoVLC);

        camara.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_MASK));
        camara.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Camara.png"))); // NOI18N
        camara.setText("Camara");
        camara.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                camaraActionPerformed(evt);
            }
        });
        jMenu1.add(camara);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edición");

        verBarraEstado.setSelected(true);
        verBarraEstado.setText("Ver barra de estado");
        verBarraEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verBarraEstadoActionPerformed(evt);
            }
        });
        jMenu2.add(verBarraEstado);

        verBarraFormas.setSelected(true);
        verBarraFormas.setText("Ver barra de formas");
        verBarraFormas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verBarraFormasActionPerformed(evt);
            }
        });
        jMenu2.add(verBarraFormas);

        verBarraAtributos.setSelected(true);
        verBarraAtributos.setText("Ver barra de atributos");
        verBarraAtributos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verBarraAtributosActionPerformed(evt);
            }
        });
        jMenu2.add(verBarraAtributos);

        verBarraVideo.setSelected(true);
        verBarraVideo.setText("Ver barra de video");
        verBarraVideo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verBarraVideoActionPerformed(evt);
            }
        });
        jMenu2.add(verBarraVideo);

        verBarraEdicion.setSelected(true);
        verBarraEdicion.setText("Ver barra de edición");
        verBarraEdicion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verBarraEdicionActionPerformed(evt);
            }
        });
        jMenu2.add(verBarraEdicion);

        verBarraSlider.setSelected(true);
        verBarraSlider.setText("Ver barra de slider");
        verBarraSlider.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verBarraSliderActionPerformed(evt);
            }
        });
        jMenu2.add(verBarraSlider);

        verBarraTexto.setSelected(true);
        verBarraTexto.setText("Ver opciones de texto");
        verBarraTexto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verBarraTextoActionPerformed(evt);
            }
        });
        jMenu2.add(verBarraTexto);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Imagen");

        nuevaImagen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.ALT_MASK));
        nuevaImagen.setText("Tamaño nueva imagen");
        nuevaImagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuevaImagenActionPerformed(evt);
            }
        });
        jMenu3.add(nuevaImagen);

        jMenuBar1.add(jMenu3);

        jMenu4.setText("Ayuda");

        acercaDe.setText("Acerca de");
        acercaDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acercaDeActionPerformed(evt);
            }
        });
        jMenu4.add(acercaDe);

        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void nuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevoActionPerformed
        // TODO add your handling code here:
        VentanaInterna vi = new VentanaInterna(this);
        int ventana = tipoVentana();
        
        switch (ventana) {
            case 0:
                VentanaInterna actualI = (VentanaInterna)escritorio.getSelectedFrame();
                if(actualI != null){
                    vi.setLocation(actualI.getX()+20, actualI.getY()+20);
                }   
                break;
                
            case 1:
                VentanaInternaReproductor actualR = (VentanaInternaReproductor)escritorio.getSelectedFrame();
                if(actualR != null){
                    vi.setLocation(actualR.getX()+20, actualR.getY()+20);
                }   
                break;
                
            case 2:
                VentanaInternaGrabador actualG = (VentanaInternaGrabador)escritorio.getSelectedFrame();
                if(actualG != null){
                    vi.setLocation(actualG.getX()+20, actualG.getY()+20);
                }   
                break;
                
            case 3:
                VentanaInternaVLCPlayer actualVLC = (VentanaInternaVLCPlayer)escritorio.getSelectedFrame();
                if(actualVLC != null){
                    vi.setLocation(actualVLC.getX()+20, actualVLC.getY()+20);
                }   
                break;
                
            case 4:
                VentanaInternaCamara actualC = (VentanaInternaCamara)escritorio.getSelectedFrame();
                if(actualC != null){
                    vi.setLocation(actualC.getX()+20, actualC.getY()+20);
                }   
                break;
                
            case 5:
                VentanaInternaJMFPlayer actualJMF = (VentanaInternaJMFPlayer)escritorio.getSelectedFrame();
                if(actualJMF != null){
                    vi.setLocation(actualJMF.getX()+20, actualJMF.getY()+20);
                }   
                break;
        }
        
        vi.setTitle("Nueva imagen");
        escritorio.add(vi);
        vi.setVisible(true);
        
        BufferedImage img;
        img = new BufferedImage(300,300,BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setPaint(Color.white);
        g2d.fillRect(0, 0, img.getWidth(), img.getHeight());
        
        vi.getLienzo().setImage(img);
    }//GEN-LAST:event_nuevoActionPerformed

    private void nuevoActionPerformed(String titulo, BufferedImage img) {                                      
        // TODO add your handling code here:
        VentanaInterna vi = new VentanaInterna(this);
        int ventana = tipoVentana();
        
        switch (ventana) {
            case 0:
                VentanaInterna actualI = (VentanaInterna)escritorio.getSelectedFrame();
                if(actualI != null){
                    vi.setLocation(actualI.getX()+20, actualI.getY()+20);
                }   
                break;
                
            case 1:
                VentanaInternaReproductor actualR = (VentanaInternaReproductor)escritorio.getSelectedFrame();
                if(actualR != null){
                    vi.setLocation(actualR.getX()+20, actualR.getY()+20);
                }   
                break;
                
            case 2:
                VentanaInternaGrabador actualG = (VentanaInternaGrabador)escritorio.getSelectedFrame();
                if(actualG != null){
                    vi.setLocation(actualG.getX()+20, actualG.getY()+20);
                }   
                break;
                
            case 3:
                VentanaInternaVLCPlayer actualVLC = (VentanaInternaVLCPlayer)escritorio.getSelectedFrame();
                if(actualVLC != null){
                    vi.setLocation(actualVLC.getX()+20, actualVLC.getY()+20);
                }   
                break;
                
            case 4:
                VentanaInternaCamara actualC = (VentanaInternaCamara)escritorio.getSelectedFrame();
                if(actualC != null){
                    vi.setLocation(actualC.getX()+20, actualC.getY()+20);
                }   
                break;
                
            case 5:
                    VentanaInternaJMFPlayer actualJMF = (VentanaInternaJMFPlayer)escritorio.getSelectedFrame();
                    if(actualJMF != null){
                        vi.setLocation(actualJMF.getX()+20, actualJMF.getY()+20);
                    }   
                    break;
        }
        
        vi.setTitle(titulo);
        escritorio.add(vi);
        vi.setVisible(true);
        vi.getLienzo().setImage(img);
    }   
    
    private void abrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_abrirActionPerformed
        // TODO add your handling code here:
        int ventana = tipoVentana();
        JFileChooser dlg = new JFileChooser();
        
        dlg.setFileFilter(filtroVideosJMF);
        dlg.setFileFilter(filtroVideosVLC);
        dlg.setFileFilter(filtroAudios);
        dlg.setFileFilter(filtroFotos);
        int resp = dlg.showOpenDialog(this);
        
        File f = dlg.getSelectedFile();
        
        if(dlg.getFileFilter() == filtroFotos){
            VentanaInterna vi = new VentanaInterna(this);
            switch (ventana) {
                case 0:
                    VentanaInterna actualI = (VentanaInterna)escritorio.getSelectedFrame();
                    if(actualI != null){
                        vi.setLocation(actualI.getX()+20, actualI.getY()+20);
                    }   
                    break;

                case 1:
                    VentanaInternaReproductor actualR = (VentanaInternaReproductor)escritorio.getSelectedFrame();
                    if(actualR != null){
                        vi.setLocation(actualR.getX()+20, actualR.getY()+20);
                    }   
                    break;

                case 2:
                    VentanaInternaGrabador actualG = (VentanaInternaGrabador)escritorio.getSelectedFrame();
                    if(actualG != null){
                        vi.setLocation(actualG.getX()+20, actualG.getY()+20);
                    }   
                    break;
                
                case 3:
                    VentanaInternaVLCPlayer actualVLC = (VentanaInternaVLCPlayer)escritorio.getSelectedFrame();
                    if(actualVLC != null){
                        vi.setLocation(actualVLC.getX()+20, actualVLC.getY()+20);
                    }   
                    break;

                case 4:
                    VentanaInternaCamara actualC = (VentanaInternaCamara)escritorio.getSelectedFrame();
                    if(actualC != null){
                        vi.setLocation(actualC.getX()+20, actualC.getY()+20);
                    }  
                    break;
                    
                case 5:
                    VentanaInternaJMFPlayer actualJMF = (VentanaInternaJMFPlayer)escritorio.getSelectedFrame();
                    if(actualJMF != null){
                        vi.setLocation(actualJMF.getX()+20, actualJMF.getY()+20);
                    }   
                    break;
            }
        
            if( resp == JFileChooser.APPROVE_OPTION) {
                try{
                    if(formatoCorrecto(f)){
                        BufferedImage img = ImageIO.read(f);
                        vi.getLienzo().setImage(img);
                        this.escritorio.add(vi);
                        vi.setTitle(f.getName());
                        vi.setVisible(true);
                    }
                    else{
                        mensaje(1);
                    }
                }
                catch(Exception ex){
                    System.err.println("Error al abrir la imagen" + ex);
                }
            }
        }
        else if (dlg.getFileFilter() == filtroAudios){
            abrirAudioActionPerformed(evt, resp, f);
        }
        else if(dlg.getFileFilter() == filtroVideosVLC){
            abrirVideoVLCActionPerformed(evt, resp, f);
        }
        else if(dlg.getFileFilter() == filtroVideosJMF){
            abrirVideoJMFActionPerformed(evt, resp, f);
        }
    }//GEN-LAST:event_abrirActionPerformed
    
    private void guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarActionPerformed
        // TODO add your handling code here:
        if (tipoVentana() != 0){
            mensaje(10); 
        }
        else{
            VentanaInterna vi=(VentanaInterna) escritorio.getSelectedFrame();
            String formato = "";

            if (vi != null) {
                JFileChooser dlg = new JFileChooser();
                dlg.setFileFilter(filtroFotos);
                dlg.setFileFilter(filtroGIF);
                dlg.setFileFilter(filtroJPG);
                dlg.setFileFilter(filtroPNG);
                int resp = dlg.showSaveDialog(this);
                if (resp == JFileChooser.APPROVE_OPTION) {
                    try {
                        BufferedImage img = vi.getLienzo().getImage(true);
                        if (img != null) {
                            File f = dlg.getSelectedFile();

                            if(dlg.getFileFilter() == filtroGIF){
                                f = new File(dlg.getSelectedFile() + ".gif");
                                formato = "gif";
                            }
                            else if(dlg.getFileFilter() == filtroJPG){
                                f = new File(dlg.getSelectedFile() + ".jpg");
                                formato =  "jpg";
                            }
                            else if(dlg.getFileFilter() == filtroPNG){
                                f = new File(dlg.getSelectedFile() + ".png");
                                formato = "png";
                            }

                            ImageIO.write(img, formato, f);
                            vi.setTitle(f.getName());
                        }
                        else
                            mensaje(2);
                    }
                    catch (Exception ex) {
                        System.err.println("Error al guardar la imagen " + ex);
                    }
                }
            }
        }
    }//GEN-LAST:event_guardarActionPerformed

    private void verBarraEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verBarraEstadoActionPerformed
        // TODO add your handling code here:
        barraEstado.setVisible(verBarraEstado.isSelected());
    }//GEN-LAST:event_verBarraEstadoActionPerformed

    private void verBarraFormasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verBarraFormasActionPerformed
        // TODO add your handling code here:
        punto.setVisible(verBarraFormas.isSelected());
        linea.setVisible(verBarraFormas.isSelected());
        rectangulo.setVisible(verBarraFormas.isSelected());
        elipse.setVisible(verBarraFormas.isSelected());
        curva.setVisible(verBarraFormas.isSelected());
        texto.setVisible(verBarraFormas.isSelected());
        editar.setVisible(verBarraFormas.isSelected());
        separador1.setVisible(verBarraFormas.isSelected());
    }//GEN-LAST:event_verBarraFormasActionPerformed

    private void verBarraAtributosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verBarraAtributosActionPerformed
        // TODO add your handling code here:
        eleccionColores.setVisible(verBarraAtributos.isSelected());
        trazo.setVisible(verBarraAtributos.isSelected());
        colores.setVisible(verBarraAtributos.isSelected());
        contador.setVisible(verBarraAtributos.isSelected());
        relleno.setVisible(verBarraAtributos.isSelected());
        alisar.setVisible(verBarraAtributos.isSelected());
        separador2.setVisible(verBarraAtributos.isSelected());
        separador3.setVisible(verBarraAtributos.isSelected());
        separador4.setVisible(verBarraAtributos.isSelected());
    }//GEN-LAST:event_verBarraAtributosActionPerformed

    private void puntoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_puntoActionPerformed
        // TODO add your handling code here:
        VentanaInterna vi;
        vi = (VentanaInterna)escritorio.getSelectedFrame();
        barraEstado.setText("Punto");
        if(vi != null){
            vi.getLienzo().setSeleccionado(0);
            vi.getLienzo().setMover(false); 
            relleno.setEnabled(false);
            alisar.setEnabled(false);
            contador.setEnabled(false);
            trazo.setEnabled(false);
            colores.setEnabled(false);
            eleccionColores.setEnabled(false);
            tipoLetra1.setEnabled(false);
            
            if (vi.getLienzo().getMover()){
                vi.getLienzo().getFigura().setColorBorde((Color) colores.getSelectedItem());
                vi.getLienzo().getFigura().setContador((int) contador.getValue());
            }
            else{
                vi.getLienzo().setColorBorde((Color) colores.getSelectedItem());
                vi.getLienzo().setContador((int) contador.getValue());
            }
        }
    }//GEN-LAST:event_puntoActionPerformed

    private void lineaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lineaActionPerformed
        // TODO add your handling code here:
        VentanaInterna vi;
        vi = (VentanaInterna)escritorio.getSelectedFrame();
        barraEstado.setText("Línea");
        if(vi != null){ 
            vi.getLienzo().setSeleccionado(1);
            vi.getLienzo().setMover(false);
            relleno.setEnabled(false);
            alisar.setEnabled(false);
            contador.setEnabled(false);
            trazo.setEnabled(false);
            colores.setEnabled(false);
            eleccionColores.setEnabled(false);
            tipoLetra1.setEnabled(false);
            
            if (vi.getLienzo().getMover()){
                vi.getLienzo().getFigura().setColorBorde((Color) colores.getSelectedItem());
                vi.getLienzo().getFigura().setContador((int) contador.getValue());
            }
            else{
                vi.getLienzo().setColorBorde((Color) colores.getSelectedItem());
                vi.getLienzo().setContador((int) contador.getValue());
            }
        }
    }//GEN-LAST:event_lineaActionPerformed

    private void rectanguloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rectanguloActionPerformed
        // TODO add your handling code here:
        VentanaInterna vi;
        vi = (VentanaInterna)escritorio.getSelectedFrame();
        barraEstado.setText("Rectángulo");
        if(vi != null){
            vi.getLienzo().setSeleccionado(2);
            vi.getLienzo().setMover(false);
            relleno.setEnabled(false);
            alisar.setEnabled(false);
            contador.setEnabled(false);
            trazo.setEnabled(false);
            colores.setEnabled(false);
            eleccionColores.setEnabled(false);
            tipoLetra1.setEnabled(false);
            
            if (vi.getLienzo().getMover()){
                vi.getLienzo().getFigura().setColorBorde((Color) colores.getSelectedItem());
                vi.getLienzo().getFigura().setContador((int) contador.getValue());
            }
            else{
                vi.getLienzo().setColorBorde((Color) colores.getSelectedItem());
                vi.getLienzo().setContador((int) contador.getValue());
            }
        
        }
    }//GEN-LAST:event_rectanguloActionPerformed

    private void elipseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_elipseActionPerformed
        // TODO add your handling code here:
        VentanaInterna vi;
        vi = (VentanaInterna)escritorio.getSelectedFrame();
        barraEstado.setText("Elipse");
        if(vi != null){
            vi.getLienzo().setSeleccionado(3);
            vi.getLienzo().setMover(false);
            relleno.setEnabled(false);
            alisar.setEnabled(false);
            contador.setEnabled(false);
            trazo.setEnabled(false);
            colores.setEnabled(false);
            eleccionColores.setEnabled(false);
            tipoLetra1.setEnabled(false);
            
            if (vi.getLienzo().getMover()){
                vi.getLienzo().getFigura().setColorBorde((Color) colores.getSelectedItem());
                vi.getLienzo().getFigura().setContador((int) contador.getValue());
            }
            else{
                vi.getLienzo().setColorBorde((Color) colores.getSelectedItem());
                vi.getLienzo().setContador((int) contador.getValue());
            }
        }
    }//GEN-LAST:event_elipseActionPerformed

    private void nuevo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevo1ActionPerformed
        // TODO add your handling code here:
        nuevoActionPerformed(evt);
    }//GEN-LAST:event_nuevo1ActionPerformed

    private void abrir1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_abrir1ActionPerformed
        // TODO add your handling code here:
        abrirActionPerformed(evt);
    }//GEN-LAST:event_abrir1ActionPerformed

    private void guardar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardar1ActionPerformed
        // TODO add your handling code here:
        guardarActionPerformed(evt);
    }//GEN-LAST:event_guardar1ActionPerformed

    private void editarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editarActionPerformed
        // TODO add your handling code here:
        VentanaInterna vi;
        
        vi = (VentanaInterna)escritorio.getSelectedFrame();
        
        if(vi != null){
            vi.getLienzo().setMover(editar.isSelected());
            vi.getLienzo().getFigura().setEditar(editar.isSelected());
            
            relleno.setEnabled(true);
            alisar.setEnabled(true);
            contador.setEnabled(true);
            trazo.setEnabled(true);
            colores.setEnabled(true);
            eleccionColores.setEnabled(true);
            tipoLetra1.setEnabled(true);
        }
        
        barraEstado.setText("Editar");
        this.repaint();
    }//GEN-LAST:event_editarActionPerformed

    private void contadorStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_contadorStateChanged
        // TODO add your handling code here:
        VentanaInterna vi;
        
        vi = (VentanaInterna)escritorio.getSelectedFrame();
        
        if(vi != null){
            vi.getLienzo().getFigura().setContador((int) contador.getValue());
            vi.getLienzo().getFigura().setTamLetra((int) contador.getValue());
        }
        
        this.repaint();
    }//GEN-LAST:event_contadorStateChanged

    private void alisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alisarActionPerformed
        // TODO add your handling code here:
        VentanaInterna vi;
        
        vi = (VentanaInterna)escritorio.getSelectedFrame();
        
        if(vi != null)
            vi.getLienzo().getFigura().setAlisado(alisar.isSelected());
        
        this.repaint();
    }//GEN-LAST:event_alisarActionPerformed

    private void coloresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_coloresActionPerformed
        // TODO add your handling code here:
        VentanaInterna vi;
        
        vi = (VentanaInterna)escritorio.getSelectedFrame();

        vi.getLienzo().getFigura().setColorBorde((Color) colores.getSelectedItem());

        this.repaint();
    }//GEN-LAST:event_coloresActionPerformed

    private void barraBrilloStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_barraBrilloStateChanged
        // TODO add your handling code here:
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        
        if (vi != null) {
            if(imagenFuente!=null){
                try{
                    vi.getLienzo().getFigura().setEditar(false);
                    RescaleOp rop = new RescaleOp(1.0F, barraBrillo.getValue(), null);
                    rop.filter(imagenFuente, vi.getLienzo().getImage(true));
                    volcado();
                    escritorio.repaint();
                } 
                catch(IllegalArgumentException e){
                    System.err.println(e.getLocalizedMessage());
                }
            } 
        } 
    }//GEN-LAST:event_barraBrilloStateChanged

    private void barraBrilloFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_barraBrilloFocusLost
        // TODO add your handling code here:
        imagenFuente = null;
    }//GEN-LAST:event_barraBrilloFocusLost

    private void barraBrilloFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_barraBrilloFocusGained
        // TODO add your handling code here:
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        
        if(vi!=null){
            vi.getLienzo().getFigura().setEditar(false);
            ColorModel cm = vi.getLienzo().getImage(true).getColorModel(); 
            WritableRaster raster = vi.getLienzo().getImage(true).copyData(null);
            boolean alfaPre = vi.getLienzo().getImage(true).isAlphaPremultiplied();
            imagenFuente = new BufferedImage(cm,raster,alfaPre,null);
        }
    }//GEN-LAST:event_barraBrilloFocusGained

    private void filtrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filtrosActionPerformed
        // TODO add your handling code here:
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        
        if (vi != null) {
            vi.getLienzo().getFigura().setEditar(false);
            BufferedImage imgSource = vi.getLienzo().getImage(true);
            volcado();
            
            if(imgSource!=null){
                try{
                    ColorModel cm = vi.getLienzo().getImage(true).getColorModel(); 
                    WritableRaster raster = vi.getLienzo().getImage(true).copyData(null);
                    boolean alfaPre = vi.getLienzo().getImage(true).isAlphaPremultiplied();
                    volcado();
                    imagenFuente = new BufferedImage(cm,raster,alfaPre,null);
                    Kernel k = KernelProducer.createKernel(KernelProducer.TYPE_MEDIA_3x3);
                    
                    if(filtros.getSelectedItem() == "Media")
                        k = KernelProducer.createKernel(KernelProducer.TYPE_MEDIA_3x3);
                    else if(filtros.getSelectedItem() == "Binomial")
                        k = KernelProducer.createKernel(KernelProducer.TYPE_BINOMIAL_3x3);
                    else if(filtros.getSelectedItem() == "Enfoque")
                        k = KernelProducer.createKernel(KernelProducer.TYPE_ENFOQUE_3x3);
                    else if(filtros.getSelectedItem() == "Relieve")
                        k = KernelProducer.createKernel(KernelProducer.TYPE_RELIEVE_3x3);
                    else if(filtros.getSelectedItem() == "Laplaciano")
                        k = KernelProducer.createKernel(KernelProducer.TYPE_LAPLACIANA_3x3);
                    
                    ConvolveOp cop = new ConvolveOp(k,ConvolveOp.EDGE_NO_OP,null);
                    cop.filter(imagenFuente, imgSource);
                    escritorio.repaint();
                } 
                catch(IllegalArgumentException e){
                   System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_filtrosActionPerformed

    private void nuevaImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevaImagenActionPerformed
        // TODO add your handling code here:
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        
        if (vi != null) {
            ancho.setText(Integer.toString(vi.getLienzo().getPreferredSize().width));
            alto.setText(Integer.toString(vi.getLienzo().getPreferredSize().height));
        }
        else{
            ancho.setText("300");
            alto.setText("300");
            VentanaInterna vi2 = new VentanaInterna(this);
            
            BufferedImage img;
            img = new BufferedImage(Integer.parseInt(ancho.getText()),Integer.parseInt(alto.getText()),BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = img.createGraphics();
            g2d.setPaint(Color.white);
            g2d.fillRect(0, 0, img.getWidth(), img.getHeight());
            vi2.setTitle("Nueva imagen");
            escritorio.add(vi2);
            vi2.setVisible(true);
            vi2.getLienzo().setImage(img);
        }
        
        tamImagen.setLocationRelativeTo(this);
        tamImagen.setVisible(true);
        redimensionar.setVisible(false);
        escalar.setVisible(false);
    }//GEN-LAST:event_nuevaImagenActionPerformed

    private void contrasteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contrasteActionPerformed
        // TODO add your handling code here:
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        
        if (vi != null) {
            vi.getLienzo().getFigura().setEditar(false);
            BufferedImage imgSource = vi.getLienzo().getImage(true);
            volcado();
            if(imgSource!=null){
                try{
                    int type = LookupTableProducer.TYPE_SFUNCION;
                    LookupTable lt = LookupTableProducer.createLookupTable(type);
                    LookupOp lop = new LookupOp(lt, null);
                    // Imagen origen y destino iguales
                    lop.filter(imgSource , imgSource);
                    vi.repaint();
                } 
                catch(Exception e){
                    System.err.println(e.getLocalizedMessage());
                }
            } 
        }
    }//GEN-LAST:event_contrasteActionPerformed

    private void iluminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_iluminarActionPerformed
        // TODO add your handling code here:
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        
        if (vi != null) {
            vi.getLienzo().getFigura().setEditar(false);
            BufferedImage imgSource = vi.getLienzo().getImage(true);
            volcado();
            if(imgSource!=null){
                try{
                    int type = LookupTableProducer.TYPE_ROOT;
                    LookupTable lt = LookupTableProducer.createLookupTable(type);
                    LookupOp lop = new LookupOp(lt, null);
                    // Imagen origen y destino iguales
                    lop.filter(imgSource , imgSource);
                    vi.repaint();
                } 
                catch(Exception e){
                    System.err.println(e.getLocalizedMessage());
                }
            } 
        }
    }//GEN-LAST:event_iluminarActionPerformed

    private void oscurecerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_oscurecerActionPerformed
        // TODO add your handling code here:
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        
        if (vi != null) {
            vi.getLienzo().getFigura().setEditar(false);
            BufferedImage imgSource = vi.getLienzo().getImage(true);
            volcado();
            if(imgSource!=null){
                try{
                    int type = LookupTableProducer.TYPE_POWER;
                    LookupTable lt = LookupTableProducer.createLookupTable(type);
                    LookupOp lop = new LookupOp(lt, null);
                    // Imagen origen y destino iguales
                    lop.filter(imgSource , imgSource);
                    vi.repaint();
                } 
                catch(Exception e){
                    System.err.println(e.getLocalizedMessage());
                }
            } 
        }
    }//GEN-LAST:event_oscurecerActionPerformed

    private void barraRotacionFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_barraRotacionFocusGained
        // TODO add your handling code here:
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        
        if(vi!=null){
            vi.getLienzo().getFigura().setEditar(false);
            ColorModel cm = vi.getLienzo().getImage(true).getColorModel(); 
            WritableRaster raster = vi.getLienzo().getImage(true).copyData(null);
            boolean alfaPre = vi.getLienzo().getImage(true).isAlphaPremultiplied();
            volcado();
            imagenFuente = new BufferedImage(cm,raster,alfaPre,null);
        }
    }//GEN-LAST:event_barraRotacionFocusGained

    private void barraRotacionFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_barraRotacionFocusLost
        // TODO add your handling code here:
        imagenFuente = null;
    }//GEN-LAST:event_barraRotacionFocusLost

    private void barraRotacionStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_barraRotacionStateChanged
        // TODO add your handling code here:
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        
        if (vi != null) {
            double r = Math.toRadians(barraRotacion.getValue());
            Point c = new Point(imagenFuente.getWidth()/2, imagenFuente.getHeight()/2);
            AffineTransform at = AffineTransform.getRotateInstance(r,c.x,c.y);
            AffineTransformOp atop;
            atop = new AffineTransformOp(at,AffineTransformOp.TYPE_BILINEAR);
            BufferedImage imgdest = atop.filter(imagenFuente, null);
            vi.getLienzo().setImage(imgdest);
            vi.getLienzo().repaint(); 
        } 
    }//GEN-LAST:event_barraRotacionStateChanged
    
    private void aumentarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aumentarActionPerformed
        // TODO add your handling code here:
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        
        if (vi != null) {
            vi.getLienzo().getFigura().setEditar(false);
            BufferedImage imgSource = vi.getLienzo().getImage(true);
            volcado();
            if(imgSource!=null){
                try{
                    AffineTransform at = AffineTransform.getScaleInstance(1.25, 1.25);
                    AffineTransformOp atop = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
                    BufferedImage imgdest = atop.filter(imgSource, null);
                    vi.getLienzo().setImage(imgdest);
                    vi.getLienzo().repaint(); 
                } 
                catch(Exception e){
                    System.err.println(e.getLocalizedMessage());
                }
            } 
        }
    }//GEN-LAST:event_aumentarActionPerformed

    private void altoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_altoKeyReleased
        // TODO add your handling code here:
        if(!"300".equals(alto.getText())){
            redimensionar.setVisible(true);
            escalar.setVisible(true);
            aceptar.setText("Cancelar");
        }
    }//GEN-LAST:event_altoKeyReleased

    private void anchoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_anchoKeyReleased
        // TODO add your handling code here:
        if(!"300".equals(ancho.getText())){
            redimensionar.setVisible(true);
            escalar.setVisible(true);
            aceptar.setText("Cancelar");
        }
    }//GEN-LAST:event_anchoKeyReleased

    private void escalarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_escalarActionPerformed
        // TODO add your handling code here:
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        
        if (vi != null) {
            Dimension dimen = new Dimension();
            dimen.height = Integer.parseInt(alto.getText());
            dimen.width = Integer.parseInt(ancho.getText());
            vi.getLienzo().getFigura().setEditar(false);
            BufferedImage img = vi.getLienzo().getImage(true);
            volcado();
            BufferedImage imgNueva = new BufferedImage(dimen.width, dimen.height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = imgNueva.createGraphics();
            g2d.drawImage(img, 0, 0, imgNueva.getWidth(), imgNueva.getHeight(), 0, 0, img.getWidth(), img.getHeight(), vi.getLienzo());
            vi.getLienzo().setImage(imgNueva);
            vi.getLienzo().repaint();
            tamImagen.setVisible(false);
        }
    }//GEN-LAST:event_escalarActionPerformed

    private void redimensionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_redimensionarActionPerformed
        // TODO add your handling code here:
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        
        if (vi != null) {
            Dimension dimen = new Dimension();
            dimen.height = Integer.parseInt(alto.getText());
            dimen.width = Integer.parseInt(ancho.getText());
            vi.getLienzo().getFigura().setEditar(false);
            BufferedImage img = vi.getLienzo().getImage(true);
            volcado();
            BufferedImage imgNueva = new BufferedImage(dimen.width, dimen.height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = imgNueva.createGraphics();
            g2d.setColor(Color.white);
            g2d.fillRect(0, 0, dimen.width, dimen.height);
            g2d.drawImage(img, 0, 0, vi.getLienzo());
            vi.getLienzo().setImage(imgNueva);
            vi.getLienzo().repaint();
            tamImagen.setVisible(false);
        }
    }//GEN-LAST:event_redimensionarActionPerformed

    private void aceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aceptarActionPerformed
        // TODO add your handling code here:
        tamImagen.setVisible(false);
    }//GEN-LAST:event_aceptarActionPerformed

    private void disminuirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_disminuirActionPerformed
        // TODO add your handling code here:
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        
        if (vi != null) {
            vi.getLienzo().getFigura().setEditar(false);
            BufferedImage imgSource = vi.getLienzo().getImage(true);
            volcado();
            if(imgSource!=null){
                try{
                    AffineTransform at = AffineTransform.getScaleInstance(0.75, 0.75);
                    AffineTransformOp atop = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
                    BufferedImage imgdest = atop.filter(imgSource, null);
                    vi.getLienzo().setImage(imgdest);
                    vi.getLienzo().repaint(); 
                } 
                catch(Exception e){
                    System.err.println(e.getLocalizedMessage());
                }
            } 
        }
    }//GEN-LAST:event_disminuirActionPerformed

    private void anchoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_anchoKeyTyped
        // TODO add your handling code here:
        char numero = evt.getKeyChar();
        
        if(numero < '0' || numero > '9')
            evt.consume();
    }//GEN-LAST:event_anchoKeyTyped

    private void altoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_altoKeyTyped
        // TODO add your handling code here:
        char numero = evt.getKeyChar();
        
        if(numero < '0' || numero > '9')
            evt.consume();
    }//GEN-LAST:event_altoKeyTyped

    private void bandaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bandaActionPerformed
        // TODO add your handling code here:
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        
        if (vi != null) {
        BufferedImage imgSource = vi.getLienzo().getImage();
        int iBanda = 0;
        String nombre = vi.getTitle();
            if(imgSource!= null){
                //Creamos el modelo de color de la nueva imagen basado en un espcio de color GRAY
                ColorSpace cs = new sm.image.color.GreyColorSpace(); 
                ComponentColorModel cm = new ComponentColorModel(cs, false, false, Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
                
                //Creamos el nuevo raster a partir del raster de la imagen original
                int bandList[] = {iBanda};
                
                for(iBanda = 0; iBanda < imgSource.getRaster().getNumBands(); iBanda++){
                    bandList[0] = iBanda;
                    WritableRaster bandRaster = (WritableRaster)imgSource.getRaster().createWritableChild(0,0,imgSource.getWidth(), imgSource.getHeight(), 0, 0, bandList);
                    //Creamos una nueva imagen que contiene como raster el correspondiente a la banda
                    BufferedImage imgBanda = new BufferedImage(cm, bandRaster, false, null);
                    nuevoActionPerformed(nombre + " [banda " + iBanda +"]", imgBanda);
                }
            }
        }
    }//GEN-LAST:event_bandaActionPerformed

    private void espacioColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_espacioColorActionPerformed
        // TODO add your handling code here:
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        
        if (vi != null) {
            vi.getLienzo().getFigura().setEditar(false);
            BufferedImage imgSource = vi.getLienzo().getImage(true);
            volcado();
            String nombre = "";
            if(imgSource!= null){ 
                if(espacioColor.getSelectedItem() == "YCC"){
                    if (imgSource.getColorModel().getColorSpace().isCS_sRGB()) {
                        ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_PYCC);
                        ColorConvertOp cop = new ColorConvertOp(cs, null);
                        BufferedImage imgOut = cop.filter(imgSource, null);
                        nombre = vi.getTitle();
                        nuevoActionPerformed(nombre + " [YCC]", imgOut);
                    }
                    else{
                        mensaje(4);
                    }
                }
                else if(espacioColor.getSelectedItem() == "sRGB"){
                    if (imgSource.getColorModel().getColorSpace().isCS_sRGB()) {
                        mensaje(3);
                    }
                    else{
                        ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_sRGB);
                        ColorConvertOp cop = new ColorConvertOp(cs, null);
                        BufferedImage imgOut = cop.filter(imgSource, null);
                        nombre = vi.getTitle();
                        nuevoActionPerformed(nombre + " [sRGB]", imgOut);
                    }
                }
                else if(espacioColor.getSelectedItem() == "Grey"){
                    ColorSpace cs = new sm.image.color.GreyColorSpace();
                    ColorConvertOp cop = new ColorConvertOp(cs, null);
                    BufferedImage imgOut = cop.filter(imgSource, null);
                    nombre = vi.getTitle();
                    nuevoActionPerformed(nombre + " [Grey]", imgOut);
                }
                else if(espacioColor.getSelectedItem() == "YCbCr"){
                    if (imgSource.getColorModel().getColorSpace().isCS_sRGB()) {
                        ColorSpace cs = new sm.image.color.YCbCrColorSpace();
                        ColorConvertOp cop = new ColorConvertOp(cs, null);
                        BufferedImage imgOut = cop.filter(imgSource, null);
                        nombre = vi.getTitle();
                        nuevoActionPerformed(nombre + " [YCbCr]", imgOut);
                    }
                    else{
                        mensaje(4);
                    }
                }
            }
        }
    }//GEN-LAST:event_espacioColorActionPerformed

    private void sepiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sepiaActionPerformed
        // TODO add your handling code here:
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        
        if (vi != null) {
            vi.getLienzo().getFigura().setEditar(false);
            BufferedImage imgSource = vi.getLienzo().getImage(true);
            volcado();
            if(imgSource!=null){
                try{
                    SepiaOp sop = new SepiaOp();
                    sop.filter(imgSource, imgSource);
                    vi.getLienzo().repaint(); 
                } 
                catch(Exception e){
                    System.err.println(e.getLocalizedMessage());
                }
            } 
        }
    }//GEN-LAST:event_sepiaActionPerformed

    private void tintarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tintarActionPerformed
        // TODO add your handling code here:
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        
        if (vi != null) {
            BufferedImage imgSource = vi.getLienzo().getImage(true);
            
            if(imgSource!=null){
                try{
                    Color c = JColorChooser.showDialog(this, "Selección color" , Color.white);      
                    vi.getLienzo().setColorBorde(c);
                    
                    TintOp top = new TintOp(vi.getLienzo().getColorBorde(),0.5f);
                    top.filter(imgSource, imgSource);
                    vi.getLienzo().repaint();
                } 
                catch(Exception e){
                    System.err.println(e.getLocalizedMessage());
                }
            }
            //volcado();
        }
                
    }//GEN-LAST:event_tintarActionPerformed

    private void ecualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ecualizarActionPerformed
        // TODO add your handling code here:
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        
        if (vi != null) {
            vi.getLienzo().getFigura().setEditar(false);
            BufferedImage imgSource = vi.getLienzo().getImage(true);
            volcado();
            if(imgSource!=null){
                try{
                    EqualizationOp eop = new EqualizationOp();
                    eop.filter(imgSource, imgSource);
                    vi.getLienzo().repaint();
                } 
                catch(Exception e){
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_ecualizarActionPerformed

    private void umbralStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_umbralStateChanged
        // TODO add your handling code here:
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        
        if (vi != null) {
            vi.getLienzo().getFigura().setEditar(false);
            BufferedImage imgSource = vi.getLienzo().getImage(true);
            volcado();
            if(imgSource!=null){
                try{
                    UmbralizacionOp uop = new UmbralizacionOp(umbral.getValue());
                    uop.filter(imagenFuente, imgSource);
                    escritorio.repaint(); 
                } 
                catch(Exception e){
                    System.err.println(e.getLocalizedMessage());
                }
            } 
        }    
    }//GEN-LAST:event_umbralStateChanged

    private void umbralFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_umbralFocusGained
        // TODO add your handling code here:
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        
        if(vi!=null){
            vi.getLienzo().getFigura().setEditar(false);
            ColorModel cm = vi.getLienzo().getImage(true).getColorModel(); 
            WritableRaster raster = vi.getLienzo().getImage(true).copyData(null);
            boolean alfaPre = vi.getLienzo().getImage(true).isAlphaPremultiplied();
            imagenFuente = new BufferedImage(cm,raster,alfaPre,null);
        }
    }//GEN-LAST:event_umbralFocusGained

    private void umbralFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_umbralFocusLost
        // TODO add your handling code here:
        imagenFuente = null;
    }//GEN-LAST:event_umbralFocusLost

    private void sinusoidalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sinusoidalActionPerformed
        // TODO add your handling code here:
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        
        if (vi != null) {
            vi.getLienzo().getFigura().setEditar(false);
            BufferedImage imgSource = vi.getLienzo().getImage(true);
            volcado();
            if(imgSource!=null){
                LookupTable lt = seno(2.0);
                LookupOp lop = new LookupOp(lt, null);
                lop.filter(imgSource, imgSource);
                escritorio.repaint(); 
            } 
        } 
    }//GEN-LAST:event_sinusoidalActionPerformed

    private void abrirAudioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_abrirAudioActionPerformed
        // TODO add your handling code here:
        int ventana = tipoVentana();           
        JFileChooser dlg = new JFileChooser();
        
        dlg.setFileFilter(filtroAudios);
        int resp = dlg.showOpenDialog(this);
        if( resp == JFileChooser.APPROVE_OPTION) {
            try{
                File f = dlg.getSelectedFile();
                VentanaInternaReproductor vir = new VentanaInternaReproductor(f);
                if(formatoCorrecto(f)){
                    switch (ventana) {
                        case 0:
                            VentanaInterna actualI = (VentanaInterna)escritorio.getSelectedFrame();
                            if(actualI != null){
                                vir.setLocation(actualI.getX()+20, actualI.getY()+20);
                            }   
                            break;
                            
                        case 1:
                            VentanaInternaReproductor actualR = (VentanaInternaReproductor)escritorio.getSelectedFrame();
                            if(actualR != null){
                                vir.setLocation(actualR.getX()+20, actualR.getY()+20);
                            }   
                            break;
                            
                        case 2:
                            VentanaInternaGrabador actualG = (VentanaInternaGrabador)escritorio.getSelectedFrame();
                            if(actualG != null){
                                vir.setLocation(actualG.getX()+20, actualG.getY()+20);
                            }   
                            break;
                            
                        case 3:
                            VentanaInternaVLCPlayer actualVLC = (VentanaInternaVLCPlayer)escritorio.getSelectedFrame();
                            if(actualVLC != null){
                                vir.setLocation(actualVLC.getX()+20, actualVLC.getY()+20);
                            }  
                            break;
                            
                        case 4:
                            VentanaInternaCamara actualC = (VentanaInternaCamara)escritorio.getSelectedFrame();
                            if(actualC != null){
                                vir.setLocation(actualC.getX()+20, actualC.getY()+20);
                            }  
                            break;
                            
                        case 5:
                            VentanaInternaJMFPlayer actualJMF = (VentanaInternaJMFPlayer)escritorio.getSelectedFrame();
                            if(actualJMF != null){
                                vir.setLocation(actualJMF.getX()+20, actualJMF.getY()+20);
                            }   
                            break;
                    }
                    this.escritorio.add(vir);
                    vir.setTitle(f.getName());
                    vir.setVisible(true);
                }
                else{
                    mensaje(5);
                }
            }
            catch(Exception ex){
                System.err.println("Error al abrir el audio");
            }
        }
    }//GEN-LAST:event_abrirAudioActionPerformed

    private void abrirAudioActionPerformed(java.awt.event.ActionEvent evt, int resp, File f) {                                           
        // TODO add your handling code here:
        int ventana = tipoVentana();
        
        if( resp == JFileChooser.APPROVE_OPTION) {
            try{
                VentanaInternaReproductor vir = new VentanaInternaReproductor(f);
                if(formatoCorrecto(f)){
                    switch (ventana) {
                        case 0:
                            VentanaInterna actualI = (VentanaInterna)escritorio.getSelectedFrame();
                            if(actualI != null){
                                vir.setLocation(actualI.getX()+20, actualI.getY()+20);
                            }   
                            break;
                            
                        case 1:
                            VentanaInternaReproductor actualR = (VentanaInternaReproductor)escritorio.getSelectedFrame();
                            if(actualR != null){
                                vir.setLocation(actualR.getX()+20, actualR.getY()+20);
                            }   
                            break;
                            
                        case 2:
                            VentanaInternaGrabador actualG = (VentanaInternaGrabador)escritorio.getSelectedFrame();
                            if(actualG != null){
                                vir.setLocation(actualG.getX()+20, actualG.getY()+20);
                            }   
                            break;
                            
                        case 3:
                            VentanaInternaVLCPlayer actualVLC = (VentanaInternaVLCPlayer)escritorio.getSelectedFrame();
                            if(actualVLC != null){
                                vir.setLocation(actualVLC.getX()+20, actualVLC.getY()+20);
                            }  
                            break;
                            
                        case 4:
                            VentanaInternaCamara actualC = (VentanaInternaCamara)escritorio.getSelectedFrame();
                            if(actualC != null){
                                vir.setLocation(actualC.getX()+20, actualC.getY()+20);
                            }  
                            break;
                            
                        case 5:
                            VentanaInternaJMFPlayer actualJMF = (VentanaInternaJMFPlayer)escritorio.getSelectedFrame();
                            if(actualJMF != null){
                                vir.setLocation(actualJMF.getX()+20, actualJMF.getY()+20);
                            }   
                            break;
                    }
                    this.escritorio.add(vir);
                    vir.setTitle(f.getName());
                    vir.setVisible(true);
                }
                else{
                    mensaje(5);
                }
            }
            catch(Exception ex){
                System.err.println("Error al abrir el audio");
            }
        }
    }
    
    private void guardarAudioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarAudioActionPerformed
        // TODO add your handling code here:
        int ventana = tipoVentana();
        JFileChooser dlg = new JFileChooser();
        
        dlg.setFileFilter(filtroAudios);
        dlg.setFileFilter(filtroAU);
        dlg.setFileFilter(filtroWAB);
        dlg.setFileFilter(filtroAIF); 
        dlg.setFileFilter(filtroMP3);
        dlg.setFileFilter(filtroMID);
        
        int resp = dlg.showSaveDialog(this);
        if( resp == JFileChooser.APPROVE_OPTION) {
            try{
                File f = dlg.getSelectedFile();
                
                if(dlg.getFileFilter() == filtroAU)
                    f = new File(dlg.getSelectedFile() + ".au");
                else if(dlg.getFileFilter() == filtroWAB)
                    f = new File(dlg.getSelectedFile() + ".wav");
                else if(dlg.getFileFilter() == filtroAIF)
                    f = new File(dlg.getSelectedFile() + ".aif");
                else if(dlg.getFileFilter() == filtroMP3)
                    f = new File(dlg.getSelectedFile() + ".mp3");
                else if(dlg.getFileFilter() == filtroMID)
                    f = new File(dlg.getSelectedFile() + ".mid");
                
                VentanaInternaGrabador vig = new VentanaInternaGrabador(f);
                if(formatoCorrecto(f)){
                    switch (ventana) {
                        case 0:
                            VentanaInterna actualI = (VentanaInterna)escritorio.getSelectedFrame();
                            if(actualI != null){
                                vig.setLocation(actualI.getX()+20, actualI.getY()+20);
                            }   
                            break;
                            
                        case 1:
                            VentanaInternaReproductor actualR = (VentanaInternaReproductor)escritorio.getSelectedFrame();
                            if(actualR != null){
                                vig.setLocation(actualR.getX()+20, actualR.getY()+20);
                            }  
                            break;
                            
                        case 2:
                            VentanaInternaGrabador actualG = (VentanaInternaGrabador)escritorio.getSelectedFrame();
                            if(actualG != null){
                                vig.setLocation(actualG.getX()+20, actualG.getY()+20);
                            }   
                            break;
                            
                        case 3:
                            VentanaInternaVLCPlayer actualVLC = (VentanaInternaVLCPlayer)escritorio.getSelectedFrame();
                            if(actualVLC != null){
                                vig.setLocation(actualVLC.getX()+20, actualVLC.getY()+20);
                            }  
                            break;
                            
                        case 4:
                            VentanaInternaCamara actualC = (VentanaInternaCamara)escritorio.getSelectedFrame();
                            if(actualC != null){
                                vig.setLocation(actualC.getX()+20, actualC.getY()+20);
                            }  
                            break;
                            
                         case 5:
                            VentanaInternaJMFPlayer actualJMF = (VentanaInternaJMFPlayer)escritorio.getSelectedFrame();
                            if(actualJMF != null){
                                vig.setLocation(actualJMF.getX()+20, actualJMF.getY()+20);
                            }   
                            break;
                    }
  
                    this.escritorio.add(vig);
                    vig.setTitle(f.getName());
                    vig.setVisible(true);
                }
                else{
                    mensaje(6);
                }
            }
            catch(Exception ex){
                System.err.println("Error al guardar el audio");
            }
        }
    }//GEN-LAST:event_guardarAudioActionPerformed

    private void abrirVideoVLCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_abrirVideoVLCActionPerformed
        // TODO add your handling code here:
        int ventana = tipoVentana();          
        JFileChooser dlg = new JFileChooser();
        
        dlg.setFileFilter(filtroVideosVLC);
        int resp = dlg.showOpenDialog(this);
        if( resp == JFileChooser.APPROVE_OPTION) {
            try{
                File f = dlg.getSelectedFile();
                VentanaInternaVLCPlayer vlc = new VentanaInternaVLCPlayer(f);
                if(formatoCorrecto(f)){
                    switch (ventana) {
                        case 0:
                            VentanaInterna actualI = (VentanaInterna)escritorio.getSelectedFrame();
                            if(actualI != null){
                                vlc.setLocation(actualI.getX()+20, actualI.getY()+20);
                            }   
                            break;
                            
                        case 1:
                            VentanaInternaReproductor actualR = (VentanaInternaReproductor)escritorio.getSelectedFrame();
                            if(actualR != null){
                                vlc.setLocation(actualR.getX()+20, actualR.getY()+20);
                            }  
                            break;
                            
                        case 2:
                            VentanaInternaGrabador actualG = (VentanaInternaGrabador)escritorio.getSelectedFrame();
                            if(actualG != null){
                                vlc.setLocation(actualG.getX()+20, actualG.getY()+20);
                            }  
                            break;
                            
                        case 3:
                            VentanaInternaVLCPlayer actualVLC = (VentanaInternaVLCPlayer)escritorio.getSelectedFrame();
                            if(actualVLC != null){
                                vlc.setLocation(actualVLC.getX()+20, actualVLC.getY()+20);
                            }  
                            break;
                            
                        case 4:
                            VentanaInternaCamara actualC = (VentanaInternaCamara)escritorio.getSelectedFrame();
                            if(actualC != null){
                                vlc.setLocation(actualC.getX()+20, actualC.getY()+20);
                            }   
                            break;
                            
                        case 5:
                            VentanaInternaJMFPlayer actualJMF = (VentanaInternaJMFPlayer)escritorio.getSelectedFrame();
                            if(actualJMF != null){
                                vlc.setLocation(actualJMF.getX()+20, actualJMF.getY()+20);
                            }   
                            break;
                    }
                    this.escritorio.add(vlc);
                    vlc.setTitle(f.getName());
                    vlc.setVisible(true);
                }
                else{
                    mensaje(7);
                }
            }
            catch(Exception ex){
                System.err.println("Error al abrir el video " + ex);
            }
        }
    }//GEN-LAST:event_abrirVideoVLCActionPerformed
    
    private void abrirVideoVLCActionPerformed(java.awt.event.ActionEvent evt, int resp, File f) {                                              
        // TODO add your handling code here:
        int ventana = tipoVentana();          
        
        if( resp == JFileChooser.APPROVE_OPTION) {
            try{
                VentanaInternaVLCPlayer vlc = new VentanaInternaVLCPlayer(f);
                if(formatoCorrecto(f)){
                    switch (ventana) {
                        case 0:
                            VentanaInterna actualI = (VentanaInterna)escritorio.getSelectedFrame();
                            if(actualI != null){
                                vlc.setLocation(actualI.getX()+20, actualI.getY()+20);
                            }   
                            break;
                            
                        case 1:
                            VentanaInternaReproductor actualR = (VentanaInternaReproductor)escritorio.getSelectedFrame();
                            if(actualR != null){
                                vlc.setLocation(actualR.getX()+20, actualR.getY()+20);
                            }  
                            break;
                            
                        case 2:
                            VentanaInternaGrabador actualG = (VentanaInternaGrabador)escritorio.getSelectedFrame();
                            if(actualG != null){
                                vlc.setLocation(actualG.getX()+20, actualG.getY()+20);
                            }  
                            break;
                            
                        case 3:
                            VentanaInternaVLCPlayer actualVLC = (VentanaInternaVLCPlayer)escritorio.getSelectedFrame();
                            if(actualVLC != null){
                                vlc.setLocation(actualVLC.getX()+20, actualVLC.getY()+20);
                            }  
                            break;
                            
                        case 4:
                            VentanaInternaCamara actualC = (VentanaInternaCamara)escritorio.getSelectedFrame();
                            if(actualC != null){
                                vlc.setLocation(actualC.getX()+20, actualC.getY()+20);
                            }   
                            break;
                            
                        case 5:
                            VentanaInternaJMFPlayer actualJMF = (VentanaInternaJMFPlayer)escritorio.getSelectedFrame();
                            if(actualJMF != null){
                                vlc.setLocation(actualJMF.getX()+20, actualJMF.getY()+20);
                            }   
                            break;
                    }
                    this.escritorio.add(vlc);
                    vlc.setTitle(f.getName());
                    vlc.setVisible(true);
                }
                else{
                    mensaje(7);
                }
            }
            catch(Exception ex){
                System.err.println("Error al abrir el video " + ex);
            }
        }
    }
    
    private void camaraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_camaraActionPerformed
       // TODO add your handling code here:
        int ventana = tipoVentana();        
        VentanaInternaCamara vic = new VentanaInternaCamara();
        
        switch (ventana) {
            case 0:
                VentanaInterna actualI = (VentanaInterna)escritorio.getSelectedFrame();
                if(actualI != null){
                    vic.setLocation(actualI.getX()+20, actualI.getY()+20);
                }  
                break;
                
            case 1:
                VentanaInternaReproductor actualR = (VentanaInternaReproductor)escritorio.getSelectedFrame();
                if(actualR != null){
                    vic.setLocation(actualR.getX()+20, actualR.getY()+20);
                }  
                break;
                
            case 2:
                VentanaInternaGrabador actualG = (VentanaInternaGrabador)escritorio.getSelectedFrame();
                if(actualG != null){
                    vic.setLocation(actualG.getX()+20, actualG.getY()+20);
                } 
                break;
                
            case 3:
                VentanaInternaVLCPlayer actualVLC = (VentanaInternaVLCPlayer)escritorio.getSelectedFrame();
                if(actualVLC != null){
                    vic.setLocation(actualVLC.getX()+20, actualVLC.getY()+20);
                }  
                break;
                
            case 4:
                VentanaInternaCamara actualC = (VentanaInternaCamara)escritorio.getSelectedFrame();
                if(actualC != null){
                    vic.setLocation(actualC.getX()+20, actualC.getY()+20);
                }  
                break;
                
            case 5:
                VentanaInternaJMFPlayer actualJMF = (VentanaInternaJMFPlayer)escritorio.getSelectedFrame();
                if(actualJMF != null){
                    vic.setLocation(actualJMF.getX()+20, actualJMF.getY()+20);
                }   
                break;
            }
            this.escritorio.add(vic);
            vic.setVisible(true);
        
    }//GEN-LAST:event_camaraActionPerformed

    private void capturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_capturaActionPerformed
        // TODO add your handling code here:
        VentanaInterna vi = new VentanaInterna(this);
        int ventana = tipoVentana();
        
        if(tipoVentana() == 3 || tipoVentana() == 4 || tipoVentana() == 5){
            switch (ventana) {
                case 0:
                    VentanaInterna actualI = (VentanaInterna)escritorio.getSelectedFrame();
                    if(actualI != null){
                        vi.setLocation(actualI.getX()+20, actualI.getY()+20);
                    }   
                    break;

                case 1:
                    VentanaInternaReproductor actualR = (VentanaInternaReproductor)escritorio.getSelectedFrame();
                    if(actualR != null){
                        vi.setLocation(actualR.getX()+20, actualR.getY()+20);
                    }  
                    break;

                case 2:
                    VentanaInternaGrabador actualG = (VentanaInternaGrabador)escritorio.getSelectedFrame();
                    if(actualG != null){
                        vi.setLocation(actualG.getX()+20, actualG.getY()+20);
                    }  
                    break;

                case 3:
                    VentanaInternaVLCPlayer actualVLC = (VentanaInternaVLCPlayer)escritorio.getSelectedFrame();
                    if(actualVLC != null){
                        vi.setLocation(actualVLC.getX()+20, actualVLC.getY()+20);
                    }  
                    break;

                case 4:
                    VentanaInternaCamara actualC = (VentanaInternaCamara)escritorio.getSelectedFrame();
                    if(actualC != null){
                        vi.setLocation(actualC.getX()+20, actualC.getY()+20);
                    }   
                    break;
                
                case 5:
                    VentanaInternaJMFPlayer actualJMF = (VentanaInternaJMFPlayer)escritorio.getSelectedFrame();
                    if(actualJMF != null){
                        vi.setLocation(actualJMF.getX()+20, actualJMF.getY()+20);
                    }   
                    break;
            }
        
            try{
                if(tipoVentana() == 4){
                    VentanaInternaCamara vc = (VentanaInternaCamara)escritorio.getSelectedFrame();
                    BufferedImage captura = vc.getCamera().getImage();
                    BufferedImage img;
                    img = new BufferedImage(captura.getWidth(),captura.getHeight(),BufferedImage.TYPE_3BYTE_BGR);
                    Graphics2D g2d = img.createGraphics();
                    g2d.drawImage(captura, 0, 0, null);  
                    vi.getLienzo().setImage(img);
                }
                else if(tipoVentana() == 3){
                    VentanaInternaVLCPlayer vlc = (VentanaInternaVLCPlayer)escritorio.getSelectedFrame();
                    BufferedImage captura = vlc.capturaVideo();
                    BufferedImage img;
                    img = new BufferedImage(captura.getWidth(),captura.getHeight(),BufferedImage.TYPE_3BYTE_BGR);
                    Graphics2D g2d = img.createGraphics();
                    g2d.drawImage(captura, 0, 0, null);
                    vi.getLienzo().setImage(img);
                } 
                else if(tipoVentana() == 5){
                    VentanaInternaJMFPlayer jmf = (VentanaInternaJMFPlayer)escritorio.getSelectedFrame();
                    BufferedImage captura = jmf.capturaVideo();
                    BufferedImage img;
                    img = new BufferedImage(captura.getWidth(),captura.getHeight(),BufferedImage.TYPE_3BYTE_BGR);
                    Graphics2D g2d = img.createGraphics();
                    g2d.drawImage(captura, 0, 0, null);
                    vi.getLienzo().setImage(img);
                }
                vi.setTitle("Captura");
                this.escritorio.add(vi);
                vi.setVisible(true);
            }
            catch(Exception ex){
                System.err.println("Error al leer la imagen " + ex);
            }
        } 
        else
            mensaje(9);
    }//GEN-LAST:event_capturaActionPerformed

    private void acercaDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acercaDeActionPerformed
        // TODO add your handling code here:
        ayuda.setLocationRelativeTo(this);
        ayuda.setVisible(true);
    }//GEN-LAST:event_acercaDeActionPerformed

    private void verBarraVideoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verBarraVideoActionPerformed
        // TODO add your handling code here:
        captura.setVisible(verBarraVideo.isSelected());
        separador5.setVisible(verBarraVideo.isSelected());
    }//GEN-LAST:event_verBarraVideoActionPerformed

    private void verBarraEdicionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verBarraEdicionActionPerformed
        // TODO add your handling code here:
        barraEdicion.setVisible(verBarraEdicion.isSelected());
    }//GEN-LAST:event_verBarraEdicionActionPerformed

    private void guardarAudio1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarAudio1ActionPerformed
        // TODO add your handling code here:
        guardarAudioActionPerformed(evt);
    }//GEN-LAST:event_guardarAudio1ActionPerformed

    private void camara2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_camara2ActionPerformed
        // TODO add your handling code here:
        camaraActionPerformed(evt);
    }//GEN-LAST:event_camara2ActionPerformed

    private void abrirVideoJMFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_abrirVideoJMFActionPerformed
        // TODO add your handling code here:
        int ventana = tipoVentana();          
        JFileChooser dlg = new JFileChooser();
        
        dlg.setFileFilter(filtroVideosJMF);
        int resp = dlg.showOpenDialog(this);
        if( resp == JFileChooser.APPROVE_OPTION) {
            try{
                File f = dlg.getSelectedFile();
                VentanaInternaJMFPlayer jmf = new VentanaInternaJMFPlayer(f);
                if(formatoCorrecto(f)){
                    switch (ventana) {
                        case 0:
                            VentanaInterna actualI = (VentanaInterna)escritorio.getSelectedFrame();
                            if(actualI != null){
                                jmf.setLocation(actualI.getX()+20, actualI.getY()+20);
                            }   
                            break;
                            
                        case 1:
                            VentanaInternaReproductor actualR = (VentanaInternaReproductor)escritorio.getSelectedFrame();
                            if(actualR != null){
                                jmf.setLocation(actualR.getX()+20, actualR.getY()+20);
                            }  
                            break;
                            
                        case 2:
                            VentanaInternaGrabador actualG = (VentanaInternaGrabador)escritorio.getSelectedFrame();
                            if(actualG != null){
                                jmf.setLocation(actualG.getX()+20, actualG.getY()+20);
                            }  
                            break;
                            
                        case 3:
                            VentanaInternaVLCPlayer actualVLC = (VentanaInternaVLCPlayer)escritorio.getSelectedFrame();
                            if(actualVLC != null){
                                jmf.setLocation(actualVLC.getX()+20, actualVLC.getY()+20);
                            }  
                            break;
                            
                        case 4:
                            VentanaInternaCamara actuaC = (VentanaInternaCamara)escritorio.getSelectedFrame();
                            if(actuaC != null){
                                jmf.setLocation(actuaC.getX()+20, actuaC.getY()+20);
                            }   
                            break;
                         
                        case 5:
                            VentanaInternaJMFPlayer actualJMF = (VentanaInternaJMFPlayer)escritorio.getSelectedFrame();
                            if(actualJMF != null){
                                jmf.setLocation(actualJMF.getX()+20, actualJMF.getY()+20);
                            }   
                            break;
                    }
                    this.escritorio.add(jmf);
                    jmf.setTitle(f.getName());
                    jmf.setVisible(true);
                }
                else{
                    mensaje(7);
                }
            }
            catch(Exception ex){
                System.err.println("Error al abrir el video " + ex);
            }
        }
    }//GEN-LAST:event_abrirVideoJMFActionPerformed

    private void abrirVideoJMFActionPerformed(java.awt.event.ActionEvent evt, int resp, File f) {                                              
        // TODO add your handling code here:
        int ventana = tipoVentana();  
        
        if( resp == JFileChooser.APPROVE_OPTION) {
            try{
                VentanaInternaJMFPlayer jmf = new VentanaInternaJMFPlayer(f);
                if(formatoCorrecto(f)){
                    switch (ventana) {
                        case 0:
                            VentanaInterna actualI = (VentanaInterna)escritorio.getSelectedFrame();
                            if(actualI != null){
                                jmf.setLocation(actualI.getX()+20, actualI.getY()+20);
                            }   
                            break;
                            
                        case 1:
                            VentanaInternaReproductor actualR = (VentanaInternaReproductor)escritorio.getSelectedFrame();
                            if(actualR != null){
                                jmf.setLocation(actualR.getX()+20, actualR.getY()+20);
                            }  
                            break;
                            
                        case 2:
                            VentanaInternaGrabador actualG = (VentanaInternaGrabador)escritorio.getSelectedFrame();
                            if(actualG != null){
                                jmf.setLocation(actualG.getX()+20, actualG.getY()+20);
                            }  
                            break;
                            
                        case 3:
                            VentanaInternaVLCPlayer actualVLC = (VentanaInternaVLCPlayer)escritorio.getSelectedFrame();
                            if(actualVLC != null){
                                jmf.setLocation(actualVLC.getX()+20, actualVLC.getY()+20);
                            }  
                            break;
                            
                        case 4:
                            VentanaInternaCamara actuaC = (VentanaInternaCamara)escritorio.getSelectedFrame();
                            if(actuaC != null){
                                jmf.setLocation(actuaC.getX()+20, actuaC.getY()+20);
                            }   
                            break;
                         
                        case 5:
                            VentanaInternaJMFPlayer actualJMF = (VentanaInternaJMFPlayer)escritorio.getSelectedFrame();
                            if(actualJMF != null){
                                jmf.setLocation(actualJMF.getX()+20, actualJMF.getY()+20);
                            }   
                            break;
                    }
                    this.escritorio.add(jmf);
                    jmf.setTitle(f.getName());
                    jmf.setVisible(true);
                }
                else{
                    mensaje(7);
                }
            }
            catch(Exception ex){
                System.err.println("Error al abrir el video " + ex);
            }
        }
    }
    
    private void verBarraSliderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verBarraSliderActionPerformed
        // TODO add your handling code here:
        barraSlider.setVisible(verBarraSlider.isSelected());
    }//GEN-LAST:event_verBarraSliderActionPerformed

    private void eleccionColoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eleccionColoresActionPerformed
        // TODO add your handling code here:
        Color c = JColorChooser.showDialog(this, "Selección color" , Color.white);
        VentanaInterna vi = (VentanaInterna)escritorio.getSelectedFrame();
        
        vi.getLienzo().getFigura().setColorBorde(c);

        this.repaint();      
    }//GEN-LAST:event_eleccionColoresActionPerformed

    private void gradoTransparenciaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_gradoTransparenciaFocusGained
        // TODO add your handling code here:
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        
        if(vi!=null){
            ColorModel cm = vi.getLienzo().getImage().getColorModel(); 
            WritableRaster raster = vi.getLienzo().getImage().copyData(null);
            boolean alfaPre = vi.getLienzo().getImage().isAlphaPremultiplied();
            imagenFuente = new BufferedImage(cm,raster,alfaPre,null);
        }
    }//GEN-LAST:event_gradoTransparenciaFocusGained

    private void gradoTransparenciaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_gradoTransparenciaFocusLost
        // TODO add your handling code here:
        imagenFuente = null;
    }//GEN-LAST:event_gradoTransparenciaFocusLost

    private void gradoTransparenciaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_gradoTransparenciaStateChanged
        // TODO add your handling code here:
        VentanaInterna vi = (VentanaInterna)escritorio.getSelectedFrame();
        
        if(vi != null){
            if(imagenFuente != null){
                try{
                    BufferedImage imgNueva = new BufferedImage(Integer.parseInt(ancho.getText()),Integer.parseInt(alto.getText()), BufferedImage.TYPE_INT_RGB);
                    Graphics2D g2d = imgNueva.createGraphics();
                    
                    String transparencia;
                    float valor;
                    if(gradoTransparencia.getValue() > 10)
                        transparencia = "0." + gradoTransparencia.getValue() + "f";
                    else
                        transparencia = "0.0" + gradoTransparencia.getValue() + "f";
                    valor = Float.parseFloat(transparencia);
                    vi.getLienzo().getFigura().setTransparencia(valor);

                    this.repaint();    
                    
                }
                catch(IllegalArgumentException e){
                    System.err.println(e.getLocalizedMessage());
                }
                vi.getLienzo().repaint();
            }
        }
    }//GEN-LAST:event_gradoTransparenciaStateChanged

    private void duplicarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_duplicarActionPerformed
       // TODO add your handling code here:
        VentanaInterna vi = (VentanaInterna)escritorio.getSelectedFrame();
        String nombre;
        if(vi != null){
            vi.getLienzo().getFigura().setEditar(false);
            nombre = vi.getTitle();
            nuevoActionPerformed(nombre + " [Copia]", vi.getLienzo().getImage(true));
            volcado();
        } 
        else
            mensaje(11);
    }//GEN-LAST:event_duplicarActionPerformed

    private void negativoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_negativoActionPerformed
        // TODO add your handling code here:
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        
        if (vi != null) {
            vi.getLienzo().getFigura().setEditar(false);
            BufferedImage imgSource = vi.getLienzo().getImage(true);
            volcado();
            if(imgSource!=null){
                try{
                    int type = LookupTableProducer.TYPE_NEGATIVE;
                    LookupTable lt = LookupTableProducer.createLookupTable(type);
                    LookupOp lop = new LookupOp(lt, null);
                    // Imagen origen y destino iguales
                    lop.filter(imgSource , imgSource);
                    vi.repaint();
                } 
                catch(Exception e){
                    System.err.println(e.getLocalizedMessage());
                }
            } 
        }
    }//GEN-LAST:event_negativoActionPerformed

    private void curvaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_curvaActionPerformed
        // TODO add your handling code here:
        VentanaInterna vi;
        vi = (VentanaInterna)escritorio.getSelectedFrame();
        barraEstado.setText("Curva");
        if(vi != null){ 
            vi.getLienzo().setSeleccionado(4);
            vi.getLienzo().setMover(false);
            relleno.setEnabled(false);
            alisar.setEnabled(false);
            contador.setEnabled(false);
            trazo.setEnabled(false);
            colores.setEnabled(false);
            eleccionColores.setEnabled(false);
            tipoLetra1.setEnabled(false);
            
            if (vi.getLienzo().getMover()){
                vi.getLienzo().getFigura().setColorBorde((Color) colores.getSelectedItem());
                vi.getLienzo().getFigura().setContador((int) contador.getValue());
            }
            else{
                vi.getLienzo().setColorBorde((Color) colores.getSelectedItem());
                vi.getLienzo().setContador((int) contador.getValue());
            }
        }
    }//GEN-LAST:event_curvaActionPerformed

    private void textoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textoActionPerformed
        // TODO add your handling code here:
        VentanaInterna vi;
        vi = (VentanaInterna)escritorio.getSelectedFrame();
        barraEstado.setText("Texto");
        if(vi != null){ 
            vi.getLienzo().setSeleccionado(5);
            vi.getLienzo().setMover(false);
            relleno.setEnabled(false);
            alisar.setEnabled(false);
            contador.setEnabled(false);
            trazo.setEnabled(false);
            colores.setEnabled(false);
            eleccionColores.setEnabled(false);
            tipoLetra1.setEnabled(false);
            
            tipoTexto.setLocationRelativeTo(this);
            tipoTexto.setVisible(true);
            
            if (vi.getLienzo().getMover()){
                vi.getLienzo().getFigura().setColorBorde((Color) colores.getSelectedItem());
            }
            else{
                vi.getLienzo().setColorBorde((Color) colores.getSelectedItem());
            }
        }
    }//GEN-LAST:event_textoActionPerformed

    private void rellenoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rellenoActionPerformed
        // TODO add your handling code here:
        tipoRelleno.setLocationRelativeTo(this);
        tipoRelleno.setVisible(true);
    }//GEN-LAST:event_rellenoActionPerformed

    private void cancelarRellenoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarRellenoActionPerformed
        // TODO add your handling code here:
        tipoRelleno.setVisible(false);
    }//GEN-LAST:event_cancelarRellenoActionPerformed

    private void conRellenoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_conRellenoActionPerformed
        // TODO add your handling code here:
        Color c = JColorChooser.showDialog(this, "Selección color" , Color.white);
        VentanaInterna vi = (VentanaInterna)escritorio.getSelectedFrame();
                
        if(c != null && vi !=null){ 
            vi.getLienzo().getFigura().setColorFondo(c);
            vi.getLienzo().getFigura().setFill(true);
        }
        this.repaint();   
    }//GEN-LAST:event_conRellenoActionPerformed

    private void aceptarRellenoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aceptarRellenoActionPerformed
        // TODO add your handling code here:
        tipoRelleno.setVisible(false);
    }//GEN-LAST:event_aceptarRellenoActionPerformed

    private void sinRellenoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sinRellenoActionPerformed
        // TODO add your handling code here:
        VentanaInterna vi = (VentanaInterna)escritorio.getSelectedFrame();
        
        if(vi != null){
            vi.getLienzo().getFigura().setFill(false);
            tipoRelleno.setVisible(false);
            this.repaint();
        }
    }//GEN-LAST:event_sinRellenoActionPerformed

    private void degradadoVerticalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_degradadoVerticalActionPerformed
        // TODO add your handling code here:
        Color c = JColorChooser.showDialog(this, "Selección color" , Color.white);
        Color c2 = JColorChooser.showDialog(this, "Selección color" , Color.white);
        VentanaInterna vi = (VentanaInterna)escritorio.getSelectedFrame();
                
        if(c != null && vi !=null){ 
            vi.getLienzo().getFigura().setColorFondo(c);
            vi.getLienzo().getFigura().setColorFondoSecundario(c2);
            vi.getLienzo().getFigura().setDegradadoVertical(true);
        }
        this.repaint();
    }//GEN-LAST:event_degradadoVerticalActionPerformed

    private void degradadoHorizontalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_degradadoHorizontalActionPerformed
        // TODO add your handling code here:
        Color c = JColorChooser.showDialog(this, "Selección color" , Color.white);
        Color c2 = JColorChooser.showDialog(this, "Selección color" , Color.white);
        VentanaInterna vi = (VentanaInterna)escritorio.getSelectedFrame();
                
        if(c != null && vi !=null){ 
            vi.getLienzo().getFigura().setColorFondo(c);
            vi.getLienzo().getFigura().setColorFondoSecundario(c2);
            vi.getLienzo().getFigura().setDegradadoHorizontal(true);
        }
        this.repaint();
    }//GEN-LAST:event_degradadoHorizontalActionPerformed

    private void trazoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_trazoActionPerformed
        // TODO add your handling code here:
        tipoTrazo.setLocationRelativeTo(this);
        tipoTrazo.setVisible(true);
    }//GEN-LAST:event_trazoActionPerformed

    private void aceptarTrazoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aceptarTrazoActionPerformed
        // TODO add your handling code here:
        tipoTrazo.setVisible(false);
    }//GEN-LAST:event_aceptarTrazoActionPerformed

    private void cancelarTrazoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarTrazoActionPerformed
        // TODO add your handling code here:
        tipoTrazo.setVisible(false);
    }//GEN-LAST:event_cancelarTrazoActionPerformed

    private void continuoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_continuoActionPerformed
        // TODO add your handling code here:
        VentanaInterna vi = (VentanaInterna)escritorio.getSelectedFrame();
                
        if(vi !=null){ 
            vi.getLienzo().getFigura().setContinuidad(true);
        }
        this.repaint(); 
        tipoTrazo.setVisible(false);
    }//GEN-LAST:event_continuoActionPerformed

    private void discontinuoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_discontinuoActionPerformed
        // TODO add your handling code here:
        VentanaInterna vi = (VentanaInterna)escritorio.getSelectedFrame();
                
        if(vi !=null){ 
            vi.getLienzo().getFigura().setContinuidad(false);
        }
        this.repaint(); 
        tipoTrazo.setVisible(false);
    }//GEN-LAST:event_discontinuoActionPerformed

    private void daltonicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_daltonicoActionPerformed
        // TODO add your handling code here:
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        
        if (vi != null) {
            vi.getLienzo().getFigura().setEditar(false);
            BufferedImage imgSource = vi.getLienzo().getImage(true);
            volcado();
            if(imgSource!=null){
                try{
                    DaltonismoOp dop = new DaltonismoOp();
                    dop.filter(imgSource, imgSource);
                    vi.getLienzo().repaint(); 
                } 
                catch(Exception e){
                    System.err.println(e.getLocalizedMessage());
                }
            } 
        }
    }//GEN-LAST:event_daltonicoActionPerformed

    private void tipoLetraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tipoLetraActionPerformed
        // TODO add your handling code here:
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        
        if (vi != null) {
            vi.getLienzo().setTipoLetra(tipoLetra.getSelectedItem().toString());
            this.repaint();
        }
    }//GEN-LAST:event_tipoLetraActionPerformed

    private void aceptarLetraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aceptarLetraActionPerformed
        // TODO add your handling code here:
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        
        if (vi != null) {
            vi.getLienzo().escrito = escrito.getText();
        }
        tipoTexto.setVisible(false);
    }//GEN-LAST:event_aceptarLetraActionPerformed

    private void cancelarLetraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarLetraActionPerformed
        // TODO add your handling code here:
        tipoTexto.setVisible(false);
    }//GEN-LAST:event_cancelarLetraActionPerformed

    private void tamLetraStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tamLetraStateChanged
        // TODO add your handling code here:
        VentanaInterna vi;
        
        vi = (VentanaInterna)escritorio.getSelectedFrame();
        
        if(vi != null)
            vi.getLienzo().setTamLetra((int) tamLetra.getValue());

    }//GEN-LAST:event_tamLetraStateChanged

    private void negritaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_negritaActionPerformed
        // TODO add your handling code here:
        VentanaInterna vi;
        
        vi = (VentanaInterna)escritorio.getSelectedFrame();
        
        if(vi != null)
            vi.getLienzo().setNegrita(negrita.isSelected());
        repaint();
    }//GEN-LAST:event_negritaActionPerformed

    private void cursivaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cursivaActionPerformed
        // TODO add your handling code here:
        VentanaInterna vi;
        
        vi = (VentanaInterna)escritorio.getSelectedFrame();
        
        if(vi != null)
            vi.getLienzo().setCursiva(cursiva.isSelected());
        repaint();
    }//GEN-LAST:event_cursivaActionPerformed

    private void subrayadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subrayadoActionPerformed
        // TODO add your handling code here:
        VentanaInterna vi;
        
        vi = (VentanaInterna)escritorio.getSelectedFrame();
        
        if(vi != null)
            vi.getLienzo().setSubrayado(subrayado.isSelected());
        repaint();
    }//GEN-LAST:event_subrayadoActionPerformed

    private void negrita1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_negrita1ActionPerformed
        // TODO add your handling code here:
        VentanaInterna vi;
        
        vi = (VentanaInterna)escritorio.getSelectedFrame();
        
        if(vi != null)
            vi.getLienzo().getFigura().negrita = negrita1.isSelected();
        repaint();
    }//GEN-LAST:event_negrita1ActionPerformed

    private void cursiva1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cursiva1ActionPerformed
        // TODO add your handling code here:
        VentanaInterna vi;
        
        vi = (VentanaInterna)escritorio.getSelectedFrame();
        
        if(vi != null)
            vi.getLienzo().getFigura().cursiva = cursiva1.isSelected();
        repaint();
    }//GEN-LAST:event_cursiva1ActionPerformed

    private void subrayado1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subrayado1ActionPerformed
        // TODO add your handling code here:
        VentanaInterna vi;
        
        vi = (VentanaInterna)escritorio.getSelectedFrame();
        
        if(vi != null)
            vi.getLienzo().getFigura().subrayado = subrayado1.isSelected();
        repaint();
    }//GEN-LAST:event_subrayado1ActionPerformed

    private void verBarraTextoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verBarraTextoActionPerformed
        // TODO add your handling code here:
        negrita1.setVisible(verBarraTexto.isSelected());
        cursiva1.setVisible(verBarraTexto.isSelected());
        subrayado1.setVisible(verBarraTexto.isSelected());
        tipoLetra1.setVisible(verBarraTexto.isSelected());
        jSeparator2.setVisible(verBarraTexto.isSelected());
    }//GEN-LAST:event_verBarraTextoActionPerformed

    private void tipoLetra1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tipoLetra1ActionPerformed
        // TODO add your handling code here:
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        
        if (vi != null) {
            vi.getLienzo().getFigura().setTipoLetra(tipoLetra1.getSelectedItem().toString());
            this.repaint();
        }
    }//GEN-LAST:event_tipoLetra1ActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem abrir;
    private javax.swing.JButton abrir1;
    private javax.swing.JMenuItem abrirAudio;
    private javax.swing.JMenuItem abrirVideoJMF;
    private javax.swing.JMenuItem abrirVideoVLC;
    private javax.swing.JButton aceptar;
    private javax.swing.JButton aceptarLetra;
    private javax.swing.JButton aceptarRelleno;
    private javax.swing.JButton aceptarTrazo;
    private javax.swing.JMenuItem acercaDe;
    private javax.swing.JToggleButton alisar;
    private javax.swing.JTextField alto;
    private javax.swing.JLabel altoL;
    private javax.swing.JTextField ancho;
    private javax.swing.JLabel anchoL;
    private javax.swing.JButton aumentar;
    private javax.swing.JDialog ayuda;
    private javax.swing.JButton banda;
    private javax.swing.JSlider barraBrillo;
    private javax.swing.JToolBar barraEdicion;
    private javax.swing.JLabel barraEstado;
    private javax.swing.JToolBar barraFormas;
    private javax.swing.JSlider barraRotacion;
    private javax.swing.JToolBar barraSlider;
    private javax.swing.ButtonGroup botonesHerramientas;
    private javax.swing.JMenuItem camara;
    private javax.swing.JButton camara2;
    private javax.swing.JButton cancelarLetra;
    private javax.swing.JButton cancelarRelleno;
    private javax.swing.JButton cancelarTrazo;
    private javax.swing.JButton captura;
    private javax.swing.JComboBox<String> colores;
    private javax.swing.JToggleButton conRelleno;
    private javax.swing.JSpinner contador;
    private javax.swing.JToggleButton continuo;
    private javax.swing.JButton contraste;
    private javax.swing.JLabel coordenadas;
    private javax.swing.JToggleButton cursiva;
    private javax.swing.JToggleButton cursiva1;
    private javax.swing.JToggleButton curva;
    private javax.swing.JButton daltonico;
    private javax.swing.JToggleButton degradadoHorizontal;
    private javax.swing.JToggleButton degradadoVertical;
    private javax.swing.JToggleButton discontinuo;
    private javax.swing.JButton disminuir;
    private javax.swing.JButton duplicar;
    private javax.swing.JButton ecualizar;
    private javax.swing.JToggleButton editar;
    private javax.swing.JButton eleccionColores;
    private javax.swing.JToggleButton elipse;
    private javax.swing.JButton escalar;
    private javax.swing.JTextPane escrito;
    private javax.swing.JDesktopPane escritorio;
    private javax.swing.JComboBox<String> espacioColor;
    private javax.swing.JComboBox<String> filtros;
    private javax.swing.JSlider gradoTransparencia;
    private javax.swing.JMenuItem guardar;
    private javax.swing.JButton guardar1;
    private javax.swing.JMenuItem guardarAudio;
    private javax.swing.JButton guardarAudio1;
    private javax.swing.JButton iluminar;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JToggleButton linea;
    private javax.swing.JButton negativo;
    private javax.swing.JToggleButton negrita;
    private javax.swing.JToggleButton negrita1;
    private javax.swing.JMenuItem nuevaImagen;
    private javax.swing.JMenuItem nuevo;
    private javax.swing.JButton nuevo1;
    private javax.swing.JButton oscurecer;
    private javax.swing.JPanel panelEstado;
    private javax.swing.JToggleButton punto;
    private javax.swing.JToggleButton rectangulo;
    private javax.swing.JButton redimensionar;
    private javax.swing.JButton relleno;
    private javax.swing.ButtonGroup rellenos;
    private javax.swing.JToolBar.Separator separador1;
    private javax.swing.JToolBar.Separator separador2;
    private javax.swing.JToolBar.Separator separador3;
    private javax.swing.JToolBar.Separator separador4;
    private javax.swing.JToolBar.Separator separador5;
    private javax.swing.JButton sepia;
    private javax.swing.JToggleButton sinRelleno;
    private javax.swing.JButton sinusoidal;
    private javax.swing.JToggleButton subrayado;
    private javax.swing.JToggleButton subrayado1;
    private javax.swing.JDialog tamImagen;
    private javax.swing.JSpinner tamLetra;
    private javax.swing.JToggleButton texto;
    private javax.swing.JButton tintar;
    private javax.swing.JComboBox<String> tipoLetra;
    private javax.swing.JComboBox<String> tipoLetra1;
    private javax.swing.JDialog tipoRelleno;
    private javax.swing.JDialog tipoTexto;
    private javax.swing.JDialog tipoTrazo;
    private javax.swing.JButton trazo;
    private javax.swing.ButtonGroup trazos;
    private javax.swing.JSlider umbral;
    private javax.swing.JCheckBoxMenuItem verBarraAtributos;
    private javax.swing.JCheckBoxMenuItem verBarraEdicion;
    private javax.swing.JCheckBoxMenuItem verBarraEstado;
    private javax.swing.JCheckBoxMenuItem verBarraFormas;
    private javax.swing.JCheckBoxMenuItem verBarraSlider;
    private javax.swing.JCheckBoxMenuItem verBarraTexto;
    private javax.swing.JCheckBoxMenuItem verBarraVideo;
    // End of variables declaration//GEN-END:variables
}