package id.rrdevfundamental.ui.detail.follow

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import id.rrdevfundamental.data.adapter.UserAdapter
import id.rrdevfundamental.data.response.Status
import id.rrdevfundamental.data.response.User
import id.rrdevfundamental.databinding.FollowFragmentBinding
import id.rrdevfundamental.ui.detail.DetailActivity
import id.rrdevfundamental.ui.detail.DetailViewModel
import id.rrdevfundamental.utils.OnItemClicked
import id.rrdevfundamental.utils.hide
import id.rrdevfundamental.utils.show
import id.rrdevfundamental.utils.toast
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.follow_fragment.*
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

    override fun onEventClick(data: User) {
        super.onEventClick(data)

        activity?.let{
            val intent = Intent (it, DetailActivity::class.java)
            intent.putExtra(DetailActivity.USER, data)
            it.startActivity(intent)
        }
    }

    private fun observeFollowing(username: String) {
        binding.apply {
            viewModel.getFollowing(username).observe(viewLifecycleOwner, Observer {
                it?.let { status ->
                    when (status.status) {
                        Status.StatusType.START -> {
                            with(binding) {
                                profileFollow_progressBar.show()
                            }
                        }
                        Status.StatusType.SUCCESS -> {
                            adapter.addList(it.data!!)
                            with(binding) {
                                profileFollow_progressBar.hide()
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