package com.demo.ui.albums.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.demo.domain.model.albums.Album
import com.demo.domain.repositories.albums.AlbumsRepository
import com.demo.logging.Logger
import com.demo.testing.RxJavaSchedulerImmediateRule
import com.demo.ui.albums.details.FragmentAlbumDetailsViewModel.ActionType
import com.demo.ui.albums.details.FragmentAlbumDetailsViewModel.Data
import com.nhaarman.mockitokotlin2.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class FragmentAlbumDetailsViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    var testSchedulerRule = RxJavaSchedulerImmediateRule()

    @Mock
    private lateinit var logger: Logger

    @Mock
    private lateinit var albumsRepository: AlbumsRepository

    @Captor
    private lateinit var loadingCaptor: ArgumentCaptor<Pair<Boolean, ActionType>>


    @Test
    fun `test start observing album present`() {
        val albumId = 1
        val album = Album(albumId, 2, "Title")

        val dataObserver = mock<Observer<Data>>()
        val errorObserver = mock<Observer<Pair<Throwable, ActionType>>>()
        val loadingObserver = mock<Observer<Pair<Boolean, ActionType>>>()

        whenever(albumsRepository.observeAlbum(albumId)).thenReturn(Flowable.just(Optional.of(album)))
        whenever(albumsRepository.refreshAlbum(albumId)).thenReturn(Completable.complete())


        val viewModel = FragmentAlbumDetailsViewModel(logger, albumsRepository)

        viewModel.observeViewData().observeForever(dataObserver)
        viewModel.observeErrors().observeForever(errorObserver)
        viewModel.observeProgress().observeForever(loadingObserver)

        viewModel.observeAlbum(albumId)


        verifyZeroInteractions(errorObserver)

        // Verify Loading states
        verify(loadingObserver, times(4)).onChanged(loadingCaptor.capture())
        verify(dataObserver, times(1)).onChanged(Data.AlbumData(album))
    }

    @Test
    fun `test start observing album not present`() {
        val albumId = 1

        val dataObserver = mock<Observer<Data>>()
        val errorObserver = mock<Observer<Pair<Throwable, ActionType>>>()
        val loadingObserver = mock<Observer<Pair<Boolean, ActionType>>>()

        whenever(albumsRepository.observeAlbum(albumId)).thenReturn(Flowable.just(Optional.empty()))
        whenever(albumsRepository.refreshAlbum(albumId)).thenReturn(Completable.complete())


        val viewModel = FragmentAlbumDetailsViewModel(logger, albumsRepository)

        viewModel.observeViewData().observeForever(dataObserver)
        viewModel.observeErrors().observeForever(errorObserver)
        viewModel.observeProgress().observeForever(loadingObserver)

        viewModel.observeAlbum(albumId)


        verifyZeroInteractions(errorObserver)

        // Verify Loading states
        verify(loadingObserver, times(4)).onChanged(loadingCaptor.capture())
        verify(dataObserver, times(1)).onChanged(Data.NoAlbumData(albumId))
    }

    @Test
    fun `test start observing album not exception`() {
        val albumId = 1
        val error: Exception = mock()

        val dataObserver = mock<Observer<Data>>()
        val errorObserver = mock<Observer<Pair<Throwable, ActionType>>>()
        val loadingObserver = mock<Observer<Pair<Boolean, ActionType>>>()

        whenever(albumsRepository.observeAlbum(albumId)).thenReturn(Flowable.error(error))
        whenever(albumsRepository.refreshAlbum(albumId)).thenReturn(Completable.complete())


        val viewModel = FragmentAlbumDetailsViewModel(logger, albumsRepository)

        viewModel.observeViewData().observeForever(dataObserver)
        viewModel.observeErrors().observeForever(errorObserver)
        viewModel.observeProgress().observeForever(loadingObserver)

        viewModel.observeAlbum(albumId)


        verifyZeroInteractions(dataObserver)

        verify(loadingObserver, times(2)).onChanged(loadingCaptor.capture())
        verify(errorObserver, times(1)).onChanged(error to ActionType.LoadingAlbum)
    }
}