package org.example.templatemessenger.controller

import org.example.templatemessenger.dto.AddTemplateRequest
import org.example.templatemessenger.dto.SendTemplateRequest
import org.example.templatemessenger.service.TemplateService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.lang.IllegalStateException

@RestController
class TemplateController(private val templateService: TemplateService) {

    @PostMapping("/addtemplate")
    fun addTemplate(@RequestBody request: AddTemplateRequest): String {
        return try {
            templateService.addTemplate(request)
            "Template successfully added"
        } catch (e: IllegalStateException) {
            "Failed to add template:${e.message}"
        }
    }

    @PostMapping("/sendtemplate")
    fun sendTemplate(@RequestBody request: SendTemplateRequest): Any {
        return templateService.sendTemplate(request)
    }
}