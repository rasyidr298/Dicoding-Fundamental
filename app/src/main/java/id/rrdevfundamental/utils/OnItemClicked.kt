package id.rrdevfundamental.utils

import id.rrdevfundamental.data.response.User

interface OnItemClicked {
    fun onEventClick(data: User){}
}