package org.example.lab6_ds_v1;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * <h2>Интерфейс RMIService</h2>
 * <p>
 * Предоставляет удалённый интерфейс для операций над продуктами.
 * </p>
 *
 * @author LESHA
 * @see java.rmi.Remote
 * @see java.rmi.RemoteException
 * @see org.example.lab6_ds_v1.ProductDAO
 */
public interface RMIService extends Remote {

    /**
     * Обновляет цену товара по заданному ID с использованием процента.
     * <p>
     * Новая цена рассчитывается по формуле: <br>
     * <code>newPrice = oldPrice * (1 + percentage / 100)</code>
     * </p>
     *
     * @param productId ID товара для обновления.
     * @param percentage Процент изменения цены (отрицательный — для снижения).
     * @throws RemoteException если произошла ошибка при удалённом вызове или обновлении.
     * @throws IllegalArgumentException если товар не найден или процент некорректен.
     *
     * @see java.lang.IllegalArgumentException
     * @see java.rmi.RemoteException
     * @see ProductDAO#updateProductPrice(int, double)
     */
    void updateProductPrice(int productId, double percentage) throws RemoteException;
}
