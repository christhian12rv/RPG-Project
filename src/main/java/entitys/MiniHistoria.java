package entitys;

import enums.DificuldadeMonstro;

import javax.persistence.*;

@Entity
@Table(name = "miniHistoria")
public class MiniHistoria {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "historia")
	private Historia historia;

	@Column(name = "dificuldade")
	private DificuldadeMonstro dificuldade;

	@Column(name = "ordem")
	private Integer ordem;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Historia getHistoria() {
		return historia;
	}

	public void setHistoria(Historia historia) {
		this.historia = historia;
	}

	public DificuldadeMonstro getDificuldade() {
		return dificuldade;
	}

	public void setDificuldade(DificuldadeMonstro dificuldade) {
		this.dificuldade = dificuldade;
	}

	public Integer getOrdem() {
		return ordem;
	}

	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}

}