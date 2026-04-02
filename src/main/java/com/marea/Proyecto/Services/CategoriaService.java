package com.marea.Proyecto.Services;

import com.marea.Proyecto.dto.CategoriaDTO;
import com.marea.Proyecto.Model.Categoria;
import com.marea.Proyecto.Repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional
    public CategoriaDTO crearCategoria(CategoriaDTO dto) {
        if (categoriaRepository.existsByNombre(dto.getNombre())) {
            throw new RuntimeException("La categoría ya existe");
        }

        Categoria categoria = new Categoria();
        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());

        Categoria guardada = categoriaRepository.save(categoria);
        return new CategoriaDTO(guardada);
    }

    @Transactional(readOnly = true)
    public List<CategoriaDTO> obtenerTodas() {
        List<Categoria> categorias = categoriaRepository.findAll();
        return categorias.stream()
                .map(CategoriaDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoriaDTO obtenerPorId(Integer id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + id));
        return new CategoriaDTO(categoria);
    }

    @Transactional
    public CategoriaDTO actualizarCategoria(Integer id, CategoriaDTO dto) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + id));

        if (dto.getNombre() != null && !dto.getNombre().isEmpty() &&
            !dto.getNombre().equals(categoria.getNombre())) {
            if (categoriaRepository.existsByNombre(dto.getNombre())) {
                throw new RuntimeException("El nombre de categoría ya está en uso");
            }
            categoria.setNombre(dto.getNombre());
        }

        if (dto.getDescripcion() != null) {
            categoria.setDescripcion(dto.getDescripcion());
        }

        Categoria actualizada = categoriaRepository.save(categoria);
        return new CategoriaDTO(actualizada);
    }

    @Transactional
    public void eliminarCategoria(Integer id) {
        if (!categoriaRepository.existsById(id)) {
            throw new RuntimeException("Categoría no encontrada con ID: " + id);
        }
        categoriaRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public boolean existePorNombre(String nombre) {
        return categoriaRepository.existsByNombre(nombre);
    }
}
