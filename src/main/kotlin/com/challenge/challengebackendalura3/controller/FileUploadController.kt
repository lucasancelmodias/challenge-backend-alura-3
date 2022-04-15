package com.challenge.challengebackendalura3.controller



import com.challenge.challengebackendalura3.model.TransactionLog
import com.challenge.challengebackendalura3.repository.TransactionLogRepository
import com.challenge.challengebackendalura3.service.TransactionService
import org.springframework.data.domain.Sort

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile


@RestController()
class FileUploadController(private val transactionService: TransactionService, private val transactionLogRepository: TransactionLogRepository) {
    @CrossOrigin(origins = ["http://localhost:8000"])
    @PostMapping("/fileupload")
    fun fileUpload(@RequestParam("file") file: MultipartFile): ResponseEntity<*> = transactionService.validate(file)

    @GetMapping("/transactionslog", produces = ["application/json"])
    fun transactions(): List<TransactionLog> = transactionLogRepository.findAll(Sort.by(Sort.Direction.ASC, "transactionDay"))
}