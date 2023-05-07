package com.powerplay.zimmy.powerplaybeerapp.util

sealed class ListNotifier {
    object NotifyDataChanged: ListNotifier()
}
