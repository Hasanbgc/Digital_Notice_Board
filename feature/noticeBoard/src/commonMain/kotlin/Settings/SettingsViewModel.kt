package Settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.transform

class SettingsViewModel: ViewModel() {

    var isDarkMode = false

    /**
     * basic example of flow
     */
    fun basicFlowExample() = flow{
        println("flow started")
        for (i in 1..5) {
            delay(1000)
            emit(i)
        }
        println("flow completed")
    }

    /**flow doesn't start until its collected by a collector*/
    //
    suspend fun coldFlowDemo(){
        val flo = flow{
            println("flow started")
            emit(1)
        }
        println("flow created, but not started")
        delay(1000)
        println("flow now collecting")
        flo.collect {
            println("flow_Received value: $it")
        }
    }

    /** flow builder  */
    fun flowBuilder() = flow{
        emit(1)
        emit(2)
        emit(3)
    }

    fun flowOfExample() = flowOf(1,2,3)

    fun asFlowExample() = listOf<Int>(1,2,3).asFlow()

    /**
     * ═══════════════════════════════════════════════════════════════
     * PART 2: FLOW OPERATORS - Transformation & Processing
     * ═══════════════════════════════════════════════════════════════
     */

    /** 1. MAP - Transform each value */
    fun mapExample( ) =flow{
        emit(1)
        delay(1000)
        emit(2)
        delay(1000)
        emit(3)
    }.map { value ->
        value *2
    }

    fun filterExample()=flow {
        emit(1)
        emit(2)
        emit(3)
        emit(4)
        emit(5)
    }.filter { value ->
        value % 2 == 1
    }

    fun transformExample() =flow{
        emit(1)
        emit(2)
        emit(3)
        emit(4)
        emit(5)
    }.transform {
        emit(it)
        emit(it*2)
    }

    fun takeExample() = flow {
        repeat(10) {
            emit(it)
        }
    }.drop(5)  //drops first five
        .take(3) // after dropping first 5 takes first three in the flow

    // 6. DISTINCT UNTIL CHANGED - Remove consecutive duplicates
    fun distinctUntilChangedExample() = flow {
        emit(1)
        emit(1)
        emit(2)
        emit(2)
        emit(1)
    }.distinctUntilChanged()

    fun mergeExample() = merge(
        flowOf(1, 2, 3),
        flowOf(4, 5, 6)
    )

    // 2. COMBINE - Combine latest values from two flows
    fun combineExample() = flow {
        emit(1)
        delay(100)
        emit(2)
        delay(100)
        emit(3)
    }.combine(
            flow {
                emit(4)
                delay(50)
                emit(5)
                delay(100)
                emit(6)
            }
        ) { a, b ->
            "$a + $b"
        }

    fun flatMapConcatExample() = flowOf(1,2,3,4)
        .flatMapConcat { value ->
            flow{
                emit(value)
                delay(2000)
                emit(value*2)
            }
        }

    /**
     * ═══════════════════════════════════════════════════════════════
     * PART 6: FLOW LIFECYCLE - Handling Events
     * ═══════════════════════════════════════════════════════════════
     */

// 1. ON START - Execute before flow starts
    fun onStartExample() = flowOf(1, 2, 3)
        .onStart {
            println("Flow starting")
            emit(0) // Can emit additional values
        }

    fun onCompleteExample() = flowOf(1,2,3,4)
        .onCompletion { exception ->
           /* if (exception != null) {
                println("Flow completed with exception: $exception")
            } else {
                println("Flow completed successfully")
            }*/

        }

    fun catchExceptionExample() = flow {
        emit(1)
        emit(2)
        throw RuntimeException("Exception occurred")
        //emit(3)
    }.catch { exception ->
        println("Caught exception: $exception")
    }


    /**
     * ═══════════════════════════════════════════════════════════════
     * PART 7: SHARED FLOW & STATE FLOW - Hot Flows
     * ═══════════════════════════════════════════════════════════════
     */

    // 3. SHARE IN - Convert cold flow to hot SharedFlow

    fun shareInExample(scope: CoroutineScope) = flow {
        repeat(10){
            emit(it)
            delay(1000)
        }
    }.shareIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        replay = 2
    )

}