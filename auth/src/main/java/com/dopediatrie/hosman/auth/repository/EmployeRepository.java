package com.dopediatrie.hosman.auth.repository;

import com.dopediatrie.hosman.auth.entity.Employe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployeRepository extends JpaRepository<Employe,Long> {
    boolean existsByMatricule(String matricule);
    Optional<Employe> findByMatriculeEquals(String matricule);

    @Query("select e from Employe e where e.is_employe = true and e.is_medecin = true")
    List<Employe> findByEmployeAndMedecinTrue();

    @Query("select e from Employe e where e.is_employe = false and e.is_medecin = true")
    List<Employe> findByEmployeFalseAndMedecinTrue();

    @Query("select e from Employe e JOIN User u on e.user.id = u.id and u.id = :userId")
    Optional<Employe> findByUserId(@Param("userId") long userId);
}
