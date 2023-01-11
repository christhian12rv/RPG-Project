package utils.EntitiesUtils;

import config.AtributosIniciais;
import entities.Habilidade;
import entities.Jogador;
import entities.Personagem;
import services.HabilidadeService;
import utils.ScannerUtil;

import java.util.List;
import java.util.Scanner;

public class PersonagemUtil {
    private HabilidadeService habilidadeService;
    private ScannerUtil scannerUtil;
    private Scanner scanner;

    public Personagem criarPersonagemJogador(List<Jogador> jogadores) {
        String nome = "";
        String descricao = "";
        String classe = "";
        boolean existeJogador = false;
        int i = 0;
        int escolha = 0;
        int vida = 0;
        int vidaMaxima = 0;
        int constituicao = 0;
        int forca = 0;
        int destreza = 0;
        int sabedoria = 0;
        int defesa = 0;
        int pontosDistribuicao = AtributosIniciais.DISTRIBUICAO_PONTOS_TOTAL_INICIAIS;

        Personagem personagem = null;
        
        System.out.print("Nome: ");
        nome = scanner.nextLine();

        for (Jogador jogador: jogadores) {
            if (nome.toLowerCase().equals(jogador.getNome().toLowerCase()))
                existeJogador = true;
        }

        while (nome.trim().isEmpty() || existeJogador) {
            System.out.print("Nome incorreto ou já existe um jogador com esse nome. Digite novamente: ");
            nome = scanner.nextLine();

            existeJogador = false;
            for (Jogador jogador: jogadores) {
                if (nome.toLowerCase().equals(jogador.getNome().toLowerCase()))
                    existeJogador = true;
            }
        }
        nome = nome.substring(0,1).toUpperCase() + nome.substring(1).toLowerCase();

        System.out.print("Descrição: ");
        descricao = scanner.nextLine();
        while (descricao.trim().isEmpty()) {
            System.out.print("Descrição incorreta. Digite novamente: ");
            descricao = scanner.nextLine();
        }
        descricao = descricao.substring(0,1).toUpperCase() + descricao.substring(1).toLowerCase();

        System.out.print("Classe: ");
        classe = scanner.nextLine();
        while (classe.trim().isEmpty()) {
            System.out.print("Classe incorreta. Digite novamente: ");
            classe = scanner.nextLine();
        }
        classe = classe.substring(0,1).toUpperCase() + classe.substring(1).toLowerCase();

        while (pontosDistribuicao > 0) {
            pontosDistribuicao = AtributosIniciais.DISTRIBUICAO_PONTOS_TOTAL_INICIAIS;
            System.out.println("\n***************** Pontos de distribuição *****************");
            System.out.println("Cada atributo terá no mínimo " + AtributosIniciais.PONTOS_INICIAIS + " pontos e você poderá distribuir "+ AtributosIniciais.DISTRIBUICAO_PONTOS_TOTAL_INICIAIS +" para os atributos seguintes.");
            System.out.println("Você poderá distribuir no máximo "+ AtributosIniciais.DISTRIBUICAO_PONTOS_MAXIMA_ATRIBUTO_INICIAIS +" pontos para cada atributo.");

            System.out.print("Constituição: ");
            constituicao = scannerUtil.getInt(scanner);
            while (constituicao < 0 || constituicao > AtributosIniciais.DISTRIBUICAO_PONTOS_MAXIMA_ATRIBUTO_INICIAIS) {
                System.out.println("O atributo não pode conter + que "+ AtributosIniciais.DISTRIBUICAO_PONTOS_MAXIMA_ATRIBUTO_INICIAIS +" pontos de atributo. Digite novamente:");
                constituicao = scannerUtil.getInt(scanner);
            }
            pontosDistribuicao -= constituicao;

            System.out.print("Força: ");
            forca = scannerUtil.getInt(scanner);
            while (forca < 0 || forca > AtributosIniciais.DISTRIBUICAO_PONTOS_MAXIMA_ATRIBUTO_INICIAIS || forca > pontosDistribuicao) {
                System.out.println("O atributo não pode conter + que "+ AtributosIniciais.DISTRIBUICAO_PONTOS_MAXIMA_ATRIBUTO_INICIAIS +" pontos de atributo e não deve superar os pontos de " +
                    "distribuição restantes (" + pontosDistribuicao + " pontos de distribuição restantes). Digite novamente:");
                forca = scannerUtil.getInt(scanner);
            }
            pontosDistribuicao -= forca;

            System.out.print("Destreza: ");
            destreza = scannerUtil.getInt(scanner);
            while (destreza < 0 || destreza > AtributosIniciais.DISTRIBUICAO_PONTOS_MAXIMA_ATRIBUTO_INICIAIS || destreza > pontosDistribuicao) {
                System.out.println("O atributo não pode conter + que "+ AtributosIniciais.DISTRIBUICAO_PONTOS_MAXIMA_ATRIBUTO_INICIAIS +" pontos de atributo e não deve superar os pontos de " +
                    "distribuição restantes (" + pontosDistribuicao + " pontos de distribuição restantes). Digite novamente:");
                destreza = scannerUtil.getInt(scanner);
            }
            pontosDistribuicao -= destreza;

            System.out.print("Sabedoria: ");
            sabedoria = scannerUtil.getInt(scanner);
            while (sabedoria < 0 || sabedoria > AtributosIniciais.DISTRIBUICAO_PONTOS_MAXIMA_ATRIBUTO_INICIAIS || sabedoria > pontosDistribuicao) {
                System.out.println("O atributo não pode conter + que "+ AtributosIniciais.DISTRIBUICAO_PONTOS_MAXIMA_ATRIBUTO_INICIAIS +" pontos de atributo e não deve superar os pontos de " +
                    "distribuição restantes (" + pontosDistribuicao + " pontos de distribuição restantes). Digite novamente:");
                sabedoria = scannerUtil.getInt(scanner);
            }
            pontosDistribuicao -= sabedoria;

            System.out.print("Defesa: ");
            defesa = scannerUtil.getInt(scanner);
            while (defesa < 0 || defesa > AtributosIniciais.DISTRIBUICAO_PONTOS_MAXIMA_ATRIBUTO_INICIAIS || defesa > pontosDistribuicao) {
                System.out.println("O atributo não pode conter + que "+ AtributosIniciais.DISTRIBUICAO_PONTOS_MAXIMA_ATRIBUTO_INICIAIS +" pontos de atributo e não deve superar os pontos de " +
                    "distribuição restantes (" + pontosDistribuicao + " pontos de distribuição restantes). Digite novamente:");
                defesa = scannerUtil.getInt(scanner);
            }
            pontosDistribuicao -= defesa;

            if (pontosDistribuicao > 0)
                System.out.println("\nVocê não distribuiu os pontos corretamente. Faltam " + pontosDistribuicao +
                    " para distribuir. Distribua os pontos novamente.");
        }

        constituicao += AtributosIniciais.PONTOS_INICIAIS;
        forca += AtributosIniciais.PONTOS_INICIAIS;
        destreza += AtributosIniciais.PONTOS_INICIAIS;
        sabedoria += AtributosIniciais.PONTOS_INICIAIS;
        defesa += AtributosIniciais.PONTOS_INICIAIS;

        List<Habilidade> habilidades = habilidadeService.findHabilidadesByPreRequisitos(forca, destreza, sabedoria, defesa);

        System.out.println("\nEscolha uma dentre as " + habilidades.size() + " habilidades abaixo:");
        i = 1;
        for (Habilidade h: habilidades) {
            System.out.println("[" + i + "]\n" + h + "\n");
            i++;
        }

        System.out.print("Digite uma habilidade: ");
        escolha = scannerUtil.getInt(scanner);

        while (escolha <= 0 || escolha > habilidades.size()) {
            System.out.print("Habilidade incorreta. Digite novamente: ");
            escolha = scannerUtil.getInt(scanner);
        }
        scanner.nextLine();

        habilidades = habilidades.subList(escolha - 1, escolha);

        vida = AtributosIniciais.VIDA + (constituicao * AtributosIniciais.MULTIPLICADOR_VIDA);
        vidaMaxima = vida;

        personagem = new Personagem(nome, descricao, classe, vida, vidaMaxima, constituicao, forca, destreza, sabedoria, defesa, habilidades);

        return personagem;
    }

    public HabilidadeService getHabilidadeService() {
        return habilidadeService;
    }

    public void setHabilidadeService(HabilidadeService habilidadeService) {
        this.habilidadeService = habilidadeService;
    }

    public ScannerUtil getScannerUtil() {
        return scannerUtil;
    }

    public void setScannerUtil(ScannerUtil scannerUtil) {
        this.scannerUtil = scannerUtil;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }
}