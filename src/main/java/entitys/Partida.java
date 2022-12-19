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

	@Column(name = "historia")
	private Historia historia;

	private List<Batalha> batalhas;

	private List<Jogador> jogadores;
}