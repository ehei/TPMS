package com.ehei.tpms.server.controller

import JsonListResponse
import com.ehei.tpms.server.datastore.TermRepository
import com.ehei.tpms.server.model.EditableTerm
import com.ehei.tpms.server.model.Term
import net.minidev.json.JSONObject
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.HttpClientErrorException
import java.net.http.HttpResponse
import java.util.*

@RestController
@RequestMapping("api/terms")
class TermController(
    val termRepository: TermRepository
) {

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    fun getAll(): JsonListResponse {

        val all = termRepository.findAll()

        return JsonListResponse(all, all.size)
    }

    @GetMapping("/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    fun get(@PathVariable id: Long): Optional<Term> {

        return termRepository.findById(id)
    }

    @PutMapping("/{id}", consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    fun update(@PathVariable id: Long, paramMap: Map<String, List<String>>): EditableTerm {

        val found = termRepository.findById(id)

        val term1 = found.get()

        val list = paramMap.getOrDefault("startDate", listOf())
        if (list.isNotEmpty()) {
            term1.startDate = list.first()
        }

        val list2 = paramMap.getOrDefault("endDate", listOf())
        if (list2.isNotEmpty()) {
            term1.endDate = list2.first()
        }

        val list3 = paramMap.getOrDefault("title", listOf())
        if (list3.isNotEmpty()) {
            term1.title = list3.first()
        }

        val saved = termRepository.saveAndFlush(term1)

        return EditableTerm(
            id = saved.id.toString(),
            title = saved.title,
            startDate = saved.startDate,
            endDate = saved.endDate)
    }
}