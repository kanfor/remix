package com.kitmaker.finalkombat;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import android.util.Log;
import com.kitmaker.ads.KitmakerAdsProviderManager;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.microedition.ActivityMain;
import javax.microedition.lcdui.game.*;
import javax.microedition.lcdui.*;
import javax.microedition.media.Player;
import javax.microedition.media.Manager;
import javax.microedition.media.control.VolumeControl;
import javax.microedition.midlet.MIDlet;

import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
/**
 * @author josemariaclimentmartinez
 */
public class SuperCanvas extends Canvas implements Runnable

{

    /**
     * constructor
     */
//#if BackBuffer=="true"
     static Image ms_vCanvas;
//#endif        
    //RUN
    public static boolean ms_bFinishApp=false;
    //RMS
    static RecordStore gRecordLang = null;
    static RecordStore gRecordMemoryCard = null;
    public static String mylanguage;
    public static String myLevelCompleted;
    public static boolean noLanguage;
    public static boolean partidaGuardada = false;
    //////////////////////////////
    //Hacking de Hernan
    public static boolean godMode = false;
    public static byte godModeContador;
    //Control FPS
    public long currentTime;
    public long timeFPS;
    public static int ms_iFrame;
    public static int ms_iFrameTicks;
    public static long ms_lCurrentTime, ms_lLastFrameTime;
    public static long ms_lStartTime;
	// Defaults to 25 FPS max.
	public static int ms_iTicksPerSecond = Define.FPS; //LÃ­mite de 25fps.
	public static int ms_iMillisPerTick = 1000 / ms_iTicksPerSecond;
	public static final int MAX_FRAMETIME = 1500; // If time between frames is > this, game will pause
	public static long ms_lTickTime;
	public static int ms_iSeconds;    
        
    //Limitador inteligente de pintado.
    public int limiteScrollIzq;
    public int limiteScrollDer;
    public static boolean kanforSound = true;
    static boolean bugWaviniciado = false;
    //static Player sonidoFX;
    //static Player sonidoFX2;
    VolumeControl volumeControl;
//#if API!="Nextel" && API!="BlackBerry"  
    public static byte nivelVolumen = 50;
//#elif API=="Nextel" || API=="BlackBerry"
//#     public static byte nivelVolumen = 100;
//#endif    
    //Valores temporales
    //Estos valores se pueden coger y reutilizar. La condiciÃ³n es que cuando acabe el proceso
    //se reseteen a cero para que se puedan volver a usar.
    public int PosXtemp1 = 0;
    public int PosYtemp1 = 0;
    public int PosXtemp2 = 0;
    public int PosYtemp2 = 0;
    public int estadoTemp = 0;
    public int opcionSelect = 0;
    ////////////////////////
    public int debugKeycode;
    public int tiempo = 0;
    public Ktext ktext;
    public MidletFinalKombat midletFinalKombat;
    public boolean submenu = false; //Activa o no el submenu del menÃº.
    public static boolean mostrarAdvertencia = false;
    public static boolean gameStarted = false; //Indica que el juego ya ha sido cargado.
    public static boolean juegoIniciado = false; //Indica que la intro ya ha sido cargada.
    public static boolean partidaIniciada = false; //Indica que el rÃ³tulo de la fase ha acabado y empieza la fase.
    public static int limiteFase; //Indica el lÃ­mite de la fase. Ya no se puede avanzar mÃ¡s. Se define al comienzo de la fase.
    //Imagenes menu e intro.
    public boolean graficosYaCargados = false;
    public Image logoKitmaker;
    public Image abomb;
    public Image ifont1;
    public Sprite sfont1;
    public Image ifont2;
    public Sprite sfont2;
    public Image ifont3;
    public static Sprite sfont3;
    public Image ladrilloFondo1;
    public Image fondoMenu;
    public Image fondoCielo1;
    public Image fondoCieloTrans;
    public Image ibotones;
    public Sprite sbotones;
    public Image duke_intro;
//#if TouchScreen=="true"
     public Image tablero;
     public Image pad;
     public Image boton;
     public int posYTablero, alturaTablero;
     public int centradoPalancaX, centradoPalancaY;
     public int contadorPulsacionTactil;
     public boolean pulsacionTactil = true;
     public byte tiempoTactil = 5;
//#endif    
    //Valores para separaciones dinÃ¡micas
    public int unDecimo = Define.SCR_HEIGHT / 10;
    public int unSexto = Define.SCR_HEIGHT / 6;
    public int unTercio = Define.SCR_HEIGHT / 3;
    public int unCuarto = Define.SCR_HEIGHT >> 2;
    public int unMedio = Define.SCR_HEIGHT >> 1;
    public int mitadWidth = Define.SCR_WIDTH >> 1;
    //Valores del juego
    public boolean pause = false;
    public boolean direccionDuke = true; //Si es FALSE mira a la izquierda. Si es TRUE mira a la derecha.
    public boolean dukeMuere = false;
    public boolean menuMuerte = false;
    public boolean menuFinFase = false;
    public int tiempoMuerto=0;
    public int tiempoGolpe=0;
    public int scrollX;
    boolean pressUp, pressDown, pressLeft, pressRight, pressFight;
    public byte fotograma;
    public byte fuerzaSalto = Define.fuerzaSalto; //20; //La altura mÃ¡xima del salto.
    public byte saltoDesplazMax = Define.saltoDesplazMax; //10; //Desplazamiento mÃ¡ximo al saltar o caer.
    public boolean salto;
    public boolean colision;
    public byte numeroVidas = 3;
    public byte numeroBombas = 0;
    public int tiempoBomba = 1000;
    public int puntosFinFase = 0;
    public boolean botonMuerte;
    public boolean patadaAerea;
    public byte contadorPatada;
    //Estados de la animacion.
    int velocidadAnimacion;
    int velocidadMaxAnimacion = Define.VELOCIDADANIMACION;//Controla la velocidad de la animacion. Mayor nÃºmero, mÃ¡s lento.
    public static final byte velocidadDuke = Define.velocidadDuke;//3; //La velocidad de Duke en pÃ­xeles. MÃ¡s pÃ­xeles mÃ¡s rÃ¡pido.
    public static byte animacion;
    public static final byte CORRIENDO = 1;
    public static final byte PARADO = 2;
    public static final byte SALTANDO = 3;
    public static final byte PATADA = 4;
    public static int tiempoCombo = 0;
    public static byte COMBO = 0;
    public static boolean pintarDuke = false;
    //Imagenes comunes del juego activo.
    //Imagenes del mundo1.
    public byte[] bomba;
    Image iduke;
    Sprite duke;
    int dukePosX;
    int dukePosY;
    Image ininja1, ininja2;
    Sprite sninja1, sninja1rotado, sninja2;
    Sprite sninja1muertoDer;
    Sprite sninja1muertoIzq;
    final static byte numeroMaxNinjas = 20;
    static byte numerGolpesMatarNinja1 = 2; //Golpes necesarios para matar a los ninjas.
    public static final byte velocidadAnimancionNinja = Define.VELOCIDADANIMACIONNINJA; //5; //Velocidad de la animaciÃ³n Ninja.
    boolean direccionNinja[] = new boolean [numeroMaxNinjas]; //La direcciÃ³n a la que apunta el Ninja. El 20 es el nÃºmero de Ninjas.
    Image iDinamita;
    Image iDinamitaMarcador;
    Image iDinamitaOk;
    Sprite sDinamita;
    Sprite sDinamitaMarcador;
    byte frameDinamita;
    Image isuelo;
    Sprite ssuelo;
    Image itienda;
    Image plataformaMetalica;
    int plataformaMetalicaY1;
    int plataformaMetalicaY2;
    int bombaPosY;
    Image oscurecer;
    Image ladrillo1;
    Image cartel1;
    Image cartel2;
    Image cartel3;
    Image senefa1;
    Image ventana1;
    Image paredbaja1;
    Image fondocielo;
    Image ladrillo2;
    Image barril;
    Image vidas;
    Image policeCar;
    Image botonPausa;
    public byte fotogramasDuke = 19;
    public int alturaSuelo, anchuraSuelo, alturaTienda, anchuraTienda, alturaDuke, anchuraDuke,
            alturaCartel1, anchuraCartel1, alturaPlataforma, anchuraPlataforma, alturaFondocielo,
            anchuraFondocielo, alturaSenefa, anchuraSenefa, alturaLadrillo2, anchuraLadrillo2,
            alturaLadrillo1, anchuraLadrillo1, alturaParedbaja1, anchuraParedbaja1, alturaVentana1,
            anchuraVentana1, alturaPoliceCar, anchuraPoliceCar, anchuraDinamita, alturaDinamita,
            anchuraNinja, alturaNinja, anchuraNinja2, alturaNinja2;
    //Imagenes del mundo2.
    //Imagenes del mundo3.
    //Estados principales del juego.
    public String nombreFase;
    public static byte ESTADO = 0,
            LOGO = 1,
            MENUIDIOMAINIC = 2,
            MENUSONIDOINIC = 3,
            INTROMENU = 4,
            MENU = 5,
            SUBMENU = 6,
            INSTRUCCIONES = 7,
            ABOUT = 8,
            SALIR = 9,
            CONTINUAR = 10,
            NEWGAME = 11,
            MENUANIMADO = 12,
            GAMEOVER = 13,
            JUGAR = 69,
            FASEAJUGAR = 20, //Este valor cambia segÃºn la fase a jugar. Por defecta vale igual que FASE11.
            FASE11 = 20,
            FASE12 = 21,
            FASE13 = 22,
            FASE21 = 23,
            FASE22 = 24,
            FASE23 = 25,
            FASE31 = 26,
            FASE32 = 27,
            FASE33 = 28,
            FINAL = 29;
           
    //Ninjas purpuras
    public int desplazamientoNinja;
    public int avanceNinja=2;
    public int ninja1PoxY;
    public static byte[] Ninja1Vida = new byte [numeroMaxNinjas];
    public int ninja2PoxY;
    public int ninja2Margen; //Es el margen para colocar correctamente al Ninjea2 en el borde la plataforma
    public byte frameNinja;
    public byte frameNinjaFinal[] = new byte [numeroMaxNinjas]; //numeroMaxNinjas es el nÃºmero mÃ¡ximo de Ninjas
    public static int[] ninjaPosXeditable = new int[numeroMaxNinjas]; //numeroMaxNinjas es el nÃºmero mÃ¡ximo de Ninjas
    public static int[] ninjaPosXeditable2 = new int[200]; //200 es el mÃ¡ximo de la matriz
    
    public int [] Ninja2Delta = new int [200]; //Los pixeles que avanza el Ninja2 en su recorrido.
    public int [] Ninja2Tiempo = new int [200]; //Controla los tiempos de pausa entre estados.
    public int [] Ninja2Frame = new int [200]; //Frames del ninja2
    public byte ESTADONINJA2;
    public byte NINJA2PARADODERECHA = 0,
                NINJA2CORRIENDOIZQUIERDA = 1,
                NINJA2PARADOIZQUIERDA = 2,
                NINJA2CORRIENDODERECHA = 3;
    //Matrices de las fases
    public static int[] mapaLadrillo;
    public static int[] mapaTiendas;
    public static int[] mapaVentanas;
    public static int[] mapaSenefas;
    public static int[] mapaMetalicas1;
    public static int[] mapaMetalicas2;
    public static int[] Bomba;
    public static int[] BombaReset;
    public static int[] NinjaPosX;
    public static int[] Ninja2PosX;
    
//KEYCODES//    
//#if API=="Nokia" && !s60dp3e61    
     public static final int KEYCODE_UP = -1;
     public static final int KEYCODE_DOWN = -2;
     public static final int KEYCODE_LEFT = -3;
     public static final int KEYCODE_RIGHT = -4;
     public static final int KEYCODE_FIRE = -5;
     public static final int KEYCODE_NUM2 = 50;
     public static final int KEYCODE_NUM8 = 56;
     public static final int KEYCODE_NUM4 = 52;
     public static final int KEYCODE_NUM6 = 54;
     public static final int KEYCODE_NUM5 = 53;
     public static final int KEYCODE_SK_LEFT = -6;
     public static final int KEYCODE_SK_RIGHT = -7;
     public static final int KEYCODE_CLEAR = -8;
//#elif s60dp3e61
//# public static final int KEYCODE_UP  = -1;
//# public static final int KEYCODE_DOWN  = -2;
//# public static final int KEYCODE_LEFT  = -3;
//# public static final int KEYCODE_RIGHT  = -4;
//# public static final int KEYCODE_FIRE  = -5;
//# public static final int KEYCODE_NUM2  = 116;
//# public static final int KEYCODE_NUM8  = 98;
//# public static final int KEYCODE_NUM4  = 102;
//# public static final int KEYCODE_NUM6  = 104;
//# public static final int KEYCODE_NUM5  = 103;
//# 
//#     public static final int KEYCODE_SK_LEFT = -6;
//#     public static final int KEYCODE_SK_RIGHT = -7;
//#     public static final int KEYCODE_CLEAR = -8;
//#elif API=="Nextel"
//# public static final int KEYCODE_UP  = -10;
//# public static final int KEYCODE_DOWN  = -11;
//# public static final int KEYCODE_LEFT  = -13;
//# public static final int KEYCODE_RIGHT  = -12;
//# public static final int KEYCODE_FIRE  = -23;
//#  public static final int KEYCODE_NUM2  = 50;
//#  public static final int KEYCODE_NUM8  = 56;
//#  public static final int KEYCODE_NUM4  = 52;
//#  public static final int KEYCODE_NUM6  = 54;
//#  public static final int KEYCODE_NUM5  = 53;
//# 
//# public static final int KEYCODE_NUM1  = 49;
//# public static final int KEYCODE_NUM3  = 51;
//# public static final int KEYCODE_NUM7  = 55;
//# public static final int KEYCODE_NUM9  = 57;
//# public static final int KEYCODE_NUM0  = 48;
//# public static final int KEYCODE_ASTERISCO  = 42;
//# public static final int KEYCODE_ALMOHADILLA  = 35;
//# 
//#     public static final int KEYCODE_SK_LEFT = -20;
//#     public static final int KEYCODE_SK_RIGHT = -21;
//#     public static final int KEYCODE_CLEAR = -8;
    //#elif kg800
//# public static final int KEYCODE_UP  = -1;
//# public static final int KEYCODE_DOWN  = -2;
//# public static final int KEYCODE_LEFT  = -3;
//# public static final int KEYCODE_RIGHT  = -4;
//# public static final int KEYCODE_FIRE  = -5;
//# public static final int KEYCODE_NUM2  = 50;
//# public static final int KEYCODE_NUM8  = 56;
//# public static final int KEYCODE_NUM4  = 52;
//# public static final int KEYCODE_NUM6  = 54;
//# public static final int KEYCODE_NUM5  = 53;
//#     
//#       public static final int KEYCODE_SK_LEFT  = -202;
//#       public static final int KEYCODE_SK_RIGHT = -203;
//#       public static final int KEYCODE_CLEAR = -8;
    //#elif API=="BlackBerry"
//# public static final int KEYCODE_UP  = 1;
//# public static final int KEYCODE_DOWN  = 6;
//# public static final int KEYCODE_LEFT  = 2;
//# public static final int KEYCODE_RIGHT  = 5;
//# public static final int KEYCODE_FIRE  = -8;
//# public static final int KEYCODE_NUM2  = 101;
//# public static final int KEYCODE_NUM8  = 120;
//# public static final int KEYCODE_NUM4  = 115;
//# public static final int KEYCODE_NUM6  = 102;
//# public static final int KEYCODE_NUM5  = 100;
//# 
//# public static final int KEYCODE_NUM1  = 49;
//# public static final int KEYCODE_NUM3  = 51;
//# public static final int KEYCODE_NUM7  = 55;
//# public static final int KEYCODE_NUM9  = 57;
//# public static final int KEYCODE_NUM0  = 48;
//# public static final int KEYCODE_ASTERISCO  = 42;
//# public static final int KEYCODE_ALMOHADILLA  = 35;
//# 
//#     public static final int KEYCODE_SK_LEFT = 1114112;
//#     public static final int KEYCODE_SK_RIGHT = 1179648;
//#     //public static final int KEYCODE_CLEAR = -8;
//#     public static final int KEYCODE_SK_MENU  = 268566528;
//#     public static final int KEYCODE_SK_BACK  = 1769472;    
//#     
//# //QWERTY
//# public static final int KEYCODE_Q  = 113;  
//# public static final int KEYCODE_W  = 119; 
//# public static final int KEYCODE_E  = 101;
//# public static final int KEYCODE_R  = 114;
//# public static final int KEYCODE_T  = 116;
//# public static final int KEYCODE_Y  = 121;
//# public static final int KEYCODE_U  = 117;
//# public static final int KEYCODE_I  = 105;
//# public static final int KEYCODE_O  = 111;
//# public static final int KEYCODE_P  = 112;
//# public static final int KEYCODE_A  = 97;
//# public static final int KEYCODE_S  = 115;
//# public static final int KEYCODE_D  = 100;
//# public static final int KEYCODE_F  = 102;
//# public static final int KEYCODE_G  = 103;
//# public static final int KEYCODE_H  = 104;
//# public static final int KEYCODE_J  = 106;
//# public static final int KEYCODE_K  = 107;
//# public static final int KEYCODE_L  = 108;
//# public static final int KEYCODE_BORRAR  = 8;
//# public static final int KEYCODE_Z  = 122;
//# public static final int KEYCODE_X  = 120; 
//# public static final int KEYCODE_C  = 99;
//# public static final int KEYCODE_V  = 118;
//# public static final int KEYCODE_B  = 98;
//# public static final int KEYCODE_N  = 110;
//# public static final int KEYCODE_M  = 109;
//# public static final int KEYCODE_COMA  = 44;
//# public static final int KEYCODE_PUNTO  = 46;
//# public static final int KEYCODE_ENTER  = 10;    
    //#elif API=="Motorola"    
//# public static final int KEYCODE_UP  = -1;
//# public static final int KEYCODE_DOWN  = -6;
//# public static final int KEYCODE_LEFT  = -2;
//# public static final int KEYCODE_RIGHT  = -5;
//# public static final int KEYCODE_FIRE  = -20;
//# public static final int KEYCODE_NUM2  = 50;
//# public static final int KEYCODE_NUM8  = 56;
//# public static final int KEYCODE_NUM4  = 52;
//# public static final int KEYCODE_NUM6  = 54;
//# public static final int KEYCODE_NUM5  = 53;
//#     
//#       public static final int KEYCODE_SK_LEFT  = -21;
//#       public static final int KEYCODE_SK_RIGHT = -22;
//#       public static final int KEYCODE_CLEAR = -8;     
    //#endif

////////////    
    public SuperCanvas() {
        Log.d("debug", "SuperCanvas() - ini");
        setFullScreenMode(true);
        //#if BackBuffer=="true"
         ms_vCanvas = Image.createImage(Define.SCR_WIDTH, Define.SCR_HEIGHT);
        //#endif
         Log.d("debug", "SuperCanvas() - end");
    }

