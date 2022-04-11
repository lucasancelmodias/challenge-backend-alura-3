package com.challenge.challengebackendalura3.model

import org.jetbrains.annotations.NotNull
import java.time.LocalDateTime


import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id


@Entity
class Transaction (
    @Id
    @GeneratedValue
    var id: Long? = null,
    @NotNull
    var bankOrigin: String,
    @NotNull
    var agencyOrigin: String,
    @NotNull
    var accountOrigin: String,
    @NotNull
    var bankDestination: String,
    @NotNull
    var agencyDestination: String,
    @NotNull
    var accountDestination: String,
    @NotNull
    var value : String,
    @NotNull
    var date: LocalDateTime

    )