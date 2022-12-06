package entity;

import javax.persistence.*;

@Entity
@Table(name = "monstro")
public class Monstro {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "dificuldade")
    private DificuldadeMonstro dificuldade;

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
}