
object Deps {
    val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    private object Versions {
        val kotlin = "1.3.50"
        val appCompat = "1.1.0"
        val dagger = "2.25.2"
    }
}

object Modules {
    val di = ":di"
}