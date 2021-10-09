package br.com.mv.breakfast.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(schema = "mv_sistemas")
public class Colaborador {	
	@Id
	private Long id;
	private String cpf;
	private String nome;
	@OneToMany
	private List<Item> listaItem;
}
