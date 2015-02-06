package com.kitmaker.finalkombat;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author josemariaclimentmartinez
 */
public class Define {
    
    public static byte Language = 1; //El idioma. 0 es inglés. 1 es español.
    public static byte NumeroIdiomas = 3; //Número de idiomas.
    public static final String[][] menuIdiSon = {
        {"language","language"}, //0
        {"english","english"}, //1
        {"espa�ol","espa�ol"}, //2
        
        {"sound","sonido","som"}, //3
        {"no","no","_naCo"}, //4
        {"yes","si","sim"}, //5
        
        {"brasileiro","brasileiro"}, //6
    };
    public static final String[][] menuAdvertencia = {
        {"you_have_a_previous","tiene_una_partidada","_estaA_com_um_desafio"}, //0
        {"game._if_you_continue_all","anterior._si_continua","anterior._se_continuar"}, //1
        {"data_will_be_deleted","se_borraran_los_datos","os_dados_foram_apagados"}, //2
        
        {"delete","borrar","apagar"}, //3
        {"back_to_menu","volver_al_menu","voltar_ao_menu"}, //4
    };    

    public static final String[][] intro = {
        {"year_20xx","a�o_20xx","ano_20xx"}, //0
        {"metrocity_has_been_taken","la_ciudad_de_metrocity","a_cidade_de_metrocity"}, //1
        {"by_criminal_bands.","ha_sido_tomada_por","foi_invadida_por"}, //2
        {"mr.k_and_his_ciberninjas","bandas_criminales.","gangues_criminais."}, //3
        {"control_the_streets.","mr.k_y_sus_cyberninjas","mr.k_e_suas_cyberninjas"}, //4
        {"only_a_corageous_man","controlan_las_calles.","controlam_as_ruas."}, //5
        {"can_stop_him.","solo_un_hombre_con","soA_um_homem_com"}, //6
//#if !s60dp5800        
//#         {"his_nickname:_j.a.x.","coraje_puede_detenerle."}, //7
//#elif s60dp5800      
        {"his_nickname:_j.a.x.","coraje_puede_pararle.","coragem_pode_paraAlos."}, //7
//#endif        
        {"","su_nombre_en_clave:","seu_nome_em_senha:"}, //8
        {"","j.a.x.","j.a.x."}, //9
        {"","",""}, //10
    };  
    
    public static final String[][] ending = {
        {"congratulations","enhorabuena","em_boa_hora"}, //0
        {"you_saved_the_city","has_salvado_a_la","salvou_a"}, //1
        {"from_total_destruction.","ciudad_de_la","cidade_da"}, //2
        {"metrocity_goes_back","destruccion_total.","destruDaCo_total."}, //3
        {"to_normal_but_you","metrocity_vuelve_a","metrocity_volta_aA"}, //4
        {"couldnt_catch","la_normalidad_pero","normalidad_mas"}, //5
        {"mr.k.","no_pudiste_atrapar","naCo_podia_pegar"}, //6
        {"you_know_the_threat","a_mr.k.","o_mr.k."}, //7
        {"will_come_back","sabes_que_pronto","sabe_que_logo"}, //8
        {"soon._luckily","volvera_la_amenaza.","voltaraA_a_ameaDa."}, //9
        {"you_are_a_man","afortunadamente_eres","felizmente_eA"}, //10
        {"of_action_and_you","un_hombre_de_accion","um_homem_de_aDaCo"}, //11
        {"have_no_fear.","y_no_temes_a_nada.","e_naCo_teme_nada."}, //12
        {"the_final_combat","el_combate_final","o_combate_final"}, //13
        {"still_awaits.","aun_te_espera.","ainda_est�_por_vir."}, //14
    };     
    
