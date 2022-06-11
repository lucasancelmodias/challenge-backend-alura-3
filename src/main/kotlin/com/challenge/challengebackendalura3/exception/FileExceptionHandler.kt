package com.challenge.challengebackendalura3.exception

import com.fasterxml.jackson.annotation.JsonFormat

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.multipart.MaxUploadSizeExceededException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.time.LocalDateTime


@ControllerAdvice
class FileExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [FileInvalidFormatException::class])
    fun handleException(ex : Exception): ResponseEntity<ErrorResponse>{
        return ErrorResponse(statusCode = HttpStatus.BAD_REQUEST.value(), message = ex.message)
            .buildResponse()
    }

    @ExceptionHandler(value = [TransactionAlreadyExists::class])
    fun handleTransactionsConflict(ex : Exception) : ResponseEntity<ErrorResponse>{
        return ErrorResponse(statusCode = HttpStatus.CONFLICT.value(), message = ex.message)
            .buildResponse()
    }

    @ExceptionHandler(value = [MaxUploadSizeExceededException::class])
    fun handleMaxFileSizeExceed(ex : Exception) : ResponseEntity<ErrorResponse>{
        return ErrorResponse(statusCode = HttpStatus.PAYLOAD_TOO_LARGE.value(), message = ex.message)
            .buildResponse()
    }
}
class TransactionAlreadyExists(message: String?) : Exception(message)
class FileInvalidFormatException(message: String?) : Exception(message)
class ErrorResponse(
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    val timestamp: LocalDateTime? = LocalDateTime.now(),
    val statusCode : Int,
    val message : String?){


    fun buildResponse(): ResponseEntity<ErrorResponse>{
        return ResponseEntity(this, HttpStatus.valueOf(this.statusCode))
    }
}
