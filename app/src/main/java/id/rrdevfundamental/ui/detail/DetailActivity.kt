package id.rrdevfundamental.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import id.rrdevfundamental.data.adapter.SectionsPagerAdapter
import id.rrdevfundamental.data.response.Status
import id.rrdevfundamental.data.response.User
import id.rrdevfundamental.databinding.ActivityDetailBinding
import id.rrdevfundamental.utils.hide
import id.rrdevfundamental.utils.loadImage
import id.rrdevfundamental.utils.show
import id.rrdevfundamental.viewModelModule
import kotlinx.android.synthetic.main.activity_detail.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModel()

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

    private fun initView() {
        user = intent.extras?.getParcelable(USER)

        with(binding) {
            val sectionsPagerAdapter = SectionsPagerAdapter(this@DetailActivity, supportFragmentManager)
            profileViewPager.adapter = sectionsPagerAdapter
            profileTabLayout.setupWithViewPager(binding.profileViewPager)
        }
    }

    private fun setupView() {
        observeDetail(user!!.login)

        with(binding) {
            profile_btn_back.setOnClickListener {
                onBackPressed()
            }
        }
    }

    private fun observeDetail(username: String) {
        binding.apply {
            viewModel.getDetail(username).observe(this@DetailActivity, Observer {
                    it?.let { status ->
                        when (status.status) {
                            Status.StatusType.START -> {
                                with(binding) {
                                    lootie.hide()
                                    profileProgressBar.show()
                                }
                            }
                            Status.StatusType.SUCCESS -> {
                                with(binding) {
                                    profileProgressBar.hide()
                                    lootie.hide()

                                    with(it.data) {
                                        profile_img_profile.loadImage(this?.avatar_url!!)
                                        profile_tv_name.text = this.name
                                        profile_tv_username.text = this.login
                                        profile_tv_company.text = this.company
                                        profile_tv_location.text = this.company
                                    }
                                }
                            }
                            Status.StatusType.ERROR -> {
                                with(binding) {
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
}