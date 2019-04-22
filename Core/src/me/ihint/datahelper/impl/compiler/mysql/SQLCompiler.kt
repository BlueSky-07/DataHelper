package me.ihint.datahelper.impl.compiler.mysql

import me.ihint.datahelper.compiler.SQLCompiler
import me.ihint.datahelper.impl.core.group.mysql.Record

class SQLCompiler: SQLCompiler<Record> {
    override fun insert(record:Record): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun insert(record:Record, returnId: Boolean): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update(target:Record, condition:Record): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun select(condition:Record): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun select(condition:Record, offset: Long, size: Long): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(condition:Record): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
