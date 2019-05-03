package me.ihint.datahelper.annotation

import kotlin.annotation.Target
import kotlin.annotation.Retention

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class Path(
        val file: String = "classpath:tables.txt",
        val charset: String = "utf8"
)
