package entitys;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "partida")
public class Partida {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToOne
	@JoinColumn(name = "historia_id", referencedColumnName = "id")
	private Historia historia;

	@OneToMany(mappedBy="partida")
	private List<Batalha> batalhas;

	@OneToMany(mappedBy="partida")
	private List<Jogador> jogadores;
}