package model;

public class LinkedList {
    
    Node first;
    Node last;
    
    public LinkedList(){
        first=null;
        last=null;
    }
    //Insertar valores
    
    /**/
    public void insert(String data){
        Node newNode =new Node();
        newNode.data=data;
        if(first==null){
            first=newNode;
            first.next=first;
            Node.pos++;
            first.index=Node.pos;
            first.portaljoined="[ ]";
            newNode.previous=last;
            last=newNode;            
        }else{
            Node.pos++;
            newNode.index=Node.pos;
            newNode.portaljoined="[ ]";
            last.next=newNode;
            newNode.next=first;
            newNode.previous=last;
            last=newNode;
            first.previous=last;
        }
    }
    //Imprimir los valores, "POCO IMPORTANTE"
    public void print(){
        Node current=new Node();
        current=first;
        do{
            System.out.println(current.data);
            current = current.next;
        }while(current!=first);
    }
    //Display
    public void display(int num,int n){
        displayrecursive(first,1,num,n,1);
    }
    //Imprimir en forma de tablero
    //n es el numero de casillas
    //a es el numero de columnas
    public void displayrecursive(Node current,int i,int n,int a,int m){
        if(i>n){
            return;
        }
        if(m%2!=0) {
            System.out.printf("%10s",current.data+" ");
            }else {
                for(int j=current.index+a-1;j>=current.index;j--) {
                	Node node=searchNode(current,j);
                    System.out.printf("%10s",node.data+" ");
                    i++;
                }
                i=current.index+a-1;
                current = searchNode(current,i);
            }
            
            if(i%a==0){
                System.out.println();
                m++;
            }
        i++;
        displayrecursive(current.next,i,n,a,m);
    } 
    
    public void changeSem(String sem){
        semrecursive(first,sem);
    }
    //colocar semillas en el tablero
    public void semrecursive(Node current,String sem){
        if(sem.equals(current.data)){
            current.data="*";
            return;
        }
        if(current.next.equals(first)){
            return;
        }
        semrecursive(current.next,sem);
        
    }
    public void putPlayers(String pRick,String pMorty,String idR,String idM){
        putPlayersrecursive(first,pRick,pMorty,idR,idM);
    }
    //Colocar jugadores en el tablero
    public void putPlayersrecursive(Node current,String pRick,String pMorty,String idR,String idM){
        if(pRick.equals(current.data)){
            current.data=idR;
        }
        if(pMorty.equals(current.data)){
            current.data=idM;
        }
        if(current.next.equals(first)){
            return;
        }
        putPlayersrecursive(current.next,pRick,pMorty,idR,idM);
    }
    public void joinPortals(int p1,int p2,String abc){
        joinNode(first,p1,p2,abc);
    }
    //Unir portales
    public void joinNode(Node current,int p1,int p2,String abc){
        if(current.index==p1){
           Node joined = searchNode(first,p2);
           current.portal=joined; 
           //manejar con referencia
           current.portal.index=p2;
           current.portaljoined="["+abc+"]";
           current.portal.portaljoined="["+abc+"]";
        }
        if(current.index==p2){
            Node joined = searchNode(first,p1);
            current.portal=joined;
            current.portal.index=p1;
        }
        if(current.next.equals(first)){
            return;
        }
        joinNode(current.next,p1,p2,abc);
    }
    //Buscar un nodo
    public Node searchNode(Node current,int value){
        if(current.index==value){
            return current;
        }
        if(current.next.equals(first)){
            return null;
        }
        return searchNode(current.next,value);
    }
    
