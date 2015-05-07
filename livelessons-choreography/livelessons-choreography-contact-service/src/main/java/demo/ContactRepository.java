package demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {

	List<Contact> findByUserId(String userId);

	Contact findByUserIdAndId(String userId, Long id);

}
