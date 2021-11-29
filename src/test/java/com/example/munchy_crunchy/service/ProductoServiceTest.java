package com.example.munchy_crunchy.service;

import com.example.munchy_crunchy.model.Producto;
import com.example.munchy_crunchy.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)

public class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    @Test
    void siSeInvocaGetAllDeProductoYExistenSeRetornaUnaListaConProductos(){
        List<Producto> esperados =new ArrayList<>();
        esperados.add(new Producto(1,"Avena","kg",800,20));
        esperados.add(new Producto(2,"Lentejas","kg",100,20));
        List<Producto>resultado= new ArrayList<>();

        when(productoRepository.findAll()).thenReturn(esperados);
        resultado = productoService.getAll();

        assertNotNull(resultado);
        assertThat(resultado).isEqualTo(esperados);



    }

    @Test
    void siSeInvocaGetAllDeProductoYNoExisteProductosSeRetornaUnaListaVacia(){

        //Arrange
        ArrayList<Producto>productos = new ArrayList<>();
        List <Producto> esperado;

        when(productoRepository.findAll()).thenReturn(productos);

        //Act

        esperado = productoService.getAll();

        //Assert

        assertNotNull(esperado);
        assertThat(esperado).isEqualTo(productos);
    }

    @Test
    @Transactional
    void siSeInvocaSaveYRecibeUnObjetoProductoDistintoANullLoGuarda(){

        //Arrange
        Producto esperado = new Producto(3,"Trigo","kg",800,20);
        Producto resultado = new Producto();

        when(productoRepository.saveAndFlush(esperado)).thenReturn(esperado);

        resultado = productoRepository.saveAndFlush(esperado);

        //Assert

        assertNotNull(resultado);
        assertEquals(esperado.getId(),resultado.getId());
        assertEquals(esperado.getNombre(),resultado.getNombre());
        assertEquals(esperado.getUnidadMedida(),resultado.getUnidadMedida());
        assertEquals(esperado.getValor(),resultado.getValor());
        assertEquals(esperado.getCantidad(),resultado.getCantidad());
        verify(productoRepository).saveAndFlush(any(Producto.class));
    }

    @Test
    @Transactional
    void siSeInvocaSaveYRecibeUnObjetoProductoNullArrojaUnaExcepcion(){

        //Arrange
        Producto resultado = null;

        //Act + Assert
        assertThrows(IllegalArgumentException.class,()->productoService.saveProducto(resultado));
    }

    @Test
    @Transactional
    void siSeInvocaDeleteProductoEsteLoBorra(){
        int id = 1;
        productoService.deleteProducto(id);
        verify(productoRepository,times(1)).deleteById(1);
    }

    @Test
    @Transactional
    void siSeInvocaDeleteProductoYFallaSeLanzaUnaExcepcion(){
        Integer id = null;
        assertThrows(NullPointerException.class,()-> productoService.deleteProducto(id));
    }


    @Test
    @Transactional
    void siSeInvocaFindProductoByNombreRetornaElProducto(){
        Producto esperado = new Producto(1,"Avena","kg",800,20);
        when(productoRepository.getProductoByNombre(esperado.getNombre())).thenReturn((esperado));

        Producto resultado = productoService.findProductoByNombre(esperado.getNombre());

        assertNotNull(esperado);
        assertEquals(esperado.getId(),resultado.getId());
        assertEquals(esperado.getNombre(),resultado.getNombre());
        assertEquals(esperado.getUnidadMedida(),resultado.getUnidadMedida());
        assertEquals(esperado.getValor(),resultado.getValor());
        assertEquals(esperado.getCantidad(),resultado.getCantidad());
    }

    @Test
    @Transactional
    void siSeInvocaFindProductoByNombreYNoExisteLanzaUnaExcepcion(){

        String nombre = null;

        assertThrows(NullPointerException.class,()-> productoService.findProductoByNombre(nombre));
    }
}


