package id.rrdevfundamental.ui.main

import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.rrdevfundamental.data.model.User
import id.rrdevfundamental.utils.getDataFromJsonAsset

class HomeViewModel : ViewModel() {

    // Func : Get list User Object from file json
    fun getUserList(context: Context): List<User> {
        val jsonFileString = getDataFromJsonAsset(context, "github_user.json")
        val jsonType = object : TypeToken<List<User>>() {}.type

        return Gson().fromJson(jsonFileString, jsonType)
    }
}
