package com.dicoding.moviecatalogue.comment.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.moviecatalogue.comment.R
import com.dicoding.moviecatalogue.comment.databinding.ActivityCommentBinding
import com.dicoding.moviecatalogue.comment.databinding.CommentDialogBinding
import com.dicoding.moviecatalogue.comment.di.commentModule
import com.dicoding.moviecatalogue.core.domain.model.Comment
import com.dicoding.moviecatalogue.ui.detail.DetailFilmActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import java.text.DateFormat
import java.util.*

class CommentActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityCommentBinding
    private val viewModel: CommentViewModel by viewModel()
    private val adapter = CommentAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadKoinModules(commentModule)

        binding.rvComment.adapter = adapter
        binding.rvComment.layoutManager = LinearLayoutManager(this)
        binding.fabAdd.setOnClickListener(this)

        val filmId = intent.getStringExtra(DetailFilmActivity.EXTRA_FILM)
        val filmType = intent.getStringExtra(DetailFilmActivity.EXTRA_TYPE)

        viewModel.filmId = filmId.toString()
        viewModel.filmType = filmType.toString()
        getComment()
        supportActionBar?.title = getString(R.string.title_comment)
    }

    private fun getComment() {
        viewModel.getComment().observe(this, { data ->
            binding.progressBar.visibility = View.GONE
            if (data.isNotEmpty()){
                adapter.list = data
                binding.rvComment.visibility = View.VISIBLE
                binding.tvEmpty.visibility = View.GONE
            }else {
                binding.tvEmpty.visibility = View.VISIBLE
                binding.rvComment.visibility = View.GONE
            }
        })
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.fab_add -> {
                showCommentDialog()
            }
        }
    }

    private fun showCommentDialog() {
        val binding = CommentDialogBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add Comment")
        builder.setCancelable(true)
        builder.setView(binding.root)
        builder.setPositiveButton("Send"
        ) { _, _ ->
            val date =
                DateFormat.getDateInstance(DateFormat.LONG).format(Calendar.getInstance().time)
            val message = binding.etComment.text.toString()

            val comment = Comment(
                film_id = viewModel.filmId,
                film_type = viewModel.filmType,
                comment = message,
                date = date
            )
            viewModel.insertComment(comment)
        }
        builder.setNegativeButton("Back"
        ) { dialog, _ -> dialog?.dismiss() }

        val dialog = builder.create()
        dialog.show()
    }
}