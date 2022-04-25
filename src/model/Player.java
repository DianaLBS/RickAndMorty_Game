package model;


public class Player {
    
    private String name;
    private String nickname;
    private String idPlayer;
    private int amountSeeds;

    public Player(String name, String nickname, String idPlayer, int amountSeeds) {
        this.name = name;
        this.nickname = nickname;
        this.idPlayer = idPlayer;
        this.amountSeeds = amountSeeds;
 
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(String idPlayer) {
        this.idPlayer = idPlayer;
    }

    public int getAmountSeeds() {
        return amountSeeds;
    }

    public void setAmountSeeds(int amountSeeds) {
        this.amountSeeds = amountSeeds;
    }

    
}

