package me.ihint.datahelper.impl.core.datatype.mysql

import me.ihint.datahelper.core.Data
import me.ihint.datahelper.core.Entry
import me.ihint.datahelper.exception.verify.ValueIsNullException
import me.ihint.datahelper.exception.VerifyNotPassException
import java.math.BigDecimal
import java.math.RoundingMode

/**
 * DOUBLE : MysqlDataType(DataType)
 *
 * config: Config
 *      (FOR READING)
 *          ["max"] : Double?        // value should be less than it, not included itself
 *          ["min"] : Double?        // value should be greater then it, included itself
 *
 *      (FOR GENERATING)
 *          ["fix"] : Int?           // value will be fixed, rounding mode set as RoundingMode.FLOOR
 */

object DOUBLE : MysqlDataType() {
    override fun verify(data: Data, allowNull: Boolean): Boolean =
            when (val value: String? = data.value) {
                null -> allowNull
                else -> {
                    val config = data.config
                    when (val number: Double? = try {
                        java.lang.Double.valueOf(value)
                    } catch (e: Exception) {
                        null
                    }) {
                        null -> false
                        else -> {
                            val max: Double? = config["max"] as Double?
                            val min: Double? = config["min"] as Double?
                            when {
                                (max != null && number >= max) || (min != null && number < min) -> false
                                else -> true
                            }
                        }
                    }
                }
            }

    override fun toEntry(data: Data): Entry =
            if (verify(data, false))
                Entry(
                        "`${data.fieldName}`",
                        when (val fix: Int? = data.config["fix"] as Int?) {
                            null -> data.value!!
                            else -> BigDecimal(data.value!!)
                                    .setScale(fix, RoundingMode.FLOOR)
                                    .toString()
                        })
            else when (data.value) {
                null -> throw ValueIsNullException()
                else -> throw VerifyNotPassException()
            }
}
