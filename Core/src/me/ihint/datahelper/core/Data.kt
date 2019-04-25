package me.ihint.datahelper.core

/**
 * Data : class
 *
 * a unit, including value, field name, data type, and its field's config
 */

class Data(
        var value: String?,
        val field: Field
) {
    /**
     * copy a unit
     */
    constructor(data: Data) : this(data.value, data.field)

    val fieldName: String = field.name
    val type: DataType = field.type
    val config: Config = field.config

    fun clear() {
        this.value = null
    }

    /**
     * check value's validity, depends on the implement of DataType, and its field's config
     */
    fun verify(allowNull: Boolean): Boolean =
            type.verify(this, allowNull)

    fun toEntry():Entry = type.toEntry(this)
}
