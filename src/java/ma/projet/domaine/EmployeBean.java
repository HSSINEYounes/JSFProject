/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.projet.domaine;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ListenerFor;
import javax.faces.event.PostAddToViewEvent;
import ma.projet.beans.Employe;
import ma.projet.beans.Service;
import ma.projet.service.EmployeService;
import ma.projet.service.ServiceService;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.apache.commons.io.FilenameUtils;
        

@ManagedBean(name = "employeBean")
public class EmployeBean{

    private Employe employe;
    private Service service;
    private List<Employe> employes;
    
    private EmployeService employeService;
    private ServiceService serviceService;
    private static ChartModel barModel;
    private String file;
    private String fileName;
    private UploadedFile uploadedFile;
    private UploadedFile Ufile;
    private Employe superviseur;

    
   public EmployeBean() {
        employe = new Employe();
        superviseur = new Employe();
        Ufile = null;
        employeService = new EmployeService();
        serviceService = new ServiceService();

    }
   public UploadedFile getUFile() {
        return Ufile;
    }

    public void setUFile(UploadedFile ufile) {
        this.Ufile = ufile;
    }
   
   public List<Employe> getEmployes() {
        if (employes == null) {
            employes = employeService.getAll();
        }
        return employes;
    }
   
    public Employe getEmploye() {
        return employe;
    }

    public void setEmployes(List<Employe> employes) {
        this.employes = employes;
    }
     
    public void setEmployeService(EmployeService employeService) {
        this.employeService = employeService;
    }
    
    public EmployeService getEmployeService() {
        return employeService;
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
    
    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
    
    public String onCreateAction() {
        if(superviseur.getId() != null){
            superviseur = employeService.getById(superviseur.getId());
            employe.setSuperviseur(superviseur);
        }
        if(Ufile != null){
            System.out.println("============================ TEST TEST TEST ============================");
            employe.setPhoto(Ufile.getFileName());
        }
        employeService.create(employe);
        employe = new Employe();
        superviseur = new Employe();
        return null;
    }
    
    public String onDeleteAction() {
        
        List<Employe> employeesToUpdate = employeService.getBySuperviseur(employe);
        for (Employe employee : employeesToUpdate) {
                employee.setSuperviseur(null);
                employeService.update(employee);
            }
        employe.setSuperviseur(null);
        employe.setService(null);
        employeService.delete(employeService.getById(employe.getId()));

        return null;
    }
    
    public void onEdit(RowEditEvent event) {
        superviseur = employeService.getById(superviseur.getId());
        employe = (Employe) event.getObject();
        employe.setSuperviseur(superviseur);
        service = serviceService.getById(this.employe.getService().getId());        
        employe.setService(service);
        employe.getService().setNom(service.getNom());
        employeService.update(employe);
        superviseur = new Employe();
    }
    
    public void onCancel(RowEditEvent event) {
    }
    
    public ChartModel getBarModel() {
        return barModel;
    }

    
    
    
    public List<Employe> serviceLoad() {
        for (Employe m : employeService.getAll()) {
            if (m.getService().equals(service)) {
                employes.add(m);
            }
        }
        return employes;
    }
    
    public void load() {
        System.out.println(service.getNom());
        service = serviceService.getById(service.getId());
        getEmployes();
    }
    
    public void createEmploye() {
            employeService.create(employe);
            employes = employeService.getAll();
            employe = new Employe();
    }
    
    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
    
    public Employe getSuperviseur() {
        return superviseur;
    }

    public void setSuperviseur(Employe superviseur) {
        this.superviseur = superviseur;
    }
    
    public void handleFileUpload(FileUploadEvent event) {
        Ufile = event.getFile();
        fileName = Ufile.getFileName();
        FacesMessage message = new FacesMessage("Successful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public List<Employe> getBySup(Employe supervisor) {
        List<Employe> employeesWithSupervisor = new ArrayList<>();

        for (Employe employee : employes) {
            if (Objects.equals(supervisor.getId(), employee.getSuperviseur().getId())) {
                employeesWithSupervisor.add(employee);
                System.out.println("employee => "+employee);
            }
        }
        return employeesWithSupervisor;
    }
    
    public void upload() {
        if (Ufile != null) {
            fileName = Ufile.getFileName();
            String uploadPath = "D:/jsf7/jsf7/web/resources/images/";
            try (InputStream input = Ufile.getInputstream();
                OutputStream output = new FileOutputStream(new File(uploadPath, fileName))) {
                IOUtils.copy(input, output);
                
            FacesMessage message = new FacesMessage("Successful", Ufile.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            } catch (IOException e) {
            e.printStackTrace();
        }
            
        }
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public UploadedFile getUfile() {
        return Ufile;
    }

    public void setUfile(UploadedFile Ufile) {
        this.Ufile = Ufile;
    }
    
    
    
}
