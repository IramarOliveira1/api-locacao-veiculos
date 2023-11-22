package br.fvc.api.dtos.model;

import br.fvc.api.models.Model;

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
        this.url_imagem = "http://ec2-18-212-187-130.compute-1.amazonaws.com:8080/" + model.getUrl_imagem();
    }
}
