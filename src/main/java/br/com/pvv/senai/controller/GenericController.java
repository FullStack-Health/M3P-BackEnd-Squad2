package br.com.pvv.senai.controller;

import br.com.pvv.senai.controller.filter.IFilter;
import br.com.pvv.senai.entity.IEntity;
import br.com.pvv.senai.exceptions.DtoToEntityException;
import br.com.pvv.senai.model.dto.GenericDto;
import br.com.pvv.senai.service.GenericService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public abstract class GenericController<U extends GenericDto<T>, T extends IEntity> {

	public abstract GenericService<T> getService();

	public abstract IFilter<T> filterBuilder(Map<String, String> params) throws Exception;

	@GetMapping
	public ResponseEntity<Page<T>> list(@RequestParam Map<String, String> params) throws Exception {
		if (params.size() != 0) {
			var filter = this.filterBuilder(params);
			var list = getService().paged(filter.example(), filter.getPagination());
			if (list.hasContent())
				return ResponseEntity.ok(list);
		} else {
			var list = getService().all();
			if (list.size() > 0) {
				PageImpl<T> p = new PageImpl<T>(list);
				return ResponseEntity.ok(p);
			}
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("{id}")
	public ResponseEntity get(@PathVariable Long id) {
		var retorno = getService().get(id);
		if (retorno == null)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(retorno);
	}

	@PutMapping("{id}")
	public ResponseEntity put(@PathVariable Long id, @Valid @RequestBody U model) throws DtoToEntityException {
		if (getService().get(id) == null)
			ResponseEntity.notFound().build();
		getService().alter(id, model.makeEntity());
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
