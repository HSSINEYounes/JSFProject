
import java.util.Date;
import ma.projet.beans.Employe;
import ma.projet.beans.Machine;
import ma.projet.beans.Salle;
import ma.projet.beans.Service;
import ma.projet.service.EmployeService;
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
public class Test1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MachineService ms = new MachineService();
        SalleService ss = new SalleService();
        ServiceService servS = new ServiceService();
        EmployeService EmpS = new EmployeService();
        //Création des machines
        /*ms.create(new Machine("HP", "HZ23", 5000, new Date(), ss.getById(1)));
        ms.create(new Machine("HP", "HZ77", 4500, new Date(), ss.getById(1)));
        ms.create(new Machine("TOSHIBA", "AZ34", 6000, new Date(), ss.getById(2)));*/
        
        
        
        
        EmpS.create(new Employe("HSSINE", "Younes", new Date(), "PHOTO", servS.getById(1), null));
        
        Employe e1 = EmpS.getById(5);
        
        System.out.println("===========> "+e1);
        EmpS.delete(e1);
        
        //Afficher les machines par salle
        
        for(Employe e : EmpS.getAll()){
            System.out.println("Employe : "+ e +" : ");
        }
    }
}
