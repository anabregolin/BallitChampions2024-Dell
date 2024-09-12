public class Team {

    private String name, warCry;
    private int foundedYear;
    private int totalBlots;
    private int totalPlifs;
    private int totalAdvrunghs;
    private int totalPoints;

    public Team(String name, String warCry, int foundedYear){
        this.name = name;
        this.warCry = warCry;
        this.foundedYear = foundedYear;

    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setWarCry(String warCry){
        this.warCry = warCry;
    }

    public String getWarCry(){
        return warCry;
    }

    public void setFoundedYear(int foundedYear){
        this.foundedYear = foundedYear;
    }

    public int getFoundedYear(){
        return foundedYear;
    }

    //somatório de valores para pontuação total
    public void setTotalBlots(int totalBlots){
        this.totalBlots += totalBlots;
    }

    public int getTotalBlots(){
        return totalBlots;
    }

    public void setTotalPlifs(int totalPlifs){
        this.totalPlifs += totalPlifs;
    }

    public int getTotalPlifs(){
        return totalPlifs;
    }

    public void setTotalAdvrunghs(int totalAdvrunghs){
        this.totalAdvrunghs += totalAdvrunghs;
    }

    public int getTotalAdvrunghs(){
        return totalAdvrunghs;
    }

    public void setTotalPoints(int totalPoints){
        this.totalPoints += totalPoints;
    }

    public int getTotalPoints(){
        return totalPoints;
    }
    @Override
    public String toString(){
        return name + " | " + totalBlots + " | " + totalPlifs + " | " + totalAdvrunghs + " | " + totalPoints ;
    }

}
