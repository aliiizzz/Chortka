
object Deps {
    val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"

    val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    val roomKtx = "androidx.room:room-ktx:${Versions.room}"

    val junit = "junit:junit:${Versions.junit}"
    val junitExt = "androidx.test.ext:junit:${Versions.junitExt}"
    val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"

    val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.livedata}"
    val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.livedata}"

    private object Versions {
        val kotlin = "1.3.50"
        val appCompat = "1.1.0"
        val dagger = "2.25.2"
        val room = "2.2.3"
        val junit = "4.12"
        val junitExt = "1.1.1"
        val espresso = "3.2.0"
        val livedata = "2.2.0"
    }
}

object Modules {
    val di = ":di"
    val local = ":local"
    val repository = ":repository"
    val domain = ":domain"
}