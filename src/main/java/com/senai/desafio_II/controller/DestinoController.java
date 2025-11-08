package com.senai.desafio_II.controller;

import com.senai.desafio_II.dto.AvaliacaoRequesicaoDto;
import com.senai.desafio_II.dto.DestinoRequesicaoDto;
import com.senai.desafio_II.model.DestinoModel;
import com.senai.desafio_II.service.DestinoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/destinos")
public class DestinoController {

    @Autowired
    private DestinoService destinoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DestinoModel cadastrarDestino(@RequestBody DestinoRequesicaoDto dto) {
        return destinoService.cadastrar(dto);
    }

    @GetMapping
    public List<DestinoModel> listarOuPesquisarDestinos(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String localizacao) {

        if (nome != null || localizacao != null) {
            return destinoService.pesquisar(nome, localizacao);
        }
        return destinoService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DestinoModel> buscarDestinoPorId(@PathVariable Long id) {
        return destinoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DestinoModel> atualizarDestino(@PathVariable Long id, @RequestBody DestinoRequesicaoDto dto) {
        return destinoService.atualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/avaliar")
    public ResponseEntity<DestinoModel> avaliarDestino(@PathVariable Long id, @RequestBody AvaliacaoRequesicaoDto avaliacaoDto) {
        if (avaliacaoDto.getNota() == null || avaliacaoDto.getNota() < 1.0 || avaliacaoDto.getNota() > 10.0) {
            return ResponseEntity.badRequest().build();
        }

        return destinoService.avaliar(id, avaliacaoDto.getNota())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirDestino(@PathVariable Long id) {
        if (destinoService.excluir(id)) {
            return ResponseEntity.noContent().build(); // Retorna 204 No Content se a exclusão for bem-sucedida
        } else {
            return ResponseEntity.notFound().build(); // Retorna 404 Not Found se o destino não existir
        }
    }

}
