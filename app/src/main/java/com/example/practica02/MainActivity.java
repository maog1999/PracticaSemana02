package com.example.practica02;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView preguntaView;
    private EditText respuestaView;
    private Button ok;
    private Button tryAgain;
    private TextView puntajeView;
    private TextView contador;
    private Pregunta p;
    private int Puntos;
    private int contadoVar;
    private boolean presiono = false;
    private int tiempo = 1500;
   // private long lastClick;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preguntaView = findViewById(R.id.pregunta);
        respuestaView = findViewById(R.id.respuestaView);
        puntajeView = findViewById(R.id.puntaje);
        contador = findViewById(R.id.contador);
        puntajeView.setText("Puntaje: " + Puntos);
        ok = findViewById(R.id.ok);
        tryAgain = findViewById(R.id.tryAgain);

        contadoVar = 31;
        Puntos = 0;

        tryAgain.setVisibility(View.GONE);

        //Para que me muestre una pregunta Inicial
        mostrarNuevaPregunta();


        Toast.makeText(this,"Respuesta: "+p.getRespuesta(),Toast.LENGTH_SHORT).show();

        ok.setOnClickListener(
                v->{
                    responder();
                }
        );

        //metodo para cambiar de pregunta presionando la pantalla
        preguntaView.setOnTouchListener(
                (view, event)->{
                    switch (event.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            presiono = true;
                            new Thread(
                                    ()->{
                                      for (int i=0;i<20;i++) {
                                          try {
                                              Thread.sleep(75);
                                              if (presiono == false) {
                                                  return;
                                              }
                                          } catch (InterruptedException e) {
                                              e.printStackTrace();
                                          }
                                      }
                                      runOnUiThread(
                                              ()->{

                                                  mostrarNuevaPregunta();
                                              }
                                      );

                                    }
                            ).start();
                        break;

                        case MotionEvent.ACTION_UP:
                            presiono = false;
                        break;
                    }
                    return true;
                }
        );
      /*  preguntaView.setOnTouchListener(
                (view, event)->{
                    if(event.getAction() == MotionEvent.ACTION_DOWN){
                        lastClick = SystemClock.elapsedRealtime();

                    } if(event.getAction() == MotionEvent.ACTION_UP){


                        long duration = SystemClock.elapsedRealtime() - lastClick;
                        Log.e("----->","" + duration);
                        if(duration > 1500){
                                mostrarNuevaPregunta();
                            }
                    }

                    return true;
                }
        );*/

        //Hilo para reiniciar el juego
        tryAgain.setOnClickListener(

                v->{
                    if(contadoVar ==0){
                        Puntos = 0;
                        puntajeView.setText("Puntaje: " + Puntos);

                    }
                    tryAgain.setVisibility(View.GONE);
                    contadoVar = 31;

                    new Thread(
                            ()->{
                                while(contadoVar > 0){
                                    contadoVar--;
                                   // Log.e("-->",""+contadoVar);
                                    //imprimir varibale en pantalla
                                    int finalContadoVar = contadoVar;
                                    runOnUiThread(
                                            ()->{
                                                contador.setText(""+ finalContadoVar);
                                                if(finalContadoVar==0){
                                                    tryAgain.setVisibility(View.VISIBLE);

                                                }
                                            }
                                    );
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                } }

                    ).start();
                }
        );

        //Hilo "Principal"
        new Thread(
                ()-> {

                    while(contadoVar > 0){
                      contadoVar--;
                     // Log.e("-->",""+contadoVar);
                      //imprimir varibale en pantalla
                        int finalContadoVar = contadoVar;
                        runOnUiThread(
                                ()->{
                                contador.setText(""+ finalContadoVar);
                                if(finalContadoVar==0){
                                    tryAgain.setVisibility(View.VISIBLE);

                                }

                                }
                        );
                      try {
                          Thread.sleep(1000);
                      } catch (InterruptedException e) {
                          e.printStackTrace();
                      }
                  }

                }
        ).start();

    }

    private void runOnUiThread() {
    }

    public void responder(){
        String res = respuestaView.getText().toString();
        int resInt = Integer.parseInt(res);
        int correcta = p.getRespuesta();

        if(resInt == correcta){
            Toast.makeText(this, "Correcto", Toast.LENGTH_SHORT).show();
            Puntos = Puntos + 5;
            puntajeView.setText("Puntaje: " + Puntos);
        }else {
            Toast.makeText(this, "Mal", Toast.LENGTH_SHORT).show();
            Puntos = Puntos - 4;
            puntajeView.setText("Puntaje: " + Puntos);
        }


        mostrarNuevaPregunta();

    }

    public void mostrarNuevaPregunta(){
        //Instanciar una clase o creando el objeto
        p = new Pregunta();
        preguntaView.setText(p.getPregunta());
    }
}