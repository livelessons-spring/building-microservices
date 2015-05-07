package demo;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "people")
interface PersonRepository extends JpaRepository<Person, Long> {

	Collection<Person> findByEmail(@Param("email") String email);

}
