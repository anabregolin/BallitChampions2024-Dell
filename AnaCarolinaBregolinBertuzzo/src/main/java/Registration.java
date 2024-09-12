import java.util.ArrayList;
import java.util.List;

public class Registration{
    //criar em stage um método private que duplica a lista
    private List<Team> teamList;
    private final int MAX_TEAMS = 16;
    private int count;

    public Registration() {
        teamList = new ArrayList<>();
        count = 0;
    }

    /*
        addTeam()
        - Adiciona um time em registration fazendo verificações de valor máximo (16)
     */
    public void addTeam(Team team) {
        if(teamList.size() < MAX_TEAMS) {
            teamList.add(team);
            count ++;
            System.out.println("\nTime adicionado com sucesso.");

        }else {
            System.out.println("\nA capacidade de times máxima já foi atingida.");
        }

    }

    public boolean isFull(){
        return (count == MAX_TEAMS);
    }

    public Team getTeam(int index) {
        if (index >= teamList.size() || index < 0) {
            System.out.println("\nErro ao coletar time.");
        }
        return teamList.get(index);
    }

    public int size() {
        return count;
    }

    public void clear() {
        teamList.clear();
        count = 0;
    }

    public boolean minimalCapacity() {
        return(count == 8);
    }

    public boolean isEmpty() {
        return (count == 0);
    }

    /*
        toString()
        - Mostra todos os valores dentro da lista

     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nTimes cadastrados: ");

        if (!isEmpty()) {
            for (int i = 0; i < teamList.size(); i++) {
                sb.append(teamList.get(i).getName());
                if (i < teamList.size() - 1) {
                    sb.append(", ");
                }
            }
        }

        sb.append("");
        return sb.toString();
    }

}
