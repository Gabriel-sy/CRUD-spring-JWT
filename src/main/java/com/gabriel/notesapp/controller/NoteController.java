package com.gabriel.notesapp.controller;

import com.gabriel.notesapp.domain.note.Note;
import com.gabriel.notesapp.domain.note.NoteDTO;
import com.gabriel.notesapp.repository.NoteRepository;
import com.gabriel.notesapp.service.NoteService;
import jakarta.validation.Valid;
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
        model.addAttribute("notes", noteRepository.findAll());
        return mv;
    }

    @GetMapping(path = "/create")
    public ModelAndView createNote(){
        return new ModelAndView("/create/index.html");
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ModelAndView> findById(@PathVariable Long id, Model model){
        try {
            ModelAndView mv = new ModelAndView("/findById/index.html");
            model.addAttribute("note", noteRepository.findById(id));
            return ResponseEntity.ok(mv);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Id nao encontrado");
        }
    }

}