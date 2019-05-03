package me.ihint.datahelper

import me.ihint.datahelper.compiler.Compiler
import me.ihint.datahelper.parser.Parser

abstract class DataHelper<C: Compiler, P: Parser> {
    // caller's name & property name
    protected var location = ""
    protected var isInited = false

    // Compiler & Parser
    protected open var compiler: C? = null
    protected open var parser: P? = null
}
