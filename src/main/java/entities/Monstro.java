package entities;

import javax.persistence.*;
import enums.DificuldadeMonstro;
import enums.TipoMonstro;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "monstro")
@PrimaryKeyJoinColumn( name = "idPersonagem" )
public class Monstro extends Personagem {
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

    @Column(name = "ascii")
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
}