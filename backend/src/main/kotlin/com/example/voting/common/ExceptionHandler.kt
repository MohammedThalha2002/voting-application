package com.example.voting.common

import com.example.voting.dao.HttpResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandler {
    @ExceptionHandler
    fun handleCustomException(ex : CustomException): ResponseEntity<HttpResponse> {
        val httpResponse = HttpResponse(HttpStatus.BAD_REQUEST, ex.message!!)
        return ResponseEntity.status(500).body(httpResponse)
    }
}