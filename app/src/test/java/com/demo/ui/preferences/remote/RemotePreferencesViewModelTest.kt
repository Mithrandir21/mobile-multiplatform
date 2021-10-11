package com.demo.ui.preferences.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.demo.common.config.RemoteConfig
import com.demo.common.config.RemoteConfigurationProvider
import com.demo.common.config.RemoteConfigurations
import com.demo.logging.Logger
import com.demo.testing.RxJavaSchedulerImmediateRule
import com.demo.ui.preferences.remote.RemotePreferencesViewModel.ActionType
import com.demo.ui.preferences.remote.RemotePreferencesViewModel.Data
import com.nhaarman.mockitokotlin2.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RemotePreferencesViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    var testSchedulerRule = RxJavaSchedulerImmediateRule()

    @Mock
    private lateinit var logger: Logger

    @Mock
    private lateinit var remoteConfigurationProvider: RemoteConfigurationProvider

    @Captor
    private lateinit var loadingCaptor: ArgumentCaptor<Pair<Boolean, ActionType>>


    @Test
    fun `test loading known remote environments`() {
        val remoteConfigA = RemoteConfig(RemoteConfigurations.DEV, "A_URL")
        val remoteConfigB = RemoteConfig(RemoteConfigurations.PRODUCTION, "B_URL")
        val currentConfig = RemoteConfig(RemoteConfigurations.CUSTOM, "CUSTOM_URL")

        val remoteEnvironments = listOf(remoteConfigA, remoteConfigB, currentConfig)
        val currentIndex = remoteEnvironments.indexOf(currentConfig)

        val dataObserver = mock<Observer<Data>>()
        val errorObserver = mock<Observer<Pair<Throwable, ActionType>>>()
        val loadingObserver = mock<Observer<Pair<Boolean, ActionType>>>()

        whenever(remoteConfigurationProvider.getKnownRemoteConfigurations()).thenReturn(remoteEnvironments)
        whenever(remoteConfigurationProvider.getRemoteConfiguration()).thenReturn(currentConfig)


        val viewModel = RemotePreferencesViewModel(logger, remoteConfigurationProvider)

        viewModel.observeViewData().observeForever(dataObserver)
        viewModel.observeErrors().observeForever(errorObserver)
        viewModel.observeProgress().observeForever(loadingObserver)

        viewModel.loadRemoteEnvironment()


        verifyZeroInteractions(errorObserver)

        // Verify Loading states
        verify(loadingObserver, times(2)).onChanged(loadingCaptor.capture())
        assert(loadingCaptor.firstValue == true to ActionType.LoadingRemoteEnvironment)
        assert(loadingCaptor.secondValue == false to ActionType.LoadingRemoteEnvironment)

        verify(dataObserver, times(1)).onChanged(Data.PossibleRemoteEnvironments(remoteEnvironments, currentIndex))
    }


    @Test
    fun `test setting remote environments`() {
        val remoteConfigCustom = RemoteConfig(RemoteConfigurations.CUSTOM, "CUSTOM_URL")

        val dataObserver = mock<Observer<Data>>()
        val errorObserver = mock<Observer<Pair<Throwable, ActionType>>>()
        val loadingObserver = mock<Observer<Pair<Boolean, ActionType>>>()

        whenever(remoteConfigurationProvider.setRemoteConfiguration(remoteConfigCustom)).thenReturn(true)


        val viewModel = RemotePreferencesViewModel(logger, remoteConfigurationProvider)

        viewModel.observeViewData().observeForever(dataObserver)
        viewModel.observeErrors().observeForever(errorObserver)
        viewModel.observeProgress().observeForever(loadingObserver)

        viewModel.setRemoteEnvironment(remoteConfigCustom)


        verifyZeroInteractions(errorObserver)

        // Verify Loading states
        verify(loadingObserver, times(2)).onChanged(loadingCaptor.capture())
        assert(loadingCaptor.firstValue == true to ActionType.SetRemoteEnvironment)
        assert(loadingCaptor.secondValue == false to ActionType.SetRemoteEnvironment)

        verify(dataObserver, times(1)).onChanged(Data.RemoteEnvironmentSet)
    }
}