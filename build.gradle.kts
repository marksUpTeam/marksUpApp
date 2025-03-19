import java.util.Properties

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.7.0" apply false
    id("org.jetbrains.kotlin.android") version "2.0.0" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0" apply false
    id("vkid.manifest.placeholders") version "1.1.0" apply true
}

val localProperties = Properties().apply {
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        load(localPropertiesFile.inputStream())
    }
}

val clientSecret: String? by project.extra {
    localProperties.getProperty("vkIntegrationSecret")
}

vkidManifestPlaceholders {
    init(
        clientId = "53171272",
        clientSecret = clientSecret?: "",
    )
    vkidRedirectHost = "vk.com"
    vkidRedirectScheme = "vk53171272"
}