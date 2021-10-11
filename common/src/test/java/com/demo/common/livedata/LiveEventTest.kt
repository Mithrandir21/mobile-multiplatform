package com.demo.common.livedata

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class LiveEventTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var liveEvent: LiveEvent<String>
    private lateinit var owner: TestLifecycleOwner
    private lateinit var observer: Observer<String>

    @Before
    fun setup() {
        liveEvent = LiveEvent()
        owner = TestLifecycleOwner()
        observer = mock()
    }

    @Test
    fun observe() {

        // Given
        owner.start()
        liveEvent.observe(owner, observer)

        val event = "event"

        // When
        liveEvent.postValue(event)

        // Then
        verify(observer, times(1)).onChanged(event)
    }

    @Test
    fun `observe after start`() {

        // Given
        owner.create()
        liveEvent.observe(owner, observer)

        val event = "event"

        // When
        liveEvent.postValue(event)

        // Then
        verify(observer, never()).onChanged(event)

        // When
        owner.start()

        // Then
        verify(observer, times(1)).onChanged(event)
    }

    @Test
    fun `observe after stop`() {

        // Given
        owner.stop()
        liveEvent.observe(owner, observer)

        val event = "event"

        // When
        liveEvent.postValue(event)

        // Then
        verify(observer, never()).onChanged(event)
    }

    @Test
    fun `observe after start again`() {

        // Given
        owner.stop()
        liveEvent.observe(owner, observer)

        val event = "event"

        // When
        liveEvent.postValue(event)

        // Then
        verify(observer, never()).onChanged(event)

        // When
        owner.start()

        // Then
        verify(observer, times(1)).onChanged(event)
    }

    @Test
    fun `observe after start second time`() {

        // Given
        owner.stop()
        liveEvent.observe(owner, observer)

        val event = "event"

        // When
        liveEvent.postValue(event)

        // Then
        verify(observer, never()).onChanged(event)

        // When
        owner.start()

        // Then
        verify(observer, times(1)).onChanged(event)

        // When
        owner.stop()
        owner.start()

        // Then
        verify(observer, times(1)).onChanged(event)
    }

    @Test
    fun `observe after start second time again`() {

        // Given
        owner.stop()
        liveEvent.observe(owner, observer)

        val event = "event"

        // When
        liveEvent.postValue(event)

        // Then
        verify(observer, never()).onChanged(event)

        // When
        owner.start()

        // Then
        verify(observer, times(1)).onChanged(event)

        // When
        owner.stop()
        owner.start()

        // Then
        verify(observer, times(1)).onChanged(event)

        // When
        liveEvent.postValue(event)

        // Then
        verify(observer, times(2)).onChanged(event)
    }

    @Test
    fun `observe after one observation`() {

        // Given
        owner.start()
        liveEvent.observe(owner, observer)

        val event = "event"

        // When
        liveEvent.postValue(event)

        // Then
        verify(observer, times(1)).onChanged(event)

        // When
        owner.stop()

        // Then
        verify(observer, times(1)).onChanged(event)

        // When
        owner.start()

        // Then
        verify(observer, times(1)).onChanged(event)
    }

    @Test
    fun `observe after one observation multi owner`() {

        // Given
        owner.start()
        liveEvent.observe(owner, observer)
        val owner1 = TestLifecycleOwner()
        val observer1 = mock<Observer<String>>()
        owner1.start()

        val event = "event"

        // When
        liveEvent.postValue(event)

        // Then
        verify(observer, times(1)).onChanged(event)

        // Given
        liveEvent.observe(owner1, observer1)

        // Then
        verify(observer1, never()).onChanged(event)

        // When
        liveEvent.postValue(event)

        // Then
        verify(observer, times(2)).onChanged(event)
        verify(observer1, times(1)).onChanged(event)
    }

    @Test
    fun `observe after one observation with new owner`() {

        // Given
        owner.start()
        liveEvent.observe(owner, observer)

        val event = "event"

        // When
        liveEvent.postValue(event)

        // Then
        verify(observer, times(1)).onChanged(event)

        // When
        owner.destroy()

        // Then
        verify(observer, times(1)).onChanged(event)

        // When
        owner = TestLifecycleOwner()
        observer = mock()
        liveEvent.observe(owner, observer)
        owner.start()

        // Then
        verify(observer, never()).onChanged(event)
    }

    @Test
    fun `observe after one observation with new owner after start`() {

        // Given
        owner.start()
        liveEvent.observe(owner, observer)

        val event = "event"

        // When
        liveEvent.postValue(event)

        // Then
        verify(observer, times(1)).onChanged(event)

        // When
        owner.destroy()

        // Then
        verify(observer, times(1)).onChanged(event)

        // When
        owner = TestLifecycleOwner()
        observer = mock()
        owner.start()
        liveEvent.observe(owner, observer)

        // Then
        verify(observer, never()).onChanged(event)
    }

    @Test
    fun `observe after remove`() {

        // Given
        owner.start()
        liveEvent.observe(owner, observer)

        val event = "event"

        // When
        liveEvent.postValue(event)

        // Then
        verify(observer, times(1)).onChanged(event)

        // When
        liveEvent.removeObserver(observer)
        liveEvent.postValue(event)

        // Then
        verify(observer, times(1)).onChanged(event)
    }

    @Test
    fun `observe after remove observer before emit`() {

        // Given
        owner.start()
        liveEvent.observe(owner, observer)
        liveEvent.removeObserver(observer)

        val event = "event"

        // When
        liveEvent.postValue(event)

        // Then
        verify(observer, never()).onChanged(event)
    }

    @Test
    fun `observe after remove owner`() {

        // Given
        owner.start()
        liveEvent.observe(owner, observer)

        val event = "event"

        // When
        liveEvent.postValue(event)

        // Then
        verify(observer, times(1)).onChanged(event)

        // When
        liveEvent.removeObservers(owner)
        liveEvent.postValue(event)

        // Then
        verify(observer, times(1)).onChanged(event)
    }

    @Test
    fun `observe after remove owner before emit`() {

        // Given
        owner.start()
        liveEvent.observe(owner, observer)
        liveEvent.removeObservers(owner)

        val event = "event"

        // When
        liveEvent.postValue(event)

        // Then
        verify(observer, never()).onChanged(event)
    }

    @Test
    fun `observe after remove multi owner`() {

        // Given
        owner.start()
        liveEvent.observe(owner, observer)
        val owner1 = TestLifecycleOwner()
        val observer1 = mock<Observer<String>>()
        owner1.start()

        val event = "event"

        // When
        liveEvent.postValue(event)

        // Then
        verify(observer, times(1)).onChanged(event)
        verify(observer1, never()).onChanged(event)

        // When
        liveEvent.observe(owner1, observer1)
        liveEvent.removeObserver(observer)
        liveEvent.postValue(event)

        // Then
        verify(observer, times(1)).onChanged(event)
        verify(observer1, times(1)).onChanged(event)
    }

    @Test
    fun `observe after remove owner multi owner`() {

        // Given
        owner.start()
        liveEvent.observe(owner, observer)
        val owner1 = TestLifecycleOwner()
        val observer1 = mock<Observer<String>>()
        owner1.start()

        val event = "event"

        // When
        liveEvent.postValue(event)

        // Then
        verify(observer, times(1)).onChanged(event)
        verify(observer1, never()).onChanged(event)

        // When
        liveEvent.observe(owner1, observer1)
        liveEvent.removeObservers(owner)
        liveEvent.postValue(event)

        // Then
        verify(observer, times(1)).onChanged(event)
        verify(observer1, times(1)).onChanged(event)
    }

    @Test
    fun `observe forever`() {

        // Given
        liveEvent.observeForever(observer)

        val event = "event"

        // When
        liveEvent.postValue(event)

        // Then
        verify(observer, times(1)).onChanged(event)

        // When
        owner.start()

        // Then
        verify(observer, times(1)).onChanged(event)

        // When
        owner.stop()

        // Then
        verify(observer, times(1)).onChanged(event)
    }
}