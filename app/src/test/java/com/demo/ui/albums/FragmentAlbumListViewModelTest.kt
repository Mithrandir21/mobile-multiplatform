package com.demo.ui.albums

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.demo.domain.model.albums.Album
import com.demo.domain.repositories.albums.AlbumsRepository
import com.demo.logging.Logger
import com.demo.testing.RxJavaSchedulerImmediateRule
import com.demo.ui.albums.list.FragmentAlbumListViewModel
import com.demo.ui.albums.list.FragmentAlbumListViewModel.ActionType
import com.demo.ui.albums.list.FragmentAlbumListViewModel.Data
import com.demo.ui.albums.list.ModelAlbum
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

@RunWith(MockitoJUnitRunner::class)
class FragmentAlbumListViewModelTest {

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
    fun `test start observing albums present`() {
        val album = Album(1, 2, "Title")
        val modelAlbum = ModelAlbum(album)
        val listOfModelAlbum = listOf(modelAlbum)

        val dataObserver = mock<Observer<Data>>()
        val errorObserver = mock<Observer<Pair<Throwable, ActionType>>>()
        val loadingObserver = mock<Observer<Pair<Boolean, ActionType>>>()

        whenever(albumsRepository.observeAlbums()).thenReturn(Flowable.just(listOf(album)))


        val viewModel = FragmentAlbumListViewModel(logger, albumsRepository)

        viewModel.observeViewData().observeForever(dataObserver)
        viewModel.observeErrors().observeForever(errorObserver)
        viewModel.observeProgress().observeForever(loadingObserver)

        viewModel.startObservingAlbums()


        verifyZeroInteractions(errorObserver)

        // Verify Loading states
        verify(loadingObserver, times(2)).onChanged(loadingCaptor.capture())
        assert(loadingCaptor.firstValue == true to ActionType.LoadingAlbums)
        assert(loadingCaptor.secondValue == false to ActionType.LoadingAlbums)

        verify(dataObserver, times(1)).onChanged(Data.AlbumsData(listOfModelAlbum))
    }

    @Test
    fun `test start observing albums exception`() {
        val error: Exception = mock()

        val dataObserver = mock<Observer<Data>>()
        val errorObserver = mock<Observer<Pair<Throwable, ActionType>>>()
        val loadingObserver = mock<Observer<Pair<Boolean, ActionType>>>()

        whenever(albumsRepository.observeAlbums()).thenReturn(Flowable.error(error))


        val viewModel = FragmentAlbumListViewModel(logger, albumsRepository)

        viewModel.observeViewData().observeForever(dataObserver)
        viewModel.observeErrors().observeForever(errorObserver)
        viewModel.observeProgress().observeForever(loadingObserver)

        viewModel.startObservingAlbums()


        verifyZeroInteractions(dataObserver)
        verifyZeroInteractions(loadingObserver)

        verify(errorObserver, times(1)).onChanged(error to ActionType.LoadingAlbums)
    }

    @Test
    fun `test refresh albums`() {
        val dataObserver = mock<Observer<Data>>()
        val errorObserver = mock<Observer<Pair<Throwable, ActionType>>>()
        val loadingObserver = mock<Observer<Pair<Boolean, ActionType>>>()

        whenever(albumsRepository.refreshAlbums()).thenReturn(Completable.complete())


        val viewModel = FragmentAlbumListViewModel(logger, albumsRepository)

        viewModel.observeViewData().observeForever(dataObserver)
        viewModel.observeErrors().observeForever(errorObserver)
        viewModel.observeProgress().observeForever(loadingObserver)

        viewModel.refreshAlbums()

        verifyZeroInteractions(errorObserver)
        verifyZeroInteractions(dataObserver)

        // Verify Loading states
        verify(loadingObserver, times(2)).onChanged(loadingCaptor.capture())
        assert(loadingCaptor.firstValue == true to ActionType.RefreshingAlbums)
        assert(loadingCaptor.secondValue == false to ActionType.RefreshingAlbums)
    }

    @Test
    fun `test refresh albums exception`() {
        val error: Exception = mock()

        val dataObserver = mock<Observer<Data>>()
        val errorObserver = mock<Observer<Pair<Throwable, ActionType>>>()
        val loadingObserver = mock<Observer<Pair<Boolean, ActionType>>>()

        whenever(albumsRepository.refreshAlbums()).thenReturn(Completable.error(error))


        val viewModel = FragmentAlbumListViewModel(logger, albumsRepository)

        viewModel.observeViewData().observeForever(dataObserver)
        viewModel.observeErrors().observeForever(errorObserver)
        viewModel.observeProgress().observeForever(loadingObserver)

        viewModel.refreshAlbums()


        verifyZeroInteractions(dataObserver)


        // Verify Loading states
        verify(loadingObserver, times(2)).onChanged(loadingCaptor.capture())
        assert(loadingCaptor.firstValue == true to ActionType.RefreshingAlbums)
        assert(loadingCaptor.secondValue == false to ActionType.RefreshingAlbums)

        verify(errorObserver, times(1)).onChanged(error to ActionType.RefreshingAlbums)
    }
}