    public boolean  existseeds(){
        boolean flag=false;
        flag = existSeedsr(first);
        return flag;
    }
    //Existen semillas, (saber cuando terminar el juego)
    public boolean existSeedsr(Node current){
        if(current.data.equals("*")){
            return true;
        }
        if(current.next.equals(first)){
            return false;
        }
        return existSeedsr(current.next);
    }
    public void moveDices(int destiny,Player player){
        movDice(first,destiny,player);
    }
    //Lanzar dados
    public void movDice(Node current,int destiny,Player player){
        if(current.index==destiny&&current.portal!=null){
            if(current.data.equals("*")){
                int seeds = player.getAmountSeeds();
                seeds++;
                player.setAmountSeeds(seeds);
            }
            deletepos(destiny,player);
           destiny = current.portal.index;
           movPortal(first,destiny,player);
        }else if(current.index==destiny){
            if(current.data.equals("*")){
                int seeds = player.getAmountSeeds();
                seeds++;
                player.setAmountSeeds(seeds);
            }
            if(current.data.equals("R")||current.data.equals("M")) {
            	current.data="RM";
            }
            else{
            	current.data=player.getIdPlayer();
            }
        }
        if(current.next.equals(first)){
            return;
        }
        movDice(current.next,destiny,player);
    }
    public void movPortal(Node current,int destiny,Player player){
        if(current.index==destiny){
            if(current.data.equals("*")){
                int seeds = player.getAmountSeeds();
                seeds++;
                player.setAmountSeeds(seeds);
            }
            if(current.data.equals("R")||current.data.equals("M")) {
            	current.data="RM";
            }
            else{
            	current.data=player.getIdPlayer();
            }
            return;
        }
        if(current.next.equals(first)){
            return;
        }
        movPortal(current.next,destiny,player);
    }
    public void moveDicesback(int destiny,Player player){
        movBack(last,destiny,player);
    }
    public void movBack(Node current,int destiny,Player player){
        if(current.index==destiny&&current.portal!=null){
            
            if(current.portal.data.equals("*")){
                int seeds = player.getAmountSeeds();
                seeds++;
                player.setAmountSeeds(seeds);
            }
            deletepos(destiny,player);
            destiny = current.portal.index;
            movPortal(first,destiny,player);
            return;
        }else if(current.index==destiny){
            if(current.data.equals("*")){
                int seeds = player.getAmountSeeds();
                seeds++;
                player.setAmountSeeds(seeds);
            }
            if(current.data.equals("R")||current.data.equals("M")) {
            	current.data="RM";
            }
            else{
            	current.data=player.getIdPlayer();
            }
            return;
        }
        if(current.previous==last){
            return;
        }
        movBack(current.previous,destiny,player);
    }
    public void deletepos(int posplayer, Player player){
        deleteposr(first,posplayer, player);
    }
    //Borrar las posiciones en donde estaban los jugadores
    public void deleteposr(Node current,int posplayer, Player player){
        if(current.data.equals("RM")&&player.getIdPlayer().equals("R")) {
    		current.data="M";
    		return;
    	}else if(current.data.equals("RM")&&player.getIdPlayer().equals("M")) {
    		current.data="R";
    		return;
    	}
        if(current.index==posplayer){
            String posnew = String.valueOf(current.index);
            current.data=posnew;
        }
        if(current.next.equals(first)){
            return;
        }
        deleteposr(current.next,posplayer,player);
    }
    public int posplayer(String idplayer){
        int pos = searchplayer(first,idplayer);
        return pos;
    }
    //Buscar la posicion donde esta el jugador
    public int searchplayer(Node current,String idplayer){
        
        if(current.data.equals(idplayer) || current.data.equals("RM")){
            return current.index;
        }else {
        	return searchplayer(current.next,idplayer);
        }
    }
    public void pos(){
        posr(first);
    }
    //************************************************************************//
    //Imprimir las posiciones de cada nodo, solo es prueba
    public void posr(Node current){
        System.out.println(current.index);
        if(current.next==first){
            return;
        }
        posr(current.next);
    }
    //Aqui va el metodo de retroceder
    public void displayPortals(int num,int n,int m){
        printPortals(first,1,num,n,m);
    }
    //Imprimir enlaces de forma correcta
    public void printPortals(Node current,int i,int num,int n,int m){
        if(i>num){
            return;
        }
        if(m%2!=0) {
        System.out.printf("%10s",current.portaljoined+" ");
        }else {
        	for(int j=current.index+n-1;j>=current.index;j--) {
        		Node node = searchNode(current,j);
        		System.out.printf("%10s",node.portaljoined+" ");
        		i++;
        	}
        	i=current.index+n-1;
        	current = searchNode(current,i);
        }
        
        if(i%n==0){
            System.out.println();
            m++;
        }
        i++;
        printPortals(current.next,i,num,n,m);
    }
}

