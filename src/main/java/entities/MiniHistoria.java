package entities;

import com.google.gson.annotations.Expose;
import enums.DificuldadeMonstro;
import enums.TipoResultadoMiniHistoria;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "miniHistoria")
public class MiniHistoria {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Expose
	private Integer id;

	@CreationTimestamp
	@Expose
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Expose
	private LocalDateTime updatedAt;

	@Column(name = "descricao")
	private String descricao;

	@Enumerated(EnumType.STRING)
	@Column(name = "dificuldade")
	private DificuldadeMonstro dificuldade;

	@OneToOne
	private MiniHistoria miniHistoriaEscolhaOposta;

	@Column(name = "resultadoEscolha")
	private String resultadoEscolha;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipoResultado")
	private TipoResultadoMiniHistoria tipoResultado;

	@Column(name = "danoCura")
	private Integer danoCura;

	public MiniHistoria() {

	}

	public MiniHistoria(String descricao, DificuldadeMonstro dificuldade, MiniHistoria miniHistoriaEscolhaOposta, String resultadoEscolha, TipoResultadoMiniHistoria tipoResultado, Integer danoCura) {
		this.descricao = descricao;
		this.dificuldade = dificuldade;
		this.miniHistoriaEscolhaOposta = miniHistoriaEscolhaOposta;
		this.resultadoEscolha = resultadoEscolha;
		this.tipoResultado = tipoResultado;
		this.danoCura = danoCura;
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public DificuldadeMonstro getDificuldade() {
		return dificuldade;
	}

	public void setDificuldade(DificuldadeMonstro dificuldade) {
		this.dificuldade = dificuldade;
	}

	public MiniHistoria getMiniHistoriaEscolhaOposta() {
		return miniHistoriaEscolhaOposta;
	}

	public void setMiniHistoriaEscolhaOposta(MiniHistoria miniHistoriaEscolhaOposta) {
		this.miniHistoriaEscolhaOposta = miniHistoriaEscolhaOposta;
	}

	public String getResultadoEscolha() {
		return resultadoEscolha;
	}

	public void setResultadoEscolha(String resultadoEscolha) {
		this.resultadoEscolha = resultadoEscolha;
	}

	public TipoResultadoMiniHistoria getTipoResultado() {
		return tipoResultado;
	}

	public void setTipoResultado(TipoResultadoMiniHistoria tipoResultado) {
		this.tipoResultado = tipoResultado;
	}

	public Integer getDanoCura() {
		return danoCura;
	}

	public void setDanoCura(Integer danoCura) {
		this.danoCura = danoCura;
	}
}