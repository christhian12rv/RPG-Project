package utils.EntitiesUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import entities.Historia;
import entities.MiniHistoria;
import enums.DificuldadeMonstro;
import enums.TipoMonstro;
import enums.TipoResultadoMiniHistoria;
import repositories.HistoriaRepository;
import repositories.MiniHistoriaRepository;
import utils.JsonUtil;
import utils.ScannerUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HistoriaUtil implements JsonUtil {
    private HistoriaRepository historiaRepository;
    private MiniHistoriaRepository miniHistoriaRepository;
    private ScannerUtil scannerUtil;
    private Scanner scanner;

    public Historia historiaRandomica() {
        Scanner scanner = new Scanner(System.in);
        int i = 1;
        int escolha = 0;

        System.out.println("Histórias");

        List<Historia> historias = null;
        historias = historiaRepository.findAllRandom();


        for (Historia historia: historias) {
            System.out.println("[" + i + "]");
            System.out.println(historia);
            i++;
        }

        System.out.print("Digite uma historia: ");
        escolha = scannerUtil.getInt(scanner, 1, historias.size());

        return historias.get(escolha - 1);
    }

    public void lerJsonESalvar () {
        Gson gson = new Gson();
        try {
            JsonArray jsonArray = gson.fromJson(new FileReader("jsons/historias.json"), JsonArray.class);

            for (JsonElement jsonElement: jsonArray) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                Historia historia = new Historia();
                historia.setTipoMonstros(TipoMonstro.valueOf(jsonObject.get("tipoMonstro").getAsString()));
                historia.setDescricao(jsonObject.get("descricao").getAsString());
                historia.setPreviaDescricao(jsonObject.get("previaDescricao").getAsString());

                List<MiniHistoria> miniHistorias = new ArrayList<>();
                JsonArray jsonArrayMiniHistorias = jsonObject.get("miniHistorias").getAsJsonArray();
                for (JsonElement jsonElementMiniHistoria: jsonArrayMiniHistorias) {
                    JsonObject jsonObjectMiniHistoria = jsonElementMiniHistoria.getAsJsonObject();
                    MiniHistoria miniHistoria = new MiniHistoria();
                    miniHistoria.setDescricao(jsonObjectMiniHistoria.get("descricao").getAsString());
                    miniHistoria.setDificuldade(DificuldadeMonstro.valueOf(jsonObjectMiniHistoria.get("dificuldade").getAsString()));

                    MiniHistoria miniHistoriaEscolhaOposta = null;

                    if (jsonObjectMiniHistoria.get("miniHistoriaEscolhaOposta").isJsonObject()) {
                        JsonObject jsonObjectMiniHistoriaEscolhaOposta = jsonObjectMiniHistoria.get("miniHistoriaEscolhaOposta").getAsJsonObject();
                        miniHistoriaEscolhaOposta = new MiniHistoria();
                        miniHistoriaEscolhaOposta.setDescricao(jsonObjectMiniHistoriaEscolhaOposta.get("descricao").getAsString());
                        miniHistoriaEscolhaOposta.setDificuldade(DificuldadeMonstro.valueOf(jsonObjectMiniHistoriaEscolhaOposta.get("dificuldade").getAsString()));
                        miniHistoriaEscolhaOposta.setMiniHistoriaEscolhaOposta(null);
                        miniHistoriaEscolhaOposta.setResultadoEscolha(jsonObjectMiniHistoriaEscolhaOposta.get("resultadoEscolha").getAsString());
                        miniHistoriaEscolhaOposta.setTextoEntreEventos(jsonObjectMiniHistoriaEscolhaOposta.get("textoEntreEventos").getAsString());
                        miniHistoriaEscolhaOposta.setTipoResultado(TipoResultadoMiniHistoria.valueOf(jsonObjectMiniHistoriaEscolhaOposta.get("tipoResultado").getAsString()));
                        miniHistoriaEscolhaOposta.setDanoCura(jsonObjectMiniHistoriaEscolhaOposta.get("danoCura").getAsInt());
                    }

                    miniHistoria.setMiniHistoriaEscolhaOposta(miniHistoriaEscolhaOposta);
                    miniHistoria.setResultadoEscolha(jsonObjectMiniHistoria.get("resultadoEscolha").getAsString());
                    miniHistoria.setTextoEntreEventos(jsonObjectMiniHistoria.get("textoEntreEventos").getAsString());
                    miniHistoria.setTipoResultado(TipoResultadoMiniHistoria.valueOf(jsonObjectMiniHistoria.get("tipoResultado").getAsString()));
                    miniHistoria.setDanoCura(jsonObjectMiniHistoria.get("danoCura").getAsInt());
                    
                    miniHistorias.add(miniHistoria);
                }

                historia.setMiniHistorias(miniHistorias);

                for (MiniHistoria m: miniHistorias) {
                    if (m.getMiniHistoriaEscolhaOposta() != null) {
                        miniHistoriaRepository.save(m.getMiniHistoriaEscolhaOposta());
                    }
                    miniHistoriaRepository.save(m);
                }

                historiaRepository.save(historia);

                System.out.println(historia);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HistoriaRepository getHistoriaRepository() {
        return historiaRepository;
    }

    public void setHistoriaRepository(HistoriaRepository historiaRepository) {
        this.historiaRepository = historiaRepository;
    }

    public MiniHistoriaRepository getMiniHistoriaRepository() {
        return miniHistoriaRepository;
    }

    public void setMiniHistoriaRepository(MiniHistoriaRepository miniHistoriaRepository) {
        this.miniHistoriaRepository = miniHistoriaRepository;
    }

    public ScannerUtil getScannerUtil() {
        return scannerUtil;
    }

    public void setScannerUtil(ScannerUtil scannerUtil) {
        this.scannerUtil = scannerUtil;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }
}