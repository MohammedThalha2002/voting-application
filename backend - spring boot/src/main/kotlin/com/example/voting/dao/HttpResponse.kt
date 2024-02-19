package com.example.voting.dao

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode

class HttpResponse(val status : HttpStatusCode = HttpStatus.OK, val message : String )