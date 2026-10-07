package com.ejemplo.tp1_APIREST.Service;
import java.util.List;
import org.springframework.stereotype.Service;
import com.ejemplo.tp1_APIREST.Model.Lista;
import com.ejemplo.tp1_APIREST.Repository.ListaRepository;

@Service
public class ListaService {
    private final ListaRepository listaRepository;

    public ListaService(ListaRepository listaRepository) {
        this.listaRepository = listaRepository;
    }

    public Lista crear(String nombre) {

        Lista lista = new Lista();

        lista.setNombre(nombre);

        return listaRepository.guardar(lista);
    }

    public List<Lista> buscarTodos() {

        return listaRepository.buscarTodos();
    }

    public Lista buscarPorId(Long id) {

        return listaRepository.buscarPorId(id)
                .orElseThrow(() ->
                    new RuntimeException(
                        "No existe una lista con el id " + id
                    )
                );
    }

    public void eliminar(Long id) {

        if (!listaRepository.eliminarPorId(id)) {
            throw new RuntimeException(
                "No existe una lista con el id " + id
            );
        }
    }
}
