package entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "jogador")
@PrimaryKeyJoinColumn( name = "idPersonagem", referencedColumnName = "id")
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