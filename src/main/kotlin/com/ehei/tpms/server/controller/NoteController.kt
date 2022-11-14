package com.ehei.tpms.server.controller

import com.ehei.tpms.server.datastore.NoteRepository
import com.ehei.tpms.server.model.Note
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.util.*

@Controller
@RequestMapping(path = ["api/notes"])
@CrossOrigin("*")
class NoteController(
    @Autowired val noteRepository: NoteRepository
) {
    @GetMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    fun getNote(@PathVariable(name = "id") id: Long): ResponseEntity<Note> {

        try {
            val findById: Optional<Note> = noteRepository.findById(id)

            if (findById.isEmpty) {
                return ResponseEntity.notFound().build()
            }

            return ResponseEntity.ok().body(findById.get())
        } catch (noSuchElement: NoSuchElementException) {
            return ResponseEntity.notFound().build()
        }
    }

    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    fun getTerms(): ResponseEntity<List<Note>> {

        val findAll = noteRepository.findAll()

        return ResponseEntity.ok()
            .header("X-Total-Count", "" + findAll.size)
            .header("Access-Control-Expose-Headers", "X-Total-Count")
            .body(findAll)
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    fun create(@RequestBody note: Note): ResponseEntity<Note> {

        val savedNote = noteRepository.save(note)

        return ResponseEntity.ok(savedNote)
    }

    @PutMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    fun update(@PathVariable(name = "id") id: Long, @RequestBody noteToUpdate: Note): ResponseEntity<Note> {

        val noteInRepo = noteRepository.findById(id).get()

        val updatedNote = Note(
            id = id,
            text = when (noteToUpdate.text) {
                null -> noteInRepo.text
                else -> noteToUpdate.text
            }
        )

        val savedNote = noteRepository.save(updatedNote)

        return ResponseEntity.ok(savedNote)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun delete(@PathVariable(name = "id") id: Long) {

        noteRepository.deleteById(id)
    }
}