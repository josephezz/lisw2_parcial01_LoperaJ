
package co.unicauca.solid.dip.domain.access;

import co.unicauca.solid.dip.domain.Product;
import java.util.List;

/**
 *
 * @author Libardo, Julio
 */
public interface IProductRepository {
    boolean save(Product newProduct);

    List<Product> list();
}
