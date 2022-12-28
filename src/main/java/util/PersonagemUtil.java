package util;

import entitys.Habilidade;
import entitys.Personagem;
import enums.TipoAtributo;
import service.HabilidadeService;

import java.util.List;
import java.util.Scanner;

public class PersonagemUtil {
    private final int PONTOS_DISTRIBUICAO = 7;

    HabilidadeService habilidadeService;

    public Personagem criarPersonagem() {
        Scanner scanner = new Scanner(System.in);
        String nome = "";
        String descricao = "";
        String classe = "";
        int i = 0;
        int escolha = 0;
        int vida = 10;
        int vidaMaxima = vida;
        int constituicao = 0;
        int forca = 0;
        int destreza = 0;
        int sabedoria = 0;
        int defesa = 0;
        int pontosDistribuicao = this.PONTOS_DISTRIBUICAO;
        TipoAtributo tipoAtributo = TipoAtributo.FORCA;
        Habilidade habilidade = null;
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
            pontosDistribuicao = this.PONTOS_DISTRIBUICAO;
            System.out.println("***************** Pontos de distribuição *****************");
            System.out.println("Cada atributo terá no mínimo 2 pontos e você poderá distribuir +7 para os atributos seguintes.");
            System.out.println("Você poderá distribuir no máximo +4 pontos para cada atributo.");

            System.out.print("Constituição: ");
            constituicao = scanner.nextInt();
            while (constituicao < 0 || constituicao > 4) {
                System.out.println("O atributo não pode conter + que 4 pontos de atributo. Digite novamente:");
                constituicao = scanner.nextInt();
            }
            pontosDistribuicao -= constituicao;

            System.out.print("Força: ");
            forca = scanner.nextInt();
            while (forca < 0 || forca > 4 || forca > pontosDistribuicao) {
                System.out.println("O atributo não pode conter + que 4 pontos de atributo e não deve superar os pontos de " +
                    "distribuição restantes (" + pontosDistribuicao + " pontos de distribuição restantes). Digite novamente:");
                forca = scanner.nextInt();
            }
            pontosDistribuicao -= forca;

            System.out.print("Destreza: ");
            destreza = scanner.nextInt();
            while (destreza < 0 || destreza > 4 || destreza > pontosDistribuicao) {
                System.out.println("O atributo não pode conter + que 4 pontos de atributo e não deve superar os pontos de " +
                    "distribuição restantes (" + pontosDistribuicao + " pontos de distribuição restantes). Digite novamente:");
                destreza = scanner.nextInt();
            }
            pontosDistribuicao -= destreza;

            System.out.print("Sabedoria: ");
            sabedoria = scanner.nextInt();
            while (sabedoria < 0 || sabedoria > 4 || sabedoria > pontosDistribuicao) {
                System.out.println("O atributo não pode conter + que 4 pontos de atributo e não deve superar os pontos de " +
                    "distribuição restantes (" + pontosDistribuicao + " pontos de distribuição restantes). Digite novamente:");
                sabedoria = scanner.nextInt();
            }
            pontosDistribuicao -= sabedoria;

            System.out.print("Defesa: ");
            defesa = scanner.nextInt();
            while (defesa < 0 || defesa > 4 || defesa > pontosDistribuicao) {
                System.out.println("O atributo não pode conter + que 4 pontos de atributo e não deve superar os pontos de " +
                    "distribuição restantes (" + pontosDistribuicao + " pontos de distribuição restantes). Digite novamente:");
                defesa = scanner.nextInt();
            }
            pontosDistribuicao -= defesa;

            if (pontosDistribuicao > 0)
                System.out.println("Você não distribuiu os pontos corretamente. Faltam " + pontosDistribuicao +
                    "para distribuir. Distribua os pontos novamente.");
        }

        if (destreza > forca)
            tipoAtributo = TipoAtributo.DESTREZA;
        
        if (sabedoria > destreza)
            tipoAtributo = TipoAtributo.SABEDORIA;
        
        if (defesa > sabedoria)
            tipoAtributo = TipoAtributo.DEFESA;

        List<Habilidade> habilidades = habilidadeService.findAllRandomByPreRequisitosAndTipo(forca, destreza, sabedoria, defesa, tipoAtributo).subList(0, 3);

        System.out.println("Escolha uma dentre as " + habilidades.size() + " habilidades abaixo:");
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

        vida += constituicao * 3;
        vidaMaxima = vida;

        personagem = new Personagem(nome, descricao, classe, vida, vidaMaxima, constituicao + 2, forca + 2, destreza + 2, sabedoria + 2, defesa + 2, habilidades);
        //personagemService.save(personagem);
        return personagem;
    }

    public HabilidadeService getHabilidadeService() {
        return habilidadeService;
    }

    public void setHabilidadeService(HabilidadeService habilidadeService) {
        this.habilidadeService = habilidadeService;
    }
}