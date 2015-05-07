package demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

	List<Bookmark> findByUserId(String userId);

	Bookmark findByUserIdAndId(String userId, Long id);

}
