plugins {
    `maven-publish`
    id("java")
    id("io.izzel.taboolib") version "1.34"
    id("org.jetbrains.kotlin.jvm") version "1.5.10"
    id("com.github.johnrengelman.shadow") version "7.0.0"
}
    
group = "me.arasple.mc.trhologram"
version = "6.0.7-16"
description = "Modern & Advanced Hologram-Plugin for Minecraft Servers"

taboolib {
    install(
        "common",
        "common-5",
        "platform-bukkit",
        "module-configuration",
        "module-chat",
        "module-lang",
        "module-nms",
        "module-nms-util",
        "module-metrics",
        "module-kether"
    )

    description {
        contributors {
            name("Arasple")
        }
        dependencies {
            name("PlaceholderAPI").optional(true)
            name("TrMenu").optional(true)
            name("SkinsRestorer").optional(true)
        }
    }

    classifier = null
    version = "6.0.7-16"
}

repositories {
    mavenCentral()
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://repo.codemc.org/repository/maven-public/")
}

dependencies {
    compileOnly("org.jetbrains.kotlin:kotlin-stdlib")
    compileOnly("ink.ptms.core:v11604:11604:all")
    compileOnly("ink.ptms.core:v11600:11600:all")
    compileOnly("ink.ptms.core:v11300:11300:all")
    compileOnly("ink.ptms.core:v11200:11200:all")
    compileOnly("ink.ptms.core:v10900:10900:all")
    compileOnly("me.clip:placeholderapi:2.10.9")
    compileOnly(fileTree("libs"))
}


tasks.shadowJar {
    dependencies {
        taboolib.modules.forEach { exclude(dependency("io.izzel:taboolib:${taboolib.version}:$it")) }
        exclude(dependency("com.google.code.gson:gson:2.8.6"))
        exclude(dependency("org.bstats:bstats-bukkit:1.5"))

        exclude("data")
        exclude("META-INF/*.kotlin_module")
        exclude("META-INF/maven")
        exclude("lang")
        exclude("holograms")
        exclude("*.yml")
    }
    relocate("taboolib", "${project.group}.taboolib")
    archiveClassifier.set("pure")
}

configure<PublishingExtension> {
    publications {
        create<MavenPublication>("shadow") {
            shadow.component(this)
            groupId = "me.arasple"
        }
    }
    repositories {
        maven {
            url = uri("https://repo.iroselle.com/repository/maven-releases/")
            credentials {
                file("access.txt").also {
                    if (!it.exists()) return@credentials
                }.readLines().apply {
                    username = this[0]
                    password = this[1]
                }
            }
        }
    }
}