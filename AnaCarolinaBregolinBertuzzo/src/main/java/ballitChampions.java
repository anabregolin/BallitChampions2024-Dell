import java.util.*;

public class ballitChampions {

    private static Set<Character> usedChars = new HashSet<>();

    public static void main(String[] args) {

        boolean auxInitialPage = true;

        Scanner input = new Scanner(System.in);
        MongoDBManager mongoDBManager = new MongoDBManager();

        do {
            System.out.println("\n***   B A L L I T  C H A M P I O N S   ***\n[1] Jogar\n[2] Detalhes sobre o jogo\n[3] Fechar jogo");

            int selection;

            try {
                selection = input.nextInt();
                input.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Valor de entrada inválido! Por favor, insira um número correspondente à opção desejada.");
                input.next();
                return;
            }

            switch (selection) {
                case 1:
                    Registration registration = new Registration();
                    boolean auxChoice = true;

                    System.out.println("Para iniciar o jogo, por favor, realize o cadastro de pelo menos oito times e, no "
                            + "\nmáximo, 16 times! Ou então, selecione a criação de times aleatória.");

                    do {

                        System.out.println("\n* CADASTROS *");
                        System.out.println(registration.toString());

                        System.out.println("\n[1] Jogar \n[2] Adicionar time \n[3] Gerar times aleatórios \n[4] Fechar o jogo.");

                        int choice;

                        try {
                            choice = input.nextInt();
                            input.nextLine();

                        } catch (InputMismatchException e) {
                            System.out.println("Valor de entrada inválido! Por favor, insira um número correspondente à opção desejada.");
                            return;
                        }

                        if (choice == 1) {
                            if (registration.size() % 2 != 0 && registration.size() >= 8) {
                                System.out.println("A quantidade de times cadastrados precisa ser par!");
                            } else if (registration.size() < 8) {
                                System.out.println("A quantidade de times cadastrados precisa ser, no mínimo, de 8 times!");
                            } else {
                                System.out.println("INICIANDO PARTIDA...");
                                Stages stage1 = new Stages(registration);
                                stage1.initStage();
                            }
                        } else if (choice == 2) {
                            if (!registration.isFull()) {
                                Team team = createTeam();
                                registration.addTeam(team);
                                mongoDBManager.registrationMongo(
                                        team.getName(),
                                        team.getWarCry(),
                                        team.getFoundedYear()
                                );
                            }
                        } else if (choice == 3) {
                            if (!registration.isFull()) {
                                Team team = createRandomTeam();
                                registration.addTeam(team);
                                mongoDBManager.registrationMongo(
                                        team.getName(),
                                        team.getWarCry(),
                                        team.getFoundedYear()
                                );
                            }
                        } else if (choice == 4) {
                            System.out.println("Retornando à página inicial!");
                            auxChoice = false;
                        }
                    } while (auxChoice);
                    break;

                case 2:
                    System.out.println("Ballit Champions: um jogo esquisito, é uma jogatina proposta por desenvolvedores da Dell um tanto quanto duvidosos em seu humor..." +
                            "\nNele, o usuário deve cadastrar os times desejados e ministrar as partidas, podendo escolher entre pontuar um Blots ou um Plif. Que vença o melhor! ");
                    break;

                case 3:
                    System.out.printf("Fechando o jogo, muito obrigada por sua atenção! :)");
                    auxInitialPage = false;
            }
        }while(auxInitialPage);
    }

    public static Team createRandomTeam() {
        String characters = "KLMNOPQRSTUVWXYZ";
        Random random = new Random();
        char randomChar;

        do {
            randomChar = characters.charAt(random.nextInt(characters.length()));
        } while (usedChars.contains(randomChar));

        usedChars.add(randomChar);

        String nameTeam = "Time " + randomChar;
        String warCry = "AHH " + randomChar;

        int foundedYear = 1900 + random.nextInt(124);

        return new Team(nameTeam, warCry, foundedYear);
    }

    public static Team createTeam() {
        Scanner input = new Scanner(System.in);
        String nameTeam;
        String warCry;
        int foundedYear;

        System.out.println("Insira o nome do time: ");
        nameTeam = input.nextLine();

        System.out.println("Insira o grito de guerra do time: ");
        warCry = input.nextLine();

        System.out.println("Insira a data de fundação do time: ");
        try {
            foundedYear = input.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Valor de entrada inválido! Por favor, insira um número correspondente à opção desejada.");
            input.next();
            return null;
        }

        Team team = new Team(nameTeam, warCry, foundedYear);
        return team;

    }

}