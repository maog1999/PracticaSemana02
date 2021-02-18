package com.example.practica02;

public class Pregunta {

    private int a;
    private int b;
    private String operador;
    private String [] operandos = {"+", "-", "*", "/"};

    //Constructor
    public Pregunta(){
        this.a = (int)(Math.random()*11);
        this.b = (int)(Math.random()*11);
        int operadorRandom = (int)(Math.random()*4);
        this.operador = operandos[operadorRandom];
    }

    //Atributos


    //Metodos
    public String getPregunta(){
        return a+" "+operador+" "+b;
    }

    public int getRespuesta(){
        int respuesta = 0;

        switch (operador){
            case "+":
                respuesta = a+b;
                break;
            case "-":
                respuesta = a-b;
                break;
            case "*":
                respuesta = a*b;
                break;
            case "/":
                respuesta = a/b;
                break;
        }
        return respuesta;
    }

}
