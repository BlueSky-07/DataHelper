package me.ihint.datahelper.impl.core.datatype.mysql

import me.ihint.datahelper.core.Data
import me.ihint.datahelper.core.Entry
import me.ihint.datahelper.exception.verify.ValueIsNullException
import me.ihint.datahelper.exception.VerifyNotPassException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * TIMESTAMP : MysqlDataType(DataType)
 *
 * config: Config
 *      (FOR READING)
 *           ["read"] : DateTimeFormatter        // required, reading format
 *
 *      (FOR GENERATING)
 *          ["write"] : DateTimeFormatter        // required, writing format
 */

object TIMESTAMP : MysqlDataType() {
    override fun verify(data: Data, allowNull: Boolean): Boolean =
            when (val value: String? = data.value) {
                null -> allowNull
                else -> {
                    val config = data.config
                    val formatter: DateTimeFormatter = config["read"] as DateTimeFormatter
                    try {
                        LocalDateTime.parse(value, formatter)
                        true
                    } catch (e: Exception) {
                        false
                    }
                }
            }

    override fun toEntry(data: Data): Entry =
            if (verify(data, false)) Entry(
                    "`${data.fieldName}`",
                    "'${LocalDateTime.parse(data.value!!, data.config["read"] as DateTimeFormatter)
                            .format(data.config["write"] as DateTimeFormatter)
                    }'"
            )
            else when (data.value) {
                null -> throw ValueIsNullException(this)
                else -> throw VerifyNotPassException(data.value!!, this)
            }
}
