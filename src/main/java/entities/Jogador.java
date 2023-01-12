package entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import config.AtributosIniciais;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Entity
@Table(name = "jogador")
public class Jogador extends Personagem {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private Integer id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    @Column(name = "mana")
    private int mana;

    @Column(name = "manaMaxima")
    private int manaMaxima;

    @OneToOne
    @JoinColumn(name = "arma_id", referencedColumnName = "id")
    private Arma arma;

    @OneToOne
    @JoinColumn(name = "inventario_id", referencedColumnName = "id")
    private Inventario inventario;

    @ManyToOne
    @JoinColumn(name="partida_id", referencedColumnName = "id")
    private Partida partida;

    public Jogador() {

    }

    public Jogador(Personagem personagem, int mana, int manaMaxima, Arma arma, Inventario inventario) {
        super(personagem.getNome(), personagem.getDescricao(), personagem.getClasse(), personagem.getVida(),
            personagem.getVidaMaxima(), personagem.getConstituicao(), personagem.getForca(), personagem.getDestreza(),
            personagem.getSabedoria(), personagem.getDefesa(), personagem.getHabilidades());
        this.mana = mana;
        this.manaMaxima = manaMaxima;
        this.arma = arma;
        this.inventario = inventario;
    }

    public String aplicarCuraHabilidade(Jogador jogadorEscolhido, Habilidade habilidade, boolean gastaMana) {
        int cura = 0;
        
        switch (habilidade.getTipoDanoHabilidade()) {
            case FIXO:
                cura = habilidade.getDanoCura();
                break;
            case PORCENTAGEM:
                cura = (int)(jogadorEscolhido.getVidaMaxima() * ((double)habilidade.getDanoCura()/100));
                break;
            case INTERVALO:
                Random random = new Random();
                cura = random.nextInt(habilidade.getDanoCura()) + 1;
                break;
            default:
                break;
        }

        if (gastaMana) {
            if (getMana() - habilidade.getCusto() > 0)
                setMana(getMana() - habilidade.getCusto());
            else
                setMana(0);
        }
            
        jogadorEscolhido.curar(cura);
    
        return jogadorEscolhido.getNome() + " foi curado em " + cura + " de vida!";
    }

    public String aplicarDanoHabilidade(Monstro monstroEscolhido, Habilidade habilidade, boolean gastaMana) {

        int atributoTipoAtributoHabilidade = 0;
        String mensagem = "Você errou a habilidade " + habilidade.getNome() + " em " + monstroEscolhido.getNome() + "!";

        switch(habilidade.getTipo()) {
            case FORCA:
                atributoTipoAtributoHabilidade = getForca();
                break;
            case DESTREZA:
                atributoTipoAtributoHabilidade = getDestreza();
                break;
            case SABEDORIA:
                atributoTipoAtributoHabilidade = getSabedoria();
                break;
            case DEFESA:
                atributoTipoAtributoHabilidade = getDefesa();
                break;
            default:
                break;
        }

        if (gastaMana) {
            if (getMana() - habilidade.getCusto() > 0)
                setMana(getMana() - habilidade.getCusto());
            else
                setMana(0);
        }

        boolean acertouAtaque = acertaAtaque(monstroEscolhido, atributoTipoAtributoHabilidade + arma.getAdicional());

        if (acertouAtaque) {       
            int dano = 0;
            
            switch (habilidade.getTipoDanoHabilidade()) {
                case FIXO:
                    dano = habilidade.getDanoCura() + atributoTipoAtributoHabilidade;
                    break;
                case PORCENTAGEM:
                    dano = (int)(monstroEscolhido.getVidaMaxima() * (((double)habilidade.getDanoCura() - monstroEscolhido.getDefesa()) / 100));;
                    break;
                case INTERVALO:
                    Random random = new Random();
                    dano = random.nextInt(habilidade.getDanoCura()) + 1;
                    break;
                default:
                    break;
            }

            mensagem = "Você deu " + dano + " de dano em " + monstroEscolhido.getNome() + "!";

            monstroEscolhido.receberDano(dano);
            if (!monstroEscolhido.estaVivo())
                mensagem += "\n " + monstroEscolhido.getNome() + " foi derrotado!";

            return mensagem;
        }

        return mensagem;
    }

