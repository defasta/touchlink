package apps.eduraya.edurayaapp.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import java.lang.IllegalArgumentException

abstract class BaseFragment <VB: ViewBinding>(
    private val bindingInflater: (inflater: LayoutInflater) -> VB
): Fragment() {

    private var _binding: VB? = null

    val binding: VB
        get() = _binding as VB

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater)
        if (_binding == null)
            throw IllegalArgumentException("Binding cant be null")
        // Inflate the layout for this fragment
        return binding.root
    }

}