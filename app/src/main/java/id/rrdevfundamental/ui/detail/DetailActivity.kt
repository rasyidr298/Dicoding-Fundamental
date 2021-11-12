package id.rrdevfundamental.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import id.rrdevfundamental.R
import id.rrdevfundamental.data.network.response.DetailResponse
import id.rrdevfundamental.data.network.response.Status
import id.rrdevfundamental.data.network.response.User
import id.rrdevfundamental.databinding.ActivityDetailBinding
import id.rrdevfundamental.ui.adapter.SectionsPagerAdapter
import id.rrdevfundamental.utils.hide
import id.rrdevfundamental.utils.loadImage
import id.rrdevfundamental.utils.show
import org.koin.android.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModel()
    private var userDetail: DetailResponse? = null

    companion object {
        const val USER = "USER"
        var user: User? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initView()
        setupView()
    }

    override fun onResume() {
        super.onResume()

        observeDetail(user!!.login)
        observeIsSaved()
    }

    private fun initView() {
        user = intent.extras?.getParcelable(USER)

        with(binding) {
            val sectionsPagerAdapter = SectionsPagerAdapter(this@DetailActivity, supportFragmentManager)
            profileViewPager.adapter = sectionsPagerAdapter
            profileTabLayout.setupWithViewPager(binding.profileViewPager)
        }
    }

    private fun setupView() {

        with(binding) {
            profileBtnBack.setOnClickListener {
                onBackPressed()
            }

            favoriteButton.setOnClickListener {
                viewModel.isSaved(user!!.login).observe(this@DetailActivity, {
                    if (it) {
                        viewModel.deleteUserDb(userDetail!!)
                        binding.favoriteButton.setImageDrawable(
                            ContextCompat.getDrawable(
                                this@DetailActivity,
                                R.drawable.ic_favorite_border
                            )
                        )
                    } else {
                        viewModel.insertDb(userDetail!!)
                        binding.favoriteButton.setImageDrawable(
                            ContextCompat.getDrawable(
                                this@DetailActivity,
                                R.drawable.ic_favorite
                            )
                        )
                    }
                })
            }
        }
    }

    private fun observeDetail(username: String) {
        binding.apply {
            viewModel.getDetail(username).observe(this@DetailActivity, {
                    it?.let { status ->
                        when (status.status) {
                            Status.StatusType.START -> {
                                with(binding) {
                                    lootie.hide()
                                    profileProgressBar.show()
                                    favoriteButton.hide()
                                }
                            }
                            Status.StatusType.SUCCESS -> {
                                userDetail = it.data
                                with(binding) {
                                    profileProgressBar.hide()
                                    lootie.hide()
                                    favoriteButton.show()

                                    with(it.data) {
                                        profileImgProfile.loadImage(this?.avatar_url!!)
                                        profileTvName.text = this.name
                                        profileTvUsername.text = this.login
                                        profileTvCompany.text = this.company
                                        profileTvLocation.text = this.location
                                    }
                                }
                            }
                            Status.StatusType.ERROR -> {
                                with(binding) {
                                    favoriteButton.hide()
                                    profileProgressBar.hide()
                                    lootie.setAnimation("empty.json")
                                    lootie.playAnimation()
                                    lootie.loop(true)
                                }
                            }
                        }
                    }
                })
        }
    }

    private fun observeIsSaved() {
        viewModel.isSaved(user!!.login).observe(this, {
            if (it) {
                binding.favoriteButton.setImageDrawable(
                    ContextCompat.getDrawable(
                        this@DetailActivity,
                        R.drawable.ic_favorite
                    )
                )
            } else {
                binding.favoriteButton.setImageDrawable(
                    ContextCompat.getDrawable(
                        this@DetailActivity,
                        R.drawable.ic_favorite_border
                    )
                )
            }
        })
    }
}