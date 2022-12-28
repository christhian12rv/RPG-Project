package entitys;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "batalha")
public class Batalha {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToOne
	@JoinColumn(name = "minihistoria_id", referencedColumnName = "id")
	private MiniHistoria miniHistoria;

	@ElementCollection
	private List<Monstro> monstros;

	@ElementCollection
	private List<Personagem> iniciativa;

	@ManyToOne
	@JoinColumn(name="partida_id", referencedColumnName = "id")
	private Partida partida;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
}