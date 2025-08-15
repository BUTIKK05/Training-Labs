package org.example.lab6_ds_v1;

import jakarta.xml.bind.annotation.*;
import java.util.List;

/**
 * <h2>ProductList</h2>
 * <p>
 * Обёртка для JAXB-разбора XML-файла с множеством {@link Product}.
 * </p>
 *
 * @author LESHA
 */
@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductList {

    @XmlElement(name = "product")
    private List<Product> products;

    /**
     * @return Список всех продуктов.
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     * @param products Новый список продуктов.
     */
    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