    public void run() {
        TouchScreenCheck();
        ESTADO = LOGO;
        SndManager.InitSndManager(MIDlet.ms_Activity);//ActivityMain.ms_Context);
        //#if API == "BlackBerry"
//#         Application.getApplication().addKeyListener(this);
//#         Application.getApplication().addTrackwheelListener(this);
        //#endif
        while (!ms_bFinishApp) {
        	if (kanforSound==true) {SndManager.ms_bSound=true;} else {SndManager.ms_bSound=false;}
//#if TouchScreen=="true"
             if (contadorPulsacionTactil<=tiempoTactil && pulsacionTactil==false) {
                 contadorPulsacionTactil++;
             }
             if (contadorPulsacionTactil==tiempoTactil) {
                 pulsacionTactil=true;
             }
//#endif       
            controlFPS(); 
            repaint();
        } 
        
    }
    /**
     * paint
     */
    //El siguiente mÃ©todo se encarga de cargar los grÃ¡ficos de una manera dinÃ¡mica.
    //Es importante poner "cargarGraficos();" al principio de cada paint, como "menu(Graphics g)".
    //La carga sÃ³lo se harÃ¡ cuando "gaficosCargados" sea true, lo cual sÃ³lo sucede despuÃ©s de un borrado
    //de grÃ¡ficos. Es por ello que es importante poner un "borrarGraficos();" justo despuÃ©s de un cambio
    //de ESTADO que implique un nuevo bloque de grÃ¡ficos.
    public void cargarGraficos() {

        if (graficosYaCargados == false) {    //Esto hace que solo se cargue una vez los grÃ¡ficos.

            if (ESTADO == LOGO) {
                try {
                    logoKitmaker = Image.createImage("/logo.png");
                } catch (Exception e) {
                }
                gameStarted = true;
                graficosYaCargados = true;  //Impide la carga en bucle de grÃ¡ficos.
                iniciarLenguaje(); //Cargamos el idioma.
                
            //Inicio del limitadorFPS.
            //EstÃ¡ aquÃ­ porque es la primera carga de contenidos.
            //Una vez asignados los valores no es necesario recargarlo.    
            ms_iFrameTicks = 1;
            ms_lLastFrameTime = System.currentTimeMillis();
            }
            if (ESTADO == MENUSONIDOINIC || ESTADO == MENUIDIOMAINIC) {
            try {
                    ifont1 = Image.createImage("/font1.png");
                    sfont1 = new Sprite(ifont1, ifont1.getWidth() / 10, ifont1.getHeight() / 4);
                    ifont2 = Image.createImage("/font2.png");
                    sfont2 = new Sprite(ifont2, ifont2.getWidth() / 10, ifont2.getHeight() / 5);
                    ifont3 = Image.createImage("/font3.png");
                    sfont3 = new Sprite(ifont3, ifont3.getWidth() / 10, ifont3.getHeight() / 5);
                    ladrilloFondo1 = Image.createImage("/fondo.png");
                    ibotones = Image.createImage("/botonesanimados.png");
                    sbotones = new Sprite(ibotones, ibotones.getWidth(), ibotones.getHeight());
                    boton = Image.createImage("/boton.png");
                } catch (Exception e) {
                }
                graficosYaCargados = true; //Impide la carga en bucle de grÃ¡ficos.    
            }
            if (ESTADO == MENUANIMADO || ESTADO == FINAL) {
                try {
                    if (ESTADO == MENUANIMADO) {
                    //cancionIntromenu = Manager.createPlayer(getClass().getResourceAsStream("/intromenu.mid"), "audio/midi");
                    } else {
                    //cancionIntromenu = Manager.createPlayer(getClass().getResourceAsStream("/ending.mid"), "audio/midi");    
                    	SndManager.PlayMusic(SndManager.MUSIC_ENDING, true);
                    }
                    //comenzarMusica(cancionIntromenu, true); //Comienza la canciÃ³n.

                    ifont1 = Image.createImage("/font1.png");
                    sfont1 = new Sprite(ifont1, ifont1.getWidth() / 10, ifont1.getHeight() / 4);
                    ifont2 = Image.createImage("/font2.png");
                    sfont2 = new Sprite(ifont2, ifont2.getWidth() / 10, ifont2.getHeight() / 5);
                    ifont3 = Image.createImage("/font3.png");
                    sfont3 = new Sprite(ifont3, ifont3.getWidth() / 10, ifont3.getHeight() / 5);
                    fondoCielo1 = Image.createImage("/fondocielo.png");
                    fondoCieloTrans = Image.createImage("/fondocielotrans.png");
                    duke_intro = Image.createImage("/duke_intro.png");
                } catch (Exception e) { System.out.println("Error en imagenes del Menuanimado");
                }
                graficosYaCargados = true; //Impide la carga en bucle de grÃ¡ficos.
            }
            if (ESTADO == MENU) {
                try {
                    //sonidoFX2 = Manager.createPlayer(getClass().getResourceAsStream("/ding.wav"), "audio/wav");
//#if FXWAV                                     
                    //fxselect = Manager.createPlayer(getClass().getResourceAsStream("/fxselect.wav"), "audio/wav");        
//#endif                    
                	System.out.println("Cargando Imagenes");
                    ifont1 = Image.createImage("/font1.png");
                    sfont1 = new Sprite(ifont1, ifont1.getWidth() / 10, ifont1.getHeight() / 4);
                    ifont2 = Image.createImage("/font2.png");
                    sfont2 = new Sprite(ifont2, ifont2.getWidth() / 10, ifont2.getHeight() / 5);
                    ifont3 = Image.createImage("/font3.png");
                    sfont3 = new Sprite(ifont3, ifont3.getWidth() / 10, ifont3.getHeight() / 5);
                    ladrilloFondo1 = Image.createImage("/fondo.png");
                    ibotones = Image.createImage("/botonesanimados.png");
                    sbotones = new Sprite(ibotones, ibotones.getWidth(), ibotones.getHeight());
                    boton = Image.createImage("/boton.png");
                    fondoMenu = Image.createImage("/fondomenu.png");
                } catch (Exception e) {
                }
                graficosYaCargados = true; //Impide la carga en bucle de grÃ¡ficos.
            }
            if (ESTADO == JUGAR && partidaIniciada == false) { //RÃ³tulo del comienzo de la fase.
                try {
                    ifont1 = Image.createImage("/font1.png");
                    sfont1 = new Sprite(ifont1, ifont1.getWidth() / 10, ifont1.getHeight() / 4);
                    ifont2 = Image.createImage("/font2.png");
                    sfont2 = new Sprite(ifont2, ifont2.getWidth() / 10, ifont2.getHeight() / 5);
                    ifont3 = Image.createImage("/font3.png");
                    sfont3 = new Sprite(ifont3, ifont3.getWidth() / 10, ifont3.getHeight() / 5);
                    ladrilloFondo1 = Image.createImage("/fondo.png");
                    //Reseteo de la posiciÃ³n de los Ninjas con Espada.
                    for (int i=0;i<Ninja2Delta.length;i++) {
                    Ninja2Delta[i]=0;
                    }
                } catch (Exception e) { System.out.println("Error cargando graficos");
                }
                graficosYaCargados = true; //Impide la carga en bucle de grÃ¡ficos.
            }
            if (ESTADO == GAMEOVER) { //El Game Over con la bomba de fondo.
                try {
                    ifont1 = Image.createImage("/font1.png");
                    sfont1 = new Sprite(ifont1, ifont1.getWidth() / 10, ifont1.getHeight() / 4);
                    ifont2 = Image.createImage("/font2.png");
                    sfont2 = new Sprite(ifont2, ifont2.getWidth() / 10, ifont2.getHeight() / 5);
                    ifont3 = Image.createImage("/font3.png");
                    sfont3 = new Sprite(ifont3, ifont3.getWidth() / 10, ifont3.getHeight() / 5);
                    abomb = Image.createImage("/abomb.png");
                    
                } catch (Exception e) {}
            }
            if (ESTADO == JUGAR && partidaIniciada ) { //Graficos del juego.
                try {
                
                     tablero = Image.createImage("/tablero.png");
                     pad = Image.createImage("/palanca.png");
                     centradoPalancaX = 0;
                     centradoPalancaY = 0;
                     posYTablero = Define.SCR_HEIGHT - tablero.getHeight();
                     alturaTablero = tablero.getHeight();

                    ladrilloFondo1 = Image.createImage("/fondo.png");
                    ibotones = Image.createImage("/botonesanimados.png");
                    sbotones = new Sprite(ibotones, ibotones.getWidth(), ibotones.getHeight());
                    oscurecer = Image.createImage("/oscurecer.png");
                    ifont1 = Image.createImage("/font1.png");
                    sfont1 = new Sprite(ifont1, ifont1.getWidth() / 10, ifont1.getHeight() / 4);
                    ifont2 = Image.createImage("/font2.png");
                    sfont2 = new Sprite(ifont2, ifont2.getWidth() / 10, ifont2.getHeight() / 5);
                    ifont3 = Image.createImage("/font3.png");
                    sfont3 = new Sprite(ifont3, ifont3.getWidth() / 10, ifont3.getHeight() / 5);
                    iduke = Image.createImage("/duke.png");
                    duke = new Sprite(iduke, iduke.getWidth() / fotogramasDuke, iduke.getHeight());
                    isuelo = Image.createImage("/suelo.png");
                    ssuelo = new Sprite(isuelo, isuelo.getWidth(), isuelo.getHeight());
                    itienda = Image.createImage("/tienda1.png");
                    cartel1 = Image.createImage("/cartel1.png");
                    cartel2 = Image.createImage("/cartel2.png");
                    cartel3 = Image.createImage("/cartel3.png");
                    senefa1 = Image.createImage("/senefa1.png");
                    ventana1 = Image.createImage("/ventana1.png");
                    paredbaja1 = Image.createImage("/paredbaja1.png");
                    botonPausa = Image.createImage("/pausa.png");
                    //Cambio de grÃ¡ficos segÃºn la fase.
                    switch (FASEAJUGAR) {
                        case 20:
                        case 21:
                        case 22:
                            ininja1 = Image.createImage("/ninja1.png");
                            fondocielo = Image.createImage("/fondocielo2.png");
                            ladrillo1 = Image.createImage("/ladrillo1.png");
                            break;
                        case 23:
                        case 24:
                        case 25:
                            ininja1 = Image.createImage("/ninja3.png");
                            fondocielo = Image.createImage("/fondocielo.png");
                            ladrillo1 = Image.createImage("/ladrillo1.png");
                            break;
                        case 26:
                        case 27:
                        case 28:
                            ininja1 = Image.createImage("/ninja1.png");
                            fondocielo = Image.createImage("/fondocielo3.png");
                            ladrillo1 = Image.createImage("/ladrillo3.png");
                            break;
                    }
                    ininja2 = Image.createImage("/ninja2.png");
                    sninja1 = new Sprite(ininja1, ininja1.getWidth()/10, ininja1.getHeight());
                    sninja1.defineReferencePixel(sninja1.getWidth() >> 1, sninja1.getHeight() << 1); //Ponemos el Hot Spot en el centro del Ninja.
                    sninja1rotado = new Sprite(ininja1, ininja1.getWidth()/10, ininja1.getHeight());
                    sninja1rotado.defineReferencePixel(sninja1rotado.getWidth() >> 1, sninja1rotado.getHeight() << 1); //Ponemos el Hot Spot en el centro del Ninja.
                    sninja1rotado.setTransform(Sprite.TRANS_MIRROR);
                    sninja2 = new Sprite(ininja2, ininja2.getWidth()/6, ininja2.getHeight());
                    sninja2.defineReferencePixel(sninja2.getWidth() >> 1, sninja2.getHeight() << 1); //Ponemos el Hot Spot en el centro del Ninja.
                    sninja1muertoDer = new Sprite(ininja1, ininja1.getWidth()/10, ininja1.getHeight());
                    sninja1muertoDer.defineReferencePixel(sninja1muertoDer.getWidth() >> 1, sninja1muertoDer.getHeight() << 1); //Ponemos el Hot Spot en el centro del Ninja.
                    sninja1muertoIzq = new Sprite(ininja1, ininja1.getWidth()/10, ininja1.getHeight());
                    sninja1muertoIzq.defineReferencePixel(sninja1muertoIzq.getWidth() >> 1, sninja1muertoIzq.getHeight() << 1); //Ponemos el Hot Spot en el centro del Ninja.
                    ladrillo2 = Image.createImage("/ladrillo2.png");
                    barril = Image.createImage("/barril.png");
                    plataformaMetalica = Image.createImage("/plataformametalica.png");
                    policeCar = Image.createImage("/policecar.png");
                    iDinamita = Image.createImage("/dinamita.png");
                    iDinamitaOk = Image.createImage("/dinamitaok.png");

                    sDinamita = new Sprite(iDinamita, iDinamita.getWidth()>>1, iDinamita.getHeight());

                    iDinamitaMarcador = Image.createImage("/bombas.png");
                    sDinamitaMarcador = new Sprite(iDinamitaMarcador, iDinamitaMarcador.getWidth()>>1, iDinamitaMarcador.getHeight());

                    dukePosX = mitadWidth - (duke.getWidth() >> 1);
                    dukePosY = Define.SCR_HEIGHT - iduke.getHeight() - isuelo.getHeight();
                    duke.defineReferencePixel(duke.getWidth() >> 1, duke.getHeight() << 1);     //Ponemos el Hot Spot en el centro de Duke.
                    duke.defineCollisionRectangle(0, 0, duke.getWidth(), iduke.getHeight() + 1); //Zona sensible de Duke. MÃ¡s alto para levantar a Duke.
                    animacion = PARADO;

                    vidas = Image.createImage("/life.png");

                    plataformaMetalicaY1 = Define.SCR_HEIGHT - isuelo.getHeight() - itienda.getHeight() - cartel1.getHeight()
                            - Define.correctorAlturaPrimeraPlataforma
                            + plataformaMetalica.getHeight();
/////////////////////////////////////////////            
                    plataformaMetalicaY2 = Define.alturaSegundaPlataformaMetalica;//120;  //CAMBIAR
////////////////////////////////////////////            
                    //Variables constantes con valores de grÃ¡ficos. Esto ahorra procesamiento de la CPU. Solo lo calcula una vez.
                    alturaSuelo = isuelo.getHeight()
//#if TouchScreen=="true"
                             + tablero.getHeight()
//#endif                            
                            ;
                    anchuraSuelo = isuelo.getWidth();
                    alturaTienda = itienda.getHeight();
                    anchuraTienda = itienda.getWidth();
                    alturaDuke = iduke.getHeight();
                    anchuraDuke = iduke.getWidth() / fotogramasDuke;
                    alturaCartel1 = cartel1.getHeight();
                    anchuraCartel1 = cartel1.getWidth();
                    alturaPlataforma = plataformaMetalica.getHeight();
                    anchuraPlataforma = plataformaMetalica.getWidth();
                    alturaFondocielo = fondocielo.getHeight();
                    anchuraFondocielo = fondocielo.getWidth();
                    alturaSenefa = senefa1.getHeight();
                    anchuraSenefa = senefa1.getWidth();
                    alturaLadrillo2 = ladrillo2.getHeight();
                    anchuraLadrillo2 = ladrillo2.getWidth();
                    alturaLadrillo1 = ladrillo1.getHeight();
                    anchuraLadrillo1 = ladrillo1.getWidth();
                    alturaParedbaja1 = paredbaja1.getHeight();
                    anchuraParedbaja1 = paredbaja1.getWidth();
                    alturaVentana1 = ventana1.getHeight();
                    anchuraVentana1 = ventana1.getWidth();
                    alturaPoliceCar = policeCar.getHeight();
                    anchuraPoliceCar = policeCar.getWidth();
                    alturaDinamita = iDinamita.getHeight();
                    anchuraDinamita = iDinamita.getWidth() / 2;
                    anchuraNinja = ininja1.getWidth() / 9;
                    alturaNinja = ininja1.getHeight();
                    anchuraNinja2 = ininja2.getWidth() / 5;
                    alturaNinja2 = ininja2.getHeight();
                    
                    ninja1PoxY = Define.SCR_HEIGHT-alturaSuelo-alturaNinja;
                    ninja2PoxY = plataformaMetalicaY1-alturaNinja2;
                    ninja2Margen = anchuraDuke/3;
                    
                    //Matrices de las fases
                    matricesFases();
                    KitmakerAdsProviderManager.getManager().displayInterstitial();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                graficosYaCargados = true; //Impide la carga en bucle de grÃ¡ficos.
            }

        }
    }

    public void borrarGraficos() {

        logoKitmaker = null;
        abomb = null;
        //ifont1 = null;
        //sfont1 = null;
        //ifont2 = null;
        //sfont2 = null;
        //ifont3 = null;
        //sfont3 = null;
        iduke = null;
        duke = null;
        isuelo = null;
        ssuelo = null;
        itienda = null;
        cartel1 = null;
        cartel2 = null;
        cartel3 = null;
        senefa1 = null;
        ventana1 = null;
        paredbaja1 = null;
        ininja1 = null;
        fondocielo = null;
        ladrillo1 = null;
        ininja2 = null;
        sninja1 = null;
        sninja2 = null;
        ladrillo2 = null;
        policeCar = null;
        ladrilloFondo1 = null;
        //fondoMenu = null;
        //fondoCielo1 = null;
        fondoCieloTrans = null;
        duke_intro = null;
        //ibotones = null;
        //sbotones = null;
        opcionSelect=0;    
        //Reseteo de valores temporales.
        PosXtemp1 = 0;
        PosYtemp1 = 0;
        PosXtemp2 = 0;
        PosYtemp2 = 0;
        estadoTemp = 0;
        direccionDuke = true;
        salto=false;
        colision=false;
        puntosFinFase=0;
        //Valores referentes a Duke
        scrollX=0;
        fotograma=1;
        animacion = PARADO;
        dukeMuere=false;
        limiteScrollIzq=0;
        limiteScrollDer=0;
        pressFight=false;
        pressRight=false;
        pressLeft=false;
        pressDown=false;
        pressUp=false;
        pause=false;
        menuMuerte=false;
        pintarDuke=false;
        //////////////////////////
        //Reseteo de los frames de los Ninjas
        for (int i=0;i<numeroMaxNinjas;i++) {
            frameNinjaFinal[i]=0;
            Ninja1Vida[i]=0;
        }
        for (int i=0;i<Ninja2Frame.length;i++) {
        Ninja2Frame[i]=0; 
        }
        ESTADONINJA2 = NINJA2PARADODERECHA;
        
        bugWaviniciado = false;
        
        graficosYaCargados = false; //Prepara la siguiente carga de grÃ¡ficos.
        
        System.gc(); //Mandamos la basura a tomar por saco.
        System.out.println("Imagenes borradas");
    }

    public void paint(Graphics _g) {
        Graphics g = _g; //La G de Graphics g
// hack to keep portraid mode in devices with auto portraid-landscape mode
//#if BackBuffer=="true"
         g = SuperCanvas.ms_vCanvas.getGraphics(); //Cambia el Logica por el nombre de la clase
//#endif    


        if (ESTADO == LOGO) {
            logo(g);
            pausa(MENUIDIOMAINIC, 2000); //El siguiente estado y el tiempo de espera.
            KitmakerAdsProviderManager.getManager().displayInterstitial();
        } else if (ESTADO == MENUIDIOMAINIC) {
            if (noLanguage==true) {
            menuIdiomaInic(g);
            } else {
                   ESTADO=MENUSONIDOINIC;
            }
        } else if (ESTADO == MENUSONIDOINIC) {
            menuSonidoInic(g);    
        } else if (ESTADO == MENUANIMADO) {
            menuAnimado(g);
        } else if (ESTADO == MENU) {
            if (juegoIniciado==false) {
                borrarGraficos();
                cargarGraficos();
                KitmakerAdsProviderManager.getManager().addBanners();
//                KitmakerAdsProviderManager.getManager().displayInterstitial();
            }
            juegoIniciado = true;
            if (mostrarAdvertencia==false) {
            menu(g);
            } else if (mostrarAdvertencia==true) {
            menuAdvertencia(g);    
            }
        } else if (ESTADO == ABOUT) {
            about(g);
            if (godMode==true) {
                godMode=true;
                g.setColor (255,0,0);
                g.fillRect(0, 0, Define.SCR_WIDTH, Define.SCR_HEIGHT);
                Ktext.drawText(g, "god_mode", sfont2, mitadWidth, Define.SCR_HEIGHT>>1);
            	}
        } else if (ESTADO == INSTRUCCIONES) {
            instrucciones(g);
        } else if (ESTADO == JUGAR) {
            if (pause == false) {
                newGame(g); //El control del pintado de todas las fases se hace dentro de este mÃ©todo.
            }
            if (pause == true && menuMuerte==false) { //Pintado del menÃº de pausa
                menuPausa(g);
            }
            if (menuFinFase==true) { //Pintado del fin de la fase
                finFase(g);
            }
            if (menuMuerte==true) { //inicio del reloj para que responga el keypress en el menu muerte.
                tiempoMuerto++;
            }
        }
        else if (ESTADO == GAMEOVER) {
            tiempoMuerto++;
            gameover(g);
        }
        else if (ESTADO == FINAL) {
            finalAnimado(g);
        }
        
         if (getWidth() > getHeight()) {
             _g.drawRegion(ms_vCanvas, 0, 0, Define.SCR_WIDTH, Define.SCR_HEIGHT, javax.microedition.lcdui.game.Sprite.TRANS_ROT270, 0, 0, 0);
         } else {
             _g.drawRegion(ms_vCanvas, 0, 0, Define.SCR_WIDTH, Define.SCR_HEIGHT, 0, 0, 0, 0);
         }

        
//Fran Tactil System
if (pulsacionTactil == true) {         
        //Menu Idioma
        if (ESTADO == MENUIDIOMAINIC) { 
        	if (GameScreenTouched(0, Define.margenYmenuPrin + unDecimo - (unDecimo>>2), Define.SCR_WIDTH, Define.margenYmenuPrin + (unDecimo*2), true, false)) {
        		destroyLanguage(); //Borra la partida anterior. No se pueden editar.
                iniciarLenguaje(); //Crea una nueva vacía.
                writeLanguage("ingles");  //Crossword es el nombre de la Class.
                Define.Language=0;                          //Es ahora cuando se reselecciona el idioma.
                borrarGraficos();
                ESTADO=MENUSONIDOINIC;
                contadorPulsacionTactil = 0;
                pulsacionTactil = false;
                return;
        	}
        	if (GameScreenTouched(0, Define.margenYmenuPrin + (unDecimo*3) - (unDecimo>>2), Define.SCR_WIDTH, Define.margenYmenuPrin + (unDecimo*4), true, false)) {
        		destroyLanguage(); //Borra la partida anterior. No se pueden editar.
                iniciarLenguaje(); //Crea una nueva vacía.
                writeLanguage("espanol");  //Crossword es el nombre de la Class.
                Define.Language=1;                          //Es ahora cuando se reselecciona el idioma.
                borrarGraficos();
                ESTADO=MENUSONIDOINIC;
                contadorPulsacionTactil = 0;
                pulsacionTactil = false;
                return;
        	}
        	if (GameScreenTouched(0, Define.margenYmenuPrin + (unDecimo*5) - (unDecimo>>2), Define.SCR_WIDTH, Define.margenYmenuPrin + (unDecimo*6), true, false)) {
        		destroyLanguage(); //Borra la partida anterior. No se pueden editar.
                iniciarLenguaje(); //Crea una nueva vacía.
                writeLanguage("brasil");  //Crossword es el nombre de la Class.
                Define.Language=2;                          //Es ahora cuando se reselecciona el idioma.
                borrarGraficos();
                ESTADO=MENUSONIDOINIC;
                contadorPulsacionTactil = 0;
                pulsacionTactil = false;
                return;
        	}
        }
        //Menu Sonido
        if (ESTADO == MENUSONIDOINIC) {
        	if (GameScreenTouched(0, Define.margenYmenuPrin + unDecimo - (unDecimo>>2), Define.SCR_WIDTH, Define.margenYmenuPrin + (unDecimo*2), true, false)) {
        		kanforSound=false;
                borrarGraficos();
                ESTADO=MENUANIMADO;
                //Iniciamos carga del lenguaje.
                iniciarLenguaje();
                iniciarMemoryCard();
                //Iniciamos carga de la partida.
                iniciarMemoryCard();
                contadorPulsacionTactil = 0;
                pulsacionTactil = false;
                return;
        	}
        	if (GameScreenTouched(0, Define.margenYmenuPrin + (unDecimo*3) - (unDecimo>>2), Define.SCR_WIDTH, Define.margenYmenuPrin + (unDecimo*4), true, false)) {
        		kanforSound=true;
                borrarGraficos();
                ESTADO=MENUANIMADO;
                //Iniciamos carga del lenguaje.
                iniciarLenguaje();
                iniciarMemoryCard();
                //Iniciamos carga de la partida.
                iniciarMemoryCard();
                contadorPulsacionTactil = 0;
                pulsacionTactil = false;
                SndManager.PlayMusic(SndManager.MUSIC_MAINMENU, false);
                return;
        	}
        }
        //Menu Pausa
        
        if (pause == true && menuMuerte==false && menuFinFase==false) {
        if (GameScreenTouched(0, Define.margenYmenuPrin + unDecimo - (unDecimo>>2), Define.SCR_WIDTH, Define.margenYmenuPrin + (unDecimo*2), true, false)) {
        	opcionSelect=0;
            pause=false;
            pressDown=false;
            pressUp=false;
            pressLeft=false;
            pressRight=false;
            //cargarCanciones(cancionPlaying); //Forzar recarga de las canciones.
            //comenzarMusica(cancionPlaying, true);
            if (FASEAJUGAR == FASE11) {
            	SndManager.PlayMusic(SndManager.MUSIC_GAMEPLAY1, true);
            } else
            if (FASEAJUGAR == FASE12)  {
            	SndManager.PlayMusic(SndManager.MUSIC_GAMEPLAY1, true);
            } else
            if (FASEAJUGAR == FASE13)  {
            	SndManager.PlayMusic(SndManager.MUSIC_GAMEPLAY1, true);
            }
            if (FASEAJUGAR == FASE21)  {
            	SndManager.PlayMusic(SndManager.MUSIC_GAMEPLAY2, true);
            }
            if (FASEAJUGAR == FASE22)  {
            	SndManager.PlayMusic(SndManager.MUSIC_GAMEPLAY2, true);
            }
            if (FASEAJUGAR == FASE23)  {
            	SndManager.PlayMusic(SndManager.MUSIC_GAMEPLAY2, true);
            }
            if (FASEAJUGAR == FASE31)  {
            	SndManager.PlayMusic(SndManager.MUSIC_GAMEPLAY3, true);
            }
            if (FASEAJUGAR == FASE32)  {
            	SndManager.PlayMusic(SndManager.MUSIC_GAMEPLAY3, true);
            }
            if (FASEAJUGAR == FASE33)  {
            	SndManager.PlayMusic(SndManager.MUSIC_GAMEPLAY3, true);
            }
            return;
    	}
        if (GameScreenTouched(0, Define.margenYmenuPrin + (unDecimo*3) - (unDecimo>>2), Define.SCR_WIDTH, Define.margenYmenuPrin + (unDecimo*4), true, false)) {
        	ESTADO = MENU;
            borrarGraficos();
            cargarGraficos();
            KitmakerAdsProviderManager.getManager().addBanners();
            KitmakerAdsProviderManager.getManager().displayInterstitial();
            resetJuego(); //Reinicio los valores de las fases.
            //cargarCanciones(cancionIntromenu); //Forzar recarga de las canciones.
            //comenzarMusica(cancionIntromenu, true);
            SndManager.PlayMusic(SndManager.MUSIC_MAINMENU, true);
            return;
    	}
        if (GameScreenTouched(0, Define.margenYmenuPrin + (unDecimo*5) - (unDecimo>>2), Define.SCR_WIDTH - botonPausa.getWidth(), Define.margenYmenuPrin + (unDecimo*6), true, false)) {
        	ESTADO = MENU;
                stopMusica();
            MidletFinalKombat.quitApp();
            return;
    	}
        }
        //Menu Animado
        if (ESTADO == MENUANIMADO && duke_intro != null) {
        if (GameScreenTouched(true, false)) {
        	PosXtemp1 = 0;
            PosYtemp1 = 0;        //Ponemos los valores a cero y ahorramos memoria gráfica.
            PosXtemp2 = 0;
            PosYtemp2 = 0;
            estadoTemp = 0;
            fondoCielo1 = null;
            ESTADO = MENU;
            KitmakerAdsProviderManager.getManager().addBanners();
            borrarGraficos();            
            if (partidaGuardada == false) {
                opcionSelect = 1;
            }  //Si no hay partida guardada.
            contadorPulsacionTactil = 0;
            pulsacionTactil = false;
            return;
        }
        return;
        }
        //Menu General
        if (ESTADO == MENU && !submenu && !mostrarAdvertencia) {  //Menu normal
        	if (GameScreenTouched(0, Define.margenYmenuPrin - (unDecimo>>2) - 10, Define.SCR_WIDTH, Define.margenYmenuPrin - (unDecimo>>2) + unDecimo - 10, true, false)) {

        		//Continue
                if (partidaGuardada) {
                        opcionSelect = 0;
                        borrarGraficos();
                        //stopMusica(); //Nextel
                        ESTADO = JUGAR;
                        KitmakerAdsProviderManager.getManager().removeBanners();
                        //Cargamos la memorycard.
                        iniciarMemoryCard();
                        //No ponemos FASEAJUGAR porque ya queda registrado en la memoria al cargar la Memory Card.
                        contadorPulsacionTactil = 0;
                        pulsacionTactil = false;
                        return; 
        	}
        	}
        	if (GameScreenTouched(0, Define.margenYmenuPrin + unDecimo - (unDecimo>>2), Define.SCR_WIDTH, Define.margenYmenuPrin + unDecimo - (unDecimo>>2) + unDecimo, true, false)) {
        		//New Game
                if (partidaGuardada) {
                    
                    mostrarAdvertencia=true;
                    
                } else {                       
                opcionSelect = 0;
                borrarGraficos();
                ESTADO = JUGAR;
                KitmakerAdsProviderManager.getManager().removeBanners();
                //stopMusica(); //Nextel
                partidaGuardada=false;
                FASEAJUGAR = FASE11; //Aquí se indica la fase a jugar. Al ser New Game siempre es la primera fase. HACK.
                }
                //Falta borrar la partida.
                contadorPulsacionTactil = 0;
                pulsacionTactil = false;
                return;
        	}
        	if (GameScreenTouched(0, Define.margenYmenuPrin + (unDecimo * 2) - (unDecimo>>2) + 10, Define.SCR_WIDTH, Define.margenYmenuPrin + (unDecimo * 2) - (unDecimo>>2) + 10 + unDecimo, true, false)){
                    KitmakerAdsProviderManager.getManager().displayInterstitial();
        		//Opciones
                opcionSelect = 0;
                submenu = true;
                contadorPulsacionTactil = 0;
                pulsacionTactil = false;
                return;
        	}
        	if (GameScreenTouched(0, Define.margenYmenuPrin + (unDecimo * 3) - (unDecimo>>2) + 20, Define.SCR_WIDTH, Define.margenYmenuPrin + (unDecimo * 3) - (unDecimo>>2) + 20 + unDecimo, true, false)){
        		//Instrucciones
                ESTADO = INSTRUCCIONES;
                KitmakerAdsProviderManager.getManager().displayInterstitial();
                contadorPulsacionTactil = 0;
                pulsacionTactil = false;
        	}
        	if (GameScreenTouched(0, Define.margenYmenuPrin + (unDecimo * 4) - (unDecimo>>2) + 30, Define.SCR_WIDTH, Define.margenYmenuPrin + (unDecimo * 4) - (unDecimo>>2) + 30 + unDecimo, true, false)) {
        		//About
                ESTADO = ABOUT;
                KitmakerAdsProviderManager.getManager().displayInterstitial();
                contadorPulsacionTactil = 0;
                pulsacionTactil = false;
        	}
        	if (GameScreenTouched(0, Define.margenYmenuPrin + (unDecimo * 5) - (unDecimo>>2) + 40, Define.SCR_WIDTH, Define.margenYmenuPrin + (unDecimo * 5) - (unDecimo>>2) + 40 + unDecimo, true, false)) {
        		//Salir
                //stopMusica();
                    KitmakerAdsProviderManager.getManager().displayInterstitial();
                    MidletFinalKombat.quitApp();
        	}
        }
        //Menu Subopciones
        	if (ESTADO == MENU && submenu == true) {  //Menu normal
        		
        	if (GameScreenTouched(0, Define.margenYmenuPrin + unDecimo - (unDecimo>>2), Define.SCR_WIDTH, Define.margenYmenuPrin + (unDecimo*2), true, false)) {
        		kanforSound = !kanforSound;
        		//stopMusica();
        		SndManager.PauseMusic();
        		     if (kanforSound==true) {
        		                             //cargarCanciones(cancionIntromenu); //Forzar recarga de las canciones.
        		                             //comenzarMusica(cancionIntromenu, true);	  
        		    	 					 SndManager.UnpauseMusic();
        		     }
        		     contadorPulsacionTactil = 0;      
        		     return;
        	}
        	if (GameScreenTouched(0, Define.margenYmenuPrin + (unDecimo*3) - (unDecimo>>2), Define.SCR_WIDTH, Define.margenYmenuPrin + (unDecimo*4), true, false)) {
        									if (Define.Language == 0) {
        			                             destroyLanguage(); //Borra la partida anterior. No se pueden editar.
        			                             iniciarLenguaje(); //Crea una nueva vacía.
        			                             writeLanguage("espanol");  //Crossword es el nombre de la Class.
        			                             Define.Language=1;                          //Es ahora cuando se reselecciona el idioma.
        			                             contadorPulsacionTactil = 0;
        			                             return;
        			                         }
        			                         if (Define.Language == 1) {
        			                             destroyLanguage(); //Borra la partida anterior. No se pueden editar.
        			                             iniciarLenguaje(); //Crea una nueva vacía.
        			                             writeLanguage("ingles");  //Crossword es el nombre de la Class.
        			                             Define.Language=2;
        			                             contadorPulsacionTactil = 0;
        			                             return;
        			                         }
        			                         if (Define.Language == 2) {
        			                             destroyLanguage(); //Borra la partida anterior. No se pueden editar.
        			                             iniciarLenguaje(); //Crea una nueva vacía.
        			                             writeLanguage("brasil");  //Crossword es el nombre de la Class.
        			                             Define.Language=0;
        			                             contadorPulsacionTactil = 0;
        			                             return;
        			                         }
        	}
        	if (GameScreenTouched(0, Define.margenYmenuPrin + (unDecimo*5) - (unDecimo>>2), Define.SCR_WIDTH, Define.margenYmenuPrin + (unDecimo*6), true, false)) {
        		submenu = false;	
        		contadorPulsacionTactil = 0;
        		return;
        	}
        	//contadorPulsacionTactil = 0;
            //pulsacionTactil = false;
            //return;
        	}
        	
        	//About
        	if (ESTADO == ABOUT) {
                    if (GameScreenTouched(true, false)) {
                        ESTADO = MENU;
                        KitmakerAdsProviderManager.getManager().addBanners();
                        contadorPulsacionTactil = 0;
                        pulsacionTactil = false;
                        return;	
                    }
        	}
        	
        	//Instrucciones
        	if (ESTADO == INSTRUCCIONES) {
                    if (GameScreenTouched(true, false)) {
                        ESTADO = MENU;
                        KitmakerAdsProviderManager.getManager().addBanners();
                        contadorPulsacionTactil = 0;
                        pulsacionTactil = false;
                        return;
                    }
            }
        	
        	//Menu Muerte
        	if (pause == true && menuMuerte==true) {
        		if (GameScreenTouched(true, false)) {
        			if (tiempoMuerto>50) {
                                    pause = false;
                                    partidaIniciada = false;
                                    dukeMuere = false;
                                    scrollX=0;
                                    tiempoMuerto=0;
                                    menuMuerte=false;
                                    for (int i=0;i<Ninja2Frame.length;i++)
                                    Ninja2Frame[i]=0;
//                                    KitmakerAdsProviderManager.getManager().displayInterstitial();
                                }
                            contadorPulsacionTactil = 0;
                            pulsacionTactil = false;
                            return;
        		}
                }
        	
        	//Menu Game Over
        	if (ESTADO == GAMEOVER) {
        		if (GameScreenTouched(true, false)) {
        			if (PosXtemp1>50) 
                    {
                    pause = false;
                    partidaIniciada = false;
                    dukeMuere = false;
                    scrollX=0;
                    tiempoMuerto=0;
                    menuMuerte=false;
                    for (int i=0;i<Ninja2Frame.length;i++)
                    Ninja2Frame[i]=0;
                    ESTADO = MENU;
                    KitmakerAdsProviderManager.getManager().addBanners();
                    KitmakerAdsProviderManager.getManager().displayInterstitial();
                    borrarGraficos();
                    cargarGraficos();
                    resetJuego(); //Reinicio los valores de las fases.
                    //cargarCanciones(cancionIntromenu); //Forzar recarga de las canciones.
                    //comenzarMusica(cancionIntromenu, true);
                    SndManager.PlayMusic(SndManager.MUSIC_MAINMENU, true);
                    }
                    contadorPulsacionTactil = 0;
                    pulsacionTactil = false;
                    return;
        		}
            }
        	
        	//Menu Fin Fase
        	if (pause == true && menuMuerte==false && menuFinFase==true) {
        		if (GameScreenTouched(true, false)) {
        			if (PosXtemp2>=(Define.SCR_WIDTH) >> 1) 
                    {
                    //Aqui pones el guardado de partida y avance de nivel
                    //
                    //    
                    stopMusica();
                    menuFinFase=false;
                    pause = false;
                    partidaIniciada = false;
                    dukeMuere = false;
                    scrollX=0;
                    tiempoMuerto=0;
                    menuMuerte=false;
                    for (int i=0;i<Ninja2Frame.length;i++)
                    {
                    Ninja2Frame[i]=0;
                    }
                    //Saltamos al siguiente estado
                    ESTADO = JUGAR;
                    KitmakerAdsProviderManager.getManager().removeBanners();
                    //Aquí cambiamos la siguiente fase a jugar
                    FASEAJUGAR+=1;
                    partidaGuardada=true;
                    //////////////////////////////////////////
                    
                    
                        ////////////////
                        //FIN DL JUEGO//
                        ////////////////
                        if (FASEAJUGAR == FINAL) {
                            ESTADO = FINAL;
                            KitmakerAdsProviderManager.getManager().removeBanners();
                        }
                        
                    ///////////////////////////////////////////    
                    borrarGraficos();
                    cargarGraficos();
                    KitmakerAdsProviderManager.getManager().removeBanners();
                    resetJuego(); //Reinicio los valores de las fases.
                    //cargarCanciones(cancionIntromenu); //Forzar recarga de las canciones.
                    //comenzarMusica(cancionIntromenu, true);
                    }
                    contadorPulsacionTactil = 0;
                    pulsacionTactil = false;
                    return;
        		}
            }
        	
        	//Advertencia de borrado
        	if (mostrarAdvertencia==true) {
        		if (GameScreenTouched(0, Define.margenYmenuPrin + unDecimo - (unDecimo>>2) + unDecimo, Define.SCR_WIDTH, Define.margenYmenuPrin + (unDecimo*2) + unDecimo, true, false)) {
        			//Borrar
                    destroyMemoryCard(); //BORRA la partida.
                   iniciarMemoryCard();
                   writeMemoryCard("fase11");
                   mostrarAdvertencia=false;
                   opcionSelect=1;
                   partidaGuardada=false;  //BORRA la partida.
                   contadorPulsacionTactil = 0;
                   pulsacionTactil = false;
                   resetJuego();
                   return;
        		}
        		if (GameScreenTouched(0, Define.margenYmenuPrin + (unDecimo*3) - (unDecimo>>2) + unDecimo, Define.SCR_WIDTH, Define.margenYmenuPrin + (unDecimo*4) + unDecimo, true, false)) {
        			//Volver
                    mostrarAdvertencia=false;
                    contadorPulsacionTactil = 0;
                    pulsacionTactil = false;
                    return;
        		}
        	}
        	
        	//Pausa
        	if (ESTADO == JUGAR && pause == false && partidaIniciada == true) {
        	if (GameScreenTouched(Define.SCR_WIDTH - botonPausa.getWidth(), posYTablero - botonPausa.getHeight(), Define.SCR_WIDTH, posYTablero, true, false)) {
        		pause = true;
                stopMusica();
                contadorPulsacionTactil = 0;
                pulsacionTactil = false;
                return;
    		}
        	}
    }

			//JUGAR//
			/////////
/*
			if (ESTADO == JUGAR && pause == false && partidaIniciada == true && dukeMuere == false) { 
				
				//Puñetazo
				if (GameScreenTouched((Define.SCR_WIDTH>>1) - (pad.getWidth()>>2), posYTablero + (alturaTablero>>1), (Define.SCR_WIDTH>>1) + (pad.getWidth()>>2), Define.SCR_HEIGHT, true, false)) {
					//Puñetazo
					if (fotograma == 5 && patadaAerea==false) { //PATADA AEREA.
                        patadaAerea = true;
                        }
                        if (pressFight == false && fotograma != 5 && fotograma !=18) {
                        if (COMBO == 0) {
                        fotograma = 14 - 1; //PUÑETAZO.
                        } else {
                        fotograma = 6 - 1; //PATADA.    
                        }
                        pressFight = true;
                        velocidadAnimacion = 0; //Resetea el valor ;-)
                        }
				}
				//Salto
				if (GameScreenTouched((Define.SCR_WIDTH>>1) - (pad.getWidth()>>2), posYTablero, (Define.SCR_WIDTH>>1) + (pad.getWidth()>>2), posYTablero + (alturaTablero>>1), true, false)) {
					if (colision == true) { //Impide un doble salto.
                        pressUp = true;
                        salto = true;
                         }
				}
				//Izquierda
				if (GameScreenTouched((Define.SCR_WIDTH>>1) - (pad.getWidth()>>1), posYTablero + (alturaTablero>>1), (Define.SCR_WIDTH>>1) - (pad.getWidth()>>2), Define.SCR_HEIGHT, true, false)) {
					pressRight = false;
                    pressLeft = true;
				}
				//Derecha
				if (GameScreenTouched((Define.SCR_WIDTH>>1) + (pad.getWidth()>>2), posYTablero + (alturaTablero>>1), (Define.SCR_WIDTH>>1) + (pad.getWidth()>>1), Define.SCR_HEIGHT, true, false)) {
					pressLeft = false;
                    pressRight = true;
				}
				
			}
*/
    }

    public void logo(Graphics g) {
        cargarGraficos();
        g.setColor(0, 0, 0);
        g.fillRect(0, 0, Define.SCR_WIDTH, Define.SCR_HEIGHT);
        g.drawImage(logoKitmaker, Define.SCR_WIDTH >> 1, Define.SCR_HEIGHT >> 1, Graphics.VCENTER | Graphics.HCENTER);
    }

    public void menuIdiomaInic (Graphics g) {
        cargarGraficos();
        KitmakerAdsProviderManager.getManager().addBanners();
        g.drawImage(ladrilloFondo1,0,0,0);     
        Ktext.drawText(g, Define.menuIdiSon[0][Define.Language], sfont2, Define.SCR_WIDTH / 2, unDecimo); //Language
//#if TouchScreen!="true"         
    //    ktext.drawText(g, Define.menuIdiSon[1][Define.Language], sfont2, mitadWidth, Define.margenYmenuPrin + unDecimo); //english
    //    ktext.drawText(g, Define.menuIdiSon[2][Define.Language], sfont2, mitadWidth, Define.margenYmenuPrin + (unDecimo * 2)); //espaÃ±ol     
    //        
    //    if (opcionSelect == 0) {
    //        botonesAnimados(g, Define.margenYmenuPrin + unDecimo);
    //    }
    //    if (opcionSelect == 1) {
    //        botonesAnimados(g, Define.margenYmenuPrin + (unDecimo * 2));
    //    }
//#elif TouchScreen=="true"
         g.setColor(255,0,0);
         //g.drawRoundRect(unDecimo + Define.tactilCorreccion, Define.margenYmenuPrin + unDecimo - (unDecimo>>2), Define.SCR_WIDTH - (unDecimo<<1) - (Define.tactilCorreccion<<1), unDecimo, 10, 10);
         //g.drawRoundRect(unDecimo + Define.tactilCorreccion, Define.margenYmenuPrin + (unDecimo * 3) - (unDecimo>>2), Define.SCR_WIDTH - (unDecimo<<1) - (Define.tactilCorreccion<<1), unDecimo, 10, 10);  
         g.drawImage(boton, mitadWidth, Define.margenYmenuPrin + unDecimo + ((ifont3.getHeight()/5)/2), Graphics.HCENTER | Graphics.VCENTER);
         g.drawImage(boton, mitadWidth, Define.margenYmenuPrin + (unDecimo*3) + ((ifont3.getHeight()/5)/2), Graphics.HCENTER | Graphics.VCENTER);
         g.drawImage(boton, mitadWidth, Define.margenYmenuPrin + (unDecimo*5) + ((ifont3.getHeight()/5)/2), Graphics.HCENTER | Graphics.VCENTER);
         Ktext.drawText(g, Define.menuIdiSon[1][Define.Language], sfont3, mitadWidth - (Define.CorrectorTexto*5), Define.margenYmenuPrin + unDecimo); //No
         Ktext.drawText(g, Define.menuIdiSon[2][Define.Language], sfont3, mitadWidth - (Define.CorrectorTexto*5), Define.margenYmenuPrin + (unDecimo * 3)); //Si
         Ktext.drawText(g, Define.menuIdiSon[6][Define.Language], sfont3, mitadWidth - (Define.CorrectorTexto*5), Define.margenYmenuPrin + (unDecimo * 5)); //brasileño
//#endif        
    }
    
    public void menuSonidoInic (Graphics g) {
        cargarGraficos();
        KitmakerAdsProviderManager.getManager().addBanners();
        g.drawImage(ladrilloFondo1,0,0,0);            
        Ktext.drawText(g, Define.menuIdiSon[3][Define.Language], sfont2, Define.SCR_WIDTH / 2, unDecimo); //Sonido
//#if TouchScreen!="true"        
      //  ktext.drawText(g, Define.menuIdiSon[4][Define.Language], sfont2, mitadWidth - (Define.CorrectorTexto*5), Define.margenYmenuPrin + unDecimo); //No
      //  ktext.drawText(g, Define.menuIdiSon[5][Define.Language], sfont2, mitadWidth - (Define.CorrectorTexto*5), Define.margenYmenuPrin + (unDecimo * 2)); //Si    
//#elif TouchScreen=="true"
         g.setColor(255,0,0);
         //g.drawRoundRect(unDecimo + Define.tactilCorreccion, Define.margenYmenuPrin + unDecimo - (unDecimo>>2), Define.SCR_WIDTH - (unDecimo<<1) - (Define.tactilCorreccion<<1), unDecimo, 10, 10);
         //g.drawRoundRect(unDecimo + Define.tactilCorreccion, Define.margenYmenuPrin + (unDecimo * 3) - (unDecimo>>2), Define.SCR_WIDTH - (unDecimo<<1) - (Define.tactilCorreccion<<1), unDecimo, 10, 10);
         g.drawImage(boton, mitadWidth, Define.margenYmenuPrin + unDecimo + ((ifont3.getHeight()/5)/2), Graphics.HCENTER | Graphics.VCENTER);
         g.drawImage(boton, mitadWidth, Define.margenYmenuPrin + (unDecimo*3) + ((ifont3.getHeight()/5)/2), Graphics.HCENTER | Graphics.VCENTER);
         Ktext.drawText(g, Define.menuIdiSon[4][Define.Language], sfont3, mitadWidth - (Define.CorrectorTexto*5), Define.margenYmenuPrin + unDecimo); //No
         Ktext.drawText(g, Define.menuIdiSon[5][Define.Language], sfont3, mitadWidth - (Define.CorrectorTexto*5), Define.margenYmenuPrin + (unDecimo * 3)); //Si 
//#endif        
            
        if (opcionSelect == 0) {
            //botonesAnimados(g, Define.margenYmenuPrin + unDecimo);
        }
        if (opcionSelect == 1) {
            //botonesAnimados(g, Define.margenYmenuPrin + (unDecimo * 2));
        }        
    }   
    
    public void menuAdvertencia (Graphics g) {
        cargarGraficos();
        KitmakerAdsProviderManager.getManager().addBanners();
        g.drawImage(ladrilloFondo1,0,0,0); 
//#if TouchScreen!="true"        
      //  ktext.drawText(g, Define.menuAdvertencia[0][Define.Language], sfont3, Define.SCR_WIDTH / 2, unDecimo); //tiene una partida anterior
      // ktext.drawText(g, Define.menuAdvertencia[1][Define.Language], sfont3, Define.SCR_WIDTH / 2, unDecimo*2); //tiene una partida anterior
      //  ktext.drawText(g, Define.menuAdvertencia[2][Define.Language], sfont3, Define.SCR_WIDTH / 2, unDecimo*3); //tiene una partida anterior
        
      //  ktext.drawText(g, Define.menuAdvertencia[3][Define.Language], sfont2, mitadWidth - (Define.CorrectorTexto*5), Define.margenYmenuPrin + unDecimo); //No
      //  ktext.drawText(g, Define.menuAdvertencia[4][Define.Language], sfont2, mitadWidth - (Define.CorrectorTexto*5)
//#if ScreenWidth == 128 || ScreenWidth == 130
//#                 +10
//#endif                
              //  , Define.margenYmenuPrin + (unDecimo * 2)); //Si      
            
      //  if (opcionSelect == 0) {
     //       botonesAnimados(g, Define.margenYmenuPrin + unDecimo);
     //   }
     //   if (opcionSelect == 1) {
    //        botonesAnimados(g, Define.margenYmenuPrin + (unDecimo * 2));
    //    }
//#elif TouchScreen=="true"
         g.setColor(255,0,0);
         
         //g.drawRoundRect(unDecimo + Define.tactilCorreccion, Define.margenYmenuPrin + unDecimo - (unDecimo>>2) + unDecimo, Define.SCR_WIDTH - (unDecimo<<1) - (Define.tactilCorreccion<<1), unDecimo, 10, 10);
         //g.drawRoundRect(unDecimo + Define.tactilCorreccion, Define.margenYmenuPrin + (unDecimo * 3) - (unDecimo>>2) + unDecimo, Define.SCR_WIDTH - (unDecimo<<1) - (Define.tactilCorreccion<<1), unDecimo, 10, 10);
         g.drawImage(boton, mitadWidth, Define.margenYmenuPrin + unDecimo + unDecimo + ((ifont3.getHeight()/5)/2), Graphics.HCENTER | Graphics.VCENTER);
         g.drawImage(boton, mitadWidth, Define.margenYmenuPrin + (unDecimo * 3) + unDecimo + ((ifont3.getHeight()/5)/2), Graphics.HCENTER | Graphics.VCENTER);
         
         Ktext.drawText(g, Define.menuAdvertencia[0][Define.Language], sfont3, Define.SCR_WIDTH / 2, unDecimo); //tiene una partida anterior
         Ktext.drawText(g, Define.menuAdvertencia[1][Define.Language], sfont3, Define.SCR_WIDTH / 2, unDecimo*2); //tiene una partida anterior
         Ktext.drawText(g, Define.menuAdvertencia[2][Define.Language], sfont3, Define.SCR_WIDTH / 2, unDecimo*3); //tiene una partida anterior
         
         Ktext.drawText(g, Define.menuAdvertencia[3][Define.Language], sfont3, mitadWidth - (Define.CorrectorTexto*5), Define.margenYmenuPrin + unDecimo + unDecimo); //No
         Ktext.drawText(g, Define.menuAdvertencia[4][Define.Language], sfont3, mitadWidth - (Define.CorrectorTexto*5), Define.margenYmenuPrin + (unDecimo * 3) + unDecimo); //Si 
//#endif        
    }      
    
    public void menuAnimado(Graphics g) {
        cargarGraficos(); //Tranquilo. SÃ³lo se ejecuta una vez.
        //Fondo degradado
        KitmakerAdsProviderManager.getManager().addBanners();
        g.setColor(176, 81, 8);
        g.fillRect(0, 0, Define.SCR_WIDTH, Define.SCR_HEIGHT>>1);
        g.setColor(219, 179, 104);
        g.fillRect(0, Define.SCR_HEIGHT>>1, Define.SCR_WIDTH, Define.SCR_HEIGHT);        
        for (int i = 0; i < 50; i += 2) {
            g.setColor(219 - (i), 179 - (i * 2), 104 - (i * 2));
            g.fillRect(0, (Define.SCR_HEIGHT >> 1) - (i * 4                 
                    ), Define.SCR_WIDTH, (Define.SCR_HEIGHT / 10));
        }
        //g.fillRect(0, 0, Define.SCR_WIDTH, Define.SCR_HEIGHT);
        //Sumador.
        PosXtemp1 -= 1;
        PosYtemp1 += 2 //Velocidad del scroll          
                ; 
        if (PosXtemp1 <= (fondoCielo1.getWidth() * (-1))) {
            PosXtemp1 = 0;
            estadoTemp += 1;
        }
        if (PosYtemp1 == 2) {
            PosYtemp1 = 0;
            PosYtemp2 += 1 //Velocidad de las letras     
                    ;
        }
        if (estadoTemp >= 2) {
            PosXtemp1 -= 3;
            PosYtemp2 += 2;
            PosXtemp2 += 2;
        } //Esto hace saltar el turbo
        if (estadoTemp >= 5) {                            //Salta al menÃº principal
            ESTADO = MENU;
            KitmakerAdsProviderManager.getManager().addBanners();
            g.setColor(255, 255, 255);
            g.fillRect(0, 0, Define.SCR_WIDTH, Define.SCR_HEIGHT);
            borrarGraficos(); //Ponemos los valores a cero y ahorramos memoria grÃ¡fica
            if (partidaGuardada == false) {
                opcionSelect = 1;
            }  //Si no hay partida guardada.
            return;
        }
        
         g.drawImage(fondoCielo1, PosXtemp1, Define.SCR_HEIGHT, Graphics.BOTTOM | Graphics.LEFT);
         g.drawImage(fondoCielo1, PosXtemp1 + fondoCielo1.getWidth(), Define.SCR_HEIGHT, Graphics.BOTTOM | Graphics.LEFT);
         g.drawImage(fondoCielo1, PosXtemp1 + fondoCielo1.getWidth() * 2, Define.SCR_HEIGHT, Graphics.BOTTOM | Graphics.LEFT);   
//#if ScreenWidth>=360
//#          g.drawImage(fondoCielo1, PosXtemp1 + fondoCielo1.getWidth() * 3, Define.SCR_HEIGHT, Graphics.BOTTOM | Graphics.LEFT);   
//#endif         
         
//#if SmallFont
//#         g.drawImage(fondoCielo1, PosXtemp1 + fondoCielo1.getWidth() * 3, Define.SCR_HEIGHT, Graphics.BOTTOM | Graphics.LEFT);
//#         g.drawImage(fondoCielo1, PosXtemp1 + fondoCielo1.getWidth() * 4, Define.SCR_HEIGHT, Graphics.BOTTOM | Graphics.LEFT);
//#endif        

        //Los textos
//#if NormalFont        
         Ktext.drawText(g, Define.intro[0][Define.Language], sfont3, Define.SCR_WIDTH / 2, Define.SCR_HEIGHT - PosYtemp2);
         Ktext.drawText(g, Define.intro[1][Define.Language], sfont3, Define.SCR_WIDTH / 2, Define.SCR_HEIGHT - PosYtemp2 + (Define.SeparacionEntreLineasIntro));
         Ktext.drawText(g, Define.intro[2][Define.Language], sfont3, Define.SCR_WIDTH / 2, Define.SCR_HEIGHT - PosYtemp2 + (Define.SeparacionEntreLineasIntro * 2));
         Ktext.drawText(g, Define.intro[3][Define.Language], sfont3, Define.SCR_WIDTH / 2, Define.SCR_HEIGHT - PosYtemp2 + (Define.SeparacionEntreLineasIntro * 3));
         Ktext.drawText(g, Define.intro[4][Define.Language], sfont3, (Define.SCR_WIDTH / 2) - (Define.CorrectorTexto*3), Define.SCR_HEIGHT - PosYtemp2 + (Define.SeparacionEntreLineasIntro * 4));
         Ktext.drawText(g, Define.intro[5][Define.Language], sfont3, Define.SCR_WIDTH / 2, Define.SCR_HEIGHT - PosYtemp2 + (Define.SeparacionEntreLineasIntro * 5));
         Ktext.drawText(g, Define.intro[6][Define.Language], sfont3, Define.SCR_WIDTH / 2, Define.SCR_HEIGHT - PosYtemp2 + (Define.SeparacionEntreLineasIntro * 6));
         Ktext.drawText(g, Define.intro[7][Define.Language], sfont3, Define.SCR_WIDTH / 2, Define.SCR_HEIGHT - PosYtemp2 + (Define.SeparacionEntreLineasIntro * 7));
         Ktext.drawText(g, Define.intro[8][Define.Language], sfont3, Define.SCR_WIDTH / 2, Define.SCR_HEIGHT - PosYtemp2 + (Define.SeparacionEntreLineasIntro * 8));
         Ktext.drawText(g, Define.intro[9][Define.Language], sfont3, Define.SCR_WIDTH / 2, Define.SCR_HEIGHT - PosYtemp2 + (Define.SeparacionEntreLineasIntro * 9));
         Ktext.drawText(g, Define.intro[10][Define.Language], sfont3, Define.SCR_WIDTH / 2, Define.SCR_HEIGHT - PosYtemp2 + (Define.SeparacionEntreLineasIntro * 10));
//#elif SmallFont
//#         ktext.drawText(g, Define.intro[0][Define.Language], sfont3, (Define.SCR_WIDTH / 2) - (Define.CorrectorTexto*5), Define.SCR_HEIGHT - PosYtemp2);
//#         ktext.drawText(g, Define.intro[1][Define.Language], sfont3, Define.SCR_WIDTH / 2, Define.SCR_HEIGHT - PosYtemp2 + (Define.SeparacionEntreLineasIntro));
//#         ktext.drawText(g, Define.intro[2][Define.Language], sfont3, Define.SCR_WIDTH / 2, Define.SCR_HEIGHT - PosYtemp2 + (Define.SeparacionEntreLineasIntro * 2));
//#         ktext.drawText(g, Define.intro[3][Define.Language], sfont3, Define.SCR_WIDTH / 2, Define.SCR_HEIGHT - PosYtemp2 + (Define.SeparacionEntreLineasIntro * 3));
//#         ktext.drawText(g, Define.intro[4][Define.Language], sfont3, (Define.SCR_WIDTH / 2) + (Define.CorrectorTexto*3), Define.SCR_HEIGHT - PosYtemp2 + (Define.SeparacionEntreLineasIntro * 4));
//#         ktext.drawText(g, Define.intro[5][Define.Language], sfont3, Define.SCR_WIDTH / 2, Define.SCR_HEIGHT - PosYtemp2 + (Define.SeparacionEntreLineasIntro * 5));
//#         ktext.drawText(g, Define.intro[6][Define.Language], sfont3, Define.SCR_WIDTH / 2, Define.SCR_HEIGHT - PosYtemp2 + (Define.SeparacionEntreLineasIntro * 6));
//#         ktext.drawText(g, Define.intro[7][Define.Language], sfont3, (Define.SCR_WIDTH / 2) + (Define.CorrectorTexto*3), Define.SCR_HEIGHT - PosYtemp2 + (Define.SeparacionEntreLineasIntro * 7));
//#         ktext.drawText(g, Define.intro[8][Define.Language], sfont3, Define.SCR_WIDTH / 2, Define.SCR_HEIGHT - PosYtemp2 + (Define.SeparacionEntreLineasIntro * 8));
//#         ktext.drawText(g, Define.intro[9][Define.Language], sfont3, Define.SCR_WIDTH / 2, Define.SCR_HEIGHT - PosYtemp2 + (Define.SeparacionEntreLineasIntro * 9));
//#         ktext.drawText(g, Define.intro[10][Define.Language], sfont3, (Define.SCR_WIDTH / 2) - (Define.CorrectorTexto*10), Define.SCR_HEIGHT - PosYtemp2 + (Define.SeparacionEntreLineasIntro * 10));           
//#endif        

        //Transparencia
//#if TRANSPARENCIAS=="true"        
         g.drawImage(fondoCieloTrans, PosXtemp1, Define.SCR_HEIGHT, Graphics.BOTTOM | Graphics.LEFT);
         g.drawImage(fondoCieloTrans, PosXtemp1 + fondoCielo1.getWidth(), Define.SCR_HEIGHT, Graphics.BOTTOM | Graphics.LEFT);
         g.drawImage(fondoCieloTrans, PosXtemp1 + fondoCielo1.getWidth() * 2, Define.SCR_HEIGHT, Graphics.BOTTOM | Graphics.LEFT);
//#endif        
//#if SmallFont
//#         g.drawImage(fondoCieloTrans, PosXtemp1 + fondoCielo1.getWidth() * 3, Define.SCR_HEIGHT, Graphics.BOTTOM | Graphics.LEFT);
//#         g.drawImage(fondoCieloTrans, PosXtemp1 + fondoCielo1.getWidth() * 4, Define.SCR_HEIGHT, Graphics.BOTTOM | Graphics.LEFT);
//#endif         
        //Dibujo del prota

        g.drawImage(duke_intro, PosXtemp2 - duke_intro.getWidth(), Define.SCR_HEIGHT, Graphics.BOTTOM | Graphics.LEFT);

        //Efectos de flash
        if (PosXtemp2 >= 1 && PosXtemp2 <= 4) {
            g.setColor(255, 255, 255);
            g.fillRect(0, 0, Define.SCR_WIDTH, Define.SCR_HEIGHT);
        }
    }

    int correccionContinue;
    public void menu(Graphics g) {

        if (FASEAJUGAR==FASE11 && opcionSelect==0 && submenu==false) {
            opcionSelect=1;
        }
        //Ktext.background(g, ladrilloFondo1);
        g.drawImage(fondoMenu, 0, 0, 0);
        
        /*
        Ktext.drawText(g, Define.menu[0][Define.Language], sfont1, Define.SCR_WIDTH / 2, unDecimo - correccionContinue //Final
//#if ScreenHeight==160 || ScreenHeight==128 || ScreenHeight==208  || ScreenWidth == 130
//#                 - 15
//#elif ScreenHeight>=416 && ScreenHeight!=800 
//#                 - 30
//#elif ScreenHeight>=800
//#                 - 50
//#endif                
                ); 
        Ktext.drawText(g, Define.menu[1][Define.Language], sfont1, Define.SCR_WIDTH / 2, (unDecimo * 2) - correccionContinue //Kombat
//#if ScreenHeight==160 || ScreenHeight==128 || ScreenHeight==208  || ScreenWidth == 130
//#                 - 10
//#elif ScreenHeight>=416 && ScreenHeight!=800 
//#                 - 35     
//#elif ScreenHeight>=800
//#                 -80
//#endif                
                ); 
                */
        
        if (submenu == false) {
//#if TouchScreen!="true"            
            //Pintado del MENU
         /*   if (partidaGuardada == true) {
                Ktext.drawText(g, Define.menu[2][Define.Language], sfont2, mitadWidth, Define.margenYmenuPrin);
            }
            //Bug de idioma.
            if (Define.Language==0) {
                Define.CorrectorIngles=2;
            } else {
                Define.CorrectorIngles=0;
            }
            //
            Ktext.drawText(g, Define.menu[3][Define.Language], sfont2, mitadWidth + (Define.CorrectorTexto*3)
                    - Define.CorrectorIngles //Bug en resoluciones bajas en inglÃ©s.
                    , Define.margenYmenuPrin + unDecimo - correccionContinue);
            Ktext.drawText(g, Define.menu[4][Define.Language], sfont2, mitadWidth, Define.margenYmenuPrin + (unDecimo * 2) - correccionContinue);
            Ktext.drawText(g, Define.menu[5][Define.Language], sfont2, mitadWidth + (Define.CorrectorTexto*4), Define.margenYmenuPrin + (unDecimo * 3) - correccionContinue);
            Ktext.drawText(g, Define.menu[6][Define.Language], sfont2, mitadWidth - (Define.CorrectorTexto*6), Define.margenYmenuPrin + (unDecimo * 4) - correccionContinue);
            Ktext.drawText(g, Define.menu[7][Define.Language], sfont2, mitadWidth - (Define.CorrectorTexto*4), Define.margenYmenuPrin + (unDecimo * 5) - correccionContinue);*/
//#elif TouchScreen=="true"
             //Pintado del MENU
        	
        	//Pintado de botones táctiles
            g.drawImage(boton, mitadWidth, Define.margenYmenuPrin + unDecimo - correccionContinue + ((ifont3.getHeight()/5)/2), Graphics.HCENTER | Graphics.VCENTER);
            g.drawImage(boton, mitadWidth, Define.margenYmenuPrin + (unDecimo * 2) - correccionContinue + 10 + ((ifont3.getHeight()/5)/2), Graphics.HCENTER | Graphics.VCENTER);
            g.drawImage(boton, mitadWidth, Define.margenYmenuPrin + (unDecimo * 3) - correccionContinue + 20 + ((ifont3.getHeight()/5)/2), Graphics.HCENTER | Graphics.VCENTER);
            g.drawImage(boton, mitadWidth, Define.margenYmenuPrin + (unDecimo * 4) - correccionContinue + 30 + ((ifont3.getHeight()/5)/2), Graphics.HCENTER | Graphics.VCENTER);
            g.drawImage(boton, mitadWidth, Define.margenYmenuPrin + (unDecimo * 5) - correccionContinue + 40 + ((ifont3.getHeight()/5)/2), Graphics.HCENTER | Graphics.VCENTER);
            //
        	
        	
             if (partidaGuardada == true) {
                 //g.setColor(255,0,0);
                 //g.setAlpha(128);
                 g.drawImage(boton, mitadWidth, Define.margenYmenuPrin - 10 + ((ifont3.getHeight()/5)/2), Graphics.HCENTER | Graphics.VCENTER);
                 //g.fillRoundRect((unDecimo>>1) + Define.tactilCorreccion, Define.margenYmenuPrin - (unDecimo>>2) - 10, Define.SCR_WIDTH - (unDecimo) - (Define.tactilCorreccion<<1), unDecimo, 10, 10); 
                 //g.setAlpha(255);
            	 
                 //g.setColor(255,0,0);
                 //g.drawRoundRect((unDecimo>>1) + Define.tactilCorreccion, Define.margenYmenuPrin - (unDecimo>>2) - 10, Define.SCR_WIDTH - (unDecimo) - (Define.tactilCorreccion<<1), unDecimo, 10, 10); 
                 Ktext.drawText(g, Define.menu[2][Define.Language], sfont3, mitadWidth, Define.margenYmenuPrin - 10);
             }
             //Bug de idioma.
             if (Define.Language==0) {
                 Define.CorrectorIngles=2;
             } else {
                 Define.CorrectorIngles=0;
             }
             //
             Ktext.drawText(g, Define.menu[3][Define.Language], sfont3, mitadWidth + (Define.CorrectorTexto*3)
                     - Define.CorrectorIngles //Bug en resoluciones bajas en inglÃ©s.
                     , Define.margenYmenuPrin + unDecimo - correccionContinue);
             Ktext.drawText(g, Define.menu[4][Define.Language], sfont3, mitadWidth, Define.margenYmenuPrin + (unDecimo * 2) - correccionContinue + 10);
             Ktext.drawText(g, Define.menu[5][Define.Language], sfont3, mitadWidth + (Define.CorrectorTexto*4), Define.margenYmenuPrin + (unDecimo * 3) - correccionContinue + 20);
             Ktext.drawText(g, Define.menu[6][Define.Language], sfont3, mitadWidth - (Define.CorrectorTexto*6), Define.margenYmenuPrin + (unDecimo * 4) - correccionContinue + 30);
             Ktext.drawText(g, Define.menu[7][Define.Language], sfont3, mitadWidth - (Define.CorrectorTexto*4), Define.margenYmenuPrin + (unDecimo * 5) - correccionContinue + 40);
             
             //g.setColor(255, 0, 0);
             //g.drawRoundRect((unDecimo>>1) + Define.tactilCorreccion, Define.margenYmenuPrin + unDecimo - (unDecimo>>2), Define.SCR_WIDTH - (unDecimo) - (Define.tactilCorreccion<<1), unDecimo, 10, 10);
             //g.drawRoundRect((unDecimo>>1) + Define.tactilCorreccion, Define.margenYmenuPrin + (unDecimo * 2) - (unDecimo>>2) + 10, Define.SCR_WIDTH - (unDecimo) - (Define.tactilCorreccion<<1), unDecimo, 10, 10);   
             //g.drawRoundRect((unDecimo>>1) + Define.tactilCorreccion, Define.margenYmenuPrin + (unDecimo * 3) - (unDecimo>>2) + 20, Define.SCR_WIDTH - (unDecimo) - (Define.tactilCorreccion<<1), unDecimo, 10, 10); 
             //g.drawRoundRect((unDecimo>>1) + Define.tactilCorreccion, Define.margenYmenuPrin + (unDecimo * 4) - (unDecimo>>2) + 30, Define.SCR_WIDTH - (unDecimo) - (Define.tactilCorreccion<<1), unDecimo, 10, 10);
             //g.drawRoundRect((unDecimo>>1) + Define.tactilCorreccion, Define.margenYmenuPrin + (unDecimo * 5) - (unDecimo>>2) + 40, Define.SCR_WIDTH - (unDecimo) - (Define.tactilCorreccion<<1), unDecimo, 10, 10); 
//#endif            
        } else {
//#if TouchScreen!="true"            
            //Pintado del SUBMENU    
        /*    if (kanforSound == true) {
                Ktext.drawText(g, Define.menu[8][Define.Language], sfont2, mitadWidth, Define.margenYmenuPrin); //Sonido SI
            } else {
                Ktext.drawText(g, Define.menu[11][Define.Language], sfont2, mitadWidth, Define.margenYmenuPrin); //Sonido NO
            }
            Ktext.drawText(g, Define.menu[9][Define.Language], sfont2, mitadWidth + (Define.CorrectorTexto*6)
//#if ScreenWidth==128  || ScreenWidth == 130
//#                     - 2
//#endif                    
                    , Define.margenYmenuPrin + unDecimo); //Idioma EspaÃ±ol
            Ktext.drawText(g, Define.menu[10][Define.Language], sfont2, mitadWidth - (Define.CorrectorTexto*6), Define.margenYmenuPrin + (unDecimo * 2)); //Salir
        */
//#elif TouchScreen=="true" 
             //Pintado del SUBMENU  
        	
        	//Botones Tactiles
            
            g.drawImage(boton, mitadWidth, Define.margenYmenuPrin + unDecimo + ((ifont3.getHeight()/5)/2), Graphics.HCENTER | Graphics.VCENTER);
            g.drawImage(boton, mitadWidth, Define.margenYmenuPrin + (unDecimo*3) + ((ifont3.getHeight()/5)/2), Graphics.HCENTER | Graphics.VCENTER);
            g.drawImage(boton, mitadWidth, Define.margenYmenuPrin + (unDecimo*5) + ((ifont3.getHeight()/5)/2), Graphics.HCENTER | Graphics.VCENTER);
            
            //Fin botones Tactiles
        	
             if (kanforSound == true) {
                 Ktext.drawText(g, Define.menu[8][Define.Language], sfont3, mitadWidth, Define.margenYmenuPrin + unDecimo); //Sonido SI
             } else {
                 Ktext.drawText(g, Define.menu[11][Define.Language], sfont3, mitadWidth, Define.margenYmenuPrin + unDecimo); //Sonido NO
             }
             //g.setColor (255,0,0);
             //g.drawRoundRect(unDecimo + Define.tactilCorreccion, Define.margenYmenuPrin + unDecimo - (unDecimo>>2), Define.SCR_WIDTH - (unDecimo<<1) - (Define.tactilCorreccion<<1), unDecimo, 10, 10);
             //g.drawRoundRect(unDecimo + Define.tactilCorreccion, Define.margenYmenuPrin + (unDecimo * 3) - (unDecimo>>2), Define.SCR_WIDTH - (unDecimo<<1) - (Define.tactilCorreccion<<1), unDecimo, 10, 10);
             //g.drawRoundRect(unDecimo + Define.tactilCorreccion, Define.margenYmenuPrin + (unDecimo * 5) - (unDecimo>>2), Define.SCR_WIDTH - (unDecimo<<1) - (Define.tactilCorreccion<<1), unDecimo, 10, 10);
             Ktext.drawText(g, Define.menu[9][Define.Language], sfont3, mitadWidth + (Define.CorrectorTexto*6), Define.margenYmenuPrin + (unDecimo*3)); //Idioma EspaÃ±ol
             Ktext.drawText(g, Define.menu[10][Define.Language], sfont3, mitadWidth - (Define.CorrectorTexto*6), Define.margenYmenuPrin + (unDecimo * 5)); //Salir        
//#endif
        }
        if (opcionSelect == 0) {
            //botonesAnimados(g, Define.margenYmenuPrin);
        }
        if (opcionSelect == 1) {
            //botonesAnimados(g, Define.margenYmenuPrin + unDecimo);
        }
        if (opcionSelect == 2) {
            //botonesAnimados(g, Define.margenYmenuPrin + (unDecimo * 2));
        }
        if (opcionSelect == 3) {
            //botonesAnimados(g, Define.margenYmenuPrin + (unDecimo * 3));
        }
        if (opcionSelect == 4) {
            //botonesAnimados(g, Define.margenYmenuPrin + (unDecimo * 4));
        }
        if (opcionSelect == 5) {
            //botonesAnimados(g, Define.margenYmenuPrin + (unDecimo * 5));
        }
    }

    public void about(Graphics g) {
        g.drawImage(ladrilloFondo1,0,0,0); 
        Ktext.drawText(g, Define.about[0][Define.Language], sfont2, Define.SCR_WIDTH / 2, unDecimo);
        Ktext.drawText(g, Define.about[1][Define.Language], sfont3, mitadWidth, Define.margenYmenuPrin);
        Ktext.drawText(g, Define.about[2][Define.Language], sfont3, mitadWidth, Define.margenYmenuPrin + unDecimo);
        Ktext.drawText(g, Define.about[3][Define.Language], sfont3, mitadWidth, Define.margenYmenuPrin + unDecimo * 2);
        Ktext.drawText(g, Define.about[4][Define.Language], sfont3, mitadWidth, Define.margenYmenuPrin + unDecimo * 3);
    }

    public void instrucciones(Graphics g) {
        g.drawImage(ladrilloFondo1,0,0,0);      
        Ktext.drawText(g, Define.instrucciones[0][Define.Language], sfont2, (Define.SCR_WIDTH / 2) - 8, PosYtemp2 + (unDecimo>>1));
        Ktext.drawText(g, Define.instrucciones[1][Define.Language], sfont3, mitadWidth, PosYtemp2 + Define.margenYmenuPrin - unDecimo);
        Ktext.drawText(g, Define.instrucciones[2][Define.Language], sfont3, mitadWidth, PosYtemp2 + Define.margenYmenuPrin);
        Ktext.drawText(g, Define.instrucciones[3][Define.Language], sfont3, mitadWidth, PosYtemp2 + Define.margenYmenuPrin + unDecimo);
        Ktext.drawText(g, Define.instrucciones[4][Define.Language], sfont3, mitadWidth, PosYtemp2 + Define.margenYmenuPrin + unDecimo * 2);
        Ktext.drawText(g, Define.instrucciones[5][Define.Language], sfont3, mitadWidth, PosYtemp2 + Define.margenYmenuPrin + unDecimo * 3);
        Ktext.drawText(g, Define.instrucciones[6][Define.Language], sfont3, mitadWidth, PosYtemp2 + Define.margenYmenuPrin + unDecimo * 4);
        Ktext.drawText(g, Define.instrucciones[7][Define.Language], sfont3, mitadWidth, PosYtemp2 + Define.margenYmenuPrin + unDecimo * 5);
        Ktext.drawText(g, Define.instrucciones[8][Define.Language], sfont3, mitadWidth, PosYtemp2 + Define.margenYmenuPrin + unDecimo * 6);
        Ktext.drawText(g, Define.instrucciones[9][Define.Language], sfont3, mitadWidth, PosYtemp2 + Define.margenYmenuPrin + unDecimo * 7);
    }

//////////////////////////PINTADO DEL JUEGO///////////////////////////////////
    ///////////                                       ////////////
//////////////////////////////////////////////////////////////////////////////    
    public void nombreFase() {
        if (FASEAJUGAR == FASE11) {
            nombreFase = "1.1";
        } else
        if (FASEAJUGAR == FASE12)  {
            nombreFase = "1.2";
        } else
        if (FASEAJUGAR == FASE13)  {
            nombreFase = "1.3";
        }
        if (FASEAJUGAR == FASE21)  {
            nombreFase = "2.1";
        }
        if (FASEAJUGAR == FASE22)  {
            nombreFase = "2.2";
        }
        if (FASEAJUGAR == FASE23)  {
            nombreFase = "2.3";
        }
        if (FASEAJUGAR == FASE31)  {
            nombreFase = "3.1";
        }
        if (FASEAJUGAR == FASE32)  {
            nombreFase = "3.2";
        }
        if (FASEAJUGAR == FASE33)  {
            nombreFase = "ultimate";
        }
    }
    
    public void newGame(Graphics g) {

        
        g.setColor(0, 0, 0);
        g.fillRect(0, 0, Define.SCR_WIDTH, Define.SCR_HEIGHT);

        {

            //Rotulo del comienzo de la fase.
            cargarGraficos(); //Solo se ejecuta una vez.
            KitmakerAdsProviderManager.getManager().removeBanners();
            if (partidaIniciada == false) {
                nombreFase(); //Carga el nombre de la fase.
                PosXtemp2 += 1;
                if (PosXtemp1 <= (Define.SCR_WIDTH) >> 1) {
                    PosXtemp1 += 5;
                }
                
                if (PosXtemp1 == 5) {
                     stopMusica();                                                               //Esto jode al i560, pero lo activo porque creo que funciona.    
                     SndManager.PlayMusic(SndManager.SFX_BONG, false);
                }
                
                if (FASEAJUGAR == FASE33)  {
                	Ktext.drawText(g, "final_level", sfont2, PosXtemp1, (Define.SCR_HEIGHT >> 1) - unDecimo);
                } else {
                	Ktext.drawText(g, "level_" + nombreFase, sfont2, PosXtemp1, (Define.SCR_HEIGHT >> 1) - unDecimo);
                }
                Ktext.drawText(g, "_go", sfont2, Define.SCR_WIDTH - PosXtemp1, (Define.SCR_HEIGHT >> 1) + unDecimo);
                
                //Efectos de flash
                if (PosXtemp1 >= (Define.SCR_WIDTH >> 1) - 10 && PosXtemp1 <= (Define.SCR_WIDTH >> 1) - 9) {
                    g.setColor(255, 255, 255);
                    g.fillRect(0, 0, Define.SCR_WIDTH, Define.SCR_HEIGHT);
                }
                if (PosXtemp2==40) {
                	
                    partidaIniciada = true;
                    borrarGraficos();
                    cargarGraficos();
                    KitmakerAdsProviderManager.getManager().removeBanners();
                    //Definicion lÃ­mite de la fase
                    limiteFase = (mapaLadrillo.length * anchuraLadrillo1) - anchuraDuke;
                    //                    
                    PosXtemp2 = 0;             
                    
                    //comenzarMusica(cancionPlaying, true);                                                 //Empieza la canciÃ³n.
                    stopMusica();
                    if (FASEAJUGAR == FASE11) {
                    	SndManager.PlayMusic(SndManager.MUSIC_GAMEPLAY1, true);
                    } else
                    if (FASEAJUGAR == FASE12)  {
                    	SndManager.PlayMusic(SndManager.MUSIC_GAMEPLAY1, true);
                    } else
                    if (FASEAJUGAR == FASE13)  {
                    	SndManager.PlayMusic(SndManager.MUSIC_GAMEPLAY1, true);
                    }
                    if (FASEAJUGAR == FASE21)  {
                    	SndManager.PlayMusic(SndManager.MUSIC_GAMEPLAY2, true);
                    }
                    if (FASEAJUGAR == FASE22)  {
                    	SndManager.PlayMusic(SndManager.MUSIC_GAMEPLAY2, true);
                    }
                    if (FASEAJUGAR == FASE23)  {
                    	SndManager.PlayMusic(SndManager.MUSIC_GAMEPLAY2, true);
                    }
                    if (FASEAJUGAR == FASE31)  {
                    	SndManager.PlayMusic(SndManager.MUSIC_GAMEPLAY3, true);
                    }
                    if (FASEAJUGAR == FASE32)  {
                    	SndManager.PlayMusic(SndManager.MUSIC_GAMEPLAY3, true);
                    }
                    if (FASEAJUGAR == FASE33)  {
                    	SndManager.PlayMusic(SndManager.MUSIC_GAMEPLAY3, true);
                    }
                    
                    
                    for (int i = 0; i < NinjaPosX.length; i++) {
                        ninjaPosXeditable[i] = NinjaPosX[i];                     //Carga la posiciÃ³n de los Ninjas.
//IMPORTANTE. La matriz con las posiciÃ³n de los Ninjas de Fase11 no se modifica. Para ello usamos la variable
//ninjaPosXeditable[] que al comienzo de la fase se resetea con los valores de Fase11. Es esta variable la modificamos
//durante el juego.                
                    }
                    for (int i = 0; i < Ninja2PosX.length; i++) {
                        ninjaPosXeditable2[i] = Ninja2PosX[i];                     //Carga la posiciÃ³n de los Ninjas.
                        Ninja2Delta[i] = 0;
//IMPORTANTE. La matriz con las posiciÃ³n de los Ninjas de Fase11 no se modifica. Para ello usamos la variable
//ninjaPosXeditable[] que al comienzo de la fase se resetea con los valores de Fase11. Es esta variable la modificamos
//durante el juego.                
                    }
               }

            }

            //Pintado de la fase.
            if (partidaIniciada == true) {
                     
                //Contador del reloj de las bombas
                if (frameNinja==2 && tiempoBomba >0) {tiempoBomba -= 1;} //El frameNinja es una constante que siempre se estÃ¡ sumando.
                if (tiempoBomba==0 && fotograma!=5) {                  //Se agota el tiempo y... BOOOOM 
                    tiempoBomba=-1;
                    fotograma=9; //Duke muere.
                    dukeMuere=true;
                    stopMusica();
                    pause=false;
                    ESTADO = GAMEOVER;
                    KitmakerAdsProviderManager.getManager().removeBanners();
                    borrarGraficos();
                    cargarGraficos();
                    KitmakerAdsProviderManager.getManager().removeBanners();
                    //cargarCanciones(explosion);
                    //comenzarMusica(explosion, false); 
                    SndManager.PlayMusic(SndManager.SFX_EXPLOSION, false);
                    return;
                }
                //Completas la fase. Desactivas todas las bombas
                  if (numeroBombas==9) {                                                    //Cambiar para HACK.
                    PosXtemp2=0; //Contador que nos interesa usar.
                    PosXtemp1=0;
                    menuFinFase=true;
                    stopMusica();
                    //Guardamos la partida en la Memory Card.
                    if (FASEAJUGAR==FASE11) {
                        destroyMemoryCard();
                        iniciarMemoryCard();
                        writeMemoryCard("fase12");
                    }
                    if (FASEAJUGAR==FASE12) {
                        destroyMemoryCard();
                        iniciarMemoryCard();
                        writeMemoryCard("fase13");   
                    }
                    if (FASEAJUGAR==FASE13) {
                        destroyMemoryCard();
                        iniciarMemoryCard();
                        writeMemoryCard("fase21");   
                    }
                    if (FASEAJUGAR==FASE21) {
                        destroyMemoryCard();
                        iniciarMemoryCard();
                        writeMemoryCard("fase22");   
                    }
                    if (FASEAJUGAR==FASE22) {
                        destroyMemoryCard();
                        iniciarMemoryCard();
                        writeMemoryCard("fase23");   
                    }
                    if (FASEAJUGAR==FASE23) {
                        destroyMemoryCard();
                        iniciarMemoryCard();
                        writeMemoryCard("fase31");   
                    }
                    if (FASEAJUGAR==FASE31) {
                        destroyMemoryCard();
                        iniciarMemoryCard();
                        writeMemoryCard("fase32");   
                    }
                    if (FASEAJUGAR==FASE32) {
                        destroyMemoryCard();
                        iniciarMemoryCard();
                        writeMemoryCard("fase33");   
                    }
                    //cargarCanciones(finfase);
                    //comenzarMusica(finfase, false);
                    SndManager.PlayMusic(SndManager.MUSIC_FINISHLEVEL, false);
                }
                //Color del cielo
                switch (FASEAJUGAR) {
                    case 20: //Valor numÃ©rico de la fase 
                    case 21:
                    case 22:   
                        g.setColor(0, 187, 255); //Azul
                        break;
                    case 23:
                    case 24:
                    case 25:
                        g.setColor(219, 179, 104); //Naranja
                        break;
                    case 26:
                    case 27:
                    case 28:
                        g.setColor(24, 20, 15); //MarrÃ³n oscuro
                        break;
                }
                g.fillRect(0, 0, Define.SCR_WIDTH, Define.SCR_HEIGHT);

                //Dibujo del cielo.
                for (int i = 0; i < 8; i++) {
                    g.drawImage(fondocielo, (i * anchuraFondocielo) - (scrollX >> 2), Define.SCR_HEIGHT - alturaSuelo - (alturaLadrillo2 >> 1) - alturaFondocielo, Graphics.TOP | Graphics.LEFT);
                }

                //Dibujo muro
                for (int i = 0; i < 70; i++) {
                    g.drawImage(ladrillo2, (i * anchuraLadrillo2) - (scrollX >> 1), Define.SCR_HEIGHT - alturaSuelo - (alturaLadrillo2 >> 1), Graphics.TOP | Graphics.LEFT);
                }
                //Dibujo de los ladrillos
                //Carga el mapa de FASE11
                //Limitador inteligente de pintado//

                if (limiteScrollIzq < scrollX / anchuraLadrillo1) {
                    limiteScrollIzq += 1;
                }
                if (limiteScrollIzq > scrollX / anchuraLadrillo1) {
                    limiteScrollIzq -= 1;
                }
                limiteScrollDer = limiteScrollIzq + anchuraLadrillo1 
                        + Define.limiteInteligentePintado;
                //Limitador inteligente del pintado//
                //if (limiteScrollDer<mapaLadrillo.length) {   //Para evitar que provoque un Error.
                for (int i = limiteScrollIzq; i < limiteScrollDer; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (mapaLadrillo[i] == 1) {
                            g.drawImage(ladrillo1, (i * anchuraLadrillo1) - scrollX, j * alturaLadrillo1, Graphics.TOP | Graphics.LEFT);
                        }
                    }
                }
                //}
                //Dibujo del suelo
                for (int i = 0; i <= 100; i++) {
                    g.drawImage(isuelo, (i * anchuraSuelo) - (scrollX), Define.SCR_HEIGHT - alturaSuelo, Graphics.TOP | Graphics.LEFT);
                }
                //Dibujo de las senefas
                for (int i = 0; i < mapaSenefas.length; i++) {
                    if (mapaSenefas[i] == 1) {
                        g.drawImage(senefa1, (i * anchuraSenefa - scrollX), Define.SCR_HEIGHT - alturaSuelo - alturaSenefa, Graphics.TOP | Graphics.LEFT);
                        g.drawImage(paredbaja1, (i * anchuraParedbaja1 - scrollX), Define.SCR_HEIGHT - alturaSuelo - alturaTienda - alturaCartel1, Graphics.TOP | Graphics.LEFT);
                    }
                }
                //Dibujo de los carteles y carteles
                for (int i = 0; i < mapaTiendas.length; i++) {
                    if (mapaTiendas[i] == 1) {
                        g.drawImage(cartel1, (i * anchuraCartel1 - scrollX), Define.SCR_HEIGHT - alturaSuelo - alturaTienda - alturaCartel1, Graphics.TOP | Graphics.LEFT);
                        g.drawImage(itienda, (i * anchuraTienda - scrollX), Define.SCR_HEIGHT - alturaTienda - alturaSuelo, Graphics.TOP | Graphics.LEFT);
                    }
                    if (mapaTiendas[i] == 2) {
                        g.drawImage(cartel2, (i * anchuraCartel1 - scrollX), Define.SCR_HEIGHT - alturaSuelo - alturaTienda - alturaCartel1, Graphics.TOP | Graphics.LEFT);
                        g.drawImage(itienda, (i * anchuraTienda - scrollX), Define.SCR_HEIGHT - alturaTienda - alturaSuelo, Graphics.TOP | Graphics.LEFT);
                    }
                    if (mapaTiendas[i] == 3) {
                        g.drawImage(cartel3, (i * anchuraCartel1 - scrollX), Define.SCR_HEIGHT - alturaSuelo - alturaTienda - alturaCartel1, Graphics.TOP | Graphics.LEFT);
                        g.drawImage(itienda, (i * anchuraTienda - scrollX), Define.SCR_HEIGHT - alturaTienda - alturaSuelo, Graphics.TOP | Graphics.LEFT);
                    }
                }
                //Dibujo de las ventanas
//#if ScreenHeight!=208 && ScreenHeight!=205        
                for (int i = 0; i < mapaVentanas.length; i++) {
                    if (mapaVentanas[i] == 1) {
                        g.drawImage(ventana1, ((i * anchuraTienda - scrollX)) + (anchuraTienda >> 1) - anchuraVentana1, Define.SCR_HEIGHT >> 3, Graphics.TOP | Graphics.HCENTER);
                        g.drawImage(ventana1, ((i * anchuraTienda - scrollX)) + (anchuraTienda >> 1) + anchuraVentana1, Define.SCR_HEIGHT >> 3, Graphics.TOP | Graphics.HCENTER);
                    }
                }
//#endif                
                //Dibujo de las plataformas metÃ¡licas1
                //Plataformas 1
                for (int i = 0; i < mapaMetalicas1.length; i++) {
                    if (mapaMetalicas1[i] == 1) {
                        g.drawImage(plataformaMetalica, (i * anchuraPlataforma - scrollX), plataformaMetalicaY1, Graphics.TOP | Graphics.LEFT);
                    }
                }
                //Dibujo de las plataformas metÃ¡licas2
                for (int i = 0; i < mapaMetalicas2.length; i++) {
                    if (mapaMetalicas2[i] == 1) {
                        g.drawImage(plataformaMetalica, (i * anchuraPlataforma - scrollX), plataformaMetalicaY2, Graphics.TOP | Graphics.LEFT);
                    }
                }
                //Dibujo del coche del policÃ­a
                g.drawImage(policeCar, (anchuraPoliceCar >> 4) + dukePosX - scrollX, Define.SCR_HEIGHT - alturaSuelo - alturaPoliceCar, Graphics.TOP | Graphics.RIGHT);
                g.drawImage(policeCar, limiteFase + anchuraDuke - anchuraPoliceCar - scrollX, Define.SCR_HEIGHT - alturaSuelo - alturaPoliceCar, Graphics.TOP | Graphics.LEFT);
                //Dibujo de la DINAMITA
                PosXtemp2++;
                if (PosXtemp2 == 4) {
                    PosXtemp2 = 0;
                    if (frameDinamita == 0) {
                        frameDinamita = 1;
                    } else {
                        frameDinamita = 0;
                    }
                }
                sDinamita.setFrame(frameDinamita);
                for (int i = 0; i < Bomba.length; i++) {
                    if (Bomba[i] >= 1) {
                        if (Bomba[i] == 1) {
                            bombaPosY = Define.SCR_HEIGHT - alturaSuelo - alturaDinamita;
                        }
                        if (Bomba[i] == 2) {
                            bombaPosY = plataformaMetalicaY1 - alturaDinamita;
                        }
                        if (Bomba[i] == 3) {
                            bombaPosY = plataformaMetalicaY2 - alturaDinamita;
                        }
                        sDinamita.setPosition((i * anchuraPlataforma) - scrollX, bombaPosY);
                        sDinamita.paint(g);
                    }
                    if (Bomba[i] <= -1) {
                        if (Bomba[i] == -1) {
                            bombaPosY = Define.SCR_HEIGHT - alturaSuelo - alturaDinamita;
                        }
                        if (Bomba[i] == -2) {
                            bombaPosY = plataformaMetalicaY1 - alturaDinamita;
                        }
                        if (Bomba[i] == -3) {
                            bombaPosY = plataformaMetalicaY2 - alturaDinamita;
                        }
                        g.drawImage(iDinamitaOk, (i * anchuraPlataforma) - scrollX, bombaPosY, Graphics.TOP | Graphics.LEFT);   //El scrollX estÃ¡ jodiendo la posiciÃ³n
                    }
                }
                
//Pintado de Duke vivo para que no se solape con los enemigos.                
if (dukeMuere==false && pintarDuke==true) {duke.paint(g);}                              
                
                //Dibujo de los Ninjas
                
                //Frames Ninja
                frameNinja+=1;
                if (frameNinja==velocidadAnimancionNinja) {frameNinja=0;}
                
                //Super FOR de los Ninjas
                for (int i=0;i<NinjaPosX.length;i++) {
                    
                 if (frameNinjaFinal[i]==9 && tiempoGolpe==5) { //RecuperaciÃ³n del Ninja tras recibir un golpe.
                 frameNinjaFinal[i]=0;
                 //try {
                 //pomwavsimple.wait();//Detenemos el FX para poder reproducirlo otra vez.
                 //} catch (Exception e) {}
                 }
                    //Tiempo Golpe. PequeÃ±a pausa despuÃ©s de patear a un Ninja.
                     if (frameNinjaFinal[i]==9)
                     {
                     tiempoGolpe++;
                     }     
                    
                //NINJA VIVO
                    if (frameNinjaFinal[i]<5) { //Si vale mÃ¡s de 6 es que estÃ¡ muerto. Si vale 5 estÃ¡ golpeando.
                    //Ninja rotado si estÃ¡ a la derecha.
                    
                    if (ninjaPosXeditable[i] < scrollX+(Define.SCR_WIDTH>>1) - (anchuraDuke>>1)) {direccionNinja[i]=true;}/*sninja1.setTransform(Sprite.TRANS_NONE);*/  //Ninja no rota.
                            
                    else if (ninjaPosXeditable[i] > scrollX+(Define.SCR_WIDTH>>1) - (anchuraDuke>>1)) {direccionNinja[i]=false;}/*sninja1.setTransform(Sprite.TRANS_MIRROR);*/ //Ninja rota.
                    
                    
                    //Ninja corre hacia Duke
                    if (ninjaPosXeditable[i] > scrollX+(Define.SCR_WIDTH>>1)-(anchuraDuke>>1)
                        && ninjaPosXeditable[i] < scrollX+Define.SCR_WIDTH  
                        && dukePosY > ninja1PoxY-alturaNinja    
                            ) {
                        //sninja1.setTransform(Sprite.TRANS_MIRROR);
                        direccionNinja[i]=false; //Ninja rota.
                        ninjaPosXeditable[i]-=Define.avanceXNinja1;//2;
                        if (frameNinjaFinal[i]<= 4 && frameNinja == 2) {frameNinjaFinal[i]++;}
                        if (frameNinjaFinal[i]>=4) {frameNinjaFinal[i]=0;}
                    }
                    
                    else if (ninjaPosXeditable[i] < scrollX+(Define.SCR_WIDTH>>1)-(anchuraDuke>>1)
                        && ninjaPosXeditable[i] > scrollX - anchuraDuke    
                        && dukePosY > ninja1PoxY-alturaNinja 
                            ) {
                        //sninja1.setTransform(Sprite.TRANS_NONE);
                        direccionNinja[i]=true;
                        ninjaPosXeditable[i]+=Define.avanceXNinja1;//2;
                        if (frameNinjaFinal[i] <= 4 && frameNinja == 2) {frameNinjaFinal[i]++;}
                        if (frameNinjaFinal[i]>=4) {frameNinjaFinal[i]=0;}
                        
                    } else {frameNinjaFinal[i]=4;} //Si no se cumple ninguna condiciÃ³n se queda parado.

                 //Pintado del Ninja
                 if (direccionNinja[i]==true) {   
                 sninja1.setFrame(frameNinjaFinal[i]);   
                 sninja1.setPosition(ninjaPosXeditable[i]-scrollX, ninja1PoxY);   //Pintado de los Ninjas sobre el suelo.
                 sninja1.paint(g);   
                    } else {
                 sninja1rotado.setFrame(frameNinjaFinal[i]);   
                 sninja1rotado.setPosition(ninjaPosXeditable[i]-scrollX, ninja1PoxY);   //Pintado de los Ninjas sobre el suelo.
                 sninja1rotado.paint(g);    
                 }
                    }
                 //NINJA MUERTO
                 //if (frameNinjaFinal[i]==6) {comenzarMusica(sonidoFX, false);} //FX muriendo.
                 
                 if (frameNinjaFinal[i]>=6) { //Si vale mÃ¡s de 6 es que estÃ¡ muerto.     
                	 
                	 
                	 
                //Pintado del Ninja MUERTO
                	 
                	 
                	 
                 //Muere a la derecha.
                 if (direccionNinja[i]==true) { 
                 sninja1muertoDer.setFrame(frameNinjaFinal[i]);   
                 sninja1muertoDer.setPosition(ninjaPosXeditable[i]-scrollX, ninja1PoxY);   //Pintado de los Ninjas sobre el suelo.
                 sninja1muertoDer.paint(g);              
                 if (frameNinjaFinal[i]==6) {
                	 //repaint();
                	 pausaCorta(50);
                	 
                	 if (bugWaviniciado==false) {
                	 SndManager.PlayFX(SndManager.FX_MUERTE);
                	 bugWaviniciado=true;
                	 }
                	 //comenzarMusica(pom, false);
                	 } //Pausa corta al ser golpeado. 
                 if (frameNinjaFinal[i]==7) {bugWaviniciado=false;}
                 if (frameNinjaFinal[i]<8 && frameNinja == 2) {frameNinjaFinal[i]+=1;} //Ninja tendifo en el suelo.                   
                 }
                 
                 
                 //Muere a la izquierda.
                 if (direccionNinja[i]==false) { 
                 sninja1muertoIzq.setFrame(frameNinjaFinal[i]);   
                 sninja1muertoIzq.setPosition(ninjaPosXeditable[i]-scrollX, ninja1PoxY);   //Pintado de los Ninjas sobre el suelo.
                 sninja1muertoIzq.paint(g);
                 if (frameNinjaFinal[i]==6) {//Pausa corta al ser golpeado.
                     //repaint();
                     pausaCorta(50);
                 	 SndManager.PlayFX(SndManager.FX_MUERTE);
                     
                 }
                 if (frameNinjaFinal[i]==7) {bugWaviniciado=false;}
                 if (frameNinjaFinal[i]<8 && frameNinja == 2) {frameNinjaFinal[i]+=1;} //Ninja tendifo en el suelo.
                 }
                 
                 
                 
                 
                 }  
                 
                 
                 
                 //NINJA GOLPEANDO
                 if (frameNinjaFinal[i]==5 && godMode==false) { //Si vale 5 es que estÃ¡ golpeando.
                	 SndManager.PlayFX(SndManager.FX_MUERTE);
                     
                     pressLeft=false;
                     pressRight=false;
                     pressFight=false;
                     pressUp=false;
                     pressDown=false;
                     sninja1.setFrame(frameNinjaFinal[i]);
                     if (ninjaPosXeditable[i] < scrollX+(Define.SCR_WIDTH>>1)) {sninja1.setTransform(Sprite.TRANS_NONE);}
                     if (ninjaPosXeditable[i] > scrollX+(Define.SCR_WIDTH>>1) - (anchuraDuke>>1)) {sninja1.setTransform(Sprite.TRANS_MIRROR);}
                     sninja1.setPosition(ninjaPosXeditable[i]-scrollX, ninja1PoxY);
                     sninja1.paint(g);
                     if (fotograma<9) { //Truco para que solo se ponga en 9 una vez. Solo se ejecuta esto una vez.
                         fotograma=9;                       
                     } 
                     dukeMuere=true; //Activa el pintado de la muerte al final del Paint.                 
                     
                 }
                }
 
             //NINJA CON ESPADA
                for (int i = 0; i < ninjaPosXeditable2.length; i++) {
                    
                    if (Ninja2Frame[i]==5) { //Reseteo del bloqueo del Ninja con Espada
                        Ninja2Frame[i]=0;
                        if (ESTADONINJA2 == NINJA2CORRIENDODERECHA) {
                            sninja2.setTransform(Sprite.TRANS_NONE);
                        }
                    } 
                    //
                    if (ninjaPosXeditable2[i] == 2) {
                      if (Ninja2Frame[i]!=4) {  
                        if (ESTADONINJA2 == NINJA2PARADODERECHA) {
                            Ninja2Frame[i]=0;
                            Ninja2Tiempo[i]++; 
                        }
                        if (Ninja2Tiempo[i]==50) {
                            ESTADONINJA2 = NINJA2CORRIENDOIZQUIERDA;
                        }
                        if (ESTADONINJA2 == NINJA2CORRIENDOIZQUIERDA) {
                            sninja2.setTransform(Sprite.TRANS_MIRROR);
                            Ninja2Delta[i]+=2;
                            if (frameNinja==2) { 
                               Ninja2Frame[i]++; 
                            }
                            if (Ninja2Frame[i]==4) {
                                Ninja2Frame[i]=0;
                            }
                        }
                        if (Ninja2Delta[i]>=(anchuraLadrillo1*6) && ESTADONINJA2 == NINJA2CORRIENDOIZQUIERDA) {
                            ESTADONINJA2 = NINJA2PARADOIZQUIERDA;
                        }
                        if (ESTADONINJA2 == NINJA2PARADOIZQUIERDA) {
                            Ninja2Frame[i]=0;
                            Ninja2Tiempo[i]++; 
                        }
                        if (Ninja2Tiempo[i]==100) {
                            sninja2.setTransform(Sprite.TRANS_NONE);
                            ESTADONINJA2 = NINJA2CORRIENDODERECHA;
                        }
                        if (ESTADONINJA2 == NINJA2CORRIENDODERECHA) {
                            Ninja2Tiempo[i]=0;
                            Ninja2Delta[i]-=2;
                            if (frameNinja==2) {
                               Ninja2Frame[i]++; 
                            }
                            if (Ninja2Frame[i]==4) {
                                Ninja2Frame[i]=0;
                            }
                        }
                        if (Ninja2Delta[i]<=0 && ESTADONINJA2 == NINJA2CORRIENDODERECHA) {
                            ESTADONINJA2 = NINJA2PARADODERECHA;
                        }
                      }
            //Bloqueo del Ninja con Espada         
            if ((i * anchuraPlataforma - scrollX - Ninja2Delta[i] > dukePosX - anchuraDuke)
                 && (i * anchuraPlataforma - scrollX - Ninja2Delta[i] < dukePosX + anchuraDuke + anchuraDuke)
                 && dukePosY >= ninja2PoxY //- alturaNinja2
                 && dukePosY <= ninja2PoxY + alturaNinja2   
                 && fotograma!=5 //Duke no estÃ¡ saltando
                 && (fotograma==18 //ni ejecutando su patada aerea 
                 || fotograma==7
                 || fotograma==15)   
                 && dukeMuere==false   
                    ) {
                if (direccionDuke==true) {
                    sninja2.setTransform(Sprite.TRANS_MIRROR);
                } else {
                    sninja2.setTransform(Sprite.TRANS_NONE);
                }
                Ninja2Frame[i] = 5; //Fotograma de bloqueo del ninja con espada
            }
            //ColisiÃ³n con los Ninjas2 con espada
            //Mata de Duke          
            if ((i * anchuraPlataforma - scrollX - Ninja2Delta[i] > dukePosX - (anchuraDuke>>1))
                 && (i * anchuraPlataforma - scrollX - Ninja2Delta[i] < dukePosX + (anchuraDuke>>1))
                 && dukePosY >= ninja2PoxY
                 && dukePosY <= ninja2PoxY + alturaNinja2   
                 && fotograma!=5 //Duke no estÃ¡ saltando
                 && fotograma!=18 //ni ejecutando su patada aerea   
                 && dukeMuere==false
                 && godMode==false
                    ) {
                if (ESTADONINJA2 == NINJA2PARADOIZQUIERDA) {
                    sninja2.setTransform(Sprite.TRANS_NONE);
                }
                if (ESTADONINJA2 == NINJA2PARADODERECHA) {
                    sninja2.setTransform(Sprite.TRANS_MIRROR);
                } 
                Ninja2Frame[i]=4;
                     if (fotograma!=9) { //Truco para que solo se ponga en 9 una vez. Solo se ejecuta esto una vez.
                         fotograma=9;        
                         SndManager.PlayFX(SndManager.FX_MUERTE);
                     }                 
                dukeMuere=true;
           	 	SndManager.PlayFX(SndManager.FX_MUERTE);
            	}
                        sninja2.setFrame(Ninja2Frame[i]);
                        sninja2.setPosition((i * anchuraPlataforma - scrollX - ninja2Margen - Ninja2Delta[i]), ninja2PoxY);
                        sninja2.paint(g);                        
                    }
                }            

                //Dubujo de los marcadores
                g.drawImage(vidas, vidas.getWidth() >> 2, vidas.getHeight() >> 2, Graphics.TOP | Graphics.LEFT);
                Ktext.drawText(g, "x" + numeroVidas, sfont2, (vidas.getWidth() >> 2) + (vidas.getWidth() >> 1)
//#if ScreenWidth == 128  || ScreenWidth == 130
//#                     - 6
//#endif                         
                        , (vidas.getHeight() >> 2) + vidas.getHeight());
                Ktext.drawText(g, "time", sfont2, (Define.SCR_WIDTH >> 1)
//#if ScreenWidth==128  || ScreenWidth == 130
//#                     - 20
//#endif                        
                        , vidas.getHeight() >> 2);
                if (tiempoBomba>0) {
                	Ktext.drawText(g, "" + tiempoBomba, sfont2, (Define.SCR_WIDTH >> 1) //Tiempo Bomba
//#if ScreenWidth==128 || ScreenWidth == 130
//#                     + 5
//#endif                         
                        , (vidas.getHeight() >> 2)
//#if ScreenWidth!=128 || ScreenWidth == 130                        
                        * 4
//#endif                        
                        );
                } else {
                	Ktext.drawText(g, "boom", sfont2, (Define.SCR_WIDTH >> 1)
//#if ScreenWidth==128 || ScreenWidth == 130
//#                     + 5
//#endif                         
                        , (vidas.getHeight() >> 2)
//#if ScreenWidth!=128 || ScreenWidth == 130                        
                        * 4
//#endif                        
                        );    
                }

                sDinamitaMarcador.setFrame(frameDinamita);
                sDinamitaMarcador.setPosition(Define.SCR_WIDTH - vidas.getWidth() - (vidas.getWidth() >> 2), vidas.getHeight() >> 2);
                sDinamitaMarcador.paint(g);
                Ktext.drawText(g, numeroBombas + "/9", sfont2, Define.SCR_WIDTH - (vidas.getWidth() >> 2) - (vidas.getWidth() >> 1)
//#if ScreenWidth == 128 || ScreenWidth == 130
//#                     - 6
//#endif                         
                        , (vidas.getHeight() >> 2) + vidas.getHeight());


                //Control de estados de Duke
                if (fotograma<9 || fotograma>12) { //Si Duke sigue VIVO
                    controlDuke();
                    //Dibujar a Duke
                    if (direccionDuke == false) {
                        duke.setTransform(Sprite.TRANS_MIRROR);
                    }
                    if (direccionDuke == true) {
                        duke.setTransform(Sprite.TRANS_NONE);
                    }
                }              
                //Pintado de Duke, tanto VIVO como MUERTO.
                //Pintado de la MUERTE.
                if (dukeMuere==true) {
                    dukeMuere(g);
                }
                //Pintado de Duke VIVO.
if (numeroVidas>=0) {                
                if (patadaAerea==true) { //Patada aerea.
                    contadorPatada++;
                }
                if (contadorPatada==5) {
                    contadorPatada=0;
                    patadaAerea=false;
                }
                if (fotograma==9) {g.setColor(255,255,255);g.fillRect(0, 0, Define.SCR_WIDTH, Define.SCR_HEIGHT);} //Efecto Flash al recibir un golpe.
                duke.setFrame(fotograma);
                duke.setPosition(dukePosX, dukePosY);
                //Pintado de Duke al morir. El pintado cuando vive se hace mÃ¡s atrÃ¡s para no solapar a los enemigos.
                if (dukeMuere==true || pintarDuke==false) {
                    duke.paint(g);}
                if (frameNinja==2 && pintarDuke==false) {
                    pintarDuke=true;
                    }
                //Boton PAUSA
//#if TouchScreen!="true"                
           //     g.drawImage(botonPausa, Define.SCR_WIDTH, Define.SCR_HEIGHT, Graphics.BOTTOM | Graphics.RIGHT);
//#elif TouchScreen=="true"
                 if (dukeMuere==false) {
                 g.drawImage(botonPausa, Define.SCR_WIDTH, posYTablero, Graphics.BOTTOM | Graphics.RIGHT);
                 }
//#endif                
//#if TouchScreen=="true"
                 //Pintado del mando ARCADE
                 if (fotograma!=12) { //Duke muerto.
                 g.drawImage(tablero, 0, Define.SCR_HEIGHT, Graphics.BOTTOM | Graphics.LEFT);
                 g.drawImage(pad, (Define.SCR_WIDTH>>1), Define.SCR_HEIGHT, Graphics.BOTTOM | Graphics.HCENTER);
                 }
//#endif                          
                }   
            }
        }

    }

