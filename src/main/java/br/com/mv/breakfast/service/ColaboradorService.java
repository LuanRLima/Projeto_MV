package br.com.mv.breakfast.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.mv.breakfast.domain.Colaborador;
import br.com.mv.breakfast.repository.ColaboradorRepository;

@Service
public class ColaboradorService {
	@Autowired
	private ColaboradorRepository repository;
	@Autowired
	private ItemService itemService;

	public List<Colaborador> findAll() {
		return repository.findAll();
	}

	public Optional<Colaborador> findById(Long id) {
		return this.repository.findById(id);
	}

	public Colaborador save(Colaborador colaborador) {
		return repository.save(colaborador);
	}

	@Transactional
	public Colaborador update(Long id, Colaborador colaborador) {
		Optional<Colaborador> record = this.repository.findById(id);

		if (record.isEmpty())
			return null;

		var colab = record.get();
		colab.setCpf(colaborador.getCpf());
		colab.setNome(colaborador.getNome());

		if (colab.getListaItem() != null) {
			var listaTemp = List.copyOf(colab.getListaItem());

			listaTemp.stream().forEach(salvo -> {
				if (colaborador.getListaItem() != null) {
					boolean isPresent = colaborador.getListaItem().stream()
							.filter(novo -> novo.getNome().equals(salvo.getNome())).findFirst().isPresent();
					if (!isPresent)
						colab.getListaItem().remove(salvo);
				} else {
					colab.getListaItem().clear();
				}
			});
		}

		if (colab.getListaItem() != null) {
			if (colaborador.getListaItem() != null) {
				colaborador.getListaItem().stream().forEach(novo -> {
					boolean isPresent = colab.getListaItem().stream()
							.filter(salvo -> salvo.getNome().equals(novo.getNome())).findFirst().isPresent();
					if (!isPresent)
						colab.getListaItem().add(novo);
				});
			}
		}

		return this.repository.save(colab);
	}

	public String deleteById(Long id) throws Exception {
		this.findById(id).orElseThrow(() -> new NullPointerException("Contato_inexistente"));
		this.repository.deleteById(id);
		Optional<Colaborador> contact = this.findById(id);
		if (contact.isPresent()) {
			throw new Exception("Não foi possível deletar contato");
		}
		return "Contato deletado";
	}

}
