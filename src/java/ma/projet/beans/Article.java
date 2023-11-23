/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.projet.beans;

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author HP
 */
public class Article {
     @Id
    @GeneratedValue
    private long id;
    private String nom;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateProduction;

    public Article() {
    }
    
    public Article(long id, String nom, Date dateProduction) {
        this.id = id;
        this.nom = nom;
        this.dateProduction = dateProduction;
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDateProduction() {
        return dateProduction;
    }

    public void setDateProduction(Date dateProduction) {
        this.dateProduction = dateProduction;
    }
    
    
    
}
