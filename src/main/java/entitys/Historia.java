package entitys;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "historia")
public class Historia {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "tipo")
	private String tipo;

	@Column(name = "descricao")
	private String descricao;

	private List<MiniHistoria> miniHistorias;

}