package utils.EntitiesUtils;

import enums.DificuldadeMonstro;
import enums.TipoMonstro;
import repositories.HabilidadeRepository;
import repositories.MonstroRepository;

import javax.persistence.EntityManager;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import entities.Habilidade;
import entities.Monstro;
import entities.Personagem;
import utils.JPAUtil;
import utils.JsonUtil;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class MonstroUtil implements JsonUtil {
    private MonstroRepository monstroRepository;
    private HabilidadeRepository habilidadeRepository;

    public void lerJsonESalvar() {
        Gson gson = new Gson();
        try {
            JsonArray jsonArray = gson.fromJson(new FileReader("jsons/monstros.json"), JsonArray.class);
            
            for(JsonElement jsonElement: jsonArray){
                JsonObject jsonObject = jsonElement.getAsJsonObject();

                Personagem personagem = new Personagem();

                personagem.setNome(jsonObject.get("nome").getAsString());
                personagem.setDescricao(jsonObject.get("descricao").getAsString());
                personagem.setClasse(jsonObject.get("classe").getAsString());
                personagem.setVida(jsonObject.get("vida").getAsInt());
                personagem.setVidaMaxima(jsonObject.get("vidaMaxima").getAsInt());
                personagem.setConstituicao(jsonObject.get("constituicao").getAsInt());
                personagem.setForca(jsonObject.get("forca").getAsInt());
                personagem.setDestreza(jsonObject.get("destreza").getAsInt());
                personagem.setSabedoria(jsonObject.get("sabedoria").getAsInt());
                personagem.setDefesa(jsonObject.get("defesa").getAsInt());              

                
                JsonArray habilidadesJsonArray = jsonObject.get("habilidades").getAsJsonArray();
                List<Habilidade> habilidades = new ArrayList<>();

                for (JsonElement element: habilidadesJsonArray) {
                    Integer h = element.getAsInt();
                    Habilidade habilidade = habilidadeRepository.findById(h);
                    if (habilidade == null)
                        throw new Error("Habilidade com id = " + h + " não existe no banco de dados");

                    habilidades.add(habilidade);
                }

                personagem.setHabilidades(habilidades);

                Monstro monstro = new Monstro(personagem);
                monstro.setDificuldade(DificuldadeMonstro.valueOf(jsonObject.get("dificuldade").getAsString()));
                monstro.setTipoMonstro(TipoMonstro.valueOf(jsonObject.get("tipo").getAsString()));
                monstro.setAscii(jsonObject.get("ascii").getAsString());
                
                monstroRepository.save(monstro);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MonstroRepository getMonstroRepository() {
        return this.monstroRepository;
    }

    public void setMonstroRepository(MonstroRepository monstroRepository) {
        this.monstroRepository = monstroRepository;
    }

    public HabilidadeRepository getHabilidadeRepository() {
        return this.habilidadeRepository;
    }

    public void setHabilidadeRepository(HabilidadeRepository habilidadeRepository) {
        this.habilidadeRepository = habilidadeRepository;
    }
}
