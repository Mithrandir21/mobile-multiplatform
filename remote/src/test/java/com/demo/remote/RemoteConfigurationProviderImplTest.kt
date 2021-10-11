package com.demo.remote

import com.nhaarman.mockitokotlin2.*
import com.demo.common.config.RemoteConfig
import com.demo.common.config.RemoteConfigurations
import com.demo.common.storage.Storage
import com.demo.remote.logic.RemoteBuildType
import com.demo.remote.logic.RemoteBuildUtil
import kotlinx.serialization.DeserializationStrategy
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RemoteConfigurationProviderImplTest {


    @Mock
    private lateinit var remoteBuildUtil: RemoteBuildUtil

    @Mock
    private lateinit var settingStorage: Storage


    private fun debugConfig() = RemoteConfig(
        configType = RemoteConfigurations.DEV,
        baseUrl = BuildConfig.REMOTE_BASE_URL_DEV
    )

    private fun releaseConfig() = RemoteConfig(
        configType = RemoteConfigurations.PRODUCTION,
        baseUrl = BuildConfig.REMOTE_BASE_URL_PRODUCTION
    )

    private fun customConfig() = RemoteConfig(
        configType = RemoteConfigurations.CUSTOM,
        baseUrl = "Custom Base URL"
    )


    @Test
    fun `test getting debug fallback config`() {
        val fallbackConfig = debugConfig()

        whenever(remoteBuildUtil.buildType()).thenReturn(RemoteBuildType.DEBUG)
        whenever(settingStorage.get(any(), any<DeserializationStrategy<RemoteConfig>>(), any())).thenReturn(fallbackConfig)

        val provider = RemoteConfigurationProviderImpl(remoteBuildUtil, settingStorage)

        val remoteConfiguration = provider.getRemoteConfiguration()
        assertEquals(fallbackConfig, remoteConfiguration)


        verify(remoteBuildUtil, times(1)).buildType()
        verify(settingStorage, times(1)).get(any(), any<DeserializationStrategy<RemoteConfig>>(), any())
    }

    @Test
    fun `test getting debug new config`() {
        val fallbackConfig = debugConfig()
        val customConfig = customConfig()

        whenever(remoteBuildUtil.buildType()).thenReturn(RemoteBuildType.DEBUG)
        whenever(settingStorage.get(any(), any(), eq(fallbackConfig))).thenReturn(customConfig)

        val provider = RemoteConfigurationProviderImpl(remoteBuildUtil, settingStorage)

        val remoteConfiguration = provider.getRemoteConfiguration()
        assertEquals(customConfig, remoteConfiguration)


        verify(remoteBuildUtil, times(1)).buildType()
        verify(settingStorage, times(1)).get(any(), any(), eq(fallbackConfig))
    }

    @Test
    fun `test getting release fallback config`() {
        val fallbackConfig = releaseConfig()

        whenever(remoteBuildUtil.buildType()).thenReturn(RemoteBuildType.RELEASE)
        whenever(settingStorage.get(any(), any<DeserializationStrategy<RemoteConfig>>(), any())).thenReturn(fallbackConfig)

        val provider = RemoteConfigurationProviderImpl(remoteBuildUtil, settingStorage)

        val remoteConfiguration = provider.getRemoteConfiguration()
        assertEquals(fallbackConfig, remoteConfiguration)


        verify(remoteBuildUtil, times(1)).buildType()
        verify(settingStorage, times(1)).get(any(), any<DeserializationStrategy<RemoteConfig>>(), any())
    }

    @Test
    fun `test getting release new config`() {
        val fallbackConfig = releaseConfig()
        val customConfig = customConfig()

        whenever(remoteBuildUtil.buildType()).thenReturn(RemoteBuildType.RELEASE)
        whenever(settingStorage.get(any(), any(), eq(fallbackConfig))).thenReturn(customConfig)

        val provider = RemoteConfigurationProviderImpl(remoteBuildUtil, settingStorage)

        val remoteConfiguration = provider.getRemoteConfiguration()
        assertEquals(customConfig, remoteConfiguration)


        verify(remoteBuildUtil, times(1)).buildType()
        verify(settingStorage, times(1)).get(any(), any(), eq(fallbackConfig))
    }

    @Test
    fun `test saving custom config`() {
        val customConfig = customConfig()

        whenever(settingStorage.save(any(), eq(customConfig), any(), any())).thenReturn(true)

        val provider = RemoteConfigurationProviderImpl(remoteBuildUtil, settingStorage)

        val saveResult = provider.setRemoteConfiguration(customConfig)
        assertEquals(true, saveResult)


        verify(settingStorage, times(1)).save(any(), eq(customConfig), any(), any())
    }

    @Test
    fun `test getting all default configs`() {
        val debugConfig = debugConfig()
        val productionConfig = releaseConfig()
        val defaultConfigList = listOf(debugConfig, productionConfig)

        whenever(settingStorage.getNullable(any(), any<DeserializationStrategy<RemoteConfig>>(), eq(null))).thenReturn(null)

        val provider = RemoteConfigurationProviderImpl(remoteBuildUtil, settingStorage)

        val defaultConfigs = provider.getKnownRemoteConfigurations()
        assertEquals(defaultConfigList, defaultConfigs)


        verify(settingStorage, times(1)).getNullable(any(), any<DeserializationStrategy<RemoteConfig>>(), eq(null))
    }

    @Test
    fun `test getting all default and custom configs`() {
        val debugConfig = debugConfig()
        val productionConfig = releaseConfig()
        val customConfig = customConfig()
        val defaultConfigList = listOf(debugConfig, productionConfig, customConfig)

        whenever(settingStorage.getNullable(any(), any<DeserializationStrategy<RemoteConfig>>(), eq(null))).thenReturn(customConfig)

        val provider = RemoteConfigurationProviderImpl(remoteBuildUtil, settingStorage)

        val defaultConfigs = provider.getKnownRemoteConfigurations()
        assertEquals(defaultConfigList, defaultConfigs)


        verify(settingStorage, times(1)).getNullable(any(), any<DeserializationStrategy<RemoteConfig>>(), eq(null))
    }
}