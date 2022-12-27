package entitys;

import enums.TipoAtributo;

import javax.persistence.*;
import java.util.HashMap;

@Entity
@Table(name = "habilidade")
public class Habilidade {
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "tipo")
    private TipoAtributo tipo;

    @Column(name = "dano")
    private int dano;

    @Column(name = "custo")
    private int custo;
    
    @Column(name = "area")
    private int area;
    
    @Column(name = "dropavel")
    private boolean dropavel;

    private HashMap<TipoAtributo, Integer> preRequisitos;

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

    public TipoAtributo getTipo() {
        return tipo;
    }

    public void setTipo(TipoAtributo tipo) {
        this.tipo = tipo;
    }

    public int getDano() {
        return dano;
    }

    public void setDano(int dano) {
        this.dano = dano;
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

    public HashMap<TipoAtributo, Integer> getPreRequisitos() {
        return preRequisitos;
    }

    public void setPreRequisitos(HashMap<TipoAtributo, Integer> preRequisitos) {
        this.preRequisitos = preRequisitos;
    }

    @Override
    public String toString() {
        String valor = "Nome: " + nome +
        "\nDescrição: " + descricao +
        "\nTipo: " + tipo +
        "\nDano: " + dano +
        "\nCusto: " + custo +
        "\nArea: " + area;

        return valor;
    }
}