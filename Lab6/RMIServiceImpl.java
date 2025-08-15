package org.example.lab6_ds_v1;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
/**
 * <h2>Класс RMIServiceImpl</h2>
 * @author LESHA
 */
public class RMIServiceImpl extends UnicastRemoteObject implements RMIService {
    private ProductDAO dao;

    public RMIServiceImpl() throws RemoteException {
        dao = new ProductDAO();
    }

    @Override
    public void updateProductPrice(int productId, double percentage) throws RemoteException {
        try {
            dao.updateProductPrice(productId, percentage);
        } catch (IllegalArgumentException e) {
            throw new RemoteException("Ошибка: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RemoteException("Системная ошибка", e);
        }
    }
}