    public void controlDuke() {
        
//#if TouchScreen=="true"
//#         //if (pressRight==true) {framePalanca=2;}
//#         //if (pressLeft==true) {framePalanca=1;}
//#         //if (pressLeft==false && pressRight==false) {framePalanca=0;}       
//#endif        
        //Combo puÃ±etazo y patada.
        if (COMBO==1) {tiempoCombo++;}
        if (tiempoCombo>=10) {tiempoCombo=0;COMBO=0;}
        if (fotograma==8 && pressFight==true) {pressFight=false;}
        //
        colision();
        if (salto == true && colision == true) { //El colision es TRUE poque solo se puede saltar si estÃ¡s sobre algo. LÃ³gico.
            PosYtemp1 += 1;
            if (patadaAerea == false) {
            fotograma = 5;
            } else {
                fotograma = 18;
            }
            if (pressLeft == true && scrollX > 3) {
                direccionDuke = false;
                scrollX -= velocidadDuke;
            }
            if (pressRight == true && (scrollX+Define.SCR_WIDTH)<limiteFase) {
                direccionDuke = true;
                scrollX += velocidadDuke;
            }
            if (PosYtemp1 < fuerzaSalto) {
                dukePosY -= saltoDesplazMax - (PosYtemp1 / 2);
            }
            if (PosYtemp1 == fuerzaSalto) {
                PosYtemp1 = 0;
                salto = false;
                colision = false;
            }
            return;
        }
        if (salto == false && colision == false) {
            if (PosYtemp1 < saltoDesplazMax >> 1) {
                PosYtemp1 += 1;
            } //El -3 es para suavizar el salto.
            else {
                PosYtemp1 = 5              
                        ;
            }
            dukePosY += PosYtemp1
//#if ScreenHeight==360 || ScreenHeight>=416
//#                         +5
//#endif                              
                    ;
            if (pressLeft == true && scrollX > 3) {
                direccionDuke = false;
                scrollX -= velocidadDuke;
            }
            if (pressRight == true && (scrollX+Define.SCR_WIDTH)<limiteFase) {
                direccionDuke = true;
                scrollX += velocidadDuke;
            }
            return;
        }
        if (pressRight == true && pressLeft == false && pressFight == false && /*pressUp == false && */ (scrollX+Define.SCR_WIDTH)<limiteFase) {
            PosYtemp1 = 0; //Por si acaso.
            direccionDuke = true;
            scrollX += velocidadDuke;
            animacion = CORRIENDO;
            pasafotograma();
            return;
        }
        if (pressLeft == true && pressRight == false && pressFight == false && /*pressUp == false && */ scrollX > 3) {
            PosYtemp1 = 0; //Por si acaso.
            direccionDuke = false;
            scrollX -= velocidadDuke;
            animacion = CORRIENDO;
            pasafotograma();
            return;
        }
        if (pressFight == true /*
                 * && fotograma!=5
                 */) {
            animacion = PATADA;
            pasafotograma();
            return;
        }
        //Si no se ejectura nada sucede lo siguiente:
        PosYtemp1 = 0; //Por si acaso.
        animacion = PARADO;
        pasafotograma();
    }

