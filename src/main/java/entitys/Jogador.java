package entitys;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "jogador")
@PrimaryKeyJoinColumn( name = "idPersonagem" )
public class Jogador extends Personagem {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}