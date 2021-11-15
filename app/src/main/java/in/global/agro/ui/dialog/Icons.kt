package `in`.global.agro.ui.dialog

import `in`.global.agro.databinding.FragmentIconsBinding
import `in`.global.agro.ui.activity.alias.IconOneAlias
import `in`.global.agro.ui.activity.alias.IconTwoAlias
import android.content.ComponentName
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class Icons : CustomBottomSheet() {


    private lateinit var binding: FragmentIconsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIconsBinding.inflate(inflater, container, false)

        binding.iconOne.setOnClickListener {
            setIcon(0)
        }


        binding.iconTwo.setOnClickListener {
            setIcon(1)
        }


        return binding.root
    }


    private fun setIcon(position: Int) {
        requireContext().packageManager.setComponentEnabledSetting(
            ComponentName(
                requireContext(),
                IconOneAlias::class.java
            ), getStatusFromPosition(position == 0), PackageManager.DONT_KILL_APP
        )

        requireContext().packageManager.setComponentEnabledSetting(
            ComponentName(
                requireContext(),
                IconTwoAlias::class.java
            ),
            getStatusFromPosition(position == 1), PackageManager.DONT_KILL_APP
        )
    }


    private fun getStatusFromPosition(position: Boolean): Int {
        return if (position) {
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED
        } else {
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED
        }
    }



}