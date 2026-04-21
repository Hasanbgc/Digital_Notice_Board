package home

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.delay

class PosterPagingSource(val type:Int): PagingSource<Int, Poster.Normal>() {

    init {
        if (type == SAVED) {
            setOnSavedListChangedListener {
                invalidate()
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Poster.Normal>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Poster.Normal> {
        return try {
            val page = params.key ?: 1
            val pageSize = params.loadSize

            delay(1000)

            val data = when(type){
                FOR_YOU -> generateDummyNotices(page,pageSize)
                NEARBY -> generateDummyNotices(page,pageSize)
                else -> {
                    val allSaved = getSavedNotes()
                    val start = (page - 1) * pageSize
                    if (start < allSaved.size) {
                        allSaved.drop(start).take(pageSize)
                    } else {
                        emptyList()
                    }
                }
            }


            LoadResult.Page(
                data = data,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (data.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object{
        const val FOR_YOU = 1
        const val NEARBY = 2
        const val SAVED = 3
    }

}
