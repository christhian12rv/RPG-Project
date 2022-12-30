package util;

import entitys.Item;
import enums.TipoItem;
import service.ItemService;

public class ItemUtil {
    ItemService itemService;

    public Item criarItemVida(int v) {
        String nome = "Poção de vida";
        String descricao = "Um líquido vermelho viscoso com sabor doce criado a partir da essência instável " +
            "de um cristal de rubi que cicatriza suas feridas.";
        TipoItem tipoItem = TipoItem.VIDA;
        int vida = v;
        int mana = 0;

        Item item = new Item(nome, descricao, tipoItem, vida, mana);
        itemService.save(item);
        return item;
    }

    public Item criarItemMana(int m) {
        String nome = "Poção de mana";
        String descricao = "Um leve líquido azul que contém a essência da mais antiga árvore do mundo. " +
            "Pode ser amargo, mas é bom para o espírito.";
        TipoItem tipoItem = TipoItem.MANA;
        int vida = 0;
        int mana = m;

        Item item = new Item(nome, descricao, tipoItem, vida, mana);
        itemService.save(item);
        return item;
    }

    public Item criarItemHibrida(int v, int m) {
        String nome = "Poção híbrida";
        String descricao = "Um líquido roxo viscoso com sabor picante. Fabricado das rochas da mais alta montanha do mundo, " +
            "cura não só seus ferimentos mas também seu espírito.";
        TipoItem tipoItem = TipoItem.HIBRIDA;
        int vida = v;
        int mana = m;

        Item item = new Item(nome, descricao, tipoItem, vida, mana);
        itemService.save(item);
        return item;
    }

    public ItemService getItemService() {
        return itemService;
    }

    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }
}