    public void menuPausa (Graphics g) {
                //Dubujo de los marcadores
       
        g.drawImage(ladrilloFondo1,0,0,0); 
                g.drawImage(vidas, vidas.getWidth() >> 2, vidas.getHeight() >> 2, Graphics.TOP | Graphics.LEFT);
                Ktext.drawText(g, "x" + numeroVidas, sfont2, (vidas.getWidth() >> 2) + (vidas.getWidth() >> 1)
//#if ScreenWidth == 128 || ScreenWidth == 130
//#                     - (unDecimo>>1) + 2
//#endif                         
                        , (vidas.getHeight() >> 2) + vidas.getHeight());
                Ktext.drawText(g, "time", sfont2, Define.SCR_WIDTH >> 1, vidas.getHeight() >> 2);
                Ktext.drawText(g, "" + tiempoBomba, sfont2, Define.SCR_WIDTH >> 1, (vidas.getHeight() >> 2) * 4);

                sDinamitaMarcador.setFrame(frameDinamita);
                sDinamitaMarcador.setPosition(Define.SCR_WIDTH - vidas.getWidth() - (vidas.getWidth() >> 2), vidas.getHeight() >> 2);
                sDinamitaMarcador.paint(g);

          g.setColor(255,0,0);
          //g.drawRoundRect((unDecimo>>1) + Define.tactilCorreccion, Define.margenYmenuPrin + unDecimo - (unDecimo>>2), Define.SCR_WIDTH - (unDecimo) - (Define.tactilCorreccion<<1), unDecimo, 10, 10);
          //g.drawRoundRect((unDecimo>>1) + Define.tactilCorreccion, Define.margenYmenuPrin + (unDecimo * 3) - (unDecimo>>2), Define.SCR_WIDTH - (unDecimo) - (Define.tactilCorreccion<<1), unDecimo, 10, 10);    
          //g.drawRoundRect((unDecimo>>1) + Define.tactilCorreccion, Define.margenYmenuPrin + (unDecimo * 5) - (unDecimo>>2), Define.SCR_WIDTH - (unDecimo) - (Define.tactilCorreccion<<1), unDecimo, 10, 10);
          g.drawImage(boton, mitadWidth, Define.margenYmenuPrin + unDecimo + ((ifont3.getHeight()/5)/2), Graphics.HCENTER | Graphics.VCENTER);
          g.drawImage(boton, mitadWidth, Define.margenYmenuPrin + (unDecimo * 3) + ((ifont3.getHeight()/5)/2), Graphics.HCENTER | Graphics.VCENTER);
          g.drawImage(boton, mitadWidth, Define.margenYmenuPrin + (unDecimo * 5) + ((ifont3.getHeight()/5)/2), Graphics.HCENTER | Graphics.VCENTER);
          
          Ktext.drawText(g, Define.menuPausa[0][Define.Language], sfont3, mitadWidth, Define.margenYmenuPrin + unDecimo); //Continuar
          Ktext.drawText(g, Define.menuPausa[1][Define.Language], sfont3, mitadWidth, Define.margenYmenuPrin + (unDecimo * 3)); //Salir al menÃº      
          Ktext.drawText(g, Define.menuPausa[2][Define.Language], sfont3, mitadWidth, Define.margenYmenuPrin + (unDecimo * 5)); //Salir del juego                   
              
    }
    
