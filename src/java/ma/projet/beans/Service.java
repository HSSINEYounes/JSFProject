/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.projet.beans;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author HP
 */

@Entity
public class Service {

    @Id
    @GeneratedValue
    private Long id;
    private String nom;
    @OneToMany(mappedBy = "service", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Employe> employes;

    public Service() {
    }
    
    public Service(String nom) {
        this.nom = nom;
    }
     

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Employe> getEmployes() {
        return employes;
    }

    public void setEmployes(List<Employe> employes) {
        this.employes = employes;
    }

    @Override
    public String toString() {
        return "Service{" + "id=" + id + ", nom=" + nom + ", employes=" + employes + '}';
    }
    
    
}
