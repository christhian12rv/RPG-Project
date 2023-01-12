package utils.EntitiesUtils;

import enums.RaridadeArma;
import enums.TipoAtributo;
import repositories.ArmaRepository;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import entities.Arma;
import utils.JsonUtil;

import java.io.*;

public class ArmaUtil implements JsonUtil {
    private ArmaRepository armaRepository;

    public void lerJsonESalvar(){
        Gson gson = new Gson();
        try {
            JsonArray jsonArray = gson.fromJson(new FileReader("jsons/armas.json"), JsonArray.class);

            for(JsonElement jsonElement: jsonArray){
                JsonObject jsonObject = jsonElement.getAsJsonObject();

                Arma arma = new Arma();
                arma.setNome(jsonObject.get("nome").getAsString());
                arma.setDescricao(jsonObject.get("descricao").getAsString());
                arma.setRaridade(RaridadeArma.valueOf(jsonObject.get("raridade").getAsString()));
                arma.setQtdDados(jsonObject.get("qtdDados").getAsInt());
                arma.setTipoDado(jsonObject.get("tipoDado").getAsInt());
                arma.setAdicional(jsonObject.get("adicional").getAsInt());
                arma.setTipoAtributo(TipoAtributo.valueOf(jsonObject.get("tipoAtributo").getAsString()));

                armaRepository.save(arma);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ArmaRepository getArmaRepository() {
        return this.armaRepository;
    }

    public void setArmaRepository(ArmaRepository armaRepository) {
        this.armaRepository = armaRepository;
    }
}