    public String aplicarDanoArma(Monstro monstro) {
        String mensagem = "Dano causado: ";

        int dano = 0;

        int atributoTipoAtributoArma = 0;

        switch(arma.getTipoAtributo()) {
            case FORCA:
                atributoTipoAtributoArma = getForca();
                break;
            case DESTREZA:
                atributoTipoAtributoArma = getDestreza();
                break;
            case SABEDORIA:
                atributoTipoAtributoArma = getSabedoria();
                break;
            default:
                break;
        }

        boolean acertouAtaque = acertaAtaque(monstro, atributoTipoAtributoArma + arma.getAdicional());

        if (acertouAtaque) {
            for (int i = 0; i < arma.getQtdDados(); i++) {
                Random random = new Random();
                int dadoEscolhido = random.nextInt(arma.getTipoDado()) + 1;
                dano += dadoEscolhido;
                
                mensagem += "(" + dadoEscolhido + ") + ";
            }

            dano += atributoTipoAtributoArma;
            mensagem += atributoTipoAtributoArma;
            
            dano += arma.getAdicional();
            mensagem += " + " + arma.getAdicional() + " = " + dano;

            monstro.receberDano(dano);
            
            if (!monstro.estaVivo())
                mensagem += "\n " + monstro.getNome() + " foi derrotado!";
            return mensagem;
        }

        return "Você errou o ataque!";
    }

    private Boolean acertaAtaque(Monstro monstro, int atributoTipoAtributoAdicional) {
        Random random = new Random();
        return random.nextInt(AtributosIniciais.TIPO_DADO_DEFESA) + 1 + atributoTipoAtributoAdicional >= monstro.getDefesa() + AtributosIniciais.DEFESA_ADICIONAL;
    }

    public String usarItem(int escolha, Jogador jogador) {
        Item item = inventario.getItens().get(escolha);
        int vida = 0;
        int mana = 0;
        String menssagem = "";

        switch (item.getTipo()) {
            case VIDA:
                vida = item.getVida();
                jogador.curar(vida);
                menssagem = jogador.getNome() + " regenerou " + vida + " de vida!";
                break;
            case MANA:
                mana = item.getMana();
                jogador.regenerarMana(mana);
                menssagem = jogador.getNome() + " regenerou " + mana + " de mana!";
                break;
            case HIBRIDA:
                vida = item.getVida();
                mana = item.getMana();
                jogador.curar(vida);
                jogador.regenerarMana(mana);
                menssagem = jogador.getNome() + " regenerou " + vida + " de vida e " + mana + " de mana!";
                break;
            default:
                break;
        }

        inventario.getItens().remove(item);

        return menssagem;
    }

    public void regenerarVidaPosBatalha() {
        int qtdRegeneracao = (int)(getVidaMaxima() * AtributosIniciais.REGENERACAO_POS_BATALHA);
        if(getVida() + qtdRegeneracao >= getVidaMaxima()) {
            setVida(getVidaMaxima());
        } else {
            setVida(getVida() + qtdRegeneracao);
        }
    }

    public void regenerarManaPosBatalha() {
        int qtdRegeneracao = (int)(getManaMaxima() * AtributosIniciais.REGENERACAO_POS_BATALHA);
        if(getMana() + qtdRegeneracao >= getManaMaxima()) {
            setMana(getManaMaxima());
        } else {
            setMana(getMana() + qtdRegeneracao);
        }
    }
    
    public void regenerarMana(int mana) {
		if (getMana() + mana >= getManaMaxima()) {
            setMana(getManaMaxima());
        } else {
            setMana(getMana() + mana);
        }
	}

    public Boolean temPreRequisitosHabilidade(Habilidade habilidade) {
        List<Integer> preRequisitosHabilidade = habilidade.getPreRequisitos();

        if (getForca() < preRequisitosHabilidade.get(0))
            return false;
        if (getDestreza() < preRequisitosHabilidade.get(1))
            return false;
        if (getSabedoria() < preRequisitosHabilidade.get(2))
            return false;
        if (getDefesa() < preRequisitosHabilidade.get(3))
            return false;

        return true;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getManaMaxima() {
        return manaMaxima;
    }

    public void setManaMaxima(int manaMaxima) {
        this.manaMaxima = manaMaxima;
    }

    public Arma getArma() {
        return arma;
    }

    public void setArma(Arma arma) {
        this.arma = arma;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }

    @Override
    public String toString() {
        String valor = "Nome: " + getNome() + "\n" +
        "Descrição: " + getDescricao() + "\n" +
        "Classe: " + getClasse() + "\n" +
        "Arma inicial: " + getArma().getNome() + "\n" +
        "Habilidade inicial: " + getHabilidades().get(0).getNome() + "\n" +
        "Descrição da Habilidade inicial: " + getHabilidades().get(0).getDescricao();

        return valor;
    }
}