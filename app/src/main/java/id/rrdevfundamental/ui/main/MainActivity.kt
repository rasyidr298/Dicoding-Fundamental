package id.rrdevfundamental.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import id.rrdevfundamental.data.adapter.UserAdapter
import id.rrdevfundamental.data.model.User
import id.rrdevfundamental.databinding.ActivityMainBinding
import id.rrdevfundamental.ui.detail.DetailActivity
import id.rrdevfundamental.utils.OnItemClicked

class MainActivity : AppCompatActivity(), View.OnClickListener, OnItemClicked {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UserAdapter
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initView()
        setupView()
    }

    private fun initView() {
        adapter = UserAdapter(this)
        viewModel = HomeViewModel()
        with(binding.rvHome){
            layoutManager = GridLayoutManager(this@MainActivity, 1, GridLayoutManager.VERTICAL, false)
            adapter = this@MainActivity.adapter
        }
    }

    private fun setupView() {
        val result = viewModel.getUserList(this)
        adapter.addList(result)
    }

    override fun onEventClick(data: User) {
        super.onEventClick(data)

        val intent = Intent(this,DetailActivity::class.java)
        intent.putExtra("users",data)
        startActivity(intent)
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}