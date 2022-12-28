package entitys;

import enums.TipoItem;

import javax.persistence.*;

@Entity
@Table(name = "item")
public class Item {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "nome")
	private String nome;

	@Column(name = "descricao")
	private String descricao;

	@Column(name = "tipo")
	private TipoItem tipo;

	@Column(name = "vida")
	private int vida;

	@Column(name = "mana")
	private int mana;

	@ManyToOne
	@JoinColumn(name="inventario_id", referencedColumnName = "id")
	private Inventario inventario;

	public Item(String nome, String descricao, TipoItem tipo, int vida, int mana, Inventario inventario) {
		this.nome = nome;
		this.descricao = descricao;
		this.tipo = tipo;
		this.vida = vida;
		this.mana = mana;
		this.inventario = inventario;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public TipoItem getTipo() {
		return tipo;
	}

	public void setTipo(TipoItem tipo) {
		this.tipo = tipo;
	}

	public int getVida() {
		return vida;
	}

	public void setVida(int vida) {
		this.vida = vida;
	}

	public int getMana() {
		return mana;
	}

	public void setMana(int mana) {
		this.mana = mana;
	}

	public Inventario getInventario() {
		return inventario;
	}

	public void setInventario(Inventario inventario) {
		this.inventario = inventario;
	}

}