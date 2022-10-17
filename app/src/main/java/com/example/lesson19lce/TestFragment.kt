package com.example.lesson19lce

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.lesson19lce.databinding.FragmentTestBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class TestFragment : Fragment() {
    private var _binding: FragmentTestBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel by viewModels<TestViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentTestBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel
            .lceFlow
            .onEach { lce ->
                when (lce) {
                    Lce.Loading -> {
                        binding.progress.isVisible = true
                    }
                    is Lce.Content -> {
                        binding.progress.isVisible = false
                        binding.textResult.isVisible = true
                        binding.textResult.text = lce.data
                    }
                    is Lce.Error -> {
                        Toast.makeText(requireContext(), lce.throwable.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        binding.button.setOnClickListener {
            viewModel.onButtonClicked()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}