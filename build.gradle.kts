plugins {
    `java-library`
    id("net.minecrell.plugin-yml.bukkit") version "0.5.1"
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
    withSourcesJar()
    withJavadocJar()
}

repositories {
    mavenLocal()
    maven {
        url = uri("https://papermc.io/repo/repository/maven-public/")
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.18.1-R0.1-SNAPSHOT")
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }
    javadoc {
        options.encoding = Charsets.UTF_8.name()
    }
    processResources {
        filteringCharset = Charsets.UTF_8.name()
    }
}

group = "net.aerulion"
version = "1.2.0"

bukkit {
    name = "ArmorPrinter"
    main = "net.aerulion.armorprinter.Main"
    version = getVersion().toString()
    author = "aerulion"
    apiVersion = "1.18"
    commands {
        register("armorprinter") {
            description = "ArmorPrinter GUI öffnen."
        }
        register("printrandomarmor") {
            description = "Random gefärbte Rüstung ins Inventar legen."
        }
        register("printperson") {
            description = "Erstellt ein ArmorStand-Abbild des angegeben Spielers."
        }
    }
    permissions {
        register("armorprinter.armorprinter") {
            description = "Erlaubt dem Spieler /armorprinter zu nutzen."
            default = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.OP
        }
        register("armorprinter.printrandomarmor") {
            description = "Erlaubt dem Spieler /printrandomarmor zu nutzen."
            default = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.OP
        }
        register("armorprinter.printperson") {
            description = "Erlaubt dem Spieler /printperson zu nutzen."
            default = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.OP
        }
    }
}
