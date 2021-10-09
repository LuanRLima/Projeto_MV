package br.com.mv.breakfast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.mv.breakfast.domain.Colaborador;

@Repository
public interface ColaboradorRepository extends JpaRepository<Colaborador, Long> {}
