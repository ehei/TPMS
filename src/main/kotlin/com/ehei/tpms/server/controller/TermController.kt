package com.ehei.tpms.server.controller

import com.ehei.tpms.server.datastore.TermRepository
import com.ehei.tpms.server.model.Term
import com.ehei.tpms.server.model.UNKNOWN_USER
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.util.*

/**
 * Term controller
 *
 * @property repository
 */
@Controller
@RequestMapping(path = ["api/terms"])
@CrossOrigin("*")
class TermController(
    @Autowired val repository: TermRepository
) {
    /**
     * Get
     *
     * @param token
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ResponseBody
    @ResponseStatus(OK)
    fun get(@RequestHeader("Authorization") token: String, @PathVariable(name = "id") id: Long): ResponseEntity<Term> {

        val findById = repository.findById(id)

        if (findById.isEmpty || ! isAuthorized(token, findById.get().userId)) {
            return ResponseEntity.notFound().build()
        }

        return ResponseEntity.ok().body(findById.get())
    }

    /**
     * Get all
     *
     * @param token
     * @return
     */
    @GetMapping
    @ResponseBody
    @ResponseStatus(OK)
    fun getAll(@RequestHeader("Authorization") token: String): ResponseEntity<List<Term>> {

        var found = listOf<Term>()
        val user = getValidToken(token)
        if (user != UNKNOWN_USER)
            found = repository.findAll().filter { record -> record.userId == user.id }

        return createListResponse(found)
    }

    /**
     * Create
     *
     * @param token
     * @param term
     * @return
     */
    @PostMapping
    @ResponseBody
    @ResponseStatus(OK)
    fun create(@RequestHeader("Authorization") token: String, @RequestBody term: Term): ResponseEntity<Term> {

        val user = getValidToken(token)
        if (user == UNKNOWN_USER)
            return ResponseEntity.badRequest().build()

        term.userId = user.id
        val savedTerm = repository.save(term)

        return ResponseEntity.ok(savedTerm)
    }

    /**
     * Update
     *
     * @param token
     * @param id
     * @param termToUpdate
     * @return
     */
    @PutMapping("/{id}")
    @ResponseBody
    @ResponseStatus(OK)
    fun update(@RequestHeader("Authorization") token: String, @PathVariable(name = "id") id: Long, @RequestBody termToUpdate: Term): ResponseEntity<Term> {

        val found = repository.findById(id)

        if (found.isEmpty || ! isAuthorized(token, found.get().userId))
            return ResponseEntity.notFound().build()

        val updated = Term(
            id = id,
            title = termToUpdate.title,
            startDate = termToUpdate.startDate,
            endDate = termToUpdate.endDate,
            course_ids = termToUpdate.course_ids,
            userId = termToUpdate.id
        )

        val savedTerm = repository.save(updated)

        return ResponseEntity.ok(savedTerm)
    }

    /**
     * Delete
     *
     * @param token
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(OK)
    fun delete(@RequestHeader("Authorization") token: String?, @PathVariable(name = "id") id: Long):
        ResponseEntity<Long> {

        val found = repository.findById(id)

        if (! isAuthorized(token, found.get().userId))
            return ResponseEntity.notFound().build()

        repository.deleteById(id)
        return ResponseEntity.ok(id)
    }
}
