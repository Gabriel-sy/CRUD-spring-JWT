package com.gabriel.notesapp.controller;

import com.gabriel.notesapp.domain.category.CategoryDTO;
import com.gabriel.notesapp.domain.note.Note;
import com.gabriel.notesapp.domain.note.NoteDTO;
import com.gabriel.notesapp.repository.CategoryRepository;
import com.gabriel.notesapp.repository.NoteRepository;
import com.gabriel.notesapp.service.NoteService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/home")
public class NoteController {
    private final NoteService noteService;
    private final NoteRepository noteRepository;
    private final CategoryRepository categoryRepository;

    public NoteController(NoteService noteService, NoteRepository noteRepository, CategoryRepository categoryRepository) {
        this.noteService = noteService;
        this.noteRepository = noteRepository;
        this.categoryRepository = categoryRepository;
    }

    //Criando nova nota, retornando 200 OK se tiver tudo certo
    @PostMapping(path = "/api/create")
    public ResponseEntity<Void> createNoteApi(@RequestBody @Valid NoteDTO noteDTO){
            noteService.createNote(noteDTO);
            return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/api/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        noteService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "/api/edit")
    public ResponseEntity<String> replace(@RequestBody Note note){
        noteService.editNote(note);
        return ResponseEntity.ok().build();

    }

    @PostMapping(path = "/api/create/category")
    public ResponseEntity<Void> createCategory(@RequestBody CategoryDTO category){
        noteService.createCategory(category);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/list")
    public ModelAndView findAll(Model model){
        ModelAndView mv = new ModelAndView("home/index.html");
        model.addAttribute("notes", noteRepository.findAll(Sort.by(Sort.Direction.ASC, "id")));
        return mv;
    }

    @GetMapping(path = "/create")
    public ModelAndView createNote(Model model){
        ModelAndView mv = new ModelAndView("create/index.html");
        model.addAttribute("category", categoryRepository.findAll());
        return mv;
    }

    @GetMapping(path = "/{id}")
    public ModelAndView findById(@PathVariable Long id, Model model){
            ModelAndView mv = new ModelAndView("findbyid/index.html");
            model.addAttribute("notes", noteService.findById(id));
        return mv;
    }

    @GetMapping(path = "/edit/{id}")
    public ModelAndView replacePage(@PathVariable Long id, Model model){
        ModelAndView mv = new ModelAndView("edit/index.html");
        model.addAttribute("note", noteService.findById(id));
        model.addAttribute("category", categoryRepository.findAll());
        return mv;
    }


    @GetMapping(path = "/create/category")
    public ModelAndView createCategoryPage(){
        return new ModelAndView("category/index.html");
    }

}
