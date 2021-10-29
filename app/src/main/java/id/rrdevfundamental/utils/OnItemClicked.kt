package id.rrdevfundamental.utils

import id.rrdevfundamental.data.model.User

interface OnItemClicked {
    fun onEventClick(data: User){}
}