package ir.aliiz.chortka.presentation

class Switch<T>(val value: T?, val defaultValue: T?) {
    private var done: Boolean = false
    fun get(): T? {
        if (done.not()) {
            done = true
            return value
        }

        return defaultValue
    }

    companion object {
        fun <T> nullSwitch(value: T) = Switch(value, null)
    }
}