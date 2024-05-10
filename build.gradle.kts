plugins {
    id("java")
    id("maven-publish")
    antlr
}

group = "com.github.XYZboom"
version = "1.0.0-alpha3"

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://jitpack.io")
}

publishing {
    repositories {
        mavenLocal()
    }
    publications {
        create<MavenPublication>("depends-java") {
            groupId = "com.github.XYZboom"
            artifactId = "depends-java"
            version = "1.0.0-alpha3"

            from(components["java"])
        }
        create<MavenPublication>("depend-java-source") {
            groupId = "com.github.XYZboom"
            artifactId = "depends-java"
            version = "1.0.0-alpha3"

            // 配置要上传的源码
            artifact(tasks.register<Jar>("sourcesJar") {
                from(sourceSets.main.get().allSource)
                archiveClassifier.set("sources")
            }) {
                classifier = "sources"
            }
        }
    }
}

sourceSets {
    main {
        extensions.getByType(AntlrSourceDirectorySet::class.java)
            .setSrcDirs(listOf("$projectDir/src/main/antlr/depends/extractor/java"))
    }
}

dependencies {
    implementation("org.jetbrains:annotations:24.0.1")
    implementation("com.github.XYZboom:depends-core:1.0.0-alpha6")
    implementation("org.codehaus.plexus:plexus-utils:3.5.1")
    antlr("org.antlr:antlr4:4.13.1")
    testImplementation("junit:junit:4.13.2")
    implementation("com.github.multilang-depends:utils:04855aebf3")
}

tasks.getByName<Test>("test") {
    useJUnit()
}

tasks.generateGrammarSource {
    arguments.addAll(
        arrayOf(
            "-package", "depends.extractor.java",
            "-visitor"
        )
    )
}