        public static final String[][] menu = {
            {"final_","final_","final_"}, //0
            {"__kombat","__kombat","__kombat"}, //1
            {"continue","continuar","continuar"}, //2
            {"new_game","nuevo_juego","novo_jogo"}, //3
            {"options","opciones","_opDoCes"}, //4
            {"instructions","instrucciones","_instruDoCes"}, //5
            {"info","info","info"}, //6
            {"exit","salir","sair"}, //7
            {"sound_yes","sonido_si","som_si"}, //8
            {"lang._english","idioma_espa�ol","idioma_brasileiro"}, //9
            {"exit","salir","sair"}, //10
            {"sound_no","sonido_no","_som_naCo"}, //11
        };
        public static final String[][] about = {
            {"info","info","info"}, //0
            {"final_kombat_v1.0","final_kombat_v1.0","final_kombat_v1.0"}, //1
            {"developed_by","developed_by","developed_by"}, //2
            {"kitmaker_entertainment","kitmaker_entertainment","kitmaker_entertainment"}, //3
            {"copyright_2012","copyright_2012","copyright_2012"}, //4
        };
        public static final String[][] menuPausa = {
            {"continue","continuar","continuar"}, //0
            {"exit_to_menu","salir_al_menu","sair_do_menu"}, //1
            {"exit_game","salir_del_juego","sair_do_juego"}, //2
        };
//#if TouchScreen!="true"        
//#         public static final String[][] instrucciones = {
//#         {"instructions","instrucciones"}, //0
//#         {"move_around_pressing","muevete_pulsando_los"}, //1
//#         {"the_following_controls:","siguientes_controles:"}, //2
//#         {"4/left_and_5/right_to","4/izq._y_5/der._para"}, //3
//#         {"move_and_5/ok_to_hit.","desplazarte_y_5/ok"}, //4
//#         {"jump_pressing_2/up","para_golpear."}, //5
//#         {"deactivate_all_bombs","salta_pulsado_2/arriba."}, //6
//#         {"to_move_on_to","desactiva_todas_las"}, //7
//#         {"the_next_level.","bombas_para_pasar"}, //8
//#         {"","de_nivel."}, //9
//#elif TouchScreen=="true"
        public static final String[][] instrucciones = {
        	{"instructions","instrucciones","instruDoCes"}, //0
            {"use_the_virtual_pad","pulsa_la_izquierda","clique_a_esquerda"}, //1
            {"to_move_around.","o_derecha_para","ou_direita_para"}, //2
            {"press_the_red","moverte.","moverse."}, //3
            {"button_to_hit_and","pulsa_el_boton","clique_o_botaCo"}, //4
            {"the_up_button_to","rojo_para_golpear_y","vermelho_para_bater_e"}, //5
            {"jump._deactivate","arriba_para_saltar.","para_cima_para_saltar."}, //6
            {"all_bombs_to","desactiva_todas_las","desativa_todas_as"}, //7
            {"move_on_to_the","bombas_para_pasar","bombas_para_passar"}, //8
            {"next_level.","de_nivel.","de_nivel."}, //9  
        
//#endif       
    };     
        public static final String[][] finFase = {
        	{"completed","completado","completado"}, //0    
            {"level","nivel","nivel"}, //1
            {"time_level","time_level","time_level"}, //2
            {"score","score","score"}, //3
            {"press_on_screen","pulsa_la_pantalla_para","clique_na_tela_para"}, //4
            {"to_continue","continuar","continuar"}, //5 
//#endif        
     };
        public static final String[][] menuMuerte = {
        	 {"k.o.","has_quedado_k.o.","ficou_k.o."}, //0    
             {"you_have_","te_quedan_","faltam_"}, //1
             {"_trials_left","_intentos","_provas"}, //2
             {"press_on_screen","pulsa_la_pantalla_para","clique_na_tela_para"}, //3
             {"to_continue","continuar","continuar"}, //4   
//#endif        
        };
    
    
//#if ScreenWidth==240 && ScreenHeight==320

//#elif ScreenWidth==240 && ScreenHeight==299
//#     public static final int SCR_HEIGHT = 299;
//#     public static final int SCR_WIDTH = 240;
//#     
//#     public static final int FPS = 25;
//#        
//#     public static final boolean TOUCHSCREEN = false;
//#         
//#     public static final int margenYmenuPrin = 100;
//#      
//#     public static final int alturaSegundaPlataformaMetalica = 120; //Altura plataforma alta. Más alto el valo, más bajo.
//#  
//#     public static final int limiteInteligentePintado = 0;
//# 
//#     public static final int fuerzaSalto = 20;
//# 
//#     public static final int saltoDesplazMax = 10;   
//# 
//#     public static final int avanceXNinja1 = 2;       
//# 
//#     public static final int velocidadDuke = 3;
//#        
//#     public static final int VELOCIDADANIMACION = 5;
//#        
//#     public static final int VELOCIDADANIMACIONNINJA = 5;
//# 
//#     public static final int correctorAlturaPrimeraPlataforma = 10; 
//# 
//#     public static final int CorrectorTexto = 0; //Para recorregir algunos textos.
//#        
//#     public static final int SeparacionEntreLineasIntro = 30;  
//#    
//#     public static int CorrectorIngles = 0;
//#elif ScreenWidth==240 && ScreenHeight==298
//#     public static final int SCR_HEIGHT = 298;
//#     public static final int SCR_WIDTH = 240;
//#     
//#     public static final int FPS = 25;
//#        
//#     public static final boolean TOUCHSCREEN = false;
//#         
//#     public static final int margenYmenuPrin = 90;
//#      
//#     public static final int alturaSegundaPlataformaMetalica = 85; //Altura plataforma alta. Más alto el valo, más bajo.
//#  
//#     public static final int limiteInteligentePintado = 0;
//# 
//#     public static final int fuerzaSalto = 20;
//# 
//#     public static final int saltoDesplazMax = 10;   
//# 
//#     public static final int avanceXNinja1 = 2;       
//# 
//#     public static final int velocidadDuke = 3;
//#        
//#     public static final int VELOCIDADANIMACION = 5;
//#        
//#     public static final int VELOCIDADANIMACIONNINJA = 5;
//# 
//#     public static final int correctorAlturaPrimeraPlataforma = 40; 
//# 
//#     public static final int CorrectorTexto = 0; //Para recorregir algunos textos.
//#        
//#     public static final int SeparacionEntreLineasIntro = 30;  
//#    
//#     public static int CorrectorIngles = 0;
//#     
//#     public static int tactilCorreccion = 0;  
//#     
//#     
//#elif ScreenWidth==240 && ScreenHeight==297
//#     public static final int SCR_HEIGHT = 297;
//#     public static final int SCR_WIDTH = 240;
//#     
//#     public static final int FPS = 25;
//#        
//#     public static final boolean TOUCHSCREEN = false;
//#         
//#     public static final int margenYmenuPrin = 100;
//#      
//#     public static final int alturaSegundaPlataformaMetalica = 120; //Altura plataforma alta. Más alto el valo, más bajo.
//#  
//#     public static final int limiteInteligentePintado = 0;
//# 
//#     public static final int fuerzaSalto = 20;
//# 
//#     public static final int saltoDesplazMax = 10;   
//# 
//#     public static final int avanceXNinja1 = 2;       
//# 
//#     public static final int velocidadDuke = 3;
//#        
//#     public static final int VELOCIDADANIMACION = 5;
//#        
//#     public static final int VELOCIDADANIMACIONNINJA = 5;
//# 
//#     public static final int correctorAlturaPrimeraPlataforma = 10; 
//# 
//#     public static final int CorrectorTexto = 0; //Para recorregir algunos textos.
//#        
//#     public static final int SeparacionEntreLineasIntro = 30;  
//#    
//#     public static int CorrectorIngles = 0;  
//#elif ScreenWidth==240 && ScreenHeight==304
//#     public static final int SCR_HEIGHT = 304;
//#     public static final int SCR_WIDTH = 240;
//#     
//#     public static final int FPS = 25;
//#        
//#     public static final boolean TOUCHSCREEN = false;
//#         
//#     public static final int margenYmenuPrin = 100;
//#      
//#     public static final int alturaSegundaPlataformaMetalica = 100; //Altura plataforma alta. Más alto el valo, más bajo.
//#  
//#     public static final int limiteInteligentePintado = 0;
//# 
//#     public static final int fuerzaSalto = 20;
//# 
//#     public static final int saltoDesplazMax = 10;   
//# 
//#     public static final int avanceXNinja1 = 2;       
//# 
//#     public static final int velocidadDuke = 3;
//#        
//#     public static final int VELOCIDADANIMACION = 5;
//#        
//#     public static final int VELOCIDADANIMACIONNINJA = 5;
//# 
//#     public static final int correctorAlturaPrimeraPlataforma = 10; 
//# 
//#     public static final int CorrectorTexto = 0; //Para recorregir algunos textos.
//#        
//#     public static final int SeparacionEntreLineasIntro = 30;  
//#    
//#     public static int CorrectorIngles = 0;         
//#elif ScreenWidth==240 && ScreenHeight==260
//#     public static final int SCR_HEIGHT = 260;
//#     public static final int SCR_WIDTH = 240;
//#     
//#     public static final int FPS = 25;
//#        
//#     public static final boolean TOUCHSCREEN = false;
//#         
//#     public static final int margenYmenuPrin = 100;
//#      
//#     public static final int alturaSegundaPlataformaMetalica = 90; //Altura plataforma alta. Más alto el valo, más bajo.
//#  
//#     public static final int limiteInteligentePintado = 0;
//# 
//#     public static final int fuerzaSalto = 20;
//# 
//#     public static final int saltoDesplazMax = 10;   
//# 
//#     public static final int avanceXNinja1 = 2;       
//# 
//#     public static final int velocidadDuke = 3;
//#        
//#     public static final int VELOCIDADANIMACION = 5;
//#        
//#     public static final int VELOCIDADANIMACIONNINJA = 5;
//# 
//#     public static final int correctorAlturaPrimeraPlataforma = -8; 
//# 
//#     public static final int CorrectorTexto = 0; //Para recorregir algunos textos.
//#        
//#     public static final int SeparacionEntreLineasIntro = 30;  
//#    
//#     public static int CorrectorIngles = 0;      
//#elif ScreenWidth==240 && ScreenHeight==260
//#     public static final int SCR_HEIGHT = 260;
//#     public static final int SCR_WIDTH = 240;
//#     
//#     public static final int FPS = 25;
//#        
//#     public static final boolean TOUCHSCREEN = false;
//#         
//#     public static final int margenYmenuPrin = 100;
//#      
//#     public static final int alturaSegundaPlataformaMetalica = 120; //Altura plataforma alta. Más alto el valo, más bajo.
//#  
//#     public static final int limiteInteligentePintado = 0;
//# 
//#     public static final int fuerzaSalto = 20;
//# 
//#     public static final int saltoDesplazMax = 10;   
//# 
//#     public static final int avanceXNinja1 = 2;       
//# 
//#     public static final int velocidadDuke = 3;
//#        
//#     public static final int VELOCIDADANIMACION = 5;
//#        
//#     public static final int VELOCIDADANIMACIONNINJA = 5;
//# 
//#     public static final int correctorAlturaPrimeraPlataforma = 10; 
//# 
//#     public static final int CorrectorTexto = 0; //Para recorregir algunos textos.
//#        
//#     public static final int SeparacionEntreLineasIntro = 30;  
//#    
//#     public static int CorrectorIngles = 0;        
//#elif ScreenWidth==352 && ScreenHeight==416
//#     public static final int SCR_HEIGHT = 320;
//#     public static final int SCR_WIDTH = 180;
//#     
//#     public static final int FPS = 20;
//#        
//#     public static final boolean TOUCHSCREEN = false;
//#         
//#     public static final int margenYmenuPrin = 80;
//#      
//#     public static final int alturaSegundaPlataformaMetalica = 90; //Altura plataforma alta. Más alto el valo, más bajo.
//#  
//#     public static final int limiteInteligentePintado = - 3;
//# 
//#     public static final int fuerzaSalto = 8;
//# 
//#     public static final int saltoDesplazMax = 13;   
//# 
//#     public static final int avanceXNinja1 = 4;       
//# 
//#      public static final int velocidadDuke = 5;
//#      
//#      public static final int VELOCIDADANIMACION = 2;
//#        
//#     public static final int VELOCIDADANIMACIONNINJA = 3;
//# 
//#     public static final int correctorAlturaPrimeraPlataforma = 55; 
//# 
//#     public static final int CorrectorTexto = 0; //Para recorregir algunos textos.
//#        
//#     public static final int SeparacionEntreLineasIntro = 30;  
//#    
//#     public static int CorrectorIngles = 0;  
//#     
//#     public static int tactilCorreccion = 0;   
//#elif ScreenWidth==240 && ScreenHeight==400
//#     public static final int SCR_HEIGHT = 400;
//#     public static final int SCR_WIDTH = 240;
//#     
//#     public static final int FPS = 20;
//#        
//#     public static final boolean TOUCHSCREEN = true;
//#         
//#     public static final int margenYmenuPrin = 80;
//#      
//#     public static final int alturaSegundaPlataformaMetalica = 100; //Altura plataforma alta. Más alto el valo, más bajo.
//#  
//#     public static final int limiteInteligentePintado = 0;
//# 
//#     public static final int fuerzaSalto = 9;
//# 
//#     public static final int saltoDesplazMax = 15;   
//# 
//#     public static final int avanceXNinja1 = 4;       
//# 
//#      public static final int velocidadDuke = 5;
//#      
//#      public static final int VELOCIDADANIMACION = 2;
//#        
//#     public static final int VELOCIDADANIMACIONNINJA = 3;
//# 
//#     public static final int correctorAlturaPrimeraPlataforma = 80; 
//# 
//#     public static final int CorrectorTexto = 0; //Para recorregir algunos textos.
//#        
//#     public static final int SeparacionEntreLineasIntro = 30;  
//#    
//#     public static int CorrectorIngles = 0;  
//#     
//#     public static int tactilCorreccion = 0;     
//#elif ScreenWidth==360 && ScreenHeight==480
//#     public static final int SCR_HEIGHT = 480;
//#     public static final int SCR_WIDTH = 360;
//#     
//#     public static final int FPS = 25;
//#        
//#     public static final boolean TOUCHSCREEN = false;
//#         
//#     public static final int margenYmenuPrin = 125;
//#      
//#     public static final int alturaSegundaPlataformaMetalica = 110; //Altura plataforma alta. Más alto el valo, más bajo.
//#  
//#     public static final int limiteInteligentePintado = - 11;
//# 
//#     public static final int fuerzaSalto = 15;
//# 
//#     public static final int saltoDesplazMax = 15;   
//# 
//#     public static final int avanceXNinja1 = 5;       
//# 
//#     public static final int velocidadDuke = 7;
//#        
//#     public static final int VELOCIDADANIMACION = 3;
//#        
//#     public static final int VELOCIDADANIMACIONNINJA = 3;
//# 
//#     public static final int correctorAlturaPrimeraPlataforma = 70; 
//# 
//#     public static final int CorrectorTexto = 0; //Para recorregir algunos textos.
//#        
//#     public static final int SeparacionEntreLineasIntro = 30;  
//#    
//#     public static int CorrectorIngles = 0; 
//#     
//#     public static int tactilCorreccion = 0; 
//#elif ScreenWidth==320 && ScreenHeight==480
        public static final int SCR_HEIGHT = 480;
        public static final int SCR_WIDTH = 320;
        
