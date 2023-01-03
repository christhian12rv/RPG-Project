package entitys;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "batalha")
public class Batalha {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@CreationTimestamp
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;

	@ManyToOne
	private MiniHistoria miniHistoria;

	@ElementCollection
	private List<Monstro> monstros;

	@ElementCollection
	private List<Personagem> iniciativa;

	@ManyToOne
	@JoinColumn(name="partida_id", referencedColumnName = "id")
	private Partida partida;

	public Batalha() {

	}

	public Batalha(MiniHistoria miniHistoria, List<Monstro> monstros, List<Personagem> iniciativa) {
		this.miniHistoria = miniHistoria;
		this.monstros = monstros;
		this.iniciativa = iniciativa;
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

	public MiniHistoria getMiniHistoria() {
		return miniHistoria;
	}

	public void setMiniHistoria(MiniHistoria miniHistoria) {
		this.miniHistoria = miniHistoria;
	}

	public List<Monstro> getMonstros() {
		return monstros;
	}

	public void setMonstros(List<Monstro> monstros) {
		this.monstros = monstros;
	}

	public List<Personagem> getIniciativa() {
		return iniciativa;
	}

	public void setIniciativa(List<Personagem> iniciativa) {
		this.iniciativa = iniciativa;
	}

	public Partida getPartida() {
		return partida;
	}

	public void setPartida(Partida partida) {
		this.partida = partida;
	}
}