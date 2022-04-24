package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

import model.LinkedList;
import model.Player;


public class Main {
    
    private String [] abc = {"A","A","B","B","C","C","D","D"};
    private Scanner sc=new Scanner(System.in);
    public static void main(String[]args) {
    	Main obj =new Main();
    	System.out.println(obj);
    	obj.init();
    }
    public void init(){
        LinkedList list=new LinkedList();
        //filas
        int m=sc.nextInt();
        //columnas
        int n=sc.nextInt();
        //semillas
        int q=sc.nextInt();
        //numero de casillas
        int num=n*m;
        for(int i=0;i<num;i++){
            String pos = String.valueOf(i+1);
            list.insert(pos);
        }
        //ArrayList de portales
        ArrayList<Integer> portals=new ArrayList<>();
        //ArrayList de las casillas
        ArrayList<Integer> numbers=new ArrayList<>();
        for(int i=0;i<num;i++){
            numbers.add(i+1);
            portals.add(i+1);
        }
        //Funcion random
        Collections.shuffle(numbers);
        Collections.shuffle(portals);
        int amountPortals=sc.nextInt();
        //Crear enlaces de portales
            for(int i=0;i<amountPortals*2;i=i+2){
                int p1=portals.get(i);
                int p2=portals.get(i+1);
                System.out.println(p1+" "+p2);
                list.joinPortals(p1,p2,abc[i]);
            }
        //Agregar semillas al tablero
        for(int i=0;i<q;i++){
            String vsem=String.valueOf(numbers.get(i));
            list.changeSem(vsem);
        }
        //Crear jugadores y agregarlos al tablero
        Player Rick = new Player("Rick Sanchez","","R",0);
        Player Morty = new Player("Morty","","M",0);
        int posRick=numbers.get(q);
        String pRick= String.valueOf(posRick);
        int posMorty=numbers.get(q+1);
        String pMorty=String.valueOf(posMorty);
        list.putPlayers(pRick,pMorty,Rick.getIdPlayer(),Morty.getIdPlayer());
        System.out.println("Enter the Username for Rick");
        String nicknameRick = sc.next();
        System.out.println("Enter the Username for Morty");
        String nicknameMorty = sc.next();
        Rick.setNickname(nicknameRick);
        Morty.setNickname(nicknameMorty);
        int count=1;
        String turn="";
        Player player = new Player("","","",0);
        do{
            if(count%2==0){
                turn = Morty.getName();
                player = Morty;
            }else{
                turn = Rick.getName();
                player= Rick;
            }
            System.out.println("Es el turno de "+ turn+" ¿Qué deseas hacer?");
            System.out.println("1.Tirar dado");
            System.out.println("2.Ver Tablero");
            System.out.println("3.Ver Enlaces");
            System.out.println("4.Marcador");
            int option=sc.nextInt();
            switch(option){
                case 1:
                   throwdice(list,player,num);
                     count++;
                    break;
                case 2:
                    list.display(num,n);
                    break;
                case 3:
                    list.displayPortals(num,n);
                    break;
                case 4:
                    System.out.println(Rick.getName()+" : "+Rick.getAmountSeeds()+" Semillas");
                    System.out.println(Morty.getName()+" : "+Morty.getAmountSeeds()+" Semillas");
                    break;
            }
        }while(list.existseeds()==true);
    }
  public void throwdice(LinkedList list,Player player,int num){
       int dice=(int)(Math.random()*(7-1))+1;
       System.out.println("Dado: "+dice);
       int posPlayer = list.posplayer(player.getIdPlayer());
       System.out.println(posPlayer);
       System.out.println("1.Avanzar");
       System.out.println("2.Retrocede");
       int option = sc.nextInt();
       int destiny=0;
       if(option==1){
           if((posPlayer+dice)>num){
               System.out.println("No puedes avanzar");
           }else{
               destiny=posPlayer+dice;
               list.deletepos(posPlayer);
               list.moveDices(destiny,player);
           }
       }else{
           if((posPlayer-dice)<0){
               System.out.println("No puedes retroceder");
           }else{
               destiny=posPlayer-dice;
               list.deletepos(posPlayer);
               list.moveDicesback(destiny,player);
           }
       }
   }
}

