import java.util.*;

public class Stages {

    private Registration registration;
    private Match match;
    private Pairs matchesPairs[];
    private boolean auxCopy;
    private int stageNumber;

    private List<Team> registrationWinnerCopy;
    private List<Team> winnersList;
    private Set<Integer> chosenPairs;
    private MongoDBManager mongoDBManager;

    public Stages(Registration registration){
        this.registration = registration;
        auxCopy = true;
        stageNumber = 1;
        mongoDBManager = new MongoDBManager();
    }

    /*
        initStage()
        - Utilizado para executar todos os métodos necessários na etapa de FASES
        - É válido verificar o funcionamento de cada método para melhor compreensão
        1) Uso de winenrList para guardar todos os vencedores de cada STAGE, até que sobre apenas um vencedor,
        ou seja, o vencedor principal
        2) Uso de chosenPairs() para guardar os pares que já competiram em determinado STAGE, não permite repetições
        3) Laço para realizar uma administração completa de cada STAGE, verificando quando as partidas foram finalizadas e
        alterando o valor de allPairs para true. InitString é utilizado para mostrar as duplas disponpiveis para escolha
        do usuário.
            3.1) choiceMatch é utilizado para receber a escolha
        4) Quando um STAGE é finalizado, nectStage() é utilizado para decidir o tipo da próxima STAGE (final ou normal)
     */
    public void initStage(){
        Scanner input = new Scanner(System.in);

        copyRegistration();
        createPairs();

        boolean allPairs = false;

        winnersList = new ArrayList<>();
        chosenPairs = new HashSet<>();
        System.out.println("\nINICIANDO FASE " + stageNumber + " !");

        while(!allPairs){

            if (chosenPairs.size() == matchesPairs.length) {
                System.out.println("Partidas finalizadas!");
                allPairs = true;
            }else{
                System.out.println("Selecione a dupla de times para iniciar a partida: ");
                System.out.println(initString());
                int choice = input.nextInt();

                choiceMatch(choice);
            }
        }

        stageNumber ++;
        chosenPairs.clear();

        nextStage();

        input.close();
    }

    /*
        nextStage()
        - Usado em initStage()
        - Controla automaticamente as FASES.
        1°) Caso apenas tenha um vencedor, isso indica que todas as duplas jogaram. Então, encaminha para o FINAL STAGE.
        2°) Caso tenham mais que 1 vencedores na lista, isso indica que ainda não é FINAL STAGE, então, segue para uma próxima
            STAGE até termos 1 vencedor apenas
     */
    private void nextStage(){
        if(winnersList.size() == 1){
            finalStage();
        } else if (winnersList.size() > 1) {
            initStage();
        }
    }

    /*
        finalStage()
        - Utilizado em initStage()
        - Realiza os comandos solicitados em FINAL STAGE, sendo essas os dados finais de todos os times - através do toString() -
        e, também,o grito de guerra do time vencedor
     */
    private void finalStage(){
        System.out.println(toString());
        System.out.println("\nVencedor "+ winnersList.get(0).getName() + ", solte seu grito de guerra! \n **" + winnersList.get(0).getWarCry() + "!!!***");
        System.out.println("\nEstou extremamente feliz por você ter jogado! <3 Time Dell ");
    }

    /*
        choiceMatch()
        - Utilizado em initStage()
        - Recebe a escolha do usuário de qual partida deve ocorrer
        1) As letras "a", "b", "c" e "d" foram utilizadas para realizar as determinações de escolha, que estão visíveis ao
        usuário em initStages(). Dentro de cada escolha, é chamado um método pressChoice() que recebe a escolha e os pares.
            1.1) A partir do valor 4, é realizada uma verificação de existência de escolha, que foi explicada posteriormente.
            Um defaul para valores inexistentes em qualquer fase.
     */
    private void choiceMatch(int choice){

        switch (choice) {
            case 1:
                checkChoice(choice, matchesPairs[0]);
                break;

            case 2:
                checkChoice(choice, matchesPairs[1]);
                break;

            case 3:
                checkChoice(choice, matchesPairs[2]);
                break;

            case 4:
                checkChoice(choice, matchesPairs[3]);
                break;

            case 5:
                if(matchVerify(choice) == false)
                    break;

                checkChoice(choice, matchesPairs[4]);
                break;

            case 6:
                if(matchVerify(choice) == false)
                    break;

                checkChoice(choice, matchesPairs[5]);
                break;

            case 7:
                if(matchVerify(choice) == false)
                    break;

                checkChoice(choice, matchesPairs[6]);
                break;

            case 8:
                if(matchVerify(choice) == false)
                    break;

                checkChoice(choice, matchesPairs[7]);
                break;

            default:
                System.out.println("Valor inválido.");
                break;
        }
    }

    /*
        matchVerify()
        - Utilizado dentro de choiceMatch()
        - Verificação de valor em relação a STAGE, utilizado após o valor mínimo 4. Usada para permitir o uso de switch em
        diferentes cenários de partidas
        1) Recebe choice e compara com o tamanho do vetor matchesPairs, que são todas as pairs do STAGE atual
        2) Caso seja maior, não permite que a partida ocorra e retorna false
    */
    private boolean matchVerify(int choice){ //verifica se valor é válido para o tamanho da lista
        if(choice > matchesPairs.length){
            System.out.println("Valor inválido.");
            return false;
        }

        return true;
    }

