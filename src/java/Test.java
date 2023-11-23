
import ma.projet.beans.Salle;
import ma.projet.beans.Service;
import ma.projet.service.MachineService;
import ma.projet.service.SalleService;
import ma.projet.service.ServiceService;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author LACHGAR
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SalleService ss = new SalleService();
        ServiceService servS = new ServiceService();
        //création des Salles
        ss.create(new Salle("I", "Informatique"));
        ss.create(new Salle("A", "Arabe"));
        ss.create(new Salle("C", "Comptailité"));
        
        servS.create(new Service("NOM_1"));
        
         for (Service s : servS.getAll()) {
            System.out.println("" + s.getNom());
        }
        
    }
}
