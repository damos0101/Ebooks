package moskvin.controllers;

import moskvin.models.Book;
import moskvin.services.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BooksService booksService;

    @Autowired
    public BooksController(BooksService booksService) {
        this.booksService = booksService;
    }

    @GetMapping("/search")
    public String searchForm() {
        return "books/search"; // Повертаємо сторінку для відображення форми пошуку
    }

    @PostMapping("/search")
    public String searchBooks(
            @RequestParam(value = "searchType", required = false) String searchType,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "author", required = false) String author,
            Model model) {

        Iterable<Book> books;

        if ("title".equals(searchType)) {
            // Здійснюємо пошук книг за назвою
            books = booksService.findByTitleStartingWith(title);
        } else if ("author".equals(searchType)) {
            // Здійснюємо пошук книг за автором
            books = booksService.findByAuthorStartingWith(author);
        } else {
            // Виконуємо інші дії, наприклад, виводимо всі книги
            books = booksService.findAll();
        }

        model.addAttribute("books", books);
        return "books/search"; // Повертаємо сторінку з результатами пошуку
    }


    @GetMapping()
    public String index(Model model){
        model.addAttribute("books", booksService.findAll());
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id,
                       Model model){
        model.addAttribute("book", booksService.findOne(id));
        return "books/book";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book){
        return "books/newBook";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "books/newBook";
        }
        booksService.saveBook(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("book", booksService.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult,
                         @PathVariable("id") int id){
        if(bindingResult.hasErrors()){
            return "books/edit";
        }
        booksService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        booksService.delete(id);
        return "redirect:/books";
    }


}
