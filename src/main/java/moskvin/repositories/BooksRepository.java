package moskvin.repositories;

import moskvin.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    List<Book> findBookByTitle(String title);
    List<Book> findByTitleStartingWith(String startingWith);
    List<Book> findByAuthorStartingWith(String startingWith);
}
