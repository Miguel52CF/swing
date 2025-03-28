package com.swing_back_end.swing.base;

import java.util.List;
import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import lombok.Getter;

@Service
public abstract class BaseService<T, DTO> {

  private final BaseRepository<T, DTO> baseRepository;
  @Getter
  private final Class<T> modelType;

  public abstract List<DTO> findAllById(Long id);

  public abstract DTO findById(Long id);

  public BaseService(BaseRepository<T, DTO> baseRepository, Class<T> modelType) {
    this.baseRepository = baseRepository;
    this.modelType = modelType;
  }

  public T findOriginalElementsById(Long id) {
    Optional<T> optional = baseRepository.findByIdAndActiveIsTrue(id);
    return optional.orElseThrow(() -> new ObjectNotFoundException(modelType.getName(), id));
  }

  public T save(T object) {
    return baseRepository.save(object);
  }

  public void saveAll(List<T> objects) {
    baseRepository.saveAll(objects);
  }

  public DTO update(Long id, DTO updatedObject) {
    T object = findOriginalElementsById(id);

    BeanUtils.copyProperties(updatedObject, object, "id", "active");
    object = save(object);

    DTO newUpdatedObject = findById(id);
    return newUpdatedObject;

  };

  public String delete(Long id) {
    T object = findOriginalElementsById(id);
    if (object != null) {
      BeanUtils.copyProperties(false, object, "active");
      save(object);
      return "Elemento desactivado correctamente.";
    }
    return "Elemento no encontrado.";
  }

}
