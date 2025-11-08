package com.senai.desafio_II.service;

import com.senai.desafio_II.dto.DestinoRequesicaoDto;
import com.senai.desafio_II.model.DestinoModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class DestinoService {

    //Como não foi citado banco de dados na atividade, vou inicialiazar com hard code
    private final Map<Long, DestinoModel> destinos = new ConcurrentHashMap<>();
    private final AtomicLong nextId = new AtomicLong(1);

    public DestinoService() {
        cadastrar(new DestinoRequesicaoDto() {{
            setNome("Rio de Janeiro");
            setLocalizacao("Brasil");
            setDescricao("Cidade Maravilhosa, com praias e Cristo Redentor.");
            setPrecoMedioPacote(1500.00);
        }});
        cadastrar(new DestinoRequesicaoDto() {{
            setNome("Paris");
            setLocalizacao("França");
            setDescricao("A Cidade Luz, lar da Torre Eiffel e museus.");
            setPrecoMedioPacote(3000.00);
        }});
    }

    public DestinoModel cadastrar(DestinoRequesicaoDto dto) {
        Long id = nextId.getAndIncrement();
        DestinoModel novoDestino = new DestinoModel(id, dto.getNome(), dto.getLocalizacao(), dto.getDescricao(), dto.getPrecoMedioPacote());
        destinos.put(id, novoDestino);
        return novoDestino;
    }

    public List<DestinoModel> listar() {
        return new ArrayList<>(destinos.values());
    }

    public Optional<DestinoModel> buscarPorId(Long id) {
        return Optional.ofNullable(destinos.get(id));
    }

    public List<DestinoModel> pesquisar(String nome, String localizacao) {
        String nomeBusca = nome != null ? nome.toLowerCase() : null;
        String localizacaoBusca = localizacao != null ? localizacao.toLowerCase() : null;

        return destinos.values().stream()
                .filter(d -> (nomeBusca == null || d.getNome().toLowerCase().contains(nomeBusca)) &&
                        (localizacaoBusca == null || d.getLocalizacao().toLowerCase().contains(localizacaoBusca)))
                .collect(Collectors.toList());
    }

    public Optional<DestinoModel> avaliar(Long id, Double novaNota) {
        DestinoModel destino = destinos.get(id);
        if (destino == null) {
            return Optional.empty();
        }

        if (novaNota == null || novaNota < 1 || novaNota > 10) {
            return Optional.empty();
        }

        int totalAntigo = destino.getTotalAvaliacoes();
        double mediaAntiga = destino.getNotaMedia();

        double novaMedia = ((mediaAntiga * totalAntigo) + novaNota) / (totalAntigo + 1);

        destino.setNotaMedia(Math.round(novaMedia * 100.0) / 100.0); // Arredonda para 2 casas decimais
        destino.setTotalAvaliacoes(totalAntigo + 1);

        return Optional.of(destino);
    }

    public Optional<DestinoModel> atualizar(Long id, DestinoRequesicaoDto dto) {
        DestinoModel destino = destinos.get(id);
        if (destino == null) {
            return Optional.empty();
        }

        destino.setNome(dto.getNome());
        destino.setLocalizacao(dto.getLocalizacao());
        destino.setDescricao(dto.getDescricao());
        destino.setPrecoMedioPacote(dto.getPrecoMedioPacote());

        return Optional.of(destino);
    }

    public boolean excluir(Long id) {
        return destinos.remove(id) != null;
    }

}
