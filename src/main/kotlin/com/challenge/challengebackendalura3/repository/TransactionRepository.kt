package com.challenge.challengebackendalura3.repository

import com.challenge.challengebackendalura3.model.Transaction
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface TransactionRepository : JpaRepository<Transaction, Long> {

    fun getTransactionByDate(dateTime: LocalDateTime) : Transaction?
}