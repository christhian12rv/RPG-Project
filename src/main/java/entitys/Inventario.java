package entitys;

public class Inventario {
 
    private List<Item> itens;

    public Item getItem(int id) {
        return itens[id];
    }

    public void setItem(int item) {
        itens.add(item);
    }
}