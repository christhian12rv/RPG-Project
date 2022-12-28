package entitys;

import javax.persistence.*;
import enums.DificuldadeMonstro;

import java.util.List;

@Entity
@Table(name = "monstro")
@PrimaryKeyJoinColumn( name = "idPersonagem" )
public class Monstro extends Personagem {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "dificuldade")
    private DificuldadeMonstro dificuldade;

    @Column(name = "ascii")
    private String ascii;

    public Monstro(Personagem personagem, DificuldadeMonstro dificuldade, String ascii) {
        super(personagem.getNome(), personagem.getDescricao(), personagem.getClasse(), personagem.getVida(),
                personagem.getVidaMaxima(), personagem.getConstituicao(), personagem.getForca(), personagem.getDestreza(),
                personagem.getSabedoria(), personagem.getDefesa(), personagem.getHabilidades());

        this.dificuldade = dificuldade;
        this.ascii = ascii;
    }

    public DificuldadeMonstro getDificuldade() {
        return dificuldade;
    }

    public void setDificuldade(DificuldadeMonstro dificuldade) {
        this.dificuldade = dificuldade;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAscii() {
        return ascii;
    }

    public void setAscii(String ascii) {
        this.ascii = ascii;
    }
}