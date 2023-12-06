package uta.fisei.app_005.logic;

import android.content.ContentValues;
import android.content.Context;

import uta.fisei.app_005.entities.Client;
import uta.fisei.app_005.dal.ClientDAL;

public class ClientBLL {

    private Context context;
    private ClientDAL clientDAL;

    public ClientBLL(Context context) {
        this.context = context;
        this.clientDAL = new ClientDAL(context);
    }

    public long insert(Client client) {
        long count = 0;
        try {
            // Reglas de negocio o VALIDACIONES: el balance debe estar entre 0 y 100000
            if (client.getBalance() >= 0 && client.getBalance() <= 100000) {
                count = clientDAL.insert(client);
            } else {
                // Lanza una excepción si el balance no está en el rango permitido
                throw new Exception("El Balance debe estar entre 0 y 100000");
            }
        } catch (Exception e) {
            // Maneja la excepción (registrar, notificar, etc.)
            e.printStackTrace();
        } finally {
            // Puedes agregar lógica aquí si es necesario
        }
        return count;
    }

    public long insertWithValidation(Client client, double balance) {
        long count = 0;
        try {
            // Reglas de negocio o VALIDACIONES: el balance debe estar entre 0 y 100000
            if (balance >= 1 && balance <= 100000) {
                client.setBalance(balance);
                count = clientDAL.insert(client);
            } else {
                // Lanza una excepción si el balance no está en el rango permitido
                throw new Exception("El Balance debe estar entre 0 y 100000");
            }
        } catch (Exception e) {
            // Maneja la excepción (registrar, notificar, etc.)
            e.printStackTrace();
        }
        return count;
    }

}

