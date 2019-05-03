package me.ihint.datahelper.annotation

import kotlin.annotation.Target
import kotlin.annotation.Retention

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class DateTime (
        val read: String = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
        val write: String = "yyyy/MM/dd HH:mm:ss"
)
