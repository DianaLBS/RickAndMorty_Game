package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import Exception.NumberOfPortalsException;
import Exception.NumberOfSeedsException;
import model.LinkedList;
import model.Player;
import model.ScoreData;
import model.User;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;


public class Main {
    
    private String [] abc = {"A","A","B","B","C","C","D","D","E","E","F","F","G","G","H","H","I","I","J","J","K","K","L","L","M","M","N","N","O","O","P","P","Q","Q","R","R","S","S","T","T","U","U","V","V","W","W","X","X","Y","Y","Z","Z"};
    private Scanner sc=new Scanner(System.in);
    private static ScoreData scoredata;
    public static void main(String[]args) {
    	Main obj =new Main();
   
    	scoredata =new ScoreData();
    	ScoreData.loadJSON();
    	System.out.println("-----Welcome to the best Rick and Morty game!-----\n");
    	try {
			obj.init();
		} catch (NumberOfSeedsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberOfPortalsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void init() throws NumberOfSeedsException, NumberOfPortalsException{
    	
        LinkedList list=new LinkedList();
        //filas
        System.out.println("Enter the number of board rows:");
        int m=sc.nextInt();
        //columnas
        System.out.println("Enter the number of board columns:");
        int n=sc.nextInt();
        //semillas

        System.out.println("Enter the number of seeds on the board:");
        int q=sc.nextInt();
        //numero de casillas
        int num=n*m;
        if(num<q) {
        	throw new NumberOfSeedsException();
        }
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
        System.out.println("Enter the number of portals on the board:");
        int amountPortals=sc.nextInt();
        if(amountPortals>num/2) {
        	throw new NumberOfPortalsException("The number of portals must be less than or equal to half the number of squares");
        }else if(amountPortals>26) {
        	throw new NumberOfPortalsException("The number of portals must be less than 26 (Total letters of the alphabet)");
        }
        //Crear enlaces de portales
            for(int i=0;i<amountPortals*2;i=i+2){
                int p1=portals.get(i);
                int p2=portals.get(i+1);
                //System.out.println(p1+" "+p2);
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
        System.out.println("Enter the Username for Rick:");
        String nicknameRick = sc.next();
        System.out.println("Enter the Username for Morty:");
        String nicknameMorty = sc.next();
	  scoredata.insertionSort();
        scoredata.saveJSON();

        int posR = scoredata.searchUser(nicknameRick);
       
        int posM = scoredata.searchUser(nicknameMorty);
        
        //System.out.println("rpos:"+posR);
        //System.out.println("+mpos:"+posM);
        long prevScoreM=0;
        long prevScoreR=0;
        
        //si pos es -1 no se encontro el usuario
        if(posM!=-1) {
        	 prevScoreM=ScoreData.scoreBoard.get(posM).getScore();	 //guarda el puntaje antiguo del jugador
        }
        if(posR!=-1) {
        	 prevScoreR=ScoreData.scoreBoard.get(posR).getScore();
        }
        
        Rick.setNickname(nicknameRick);
        Morty.setNickname(nicknameMorty);
        int count=1;
        String turn="";
        Player player = new Player("","","",0);
      
        //Inicia variables de conteo de segundos
       
        LocalTime start=LocalTime.now();
        LocalTime end=LocalTime.of(00,00,00);
       
        int numSeedsToWin=0;
        //Se calculan las semillas necesarias para ganar el juego, (mas de la mitad o la mitad exacta en vcaso de que el numero de semillas sea par)
        if(q%2==0) {
        	numSeedsToWin=q/2;
        }else {
        	numSeedsToWin=(q/2)+1;
        }
       
        do{
            if(count%2==0){
                turn = Morty.getName();
                player = Morty;
               
            }else{
                turn = Rick.getName();
                player= Rick;
            }
            
            System.out.println("It is the turn of "+ turn+" What do you want to do?");
            System.out.println("1.Roll the dice");
            System.out.println("2.See the board");
            System.out.println("3.See portals");
            System.out.println("4.Scoreboard(seeds)");
            int option=sc.nextInt();
            switch(option){
                case 1:
                	list.display(num,n);
                   throwdice(list,player,num);
                   count++;
                   end=LocalTime.now();
                   
                    break;
                case 2:
                    list.display(num,n);
                    break;
                case 3:
                    list.displayPortals(num,n,m);
                    break;
                case 4:
                    System.out.println(Rick.getName()+" : "+Rick.getAmountSeeds()+" Semillas");
                    System.out.println(Morty.getName()+" : "+Morty.getAmountSeeds()+" Semillas");
                    break;
            }
        }while(Rick.getAmountSeeds()<numSeedsToWin && Morty.getAmountSeeds()<numSeedsToWin);
        end=LocalTime.now();
        
        long totalSeconds=start.until(end, ChronoUnit.SECONDS);//se calcula el tiempo de la partida
       
        System.out.println("---RESULTS---\n"
        		+ "RICK: \n"
        		+ "Total seeds collected: "+Rick.getAmountSeeds()+"\n\n"
				+ "MORTY: \n"
        		+ "Total seeds collected: "+Morty.getAmountSeeds()+ "\n");
        String winner="";
       
        //Se dice el ganador (Gana el de m s semillas recolectadas)
        if(Rick.getAmountSeeds()>Morty.getAmountSeeds()) {
        	winner=nicknameRick;
        }else if(Morty.getAmountSeeds()>Rick.getAmountSeeds()) {
        	winner=nicknameMorty;
        }
        long totalScore=0;
        if(winner==nicknameRick) {
        	 totalScore=(Rick.getAmountSeeds()*120)-totalSeconds; 
        	 System.out.println("Rick has won by collecting "+Rick.getAmountSeeds() +" seeds. And got a score of: "+ totalScore);
        }else {
        	totalScore=(Morty.getAmountSeeds()*120)-totalSeconds;
        	System.out.println("Morty has won by collecting "+Morty.getAmountSeeds() +" seeds. And got a score of: "+ totalScore);
        }
        System.out.println("CONGRATULATIONS TO THE WINNER!");
        //Actualiza el puntaje en caso de que sea un usuario que ya ha ganado
        if(winner==nicknameRick && posR!=-1) {
        	scoredata.scoreBoard.get(posR).setScore(prevScoreR+totalScore);
        }else if(winner==nicknameMorty && posM!=-1) {
        	scoredata.scoreBoard.get(posM).setScore(prevScoreM+totalScore);
        }else {
        	scoredata.scoreBoard.add(new User(winner,totalScore)); //Guarda en el tablero de puntajes un nuevo usuario en caso de no haber ganado antes
        }
	  scoredata.insertionSort();
        scoredata.saveJSON();
	 //Mostrar el top 5 de jugadores.
	  scoredata.printTopFivePlayer();
    }
  public void throwdice(LinkedList list,Player player,int num){
       int dice=(int)(Math.random()*(7-1))+1;
       System.out.println("Dado: "+dice);
       int posPlayer = list.posplayer(player.getIdPlayer());
       //System.out.println(posPlayer);
       System.out.println("1.Avanzar");
       System.out.println("2.Retrocede");
       int option = sc.nextInt();
       int destiny=0;
       if(option==1){
          if((posPlayer+dice)>num){
              destiny=posPlayer+dice;
              while(destiny>num) {
            	  destiny=destiny-num;
              }
              list.deletepos(posPlayer,player);
              list.moveDices(destiny,player);  
           }else{
               destiny=posPlayer+dice;
               list.deletepos(posPlayer,player);
               list.moveDices(destiny,player);
           }
       }else{
           if((posPlayer-dice)<=0){
               destiny=posPlayer-dice;
               while(destiny<0) {   
            	   destiny=destiny+num;
               }
               
               list.deletepos(posPlayer,player);
               list.moveDices(destiny,player);
           }else{
               destiny=posPlayer-dice;
               list.deletepos(posPlayer,player);
               list.moveDicesback(destiny,player);
           }
       }
   }
}