    public void finFase (Graphics g) {
        pause = true;
        //ktext.background(g, ladrilloFondo1);
        g.setColor(0,0,0);
        g.fillRect(0, 0, Define.SCR_WIDTH, Define.SCR_HEIGHT);
                //Texto animado
                if (PosXtemp2 <= (Define.SCR_WIDTH) >> 1) {
                    PosXtemp2 += 5;
                }
                Ktext.drawText(g, Define.finFase[0][Define.Language], sfont2, PosXtemp2, 100);
                Ktext.drawText(g, Define.finFase[1][Define.Language], sfont2, Define.SCR_WIDTH - PosXtemp2, 50);
                //Efectos de flash
                if (PosXtemp2 >= (Define.SCR_WIDTH >> 1) - 10 && PosXtemp2 <= (Define.SCR_WIDTH >> 1) - 9) {
                    g.setColor(255, 255, 255);
                    g.fillRect(0, 0, Define.SCR_WIDTH, Define.SCR_HEIGHT);
                }
        if (PosXtemp2>=(Define.SCR_WIDTH) >> 1) {        
        if (tiempoBomba>0) {
            tiempoBomba-=1;
            puntosFinFase+=100;
        }
        //Dubujo de los marcadores
        Ktext.drawText(g, Define.finFase[2][Define.Language], sfont3, (Define.SCR_WIDTH >> 1), (unCuarto*2) - unDecimo);
                Ktext.drawText(g, "" + tiempoBomba, sfont3, (Define.SCR_WIDTH >> 1)
//#if ScreenWidth==128 || ScreenWidth == 130
//#         - 6               
//#endif                         
                        , (unCuarto*2)+unDecimo - unDecimo);
                Ktext.drawText(g, Define.finFase[3][Define.Language], sfont3, (Define.SCR_WIDTH >> 1)
//#if ScreenWidth==128 || ScreenWidth == 130
//#         - 5                
//#endif                         
                        , (unCuarto*3) - unDecimo);
                Ktext.drawText(g, "" + puntosFinFase, sfont3, (Define.SCR_WIDTH >> 1)
//#if ScreenWidth==128 || ScreenWidth == 130
//#         - 5                
//#endif                         
                        , (unCuarto*3)+unDecimo - unDecimo);
//#if TouchScreen!="true"
        //ktext.drawText(g, Define.finFase[4][Define.Language], sfont3, Define.SCR_WIDTH >> 1, (Define.SCR_HEIGHT) - unDecimo);        
//#elif TouchScreen=="true" && !s60dp5800 &&!bb9800 && ScreenHeight<640             
//#         ktext.drawText(g, Define.finFase[4][Define.Language], sfont3, Define.SCR_WIDTH >> 1, (Define.SCR_HEIGHT) - unDecimo);        
//#         ktext.drawText(g, Define.finFase[5][Define.Language], sfont3, Define.SCR_WIDTH >> 1, (Define.SCR_HEIGHT));
//#elif TouchScreen=="true" && s60dp5800 || bb9800 || ScreenHeight>=640
                Ktext.drawText(g, Define.finFase[4][Define.Language], sfont3, Define.SCR_WIDTH >> 1, (Define.SCR_HEIGHT) - unDecimo - (unDecimo>>1));        
                Ktext.drawText(g, Define.finFase[5][Define.Language], sfont3, Define.SCR_WIDTH >> 1, (Define.SCR_HEIGHT) - unDecimo);
//#endif        
        }
    }
    
    public void dukeMuere(Graphics g) {
        
        if (fotograma>=10) {pausaCorta(100);}
        
        if (fotograma<12 && frameNinja == 2) {
            fotograma+=1;
        }
        if (fotograma==12) {
        	Ktext.background(g, oscurecer);
            if (numeroVidas>0) {
            numeroVidas-=1;
//#if ScreenWidth!=176 && ScreenHeight!=416 && ScreenHeight!=480            
            Ktext.drawText(g, Define.menuMuerte[0][Define.Language], sfont3, (Define.SCR_WIDTH>>1)
//#elif ScreenHeight==416 || ScreenWidth==176 || ScreenHeight==480
//#             ktext.drawText(g, Define.menuMuerte[0][Define.Language], sfont3, (Define.SCR_WIDTH>>1)                             
//#endif                    
//#if ScreenWidth == 128 || ScreenWidth == 130
//#                     + (unDecimo>>1)
//#endif                     
                    , (Define.SCR_HEIGHT>>1) - (Define.SCR_HEIGHT>>4)
//#if ScreenWidth == 128 || ScreenWidth == 176 || ScreenHeight == 240 || ScreenWidth == 130 || ScreenHeight >= 400 || ScreenHeight == 298
                     - (Define.SCR_HEIGHT>>4)
//#endif                    
                    );
            Ktext.drawText(g, Define.menuMuerte[1][Define.Language]+numeroVidas+Define.menuMuerte[2][Define.Language], sfont3, Define.SCR_WIDTH>>1, (Define.SCR_HEIGHT>>1)
//#if ScreenWidth == 128 || ScreenWidth == 176 || ScreenWidth == 130 || ScreenHeight == 400
//#                     + (unDecimo>>1) + 5
//#endif                          
                    );
//#if TouchScreen!="true"            
            //ktext.drawText(g, Define.menuMuerte[3][Define.Language], sfont3, Define.SCR_WIDTH>>1, (Define.SCR_HEIGHT>>1) + unCuarto);
//#elif TouchScreen=="true" && !s60dp5800 && !bb9800 && ScreenHeight<400 && !ku990
         //g.setColor(0, 0, 0);
         //g.fillRect(0, alturaTablero, Define.SCR_WIDTH, Define.SCR_HEIGHT);
            
//#elif TouchScreen=="true" && s60dp5800 || bb9800 || ScreenHeight>=400 || ku990
         g.setColor(0, 0, 0);
         g.fillRect(0, posYTablero, Define.SCR_WIDTH, Define.SCR_HEIGHT);  
         
         Ktext.drawText(g, Define.menuMuerte[3][Define.Language], sfont3, Define.SCR_WIDTH >> 1, (Define.SCR_HEIGHT) - unDecimo - (unDecimo>>1));        
         Ktext.drawText(g, Define.menuMuerte[4][Define.Language], sfont3, Define.SCR_WIDTH >> 1, (Define.SCR_HEIGHT) - unDecimo);
//#         ktext.drawText(g, Define.menuMuerte[3][Define.Language], sfont3, Define.SCR_WIDTH >> 1, (Define.SCR_HEIGHT) - unDecimo - (unDecimo>>1));        
//#         ktext.drawText(g, Define.menuMuerte[4][Define.Language], sfont3, Define.SCR_WIDTH >> 1, (Define.SCR_HEIGHT) - unDecimo);
//#endif            
            pause=true;
            menuMuerte=true;
            } else {
                //stopMusica();
                pause=false;
                ESTADO = GAMEOVER;
                KitmakerAdsProviderManager.getManager().removeBanners();
                borrarGraficos();
                cargarGraficos();
                KitmakerAdsProviderManager.getManager().removeBanners();
                //cargarCanciones(explosion);
                //comenzarMusica(explosion, false);
                SndManager.PlayMusic(SndManager.SFX_EXPLOSION, false);
                numeroVidas=-1;
            }
        }
    }
    
    public void finalAnimado(Graphics g) {
        
        cargarGraficos(); //Tranquilo. SÃ³lo se ejecuta una vez.
        KitmakerAdsProviderManager.getManager().removeBanners();
        //Fondo degradado
        g.setColor(176, 81, 8);
        g.fillRect(0, 0, Define.SCR_WIDTH, Define.SCR_HEIGHT>>1);
        g.setColor(219, 179, 104);
        g.fillRect(0, Define.SCR_HEIGHT>>1, Define.SCR_WIDTH, Define.SCR_HEIGHT); 
        for (int i = 0; i < 50; i += 2) {
            g.setColor(219 - (i), 179 - (i * 2), 104 - (i * 2));
            g.fillRect(0, (Define.SCR_HEIGHT >> 1) - (i * 4), Define.SCR_WIDTH, Define.SCR_HEIGHT / 10);
        }
        //g.fillRect(0, 0, Define.SCR_WIDTH, Define.SCR_HEIGHT);
        //Sumador.
        PosXtemp1 -= 1;
        PosYtemp1 += 2 //Velocidad del scroll
//#if ScreenWidth==128 || ScreenWidth == 130
//#         - 1        
//#endif                
                ; 
        if (PosXtemp1 <= (fondoCielo1.getWidth() * (-1))) {
            PosXtemp1 = 0;
            estadoTemp += 1;
        }
        if (PosYtemp1 == 2) {
            PosYtemp1 = 0;
            PosYtemp2 += 1;
        }
                                                        //ZONA DE CONFLICTO AL CREAR UNA RESOLUCION NUEVA//        
        
//#if ScreenHeight==320      
        if (estadoTemp >= 4) {
//#elif ScreenHeight==416 || ScreenHeight==480 || ScreenHeight>=640 || ScreenHeight==299 || ScreenHeight==297 || ScreenHeight==298 || ScreenHeight==304    
//#         if (estadoTemp >= 4) {        
//#elif ScreenHeight==240 || ScreenHeight==260 || ScreenHeight==360 || ScreenHeight==400        
//#         if (estadoTemp >= 4) {         
//#elif ScreenHeight==208 || ScreenHeight==205 || ScreenHeight==204       
//#         if (estadoTemp >= 4) {          
//#elif ScreenHeight==160 || ScreenHeight==147 || ScreenHeight==149        
//#         if (estadoTemp >= 6) {
//#elif ScreenHeight==128 || ScreenHeight==130        
//#         if (estadoTemp >= 6) {
//#endif            
            
            PosXtemp1 -= 3;
            PosYtemp2 += 2;
            PosXtemp2 += 2;
        } //Esto hace saltar el turbo
        if (estadoTemp >= 9) {                            //Salta al menÃº principal
            ESTADO = MENU;
            KitmakerAdsProviderManager.getManager().addBanners();
            KitmakerAdsProviderManager.getManager().displayInterstitial();
            FASEAJUGAR = FASE33;
            g.setColor(255, 255, 255);
            g.fillRect(0, 0, Define.SCR_WIDTH, Define.SCR_HEIGHT);
            borrarGraficos(); //Ponemos los valores a cero y ahorramos memoria grÃ¡fica
            cargarGraficos();
            KitmakerAdsProviderManager.getManager().addBanners();
            
            for (int i = 0; i < Ninja2PosX.length; i++) {
                ninjaPosXeditable2[i] = 0;   //Reset de posición de los ninjas con Espada
            }          
            resetJuego();
            
            
            SndManager.StopMusic();
            SndManager.PlayMusic(SndManager.MUSIC_MAINMENU, true);
            if (partidaGuardada == false) {
                opcionSelect = 1;
            }  //Si no hay partida guardada.
            return;
        }
        //

        g.drawImage(fondoCielo1, PosXtemp1, Define.SCR_HEIGHT, Graphics.BOTTOM | Graphics.LEFT);
        g.drawImage(fondoCielo1, PosXtemp1 + fondoCielo1.getWidth(), Define.SCR_HEIGHT, Graphics.BOTTOM | Graphics.LEFT);
        g.drawImage(fondoCielo1, PosXtemp1 + fondoCielo1.getWidth() * 2, Define.SCR_HEIGHT, Graphics.BOTTOM | Graphics.LEFT);

        //Los textos
        Ktext.drawText(g, Define.ending[0][Define.Language], sfont3, Define.SCR_WIDTH / 2, Define.SCR_HEIGHT - PosYtemp2);
        Ktext.drawText(g, Define.ending[1][Define.Language], sfont3, Define.SCR_WIDTH / 2, Define.SCR_HEIGHT - PosYtemp2 + (Define.SeparacionEntreLineasIntro));
        Ktext.drawText(g, Define.ending[2][Define.Language], sfont3, Define.SCR_WIDTH / 2, Define.SCR_HEIGHT - PosYtemp2 + (Define.SeparacionEntreLineasIntro * 2));
        Ktext.drawText(g, Define.ending[3][Define.Language], sfont3, Define.SCR_WIDTH / 2, Define.SCR_HEIGHT - PosYtemp2 + (Define.SeparacionEntreLineasIntro * 3));
        Ktext.drawText(g, Define.ending[4][Define.Language], sfont3, Define.SCR_WIDTH / 2, Define.SCR_HEIGHT - PosYtemp2 + (Define.SeparacionEntreLineasIntro * 4));
        Ktext.drawText(g, Define.ending[5][Define.Language], sfont3, Define.SCR_WIDTH / 2, Define.SCR_HEIGHT - PosYtemp2 + (Define.SeparacionEntreLineasIntro * 5));
        Ktext.drawText(g, Define.ending[6][Define.Language], sfont3, Define.SCR_WIDTH / 2, Define.SCR_HEIGHT - PosYtemp2 + (Define.SeparacionEntreLineasIntro * 6));
        Ktext.drawText(g, Define.ending[7][Define.Language], sfont3, Define.SCR_WIDTH / 2, Define.SCR_HEIGHT - PosYtemp2 + (Define.SeparacionEntreLineasIntro * 7));
        Ktext.drawText(g, Define.ending[8][Define.Language], sfont3, Define.SCR_WIDTH / 2, Define.SCR_HEIGHT - PosYtemp2 + (Define.SeparacionEntreLineasIntro * 8));
        Ktext.drawText(g, Define.ending[9][Define.Language], sfont3, Define.SCR_WIDTH / 2, Define.SCR_HEIGHT - PosYtemp2 + (Define.SeparacionEntreLineasIntro * 9));
        Ktext.drawText(g, Define.ending[10][Define.Language], sfont3, Define.SCR_WIDTH / 2, Define.SCR_HEIGHT - PosYtemp2 + (Define.SeparacionEntreLineasIntro * 10));
        Ktext.drawText(g, Define.ending[11][Define.Language], sfont3, Define.SCR_WIDTH / 2, Define.SCR_HEIGHT - PosYtemp2 + (Define.SeparacionEntreLineasIntro * 11));
        Ktext.drawText(g, Define.ending[12][Define.Language], sfont3, Define.SCR_WIDTH / 2, Define.SCR_HEIGHT - PosYtemp2 + (Define.SeparacionEntreLineasIntro * 12));
        Ktext.drawText(g, Define.ending[13][Define.Language], sfont3, Define.SCR_WIDTH / 2, Define.SCR_HEIGHT - PosYtemp2 + (Define.SeparacionEntreLineasIntro * 13));
        Ktext.drawText(g, Define.ending[14][Define.Language], sfont3, Define.SCR_WIDTH / 2, Define.SCR_HEIGHT - PosYtemp2 + (Define.SeparacionEntreLineasIntro * 14));

        //Transparencia
//#if TRANSPARENCIAS=="true"        
//#         g.drawImage(fondoCieloTrans, PosXtemp1, Define.SCR_HEIGHT, Graphics.BOTTOM | Graphics.LEFT);
//#         g.drawImage(fondoCieloTrans, PosXtemp1 + fondoCielo1.getWidth(), Define.SCR_HEIGHT, Graphics.BOTTOM | Graphics.LEFT);
//#         g.drawImage(fondoCieloTrans, PosXtemp1 + fondoCielo1.getWidth() * 2, Define.SCR_HEIGHT, Graphics.BOTTOM | Graphics.LEFT);
//#endif        
        //Dibujo del prota

        g.drawImage(duke_intro, PosXtemp2 - duke_intro.getWidth(), Define.SCR_HEIGHT, Graphics.BOTTOM | Graphics.LEFT);

        /*
        //Efectos de flash
        if (PosXtemp2 >= 1 && PosXtemp2 <= 4) {
            g.setColor(255, 255, 255);
            g.fillRect(0, 0, Define.SCR_WIDTH, Define.SCR_HEIGHT);
        } */ 
    }
    
    public void gameover(Graphics g) {
        //Reseteo de valores.
                    partidaIniciada = false;
                    dukeMuere = false;
                    scrollX=0;
                    tiempoMuerto=0;
                    menuMuerte=false;
                    for (int i=0;i<Ninja2Frame.length;i++)
                    Ninja2Frame[i]=0;        
        PosXtemp1++;
        PosXtemp2++;
        PosYtemp1++;
        //Temporizador que controla el destello
        if (PosXtemp2==2 && PosXtemp1<50) {
            PosXtemp2=0;
        }
        //Temblor de la bomba.
        if (PosYtemp1==4) {
            PosYtemp1=0;
        }
        g.drawImage(abomb, 0, (-1)*PosYtemp1, 0);
        //Efectos de flash
        if (PosXtemp2 >= 1 && PosXtemp2 <= 1) {
            g.setColor(255, 255, 255);
            g.fillRect(0, 0, Define.SCR_WIDTH, Define.SCR_HEIGHT);
        }
        if (PosXtemp1>50) {
        	Ktext.drawText(g, "game", sfont1, Define.SCR_WIDTH>>1, (Define.SCR_HEIGHT>>1) - (unDecimo*2));
        	Ktext.drawText(g, "over", sfont1, Define.SCR_WIDTH>>1, (Define.SCR_HEIGHT>>1) - unDecimo);
//#if TouchScreen!="true"        
        //ktext.drawText(g, Define.menuMuerte[3][Define.Language], sfont3, Define.SCR_WIDTH>>1, (Define.SCR_HEIGHT>>1) + unCuarto);
//#elif TouchScreen=="true"
        	Ktext.drawText(g, Define.menuMuerte[3][Define.Language], sfont3, Define.SCR_WIDTH >> 1, (Define.SCR_HEIGHT) - unDecimo - (unDecimo>>1));        
        	Ktext.drawText(g, Define.menuMuerte[4][Define.Language], sfont3, Define.SCR_WIDTH >> 1, (Define.SCR_HEIGHT) - unDecimo);
//#endif        
        }
        //repaint();
    }
    
    public void pasafotograma() {

        switch (animacion) {
            case PARADO:
                fotograma = 1;
                break;
            case CORRIENDO:
                if (fotograma == 8 || fotograma == 5 || fotograma >= 13) {
                    fotograma = 0;
                } //Reinicia los framse si se ejecutÃ³ la patada.

                velocidadAnimacion++;
                if (velocidadAnimacion == velocidadMaxAnimacion) {
                    velocidadAnimacion = 0;
                }
                if (fotograma < 5 && velocidadAnimacion == 1) {
                    fotograma++;
                }
                if (fotograma == 4) {
                    fotograma = 0;
                }
                break;
            case PATADA:
                //if (fotograma==6) {comenzarMusica(sonidoFX);}
                velocidadAnimacion++;
                if (COMBO == 0) {
                if (velocidadAnimacion == (velocidadMaxAnimacion + 1)) {
                    velocidadAnimacion = 0;
                }
                if (fotograma < 17 && velocidadAnimacion == 1) {
                    fotograma++;
                }
                if (fotograma == 16) {
                    pressFight = false;
                    animacion = PARADO;
                    velocidadAnimacion = 0;
                    COMBO = 1;
                }
                return;
                }
                if (COMBO == 1) {
                if (velocidadAnimacion == (velocidadMaxAnimacion + 1)) {
                    velocidadAnimacion = 0;
                }
                if (fotograma < 8 && velocidadAnimacion == 1) {
                    fotograma++;
                }
                if (fotograma == 8) {
                    pressFight = false;
                    animacion = PARADO;
                    velocidadAnimacion = 0;
                    COMBO = 0;
                }
                return;
                }
                break;
        }
    }

/////////////////////////////////COLISIONES/////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////    
    public void colision() {

        //Colision con las bombas
        for (int i = 0; i < Bomba.length; i++) {
            if (Bomba[i] >= 1) {
                if (Bomba[i] == 1) {
                    bombaPosY = Define.SCR_HEIGHT - alturaSuelo - alturaDinamita;
                }
                if (Bomba[i] == 2) {
                    bombaPosY = plataformaMetalicaY1 - alturaDinamita;
                }
                if (Bomba[i] == 3) {
                    bombaPosY = plataformaMetalicaY2 - alturaDinamita;
                }
                if (dukePosX > (i * anchuraPlataforma - scrollX) - (anchuraPlataforma << 1) + (anchuraPlataforma >> 1)
                        && dukePosX < (i * anchuraPlataforma - scrollX) + (anchuraPlataforma) - (anchuraPlataforma >> 1)
                        && dukePosY < bombaPosY + alturaDinamita
                        && dukePosY + alturaDuke > bombaPosY) {
                    if (Bomba[i] == 1) {
                        Bomba[i] = -1;
                    } //Con esta tÃ©cnica obtenemos la pociÃ³n que tenÃ­a la bomba.
                    if (Bomba[i] == 2) {
                        Bomba[i] = -2;
                    }
                    if (Bomba[i] == 3) {
                        Bomba[i] = -3;
                    }
                    numeroBombas += 1;
                    SndManager.PlayFX(SndManager.FX_BOMBADESAC);
                }
            }
        }

        //ColisiÃ³n con la plataforma metÃ¡lica 1.
        for (int i = 0; i < mapaMetalicas1.length; i++) {
            if (mapaMetalicas1[i] == 1) {
                //CorreciÃ³n de solapaciÃ³n.  
                if (dukePosY > plataformaMetalicaY1 - alturaDuke
                        && dukePosY < plataformaMetalicaY1 - alturaDuke + 5 //1 pixel menos que la Colision real
//#if ScreenHeight>=480
//#                         + 10
//#endif                          
                        && dukePosX < (i * anchuraPlataforma - scrollX) + anchuraPlataforma - 16 //1 pixel mÃ¡s que la Colision real
                        && dukePosX > (i * anchuraPlataforma - scrollX) - anchuraPlataforma - 5 //1 pixel menos que la Colision real
                        ) {
                    dukePosY = plataformaMetalicaY1 - alturaDuke;
                    fotograma = 1;
                }

                if (duke.collidesWith(plataformaMetalica, (i * anchuraPlataforma - scrollX), plataformaMetalicaY1, false)
                        && dukePosY < plataformaMetalicaY1 - alturaDuke + 4 //Forma elegante de reducir la zona de colisiÃ³n ;-)
//#if ScreenHeight>=480
//#                         + 10
//#endif                        
                        && dukePosX < (i * anchuraPlataforma - scrollX) + anchuraPlataforma - 15
                        && dukePosX > (i * anchuraPlataforma - scrollX) - anchuraPlataforma - 6) {
                    colision = true;
                    return;
                }
            }
        }
        //ColisiÃ³n con la plataforma metÃ¡lica 2.
        for (int i = 0; i < mapaMetalicas2.length; i++) {
            if (mapaMetalicas2[i] == 1) {
                //CorreciÃ³n de solapaciÃ³n.  
                if (dukePosY > plataformaMetalicaY2 - alturaDuke
                        && dukePosY < plataformaMetalicaY2 - alturaDuke + 5 //1 pixel menos que la Colision real
                        && dukePosX < (i * anchuraPlataforma - scrollX) + anchuraPlataforma - 16 //1 pixel mÃ¡s que la Colision real
                        && dukePosX > (i * anchuraPlataforma - scrollX) - anchuraPlataforma - 5 //1 pixel menos que la Colision real
                        ) {
                    dukePosY = plataformaMetalicaY2 - alturaDuke;
                    fotograma = 1;
                }

                if (duke.collidesWith(plataformaMetalica, (i * anchuraPlataforma - scrollX), plataformaMetalicaY2, false)
                        && dukePosY < plataformaMetalicaY2 - alturaDuke + 4 //Forma elegante de reducir la zona de colisiÃ³n ;-)
                        && dukePosX < (i * anchuraPlataforma - scrollX) + anchuraPlataforma - 15
                        && dukePosX > (i * anchuraPlataforma - scrollX) - anchuraPlataforma - 6) {
                    colision = true;
                    return;
                }
            }
        }
        
        //ColisiÃ³n con los Ninjas1
        //Patada en el Ninja1
        
        for (int i=0;i<NinjaPosX.length;i++) {
        if (direccionDuke==true //Duke a la derecha.
                && (duke.getFrame()==7 || duke.getFrame()==15 || duke.getFrame()==18) //Patada y puÃ±o o patada aerea.
                && frameNinjaFinal[i] < 6 //El ninja no estÃ¡ muerto.
                && ninjaPosXeditable[i] - scrollX > dukePosX
                && ninjaPosXeditable[i] - scrollX < dukePosX + anchuraDuke - (anchuraDuke/2)
                && dukePosY>=ninja1PoxY-alturaDuke
                ) {
            if (Ninja1Vida[i]!=numerGolpesMatarNinja1) { //Recibe un toque.
                ninjaPosXeditable[i]+=anchuraDuke-(anchuraDuke>>1);
                Ninja1Vida[i]++;
                sninja1muertoIzq.setTransform(Sprite.TRANS_MIRROR);
                frameNinjaFinal[i]=9;
                tiempoGolpe=0;
                SndManager.PlayFX(SndManager.FX_GOLPE);

            } else {
            sninja1muertoIzq.setTransform(Sprite.TRANS_MIRROR);
            direccionNinja[i]=false; //Mira a la izquierda.
            frameNinjaFinal[i]=6;
            }
        }
        if (direccionDuke==false //Duke a la izquierda.
                && (duke.getFrame()==7 || duke.getFrame()==15 || duke.getFrame()==18) //Patada y puÃ±o o patada aerea.
                && frameNinjaFinal[i] < 6 //El ninja no estÃ¡ muerto.
                && ninjaPosXeditable[i] - scrollX < dukePosX
                && ninjaPosXeditable[i] - scrollX > dukePosX - anchuraDuke + (anchuraDuke/2)
                && dukePosY>=ninja1PoxY-alturaDuke
                ) {
            if (Ninja1Vida[i]!=numerGolpesMatarNinja1) { //Recibe un toque.
                ninjaPosXeditable[i]-=anchuraDuke-(anchuraDuke>>1);
                Ninja1Vida[i]++;
                sninja1muertoIzq.setTransform(Sprite.TRANS_NONE);
                frameNinjaFinal[i]=9;
                tiempoGolpe=0;
                SndManager.PlayFX(SndManager.FX_GOLPE);

            } else {            
            direccionNinja[i]=true;//Mira a la derecha.
            frameNinjaFinal[i]=6;
            }
        }
        //PuÃ±etazo a Duke
        if (ninjaPosXeditable[i] - scrollX > dukePosX - (anchuraDuke>>2)
                && ninjaPosXeditable[i] - scrollX < dukePosX + (anchuraDuke>>2)
                && dukePosY >= Define.SCR_HEIGHT - alturaSuelo - alturaDuke
                && frameNinjaFinal[i]<5
                && godMode==false
                ) {
            frameNinjaFinal[i]=5;
            fotograma=9; //Duke muere. Fotograma de Duke muriendo.
        }
        }
        
        //ColisiÃ³n contra el suelo.   
        if (dukePosY >= Define.SCR_HEIGHT - alturaSuelo - alturaDuke) {
            dukePosY = Define.SCR_HEIGHT - alturaSuelo - alturaDuke;
            colision = true;
            if (fotograma == 18) {fotograma = 0;}
            return;
        }


        //GRAVEDAD. Duke cae si no se produce ninguna colisiÃ³n gracias al RETURN y si Duke no estÃ¡ saltando.
        if (salto == false) {
            if (patadaAerea==false) {
            fotograma = 5;
            } else {
                fotograma = 18;
            }
            colision = false;
        }
    }

///////FIN PINTADO DEL JUEGO//////////////////////////////////////////////////////    
    public void pausa(byte estado, int time) {
        //"estado" es el estado siguiente al que salta.
        //"time" es el tiempo de espera.
        tiempo += 1;
        if (tiempo > (time / 1000)) {
            try {
                Thread.sleep(time);
            } catch (Exception e) {
            }
            borrarGraficos();
            ESTADO = estado;
            tiempo = 0;
        }
    }
        public void pausaCorta(int time) {
        //"time" es el tiempo de espera.

            try {
                Thread.sleep(time);
            } catch (Exception e) {
            }
            tiempo = 0;
        }

