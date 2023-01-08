package entities;

import enums.TipoAtributo;
import enums.TipoDanoHabilidade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "habilidade")
public class Habilidade {
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
    @Column(name = "tipo")
    private TipoAtributo tipo;

    @Column(name = "danoCura")
    private int danoCura;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipoDanoHabilidade")
    private TipoDanoHabilidade tipoDanoHabilidade;

    @Column(name = "custo")
    private int custo;
    
    @Column(name = "area")
    private int area;
    
    @Column(name = "dropavel")
    private boolean dropavel;

    @ElementCollection
    @Column(name = "preRequisitos")
    private List<Integer> preRequisitos = new ArrayList<>();

    public Habilidade() {

    }

    public Habilidade(String nome, String descricao, TipoAtributo tipo, int danoCura, TipoDanoHabilidade tipoDanoHabilidade, int custo, int area, boolean dropavel, List<Integer> preRequisitos) {
        this.nome = nome;
        this.descricao = descricao;
        this.tipo = tipo;
        this.danoCura = danoCura;
        this.tipoDanoHabilidade = tipoDanoHabilidade;
        this.custo = custo;
        this.area = area;
        this.dropavel = dropavel;
        this.preRequisitos = preRequisitos;
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

    public TipoAtributo getTipo() {
        return tipo;
    }

    public void setTipo(TipoAtributo tipo) {
        this.tipo = tipo;
    }

    public int getDanoCura() {
        return danoCura;
    }

    public void setDanoCura(int danoCura) {
        this.danoCura = danoCura;
    }

    public TipoDanoHabilidade getTipoDanoHabilidade() {
        return tipoDanoHabilidade;
    }

    public void setTipoDanoHabilidade(TipoDanoHabilidade tipoDanoHabilidade) {
        this.tipoDanoHabilidade = tipoDanoHabilidade;
    }

    public int getCusto() {
        return custo;
    }

    public void setCusto(int custo) {
        this.custo = custo;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public boolean getDropavel() {
        return dropavel;
    }

    public void setDropavel(boolean dropavel) {
        this.dropavel = dropavel;
    }

    public List<Integer> getPreRequisitos() {
        return preRequisitos;
    }

    public void setPreRequisitos(List<Integer> preRequisitos) {
        this.preRequisitos = preRequisitos;
    }

    @Override
    public String toString() {
        String valor = "Nome: " + nome +
        "\nDescrição: " + descricao +
        "\nTipo: " + tipo +
        "\nDano/Cura: " + danoCura +
        "\nTipo de dano: " + tipoDanoHabilidade.getValor() + 
        "\nCusto: " + custo +
        "\nArea: " + area;

        return valor;
    }

    public String toString(Personagem personagem) {
        int valorAtributo = 0;
        
        switch (tipo) {
            case FORCA:
                valorAtributo = personagem.getForca();
                break;
            case DESTREZA:
                valorAtributo = personagem.getDestreza();
                break;
            case SABEDORIA:
                valorAtributo = personagem.getSabedoria();
                break;
            case DEFESA:
                valorAtributo = personagem.getDefesa();
                break;
        }
        
        String dano = "";
        if (tipoDanoHabilidade == TipoDanoHabilidade.FIXO) {
            dano = danoCura + " + " + valorAtributo; 
        } else if (tipoDanoHabilidade == TipoDanoHabilidade.PORCENTAGEM) {
            dano = danoCura + "%";
        } else {
            dano = "1 - " + danoCura;
        }
        
        String valor = "Nome: " + nome +
        "\nDescrição: " + descricao +
        "\nTipo: " + tipo +
        "\nDano/Cura: " + dano +
        "\nCusto: " + custo +
        "\nArea: " + area;

        return valor;
    }
}