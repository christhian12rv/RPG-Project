package utils.EntitiesUtils;

import config.AtributosIniciais;
import entities.Habilidade;
import entities.Personagem;
import services.HabilidadeService;

import java.util.List;
import java.util.Scanner;

public class PersonagemUtil {
    private HabilidadeService habilidadeService;

    public Personagem criarPersonagem() {
        Scanner scanner = new Scanner(System.in);
        String nome = "";
        String descricao = "";
        String classe = "";
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
        while (nome.trim().isEmpty()) {
            System.out.print("Nome incorreto. Digite novamente: ");
            nome = scanner.nextLine();
        }

        System.out.print("Descrição: ");
        descricao = scanner.nextLine();
        while (descricao.trim().isEmpty()) {
            System.out.print("Descrição incorreta. Digite novamente: ");
            descricao = scanner.nextLine();
        }

        System.out.print("Classe: ");
        classe = scanner.nextLine();
        while (classe.trim().isEmpty()) {
            System.out.print("Classe incorreta. Digite novamente: ");
            classe = scanner.nextLine();
        }

        while (pontosDistribuicao > 0) {
            pontosDistribuicao = AtributosIniciais.DISTRIBUICAO_PONTOS_TOTAL_INICIAIS;
            System.out.println("***************** Pontos de distribuição *****************");
            System.out.println("Cada atributo terá no mínimo " + AtributosIniciais.PONTOS_INICIAIS + " pontos e você poderá distribuir "+ AtributosIniciais.DISTRIBUICAO_PONTOS_TOTAL_INICIAIS +" para os atributos seguintes.");
            System.out.println("Você poderá distribuir no máximo "+ AtributosIniciais.DISTRIBUICAO_PONTOS_MAXIMA_ATRIBUTO_INICIAIS +" pontos para cada atributo.");

            System.out.print("Constituição: ");
            constituicao = scanner.nextInt();
            while (constituicao < 0 || constituicao > AtributosIniciais.DISTRIBUICAO_PONTOS_MAXIMA_ATRIBUTO_INICIAIS) {
                System.out.println("O atributo não pode conter + que "+ AtributosIniciais.DISTRIBUICAO_PONTOS_MAXIMA_ATRIBUTO_INICIAIS +" pontos de atributo. Digite novamente:");
                constituicao = scanner.nextInt();
            }
            pontosDistribuicao -= constituicao;

            System.out.print("Força: ");
            forca = scanner.nextInt();
            while (forca < 0 || forca > AtributosIniciais.DISTRIBUICAO_PONTOS_MAXIMA_ATRIBUTO_INICIAIS || forca > pontosDistribuicao) {
                System.out.println("O atributo não pode conter + que "+ AtributosIniciais.DISTRIBUICAO_PONTOS_MAXIMA_ATRIBUTO_INICIAIS +" pontos de atributo e não deve superar os pontos de " +
                    "distribuição restantes (" + pontosDistribuicao + " pontos de distribuição restantes). Digite novamente:");
                forca = scanner.nextInt();
            }
            pontosDistribuicao -= forca;

            System.out.print("Destreza: ");
            destreza = scanner.nextInt();
            while (destreza < 0 || destreza > AtributosIniciais.DISTRIBUICAO_PONTOS_MAXIMA_ATRIBUTO_INICIAIS || destreza > pontosDistribuicao) {
                System.out.println("O atributo não pode conter + que "+ AtributosIniciais.DISTRIBUICAO_PONTOS_MAXIMA_ATRIBUTO_INICIAIS +" pontos de atributo e não deve superar os pontos de " +
                    "distribuição restantes (" + pontosDistribuicao + " pontos de distribuição restantes). Digite novamente:");
                destreza = scanner.nextInt();
            }
            pontosDistribuicao -= destreza;

            System.out.print("Sabedoria: ");
            sabedoria = scanner.nextInt();
            while (sabedoria < 0 || sabedoria > AtributosIniciais.DISTRIBUICAO_PONTOS_MAXIMA_ATRIBUTO_INICIAIS || sabedoria > pontosDistribuicao) {
                System.out.println("O atributo não pode conter + que "+ AtributosIniciais.DISTRIBUICAO_PONTOS_MAXIMA_ATRIBUTO_INICIAIS +" pontos de atributo e não deve superar os pontos de " +
                    "distribuição restantes (" + pontosDistribuicao + " pontos de distribuição restantes). Digite novamente:");
                sabedoria = scanner.nextInt();
            }
            pontosDistribuicao -= sabedoria;

            System.out.print("Defesa: ");
            defesa = scanner.nextInt();
            while (defesa < 0 || defesa > AtributosIniciais.DISTRIBUICAO_PONTOS_MAXIMA_ATRIBUTO_INICIAIS || defesa > pontosDistribuicao) {
                System.out.println("O atributo não pode conter + que "+ AtributosIniciais.DISTRIBUICAO_PONTOS_MAXIMA_ATRIBUTO_INICIAIS +" pontos de atributo e não deve superar os pontos de " +
                    "distribuição restantes (" + pontosDistribuicao + " pontos de distribuição restantes). Digite novamente:");
                defesa = scanner.nextInt();
            }
            pontosDistribuicao -= defesa;

            if (pontosDistribuicao > 0)
                System.out.println("Você não distribuiu os pontos corretamente. Faltam " + pontosDistribuicao +
                    "para distribuir. Distribua os pontos novamente.");
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
        escolha = scanner.nextInt();

        while (escolha <= 0 || escolha > habilidades.size()) {
            System.out.print("Habilidade incorreta. Digite novamente: ");
            escolha = scanner.nextInt();
        }

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
}