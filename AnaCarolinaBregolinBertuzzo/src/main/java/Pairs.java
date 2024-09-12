public class Pairs {

    private Team firstTeam;
    private Team secondTeam;
    private int id;

    //pares são definidos por dois times
    public Pairs(int id, Team firstTeam, Team secondTeam){
        this.firstTeam = firstTeam;
        this.secondTeam = secondTeam;
    }

    public Team getFirstTeam(){
        return firstTeam;
    }

    public Team getSecondTeam(){
        return secondTeam;
    }

    public void setID(int id){
        this.id = id;
    }

    public int getID(){
        return id;
    }

}
