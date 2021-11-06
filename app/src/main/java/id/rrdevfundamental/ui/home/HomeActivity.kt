package id.rrdevfundamental.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import com.miguelcatalan.materialsearchview.MaterialSearchView
import id.rrdevfundamental.R
import id.rrdevfundamental.data.adapter.UserAdapter
import id.rrdevfundamental.data.response.Status
import id.rrdevfundamental.data.response.User
import id.rrdevfundamental.databinding.ActivityHomeBinding
import id.rrdevfundamental.ui.detail.DetailActivity
import id.rrdevfundamental.utils.OnItemClicked
import id.rrdevfundamental.utils.hide
import id.rrdevfundamental.utils.show
import id.rrdevfundamental.viewModelModule
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class HomeActivity : AppCompatActivity(), OnItemClicked {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var adapter: UserAdapter
    private val viewModel: HomeViewModel by viewModel()
    private lateinit var searchView: MaterialSearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupToolbar()
        initView()
        observeSearchQuery()
    }

    private fun initView() {
        adapter = UserAdapter(this)

        with(binding) {
            progress.hide()
            lootie.setAnimation("search.json")
            lootie.playAnimation()
            lootie.loop(true)
        }

        with(binding.rvMain){
            layoutManager = GridLayoutManager(this@HomeActivity, 1, GridLayoutManager.VERTICAL, false)
            adapter = this@HomeActivity.adapter
        }
    }

    private fun observeUserSearch(keyword: String) {
        viewModel.getUserSearch(keyword).observe(this, {
                it?.let { status ->
                    when (status.status) {
                        Status.StatusType.START -> {
                            with(binding) {
                                lootie.hide()
                                progress.show()
                            }
                        }
                        Status.StatusType.SUCCESS -> {
                            it.data?.let { data ->
                                progress.hide()
                                lootie.hide()
                                adapter.addList(data.items)
                            }
                        }
                        Status.StatusType.ERROR -> {
                            with(binding) {
                                progress.hide()
                                lootie.setAnimation("empty.json")
                                lootie.playAnimation()
                                lootie.loop(true)
                            }
                        }
                    }
                }
            }
        )
    }

    override fun onEventClick(data: User) {
        super.onEventClick(data)

        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.USER, data)
        startActivity(intent)
    }

    private fun observeSearchQuery() {
        searchView.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    observeUserSearch(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    private fun setupToolbar() {
        val toolbar: Toolbar = this.findViewById<View>(R.id.toolbar) as Toolbar
        (this as AppCompatActivity?)?.setSupportActionBar(toolbar)
        searchView = this.findViewById(R.id.search_view)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val item = menu.findItem(R.id.action_search)
        searchView.setMenuItem(item)
        return super.onCreateOptionsMenu(menu)
    }
}