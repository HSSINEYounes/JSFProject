/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.projet.domaine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import ma.projet.beans.Employe;
import ma.projet.beans.Service;
import ma.projet.service.EmployeService;
import ma.projet.service.MachineService;
import ma.projet.service.ServiceService;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartModel;
import org.primefaces.model.chart.ChartSeries;

@ManagedBean(name = "serviceBean")
public class ServiceBean{

    private Employe employe;
    private Service service;
    private ServiceService serviceService;
    private List<Service> services;
    private List<Employe> employes;
    private EmployeService employeService;
    private static ChartModel barModel;
    private Service selectedService;
    private List<Employe> collaborators;
    private List<Employe> subordonnes;

    public Service getSelectedService() {
        return selectedService;
    }

    public void setSelectedService(Service selectedService) {
        this.selectedService = selectedService;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public ServiceService getServiceService() {
        return serviceService;
    }

    public void setServiceService(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    public EmployeService getEmployeService() {
        return employeService;
    }

    public void setEmployeService(EmployeService employeService) {
        this.employeService = employeService;
    }

    public List<Employe> getSubordonnes() {
        return subordonnes;
    }

    public void setSubordonnes(List<Employe> subordonnes) {
        this.subordonnes = subordonnes;
    }
    
    
    
    public ServiceBean() {
        service = new Service();
        serviceService = new ServiceService();
        employe = new Employe();
        employeService = new EmployeService();
    }
    
    public List<Employe> getEmployes() {
        if (employes == null) {
            employes = employeService.getAll();
        }
        return employes;
    }
    
    public void setEmployes(List<Employe> employes) {
        this.employes = employes;
    }
    

    public List<Service> getservices() {
        if (services == null) {
            services = serviceService.getAll();
            
        }
        return services;
    }
    
    public void setServices(List<Service> services) {
        this.services = services;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public String onCreateAction() {
        serviceService.create(service);
        service  = new Service();
        return null;
    }

    public void onDeleteAction() {
        service.setEmployes(null);
        serviceService.delete(service);
    }
    
    public void onEdit(RowEditEvent event) {
        service = (Service) event.getObject();
        service.setEmployes(null);
        serviceService.update(service);
    }

    public void load() {
        System.out.println(service.getNom());
        service = serviceService.getById(service.getId());
        getEmployes();
    }
    
    public void onCancel(RowEditEvent event) {
    }

    public void onEditm(RowEditEvent event) {
        employe = (Employe) event.getObject();
        service = serviceService.getById(this.employe.getService().getId());
        employe.setService(service);
        employe.getService().setNom(service.getNom());
        employeService.update(employe);
    }

    public String onDeleteActionm() {
        employeService.delete(employeService.getById(employe.getId()));
        return null;
    }
    
    public List<Employe> serviceLoad() {
        for (Employe m : employeService.getAll()) {
            if (m.getService().equals(service)) {
                employes.add(m);
            }
        }
        return employes;

    }
    
     public void onCancelm(RowEditEvent event) {
    }
     
     public ChartModel getBarModel() {
        return barModel;
    }

    public ChartModel initBarModel() {
        CartesianChartModel model = new CartesianChartModel();
        ChartSeries services = new ChartSeries();
        services.setLabel("Services");
        model.setAnimate(true);
        for (Service s : serviceService.getAll()) {
            services.set(s.getNom(), s.getEmployes().size());
        }
        model.addSeries(services);

        return model;
    }

    public List<Employe> getCollaborators() {
        return collaborators;
    }

    public void setCollaborators(List<Employe> collaborators) {
        this.collaborators = collaborators;
    }
    
    

    public List<Employe> getCollaborateursDuService() {
        if (selectedService != null) {
            collaborators = serviceService.getCollaborateursDuService(selectedService.getId());
        }
        return collaborators;
    }
    
    public BarChartModel getEmployeeCountModel() {
        BarChartModel model = new BarChartModel();

        ChartSeries employeesSeries = new ChartSeries();
        employeesSeries.setLabel("Nombre d'employes par service");

        List<Object[]> employeeCountsByService = employeService.nbemployeByService();
        for (Object[] entry : employeeCountsByService) {
            employeesSeries.set((String) entry[1], (Number) entry[0]);
        }

        model.addSeries(employeesSeries);

        return model;
    }
    public void showCollaborateurs(Service service) {
        System.out.println("SERVICE : " + service);
        selectedService = service;
    }
    
    public Employe getChefService(Service service) {
    List<Employe> employees = service.getEmployes();

    for (Employe employee : employees) {
        if (employee.getSuperviseur() == null) {
            return employee;
        }
    }
    return null;
}

    public List<Employe> getSubordinates() {
        if (selectedService != null) {
            Employe chefService = getChefService(selectedService);
            if (chefService != null) {
                return getSubordinates(chefService);
            }
        }
        return Collections.emptyList();
    }

    private List<Employe> getSubordinates(Employe supervisor) {
        List<Employe> subordinates = new ArrayList<>();

        for (Employe employee : employes) {
            if (supervisor.getId() == employee.getSuperviseur().getId() || supervisor.getId() != employee.getId()) {
                subordinates.add(employee);
            }
        }
        return subordinates;
    }

    public List<Employe> getSubordinatesOfChefService() {
        if (selectedService != null) {
            Employe chefService = getChefService(selectedService);
            if (chefService != null) {
                subordonnes =  getSubordinates(chefService);
                System.out.println("Subordinates => " + subordonnes);
                return subordonnes;
            }
        }
        return Collections.emptyList();
    }
    
    public List<Employe> getBySuperviseur(Employe superviseur) {
        List<Employe> employes = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        // Use HQL (Hibernate Query Language) to get employees with the specified supervisor
        employes = session.createQuery("FROM Employe e WHERE e.superviseur = :superviseur")
                .setParameter("superviseur", superviseur)
                .list();

        session.getTransaction().commit();
        return employes;
    }
    
}
