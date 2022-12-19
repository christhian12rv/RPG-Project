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

	@Column(name = "miniHistoria")
	private MiniHistoria miniHistoria;

	private List<Monstro> monstros;

	private List<Personagem> iniciativa;

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