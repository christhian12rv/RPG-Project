package entitys;

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
}