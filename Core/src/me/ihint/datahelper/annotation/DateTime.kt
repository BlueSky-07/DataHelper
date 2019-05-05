package me.ihint.datahelper.annotation

import kotlin.annotation.Target
import kotlin.annotation.Retention

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class DateTime(
        val input: String = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
        val output: String = "yyyy/MM/dd HH:mm:ss"
)
