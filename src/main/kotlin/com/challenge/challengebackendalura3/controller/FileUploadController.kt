package com.challenge.challengebackendalura3.controller


import com.challenge.challengebackendalura3.model.Transaction
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.nio.charset.StandardCharsets

import java.time.LocalDateTime



@RestController
class FileUploadController {

    @PostMapping("/fileupload")
    fun fileUpload(@RequestParam("file") file: MultipartFile): ResponseEntity<String>{

        if(file.isEmpty){
            return ResponseEntity("O arquivo enviado está vazio.", HttpStatus.BAD_REQUEST)
        }
        val lines = String(file.bytes, StandardCharsets.UTF_8).split("\n")

        val transactions = mutableListOf<Transaction>()
        for(line in lines){
            val columns = line.split(",")

            val isValid = columns.filter { it.isNotEmpty() }.size >= 8

            if(!isValid){
                continue
            }
            val transaction = Transaction(null,
                columns[0],
                columns[1],
                columns[2],
                columns[3],
                columns[4],
                columns[5],
                columns[6],
                LocalDateTime.parse(columns[7])
            )
            transactions.add(transaction)
        }
        transactions.forEach{println("Banco Origem ${it.bankOrigin}\nValor ${it.value}\nData ${it.date}")}

        return ResponseEntity.ok().build()
    }
}