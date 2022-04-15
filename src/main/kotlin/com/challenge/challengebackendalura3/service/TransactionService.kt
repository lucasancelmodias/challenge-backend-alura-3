package com.challenge.challengebackendalura3.service

import com.challenge.challengebackendalura3.model.Transaction
import com.challenge.challengebackendalura3.model.TransactionLog
import com.challenge.challengebackendalura3.repository.TransactionLogRepository
import com.challenge.challengebackendalura3.repository.TransactionRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.charset.StandardCharsets
import java.time.LocalDateTime


@Service
class TransactionService(private val transRepo: TransactionRepository, private val transLogRepo: TransactionLogRepository) {

    fun getTransactions() : List<Transaction> = transRepo.findAll()
    fun addTransactions(transactions: MutableList<Transaction>) = ResponseEntity.ok(transRepo.saveAll(transactions))
    fun getTransactionById(id: Long) : ResponseEntity<Transaction> = transRepo.findById(id)
        .map{ trans -> ResponseEntity.ok(trans)}
        .orElse(ResponseEntity.notFound().build())
    fun getTransactionByDate(dateTime: LocalDateTime): Transaction? = transRepo.getTransactionByDate(dateTime)

    fun validate(file: MultipartFile): ResponseEntity<*>{
        if(file.isEmpty){
            return ResponseEntity(mapOf("error" to "O arquivo enviado está vazio."), HttpStatus.BAD_REQUEST)
        }
        if(file.size >= 1048576){

            return ResponseEntity(mapOf("error" to "O arquivo não pode exceder 1048576 bytes."), HttpStatus.BAD_REQUEST)
        }
        val transactions = mutableListOf<Transaction>()
        try {
            val lines = String(file.bytes, StandardCharsets.UTF_8).split("\n")
            val dayOfTransaction = LocalDateTime.parse(lines[0].split(",")[7])


            val transactionAlreadyExists = transLogRepo.findFirstByTransactionDay(dayOfTransaction)

            if(transactionAlreadyExists != null){
                return ResponseEntity(mapOf("error" to "As transações para o dia ${dayOfTransaction.toLocalDate()} já foram cadastradas."), HttpStatus.CONFLICT)
            }
            for(line in lines){
                val columns = line.split(",")

                val isValid = columns.all { it.isNotEmpty() } && columns.size == 8

                if(!isValid){
                    continue
                }
                val currentTransactionDate = LocalDateTime.parse(columns[7])

                if(dayOfTransaction.toLocalDate().compareTo(currentTransactionDate.toLocalDate()) != 0){
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
                    currentTransactionDate
                )
                transactions.add(transaction)
            }

            if(transactions.isNotEmpty()) {
                val translog = TransactionLog(null, dayOfTransaction, LocalDateTime.now())
                transLogRepo.save(translog)
            }
            transactions.forEach{println("Banco Origem ${it.bankOrigin}\nValor ${it.value}\nData ${it.date}")}


        }catch(e : Exception){
            println(e.message)
            println(e.toString())
            return ResponseEntity(mapOf("error" to "Não foi possível extrair os dados do arquivo, verifique o formato e tente novamente."), HttpStatus.BAD_REQUEST)
        }
        return ResponseEntity.ok(addTransactions(transactions))
    }




}