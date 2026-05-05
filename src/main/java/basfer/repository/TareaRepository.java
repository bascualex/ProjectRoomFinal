package basfer.repository;

import basfer.model.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
public interface TareaRepository extends JpaRepository<Tarea, Integer> { }
