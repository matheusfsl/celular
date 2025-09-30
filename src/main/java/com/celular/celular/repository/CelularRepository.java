package com.celular.celular.repository;

import com.celular.celular.model.CelularModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.UUID;

@Repository
public interface CelularRepository extends JpaRepository<CelularModel, UUID> {
    Optional<CelularModel> findByNameContainingIgnoreCase(String name);

    Set<CelularModel> findByIsActiveTrue();
}
