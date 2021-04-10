package org.example.templatemessenger.service

import org.example.templatemessenger.dto.AddTemplateRequest
import org.example.templatemessenger.dto.SendTemplateRequest
import org.example.templatemessenger.model.Recipient
import org.example.templatemessenger.model.Template
import org.example.templatemessenger.repository.RecipientRepository
import org.example.templatemessenger.repository.TemplateRepository
import org.example.templatemessenger.util.MessageSender
import org.springframework.stereotype.Service



@Service
class TemplateService(
    private val templateRepository: TemplateRepository,
    private val recipientRepository: RecipientRepository,
    private val messageSender: MessageSender
) {
    fun addTemplate(request: AddTemplateRequest) {
        val templateExists = templateRepository.findByName(request.templateId).isPresent
        if (templateExists) {
            throw IllegalStateException("Template already exist")
        } else {
            val template = Template(
                name = request.templateId,
                template = request.template
            )
            val recipientsList = request.recipients.map { Recipient(name = it) }
            recipientsList.map {
                if (recipientRepository.findByName(it.name).isEmpty) {
                    recipientRepository.save(it)
                }
            }
            template.recipients.addAll(recipientsList)
            templateRepository.save(template)
        }
    }

    fun sendTemplate(request: SendTemplateRequest): Any {
        var template = templateRepository.findByName(request.templateId)
            .orElseThrow { IllegalStateException("Template not found") }.template
        for (variable in request.variables) {
            if (variable.entries.size != 1) {
                throw IllegalStateException("Wrong format of request")
            }
            val templateValue = "$${variable.keys.iterator().next()}$"
            val value = variable.values.iterator().next()
            if (template.contains(templateValue)) {
                template = template.replace(templateValue, value)
            } else {
                throw IllegalStateException("Variable not found in template")
            }
        }
        if (template.contains("$.*$")) {
            throw IllegalStateException("Not all template variables replaced")
        }
        val endpointList = templateRepository.findByName(request.templateId)
            .orElseThrow { IllegalStateException("Template not found") }.recipients
        //here you can choose how to send messages
        return messageSender.createResponseList(endpointList.toList(), template)
        //return messageSender.sendDirectly(endpointList.toList(), template)
    }
}