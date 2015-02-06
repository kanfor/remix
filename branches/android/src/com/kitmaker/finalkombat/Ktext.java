package com.kitmaker.finalkombat;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author josemariaclimentmartinez
 */

public class Ktext {
    
    //Dibuja cualquir texto en tipografía gráfica
    public static void drawText(Graphics g,String texto, Sprite font, int X, int Y) {
    
        int NumChars = texto.length(); //Obtengo la longitud en caracteres del texto
        int Ascii;
        boolean acento = false;
        boolean retrocedeCarro = false;
        int retrocedeCarroPixeles = 0;
//#if ScreenWidth==176
//#         if (font == SuperCanvas.sfont3) {
//#         Corrector = -8;
//#         }
//#endif        
        for (int i=0; i < NumChars; i++) {
            Ascii=(int)texto.charAt(i)-97;//Para imprimir letras
            if (Ascii<0) {Ascii+=78;}//Para imprimir números
            if (Ascii==76) {Ascii=28;} //Para imprimir en blanco
            else if (Ascii==144) {Ascii=27;}//Para imprimir Ñ
            else if (Ascii==64) {Ascii=26;}//Para imprimir !
            else if (Ascii==29) {Ascii=39;}//Para imprimir el cero
            else if (Ascii==27) {Ascii=29;}//Para imprimir el punto
            else if (Ascii==28) {Ascii=40;}//Para imprimir el /
            else if (Ascii==39) {Ascii=41;}//Para imprimir el :
            //Simbolos raros
            else if (Ascii==46) {Ascii=42; acento=true;}//Para imprimir ACENTO
            else if (Ascii==47) {Ascii=43; acento=true;}//Para imprimir SOMBRERO CHINO
            else if (Ascii==48) {Ascii=44; acento=true;}//Para imprimir MOÑITO
            else if (Ascii==49) {Ascii=45;}//Para imprimir Ç
            font.setFrame(Ascii);
            
            
            if (retrocedeCarro==true) {
                //NumChars--;
                retrocedeCarroPixeles += (font.getWidth()                    
//#if ScreenWidth == 128 || ScreenWidth == 130                 
//#                     -2
//#endif          
                        );
                retrocedeCarro=false;
            }
            
            if (acento==false) {
            font.setPosition(X - (NumChars*(font.getWidth()/2))       
//#if ScreenWidth == 128 || ScreenWidth == 130                     
//#                     +8
//#endif          
//#if ScreenWidth == 176
//#         + Corrector            
//#endif                       
                    + (i*(font.getWidth()          
//#if ScreenWidth == 128 || ScreenWidth == 130                 
//#                     -2
//#endif                                
                    ) - retrocedeCarroPixeles), Y);
            }
            
            //Simbolos raros        
            else {
            font.setPosition(X - (NumChars*(font.getWidth()/2))       
//#if ScreenWidth == 128 || ScreenWidth == 130                     
//#                     +8
//#endif          
//#if ScreenWidth == 176
//#         + Corrector            
//#endif                       
                    + ((i-1)*(font.getWidth()                    
//#if ScreenWidth == 128 || ScreenWidth == 130                 
//#                     -2
//#endif                                
                    )), Y - font.getHeight());                
                acento=false;
                retrocedeCarro=true;
            } 
            
           font.paint(g);
           //g.setColor(255,255,255);               //Activar para mostrar los Keycodes
           //g.drawString(""+Ascii, 10, 10, 0);
        }
    }
    
    //Llena la pantalla con la imagen elegida
    public static void background(Graphics g, Image imagen) {
        
        for (int i=0;i<(Define.SCR_WIDTH/imagen.getWidth())+1;i++) {
            g.drawImage(imagen, imagen.getWidth()*i, 0, Graphics.TOP | Graphics.LEFT);
            for (int q=0;q<Define.SCR_HEIGHT/imagen.getHeight()+1;q++) {
                g.drawImage(imagen, imagen.getWidth()*i, imagen.getHeight()*q, Graphics.TOP | Graphics.LEFT);
            }
        }
    }
}