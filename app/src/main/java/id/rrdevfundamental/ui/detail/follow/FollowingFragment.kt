package id.rrdevfundamental.ui.detail.follow

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import id.rrdevfundamental.ui.adapter.UserAdapter
import id.rrdevfundamental.data.network.response.Status
import id.rrdevfundamental.data.network.response.User
import id.rrdevfundamental.databinding.FollowFragmentBinding
import id.rrdevfundamental.ui.detail.DetailActivity
import id.rrdevfundamental.ui.detail.DetailViewModel
import id.rrdevfundamental.utils.OnItemClicked
import id.rrdevfundamental.utils.hide
import id.rrdevfundamental.utils.show
import id.rrdevfundamental.utils.toast
import org.koin.android.viewmodel.ext.android.viewModel

class FollowingFragment : Fragment(), OnItemClicked {

    private var followFragmentBinding: FollowFragmentBinding? = null
    private val binding get() = followFragmentBinding!!
    private val viewModel: DetailViewModel by viewModel()
    private lateinit var adapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        followFragmentBinding =  FollowFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initView()
        observeFollowing(DetailActivity.user!!.login)
    }

    private fun initView() {
        adapter = UserAdapter(this)
        with(binding.profileFollowRvUserFollow){
            layoutManager = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
            adapter = this@FollowingFragment.adapter
        }
    }

    private fun observeFollowing(username: String) {
        binding.apply {
            viewModel.getFollowing(username).observe(viewLifecycleOwner, {
                it?.let { status ->
                    when (status.status) {
                        Status.StatusType.START -> {
                            with(binding) {
                                profileFollowProgressBar.show()
                            }
                        }
                        Status.StatusType.SUCCESS -> {
                            adapter.addList(it.data!!)
                            with(binding) {
                                profileFollowProgressBar.hide()
                            }
                        }
                        Status.StatusType.ERROR -> {
                            context?.toast(it.message.toString())
                        }
                    }
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        followFragmentBinding = null
    }

}