        public static final int FPS = 20;
           
        public static final boolean TOUCHSCREEN = false;
            
        public static final int margenYmenuPrin = 125;
         
        public static final int alturaSegundaPlataformaMetalica = 110; //Altura plataforma alta. M�s alto el valo, m�s bajo.
     
        public static final int limiteInteligentePintado = - 11;

        public static final int fuerzaSalto = 15;

        public static final int saltoDesplazMax = 15;   

        public static final int avanceXNinja1 = 5;       

        public static final int velocidadDuke = 8;
           
        public static final int VELOCIDADANIMACION = 2;
           
        public static final int VELOCIDADANIMACIONNINJA = 3;

        public static final int correctorAlturaPrimeraPlataforma = 70; 

        public static final int CorrectorTexto = 0; //Para recorregir algunos textos.
           
        public static final int SeparacionEntreLineasIntro = 30;  
       
        public static int CorrectorIngles = 0; 
        
        public static int tactilCorreccion = 0;           
//#elif ScreenWidth==640 && ScreenHeight==480
//#     public static final int SCR_HEIGHT = 480;
//#     public static final int SCR_WIDTH = 640;
//#     
//#     public static final int FPS = 25;
//#        
//#     public static final boolean TOUCHSCREEN = false;
//#         
//#     public static final int margenYmenuPrin = 125;
//#      
//#     public static final int alturaSegundaPlataformaMetalica = 110; //Altura plataforma alta. Más alto el valo, más bajo.
//#  
//#     public static final int limiteInteligentePintado = 0;
//# 
//#     public static final int fuerzaSalto = 15;
//# 
//#     public static final int saltoDesplazMax = 15;   
//# 
//#      public static final int avanceXNinja1 = 4;       
//#  
//#      public static final int velocidadDuke = 5;
//#         
//#      public static final int VELOCIDADANIMACION = 5;
//#         
//#      public static final int VELOCIDADANIMACIONNINJA = 5;
//# 
//#     public static final int correctorAlturaPrimeraPlataforma = 70; 
//# 
//#     public static final int CorrectorTexto = 0; //Para recorregir algunos textos.
//#        
//#     public static final int SeparacionEntreLineasIntro = 30;  
//#    
//#     public static int CorrectorIngles = 0;  
//#     
//#     public static int tactilCorreccion = 80;
//#elif ScreenWidth==480 && ScreenHeight==640
//#     public static final int SCR_HEIGHT = 640;
//#     public static final int SCR_WIDTH = 480;
//#     
//#     public static final int FPS = 25;
//#        
//#     public static final boolean TOUCHSCREEN = false;
//#         
//#     public static final int margenYmenuPrin = 170;
//#      
//#     public static final int alturaSegundaPlataformaMetalica = 200; //Altura plataforma alta. Más alto el valo, más bajo.
//#  
//#     public static final int limiteInteligentePintado = - 6;
//# 
//#     public static final int fuerzaSalto = 15;
//# 
//#     public static final int saltoDesplazMax = 15;   
//# 
//#     public static final int avanceXNinja1 = 5;       
//# 
//#     public static final int velocidadDuke = 7;
//#        
//#     public static final int VELOCIDADANIMACION = 3;
//#        
//#     public static final int VELOCIDADANIMACIONNINJA = 3;
//# 
//#     public static final int correctorAlturaPrimeraPlataforma = 110; 
//# 
//#     public static final int CorrectorTexto = 0; //Para recorregir algunos textos.
//#        
//#     public static final int SeparacionEntreLineasIntro = 30;  
//#    
//#     public static int CorrectorIngles = 0;
//#     
//#     public static int tactilCorreccion = 0;
//#elif ScreenWidth==480 && ScreenHeight==800
//#     public static final int SCR_HEIGHT = 800;
//#     public static final int SCR_WIDTH = 480;
//#     
//#     public static final int FPS = 25;
//#        
//#     public static final boolean TOUCHSCREEN = false;
//#         
//#     public static final int margenYmenuPrin = 190;
//#      
//#     public static final int alturaSegundaPlataformaMetalica = 250; //Altura plataforma alta. Más alto el valo, más bajo.
//#  
//#     public static final int limiteInteligentePintado = - 6;
//# 
//#     public static final int fuerzaSalto = 15;
//# 
//#     public static final int saltoDesplazMax = 15;   
//# 
//#     public static final int avanceXNinja1 = 5;       
//# 
//#     public static final int velocidadDuke = 7;
//#        
//#     public static final int VELOCIDADANIMACION = 3;
//#        
//#     public static final int VELOCIDADANIMACIONNINJA = 3;
//# 
//#     public static final int correctorAlturaPrimeraPlataforma = 220; 
//# 
//#     public static final int CorrectorTexto = 0; //Para recorregir algunos textos.
//#        
//#     public static final int SeparacionEntreLineasIntro = 30;  
//#    
//#     public static int CorrectorIngles = 0;      
//#     
//#     public static int tactilCorreccion = 0;
//#elif ScreenWidth==480 && ScreenHeight==360
//#     public static final int SCR_HEIGHT = 360;
//#     public static final int SCR_WIDTH = 480;
//#     
//#     public static final int FPS = 25;
//#        
//#     public static final boolean TOUCHSCREEN = false;
//#         
//#     public static final int margenYmenuPrin = 120;
//#      
//#     public static final int alturaSegundaPlataformaMetalica = 120; //Altura plataforma alta. Más alto el valo, más bajo.
//#  
//#     public static final int limiteInteligentePintado = -6;
//# 
//#     public static final int fuerzaSalto = 15;
//# 
//#     public static final int saltoDesplazMax = 15;   
//# 
//#     public static final int avanceXNinja1 = 5;       
//# 
//#     public static final int velocidadDuke = 7;
//#        
//#     public static final int VELOCIDADANIMACION = 3;
//#        
//#     public static final int VELOCIDADANIMACIONNINJA = 3;
//# 
//#     public static final int correctorAlturaPrimeraPlataforma = -30; 
//# 
//#     public static final int CorrectorTexto = 0; //Para recorregir algunos textos.
//#        
//#     public static final int SeparacionEntreLineasIntro = 30;  
//#    
//#     public static int CorrectorIngles = 0;         
//#elif ScreenWidth==320 && ScreenHeight==240
//#     public static final int SCR_HEIGHT = 240;
//#     public static final int SCR_WIDTH = 320;
//#     
//#     public static final int FPS = 25;
//#        
//#     public static final boolean TOUCHSCREEN = false;
//#         
//#     public static final int margenYmenuPrin = 80;
//#      
//#     public static final int alturaSegundaPlataformaMetalica = 60; //Altura plataforma alta. Más alto el valo, más bajo.
//#  
//#     public static final int limiteInteligentePintado = 5;
//# 
//#     public static final int fuerzaSalto = 20;
//# 
//#     public static final int saltoDesplazMax = 10;   
//# 
//#     public static final int avanceXNinja1 = 2;       
//# 
//#     public static final int velocidadDuke = 3;
//#        
//#     public static final int VELOCIDADANIMACION = 5;
//#        
//#     public static final int VELOCIDADANIMACIONNINJA = 5;
//# 
//#     public static final int correctorAlturaPrimeraPlataforma = 10; 
//# 
//#     public static final int CorrectorTexto = 0; //Para recorregir algunos textos.
//#        
//#     public static final int SeparacionEntreLineasIntro = 30;  
//#    
//#     public static int CorrectorIngles = 0;    
//#elif ScreenWidth==176 && ScreenHeight==208
//#     public static final int SCR_HEIGHT = 208;
//#     public static final int SCR_WIDTH = 176;
//#     
//#     public static final int FPS = 20;
//#     
//#     public static final boolean TOUCHSCREEN = false;
//#         
//#     public static final int margenYmenuPrin = 80;
//#     
//#     public static final int alturaSegundaPlataformaMetalica = 60; //Altura plataforma alta. Más alto el valo, más bajo.
//#     
//#     public static final int limiteInteligentePintado = - 4;
//#     
//#     public static final int fuerzaSalto = 10;
//#     
//#     public static final int saltoDesplazMax = 10;
//#     
//#     public static final int avanceXNinja1 = 3;
//#if !s60dp2hp    
//#     public static final int velocidadDuke = 5;
//#     
//#     public static final int VELOCIDADANIMACION = 2;
//#elif s60dp2hp
//#     public static final int velocidadDuke = 3;
//#     
//#     public static final int VELOCIDADANIMACION = 3;    
//#endif     
//#     public static final int VELOCIDADANIMACIONNINJA = 3;
//#     
//#     public static final int correctorAlturaPrimeraPlataforma = - 15; //Altura plataforma baja. Más alto el valor, más alto
//#     
//#     public static final int CorrectorTexto = 0; //Para recorregir algunos textos.
//#     
//#     public static final int SeparacionEntreLineasIntro = 30;
//#     
//#     public static int CorrectorIngles = 0;
//#elif ScreenWidth==176 && ScreenHeight==204
//#     public static final int SCR_HEIGHT = 204;
//#     public static final int SCR_WIDTH = 176;
//#     
//#     public static final int FPS = 20;
//#     
//#     public static final boolean TOUCHSCREEN = false;
//#         
//#     public static final int margenYmenuPrin = 80;
//#     
//#     public static final int alturaSegundaPlataformaMetalica = 60; //Altura plataforma alta. Más alto el valo, más bajo.
//#     
//#     public static final int limiteInteligentePintado = 14;
//#     
//#     public static final int fuerzaSalto = 10;
//#     
//#     public static final int saltoDesplazMax = 10;
//#     
//#     public static final int avanceXNinja1 = 2;
//# 
//#     public static final int velocidadDuke = 5;
//#      
//#     public static final int VELOCIDADANIMACION = 2;    
//#   
//#     public static final int VELOCIDADANIMACIONNINJA = 3;
//#     
//#     public static final int correctorAlturaPrimeraPlataforma = 20; //Altura plataforma baja. Más alto el valor, más alto
//#     
//#     public static final int CorrectorTexto = 0; //Para recorregir algunos textos.
//#     
//#     public static final int SeparacionEntreLineasIntro = 30;
//#     
//#     public static int CorrectorIngles = 0;    
//#elif ScreenWidth==176 && ScreenHeight==205
//#     public static final int SCR_HEIGHT = 205;
//#     public static final int SCR_WIDTH = 176;
//#     
//#     public static final int FPS = 20;
//#     
//#     public static final boolean TOUCHSCREEN = false;
//#         
//#     public static final int margenYmenuPrin = 80;
//#     
//#     public static final int alturaSegundaPlataformaMetalica = 60; //Altura plataforma alta. Más alto el valo, más bajo.
//#     
//#     public static final int limiteInteligentePintado = - 4;
//#     
//#     public static final int fuerzaSalto = 10;
//#     
//#     public static final int saltoDesplazMax = 10;
//#     
//#     public static final int avanceXNinja1 = 3;
//#        
//#     public static final int velocidadDuke = 5;
//#     
//#     public static final int VELOCIDADANIMACION = 2;   
//#     public static final int VELOCIDADANIMACIONNINJA = 3;
//#     
//#     public static final int correctorAlturaPrimeraPlataforma = - 15; //Altura plataforma baja. Más alto el valor, más alto
//#     
//#     public static final int CorrectorTexto = 0; //Para recorregir algunos textos.
//#     
//#     public static final int SeparacionEntreLineasIntro = 30;
//#     
//#     public static int CorrectorIngles = 0;        
//#elif ScreenWidth==128 && ScreenHeight==160
//#     public static final int SCR_HEIGHT = 160;
//#     public static final int SCR_WIDTH = 128;
//#     
//#     public static final int FPS = 25;
//#     
//#     public static final boolean TOUCHSCREEN = false;
//#         
//#     public static final int margenYmenuPrin = 60;
//#     
//#     public static final int alturaSegundaPlataformaMetalica = 60; //Altura plataforma alta. Más alto el valo, más bajo.
//#     
//#     public static final int limiteInteligentePintado = 9;
//#     
//#     public static final int fuerzaSalto = 13;
//#     
//#     public static final int saltoDesplazMax = 8;
//#     
//#     public static final int avanceXNinja1 = 3;
//#     
//#     public static final int velocidadDuke = 5;
//#        
//#     public static final int VELOCIDADANIMACION = 2;
//#        
//#     public static final int VELOCIDADANIMACIONNINJA = 5;
//#     
//#     public static final int correctorAlturaPrimeraPlataforma = 10;
//#     
//#     public static final int CorrectorTexto = 1; //Para recorregir algunos textos.
//#     
//#     public static final int SeparacionEntreLineasIntro = 15;
//#    
//#    public static int CorrectorIngles = 0;
//#elif ScreenWidth==128 && ScreenHeight==149
//#     public static final int SCR_HEIGHT = 149;
//#     public static final int SCR_WIDTH = 128;
//#     
//#     public static final int FPS = 25;
//#     
//#     public static final boolean TOUCHSCREEN = false;
//#         
//#     public static final int margenYmenuPrin = 60;
//#     
//#     public static final int alturaSegundaPlataformaMetalica = 60; //Altura plataforma alta. Más alto el valo, más bajo.
//#     
//#     public static final int limiteInteligentePintado = 9;
//#     
//#     public static final int fuerzaSalto = 13;
//#     
//#     public static final int saltoDesplazMax = 8;
//#     
//#     public static final int avanceXNinja1 = 3;
//#     
//#     public static final int velocidadDuke = 5;
//#        
//#     public static final int VELOCIDADANIMACION = 2;
//#        
//#     public static final int VELOCIDADANIMACIONNINJA = 5;
//#     
//#     public static final int correctorAlturaPrimeraPlataforma = 10;
//#     
//#     public static final int CorrectorTexto = 1; //Para recorregir algunos textos.
//#     
//#     public static final int SeparacionEntreLineasIntro = 15;
//#    
//#    public static int CorrectorIngles = 0;   
//#elif ScreenWidth==128 && ScreenHeight==147
//#     public static final int SCR_HEIGHT = 147;
//#     public static final int SCR_WIDTH = 128;
//#     
//#     public static final int FPS = 20;
//#     
//#     public static final boolean TOUCHSCREEN = false;
//#         
//#     public static final int margenYmenuPrin = 60;
//#     
//#     public static final int alturaSegundaPlataformaMetalica = 60; //Altura plataforma alta. Más alto el valo, más bajo.
//#     
//#     public static final int limiteInteligentePintado = 9;
//#     
//#     public static final int fuerzaSalto = 13;
//#     
//#     public static final int saltoDesplazMax = 8;
//#     
//#     public static final int avanceXNinja1 = 2;
//#     
//#     public static final int velocidadDuke = 5;
//#        
//#     public static final int VELOCIDADANIMACION = 2;
//#        
//#     public static final int VELOCIDADANIMACIONNINJA = 5;
//#     
//#     public static final int correctorAlturaPrimeraPlataforma = 10;
//#     
//#     public static final int CorrectorTexto = 1; //Para recorregir algunos textos.
//#     
//#     public static final int SeparacionEntreLineasIntro = 15;
//#    
//#    public static int CorrectorIngles = 0;        
//#elif ScreenWidth==128 && ScreenHeight==128
//#     public static final int SCR_HEIGHT = 128;
//#     public static final int SCR_WIDTH = 128;
//#     
//#     public static final int FPS = 25;
//#     
//#     public static final boolean TOUCHSCREEN = false;
//#         
//#     public static final int margenYmenuPrin = 50;
//#     
//#     public static final int alturaSegundaPlataformaMetalica = 40; //Altura plataforma alta. Más alto el valo, más bajo.
//#     
//#     public static final int limiteInteligentePintado = 9;
//#     
//#     public static final int fuerzaSalto = 13;
//#     
//#     public static final int saltoDesplazMax = 8;
//#     
//#     public static final int avanceXNinja1 = 1;
//#     
//#     public static final int velocidadDuke = 2;
//#        
//#     public static final int VELOCIDADANIMACION = 5;
//#        
//#     public static final int VELOCIDADANIMACIONNINJA = 5;
//#        
//#     public static final int correctorAlturaPrimeraPlataforma = 5; //Altura plataforma baja. Más alto el valor, más alto
//#     
//#     public static final int CorrectorTexto = 1; //Para recorregir algunos textos.
//#     
//#     public static final int SeparacionEntreLineasIntro = 15;
//#     
//#     public static int CorrectorIngles = 0;    
//#elif ScreenWidth==130 && ScreenHeight==130
//#     public static final int SCR_HEIGHT = 130;
//#     public static final int SCR_WIDTH = 130;
//#     
//#     public static final int FPS = 25;
//#     
//#     public static final boolean TOUCHSCREEN = false;
//#         
//#     public static final int margenYmenuPrin = 50;
//#     
//#     public static final int alturaSegundaPlataformaMetalica = 40; //Altura plataforma alta. Más alto el valo, más bajo.
//#     
//#     public static final int limiteInteligentePintado = 9;
//#     
//#     public static final int fuerzaSalto = 13;
//#     
//#     public static final int saltoDesplazMax = 8;
//#     
//#     public static final int avanceXNinja1 = 2;
//#     
//#     public static final int velocidadDuke = 3;
//#        
//#     public static final int VELOCIDADANIMACION = 2;
//#        
//#     public static final int VELOCIDADANIMACIONNINJA = 3;
//#        
//#     public static final int correctorAlturaPrimeraPlataforma = 5; //Altura plataforma baja. Más alto el valor, más alto
//#     
//#     public static final int CorrectorTexto = 1; //Para recorregir algunos textos.
//#     
//#     public static final int SeparacionEntreLineasIntro = 15;
//#     
//#     public static int CorrectorIngles = 0;          
//#endif    
}
