package com.demo.remote

import com.demo.common.config.RemoteConfig
import com.demo.common.config.RemoteConfigurationProvider
import com.demo.common.config.RemoteConfigurations
import com.demo.common.di.Settings
import com.demo.common.storage.Storage
import com.demo.remote.di.RemoteInstance
import com.demo.remote.logic.RemoteBuildType
import com.demo.remote.logic.RemoteBuildUtil
import javax.inject.Inject


internal class RemoteConfigurationProviderImpl @Inject constructor(
    @RemoteInstance val remoteBuildUtil: RemoteBuildUtil,
    @Settings val settingStorage: Storage
) : RemoteConfigurationProvider {

    /**
     * Get a specific configuration for this module, detailing specific configurations that apply this module.
     *
     * Note that if no module has previously been selected, defaults based on [RemoteBuildType] will be provided.
     */
    override fun getRemoteConfiguration(): RemoteConfig {
        val fallback = when (remoteBuildUtil.buildType()) {
            RemoteBuildType.DEBUG -> debugConfig()
            RemoteBuildType.RELEASE -> releaseConfig()
        }

        return settingStorage.get(REMOTE_CONFIG_KEY, RemoteConfig.serializer(), fallback)
    }

    /** Set a specific configuration for this module, detailing specific configurations that apply this module. */
    override fun setRemoteConfiguration(remoteConfig: RemoteConfig): Boolean = settingStorage.save(REMOTE_CONFIG_KEY, remoteConfig, RemoteConfig.serializer())

    /** Get list of known configurations, including any existing [RemoteConfigurations.CUSTOM]. */
    override fun getKnownRemoteConfigurations(): List<RemoteConfig> {
        val saved = settingStorage.getNullable(REMOTE_CONFIG_KEY, RemoteConfig.serializer())

        return RemoteConfigurations.values()
            .mapNotNull {
                when(it) {
                    RemoteConfigurations.DEV -> debugConfig()
                    RemoteConfigurations.PRODUCTION -> releaseConfig()
                    RemoteConfigurations.CUSTOM -> if(saved?.configType == RemoteConfigurations.CUSTOM) saved else null
                }
            }.toList()
    }

    private fun debugConfig() = RemoteConfig(
        configType = RemoteConfigurations.DEV,
        baseUrl = BuildConfig.REMOTE_BASE_URL_DEV
    )
    private fun releaseConfig() = RemoteConfig(
        configType = RemoteConfigurations.PRODUCTION,
        baseUrl = BuildConfig.REMOTE_BASE_URL_PRODUCTION
    )

    companion object {
        private const val REMOTE_CONFIG_KEY = "REMOTE_CONFIG_KEY"
    }
}