package entitys;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "inventario")
public class Inventario {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @ElementCollection
    private List<Item> itens;

    /*@OneToOne(mappedBy = "inventario")
    private Item item;*/

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

    public List<Item> getItens() {
        return itens;
    }

    public void setItens(List<Item> itens) {
        this.itens = itens;
    }
}