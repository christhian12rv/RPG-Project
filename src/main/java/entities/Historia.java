package entities;

import com.google.gson.annotations.Expose;
import enums.TipoMonstro;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "historia")
public class Historia {
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

	@Enumerated(EnumType.STRING)
	@Column(name = "tipoMonstros")
	private TipoMonstro tipoMonstros;

	@Column(name = "descricao", length = 1500)
	private String descricao;

	@Column(name = "previaDescricao", length = 1500)
	private String previaDescricao;

	@ElementCollection
	private List<MiniHistoria> miniHistorias;

	public Historia() {

	}

	public Historia(TipoMonstro tipoMonstros, String descricao, String previaDescricao, List<MiniHistoria> miniHistorias) {
		this.tipoMonstros = tipoMonstros;
		this.descricao = descricao;
		this.previaDescricao = previaDescricao;
		this.miniHistorias = miniHistorias;
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

	public TipoMonstro getTipoMonstros() {
		return tipoMonstros;
	}

	public void setTipoMonstros(TipoMonstro tipoMonstros) {
		this.tipoMonstros = tipoMonstros;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getPreviaDescricao() {
		return previaDescricao;
	}

	public void setPreviaDescricao(String previaDescricao) {
		this.previaDescricao = previaDescricao;
	}

	public List<MiniHistoria> getMiniHistorias() {
		return miniHistorias;
	}

	public void setMiniHistorias(List<MiniHistoria> miniHistorias) {
		this.miniHistorias = miniHistorias;
	}

	@Override
    public String toString() {
        String valor = "Descri????o: " + previaDescricao +
        "\nTipo: " + tipoMonstros;

        return valor;
    }

}