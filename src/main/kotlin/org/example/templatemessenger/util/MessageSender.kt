package org.example.templatemessenger.util

import org.example.templatemessenger.model.Recipient
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.UnknownHostException
import java.util.*
import java.util.stream.Collectors


@Service
@Async
class MessageSender {
    fun sendDirectly(endpointList: List<String>, message: String): String {
        var result: String = ""
        for (endpoint in endpointList) {
            val url = URL(endpoint)
            with(url.openConnection() as HttpURLConnection) {
                requestMethod = "POST"
                doOutput = true
                setRequestProperty("Content-Type", "application/json; utf-8")
                val jsonString = "{\"message\": \"${message}\"}"
                try {
                    outputStream.use { os ->
                        val input = jsonString.toByteArray()
                        os.write(input, 0, input.size)
                    }
                    BufferedReader(
                        InputStreamReader(inputStream, "utf-8")
                    ).use { br ->
                        val response = StringBuilder()
                        var responseLine: String? = null
                        while (br.readLine().also { responseLine = it } != null) {
                            response.append(responseLine!!.trim { it <= ' ' })
                        }
                        println(response.toString())
                    }
                } catch (e: UnknownHostException) {
                    //remove if you want to continue sending messages to other endpoints after failure
                    result += "Cannot connect to $endpoint"
                }
            }
        }
        return if (result == "") {
            "Messages sent successfully $message"
        } else {
            result
        }
    }

    fun createResponseList(endpointList: List<Recipient>, message: String): List<ResponseEntity<String>> {
        val restTemplate = RestTemplate()
        val headers = HttpHeaders()
        val content: MultiValueMap<String, Any> = LinkedMultiValueMap()
        content.add("message", message)
        headers.accept = Collections.singletonList(MediaType.APPLICATION_JSON)
        headers.contentType = MediaType.APPLICATION_JSON
        return endpointList.stream().map {
            restTemplate.postForEntity(
                it.name,
                content,
                String().javaClass
            )
        }.collect(
            Collectors.toList()
        )
    }
}