package model;


public class Node {
    
    String data;
    Node next;
    Node previous;
    Node portal;
    public static int pos=0;
    int index;
    String portaljoined;

    public static int getPos() {
        return pos;
    }

    public static void setPos(int pos) {
        Node.pos = pos;
    }

    
    
    
}