    public void botonesAnimados(Graphics g, int posY) {

//#if TouchScreen!="true"        
        if (estadoTemp == 0) {
            PosXtemp1 += 6;
        }
        if (PosXtemp1 == 12) {
            estadoTemp = 1;
        } //Controla el avance mÃ¡ximo de los puÃ±os.
        if (estadoTemp == 1) {
            PosXtemp1 -= 1;
        }
        if (PosXtemp1 == 0) {
            estadoTemp = 0;
        }
        //sbotones.setTransform(sbotones.TRANS_NONE);
        //sbotones.setPosition((unDecimo >> 1) + PosXtemp1 - sbotones.getWidth(), posY);
        //sbotones.paint(g);
        //sbotones.setPosition((Define.SCR_WIDTH - (unDecimo >> 1)) - PosXtemp1 + sbotones.getWidth(), posY);
        //sbotones.setTransform(sbotones.TRANS_MIRROR);
        //sbotones.paint(g);
//#endif        
    }  

    public void resetJuego() {
        partidaIniciada=false;
        numeroVidas=3;
        numeroBombas=0;
        //Resteo de las bombas activas.
        
        //Reseteo de la posiciÃ³n de los Ninjas
        //Hay que poner un IF para que que resetee segÃºn las fases.                    IMPORTANTE
        for (int i = 0; i < NinjaPosX.length; i++) {
             ninjaPosXeditable[i] = NinjaPosX[i];
            }
        for (int i = 0; i < Ninja2PosX.length; i++) {
            ninjaPosXeditable2[i] = 0;   //Reset de posición de los ninjas con Espada
        } 
        //Reset Tiempo Bomba.
        tiempoBomba=1000;
        //Reset de la activaciÃ³n de las bomas.
        for (int i = 0; i < Bomba.length; i++) {
             Bomba[i] = BombaReset[i];
            }
    }
    
    public void controlFPS() {
        /*try {
            currentTime = System.currentTimeMillis();
            if( timeFPS > 0){
                if(currentTime  < timeFPS + (1000/30)){
                    Thread.sleep((int)((1000/30)-(currentTime-timeFPS))); //Los FPS podrÃ­as ponerlos en el Define
                }
            }
            timeFPS = currentTime;
        } catch (Exception ex) {
            ex.printStackTrace();
        }*/
        
        ms_lCurrentTime = System.currentTimeMillis();
        ms_iFrameTicks = (int) ((ms_lCurrentTime - ms_lLastFrameTime) / ms_iMillisPerTick);

	// Frame skip limit: Prevents the action from getting too jerky
	// and softens potential sudden performance halts (due to garbage collecting, etc)
		if (ms_iFrameTicks > 5)
					ms_iFrameTicks = 5;
//					System.out.println("Pausa de FPS 1");

		if (ms_iFrameTicks == 0) {
					ms_iFrameTicks = 1;
					while ((ms_lTickTime = (System.currentTimeMillis() - ms_lLastFrameTime)) < ms_iMillisPerTick) {
						Thread.yield();
//						System.out.println("Pausa de FPS 2");
					}
				}
		ms_lLastFrameTime += ms_iFrameTicks * ms_iMillisPerTick; 
        
        
    }

    /**
     * Called when a key is pressed.
     */
    protected void keyPressed(int keyCode) {

        debugKeycode = keyCode;  //Para testear los keycodes

////////////////////////////////////////////////MENU INICIAL IDIOMA
       if (ESTADO == MENUIDIOMAINIC) {
           switch (keyCode) {
               case KEYCODE_DOWN:
               case KEYCODE_NUM8:
                   if (opcionSelect<Define.NumeroIdiomas-1) {
                       opcionSelect++;       
                   }
                   break;
               case KEYCODE_UP:
               case KEYCODE_NUM2:
                   if (opcionSelect>0) {
                       opcionSelect--;
                   }
                   break;
               case KEYCODE_FIRE:
               case KEYCODE_NUM5:
                   if (opcionSelect==0) {
                   destroyLanguage(); //Borra la partida anterior. No se pueden editar.
                   iniciarLenguaje(); //Crea una nueva vacÃ­a.
                   writeLanguage("ingles");  //Crossword es el nombre de la Class.
                   Define.Language=0;                          //Es ahora cuando se reselecciona el idioma.
                   borrarGraficos();
                   ESTADO=MENUSONIDOINIC;
                   }
                   if (opcionSelect==1) {
                   destroyLanguage(); //Borra la partida anterior. No se pueden editar.
                   iniciarLenguaje(); //Crea una nueva vacÃ­a.
                   writeLanguage("espanol");  //Crossword es el nombre de la Class.
                   Define.Language=1;                          //Es ahora cuando se reselecciona el idioma.
                   borrarGraficos();
                   ESTADO=MENUSONIDOINIC;
                   }
                   break;
           }return;
       }
////////////////////////////////////////////////MENU INICIAL SONIDO
       if (ESTADO == MENUSONIDOINIC) {
           switch (keyCode) {
               case KEYCODE_DOWN:
               case KEYCODE_NUM8:
                   if (opcionSelect<Define.NumeroIdiomas-1) {
                       opcionSelect++;       
                   }
                   break;
               case KEYCODE_UP:
               case KEYCODE_NUM2:
                   if (opcionSelect>0) {
                       opcionSelect--;
                   }
                   break;
               case KEYCODE_FIRE:
               case KEYCODE_NUM5:
                   if (opcionSelect==0) {
                       kanforSound=false; //No hay sonido.
                   }
                   if (opcionSelect==1) {
                       kanforSound=true; //No hay sonido.
                   }
                   borrarGraficos();
                   ESTADO=MENUANIMADO;
                   //Iniciamos carga del lenguaje.
                   iniciarLenguaje();
                   iniciarMemoryCard();
                   //Iniciamos carga de la partida.
                   iniciarMemoryCard();
                   break;                   
           }return;
       }
////////////////////////////////////////////////MENU PAUSA
       if (pause == true && menuMuerte==false && menuFinFase==false) {
           switch (keyCode) {
               case KEYCODE_DOWN:
               case KEYCODE_NUM8:
                   if (opcionSelect<2) { //El 2+1 son las opciones del Menu Pausa.
                       opcionSelect++;       
                   }
                   break;
               case KEYCODE_UP:
               case KEYCODE_NUM2:
                   if (opcionSelect>0) {
                       opcionSelect--;
                   }
                   break;
               case KEYCODE_FIRE:
               case KEYCODE_NUM5:
               case KEYCODE_SK_RIGHT:
                   if (opcionSelect==0) { //Continuar
                        opcionSelect=0;
                        pause=false;
                        pressDown=false;
                        pressUp=false;
                        pressLeft=false;
                        pressRight=false;
                   	 	SndManager.PlayMusic(SndManager.MUSIC_GAMEPLAY1, true);
                        
                   }
                   if (opcionSelect==1) { //Salir al menu
                       ESTADO = MENU;
                       borrarGraficos();
                       cargarGraficos();
                       KitmakerAdsProviderManager.getManager().addBanners();
                       resetJuego(); //Reinicio los valores de las fases.
                  	 	SndManager.PlayMusic(SndManager.MUSIC_MAINMENU, true);
                   }
                   if (opcionSelect==2) { //Salir del juego
                       stopMusica();
                       MidletFinalKombat.quitApp();
                   }
                   break;
           }return;
       }         
/////////////////////////////////////////////////        
        if (ESTADO == MENUANIMADO && duke_intro != null) {
            switch (keyCode) {
                case KEYCODE_FIRE:
                case KEYCODE_NUM5:
                    PosXtemp1 = 0;
                    PosYtemp1 = 0;        //Ponemos los valores a cero y ahorramos memoria grÃ¡fica.
                    PosXtemp2 = 0;
                    PosYtemp2 = 0;
                    estadoTemp = 0;
                    fondoCielo1 = null;
                    ESTADO = MENU;
                    KitmakerAdsProviderManager.getManager().addBanners();
                    borrarGraficos();
                    if (partidaGuardada == false) {
                        opcionSelect = 1;
                    }  //Si no hay partida guardada.
                    break;
            }
            return;
        }
///////////////////////////////////////////////MENU        
        if (ESTADO == MENU && submenu == false && mostrarAdvertencia==false) {  //Menu normal
            switch (keyCode) {
                case KEYCODE_NUM8:
                case KEYCODE_DOWN:
                    if (opcionSelect < 5) //4+1 es el nÃºmero de opciones del menÃº.
                    {
                    	SndManager.PlayFX(SndManager.FX_SELECT);
                        opcionSelect += 1;
                    }
                    break;
                case KEYCODE_NUM2:
                case KEYCODE_UP:
                    if (opcionSelect > 0) //4 es el nÃºmero de opciones del menÃº.
                    {
                    	SndManager.PlayFX(SndManager.FX_SELECT);
                        opcionSelect -= 1;
                    }
                    if (partidaGuardada == false && opcionSelect == 0) {
                        opcionSelect = 1;
                    } //Impide activar CONTINUE si no hay partida guardada.                
                    break;
                case KEYCODE_FIRE:
                case KEYCODE_NUM5:
                    if (opcionSelect == 0) {
                        opcionSelect = 0;
                        borrarGraficos();

                        //stopMusica(); //Nextel

                        ESTADO = JUGAR;
                        KitmakerAdsProviderManager.getManager().removeBanners();
                        //Cargamos la memorycard.
                        iniciarMemoryCard();
                        //No ponemos FASEAJUGAR porque ya queda registrado en la memoria al cargar la Memory Card.
                        break;
                    }
                    if (opcionSelect == 1) {
                        if (partidaGuardada==true) {
                            
                            mostrarAdvertencia=true;
                            
                        } else {                       
                        opcionSelect = 0;
                        borrarGraficos();
                        ESTADO = JUGAR;
                        KitmakerAdsProviderManager.getManager().removeBanners();
                        //stopMusica(); //Nextel
                        
                        partidaGuardada=false;
                        FASEAJUGAR = FASE11; //AquÃ­ se indica la fase a jugar. Al ser New Game siempre es la primera fase. HACK.
                        }
                        //Falta borrar la partida.
                        
                        break;
                    }
                    if (opcionSelect == 2) { //OPCIONES
                        opcionSelect = 0;
                        submenu = true;
                        break;
                    }
                    if (opcionSelect == 3) { //INSTRUCCIONES
                        ESTADO = INSTRUCCIONES;
                        KitmakerAdsProviderManager.getManager().addBanners();
                        break;
                    }
                    if (opcionSelect == 4) { //ABOUT
                        ESTADO = ABOUT;
                        KitmakerAdsProviderManager.getManager().addBanners();
                        break;
                    }
                    if (opcionSelect == 5) { //SALIR
                        stopMusica();
                        MidletFinalKombat.quitApp();
                        break;
                    }
                    break;
            }
            return; //Muy importante. Es para que el keycode no active las otras opciones.
        }
////////////////////////////////////////////ADVERTENCIA DE BORRADO DE PARTIDA
        
        if (mostrarAdvertencia==true) {
            switch (keyCode) {
               case KEYCODE_DOWN:
               case KEYCODE_NUM8:
                   if (opcionSelect<Define.NumeroIdiomas-1) {
                       opcionSelect++;       
                   }
                   break;
               case KEYCODE_UP:
               case KEYCODE_NUM2:
                   if (opcionSelect>0) {
                       opcionSelect--;
                   }
                   break;
               case KEYCODE_FIRE:
               case KEYCODE_NUM5:
                   if (opcionSelect==0) {
                       destroyMemoryCard(); //BORRA la partida.
                       iniciarMemoryCard();
                       writeMemoryCard("fase11");
                       mostrarAdvertencia=false;
                       opcionSelect=1;
                       partidaGuardada=false;  //BORRA la partida.
                       resetJuego();
                   }
                   if (opcionSelect==1) {
                       mostrarAdvertencia=false;
                   }
                   break;                   
           }return;
        }
        
////////////////////////////////////////////SUBMENU-OPCIONES    
        if (ESTADO == MENU && submenu == true) {    //Submenu del menu
            switch (keyCode) {
                case KEYCODE_NUM8:
                case KEYCODE_DOWN:
                    if (opcionSelect < 2) //4+1 es el nÃºmero de opciones del menÃº.
                    {
                        //comenzarMusica(sonidoFX2, false);
                        opcionSelect += 1;
                    }
                    break;
                case KEYCODE_NUM2:
                case KEYCODE_UP:
                    if (opcionSelect > 0) //4 es el nÃºmero de opciones del menÃº.
                    {
                        //comenzarMusica(sonidoFX2, false);
                        opcionSelect -= 1;
                    }
                    break;
                case KEYCODE_FIRE:
                case KEYCODE_NUM5:
                    if (opcionSelect == 0) {
                        kanforSound = !kanforSound;
                        stopMusica();
                        if (kanforSound==true) {
                        	SndManager.PlayMusic(SndManager.MUSIC_MAINMENU, true);
                        }
                        break;
                    }
                    if (opcionSelect == 1) {
                        if (Define.Language == 0) {
                            destroyLanguage(); //Borra la partida anterior. No se pueden editar.
                            iniciarLenguaje(); //Crea una nueva vacÃ­a.
                            writeLanguage("espanol");  //Crossword es el nombre de la Class.
                            Define.Language=1;                          //Es ahora cuando se reselecciona el idioma.
                            return;
                        }
                        if (Define.Language == 1) {
                            destroyLanguage(); //Borra la partida anterior. No se pueden editar.
                            iniciarLenguaje(); //Crea una nueva vacÃ­a.
                            writeLanguage("ingles");  //Crossword es el nombre de la Class.
                            Define.Language=0;
                            return;
                        }
                        break;
                    }
                    if (opcionSelect == 2) {
                        submenu = false;
                        break;
                    }
            }
            return;
        }
////////////////////////////////////////////ABOUT        
        if (ESTADO == ABOUT) {
            switch (keyCode) {
                case KEYCODE_FIRE:
                case KEYCODE_NUM5:
                    ESTADO = MENU;
                    KitmakerAdsProviderManager.getManager().addBanners();
                    break;
            }
            return;
        }
////////////////////////////////////////////INSTRUCCIONES        
        if (ESTADO == INSTRUCCIONES) {
            switch (keyCode) {
                case KEYCODE_NUM8:
                case KEYCODE_DOWN:
                    if (PosYtemp2 >= -100) {
                        PosYtemp2 -= 10;
                    }
                    break;
                case KEYCODE_NUM2:
                case KEYCODE_UP:
                    if (PosYtemp2 <= -10) {
                        PosYtemp2 += 10;
                    }
                    break;
                case KEYCODE_FIRE:
                case KEYCODE_NUM5:
                    PosYtemp2 = 0;
                    ESTADO = MENU;
                    KitmakerAdsProviderManager.getManager().addBanners();
                    break;
            }
            return;
        }
///////////////////////////////////////////JUEGO
        if (FASEAJUGAR != 0 && partidaIniciada == true && pause == false && dukeMuere == false) {
            switch (keyCode) {
                case KEYCODE_NUM2:
                case KEYCODE_UP:
                    if (colision == true) { //Impide un doble salto.
                        pressUp = true;
                        salto = true;
                    }
                    break;
                case KEYCODE_NUM4:
                case KEYCODE_LEFT:
                    pressLeft = true;
                    velocidadAnimacion = 0; //Resetea el valor ;-)
                    break;
                case KEYCODE_NUM6:
                case KEYCODE_RIGHT:
                    pressRight = true;
                    velocidadAnimacion = 0; //Resetea el valor ;-)
                    break;
                case KEYCODE_DOWN:
                case KEYCODE_NUM8:
                    pressDown = true;
                case KEYCODE_FIRE:
                case KEYCODE_NUM5:
                    if (fotograma == 5 && patadaAerea==false) { //PATADA AEREA.
                        patadaAerea = true;
                    }
                    if (pressFight == false && fotograma != 5 && fotograma !=18) {
                        if (COMBO == 0) {
                        fotograma = 14 - 1; //PUÃ‘ETAZO. Importante. Pone el programa inicial en 6. Menos 1 porque suma 1 al iniciarse la animaciÃ³n.
                        } else {
                        fotograma = 6 - 1; //PATADA.    
                        }
                        pressFight = true;
                        velocidadAnimacion = 0; //Resetea el valor ;-)
                    }
                    break;
                case KEYCODE_SK_RIGHT:
                    pause = true;
                    stopMusica();
                    break;
            }
            return;
        }
//////////////////////////////////////////MENU MUERTE
        if (pause == true && menuMuerte==true) {
            switch (keyCode) {
                case KEYCODE_FIRE:
                case KEYCODE_NUM5:
                    if (tiempoMuerto>20) {
                    pause = false;
                    partidaIniciada = false;
                    dukeMuere = false;
                    scrollX=0;
                    tiempoMuerto=0;
                    menuMuerte=false;
                    for (int i=0;i<Ninja2Frame.length;i++)
                    Ninja2Frame[i]=0;
                    }
                    break;
            }return;
        }
//////////////////////////////////////////MENU GAME OVER
        if (ESTADO == GAMEOVER) {
            switch (keyCode) {
                case KEYCODE_FIRE:
                case KEYCODE_NUM5:
                    if (PosXtemp1>50) 
                    {
                    pause = false;
                    partidaIniciada = false;
                    dukeMuere = false;
                    scrollX=0;
                    tiempoMuerto=0;
                    menuMuerte=false;
                    //for (int i=0;i<Ninja2Frame.length;i++)
                    //Ninja2Frame[i]=0;
                    ESTADO = MENU;
                    borrarGraficos();
                    cargarGraficos();
                    KitmakerAdsProviderManager.getManager().addBanners();
                    resetJuego(); //Reinicio los valores de las fases.
                	SndManager.PlayMusic(SndManager.MUSIC_MAINMENU, true);
                    }
                    break;
            }return;
        }      
//////////////////////////////////////////MENU FIN FASE
        if (pause == true && menuMuerte==false && menuFinFase==true) {
            switch (keyCode) {
                case KEYCODE_FIRE:
                case KEYCODE_NUM5:
                    if (PosXtemp2>=(Define.SCR_WIDTH) >> 1) 
                    {
                    //Aqui pones el guardado de partida y avance de nivel
                    //
                    //    
                    stopMusica();
                    menuFinFase=false;
                    pause = false;
                    partidaIniciada = false;
                    dukeMuere = false;
                    scrollX=0;
                    tiempoMuerto=0;
                    menuMuerte=false;
                    for (int i=0;i<Ninja2Frame.length;i++)
                    {
                    Ninja2Frame[i]=0;
                    }
                    //Saltamos al siguiente estado
                    ESTADO = JUGAR;
                    KitmakerAdsProviderManager.getManager().removeBanners();
                    //AquÃ­ cambiamos la siguiente fase a jugar
                    FASEAJUGAR+=1;
                    partidaGuardada=true;
                    //////////////////////////////////////////
                    
                    
                        ////////////////
                        //FIN DL JUEGO//
                        ////////////////
                        if (FASEAJUGAR == FINAL) {
                            ESTADO = FINAL;                            
                        }
                        
                    ///////////////////////////////////////////    
                    borrarGraficos();
                    cargarGraficos();
                    resetJuego(); //Reinicio los valores de las fases.
                    //cargarCanciones(cancionIntromenu); //Forzar recarga de las canciones.
                    //comenzarMusica(cancionIntromenu, true);
                    }
                    break;
            }return;
        }        
    }

    /**
     * Called when a key is released.
     */
    protected void keyReleased(int keyCode) {
        if (FASEAJUGAR != 0) {
            switch (keyCode) {
                case KEYCODE_NUM4:
                case KEYCODE_LEFT:
                    pressLeft = false;
                    break;
                case KEYCODE_NUM6:
                case KEYCODE_RIGHT:
                    pressRight = false;
                    break;
                case KEYCODE_NUM2:
                case KEYCODE_UP:
                    pressUp = false;
                    break;
                case KEYCODE_DOWN:
                case KEYCODE_NUM8:
                    pressDown = false;
                case KEYCODE_FIRE:
                case KEYCODE_NUM5:
                    //pressFight=false;
                    break;
            }

        }
    }

    
 
    
    
    
    
    
    
    
    
    
    
    
    
 // TOUCH SCREEN support
    public static boolean TOUCHSCREEN_SUPPORTED = false; //true;
    public final static int ACTION_UP = 0;
    public final static int ACTION_DOWN = 1;
    static final int ACTION_MOVE = 2;
    static int ms_iScreenOrigin_X, ms_iScreenOrigin_Y;
    static int ms_iScreenTouched_X, ms_iScreenTouched_Y;
    static int ms_iScreenTouched_Map = ACTION_UP;
    static int ms_iScreenTouched_Frames = 0;
    static boolean ms_bScreenTouchReset;

    void TouchScreenCheck () {
       TOUCHSCREEN_SUPPORTED = hasPointerEvents() && hasPointerMotionEvents();
    }

    public void pointerPressed(int x, int y) {

      ms_iScreenTouched_Map = ACTION_DOWN;
      ms_iScreenTouched_X = x;
      ms_iScreenTouched_Y = y;
      ms_iScreenOrigin_X = x;
      ms_iScreenOrigin_Y = y;
      ms_iScreenTouched_Frames = 0;

       //#if KeepPortraid
 //#       if (getWidth()>getHeight()) {
 //#          ms_iScreenTouched_X = SCR_WIDTH - _iY;
 //#          ms_iScreenTouched_Y = _iX;
 //#          ms_iScreenOrigin_X = SCR_WIDTH - _iY;
 //#          ms_iScreenOrigin_Y = _iX;
 //#       }
       //#endif
    
       if (FASEAJUGAR != 0 && partidaIniciada == true && pause == false && dukeMuere == false) {        
    	    
    	    
    	 //Puñetazo       
    	             if (y > posYTablero //+ (alturaTablero>>1)          
    	             && x > Define.SCR_WIDTH>>1//(Define.SCR_WIDTH>>1) - (pad.getWidth()>>2)
    	             && x < Define.SCR_WIDTH - (Define.SCR_WIDTH>>2)//(Define.SCR_WIDTH>>1) + (pad.getWidth()>>2)      
    	                     ) {                      
    	            	 if (fotograma == 5 && patadaAerea==false) { //PATADA AEREA.
 	                        patadaAerea = true;
 	                        }
 	                        if (pressFight == false && fotograma != 5 && fotograma !=18) {
 	                        if (COMBO == 0) {
 	                        fotograma = 14 - 1; //PUÑETAZO.
 	                        } else {
 	                        fotograma = 6 - 1; //PATADA.    
 	                        }
 	                        pressFight = true;
 	                        velocidadAnimacion = 0; //Resetea el valor ;-)
 	                        }
    	                       }
    	             
    	 //Derecha
    	             if (y > posYTablero //+ (alturaTablero>>1)
    	             //&& (y > posYTablero)       
    	             && x > Define.SCR_WIDTH>>2//(Define.SCR_WIDTH>>1) - (pad.getWidth()>>2)
    	             && x < Define.SCR_WIDTH>>1//(Define.SCR_WIDTH>>1) + (pad.getWidth()>>2)
    	                     ) {                  
    	            	 pressLeft = false;
                         pressRight = true;
    	                         }       
    	             
    	 //izquierda
    	             if (y > posYTablero //+ (alturaTablero>>1)
    	             //&& x > (Define.SCR_WIDTH>>1) - (pad.getWidth()>>1)
    	             && x < Define.SCR_WIDTH>>2//(Define.SCR_WIDTH>>1) - (pad.getWidth()>>2)     
    	                     ) {                   
    	                         pressRight = false;
    	                         pressLeft = true;
    	                       }       
    	             
    	 //Salto
    	             if (y > posYTablero //+ (alturaTablero>>1)
    	             && x > Define.SCR_WIDTH - (Define.SCR_WIDTH>>2)//(Define.SCR_WIDTH>>1) + (pad.getWidth()>>2)
    	             //&& x < (Define.SCR_WIDTH>>1) + (pad.getWidth()>>1) 
    	                     ) {                   
    	            	 if (colision == true) { //Impide un doble salto.
    	                        pressUp = true;
    	                        salto = true;
    	                         }
    	                       }              
    	        

    	 }

    }

    public void pointerReleased(int _iX, int _iY) {
       ms_iScreenTouched_Map = ACTION_UP;
       ms_iScreenTouched_X = -1;
       ms_iScreenTouched_Y = -1;
       ms_iScreenOrigin_X = -1;
       ms_iScreenOrigin_Y = -1;
       ms_iScreenTouched_Frames = 5;
       //entrada.ControlPuntero(entrada.POINTER_EVENT_RELEASE, x, y);
       if (FASEAJUGAR != 0) {
           pressLeft = false;
           pressRight = false;
           pressUp = false;
           pressDown = false;
        }
    }

    public void pointerDragged(int _iX, int _iY) {
       ms_iScreenTouched_Map = ACTION_MOVE;
       ms_iScreenTouched_X = _iX;
       ms_iScreenTouched_Y = _iY;
       ms_iScreenTouched_Frames++;

       //#if KeepPortraid
 //#       if (getWidth() > getHeight()) {
 //#          ms_iScreenTouched_X = SCR_WIDTH - _iY;
 //#          ms_iScreenTouched_Y = _iX;
 //#       }
       //#endif

    }

    static boolean GameScreenTouched(boolean _bResetTouch, boolean _bGetMove) {
       boolean bPressed =
          ((ms_iScreenTouched_Map == ACTION_DOWN) ||
          ((ms_iScreenTouched_Map == ACTION_MOVE) && ms_iScreenTouched_Frames < 5) ||
          (ms_iScreenTouched_Map == ACTION_MOVE && _bGetMove)) && TOUCHSCREEN_SUPPORTED;

       if (bPressed && _bResetTouch && !_bGetMove) {
          ms_iScreenTouched_Map = ACTION_UP;
          ms_iScreenTouched_Frames = 5;
       }
       return bPressed;
    }

    static boolean GameScreenTouched (int _iX0, int _iY0, int _iX1, int _iY1,
    	      boolean _bResetTouch, boolean _bGetMove) {
    	      boolean bPressed =
    	         ((ms_iScreenTouched_Map==ACTION_DOWN) ||
    	         ((ms_iScreenTouched_Map==ACTION_MOVE) && ms_iScreenTouched_Frames < 5) ||
    	         (ms_iScreenTouched_Map==ACTION_MOVE && _bGetMove)) &&
    	         TOUCHSCREEN_SUPPORTED &&

    	         ((ms_iScreenTouched_X > _iX0 && ms_iScreenTouched_X < _iX1) &&
    	         (ms_iScreenTouched_Y > _iY0 && ms_iScreenTouched_Y < _iY1))

    	         ;

    	      if (bPressed && _bResetTouch && !_bGetMove)
    	         ms_iScreenTouched_Map=ACTION_UP;

    	      return bPressed;
    	   }

    static final int TOUCH_L_SOFTK = 0;
    static final int TOUCH_R_SOFTK = 1;

    static final int TOUCH_ICON_SIZE_X = Define.SCR_WIDTH/4;
    static final int TOUCH_ICON_SIZE_Y = Define.SCR_HEIGHT/8;

    static boolean GameScreenSoftkey (int _iSoftKey, boolean _bResetTouch) {

       boolean bPressed = false;
       if (TOUCHSCREEN_SUPPORTED) {

          if (ms_iScreenTouched_Map==ACTION_DOWN ||
             (ms_iScreenTouched_Map==ACTION_MOVE && ms_iScreenTouched_Frames < 5)) {
             if (_iSoftKey == TOUCH_L_SOFTK &&
                ((ms_iScreenTouched_X > 1 && ms_iScreenTouched_X < TOUCH_ICON_SIZE_X) &&
                (ms_iScreenTouched_Y > Define.SCR_HEIGHT-TOUCH_ICON_SIZE_Y && ms_iScreenTouched_Y < Define.SCR_HEIGHT))) {
                ms_iScreenTouched_Frames = 5;
                bPressed = true;
             }
             if (_iSoftKey == TOUCH_R_SOFTK &&
                ((ms_iScreenTouched_X > Define.SCR_WIDTH-TOUCH_ICON_SIZE_X && ms_iScreenTouched_X < Define.SCR_WIDTH) &&
                (ms_iScreenTouched_Y > Define.SCR_HEIGHT-TOUCH_ICON_SIZE_Y && ms_iScreenTouched_Y < Define.SCR_HEIGHT))) {
                ms_iScreenTouched_Frames = 5;
                bPressed = true;
             }
          }
          if (bPressed && _bResetTouch)
             ms_iScreenTouched_Map=ACTION_UP;
       }
       return bPressed;

    }

