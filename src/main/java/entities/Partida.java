package entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "partida")
public class Partida {
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

	@OneToOne
	@JoinColumn(name = "historia_id", referencedColumnName = "id")
	private Historia historia;

	@OneToMany(mappedBy="partida")
	private List<Batalha> batalhas;

	@OneToMany(mappedBy="partida")
	private List<Jogador> jogadores;

    public Partida() {

    }

    public Partida(Historia historia, List<Batalha> batalhas, List<Jogador> jogadores) {
        this.historia = historia;
        this.batalhas = batalhas;
        this.jogadores = jogadores;
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

    public Historia getHistoria() {
        return historia;
    }

    public void setHistoria(Historia historia) {
        this.historia = historia;
    }

    public List<Batalha> getBatalhas() {
        return batalhas;
    }

    public void setBatalha(List<Batalha> batalhas) {
        this.batalhas = batalhas;
    }

    public List<Jogador> getJogadores() {
        return jogadores;
    }

    public void setJogadores(List<Jogador> jogadores) {
        this.jogadores = jogadores;
    }
}