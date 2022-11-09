package com.ehei.tpms.server.controller

import JsonListResponse
import com.ehei.tpms.server.datastore.TermRepository
import com.ehei.tpms.server.model.Term
import net.minidev.json.JSONObject
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.net.http.HttpResponse

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
}