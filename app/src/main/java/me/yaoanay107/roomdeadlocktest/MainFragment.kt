package me.yaoanay107.roomdeadlocktest

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.room.withTransaction
import com.dropbox.android.external.store4.get
import kotlinx.coroutines.async
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.launch
import me.yaoanay107.roomdeadlocktest.databinding.FragmentMainBinding
import me.yaoanay107.roomdeadlocktest.db.AppDatabase
import me.yaoanay107.roomdeadlocktest.store.ForumKey
import me.yaoanay107.roomdeadlocktest.store.ForumStore
import me.yaoanay107.roomdeadlocktest.store.ForumStoreProvider

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val appDatabase = AppDatabase.getInstance(requireContext())
        val forumStore: ForumStore = ForumStoreProvider.getInstance(appDatabase.forumDao())

        viewLifecycleOwner.lifecycleScope.launch {
            appDatabase.forumDao().deleteAll()
        }

        binding.button.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                // The transaction work fine if uncomment the line below
                // forumStore.get(ForumKey(id = "1"))
                appDatabase.withTransaction {
                    Log.d("TEST", "Start transaction")
                    forumStore.get(ForumKey(id = "1"))
                    Log.d("TEST", "End transaction")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}