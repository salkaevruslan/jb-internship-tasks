package org.example.templatemessenger.dto

data class AddTemplateRequest(val templateId: String, val template: String, val recipients: List<String>)