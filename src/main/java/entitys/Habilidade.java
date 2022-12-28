package entitys;

import enums.TipoAtributo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @Enumerated(EnumType.STRING)
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

    @ElementCollection
    @Column(name = "preRequisitos")
    private List<Integer> preRequisitos = new ArrayList<>();

    public Habilidade() {

    }

    public Habilidade(String nome, String descricao, TipoAtributo tipo, int dano, int custo, int area, boolean dropavel, List<Integer> preRequisitos) {
        this.nome = nome;
        this.descricao = descricao;
        this.tipo = tipo;
        this.dano = dano;
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
        "\nDano: " + dano +
        "\nCusto: " + custo +
        "\nArea: " + area;

        return valor;
    }
}