    public boolean TouchScreenReleased () {
       if (ms_iScreenTouched_X == -1)
          return true;

       return false;
    }    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * Called when a key is repeated (held down).
     */
    protected void keyRepeated(int keyCode) {
    }

    /**
     * Called when the pointer is dragged.
     */
    protected void pointerDraggedK(int x, int y) {
//#if TouchScreen=="true"
//#         
//# 
//#endif        

    }

    /**
     * Called when the pointer is pressed.
     */
    protected void pointerPressedK(int x, int y) {
//#if TouchScreen=="true"        
 //Jugar
 if (FASEAJUGAR != 0 && partidaIniciada == true && pause == false && dukeMuere == false) {        
     
     
 //PuÃ±etazo       
             if (y > posYTablero //+ (alturaTablero>>1)          
             && x > Define.SCR_WIDTH>>1//(Define.SCR_WIDTH>>1) - (pad.getWidth()>>2)
             && x < Define.SCR_WIDTH - (Define.SCR_WIDTH>>2)//(Define.SCR_WIDTH>>1) + (pad.getWidth()>>2)      
                     ) {                      
                         if (fotograma == 5 && patadaAerea==false) { //PATADA AEREA.
                         patadaAerea = true;
                         }
                         if (pressFight == false && fotograma != 5 && fotograma !=18) {
                         if (COMBO == 0) {
                         fotograma = 14 - 1; //PUÃ‘ETAZO.
                         } else {
                         fotograma = 6 - 1; //PATADA.    
                         }
                         pressFight = true;
                         velocidadAnimacion = 0; //Resetea el valor ;-)
                         }
                       }
             
 //Salto
             if (y > posYTablero //+ (alturaTablero>>1)
             //&& (y > posYTablero)       
             && x > Define.SCR_WIDTH>>2//(Define.SCR_WIDTH>>1) - (pad.getWidth()>>2)
             && x < Define.SCR_WIDTH>>1//(Define.SCR_WIDTH>>1) + (pad.getWidth()>>2)
                     ) {                  
                         if (colision == true) { //Impide un doble salto.
                         pressUp = true;
                         salto = true;
                          }
     
                         }       
             
 //izquierda
             if (y > posYTablero //+ (alturaTablero>>1)
             //&& x > (Define.SCR_WIDTH>>1) - (pad.getWidth()>>1)
             && x < Define.SCR_WIDTH>>2//(Define.SCR_WIDTH>>1) - (pad.getWidth()>>2)     
                     ) {                   
                         pressRight = false;
                         pressLeft = true;
                       }       
             
 //Derecha
             if (y > posYTablero //+ (alturaTablero>>1)
             && x > Define.SCR_WIDTH - (Define.SCR_WIDTH>>2)//(Define.SCR_WIDTH>>1) + (pad.getWidth()>>2)
             //&& x < (Define.SCR_WIDTH>>1) + (pad.getWidth()>>1) 
                     ) {                   
                         pressLeft = false;
                         pressRight = true;
                       }              
        
 
 }
    //#endif 
    }

    /**
     * Called when the pointer is released.
     */
    protected void pointerReleasedK(int x, int y) {
//#if TouchScreen=="true"        
    	if (pulsacionTactil == true) {
            
    		//Menu Idioma
    		        if (ESTADO == MENUIDIOMAINIC) {
    		        
    		        if (y > Define.margenYmenuPrin + unDecimo - (unDecimo>>2)
    		            && y < Define.margenYmenuPrin + (unDecimo*2)    
    		                ) {
    		                   destroyLanguage(); //Borra la partida anterior. No se pueden editar.
    		                   iniciarLenguaje(); //Crea una nueva vacía.
    		                   writeLanguage("ingles");  //Crossword es el nombre de la Class.
    		                   Define.Language=0;                          //Es ahora cuando se reselecciona el idioma.
    		                   borrarGraficos();
    		                   ESTADO=MENUSONIDOINIC;
    		                   contadorPulsacionTactil = 0;
    		                   pulsacionTactil = false;
    		                   return;
    		        }
    		        if (y > Define.margenYmenuPrin + (unDecimo*3) - (unDecimo>>2)
    		            && y < Define.margenYmenuPrin + (unDecimo*4)
    		                ) {
    		                   destroyLanguage(); //Borra la partida anterior. No se pueden editar.
    		                   iniciarLenguaje(); //Crea una nueva vacía.
    		                   writeLanguage("espanol");  //Crossword es el nombre de la Class.
    		                   Define.Language=1;                          //Es ahora cuando se reselecciona el idioma.
    		                   borrarGraficos();
    		                   ESTADO=MENUSONIDOINIC;
    		                   contadorPulsacionTactil = 0;
    		                   pulsacionTactil = false;
    		                   return;
    		        }
    		        if (y > Define.margenYmenuPrin + (unDecimo*5) - (unDecimo>>2)
        		            && y < Define.margenYmenuPrin + (unDecimo*6)
        		                ) {
        		                   destroyLanguage(); //Borra la partida anterior. No se pueden editar.
        		                   iniciarLenguaje(); //Crea una nueva vacía.
        		                   writeLanguage("brasil");  //Crossword es el nombre de la Class.
        		                   Define.Language=2;                          //Es ahora cuando se reselecciona el idioma.
        		                   borrarGraficos();
        		                   ESTADO=MENUSONIDOINIC;
        		                   contadorPulsacionTactil = 0;
        		                   pulsacionTactil = false;
        		                   return;
        		        }
    		        
    		        }
    		//Menu Sonido
    		        if (ESTADO == MENUSONIDOINIC) {
    		        
    		        if (y > Define.margenYmenuPrin + unDecimo - (unDecimo>>2)
    		            && y < Define.margenYmenuPrin + (unDecimo*2)    
    		                ) {
    		            kanforSound=false;
    		            borrarGraficos();
    		            ESTADO=MENUANIMADO;
    		            //Iniciamos carga del lenguaje.
    		            iniciarLenguaje();
    		            iniciarMemoryCard();
    		            //Iniciamos carga de la partida.
    		            iniciarMemoryCard();
    		            contadorPulsacionTactil = 0;
    		            pulsacionTactil = false;
    		            return;
    		        }
    		        if (y > Define.margenYmenuPrin + (unDecimo*3) - (unDecimo>>2)
    		            && y < Define.margenYmenuPrin + (unDecimo*4)
    		                ) {
    		            kanforSound=true;
    		            borrarGraficos();
    		            ESTADO=MENUANIMADO;
    		            //Iniciamos carga del lenguaje.
    		            iniciarLenguaje();
    		            iniciarMemoryCard();
    		            //Iniciamos carga de la partida.
    		            iniciarMemoryCard();
    		            contadorPulsacionTactil = 0;
    		            pulsacionTactil = false;
    		            return;
    		        }
    		        
    		        }
    		//Menu Pausa
    		        if (pause == true && menuMuerte==false && menuFinFase==false) {
    		        if (y > Define.margenYmenuPrin + unDecimo - (unDecimo>>2)
    		            && y < Define.margenYmenuPrin + (unDecimo*2)
    		                ) {
    		                        opcionSelect=0;
    		                        pause=false;
    		                        pressDown=false;
    		                        pressUp=false;
    		                        pressLeft=false;
    		                        pressRight=false;
    	                        	SndManager.PlayMusic(SndManager.MUSIC_GAMEPLAY1, true);
    		                        return;
    		        }
    		        if (y > Define.margenYmenuPrin + (unDecimo*3) - (unDecimo>>2)
    		            && y < Define.margenYmenuPrin + (unDecimo*4)
    		                ) {
    		                       ESTADO = MENU;
    		                       borrarGraficos();
    		                       cargarGraficos();
                                       KitmakerAdsProviderManager.getManager().addBanners();
    		                       resetJuego(); //Reinicio los valores de las fases.
    	                           SndManager.PlayMusic(SndManager.MUSIC_MAINMENU, true);
    		                       return;
    		        }
    		        if (y > Define.margenYmenuPrin + (unDecimo*5) - (unDecimo>>2)
    		            && y < Define.margenYmenuPrin + (unDecimo*6)
    		            && x < Define.SCR_WIDTH - botonPausa.getWidth()    
    		                ) {
    		                       stopMusica();
    		                       MidletFinalKombat.quitApp();
    		                       return;
    		        }
    		        }
    		//Menu Animado
    		        if (ESTADO == MENUANIMADO && duke_intro != null) {
    		                    PosXtemp1 = 0;
    		                    PosYtemp1 = 0;        //Ponemos los valores a cero y ahorramos memoria gráfica.
    		                    PosXtemp2 = 0;
    		                    PosYtemp2 = 0;
    		                    estadoTemp = 0;
    		                    fondoCielo1 = null;
    		                    ESTADO = MENU;
                                    KitmakerAdsProviderManager.getManager().addBanners();
    		                    borrarGraficos();
    		                    if (partidaGuardada == false) {
    		                        opcionSelect = 1;
    		                    }  //Si no hay partida guardada.
    		                    contadorPulsacionTactil = 0;
    		                    pulsacionTactil = false;
    		                    return;
    		        }
    		//Menu General
    		        if (ESTADO == MENU && submenu == false && mostrarAdvertencia==false) {  //Menu normal
    		            if (y > Define.margenYmenuPrin - (unDecimo>>2) - 10
    		            && y < Define.margenYmenuPrin - (unDecimo>>2) + unDecimo - 10
    		                    ) { //Continue
    		                if (partidaGuardada==true) {
    		                        opcionSelect = 0;
    		                        borrarGraficos();
    		                        //stopMusica(); //Nextel
    		                        ESTADO = JUGAR;
                                        KitmakerAdsProviderManager.getManager().removeBanners();
    		                        //Cargamos la memorycard.
    		                        iniciarMemoryCard();
    		                        //No ponemos FASEAJUGAR porque ya queda registrado en la memoria al cargar la Memory Card.
    		                        contadorPulsacionTactil = 0;
    		                        pulsacionTactil = false;
    		                        return;
    		                }
    		            }
    		            if (y > Define.margenYmenuPrin + unDecimo - (unDecimo>>2)
    		            && y < Define.margenYmenuPrin + unDecimo - (unDecimo>>2) + unDecimo
    		                    ) { //New Game
    		                        if (partidaGuardada==true) {
    		                            
    		                            mostrarAdvertencia=true;
    		                            
    		                        } else {                       
    		                        opcionSelect = 0;
    		                        borrarGraficos();
    		                        ESTADO = JUGAR;
                                        KitmakerAdsProviderManager.getManager().removeBanners();
    		                        //stopMusica(); //Nextel
    		                        partidaGuardada=false;
    		                        FASEAJUGAR = FASE11; //Aquí se indica la fase a jugar. Al ser New Game siempre es la primera fase. HACK.
    		                        }
    		                        //Falta borrar la partida.
    		                        contadorPulsacionTactil = 0;
    		                        pulsacionTactil = false;
    		                        return;
    		            }
    		            if (y > Define.margenYmenuPrin + (unDecimo * 2) - (unDecimo>>2) + 10
    		            && y < Define.margenYmenuPrin + (unDecimo * 2) - (unDecimo>>2) + 10 + unDecimo
    		                    ) { //Opciones
    		                       opcionSelect = 0;
    		                       submenu = true;
    		                       contadorPulsacionTactil = 0;
    		                       pulsacionTactil = false;
    		                       return;
    		            }
    		             if (y > Define.margenYmenuPrin + (unDecimo * 3) - (unDecimo>>2) + 20
    		            && y < Define.margenYmenuPrin + (unDecimo * 3) - (unDecimo>>2) + 20 + unDecimo
    		                    ) { //Instrucciones
    		                      ESTADO = INSTRUCCIONES;
                                      KitmakerAdsProviderManager.getManager().addBanners();
    		                      contadorPulsacionTactil = 0;
    		                      pulsacionTactil = false;
    		             }
    		            if (y > Define.margenYmenuPrin + (unDecimo * 4) - (unDecimo>>2) + 30
    		            && y < Define.margenYmenuPrin + (unDecimo * 4) - (unDecimo>>2) + 30 + unDecimo
    		                    ) { //About
    		                      ESTADO = ABOUT;
                                      KitmakerAdsProviderManager.getManager().addBanners();
    		                      contadorPulsacionTactil = 0;
    		                      pulsacionTactil = false;
    		            }
    		            if (y > Define.margenYmenuPrin + (unDecimo * 5) - (unDecimo>>2) + 40
    		            && y < Define.margenYmenuPrin + (unDecimo * 5) - (unDecimo>>2) + 40 + unDecimo
    		                    ) { //Salir
    		                        stopMusica();
    		                        MidletFinalKombat.quitApp();
    		                        }
    		        //return;    
    		        }
    		          
    		        
    		//Menu Subopciones
    		        if (ESTADO == MENU && submenu == true) {  //Menu normal
    		            if (y > Define.margenYmenuPrin + unDecimo - (unDecimo>>2)
    		            && y < Define.margenYmenuPrin + (unDecimo*2)
    		                    ) {
    		                        kanforSound = !kanforSound;
    		                        stopMusica();
    		                        if (kanforSound==true) {
    		                        	SndManager.PlayMusic(SndManager.MUSIC_MAINMENU, true);
    		                        	contadorPulsacionTactil = 0;    
    		                        	return;
    		                        }
    		            }
    		            if (y > Define.margenYmenuPrin + (unDecimo*3) - (unDecimo>>2)
    		            && y < Define.margenYmenuPrin + (unDecimo*4)
    		                    ) {
    		                    if (Define.Language == 0) {
    		                            destroyLanguage(); //Borra la partida anterior. No se pueden editar.
    		                            iniciarLenguaje(); //Crea una nueva vacía.
    		                            writeLanguage("espanol");  //Crossword es el nombre de la Class.
    		                            Define.Language=1;                          //Es ahora cuando se reselecciona el idioma.
    		                            contadorPulsacionTactil = 0;
    		                            return;
    		                        }
    		                        if (Define.Language == 1) {
    		                            destroyLanguage(); //Borra la partida anterior. No se pueden editar.
    		                            iniciarLenguaje(); //Crea una nueva vacía.
    		                            writeLanguage("ingles");  //Crossword es el nombre de la Class.
    		                            Define.Language=2;
    		                            contadorPulsacionTactil = 0;
    		                            return;
    		                        }
    		                        if (Define.Language == 2) {
    		                            destroyLanguage(); //Borra la partida anterior. No se pueden editar.
    		                            iniciarLenguaje(); //Crea una nueva vacía.
    		                            writeLanguage("brasil");  //Crossword es el nombre de la Class.
    		                            Define.Language=0;
    		                            contadorPulsacionTactil = 0;
    		                            return;
    		                        }
    		            }
    		            if (y > Define.margenYmenuPrin + (unDecimo*5) - (unDecimo>>2)
    		            && y < Define.margenYmenuPrin + (unDecimo*6)
    		                    ) {
    		                            submenu = false;
    		                            contadorPulsacionTactil = 0;
    		                            return;
    		            }
    		            contadorPulsacionTactil = 0;
    		            pulsacionTactil = false;
    		            //return;
    		        }
    		//About
    		        if (ESTADO == ABOUT) {
    		            ESTADO = MENU;
                            KitmakerAdsProviderManager.getManager().addBanners();
    		            contadorPulsacionTactil = 0;
    		            pulsacionTactil = false;
    		            return;
    		        }
    		//Instrucciones
    		        if (ESTADO == INSTRUCCIONES) {
    		            ESTADO = MENU;
                            KitmakerAdsProviderManager.getManager().addBanners();
    		            contadorPulsacionTactil = 0;
    		            pulsacionTactil = false;
    		            return;
    		        }
    		//Menu Muerte
    		        if (pause == true && menuMuerte==true) {
    		                    if (tiempoMuerto>50) {
    		                    pause = false;
    		                    partidaIniciada = false;
    		                    dukeMuere = false;
    		                    scrollX=0;
    		                    tiempoMuerto=0;
    		                    menuMuerte=false;
    		                    for (int i=0;i<Ninja2Frame.length;i++)
    		                    Ninja2Frame[i]=0;
    		                    }
    		                    contadorPulsacionTactil = 0;
    		                    pulsacionTactil = false;
    		                    return;
    		        }
    		//Menu Game Over
    		        if (ESTADO == GAMEOVER) {
    		                    if (PosXtemp1>50) 
    		                    {
    		                    pause = false;
    		                    partidaIniciada = false;
    		                    dukeMuere = false;
    		                    scrollX=0;
    		                    tiempoMuerto=0;
    		                    menuMuerte=false;
    		                    for (int i=0;i<Ninja2Frame.length;i++)
    		                    Ninja2Frame[i]=0;
    		                    ESTADO = MENU;
    		                    borrarGraficos();
    		                    cargarGraficos();
                                    KitmakerAdsProviderManager.getManager().addBanners();
    		                    resetJuego(); //Reinicio los valores de las fases.
                            	SndManager.PlayMusic(SndManager.MUSIC_MAINMENU, true);
    		                    }
    		                    contadorPulsacionTactil = 0;
    		                    pulsacionTactil = false;
    		                    return;
    		        }
    		//Menu Fin Fase
    		        if (pause == true && menuMuerte==false && menuFinFase==true) {
    		                    if (PosXtemp2>=(Define.SCR_WIDTH) >> 1) 
    		                    {
    		                    //Aqui pones el guardado de partida y avance de nivel
    		                    //
    		                    //    
    		                    stopMusica();
    		                    menuFinFase=false;
    		                    pause = false;
    		                    partidaIniciada = false;
    		                    dukeMuere = false;
    		                    scrollX=0;
    		                    tiempoMuerto=0;
    		                    menuMuerte=false;
    		                    for (int i=0;i<Ninja2Frame.length;i++)
    		                    {
    		                    Ninja2Frame[i]=0;
    		                    }
    		                    //Saltamos al siguiente estado
    		                    ESTADO = JUGAR;
    		                    //Aquí cambiamos la siguiente fase a jugar
    		                    FASEAJUGAR+=1;
    		                    partidaGuardada=true;
    		                    //////////////////////////////////////////
    		                    
    		                    
    		                        ////////////////
    		                        //FIN DL JUEGO//
    		                        ////////////////
    		                        if (FASEAJUGAR == FINAL) {
    		                            ESTADO = FINAL;
    		                        }
    		                        
    		                    ///////////////////////////////////////////    
    		                    borrarGraficos();
    		                    cargarGraficos();
                                    KitmakerAdsProviderManager.getManager().removeBanners();
    		                    resetJuego(); //Reinicio los valores de las fases.
    		                    //cargarCanciones(cancionIntromenu); //Forzar recarga de las canciones.
    		                    //comenzarMusica(cancionIntromenu, true);
    		                    }
    		                    contadorPulsacionTactil = 0;
    		                    pulsacionTactil = false;
    		                    return;
    		        }
    		//Advertencia de borrado        
    		        if (mostrarAdvertencia==true) {
    		                if (y > Define.margenYmenuPrin + unDecimo - (unDecimo>>2) + unDecimo
    		                && y < Define.margenYmenuPrin + (unDecimo*2) + unDecimo
    		                ) {
    		                    //Borrar
    		                        destroyMemoryCard(); //BORRA la partida.
    		                       iniciarMemoryCard();
    		                       writeMemoryCard("fase11");
    		                       mostrarAdvertencia=false;
    		                       opcionSelect=1;
    		                       partidaGuardada=false;  //BORRA la partida.
    		                    contadorPulsacionTactil = 0;
    		                    pulsacionTactil = false;
    		                    return;
    		                }
    		                if (y > Define.margenYmenuPrin + (unDecimo*3) - (unDecimo>>2) + unDecimo
    		                && y < Define.margenYmenuPrin + (unDecimo*4) + unDecimo
    		                ) {
    		                    //Volver
    		                    mostrarAdvertencia=false;
    		                    contadorPulsacionTactil = 0;
    		                    pulsacionTactil = false;
    		                    return;
    		                }
    		        }
    		//Pausa
    		            if (y < posYTablero
    		                && y > posYTablero - botonPausa.getHeight()
    		                && x > Define.SCR_WIDTH - botonPausa.getWidth()    
    		                    ) {
    		                       pause = true;
    		                       stopMusica();
    		                       contadorPulsacionTactil = 0;
    		                       pulsacionTactil = false;
    		                       return;
    		                       }    
    		            
    		    } //Fin del temporizador táctil.
    		//Juego (No le afecta el temporizado táctil porque no hace falta).

    		    if (FASEAJUGAR != 0) {
    		       pressLeft = false;
    		       pressRight = false;
    		       pressUp = false;
    		       pressDown = false;
    		    }
    }

    public void stopMusica() {
    	
    	SndManager.StopMusic();
    }
    
    

//+++++++++++++++++++++//      
/////////////////////////    
//MATRICES DE LAS FASES//
/////////////////////////
//+++++++++++++++++++++//   
    
