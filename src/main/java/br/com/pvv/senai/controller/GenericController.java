package br.com.pvv.senai.controller;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.pvv.senai.controller.filter.IFilter;
import br.com.pvv.senai.entity.IEntity;
import br.com.pvv.senai.exceptions.DtoToEntityException;
import br.com.pvv.senai.model.dto.GenericDto;
import br.com.pvv.senai.service.GenericService;
import jakarta.validation.Valid;

@RestController
public abstract class GenericController<U extends GenericDto<T>, T extends IEntity> {

	public abstract GenericService<T> getService();

	public abstract IFilter<T> filterBuilder(Map<String, String> params) throws Exception;

	@GetMapping
	public ResponseEntity<Page<T>> list(@RequestParam Map<String, String> params) throws Exception {
		var filter = this.filterBuilder(params);
		var list = getService().paged(filter.example(), filter.getPagination());
		if (list.hasContent())
			return ResponseEntity.ok(list);
		return ResponseEntity.notFound().build();
	}

	@GetMapping("{id}")
	public ResponseEntity get(@PathVariable long id) {
		var retorno = getService().get(id);
		if (retorno == null)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(retorno);
	}

	@PutMapping("{id}")
	public ResponseEntity put(@PathVariable long id, @Valid @RequestBody U model) throws DtoToEntityException {
		if (getService().get(id) == null)
			ResponseEntity.notFound().build();
		getService().alter(model.getId(), model.makeEntity());
		return ResponseEntity.noContent().build();
	}

	@PostMapping
	public ResponseEntity post(@Valid @RequestBody U model) throws DtoToEntityException, Exception {
		var entity = getService().create(model.makeEntity());
		return ResponseEntity.status(201).body(entity);
	}

	@DeleteMapping("{id}")
	public ResponseEntity delete(@PathVariable long id) {
		if (getService().delete(id))
			return ResponseEntity.noContent().build();
		return ResponseEntity.notFound().build();
	}
}
