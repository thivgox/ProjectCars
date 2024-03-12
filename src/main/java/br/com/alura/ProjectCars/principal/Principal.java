package br.com.alura.ProjectCars.principal;

import br.com.alura.ProjectCars.model.Dados;
import br.com.alura.ProjectCars.model.Modelos;
import br.com.alura.ProjectCars.model.Veiculo;
import br.com.alura.ProjectCars.service.ConsumoApi;
import br.com.alura.ProjectCars.service.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {


   private Scanner leitura = new Scanner(System.in);
   private ConsumoApi  consumo = new ConsumoApi();
   private ConverteDados  conversor = new ConverteDados();
   private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1";

    public void exibeMenu(){
        var menu = """
                *** OPÇÕES ***
                carro
                moto 
                caminhão
                
                
                Digite uma das opções para consultar:  
                """;

        System.out.println(menu);
        var opcao = leitura.next();
         String endereco;

         if(opcao.toLowerCase().contains("carr")){

             endereco = URL_BASE + "/carros/marcas";

         } else if (opcao.toLowerCase().contains("mo")) {

             endereco = URL_BASE + "/motos/marcas";

         }else {
             endereco = URL_BASE + "/caminhoes/marcas";
         }

         var json = consumo.obterDados(endereco);
        System.out.println(json);

        var marcas = conversor.obterLista(json, Dados.class);
        marcas.stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("Informe o codigo da marca");
        var codigoMarca = leitura.next();

         endereco = endereco + "/" + codigoMarca + "/modelos";
         json = consumo.obterDados(endereco);
         var  modeloLista = conversor.obterDados(json, Modelos.class);
        System.out.println(modeloLista);

        System.out.println("\n Modelos dessa marca ");


        modeloLista.modelos().stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("\n escolha o nome do veiculo");
        var nomeVeiculo = leitura.next();

        List<Dados> modelosFiltrados = modeloLista.modelos().stream()
                .filter(m -> m.nome().toLowerCase().contains(nomeVeiculo.toLowerCase()))
                .collect(Collectors.toList());

        System.out.println("Modelos filtrados\n");
        modelosFiltrados.forEach(System.out::println);

        System.out.println("Digite o codigo do modelo");
        var codigoModelo = leitura.next();

        endereco = endereco + "/" + codigoModelo + "/anos";
        json = consumo.obterDados(endereco);
        List<Dados> anos = conversor.obterLista(json, Dados.class);

        List<Veiculo> veiculos = new ArrayList<>();
        for(int i = 0; i < anos.size(); i++) {
            var enderecoAnos = endereco + "/" + anos.get(i).codigo();
            json = consumo.obterDados(enderecoAnos);
            Veiculo veiculo = conversor.obterDados(json, Veiculo.class);
            veiculos.add(veiculo);
        }

        System.out.println("Todos os veiculos filtrados com avaliações por ano");

        veiculos.forEach(System.out::println);
    }
}
