package me.ihint.datahelper.core

/**
 * Field : class
 *
 * a definition of one data, including name, data type, and config
 */

open class Field(
        val name: String,
        val type: DataType,
        val config: Config
) {
    /**
     * to create a Data, its value is null
     */
    fun newData(): Data = Data(
            null, this
    )

    /**
     * to create a Data, its value is set as input param
     */
    fun newData(value: String): Data = Data(
            value, this
    )

    override fun toString(): String {
        return "$name(${type.javaClass.simpleName})$config"
    }
}
