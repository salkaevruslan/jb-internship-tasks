package org.example.templatemessenger.dto

import kotlin.collections.Map

data class SendTemplateRequest(val templateId: String, val variables: List<Map<String, String>>)