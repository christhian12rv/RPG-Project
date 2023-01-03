package entitys;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "personagem")
@Inheritance(strategy = InheritanceType.JOINED)
public class Personagem {

	@Id
    @Column(name = "id")
    @GeneratedValue
    private Integer id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

	@Column(name = "nome")
	private String nome;

	@Column(name = "descricao")
	private String descricao;

	@Column(name = "classe")
	private String classe;

	@Column(name = "vida")
	private int vida;

	@Column(name = "vidaMaxima")
	private int vidaMaxima;

	@Column(name = "constituicao")
	private int constituicao;

	@Column(name = "forca")
	private int forca;

	@Column(name = "destreza")
	private int destreza;

	@Column(name = "sabedoria")
	private int sabedoria;

	@Column(name = "defesa")
	private int defesa;

	@ManyToMany
	private List<Habilidade> habilidades;

	public Personagem() {

	}

	public Personagem(String nome, String descricao, String classe, int vida, int vidaMaxima, int constituicao, int forca, int destreza, int sabedoria, int defesa, List<Habilidade> habilidades) {
		this.nome = nome;
		this.descricao = descricao;
		this.classe = classe;
		this.vida = vida;
		this.vidaMaxima = vidaMaxima;
		this.constituicao = constituicao;
		this.forca = forca;
		this.destreza = destreza;
		this.sabedoria = sabedoria;
		this.defesa = defesa;
		this.habilidades = habilidades;
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

	public String getClasse() {
		return classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}

	public int getVida() {
		return vida;
	}

	public void setVida(int vida) {
		this.vida = vida;
	}

	public int getVidaMaxima() {
		return vidaMaxima;
	}

	public void setVidaMaxima(int vidaMaxima) {
		this.vidaMaxima = vidaMaxima;
	}

	public int getConstituicao() {
		return constituicao;
	}

	public void setConstituicao(int constituicao) {
		this.constituicao = constituicao;
	}

	public int getForca() {
		return forca;
	}

	public void setForca(int forca) {
		this.forca = forca;
	}

	public int getDestreza() {
		return destreza;
	}

	public void setDestreza(int destreza) {
		this.destreza = destreza;
	}

	public int getSabedoria() {
		return sabedoria;
	}

	public void setSabedoria(int sabedoria) {
		this.sabedoria = sabedoria;
	}

	public int getDefesa() {
		return defesa;
	}

	public void setDefesa(int defesa) {
		this.defesa = defesa;
	}

	public List<Habilidade> getHabilidades() {
		return habilidades;
	}

	public void setHabilidades(List<Habilidade> habilidades) {
		this.habilidades = habilidades;
	}
}