package com.ensimag.ridetrack.daos;

import com.ensimag.ridetrack.model.TrackUserModel;
import javassist.bytecode.stackmap.BasicBlock;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;
import java.util.Objects;
import java.util.function.Consumer;
/*  user groupe : admin n'est pas utilisateur
    espace vs vehicule
    les espaces on a deux types d utilisateur, utilisateurs et admins
    car lora wan force à associer un capteur à un application ID, ce qui concerne chaque capteur avec sont application getIDil y aura un groupe d'
    espace aura donc un contrat
    les machines ont des capteurs et des coordonnées. Gestion des droits. application ID qui est commmun a tout les capteurs, le TTN propose des API admin qui ajoute des capteurs.
    donc espace peut contenir plusieurs applications
    Capteur carte (sensor) boolean ne va pas envouer les paquet par le temps mais demander au capteur d'envoyer un paquer à ,
    la machine (vehicule)
*/

public class JpaUserDao implements TrackDao<TrackUserModel> {
    private final EntityManager entityManager;

    public JpaUserDao(EntityManager entityManager){
        this.entityManager = entityManager;
    }


    @Override
    public Optional<TrackUserModel> get(long id) {
        return Optional.ofNullable(entityManager.find(TrackUserModel.class, id));
    }

    @Override
    public List<TrackUserModel> getAll() {
        Query query = entityManager.createQuery("") ;
     //   Query query = entityManager.createQuery("SELECT nom FROM e");
        return query.getResultList();
    }

    @Override
    public void save(TrackUserModel u) {
        executeInsideTransaction(entityManager -> entityManager.persist(u));
    }

    @Override
    public void update(TrackUserModel u, String[] params) {
        u.setNom(Objects.requireNonNull(params[0], "Nom ne peut pas etre null"));
        u.setPrenom(Objects.requireNonNull(params[1], "Prenom ne peut pas etre null"));
        u.setPseudo(Objects.requireNonNull(params[2], "Pseudo ne peut pas etre null"));
        u.setEmail(Objects.requireNonNull(params[3], "Email ne peut pas etre null"));
        executeInsideTransaction(entityManager -> entityManager.merge(u));
    }

    @Override
    public void delete(TrackUserModel utilisateur) {

    }

    private void executeInsideTransaction(Consumer<EntityManager> action){
        final EntityTransaction t = entityManager.getTransaction();
        try {
            t.begin();
            action.accept(entityManager);
            t.commit();
        }catch (RuntimeException e){
            t.rollback();
            throw e;
        }
    }

/*
mangoDB : impossible de reverse, il devait generer les jzon qui rentre dans la BDD.
Donc il faut : genere des données traitée

 */
}
