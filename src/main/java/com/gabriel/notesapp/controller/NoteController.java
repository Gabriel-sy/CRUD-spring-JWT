package com.gabriel.notesapp.controller;

import com.gabriel.notesapp.domain.note.Note;
import com.gabriel.notesapp.domain.note.NoteDTO;
import com.gabriel.notesapp.repository.NoteRepository;
import com.gabriel.notesapp.service.NoteService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("home")
public class NoteController {
    private final NoteService noteService;
    private final NoteRepository noteRepository;

    public NoteController(NoteService noteService, NoteRepository noteRepository) {
        this.noteService = noteService;
        this.noteRepository = noteRepository;
    }

    //Criando nova nota, retornando 200 OK se tiver tudo certo
    @PostMapping(path = "/api/create")
    public ResponseEntity<Void> createNoteApi(@RequestBody @Valid NoteDTO noteDTO){
        //Retorna IllegalArgumentException se o usuário já existir
        try {
            //Transformando o DTO em note aqui porque ajuda nos testes.
            Note newNote = new Note(noteDTO);
            noteService.createNote(newNote);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(path = "/list")
    public ModelAndView findAll(Model model){
        ModelAndView mv = new ModelAndView("/home/index.html");
        model.addAttribute("notes", noteRepository.findAll(Sort.by(Sort.Direction.ASC, "id")));
        return mv;
    }

    @GetMapping(path = "/create")
    public ModelAndView createNote(){
        return new ModelAndView("/create/index.html");
    }

    @GetMapping(path = "/{id}")
    public ModelAndView findById(@PathVariable Long id, Model model){
            ModelAndView mv = new ModelAndView("/findbyid/index.html");
            model.addAttribute("notes", noteService.findById(id));
        return mv;
    }

    @PutMapping(path = "/api/edit")
    public ResponseEntity<Void> replace(@RequestBody Note note){
        try {
            noteService.createNote(note);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(path = "/edit/{id}")
    public ModelAndView replacePage(@PathVariable Long id, Model model){
        ModelAndView mv = new ModelAndView("/edit/index.html");
        model.addAttribute("note", noteService.findById(id));
        return mv;
    }

    @PostMapping(path = "/api/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        try {
            noteService.delete(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
