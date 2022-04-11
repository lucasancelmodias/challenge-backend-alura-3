package com.challenge.challengebackendalura3.controller


import com.challenge.challengebackendalura3.model.Transaction
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.File

import java.time.LocalDateTime



@RestController
class FileUploadController {

    @PostMapping("/fileupload")
    fun fileUpload(@RequestParam("file") file: MultipartFile){

        val defaultFilename = file.originalFilename ?: "default-${LocalDateTime.now()}.csv"
        val f = File(  System.getProperty("user.dir") + "/src/main/resources/temp/", defaultFilename)
        file.transferTo(f)

        val transactions = mutableListOf<Transaction>()
        for(element in f.readLines()){
            val columns = element.split(",")

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
    }
}