package util;

import entitys.Inventario;
import entitys.Item;
import service.InventarioService;

import java.util.ArrayList;
import java.util.List;

public class InventarioUtil {
    ItemUtil itemUtil;
    InventarioService inventarioService;

    public Inventario criarInventarioInicial() {
        Inventario inventario = new Inventario();

        Item itemVida = itemUtil.criarItemVida(10);
        Item itemMana = itemUtil.criarItemMana(10);

        List<Item> itens = new ArrayList<>();
        itens.add(itemVida);
        itens.add(itemMana);
        inventario.setItens(itens);

        inventarioService.save(inventario);

        return inventario;
    }

    public ItemUtil getItemUtil() {
        return itemUtil;
    }

    public void setItemUtil(ItemUtil itemUtil) {
        this.itemUtil = itemUtil;
    }

    public InventarioService getInventarioService() {
        return inventarioService;
    }

    public void setInventarioService(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }
}