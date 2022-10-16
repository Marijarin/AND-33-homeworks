package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_new_post.*
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.util.AndroidUtils
import ru.netology.nmedia.util.StringArg
import ru.netology.nmedia.viewmodel.PostViewModel

class NewPostFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentNewPostBinding.inflate(layoutInflater)


        val viewModel by viewModels<PostViewModel>(ownerProducer = ::requireParentFragment)

        viewModel.draftData.observe(viewLifecycleOwner){
        arguments?.textArg.let{binding.edit.setText(viewModel.draftData.value)}}

            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
               if (binding.edit.text.isNotBlank()){
                   viewModel.writeDraft(binding.edit.text.toString())
               }

                findNavController().navigateUp()
            }



        binding.edit.requestFocus()


        binding.ok.setOnClickListener {
            viewModel.changeContentAndSave(binding.edit.text.toString())
            AndroidUtils.hideKeyboard(requireView())
            findNavController().navigateUp()
        }
        return binding.root
    }

    companion object {
        var Bundle.textArg by StringArg
    }
}

