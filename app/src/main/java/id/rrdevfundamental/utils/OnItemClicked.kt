package id.rrdevfundamental.utils

import id.rrdevfundamental.data.db.entitiy.UserEntity
import id.rrdevfundamental.data.network.response.User

interface OnItemClicked {
    fun onEventClick(data: User){}
    fun onEventClick(data: UserEntity){}
}