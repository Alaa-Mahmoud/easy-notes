package com.example.easynotes.controllers;

import com.example.easynotes.models.Note;
import com.example.easynotes.repositories.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class NoteController {

  @Autowired
  NoteRepository noteRepository;

  @GetMapping("/notes")
  public List<Note> getAllNotes(){
      return noteRepository.findAll();
  }

  @PostMapping("/notes")
  public Note createNote(@Valid @RequestBody Note note){
      return noteRepository.save(note);
  }


  @GetMapping("/notes/{id}")
  public ResponseEntity<Note> getNoteById(@PathVariable Long id){
    Note note = noteRepository.findOne(id);
    if(note == null){
        return ResponseEntity.notFound().build();
    }

      return ResponseEntity.ok().body(note);
  }


  @PutMapping("/notes/{id}")
  public ResponseEntity<Note> updateNote(@PathVariable Long id ,
                                         @Valid @RequestBody Note noteDetails){
      Note note = noteRepository.findOne(id);

      if(note == null){
          return ResponseEntity.notFound().build();
      }

      note.setTitle(noteDetails.getTitle());
      note.setContent(noteDetails.getContent());

      Note updatedNote = noteRepository.save(note);

      return  ResponseEntity.ok().body(updatedNote);
  }


  @DeleteMapping("/notes/{id}")
  public ResponseEntity<Note> deleteNote(@PathVariable Long id){
      Note note = noteRepository.findOne(id);
      if(note == null){
          return  ResponseEntity.notFound().build();
      }
      noteRepository.delete(note);
      return ResponseEntity.ok().build();
  }

}
