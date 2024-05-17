
/*
Copyright 2023 Breautek 

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

plugins {
    id("com.android.application")
}

android {
    namespace = "com.breautek.fuse.testapp"
    compileSdk = 34

    androidResources {
        noCompress += ""
    }

    defaultConfig {
        applicationId = "com.breautek.fuse.testapp"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation(project(":fuse"))
    implementation(project(":EchoPlugin"))
}

android.applicationVariants.configureEach {
    val variantName = this.baseName.replaceFirstChar(Char::titlecase)

    val prepareJSTask = tasks.register<Exec>("prepareJS${variantName}") {
        commandLine("./scripts/build.sh", "./src/main/assets")
    }

    tasks.named("generate${variantName}Resources").configure {
        this.dependsOn(prepareJSTask)
    }
}
