package me.ihint.datahelper.impl.core.datatype.mysql

import me.ihint.datahelper.core.Data
import me.ihint.datahelper.core.Entry
import me.ihint.datahelper.exception.verify.ValueIsNullException
import me.ihint.datahelper.exception.VerifyNotPassException

/**
 * BOOLEAN : MysqlDataType(DataType)
 *
 * config: Config
 *      (FOR READING)
 *          it will recognize: "true", "false", "1", "0"
 *
 *      (FOR GENERATING)
 *          it will use: "TRUE", "FALSE"
 */

object BOOLEAN : MysqlDataType() {
    override fun verify(data: Data, allowNull: Boolean): Boolean =
            when (val value: String? = data.value) {
                null -> allowNull
                else -> {
                    when (value.toLowerCase()) {
                        "true", "false", "1", "0" -> true
                        else -> false
                    }
                }
            }

    override fun toEntry(data: Data): Entry =
            if (verify(data, false))
                Entry(
                        "`${data.fieldName}`",
                        when (data.value!!.toLowerCase()) {
                            "1", "true" -> "TRUE"
                            "0", "false" -> "FALSE"
                            else -> throw VerifyNotPassException()
                        })
            else when (data.value) {
                null -> throw ValueIsNullException()
                else -> throw VerifyNotPassException()
            }
}
