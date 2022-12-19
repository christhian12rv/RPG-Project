package entitys;

import javax.persistence.*;

@Entity
@Table(name = "jogador")
public class Jogador extends Personagem {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "mana")
    private int mana;

    @Column(name = "manaMaxima")
    private int manaMaxima;

    @Column(name = "arma")
    private Arma arma;

    @Column(name = "inventario")
    private Inventario inventario;

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