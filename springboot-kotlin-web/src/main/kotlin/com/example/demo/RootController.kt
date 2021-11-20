package com.example.demo

import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.server.ServletServerHttpRequest
import org.springframework.util.StreamUtils
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.context.support.WebApplicationContextUtils.getWebApplicationContext
import java.nio.charset.StandardCharsets
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/")
class RootController {

    @GetMapping(value = ["/**"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun index(request: HttpServletRequest): ResponseEntity<String> {
        val context = getWebApplicationContext(request.servletContext)
        var jsonText = getJson(context)
        jsonText?.let {
            logger.info("response from cache")
            return ResponseEntity.ok(it)
        }
        jsonText = Thread.currentThread().contextClassLoader.getResourceAsStream("example.json")
            .use { StreamUtils.copyToString(it, StandardCharsets.UTF_8) }
        logger.info("response from resource file")
        setJson(context, jsonText)
        return ResponseEntity.ok(jsonText)
    }

    @GetMapping(value = ["/json"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun json(request: HttpServletRequest): ResponseEntity<String> {
        val context = getWebApplicationContext(request.servletContext)
        ServletServerHttpRequest(request).body.use {
            val jsonText = StreamUtils.copyToString(it, StandardCharsets.UTF_8)
            setJson(context, jsonText)
            return ResponseEntity.ok(jsonText)
        }
    }


    private fun getJson(context: WebApplicationContext?): String? {
        return context?.servletContext?.getAttribute("json") as String?
    }

    private fun setJson(context: WebApplicationContext?, json: String?) {
        json?.let {
            context?.servletContext?.setAttribute("json", json)
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(RootController::class.java)
    }
}