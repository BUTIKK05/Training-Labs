package org.example.lab6_ds_v1;

import jakarta.xml.bind.*;
import java.io.File;
import java.util.List;

/**
 * <h2>Класс ProductDAO</h2>
 * <p>
 * Содержит методы для управления данными о продуктах, хранящимися в XML-файле.
 * Реализация основана на технологии {@link jakarta.xml.bind.JAXB}.
 * </p>
 *
 * @author LESHA
 * @see Product
 * @see ProductList
 * @see jakarta.xml.bind.JAXBContext
 */
public class ProductDAO {

    private static final String FILE_PATH = "products.xml";

    /**
     * Обновляет цену продукта с указанным ID на заданный процент.
     *
     * @param productId ID продукта, который требуется обновить.
     * @param percentage Процент изменения цены (может быть отрицательным).
     * @throws Exception если произошла ошибка JAXB или данные некорректны.
     * <ul>
     *     <li>{@link IllegalArgumentException} — если продукт не найден, цена станет отрицательной или процент вне допустимого диапазона.</li>
     *     <li>{@link jakarta.xml.bind.JAXBException} — если произошла ошибка при разборе XML.</li>
     * </ul>
     *
     * @see java.util.List
     * @see jakarta.xml.bind.Marshaller
     * @see jakarta.xml.bind.Unmarshaller
     */
    public synchronized void updateProductPrice(int productId, double percentage) throws Exception {
        // реализация (см. код выше)
    }
}
