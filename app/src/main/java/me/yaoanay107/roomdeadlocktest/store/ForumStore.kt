package me.yaoanay107.roomdeadlocktest.store

import android.util.Log
import com.dropbox.android.external.store4.Fetcher
import com.dropbox.android.external.store4.SourceOfTruth
import com.dropbox.android.external.store4.Store
import com.dropbox.android.external.store4.StoreBuilder
import me.yaoanay107.roomdeadlocktest.db.ForumDao
import me.yaoanay107.roomdeadlocktest.db.ForumEntity

data class ForumKey(val id: String)
typealias ForumStore = Store<ForumKey, ForumEntity>

class ForumStoreProvider {

    companion object {
        @Volatile private var instance: ForumStore? = null

        fun getInstance(forumDao: ForumDao): ForumStore {
            return instance ?: synchronized(this) {
                instance ?: buildStore(forumDao)
                    .also { instance = it }
            }
        }

        private fun buildStore(forumDao: ForumDao): ForumStore {
            return StoreBuilder
                .from(
                    fetcher = Fetcher.of { key: ForumKey ->
                        Log.d("TEST", "Fetch forum ${key.id}")
                        ForumEntity(id = key.id)
                    },
                    sourceOfTruth = SourceOfTruth.of(
                        writer = { _, forum ->
                            Log.d("TEST", "Write forum ${forum.id}")
                            forumDao.addForum(forum)
                        },
                        nonFlowReader = { key ->
                            Log.d("TEST", "Read forum ${key.id}")
                            forumDao.getForum(key.id)
                        }
                    )
                )
                .build()
        }
    }
}
