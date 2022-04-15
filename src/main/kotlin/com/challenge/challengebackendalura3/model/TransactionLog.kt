package com.challenge.challengebackendalura3.model

import org.jetbrains.annotations.NotNull
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "transactionslog")
class TransactionLog(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @NotNull
    var transactionDay: LocalDateTime,
    @NotNull
    var uploadDate: LocalDateTime
){

}