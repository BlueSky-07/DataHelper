package me.ihint.datahelper.annotation

import kotlin.annotation.Target
import kotlin.annotation.Retention

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class Lang(
        val lang: String = "mysql"
)
