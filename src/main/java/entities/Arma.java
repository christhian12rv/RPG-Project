package entities;

import enums.RaridadeArma;
import enums.TipoAtributo;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "arma")
public class Arma {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@CreationTimestamp
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;

	@Column(name = "nome")
	private String nome;

	@Column(name = "descricao")
	private String descricao;

	@Enumerated(EnumType.STRING)
	@Column(name = "raridade")
	private RaridadeArma raridade;

	@Column(name = "qtdDados")
	private int qtdDados;

	@Column(name = "tipoDado")
	private int tipoDado;

	@Column(name = "adicional")
	private int adicional;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipoAtributo")
	private TipoAtributo tipoAtributo;

	public Arma() {

	}

	public Arma(String nome, String descricao, RaridadeArma raridade, int qtdDados, int tipoDado, int adicional, TipoAtributo tipoAtributo) {
		this.nome = nome;
		this.descricao = descricao;
		this.raridade = raridade;
		this.qtdDados = qtdDados;
		this.tipoDado = tipoDado;
		this.adicional = adicional;
		this.tipoAtributo = tipoAtributo;
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

	public RaridadeArma getRaridade() {
		return raridade;
	}

	public void setRaridade(RaridadeArma raridade) {
		this.raridade = raridade;
	}

	public int getQtdDados() {
		return qtdDados;
	}

	public void setQtdDados(int qtdDados) {
		this.qtdDados = qtdDados;
	}

	public int getTipoDado() {
		return tipoDado;
	}

	public void setTipoDado(int tipoDado) {
		this.tipoDado = tipoDado;
	}

	public int getAdicional() {
		return adicional;
	}

	public void setAdicional(int adicional) {
		this.adicional = adicional;
	}

	public TipoAtributo getTipoAtributo() {
		return tipoAtributo;
	}

	public void setTipoAtributo(TipoAtributo tipoAtributo) {
		this.tipoAtributo = tipoAtributo;
	}

}