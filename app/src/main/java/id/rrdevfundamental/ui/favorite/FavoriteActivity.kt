package id.rrdevfundamental.ui.favorite

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import id.rrdevfundamental.data.db.entitiy.UserEntity
import id.rrdevfundamental.databinding.ActivityFavoriteBinding
import id.rrdevfundamental.ui.adapter.FavoriteAdapter
import id.rrdevfundamental.ui.detail.DetailActivity
import id.rrdevfundamental.utils.OnItemClicked
import id.rrdevfundamental.utils.toUser
import org.koin.android.viewmodel.ext.android.viewModel

class FavoriteActivity : AppCompatActivity(), OnItemClicked {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: FavoriteAdapter
    private val viewModel: FavoritelViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initView()
        setupView()
    }

    override fun onResume() {
        super.onResume()

        viewModel.getUserAllDb().observe(this, {
            adapter.addList(it)
        })
    }

    private fun initView() {
        adapter = FavoriteAdapter(this)

        with(binding.rv){
            layoutManager = GridLayoutManager(this@FavoriteActivity, 1, androidx.recyclerview.widget.GridLayoutManager.VERTICAL, false)
            adapter = this@FavoriteActivity.adapter
        }
    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            return makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val swipedPosition = viewHolder.adapterPosition
            val user = adapter.getSwipedData(swipedPosition)
            viewModel.deleteUserDb(user)

            viewModel.getUserAllDb().observe(this@FavoriteActivity, {
                adapter.addList(it)
            })
        }
    })

    override fun onEventClick(data: UserEntity) {
        super.onEventClick(data)
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.USER, data.toUser())
        startActivity(intent)
    }

    private fun setupView() {

        with(binding) {
            backButton.setOnClickListener { onBackPressed() }
        }

        itemTouchHelper.attachToRecyclerView(binding.rv)

    }
}