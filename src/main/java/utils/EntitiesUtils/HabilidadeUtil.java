package utils.EntitiesUtils;

import enums.TipoAtributo;
import enums.TipoDanoHabilidade;
import repositories.HabilidadeRepository;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import entities.Habilidade;
import utils.JsonUtil;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class HabilidadeUtil implements JsonUtil {
    private HabilidadeRepository habilidadeRepository;

    public void lerJsonESalvar() {
        Gson gson = new Gson();
        try {
            JsonArray jsonArray = gson.fromJson(new FileReader("jsons/habilidades.json"), JsonArray.class);
            
            for(JsonElement jsonElement: jsonArray){
                JsonObject jsonObject = jsonElement.getAsJsonObject();

                Habilidade habilidade = new Habilidade();

                habilidade.setNome(jsonObject.get("nome").getAsString());
                habilidade.setDescricao(jsonObject.get("descricao").getAsString());
                habilidade.setTipo(TipoAtributo.valueOf(jsonObject.get("tipo").getAsString()));
                habilidade.setDanoCura(jsonObject.get("danoCura").getAsInt());
                habilidade.setTipoDanoHabilidade(TipoDanoHabilidade.valueOf(jsonObject.get("tipoDanoHabilidade").getAsString()));
                habilidade.setCusto(jsonObject.get("custo").getAsInt());
                habilidade.setArea(jsonObject.get("area").getAsInt());
                habilidade.setDropavel(jsonObject.get("dropavel").getAsBoolean());

                JsonArray preRequisitosJsonArray = jsonObject.get("preRequisitos").getAsJsonArray();
            
                List<Integer> preRequisitos = new ArrayList<>();

                for (JsonElement element : preRequisitosJsonArray) {
                    Integer p = element.getAsInt();
                    preRequisitos.add(p);
                }

                habilidade.setPreRequisitos(preRequisitos);

                habilidadeRepository.save(habilidade);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HabilidadeRepository getHabilidadeRepository() {
        return this.habilidadeRepository;
    }

    public void setHabilidadeRepository(HabilidadeRepository habilidadeRepository) {
        this.habilidadeRepository = habilidadeRepository;
    }
}
