package home

import androidx.paging.PagingSource
import androidx.paging.PagingState

class PosterPagingSource(val type:Int): PagingSource<Int, Poster.Normal>() {

    init {
        if (type == SAVED) {
            setOnSavedListChangedListener {
                invalidate()
            }
        }
        if (type == FOR_YOU) {
            setOnMyNoticesChangedListener {
                invalidate()
            }
        }
        if (type == NEARBY) {
            setOnNearbyNoticesChangedListener {
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

            when (type) {
                FOR_YOU -> LoadResult.Page(
                    data = if (page == 1) myNotices.toList() else emptyList(),
                    prevKey = null,
                    nextKey = null
                )
                NEARBY -> LoadResult.Page(
                    data = if (page == 1) nearbyNotices.toList() else emptyList(),
                    prevKey = null,
                    nextKey = null
                )
                else -> LoadResult.Page(
                    data = if (page == 1) savedNotices.toList() else emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }
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
