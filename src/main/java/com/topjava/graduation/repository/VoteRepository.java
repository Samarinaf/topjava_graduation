package com.topjava.graduation.repository;

import com.topjava.graduation.model.Vote;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Vote v WHERE v.id=:id")
    int delete(@Param("id") int id);

    @EntityGraph(attributePaths = {"user", "restaurant", "user.roles"})
    Optional<Vote> findById(int id);

    @EntityGraph(attributePaths = {"user", "restaurant", "user.roles"})
    List<Vote> findAll();

    @Query("SELECT v FROM Vote v WHERE v.date=:date")
    @EntityGraph(attributePaths = {"user", "restaurant", "user.roles"})
    List<Vote> findAllByDate(@Param("date") LocalDate date);
}
