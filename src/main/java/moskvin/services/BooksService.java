package moskvin.services;

import moskvin.models.Book;
import moskvin.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }


    public List<Book> findAll(){
        return booksRepository.findAll();
    }

    public List<Book> findBookByName(String title){
        return booksRepository.findBookByTitle(title);
    }

    public Book findOne(int id){
        Optional<Book> foundBook = booksRepository.findById(id);
        return foundBook.orElse(null);
    }

    @Transactional
    public void saveBook(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updBook){
        updBook.setId(id);
        booksRepository.save(updBook);
    }

    @Transactional
    public void delete(int id){
        booksRepository.deleteById(id);
    }

    public List<Book> findByTitleStartingWith(String startingWith){
        return booksRepository.findByTitleStartingWith(startingWith);
    }

    public List<Book> findByAuthorStartingWith(String startingWith){
        return booksRepository.findByAuthorStartingWith(startingWith);
    }
}
