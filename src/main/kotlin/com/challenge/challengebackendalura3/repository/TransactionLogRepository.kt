package com.challenge.challengebackendalura3.repository

import com.challenge.challengebackendalura3.model.TransactionLog
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface TransactionLogRepository : JpaRepository<TransactionLog, Long> {

    fun findFirstByTransactionDay(dateTime: LocalDateTime): TransactionLog?
}