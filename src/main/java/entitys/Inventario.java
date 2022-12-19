package entitys;

import java.util.List;

public class Inventario {
 
    private List<Item> itens;

    public List<Item> getItem(int id) {
        return itens;
    }

    public void setItem(List<Item> item) {
        this.itens = itens;
    }
}