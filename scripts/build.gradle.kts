import org.tribot.wastedbro.gradle.plugin.TribotPlugin

subprojects {
    apply<TribotPlugin>()
}

subprojects {
    tasks {
        getAt("copyToBin").dependsOn(assemble)
        getAt("repoPackage").dependsOn(assemble)

        classes {
            finalizedBy(getAt("copyToBin"))
            finalizedBy(getAt("repoPackage"))
        }
    }
}