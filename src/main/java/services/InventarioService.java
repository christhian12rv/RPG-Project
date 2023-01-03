package services;

import config.AtributosIniciais;
import entitys.Inventario;
import entitys.Item;
import repositorys.InventarioRepository;

import java.util.ArrayList;
import java.util.List;

public class InventarioService {
    ItemService itemService;
    InventarioRepository inventarioRepository;

    public Inventario criarInventarioInicial() {
        Inventario inventario = new Inventario();

        Item itemVida = itemService.criarItemVida(AtributosIniciais.VIDA_ITEM);
        Item itemMana = itemService.criarItemMana(AtributosIniciais.MANA_ITEM);

        List<Item> itens = new ArrayList<>();
        itens.add(itemVida);
        itens.add(itemMana);
        inventario.setItens(itens);

        inventarioRepository.save(inventario);

        return inventario;
    }

    public ItemService getItemService() {
        return itemService;
    }

    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    public InventarioRepository getInventarioRepository() {
        return inventarioRepository;
    }

    public void setInventarioRepository(InventarioRepository inventarioRepository) {
        this.inventarioRepository = inventarioRepository;
    }
}