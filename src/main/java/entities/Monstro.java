package entities;

import javax.persistence.*;
import enums.DificuldadeMonstro;
import enums.TipoMonstro;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import config.AtributosIniciais;

import java.time.LocalDateTime;
import java.util.Random;

@Entity
@Table(name = "monstro")
public class Monstro extends Personagem implements Comparable<Monstro> {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "dificuldade")
    private DificuldadeMonstro dificuldade;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoMonstro tipo;

    @Column(name = "ascii", length = 2000)
    private String ascii;

    public Monstro() {

    }

    public Monstro(Personagem personagem) {
        super(personagem.getNome(), personagem.getDescricao(), personagem.getClasse(), personagem.getVida(),
                personagem.getVidaMaxima(), personagem.getConstituicao(), personagem.getForca(), personagem.getDestreza(),
                personagem.getSabedoria(), personagem.getDefesa(), personagem.getHabilidades());
    }

    public Monstro(Personagem personagem, String ascii, DificuldadeMonstro dificuldade, TipoMonstro tipo) {
        super(personagem.getNome(), personagem.getDescricao(), personagem.getClasse(), personagem.getVida(),
                personagem.getVidaMaxima(), personagem.getConstituicao(), personagem.getForca(), personagem.getDestreza(),
                personagem.getSabedoria(), personagem.getDefesa(), personagem.getHabilidades());

        this.ascii = ascii;
        this.dificuldade = dificuldade;
        this.tipo = tipo;
    }

    public Monstro(Monstro monstro) {
        super(monstro.getNome(), monstro.getDescricao(), monstro.getClasse(), monstro.getVida(),
                monstro.getVidaMaxima(), monstro.getConstituicao(), monstro.getForca(), monstro.getDestreza(),
                monstro.getSabedoria(), monstro.getDefesa(), monstro.getHabilidades());

        ascii = monstro.getAscii();
        createdAt = monstro.getCreatedAt();
        updatedAt = monstro.getUpdatedAt();
        dificuldade = monstro.getDificuldade();
        id = monstro.getId();
        tipo = monstro.getTipoMonstro();
    }
    
    public String aplicarCuraHabilidade(Monstro monstroEscolhido, Habilidade habilidade) {
        int cura = 0;
        
        switch (habilidade.getTipoDanoHabilidade()) {
            case FIXO:
                cura = habilidade.getDanoCura();
                break;
            case PORCENTAGEM:
                cura = (int) (monstroEscolhido.getVidaMaxima() * ((double)habilidade.getDanoCura()/100));
                break;
            case INTERVALO:
                Random random = new Random();
                cura = random.nextInt(habilidade.getDanoCura()) + 1;
                break;
            default:
                break;
        }

        monstroEscolhido.curar(cura);
    
        return monstroEscolhido.getNome() + " foi curado em " + cura + " de vida!";
    }

    public String aplicarDanoHabilidade(Jogador jogadorEscolhido, Habilidade habilidade) {

        int atributoTipoAtributoHabilidade = 0;
        String mensagem = getNome() + " errou a habilidade " + habilidade.getNome() + " em " + jogadorEscolhido.getNome() + "!";

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
            default:
                break;
        }

        boolean acertouAtaque = acertaAtaque(jogadorEscolhido, atributoTipoAtributoHabilidade);

        if (acertouAtaque) {
            int dano = 0;
            
            switch (habilidade.getTipoDanoHabilidade()) {
                case FIXO:
                    dano = habilidade.getDanoCura() + atributoTipoAtributoHabilidade;
                    break;
                case PORCENTAGEM:
                    dano = (int)(jogadorEscolhido.getVidaMaxima() * (((double)habilidade.getDanoCura() - jogadorEscolhido.getDefesa()) / 100));
                    break;
                case INTERVALO:
                    Random random = new Random();
                    dano = random.nextInt(habilidade.getDanoCura()) + 1;
                    break;
                default:
                    break;
            }

            if (dano < 0)
                dano = 0;

            mensagem = getNome() + " deu " + dano + " de dano em " + jogadorEscolhido.getNome() + "!";

            jogadorEscolhido.receberDano(dano);
            if (!jogadorEscolhido.estaVivo())
                mensagem += "\n " + jogadorEscolhido.getNome() + " foi derrotado!";

            return mensagem;
        }

        return mensagem;
    }

    private Boolean acertaAtaque(Jogador jogador, int atributoTipoAtributoAdicional) {
        Random random = new Random();
        return random.nextInt(AtributosIniciais.TIPO_DADO_DEFESA) + 1 + atributoTipoAtributoAdicional >= jogador.getDefesa() + AtributosIniciais.DEFESA_ADICIONAL;
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

    public DificuldadeMonstro getDificuldade() {
        return dificuldade;
    }

    public void setDificuldade(DificuldadeMonstro dificuldade) {
        this.dificuldade = dificuldade;
    }

    public TipoMonstro getTipoMonstro() {
        return tipo;
    }

    public void setTipoMonstro(TipoMonstro tipo) {
        this.tipo = tipo;
    }

    public String getAscii() {
        return ascii;
    }

    public void setAscii(String ascii) {
        this.ascii = ascii;
    }

    @Override
    public int compareTo(Monstro m) {
        Integer m1AsciiLinesCount = getAscii().split("\n").length;
        Integer m2AsciiLinesCount = m.getAscii().split("\n").length;

        return m1AsciiLinesCount.compareTo(m2AsciiLinesCount);
    }
}