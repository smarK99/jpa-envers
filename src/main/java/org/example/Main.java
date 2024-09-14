package org.example;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("example-unit");

        EntityManager em = emf.createEntityManager();
        System.out.println("en marcha Alberto");

        try {

            em.getTransaction().begin();

            Domicilio domicilio1 = Domicilio.builder()
                    .numero(123)
                    .nombreCalle("Avenida Siempre Viva")
                    .build();

            Cliente cliente1 = Cliente.builder()
                    .dni(23456987)
                    .nombre("Ned")
                    .apellido("Flanders")
                    .domicilio(domicilio1)
                    .build();

            //Categorias
            Categoria categoria1 = Categoria.builder()
                    .denominacion("Finanzas")
                    .build();

            Categoria categoria2 = Categoria.builder()
                    .denominacion("Emprendimientos")
                    .build();

            Categoria categoria3 = Categoria.builder()
                    .denominacion("Fantasia")
                    .build();

            //Articulos

            Articulo articulo1 = Articulo.builder()
                    .precio(500)
                    .cantidad(4)
                    .denominacion("El toque de midas")
                    .categorias(List.of(categoria1,categoria2))
                    .build();
            Articulo articulo2 = Articulo.builder()
                    .precio(600)
                    .cantidad(10)
                    .denominacion("Harry Potter y el misterio del principe")
                    .categorias(List.of(categoria3))
                    .build();

            //Detalles

            DetalleFactura detalle1 = DetalleFactura.builder()
                    .articulo(articulo1)
                    .subtotal(1000)
                    .cantidad(2)
                    .build();
            DetalleFactura detalle2 = DetalleFactura.builder()
                    .articulo(articulo2)
                    .subtotal(1200)
                    .cantidad(2)
                    .build();

            //Facturas

            Factura factura1 = Factura.builder()
                    .numero(1)
                    .total(2200)
                    .fecha("8/9/2024")
                    .detallesFactura(List.of(detalle1,detalle2))
                    .cliente(cliente1)
                    .build();


            em.persist(factura1);
            em.flush();
            em.getTransaction().commit();

        }catch (Exception e){
            em.getTransaction().rollback();
            System.out.println(e.getMessage());
            System.out.println("No se pudo grabar");
        }

        // Cerrar el EntityManager y el EntityManagerFactory
        em.close();
        emf.close();
    }
}
