package id.rrdevfundamental.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.rrdevfundamental.data.model.User
import id.rrdevfundamental.databinding.ActivityDetailBinding
import id.rrdevfundamental.utils.loadImage

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private var user: User? = null

    companion object {
        const val USER = "USER"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initView()
        setupView()
    }

    private fun initView() {
        user =  intent.extras?.getParcelable<User>(USER)
    }

    @SuppressLint("SetTextI18n")
    private fun setupView() {
        with(binding) {
            userDetailTvName.text = user?.name
            userDetailTvUsername.text = user?.username
            tvFollowers.text = user?.follower.toString()
            tvFollowing.text = user?.following.toString()
            tvCompany.text = user?.company
            tvLocation.text = user?.location
            tvRepository.text = user?.repository.toString()+" Repository"
            userDetailImgProfile.loadImage(user!!.avatar)
        }
    }
}