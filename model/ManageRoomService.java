/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uuu.resort.model;

import java.util.List;
import uuu.resort.domain.Room;
import uuu.resort.domain.ResortException;

/**
 *
 * @author Angie Lin
 */
public class ManageRoomService {
    private RDBRoomsDAO dao = new RDBRoomsDAO();

    public void insert(Room data) throws ResortException {
        dao.insert(data);
    }

    public void update(Room data) throws ResortException {
        dao.update(data);
    }

    public void delete(Room data) throws ResortException {
        dao.delete(data);
    }

    public Room get(String key) throws ResortException {
        return dao.get(key);
    }

    public List<Room> getAll() throws ResortException {
        return dao.getAll();
    }

    public List<Room> getByName(String data) throws ResortException {
        return dao.getByName(data);
    }

   
 //        
//        return dao.getAll();
//    }

//    public static void main(String[] args) {
//        RDBRoomsDAO.main(args);
//    }
    
   

//    public void addPrice(double unitPrice) {
//        unitPrice = unitPrice +10;
//    }
//
//    public void addPrice(Room p) {
//        p.setUnitPrice(p.getUnitPrice()+10);
//    }
}
