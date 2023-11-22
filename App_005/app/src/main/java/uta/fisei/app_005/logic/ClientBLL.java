package uta.fisei.app_005.logic;

import android.content.ContentValues;
import android.content.Context;

import uta.fisei.app_005.entities.Client;
import uta.fisei.app_005.dal.ClientDAL;

public class ClientBLL {

    Context context;
    ClientDAL clientDAL;

    public ClientBLL(Context context) {
        this.context = context;
        ClientDAL clientDAL = new ClientDAL(context);
    }

    public long insert(Client client){

        long count = 0;
        try {
            //ClientDAL clientDAL = new ClientDAL(context);

            //Reglas de negocio o VALIDACIONES osea como se inserta, no quiero nulos asi
            if ((client.getBalance() >= 0) || (client.getBalance()<=100000)){
                count = clientDAL.insert(client);
            }else {
                throw new Exception("El Balance debe estar entre 1 y 100000");
            }

        }catch (Exception e){
            //throw e;
        }finally {

        }
        return count;
    }
}
