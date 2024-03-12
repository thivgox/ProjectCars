package br.com.alura.ProjectCars.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)

public record Modelos(List<Dados> modelos) {

}
