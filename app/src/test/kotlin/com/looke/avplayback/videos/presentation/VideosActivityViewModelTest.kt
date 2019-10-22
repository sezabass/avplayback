package com.looke.avplayback.videos.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.looke.avplayback.core.domain.ResponseState
import com.looke.avplayback.core.domain.ScreenState
import com.looke.avplayback.videos.data.repository.VideosRepository
import com.looke.avplayback.videos.domain.LookeVideo
import com.looke.avplayback.videos.domain.LookeVideos
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class VideosActivityViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()
    private val testDispatcher = TestCoroutineDispatcher()

    //region Observers' spies and mocks
    private val videosListStateObserver: Observer<ScreenState<List<LookeVideo>>> = spyk()
    private val loadingObserver: Observer<Boolean> = spyk()
    private val openVideoObserver: Observer<LookeVideo> = spyk()
    //endregion

    //region Other classes' spies and mocks
    private val mockVideosRepository: VideosRepository = mockk()
    //endregion

    private val sampleVideo = LookeVideo(
        name = "sampleName",
        videoURL = mockk(),
        imageURL = "https://image.com",
        audioURL = mockk()
    )
    private val sampleVideos = LookeVideos(listOf(sampleVideo, sampleVideo))

    private fun instantiateViewModel(): VideosActivityViewModel {
        return VideosActivityViewModel(mockVideosRepository).apply {
            videosListState.observeForever(videosListStateObserver)
            loading.observeForever(loadingObserver)
            openVideo.observeForever(openVideoObserver)
        }
    }

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
    }

    @Test
    fun `when user fetches videos then update videos list`() = runBlockingTest {
        coEvery {
            mockVideosRepository.fetchVideos()
        } returns ResponseState.Success(sampleVideos)

        val viewModel = instantiateViewModel()
        viewModel.fetchVideos()

        coVerifySequence {
            loadingObserver.onChanged(true)
            mockVideosRepository.fetchVideos()
            videosListStateObserver.onChanged(isNull(inverse = true))
            loadingObserver.onChanged(false)
        }
    }

    @Test
    fun `when user requested to open video then update openVideo`() = runBlockingTest {
        coEvery {
            mockVideosRepository.fetchVideos()
        } returns ResponseState.Success(sampleVideos)

        val viewModel = instantiateViewModel()
        val sampleVideosList = listOf(sampleVideo, sampleVideo)

        viewModel.videosListState.postValue(ScreenState.Success(sampleVideosList))
        viewModel.onUserRequestedToOpenVideo(0)

        coVerifySequence {
            videosListStateObserver.onChanged(isNull(inverse = true))
            openVideoObserver.onChanged(sampleVideosList[0])
        }
    }


}
