import app.web.drjackycv.buildsrc.Depends
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-parcelize")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    buildFeatures {
        dataBinding = false
        viewBinding = true
    }
    compileSdkVersion(Depends.Versions.androidCompileSdkVersion)

    buildFeatures {
        dataBinding = true
    }

    defaultConfig {
        multiDexEnabled = true
        applicationId = "app.web.drjackycv.mvvmtemplate"
        minSdkVersion(Depends.Versions.minSdkVersion)
        targetSdkVersion(Depends.Versions.targetSdkVersion)
        versionCode = Depends.Versions.appVersionCode
        versionName = Depends.generateVersionName()
        testInstrumentationRunner =
            Depends.Versions.testInstrumentationRunner
        javaCompileOptions.annotationProcessorOptions.arguments += mapOf(
            "room.schemaLocation" to "$projectDir/schemas"
        )
    }
    sourceSets {
        map { it.java.srcDir("src/${it.name}/kotlin") }
    }
    buildTypes {
        named("debug") {
            isMinifyEnabled = false
            isShrinkResources = false
            isDebuggable = true
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-DEBUG"
            signingConfig = signingConfigs.getByName("debug")
        }
        named("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            setProguardFiles(
                listOf(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            )
        }
    }
    compileOptions {
        targetCompatibility = JavaVersion.VERSION_1_8
        sourceCompatibility = JavaVersion.VERSION_1_8
    }
    lintOptions {
        isAbortOnError = false
    }
    //testOptions.unitTests.returnDefaultValues = true
    packagingOptions {
//        exclude("META-INF/rxjava.properties")
//        exclude("META-INF/proguard/androidx-annotations.pro")
//        exclude("error_prone/Annotations.gwt.xml")
//        exclude("third_party/java_src/error_prone/project/annotations/Annotations.gwt.xml")
//        exclude("third_party/java_src/error_prone/project/annotations/Google_internal.gwt.xml")
//        exclude("jsr305_annotations/Jsr305_annotations.gwt.xml")
//        exclude("META-INF/AL2.0")
//        exclude("META-INF/LGPL2.1")
//        exclude("**/*.dot")
//        exclude("**/*.kotlin_metadata")
//        exclude("**/*.properties")
//        exclude("*.properties")
//        exclude("kotlin/**")
//        exclude("LICENSE.txt")
//        exclude("LICENSE_OFL")
//        exclude("LICENSE_UNICODE")
//        exclude("META-INF/*.kotlin_module")
//        exclude("META-INF/*.version")
//        exclude("META-INF/androidx.*")
//        exclude("META-INF/CHANGES")
//        exclude("META-INF/com.uber.crumb/**")
//        exclude("META-INF/LICENSE")
//        exclude("META-INF/LICENSE.txt")
//        exclude("META-INF/NOTICE")
//        exclude("META-INF/NOTICE.txt")
//        exclude("META-INF/README.md")
//        exclude("META-INF/rxjava.properties")
//        exclude("META-INF/services/javax.annotation.processing.Processor")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
        freeCompilerArgs = listOf(
            "-XXLanguage:+InlineClasses"
        )
        useIR = true
    }
}

dependencies {
    implementation(Depends.Libraries.kotlin)
    implementation(Depends.Libraries.android_core_ktx)
    implementation(Depends.Libraries.multidex)
    implementation(Depends.Libraries.fragment_ktx)
    implementation(Depends.Libraries.paging_runtime_ktx)
    implementation(Depends.Libraries.paging_rx)
    implementation(Depends.Libraries.dataStore_preferences)
    //compose
//    implementation(Depends.Libraries.compose_foundation)
//    implementation(Depends.Libraries.compose_foundation_layout)
    implementation(Depends.Libraries.compose_ui)
    implementation(Depends.Libraries.compose_material)
//    implementation(Depends.Libraries.compose_runtime)
//    implementation(Depends.Libraries.compose_runtime_dispatch)
//    implementation(Depends.Libraries.compose_runtime_saved_instance_state)
//    implementation(Depends.Libraries.compose_navigation)
//    implementation(Depends.Libraries.ui_test)
    implementation(Depends.Libraries.ui_tooling)
//    implementation(Depends.Libraries.ui_framework)
//    implementation(Depends.Libraries.ui_foundation)
//    implementation(Depends.Libraries.ui_layout)
//    implementation(Depends.Libraries.ui_material)
    //dependency injection
    implementation(Depends.Libraries.dagger)
    kapt(Depends.Libraries.dagger_compiler)
    implementation(Depends.Libraries.dagger_hilt_android)
    kapt(Depends.Libraries.dagger_hilt_compiler)
//    kapt(Depends.Libraries.dagger_hilt_android_compiler)
//    kapt(Depends.Libraries.hilt_androidx_compiler)
    implementation(Depends.Libraries.java_inject)
    //network
    implementation(Depends.Libraries.retrofit)
    implementation(Depends.Libraries.retrofit_adapter_rx)
    implementation(Depends.Libraries.logging_interceptor)
    //other
    implementation(Depends.Libraries.timber)
    implementation(Depends.Libraries.material)
    debugImplementation(Depends.Libraries.leak_canary)
    debugImplementation(Depends.Libraries.chucker)
    releaseImplementation(Depends.Libraries.chucker_no_op)
    //test
    testImplementation(Depends.Libraries.junit)
    androidTestImplementation(Depends.Libraries.test_runner)
    androidTestImplementation(Depends.Libraries.espresso_core)

    implementation(project(":presentation"))
    implementation(project(":data"))
    implementation(project(":domain"))
}

kapt {
    correctErrorTypes = true
}