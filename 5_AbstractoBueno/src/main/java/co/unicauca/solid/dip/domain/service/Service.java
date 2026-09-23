package co.unicauca.solid.dip.domain.service;

import co.unicauca.solid.dip.domain.access.IProductRepository;
import co.unicauca.solid.dip.domain.Product;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Libardo, Julio
 */
public class Service {

    // Ahora hay una dependencia de una abstracción, no es algo concreto,
    // no sabe cómo está implementado.
    private IProductRepository repository;

    /**
     * Inyección de dependencias en el constructor. Ya no conviene que el mismo
     * servicio cree un repositorio concreto
     *
     * @param repository una clase hija de IProductRepository
     */
    public Service(IProductRepository repository) {
        this.repository = repository;
    }

    public double calculateProductTax(Product product) {

        //Validate product.
        if (product == null) {
            return 0;
        }
        double TAX = 0.19d;
        double productTax = product.getPrice() * TAX;
        return productTax;
    }

    public boolean saveProduct(Product newProduct) {

        //Validate product
        if (newProduct == null || newProduct.getProductId() < 0 || newProduct.getName().isBlank()) {
            return false;
        }

        repository.save(newProduct);
        return true;

    }

    public List<Product> listProducts() {
        List<Product> products = new ArrayList<>();
        products = repository.list();;

        return products;
    }

}
