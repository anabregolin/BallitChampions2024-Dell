import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Match{

    private Pairs pairs;
    private int pointsFirstTeam;
    private int pointsSecondTeam;
    private int totalA;
    private int totalB;
    private int totalC;
    private int totalD;
    private int totalAdvrunghtFirst;
    private int totalAdvrunghtSecond;

    public Match(Pairs pairs){ //recebe um par e faz a partida com eles
        this.pairs = pairs;
        pointsFirstTeam = 50;
        pointsSecondTeam = 50;
    }

    /*
        initMatch()
        - Utilizado para executar todos os métodos necessários na etapa de MATCH
        - É válido verificar o funcionamento de cada método para melhor compreensão

        1) Mostra painel de competidores
        2) Definição de valores "lucky" aleatórios
        3) Mostragem de controles e suas funções
        4) Exposição de pontos
        5) Penalidades com valores aleatórios para cada time
        6) Leitura de inserção do tipo char
        7) Exit partida com valor "e" ou chamada de controls()
        8) Verificação de pontos, caso seja empate, realiza grusht()
        9) Atualização de valores finais em cada time
        10) Retorno de vencedor
     */
    public Team initMatch(){
        boolean exit = false;

        System.out.println("\n" + toString());
        Random random = new Random();

        int luckyFirstTeam;
        int luckySecondTeam;

        System.out.println("\nIniciando partida.\n");

        System.out.println("[a] Registrar um 'bloft' para o time "+ pairs.getFirstTeam().getName() + ".");
        System.out.println("[b] Registrar um 'bloft' para o time " + pairs.getSecondTeam().getName() + ".");
        System.out.println("[d] Registrar um 'plift' para o time "+ pairs.getSecondTeam().getName() + ".");
        System.out.println("[c] Registrar um 'plift' para o time "+ pairs.getFirstTeam().getName() + ".");
        System.out.println("[e] Encerrar partida.");

        do{

            System.out.println("\nTime " + getFirstTeam().getName() + ": " + getFirstTeamPoints());
            System.out.println("Time " + getSecondTeam().getName() + ": " + getSecondTeamPoints());


            luckyFirstTeam = random.nextInt(31);
            luckySecondTeam = random.nextInt(31);
            advrunght(luckyFirstTeam, getFirstTeam());
            advrunght(luckySecondTeam, getSecondTeam());

            luckyFirstTeam = random.nextInt(31);
            luckySecondTeam = random.nextInt(31);
            advrunght(luckyFirstTeam, getFirstTeam());
            advrunght(luckySecondTeam, getSecondTeam());

            System.out.println("\nInsira a opção desejada:");

            int input = 0;
            try {
                input = System.in.read();
                while (System.in.read() != '\n');
            } catch (IOException e) {
                System.out.println("Erro na leitura de entrada. Tente novamente!");
                continue;
            }

            char character = (char) input;

            if(character == 'e'){
                exit = true;
                System.out.println("\nEncerrando partida.");
            }else{
                controls(character);
            }

        }while(!exit);


        if(getFirstTeamPoints() == getSecondTeamPoints()){
            grusht();
        }

        getFirstTeam().setTotalBlots(totalA);
        getSecondTeam().setTotalBlots(totalB);
        getFirstTeam().setTotalPlifs(totalC);
        getSecondTeam().setTotalPlifs(totalD);
        getFirstTeam().setTotalAdvrunghs(totalAdvrunghtFirst);
        getSecondTeam().setTotalAdvrunghs(totalAdvrunghtSecond);
        getFirstTeam().setTotalPoints(pointsFirstTeam);
        getSecondTeam().setTotalPoints(pointsSecondTeam);

        if(getFirstTeamPoints() > getSecondTeamPoints()){
            System.out.println("\nTime " + getFirstTeam().getName() + " ganhou!");
            return getFirstTeam();
        }
        else{
            System.out.println("\nTime " + getSecondTeam().getName() + " ganhou!");
            return getSecondTeam();
        }


    }


    /*
        controls()
        - Utilizado em initMatch()
        - Realiza operações conforme controles inseridos
        - Cada charactere realiza uma soma de pontos em determinado time, além de contagem de quantas vezes a
        operação ocorreu. Uso de default para possível erro
     */
    private void controls(char character){
        switch(character){
            case 'a':
                pointsFirstTeam += 5;
                totalA += 1;
                break;

            case 'b':
                pointsSecondTeam += 5;
                totalB += 1;
                break;

            case 'c':
                pointsFirstTeam += 1;
                totalC += 1;
                break;

            case 'd':
                pointsSecondTeam += 1;
                totalD += 1;
                break;

            default:
                System.out.println("Erro em controles.");
                break;
        }
    }

    /*
        grusht()
        - Utilizado em initMatch()
        - Realiza o desempate dos times a partir do grito dos toredores
        - 1 minuto para cada time
        1) Uso de laço para contagem de tempo de 10 em 10 segundos com timeUnit.SECONDS.sleep em 60 segundos
        2) Uso de random para difinir dB do war cry
        3) Print de resultado do primeiro time

        - Repetição de 3 etapas para segundo time

        Retorna o time vencedor com base em comparação ou, em caso de segundo empate, reinicia método grusht()
     */
    private Team grusht(){
        Random random = new Random();
        System.out.println("\nIniciando grusht! Cada torcidas têm 1 minuto para bradar seus gritos de guerra!\n\nTime " + getFirstTeam().getName() + ": ");

        for (int i = 1; i <= 6; i++) {
            try {
                System.out.println("**" + getFirstTeam().getWarCry() + "!!!**");
                TimeUnit.SECONDS.sleep(10);
                System.out.println(i * 10 + " segundos se passaram...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int firstTeamVolume = random.nextInt(101);
        System.out.println("Volume do grito de guerra de torcedores " + getFirstTeam().getName() + ": " + firstTeamVolume + " dB.");
        System.out.println("\nTime " + pairs.getSecondTeam().getName() + ": ");

        for (int i = 1; i <= 6; i++) {
            try {
                System.out.println("**" + pairs.getSecondTeam().getWarCry() + "!!!");
                TimeUnit.SECONDS.sleep(10);
                System.out.println(i * 10 + " segundos se passaram...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int secondTeamVolume = random.nextInt(101);
        System.out.println("Volume do grito de guerra de torcedores " + getSecondTeam().getName() + ": " + secondTeamVolume + " dB.");

        if(firstTeamVolume > secondTeamVolume){
            System.out.println("Time " + pairs.getFirstTeam().getName() + " foi mais alto! + 3 pontos!");
            pointsFirstTeam += 3;
            return getFirstTeam();
        } else if(secondTeamVolume > firstTeamVolume){
            System.out.println("Time " + pairs.getSecondTeam().getName() + " foi mais alto! + 3 pontos!");
            pointsSecondTeam +=3;
            return getSecondTeam();
        } else {
            System.out.println("Oh não! Empate no grusht! Repetindo...");
            return grusht();
        }

    }

    /*
        advrunght()
        - Utilizada em initMatch()
        - Realiza penalidades conforme um valor random, definido como "lucky" em initMatch()
        1) Penalidade de torcida em valores entre 5 e 9
        2) Penalidade para time em valores entre 17 e 20
        3) Penalidade para time em valores 1 e 2
     */
    private void advrunght(int randomAdvrunght, Team team){
        if(randomAdvrunght > 5 && randomAdvrunght < 9){
            System.out.println("Torcida de " + team.getName() + " jogou tomates! - 10 pontos.");
            if(team.getName().equals(pairs.getFirstTeam().getName())){
                pointsFirstTeam -= 10;
                totalAdvrunghtFirst += 1;
            }else{
                pointsSecondTeam -= 10;
                totalAdvrunghtSecond += 1;
            }
        }

        if(randomAdvrunght > 17 && randomAdvrunght < 20){
            System.out.println("Time " + team.getName() + " xingou o juíz de 'Bliblop'! - 10 pontos.");
            if(team.getName().equals(pairs.getFirstTeam().getName())){
                pointsFirstTeam -= 10;
                totalAdvrunghtFirst += 1;
            }else{
                pointsSecondTeam -= 10;
                totalAdvrunghtSecond += 1;
            }
        }

        if(randomAdvrunght > 1 && randomAdvrunght < 2){
            System.out.println("Time " + team.getName() + " ficou emburrado! - 10 pontos.");
            if(team.getName().equals(pairs.getFirstTeam().getName())){
                pointsFirstTeam -= 10;
                totalAdvrunghtFirst += 1;
            }else{
                pointsSecondTeam -= 10;
                totalAdvrunghtSecond += 1;
            }
        }
    }

    public int getFirstTeamPoints(){
        return pointsFirstTeam;
    }

    public int getSecondTeamPoints(){
        return pointsSecondTeam;
    }

    public Team getFirstTeam(){
        return pairs.getFirstTeam();
    }

    public Team getSecondTeam(){
        return pairs.getSecondTeam();
    }

    @Override
    /*
        toString()
        - Utilizado em initMatch()
        - Mostra o painel de competidores (A v.s B)
     */
    public String toString(){
        return pairs.getFirstTeam().getName() + " v.s " + pairs.getSecondTeam().getName();
    }


}
