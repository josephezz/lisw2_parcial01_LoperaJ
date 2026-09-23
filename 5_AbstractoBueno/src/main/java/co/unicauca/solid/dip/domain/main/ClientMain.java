package co.unicauca.solid.dip.domain.main;

import co.unicauca.solid.dip.domain.Product;
import co.unicauca.solid.dip.domain.access.Factory;
import co.unicauca.solid.dip.domain.access.IProductRepository;
import co.unicauca.solid.dip.domain.service.Service;

/**
 *
 * @author Libardo, Julio
 */
public class ClientMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Le decimos a la fábrica que nos de el repositorio por defecto
        IProductRepository repository = Factory.getInstance().getRepository("default");
        Service service = new Service(repository);

        Product newProduct = new Product(1, "Product One", 10d);
        service.saveProduct(newProduct);

        newProduct = new Product(2, "Product Two", 50);
        service.saveProduct(newProduct);

        for (Product p : service.listProducts()) {
            System.out.println(p);
        }

    }

}
