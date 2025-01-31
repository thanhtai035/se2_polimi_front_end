package com.se2.base.ext

import com.tyme.base.Common.FragmentEnum

interface FragmentListener {
    fun onNavigate(content: FragmentEnum)

    fun getUserID(): Int

    fun getUserRole(): Int
}

