/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ma.projet.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import ma.projet.beans.Employe;
import ma.projet.beans.Service;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;


public class EmployeService implements IDao<Employe>{

    
    @Override
    public boolean create(Employe o) {
        Session session  = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(o);
        session.getTransaction().commit();
        return true;
    }
    
    @Override
     public boolean update(Employe o) {
        Session session  = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(o);
        session.getTransaction().commit();
        return true;
    }
     
    @Override
    public boolean delete(Employe o) {
        Session session  = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(o);
        session.flush();
        session.getTransaction().commit();
        return true;
    }
    
    @Override
    public Employe getById(long id) {
        Employe employe  = null;
        Session session  = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        employe  = (Employe) session.get(Employe.class, id);
        session.getTransaction().commit();
        return employe;
    }
    
    @Override
    public List<Employe> getAll() {
        List <Employe> employes = null;
      
        Session session  = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        employes  = session.createQuery("from Employe").list();
        session.getTransaction().commit();
        return employes;
    }
   
    public List<Object[]> nbemployeByService() {
        List<Object[]> employeeCounts = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        employeeCounts = session.createQuery("select count(e.id), e.service.nom from Employe e group by e.service.nom").list();

        session.getTransaction().commit();
        return employeeCounts;
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