    public void matricesFases() {
        
    if (FASEAJUGAR == FASE11) {
            //mapaLadrillo controla el lÃ­mite de la fase.
            //La longitud de la fase es la longitud de mapaLadrillo.
            //Cada fila es el ancho de una tienda.
            mapaLadrillo = new int[] {1,1,1,1,1,1,1,1,
                                      1,1,1,1,1,1,1,1,
                                      0,0,0,0,0,0,0,0,
                                      1,1,1,1,1,1,1,1,
                                      1,1,1,1,1,1,1,1,
                                      0,0,0,0,0,0,0,0,
                                      1,1,1,1,1,1,1,1,
                                      1,1,1,1,1,1,1,1,
                                      1,1,1,1,1,1,1,1,
                                      1,1,1,1,1,1,1,1,
                                      1,1,1,1,1,1,1,1,
                                      1,1,1,1,1,1,1,1,
                                      0,0,0,0,0,0,0,0,
            };
            mapaSenefas = new int []   {0,0,0,0,
                                        1,1,1,1,
                                        0,0,0,0,
                                        1,1,1,1,
                                        0,0,0,0,
                                        0,0,0,0,
                                        0,0,0,0,
                                        1,1,1,1,
                                        1,1,1,1,
                                        0,0,0,0,
                                        0,0,0,0,
                                        1,1,1,1};
            mapaTiendas = new int [] {1,
                                      0,
                                      0,
                                      0,
                                      3,
                                      0,
                                      2,
                                      0,
                                      0,
                                      1,
                                      3,
                                      0};
            mapaVentanas = new int [] {1,
                                       1,
                                       0,
                                       1,
                                       1,
                                       0,
                                       1,
                                       1,
                                       1,
                                       1,
                                       1,
                                       1};
            mapaMetalicas1 = new int []    {0,0,0,0,
                                            0,0,0,0,
                                            1,1,0,0,
                                            1,1,1,1,
                                            0,0,0,0,
                                            1,1,1,1,
                                            1,1,0,0,
                                            1,1,1,1,
                                            0,1,1,1,
                                            0,0,0,0,
                                            0,0,0,0}; 
            mapaMetalicas2 = new int[] {0,0,0,0,
                                        0,0,0,0,
                                        0,0,1,1,
                                        0,0,0,0,
                                        0,0,0,0,
                                        1,1,1,1,
                                        0,0,1,1,
                                        1,1,1,1,
                                        0,0,1,0,
                                        0,0,1,1,
                                        0,0,0,0};
         //PosiciÃ³n de las bombas.
         //1 <- bomba en el suelo
         //2 <- bomba en la plataforma1
         //3 <- bomba en la plataforma2
            if (numeroVidas==3) {   //Con esto hacemos que solo se inicie al comenzar una fase. De otro modo las bombas se resetan al perder una vida.
            Bomba = new int [] {0,0,0,0,
                                0,0,0,0,
                                0,0,0,3,
                                0,0,2,0,
                                0,0,0,0,
                                0,0,2,3,
                                0,0,3,0,
                                0,0,2,0,
                                0,0,3,0,
                                0,0,0,3,
                                0,0,0,0,
                                1,0,0,0
            };
            }
         //Vale exactamente igual que el normal, pero no se edita.
         //Sirve para reinicar los valores de las bombas.
            BombaReset = new int [] {0,0,0,0,
                                0,0,0,0,
                                0,0,0,3,
                                0,0,2,0,
                                0,0,0,0,
                                0,0,2,3,
                                0,0,3,0,
                                0,0,2,0,
                                0,0,3,0,
                                0,0,0,3,
                                0,0,0,0,
                                1,0,0,0
            };
            NinjaPosX = new int [] {anchuraLadrillo1*25, anchuraLadrillo1*35, anchuraLadrillo1*50, anchuraLadrillo1*85};
            
          //Los 1 no cuentan. Son una referencia para saber dÃ³nde estÃ¡n las plataformas metÃ¡licas.
          //Los 2 son los ninjas. Deben tener tres 1 a su izquierda, pues avanza tres casillas.
            Ninja2PosX = new int [] {0,0,0,0,
                                     0,0,0,0,
                                     1,1,0,0,
                                     1,1,1,2,
                                     0,0,0,0,
                                     1,1,1,1,
                                     1,1,0,0,
                                     1,1,1,2,
                                     0,1,0,1,
                                     0,0,0,0};
        }
//    
        if (FASEAJUGAR == FASE12) {
            mapaLadrillo = new int[] {0,0,0,0,0,0,0,0,
                                      1,1,1,1,1,1,1,1,
                                      0,0,0,0,0,0,0,0,
                                      1,1,1,1,1,1,1,1,
                                      0,0,0,0,0,0,0,0,
                                      0,0,0,0,0,0,0,0,
                                      1,1,1,1,1,1,1,1,
                                      1,1,1,1,1,1,1,1,
                                      1,1,1,1,1,1,1,1,
                                      1,1,1,1,1,1,1,1,
                                      0,0,0,0,0,0,0,0,
                                      0,0,0,0,0,0,0,0,
                                      0,0,0,0,0,0,0,0,
            };
            mapaTiendas = new int [] {0,1,0,0,0,0,0,0,3};
            mapaVentanas = new int [] {0,1,0,1,0,0,1,1,1,1,0,0,0};
            mapaSenefas = new int []   {0,0,0,0,
                                        0,0,0,0,
                                        0,0,0,0,
                                        1,1,1,1,
                                        0,0,0,0,
                                        0,0,0,0,
                                        1,1,1,1,
                                        1,1,1,1,
                                        0,0,0,0,
                                        1,1,1,1,
                                        0,0,0,0,
                                        0,0,0,0,};
            mapaMetalicas1 = new int []    {0,0,0,0,
                                            0,1,1,1,
                                            0,0,0,0,
                                            1,1,1,1,
                                            0,0,0,0,
                                            0,0,0,0,
                                            1,0,0,1,
                                            0,0,1,0,
                                            0,0,0,0,
                                            1,1,1,1,
                                            1,1,1,1,  
            }; 
            mapaMetalicas2 = new int[] {0,0,0,0,
                                        0,1,1,1,
                                        1,1,1,1,
                                        1,1,1,1,
                                        0,0,0,0,
                                        0,0,0,0,
                                        0,1,1,0,
                                        1,1,0,0,
                                        0,0,0,0,
                                        1,1,1,1};
         //PosiciÃ³n de las bombas.
         //1 <- bomba en el suelo
         //2 <- bomba en la plataforma1
         //3 <- bomba en la plataforma2
            if (numeroVidas==3) {   //Con esto hacemos que solo se inicie al comenzar una fase. De otro modo las bombas se resetan al perder una vida.
            Bomba = new int [] {0,0,0,0,
                                0,0,0,0,
                                0,3,0,0,
                                0,0,3,0,
                                0,0,1,0,
                                0,0,0,0,
                                0,0,3,2,
                                0,0,2,0,
                                0,0,1,0,
                                0,0,0,2,
                                0,0,0,0,
                                1,0,0,0,
            };
            }
         //Vale exactamente igual que el normal, pero no se edita.
         //Sirve para reinicar los valores de las bombas.
            BombaReset = new int [] {0,0,0,0,
                                0,0,0,0,
                                0,3,0,0,
                                0,0,3,0,
                                0,0,1,0,
                                0,0,0,0,
                                0,0,3,2,
                                0,0,2,0,
                                0,0,1,0,
                                0,0,0,2,
                                0,0,0,0,
                                1,0,0,0,
            }; 
            NinjaPosX = new int [] {anchuraLadrillo1*21,anchuraLadrillo1*41,anchuraLadrillo1*68};
            
          //Los 1 no cuentan. Son una referencia para saber dÃ³nde estÃ¡n las plataformas metÃ¡licas.
          //Los 2 son los ninjas. Deben tener tres 1 a su izquierda, pues avanza tres casillas.
            Ninja2PosX = new int [] {0,0,0,0,
                                     0,0,0,0,
                                     0,0,0,0,
                                     0,0,0,2,
                                     0,0,0,0,
                                     0,0,0,0,
                                     0,0,0,0,
                                     0,0,0,0,
                                     0,0,0,0,
                                     0,0,0,2,
                                     0,0,0,0,
            };
            
        }
//    
        if (FASEAJUGAR == FASE13) {
            mapaLadrillo = new int[] {1,1,1,1,1,1,1,1,
                                      1,1,1,1,1,1,1,1,
                                      1,1,1,1,1,1,1,1,
                                      0,0,0,0,0,0,0,0,
                                      0,0,0,0,0,0,0,0,
                                      0,0,0,0,0,0,0,0,
                                      1,1,1,1,1,1,1,1,
                                      0,0,0,0,0,0,0,0,
                                      0,0,0,0,0,0,0,0,  
                                      1,1,1,1,1,1,1,1,
                                      1,1,1,1,1,1,1,1,
                                      0,0,0,0,0,0,0,0,
            };
            mapaTiendas = new int [] {0,0,3,0,0,0,1,0,0,2};
            mapaVentanas = new int [] {1,1,1,0,0,0,1,0,0,1,1};
            mapaSenefas = new int []   {1,1,1,1,
                                        1,1,1,1,
                                        0,0,0,0,
                                        0,0,0,0,
                                        0,0,0,0,
                                        0,0,0,0,
                                        0,0,0,0,
                                        0,0,0,0,
                                        0,0,0,0,
                                        0,0,0,0,
                                        1,1,1,1,
                                        0,0,0,0,};
            mapaMetalicas1 = new int []    {0,0,0,0,
                                            0,0,0,0,
                                            0,0,0,0,
                                            1,1,1,1,
                                            0,1,1,0,
                                            0,1,0,0,
                                            0,0,0,0,
                                            0,0,0,0,
                                            1,1,1,1,
                                            0,0,0,0,
                                            0,0,0,0,  
            }; 
            mapaMetalicas2 = new int[] {0,0,0,0,
                                        0,0,0,0,
                                        0,0,0,0,
                                        0,0,0,0,
                                        0,1,1,1,
                                        1,1,1,1,
                                        0,0,0,1,
                                        0,0,0,0,
                                        1,1,1,1,
                                        };
         //PosiciÃ³n de las bombas.
         //1 <- bomba en el suelo
         //2 <- bomba en la plataforma1
         //3 <- bomba en la plataforma2
            if (numeroVidas==3) {   //Con esto hacemos que solo se inicie al comenzar una fase. De otro modo las bombas se resetan al perder una vida.
            Bomba = new int [] {0,0,0,0,
                                0,0,0,0,
                                0,0,0,0,
                                0,2,2,0,
                                0,0,2,0,
                                0,2,0,3,
                                0,0,0,3,
                                0,0,0,1,
                                0,0,0,3,
                                0,0,0,0,
                                1,0,0,0,
            };
            }
         //Vale exactamente igual que el normal, pero no se edita.
         //Sirve para reinicar los valores de las bombas.
            BombaReset = new int [] {0,0,0,0,
                                0,0,0,0,
                                0,0,0,0,
                                0,2,2,0,
                                0,0,2,0,
                                0,2,0,3,
                                0,0,0,3,
                                0,0,0,1,
                                0,0,0,3,
                                0,0,0,0,
                                1,0,0,0,
            }; 
            NinjaPosX = new int [] {anchuraLadrillo1*27, anchuraLadrillo1*50, anchuraLadrillo1*60};
            
          //Los 1 no cuentan. Son una referencia para saber dÃ³nde estÃ¡n las plataformas metÃ¡licas.
          //Los 2 son los ninjas. Deben tener tres 1 a su izquierda, pues avanza tres casillas.
            Ninja2PosX = new int [] {0,0,0,0,
                                     0,0,0,0,
                                     0,0,0,0,
                                     0,0,0,2,
                                     0,0,0,0,
                                     0,0,0,0,
                                     0,0,0,0,
                                     0,0,0,0,
                                     0,0,0,2,
                                     0,0,0,0,
                                     0,0,0,0,
            };
            
        }        
//    
        if (FASEAJUGAR == FASE21) {
            mapaLadrillo = new int[] {0,0,0,0,0,0,0,0,
                                      1,1,1,1,1,1,1,1,
                                      1,1,1,1,1,1,1,1,
                                      0,0,0,0,0,0,0,0,
                                      1,1,1,1,1,1,1,1,
                                      0,0,0,0,0,0,0,0,
                                      1,1,1,1,1,1,1,1,
                                      0,0,0,0,0,0,0,0,
                                      1,1,1,1,1,1,1,1,  
                                      0,0,0,0,0,0,0,0,
                                      1,1,1,1,1,1,1,1,
                                      0,0,0,0,0,0,0,0,
                                      0,0,0,0,0,0,0,0,
            };
            mapaTiendas = new int [] {0,0,1,0,0,0,3};
            mapaVentanas = new int [] {0,1,1,0,1,0,1,0,1,0,1};
            mapaSenefas = new int []   {0,0,0,0,
                                        1,1,1,1,
                                        0,0,0,0,
                                        0,0,0,0,
                                        1,1,1,1,
                                        0,0,0,0,
                                        0,0,0,0,
                                        0,0,0,0,
                                        1,1,1,1,
                                        0,0,0,0,
                                        1,1,1,1
                                        };
            mapaMetalicas1 = new int []    {0,0,0,0,
                                            1,1,1,1,
                                            0,0,0,0,
                                            1,1,1,1,
                                            0,0,0,0,
                                            0,0,0,0,
                                            0,0,0,0,
                                            0,0,0,0,
                                            1,0,0,0,
                                            
            }; 
            mapaMetalicas2 = new int[] {0,0,0,0,
                                        1,1,1,1,
                                        1,1,1,1,
                                        1,1,1,1,
                                        1,1,1,1,
                                        1,0,0,0,
                                        1,0,0,0,
                                        0,0,0,0,
                                        0,0,0,0,
                                        1,1,1,1,
                                        0,0,0,1,
                                        };
         //PosiciÃ³n de las bombas.
         //1 <- bomba en el suelo
         //2 <- bomba en la plataforma1
         //3 <- bomba en la plataforma2
            if (numeroVidas==3) {   //Con esto hacemos que solo se inicie al comenzar una fase. De otro modo las bombas se resetan al perder una vida.
            Bomba = new int [] {0,0,0,0,
                                2,0,0,2,
                                0,0,3,0,
                                2,0,0,2,
                                0,0,0,0,
                                0,0,0,0,
                                3,0,0,0,
                                0,0,0,0,
                                2,0,0,0,
                                0,3,0,0,
                                0,0,0,3                 
            };
            }
         //Vale exactamente igual que el normal, pero no se edita.
         //Sirve para reinicar los valores de las bombas.
            BombaReset = new int [] {0,0,0,0,
                                2,0,0,2,
                                0,0,3,0,
                                2,0,0,2,
                                0,0,0,0,
                                0,0,0,0,
                                3,0,0,0,
                                0,0,0,0,
                                2,0,0,0,
                                0,3,0,0,
                                0,0,0,3 
            }; 
            NinjaPosX = new int [] {anchuraLadrillo1*21, anchuraLadrillo1*50, anchuraLadrillo1*55, anchuraLadrillo1*60, anchuraLadrillo1*75, anchuraLadrillo1*85};
            
          //Los 1 no cuentan. Son una referencia para saber dÃ³nde estÃ¡n las plataformas metÃ¡licas.
          //Los 2 son los ninjas. Deben tener tres 1 a su izquierda, pues avanza tres casillas.
            Ninja2PosX = new int [] {0,0,0,0,
                                            1,1,1,2,
                                            0,0,0,0,
                                            1,1,1,2,
                                            0,0,0,0,
                                            0,0,0,0,
                                            0,0,0,0,
                                            0,0,0,0,
                                            1,0,0,0,
            };
            
        }   
//    
        if (FASEAJUGAR == FASE22) {
            mapaLadrillo = new int[] {0,0,0,0,0,0,0,0,
                                      1,1,1,1,1,1,1,1,
                                      0,0,0,0,0,0,0,0,
                                      1,1,1,1,1,1,1,1,
                                      1,1,1,1,1,1,1,1,
                                      1,1,1,1,1,1,1,1,
                                      1,1,1,1,1,1,1,1,
                                      1,1,1,1,1,1,1,1,
                                      0,0,0,0,0,0,0,0,
                                      1,1,1,1,1,1,1,1,
                                      0,0,0,0,0,0,0,0,
                                      0,0,0,0,0,0,0,0,
                                      
            };
            mapaTiendas = new int [] {0,1,0,0,3,0,2};
            mapaVentanas = new int [] {0,1,0,1,1,1,1,1,0,1};
            mapaSenefas = new int []   {0,0,0,0,
                                        0,0,0,0,
                                        0,0,0,0,
                                        1,1,1,1,
                                        0,0,0,0,
                                        1,1,1,1,
                                        0,0,0,0,
                                        1,1,1,1,
                                        0,0,0,0,
                                        1,1,1,1,
                                        0,0,0,0,
                                        };
            mapaMetalicas1 = new int []    {0,0,0,0,
                                            0,1,1,1,
                                            1,1,1,1,
                                            0,0,0,0,
                                            0,0,0,0,
                                            0,0,0,0,
                                            0,0,0,0,
                                            0,0,0,0,
                                            1,1,1,1,
                                            
            }; 
            mapaMetalicas2 = new int[] {0,0,0,0,
                                        0,1,1,1,
                                        1,1,1,1,
                                        0,1,0,1,
                                        0,0,1,0,
                                        0,1,1,0,
                                        0,1,0,0,
                                        0,1,0,0,
                                        0,0,0,0,
                                        0,0,0,1,
                                        };
         //PosiciÃ³n de las bombas.
         //1 <- bomba en el suelo
         //2 <- bomba en la plataforma1
         //3 <- bomba en la plataforma2
            if (numeroVidas==3) {   //Con esto hacemos que solo se inicie al comenzar una fase. De otro modo las bombas se resetan al perder una vida.
            Bomba = new int [] {0,0,0,0,
                                0,0,2,0,
                                0,0,2,3,
                                0,0,0,3,
                                0,0,3,0,
                                0,0,3,0,
                                0,3,0,0,
                                0,3,0,0,
                                0,0,0,0,
                                0,0,0,3
            };
            }
         //Vale exactamente igual que el normal, pero no se edita.
         //Sirve para reinicar los valores de las bombas.
            BombaReset = new int [] {0,0,0,0,
                                0,0,2,0,
                                0,0,2,3,
                                0,0,0,3,
                                0,0,3,0,
                                0,0,3,0,
                                0,3,0,0,
                                0,3,0,0,
                                0,0,0,0,
                                0,0,0,3 
            }; 
            NinjaPosX = new int [] {anchuraLadrillo1*21, anchuraLadrillo1*35, anchuraLadrillo1*40, anchuraLadrillo1*45};
            
          //Los 1 no cuentan. Son una referencia para saber dÃ³nde estÃ¡n las plataformas metÃ¡licas.
          //Los 2 son los ninjas. Deben tener tres 1 a su izquierda, pues avanza tres casillas.
            Ninja2PosX = new int [] {       0,0,0,0,
                                            0,1,1,1,
                                            1,1,1,1,
                                            0,0,0,0,
                                            0,0,0,0,
                                            0,0,0,0,
                                            0,0,0,0,
                                            0,0,0,0,
                                            1,1,1,2,
            };
            
        } 
//    
        if (FASEAJUGAR == FASE23) {
            mapaLadrillo = new int[] {1,1,1,1,1,1,1,1,
                                      1,1,1,1,1,1,1,1,
                                      0,0,0,0,0,0,0,0,
                                      1,1,1,1,1,1,1,1,
                                      1,1,1,1,1,1,1,1,
                                      0,0,0,0,0,0,0,0,
                                      1,1,1,1,1,1,1,1,
                                      1,1,1,1,1,1,1,1,
                                      0,0,0,0,0,0,0,0,
                                      0,0,0,0,0,0,0,0,
                                      1,1,1,1,1,1,1,1,
                                      1,1,1,1,1,1,1,1,
            };
            mapaTiendas = new int [] {2,0,0,3,0,0,1};
            mapaVentanas = new int [] {1,1,0,1,1,0,1,1,0,0,1,1};
            mapaSenefas = new int []   {0,0,0,0,
                                        1,1,1,1,
                                        0,0,0,0,
                                        0,0,0,0,
                                        1,1,1,1,
                                        0,0,0,0,
                                        0,0,0,0,
                                        1,1,1,1,
                                        0,0,0,0,
                                        0,0,0,0,
                                        1,1,1,1,
                                        };
            mapaMetalicas1 = new int []    {0,0,0,0,
                                            1,0,0,1,
                                            0,0,0,0,
                                            1,1,1,1,
                                            1,1,1,1,
                                            0,0,0,0,
                                            1,1,1,1,
                                            1,1,1,1,
                                            0,0,0,0,
                                            
            }; 
            mapaMetalicas2 = new int[] {0,0,0,0,
                                        0,1,1,0,
                                        0,0,0,0,
                                        0,0,0,1,
                                        1,0,0,0,
                                        0,0,0,0,
                                        0,0,0,1,
                                        1,0,0,0,                  
                                        };
         //PosiciÃ³n de las bombas.
         //1 <- bomba en el suelo
         //2 <- bomba en la plataforma1
         //3 <- bomba en la plataforma2
            if (numeroVidas==3) {   //Con esto hacemos que solo se inicie al comenzar una fase. De otro modo las bombas se resetan al perder una vida.
            Bomba = new int [] {0,0,0,0,
                                0,3,0,2,
                                0,0,0,0,
                                2,0,0,0,
                                2,0,0,0,
                                0,0,0,0,
                                2,0,0,0,
                                2,0,0,0,
                                0,0,0,0,
                                1,0,1,0,
                                1,0,0,0,
            };
            }
         //Vale exactamente igual que el normal, pero no se edita.
         //Sirve para reinicar los valores de las bombas.
            BombaReset = new int [] {0,0,0,0,
                                0,3,0,2,
                                0,0,0,0,
                                2,0,0,0,
                                2,0,0,0,
                                0,0,0,0,
                                2,0,0,0,
                                2,0,0,0,
                                0,0,0,0,
                                1,0,1,0,
                                1,0,0,0, 
            }; 
            NinjaPosX = new int [] {anchuraLadrillo1*20, anchuraLadrillo1*52, anchuraLadrillo1*75, anchuraLadrillo1*80, anchuraLadrillo1*85};
            
          //Los 1 no cuentan. Son una referencia para saber dÃ³nde estÃ¡n las plataformas metÃ¡licas.
          //Los 2 son los ninjas. Deben tener tres 1 a su izquierda, pues avanza tres casillas.
            Ninja2PosX = new int [] {       0,0,0,0,
                                            1,0,0,1,
                                            0,0,0,0,
                                            1,1,1,2,
                                            2,1,2,2,
                                            0,0,0,0,
                                            1,1,1,2,
                                            2,1,2,2,
                                            0,0,0,0,
            };
            
        }
//    
        if (FASEAJUGAR == FASE31) {
            mapaLadrillo = new int[] {0,0,0,0,0,0,0,0,
                                      0,0,0,0,0,0,0,0,
                                      1,1,1,1,1,1,1,1,
                                      0,0,0,0,0,0,0,0,
                                      1,1,1,1,1,1,1,1,
                                      0,0,0,0,0,0,0,0,
                                      1,1,1,1,1,1,1,1,
                                      0,0,0,0,0,0,0,0,
                                      1,1,1,1,1,1,1,1,
                                      0,0,0,0,0,0,0,0,
                                      1,1,1,1,1,1,1,1,
                                      0,0,0,0,0,0,0,0,
                                      0,0,0,0,0,0,0,0,
            };
            mapaTiendas = new int [] {0,0,0,0,1,0,2};
            mapaVentanas = new int [] {0,0,1,0,1,0,1,0,1,0,1};
            mapaSenefas = new int []   {0,0,0,0,
                                        0,0,0,0,
                                        1,1,1,1,
                                        0,0,0,0,
                                        0,0,0,0,
                                        0,0,0,0,
                                        0,0,0,0,
                                        0,0,0,0,
                                        1,1,1,1,
                                        0,0,0,0,
                                        1,1,1,1,
                                        };
            mapaMetalicas1 = new int []    {0,0,0,0,
                                            0,0,1,1,
                                            1,1,0,0,
                                            0,0,1,0,
                                            0,0,1,0,
                                            0,0,1,0,
                                            0,0,1,0,
                                            0,0,1,0,
                                            0,0,1,1,
                                            1,1,1,1,
                                            1,1,0,0,
                                            
            }; 
            mapaMetalicas2 = new int[] {0,0,0,0,
                                        0,0,0,0,
                                        0,0,1,1,
                                        1,1,1,1,
                                        1,1,1,1,
                                        1,1,1,1,
                                        1,1,1,1,
                                        1,1,1,1,
                                        1,1,1,1,
                                        };
         //PosiciÃ³n de las bombas.
         //1 <- bomba en el suelo
         //2 <- bomba en la plataforma1
         //3 <- bomba en la plataforma2
            if (numeroVidas==3) {   //Con esto hacemos que solo se inicie al comenzar una fase. De otro modo las bombas se resetan al perder una vida.
            Bomba = new int [] {0,0,0,0,
                                0,0,0,0,
                                2,0,0,0,
                                0,0,0,0,
                                0,0,2,0,
                                0,0,0,0,
                                0,0,2,0,
                                0,0,0,0,
                                1,0,3,0,
                                1,2,2,0,
                                0,0,0,0,
                                1,0,0,0,
            };
            }
         //Vale exactamente igual que el normal, pero no se edita.
         //Sirve para reinicar los valores de las bombas.
            BombaReset = new int [] {0,0,0,0,
                                0,0,0,0,
                                2,0,0,0,
                                0,0,0,0,
                                0,0,2,0,
                                0,0,0,0,
                                0,0,2,0,
                                0,0,0,0,
                                1,0,0,0,
                                0,0,0,3,
                                1,0,0,0,
                                2,2,0,0,
                                1,0,0,0, 
            }; 
            NinjaPosX = new int [] {anchuraLadrillo1*24, anchuraLadrillo1*32, anchuraLadrillo1*40, anchuraLadrillo1*48, anchuraLadrillo1*56, anchuraLadrillo1*64};
            
          //Los 1 no cuentan. Son una referencia para saber dÃ³nde estÃ¡n las plataformas metÃ¡licas.
          //Los 2 son los ninjas. Deben tener tres 1 a su izquierda, pues avanza tres casillas.
            Ninja2PosX = new int [] {0,0,0,0,
                                     0,0,0,0,
                                     0,0,0,0,
                                     0,0,0,0,
                                     0,0,0,0,
                                     0,0,0,0,
                                     0,0,0,0,
                                     0,0,0,0,
                                     0,0,0,0,
                                     0,0,2,2,
                                     2,2,0,0,       
            };
            
        }   
//    
        if (FASEAJUGAR == FASE32) {
            mapaLadrillo = new int[] {1,1,1,1,1,1,1,1,
                                      1,1,1,1,1,1,1,1,
                                      1,1,1,1,1,1,1,1,
                                      1,1,1,1,1,1,1,1,
                                      0,0,0,0,0,0,0,0,
                                      1,1,1,1,1,1,1,1,
                                      1,1,1,1,1,1,1,1,
                                      1,1,1,1,1,1,1,1,
                                      0,0,0,0,0,0,0,0,
                                      1,1,1,1,1,1,1,1,
                                      0,0,0,0,0,0,0,0,
                                      0,0,0,0,0,0,0,0,
                                      0,0,0,0,0,0,0,0,
            };
            mapaTiendas = new int [] {3,0,1,0,0,0,2,0,0,3};
            mapaVentanas = new int [] {1,1,1,1,0,1,1,1,0,1};
            mapaSenefas = new int []   {0,0,0,0,
                                        1,1,1,1,
                                        0,0,0,0,
                                        1,1,1,1,
                                        0,0,0,0,
                                        1,1,1,1,
                                        0,0,0,0,
                                        1,1,1,1,
                                        };
            mapaMetalicas1 = new int []    {0,0,0,0,
                                            0,0,1,1,
                                            1,1,0,0,
                                            1,1,1,1,
                                            0,0,0,0,
                                            1,1,1,1,
                                            1,0,0,0,
                                            1,0,0,1,
                                            1,0,0,0,
                                            0,0,0,1,
                                            1,1,1,1
            }; 
            mapaMetalicas2 = new int[] {0,0,0,0,
                                        0,0,0,1,
                                        1,0,0,0,
                                        0,1,1,0,
                                        0,0,0,0,
                                        0,1,1,0,
                                        0,0,0,0,
                                        0,0,0,0,
                                        0,0,0,1
                                        };
         //PosiciÃ³n de las bombas.
         //1 <- bomba en el suelo
         //2 <- bomba en la plataforma1
         //3 <- bomba en la plataforma2
            if (numeroVidas==3) {   //Con esto hacemos que solo se inicie al comenzar una fase. De otro modo las bombas se resetan al perder una vida.
            Bomba = new int [] {0,0,0,0,
                                0,0,0,2,
                                0,0,0,0,
                                0,3,2,0,
                                0,0,0,0,
                                0,2,0,0,
                                0,0,0,0,
                                2,0,0,0,
                                0,0,0,3,
                                0,0,0,0,
                                0,2,2,1,
                                
            };
            }
         //Vale exactamente igual que el normal, pero no se edita.
         //Sirve para reinicar los valores de las bombas.
            BombaReset = new int [] {0,0,0,0,
                                0,0,0,2,
                                0,0,0,0,
                                0,3,2,0,
                                0,0,0,0,
                                0,2,0,0,
                                0,0,0,0,
                                2,0,0,0,
                                0,0,0,3,
                                0,0,0,0,
                                0,2,2,1,
            }; 
            NinjaPosX = new int [] {anchuraLadrillo1*24, anchuraLadrillo1*32, anchuraLadrillo1*40, anchuraLadrillo1*48, anchuraLadrillo1*56, anchuraLadrillo1*64, anchuraLadrillo1*85};
            
          //Los 1 no cuentan. Son una referencia para saber dÃ³nde estÃ¡n las plataformas metÃ¡licas.
          //Los 2 son los ninjas. Deben tener tres 1 a su izquierda, pues avanza tres casillas.
            Ninja2PosX = new int [] {0,0,0,0,
                                     0,0,0,0,
                                     0,2,0,0,
                                     0,0,0,2,
                                     0,0,0,0,
                                     0,0,0,2,
                                     2,0,0,0,
                                     0,0,0,0,
                                     0,0,0,0,
                                     0,0,0,0,
                                     0,0,2,2,
            };
            
        }  
//    
        if (FASEAJUGAR == FASE33) {
            mapaLadrillo = new int[] {1,1,1,1,1,1,1,1,
                                      0,0,0,0,0,0,0,0,
                                      1,1,1,1,1,1,1,1,
                                      1,1,1,1,1,1,1,1,
                                      0,0,0,0,0,0,0,0,
                                      1,1,1,1,1,1,1,1,
                                      1,1,1,1,1,1,1,1,
                                      0,0,0,0,0,0,0,0,
                                      0,0,0,0,0,0,0,0,
                                      1,1,1,1,1,1,1,1,
                                      0,0,0,0,0,0,0,0,
                                      1,1,1,1,1,1,1,1,
                                      1,1,1,1,1,1,1,1,
            };
            mapaTiendas = new int [] {0,0,1,0,0,0,3,0,0,0,0,1};
            mapaVentanas = new int [] {1,0,1,1,0,1,1,0,0,1,0,1,1};
            mapaSenefas = new int []   {1,1,1,1,
                                        0,0,0,0,
                                        0,0,0,0,
                                        1,1,1,1,
                                        0,0,0,0,
                                        1,1,1,1,
                                        0,0,0,0,
                                        0,0,0,0,
                                        0,0,0,0,
                                        1,1,1,1,
                                        0,0,0,0,
                                        1,1,1,1,
                                        };
            mapaMetalicas1 = new int []    {0,0,0,0,
                                            0,0,0,1,
                                            1,1,1,1,
                                            1,1,1,1,
                                            1,0,0,0,
                                            0,0,0,0,
                                            0,0,0,1,
                                            1,1,1,1,
                                            0,0,0,0,
                                            1,0,0,0,
                                            1,1,1,1,
                                            1,0,0,0
                                            
            }; 
            mapaMetalicas2 = new int[] {0,0,0,0,
                                        0,0,0,0,
                                        0,1,0,1,
                                        0,1,0,0,
                                        0,0,0,0,
                                        1,1,0,0,
                                        0,0,0,0,
                                        0,0,0,0,
                                        0,0,1,1,
                                        1,0,0,0
                                        };
         //PosiciÃ³n de las bombas.
         //1 <- bomba en el suelo
         //2 <- bomba en la plataforma1
         //3 <- bomba en la plataforma2
            if (numeroVidas==3) {   //Con esto hacemos que solo se inicie al comenzar una fase. De otro modo las bombas se resetan al perder una vida.
            Bomba = new int [] {0,0,0,0,
                                0,0,0,0,
                                0,3,0,2,
                                0,3,0,0,
                                0,0,0,0,
                                0,3,0,0,
                                0,0,0,0,
                                0,2,0,0,
                                0,0,0,3,
                                2,1,0,0,
                                0,0,2,0                  
            };
            }
         //Vale exactamente igual que el normal, pero no se edita.
         //Sirve para reinicar los valores de las bombas.
            BombaReset = new int [] {0,0,0,0,
                                0,0,0,0,
                                0,3,0,2,
                                0,3,0,0,
                                0,0,0,0,
                                0,3,0,0,
                                0,0,0,0,
                                0,2,0,0,
                                0,0,0,3,
                                2,1,0,0,
                                0,0,2,0
            }; 
            NinjaPosX = new int [] {anchuraLadrillo1*25, anchuraLadrillo1*30, anchuraLadrillo1*40, anchuraLadrillo1*45, anchuraLadrillo1*50, anchuraLadrillo1*60, anchuraLadrillo1*70, anchuraLadrillo1*80, anchuraLadrillo1*85};
            
          //Los 1 no cuentan. Son una referencia para saber dÃ³nde estÃ¡n las plataformas metÃ¡licas.
          //Los 2 son los ninjas. Deben tener tres 1 a su izquierda, pues avanza tres casillas.
            Ninja2PosX = new int [] {       0,0,0,0,
                                            0,0,0,1,
                                            1,1,2,2,
                                            1,1,2,2,
                                            1,0,0,0,
                                            0,0,0,0,
                                            0,0,0,1,
                                            1,1,2,2,
                                            0,0,0,0,
                                            1,0,0,0,
                                            1,1,1,2,
                                            2,0,0,0
                                     
            };
            
        }        
    }
       
//LANGUAGE RMS ->

   static public void iniciarLenguaje() {

            openLanguage(); //Abrimos los datos de la partida guardada.
  
            mylanguage=readLanguage(); //Primeramente habremos creado esta variable donde volcaremos los datos.
            
            if(mylanguage==null || mylanguage.equals("error") || mylanguage.equals(""))
            {    
                noLanguage=true; //Booleana que controla la activaciÃ³n o no del menÃº de idioma.
            }    
            else
            {    
                closeLanguage(); //Cerramos la partida guardada. Importante para no malgastar recursos.
                if(mylanguage.equals("ingles"))
                {
                    Define.Language=0;
                } 
                else if(mylanguage.equals("ingles"))
                {    
                    Define.Language=1;
                }    
                else if(mylanguage.equals("brasil"))
                {    
                    Define.Language=2;
                }
            }
    }

   static public void openLanguage()
   {
        try
        {
            gRecordLang = RecordStore.openRecordStore("finalkombat",true);   //IMPORTANTE. El "AOMLang" debe ser el nobre del juego. De lo contrario podemos sobreescribir las partidas de otros juegos.
        }
        catch (RecordStoreException e)
        {
        System.out.println("Error al abrir el Record Store");
        }
    }
   
   static public void writeLanguage(String entrada)
   {
        byte[] registro;
        registro = entrada.getBytes();
        try
        {
            if(gRecordLang.getNumRecords()<1)
                gRecordLang.addRecord(registro,0,registro.length);
            else
                gRecordLang.setRecord(1, registro, 0, registro.length);
        }
        catch (RecordStoreException e)
        {
            System.out.println("Error al insertar registro");
        }
    }
   
   static public String readLanguage()
   {
        byte[] registro = new byte[75];
        int longitud;
        String language="";
        try
        {
            for (int i=1;i<=gRecordLang.getNumRecords();i++)
            {
                longitud = gRecordLang.getRecordSize(i);
                registro = gRecordLang.getRecord(i);
                language = new String(registro,0,longitud);
                System.out.println("Registro "+i+": "+ language);
            }
        }
        catch (RecordStoreException e)
        {
            System.out.println("Error al leer los registros");
            return "error";
        }
        registro = null;
        return language;
    }
   
   static public void closeLanguage()
   {
        try
        {
            gRecordLang.closeRecordStore();
        }
        catch (RecordStoreException e)
        {
            System.out.println("Error al cerrar el Record Store");
        }
    }
   
   static public void destroyLanguage()
   {
        try
        {
            RecordStore.deleteRecordStore("finalkombat");  //IMPORTANTE:Â¡Â¡Cambiar el nombre!!
        }
        catch (RecordStoreException e)
        {
            System.out.println("Error al eliminar el Record Store");
        }
    }
//LANGUAGE RMS <-

   
//MEMORYCARD RMS ->

   static public void iniciarMemoryCard() {

            openMemoryCard(); //Abrimos los datos de la partida guardada.
  
            myLevelCompleted=readMemoryCard(); //Primeramente habremos creado esta variable donde volcaremos los datos.
            
            if(myLevelCompleted==null || myLevelCompleted.equals("error") || myLevelCompleted.equals("fase11") || myLevelCompleted.equals(""))
            {    
                partidaGuardada=false; //Booleana que controla la activaciÃ³n o no del menÃº de idioma.
            }    
            else
            {
                partidaGuardada=true;
                closeMemoryCard(); //Cerramos la partida guardada. Importante para no malgastar recursos.
                if(myLevelCompleted.equals("fase12"))
                {
                    FASEAJUGAR=FASE12;
                }
                if(myLevelCompleted.equals("fase13"))
                {
                    FASEAJUGAR=FASE13;
                }
                if(myLevelCompleted.equals("fase21"))
                {
                    FASEAJUGAR=FASE21;
                }
                if(myLevelCompleted.equals("fase22"))
                {
                    FASEAJUGAR=FASE22;
                }
                if(myLevelCompleted.equals("fase23"))
                {
                    FASEAJUGAR=FASE23;
                }
                if(myLevelCompleted.equals("fase31"))
                {
                    FASEAJUGAR=FASE31;
                }
                if(myLevelCompleted.equals("fase32"))
                {
                    FASEAJUGAR=FASE32;
                }
                if(myLevelCompleted.equals("fase33"))
                {
                    FASEAJUGAR=FASE33;
                }
            }
       
    }

   static public void openMemoryCard()
   {
        try
        {
            gRecordMemoryCard = RecordStore.openRecordStore("FKMemoryCard",true);   //IMPORTANTE. El "AOMLang" debe ser el nobre del juego. De lo contrario podemos sobreescribir las partidas de otros juegos.
        }
        catch (RecordStoreException e)
        {
        System.out.println("Error al abrir el Record Store");
        }
    }
   
   static public void writeMemoryCard(String entrada)
   {
        byte[] registro;
        registro = entrada.getBytes();
        try
        {
            if(gRecordMemoryCard.getNumRecords()<1)
                gRecordMemoryCard.addRecord(registro,0,registro.length);
            else
                gRecordMemoryCard.setRecord(1, registro, 0, registro.length);
        }
        catch (RecordStoreException e)
        {
            System.out.println("Error al insertar registro");
        }
    }
   
   static public String readMemoryCard()
   {
        byte[] registro = new byte[75];
        int longitud;
        String partida="";
        try
        {
            for (int i=1;i<=gRecordMemoryCard.getNumRecords();i++)
            {
                longitud = gRecordMemoryCard.getRecordSize(i);
                registro = gRecordMemoryCard.getRecord(i);
                partida = new String(registro,0,longitud);
                System.out.println("Registro "+i+": "+ partida);
            }
        }
        catch (RecordStoreException e)
        {
            System.out.println("Error al leer los registros");
            return "error";
        }
        registro = null;
        return partida;
    }
   
   static public void closeMemoryCard()
   {
        try
        {
            gRecordMemoryCard.closeRecordStore();
        }
        catch (RecordStoreException e)
        {
            System.out.println("Error al cerrar el Record Store");
        }
    }
   
   static public void destroyMemoryCard()
   {
        try
        {
            RecordStore.deleteRecordStore("FKMemoryCard");  //IMPORTANTE:Â¡Â¡Cambiar el nombre!!
        }
        catch (RecordStoreException e)
        {
            System.out.println("Error al eliminar el Record Store");
        }
    }
//LANGUAGE RMS <-   
   
/*   
public void WriteDesbloqueadas(String entrada)
    {
        byte[] registro;
        registro = entrada.getBytes();
        RecordStore rs = null;
        try 
        {
            rs = RecordStore.openRecordStore("finalkombat", true);  
              if(rs.getNumRecords()>0 && rs.getNumRecords()<2)  
                rs.addRecord(registro,0,registro.length);
            else
                rs.setRecord(2, registro, 0, registro.length);            
            rs.closeRecordStore();
            
        }catch(Exception e){}    
   }   
   * */
   
public void hideNotify()
    { 
        stopMusica();
	pause=true;
    }
public void showNotify()
    { 
	if (pause==true) {
        //comenzarMusica();
        if (ESTADO != JUGAR) {
	pause=false;
        }
        if (ESTADO == MENU || ESTADO == MENUANIMADO) {
        	SndManager.PlayMusic(SndManager.MUSIC_MAINMENU, true);
        }
	}
    }

//BlackBerry//
//Para que no se queje al desactivar los botones llamada y colgar de BB
 //y activar el KeyListener
   public boolean trackwheelRoll(int a, int b, int c) {
      return false;
   }
   public boolean trackwheelUnclick(int _iStatus, int _iTime) {
      return true;
   }
   public boolean trackwheelClick(int _iStatus, int _iTime) {
      return true;
      
   }

   public boolean keyStatus (int _iKeyCode, int _iTime) {
      return false;
   }
   public boolean keyRepeat (int _iKeyCode, int _iTime) {
      return false;
   }
   public boolean keyChar (char _iChar, int _iKeyCode, int _iTime) {
      return false;
   }

   public boolean keyDown (int keycode, int time) {
       if (keycode==KEYCODE_SK_LEFT){
       keyPressed(KEYCODE_SK_LEFT);
       return true;}
       
       if (keycode==KEYCODE_SK_RIGHT) {
       keyPressed(KEYCODE_SK_RIGHT);
       return true;}
       return false;
   }

   public boolean keyUp(int keycode, int time) {
       if (keycode==KEYCODE_SK_LEFT) {
       keyReleased(KEYCODE_SK_LEFT);
       return true;}
       
       if (keycode==KEYCODE_SK_RIGHT) {
       keyReleased(KEYCODE_SK_RIGHT);
       return true;}
     return false;
   }



}

