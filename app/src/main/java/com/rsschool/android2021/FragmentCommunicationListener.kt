package com.rsschool.android2021

interface FragmentCommunicationListener {
    fun onGenerateButtonClicked(min: Int, max: Int)

    fun onBackButtonClicked(result: Int)
}