    /*
        checkChoice()
        - Utilizado dentro de choiceMatch()
        - Verificação diferente que marchVerify(), os valores inseridos são validos dentro da partida só que podem implicar em
        uma repetição de partida, checkChoice() garante que um par não entrará em partida novamente ao utilizar a lista chosenPairs.
        1) Verifica se par já foi escolhido usando chosenPairs vinculado à escolha do usuário
        2) Caso os pares não tenham competido, a checagem permite que a partida inicie
            2.1) Pares são adicionados em chosenPairs
            2.2) Uma MATCH é iniciada e vinculada a Team winner, dentro dela ocorre a automatização da partida e retorna o vencedor
            2.3) Valores da MATCH são adicionados ao banco de dados, coleção match
     */
    private void checkChoice(int choice, Pairs pairs){
        if(chosenPairs.contains(choice)){
            System.out.println("Par já foi escolhido.");
        }else{
            chosenPairs.add(choice);
            match = new Match(pairs);
            Team winner = match.initMatch();
            winnersList.add(winner);

            mongoDBManager.saveMatchResult(
                    match.getFirstTeam().getName(),
                    match.getSecondTeam().getName(),
                    winner.getName(),
                    match.getFirstTeam().getTotalBlots(),
                    match.getFirstTeam().getTotalPlifs(),
                    match.getFirstTeam().getTotalAdvrunghs(),
                    match.getFirstTeam().getTotalPoints(),
                    match.getSecondTeam().getTotalBlots(),
                    match.getSecondTeam().getTotalPlifs(),
                    match.getSecondTeam().getTotalAdvrunghs(),
                    match.getSecondTeam().getTotalPoints()
                    );
        }
    }

    /*
        copyRegistration()
        - Utilizado dentro de initStage()
        - Realiza duas funções dependendo do STAGE atual
        1) Realiza uma copy da lista inicial registration, fazendo com que as alterações no restante do código não
        ocorram na versão oficial dos cadastros. No primeiro STAGE, auxCopy é verdadeiro. Para os próximos STAGES,
        auxCopy será falso
        2) Quando o STAGE não é o inicial, realizamos uma cópia da lista winnersList, manténdo a lógica de outros métodos
        que utilizam registrationWinnerCopy, porém, de acordo com os teams que passaram para o próximo STAGE.
            2.1) winnersList é limpo para o novo STAGE
     */
    private void copyRegistration(){
        registrationWinnerCopy = new ArrayList<>();

        if(auxCopy){
            for(int i = 0; i < registration.size(); i++){
                registrationWinnerCopy.add(registration.getTeam(i));
            }

            auxCopy = false;
        }else{
            for(int i = 0; i < winnersList.size(); i++){
                registrationWinnerCopy.add(winnersList.get(i));
            }

            winnersList.clear();
        }

    }

    /*
        createPairs()
        - Cria pares aleatóios para a STAGE
        1) Embaralha a lista criada em copyRegistration()
        2) Inicializa vetor matchesPairs
        3) Percorre metade da lista registrationWinnerCopy (agora, queremos duplas)
            3.1) Uso de auxiliar para coletar os times em registrationWinnerCopy
            3.2) Criação de par com os itens auxiliares
            3.3) Pares são adicionados ao vetor de pares
     */
    private void createPairs(){
        Collections.shuffle(registrationWinnerCopy);
        matchesPairs = new Pairs[registrationWinnerCopy.size()/2];
        int j = 0;

        for(int i = 0; i < (registrationWinnerCopy.size()/2); i++){
            Team auxTeam1 = registrationWinnerCopy.get(j);
            Team auxTeam2 = registrationWinnerCopy.get(j+1);

            Pairs pairs = new Pairs(i, auxTeam1, auxTeam2);
            matchesPairs[i] =  pairs;
            j += 2;

        }
    }

    /*
        inisString()
        - Utilizado em initStage()
        - Painel de duplas para serem selecionadas em partida
     */
    public String initString(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n");

        if (matchesPairs.length == 0) {
            sb.append("Sem duplas adicionadas.");
        } else {
            for (int i = 0; i < matchesPairs.length; i++) {
                sb.append("[ " + (i+1) + " ] ");
                sb.append(matchesPairs[i].getFirstTeam().getName() + " + " + matchesPairs[i].getSecondTeam().getName() + "\n");

            }
        }

        return sb.toString();
    }

    /*
        descendingPointsList()
        - Utilizado em toString()
        - FINAL STAGE
        - Usado para informar todos os dados finais de cada time e seus scores
        1) Torna auxCopy true para realizar copia de registration ao invés de winnerList
        2) Uso de sort para deixar lista copiada em ordem descrescente
     */
    public void descendingPointsList(){
        auxCopy = true;
        copyRegistration();

        registrationWinnerCopy.sort((team1, team2) -> Integer.compare(team2.getTotalPoints(), team1.getTotalPoints()));
    }

    /*
        toString()
        - Utilizado em FINAL STAGE
        - Uso de String Builder para mostrar todos os dados de todos os times
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");

        sb.append(String.format("%-20s", "Time"));
        sb.append(String.format("%-15s", "Total Blots"));
        sb.append(String.format("%-15s", "Total Plifs"));
        sb.append(String.format("%-20s", "Total Advrunghs"));
        sb.append(String.format("%-20s", "Pontuação Final"));
        sb.append("\n");

        descendingPointsList();

        for (int i = 0; i < registrationWinnerCopy.size(); i++) {
            sb.append(String.format("%-20s", registrationWinnerCopy.get(i).getName()));
            sb.append(String.format("%-15d", registrationWinnerCopy.get(i).getTotalBlots()));
            sb.append(String.format("%-15d", registrationWinnerCopy.get(i).getTotalPlifs()));
            sb.append(String.format("%-20d", registrationWinnerCopy.get(i).getTotalAdvrunghs()));
            sb.append(String.format("%-20d", registrationWinnerCopy.get(i).getTotalPoints()));
            sb.append("\n");
        }

        return sb.toString();
    }
}
