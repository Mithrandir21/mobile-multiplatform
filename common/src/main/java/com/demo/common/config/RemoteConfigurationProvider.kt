package com.demo.common.config

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

/**
 * Provider for [RemoteConfig].
 *
 * Note: Interface places in Common module because Remote module not a direct dependency of App.
 */
interface RemoteConfigurationProvider {

    /**
     * Get a specific configuration for this module, detailing specific configurations that apply this module.
     *
     * Note that if no module has previously been selected, defaults based on remote build type will be provided.
     */
    fun getRemoteConfiguration(): RemoteConfig

    /** Set a specific configuration for this module, detailing specific configurations that apply this module. */
    fun setRemoteConfiguration(remoteConfig: RemoteConfig): Boolean

    /** Get list of known configurations, including any existing [RemoteConfigurations.CUSTOM]. */
    fun getKnownRemoteConfigurations(): List<RemoteConfig>
}

@Keep
@Serializable
data class RemoteConfig(
    val configType: RemoteConfigurations,
    val baseUrl: String
)

@Keep
enum class RemoteConfigurations {
    DEV,
    PRODUCTION,
    CUSTOM
}