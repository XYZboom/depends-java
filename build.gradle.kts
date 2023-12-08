plugins {
    id("java")
    id("maven-publish")
    antlr
}

group = "cn.emergentdesign.se"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    maven {
        url = uri("http://47.115.213.131:8080/repository/alex-release/")
        isAllowInsecureProtocol = true
    }
    mavenCentral()
}

publishing {
    repositories {
        maven {
            url = uri("http://47.115.213.131:8080/repository/alex-snapshots/")
            credentials {
                username = properties["maven-username"].toString()
                password = properties["maven-password"].toString()
            }
            isAllowInsecureProtocol = true
        }
    }
    publications {
        create<MavenPublication>("maven") {
            groupId = "cn.emergentdesign.se"
            artifactId = "depends-java"
            version = "0.9.8-SNAPSHOT"

            from(components["java"])
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
    implementation("cn.emergentdesign.se:depends-core:0.9.8-SNAPSHOT")
    implementation("org.codehaus.plexus:plexus-utils:3.5.1")
    antlr("org.antlr:antlr4:4.13.1")
    testImplementation("junit:junit:4.13.2")
    testImplementation("cn.emergentdesign.se:utils:0.1.1")

}

tasks.getByName<Test>("test") {
    useJUnit()
}

tasks.generateGrammarSource {
    arguments.addAll(
        arrayOf(
            "-package", "depends.extractor.java",
        )
    )
}