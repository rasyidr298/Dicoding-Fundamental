package id.rrdevfundamental.ui.home

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import com.miguelcatalan.materialsearchview.MaterialSearchView
import id.rrdevfundamental.R
import id.rrdevfundamental.ui.adapter.UserAdapter
import id.rrdevfundamental.data.network.response.Status
import id.rrdevfundamental.data.network.response.User
import id.rrdevfundamental.databinding.ActivityHomeBinding
import id.rrdevfundamental.ui.detail.DetailActivity
import id.rrdevfundamental.ui.favorite.FavoriteActivity
import id.rrdevfundamental.utils.OnItemClicked
import id.rrdevfundamental.utils.hide
import id.rrdevfundamental.utils.show
import id.rrdevfundamental.utils.toast
import org.koin.android.viewmodel.ext.android.viewModel

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
        setupView()
        observeSearchQuery()
    }

    private fun initView() {
        adapter = UserAdapter(this)

        with(binding.rvMain){
            layoutManager = GridLayoutManager(this@HomeActivity, 1, GridLayoutManager.VERTICAL, false)
            adapter = this@HomeActivity.adapter
        }
    }

    private fun setupView() {

        with(binding) {
            progress.hide()
            lootie.setAnimation("search.json")
            lootie.playAnimation()
            lootie.loop(true)
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
                                with(binding) {
                                    progress.hide()
                                    lootie.hide()
                                }
                                adapter.addList(data.items)
                            }
                        }
                        Status.StatusType.ERROR -> {
                            with(binding) {
                                progress.hide()
                                lootie.show()
                                toast(it.message.toString())
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
        val itemSearch = menu.findItem(R.id.action_search)
        val itemSetting = menu.findItem(R.id.action_setting)
        val itemFavorite = menu.findItem(R.id.action_favorite)

        searchView.setMenuItem(itemSearch)
        itemFavorite.setOnMenuItemClickListener {
            startActivity(Intent(this, FavoriteActivity::class.java))
            true
        }

        itemSetting.setOnMenuItemClickListener {
            val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            try {
                startActivity(intent)
            }catch (e: ActivityNotFoundException) {
                Log.e("activity", e.message.toString())
            }
            true
        }
        return super.onCreateOptionsMenu(menu)
    }
}