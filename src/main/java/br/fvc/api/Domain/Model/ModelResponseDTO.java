package br.fvc.api.Domain.Model;

import br.fvc.api.Models.Model;

public class ModelResponseDTO {
    public Long id;
    public String nome;
    public int quantidade;
    public String categoria;
    public String valor_diaria;
    public String url_imagem;

    public ModelResponseDTO(Model model) {
        this.id = model.getId();
        this.quantidade = model.getQuantidade();
        this.nome = model.getNome();
        this.valor_diaria = model.getValor_diaria();
        this.categoria = model.getCategoria();
        this.url_imagem = "http://localhost:8080/" + model.getUrl_imagem();
    }
}
