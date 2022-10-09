package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_post.view.*
import kotlinx.android.synthetic.main.fragment_post.*
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.NewPostFragment.Companion.textArg
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostViewHolder
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.FragmentPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel

class PostFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentPostBinding.inflate(layoutInflater)

        val viewModel by viewModels<PostViewModel>(ownerProducer = ::requireParentFragment)

        val viewHolder = PostViewHolder(binding.postLayout, object: OnInteractionListener{
            override fun onEdit(post: Post) {
                viewModel.edit(post)

            }

            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onShare(post: Post) {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type = "text/plain"
                }

                val shareIntent =
                    Intent.createChooser(intent, getString(R.string.chooser_share_post))
                startActivity(shareIntent)
                viewModel.addShareByClick(post.id)
            }

            override fun onRemove(post: Post) {

                viewModel.removeById(post.id)
            }

            override fun onYoutube(post: Post) {
                if (post.video != null) {
                    val intent = Intent().apply {
                        action = Intent.ACTION_VIEW
                        data = Uri.parse(post.video)
                    }
                    startActivity(intent)
                }
            }

            override fun onPost(post: Post) {
                viewModel.clickById(post.id)
                findNavController().navigateUp()
            }

        })
        viewModel.edited.observe(viewLifecycleOwner) { post ->
            if (post.id == 0L) {
                return@observe
            }
            findNavController()
                .navigate(R.id.action_postFragment_to_newPostFragment, Bundle().apply {
                    textArg = post.content
                })

        }

        viewModel.data.observe(viewLifecycleOwner) { posts ->
            val post = posts.find{ it.isClicked} ?: run {
                findNavController().navigateUp()
                return@observe
            }
            viewHolder.bind(post)
        }



        return binding.root
    }
}