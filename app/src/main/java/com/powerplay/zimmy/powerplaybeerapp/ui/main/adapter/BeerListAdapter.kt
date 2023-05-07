package com.powerplay.zimmy.powerplaybeerapp.ui.main.adapter

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.powerplay.zimmy.powerplaybeerapp.databinding.ItemBeerListBinding
import com.powerplay.zimmy.powerplaybeerapp.model.RepositoriesModelItem


class BeerListAdapter(
    private val context: Context?,
    private val listener: BeerActionListener
) :
    ListAdapter<RepositoriesModelItem, RecyclerView.ViewHolder>(
        object : DiffUtil.ItemCallback<RepositoriesModelItem>() {
            override fun areItemsTheSame(
                oldItem: RepositoriesModelItem,
                newItem: RepositoriesModelItem
            ): Boolean {
                return newItem.id == oldItem.id
            }

            override fun areContentsTheSame(
                oldItem: RepositoriesModelItem,
                newItem: RepositoriesModelItem
            ): Boolean {
                return newItem == oldItem
            }
        }
    ) {

    inner class BeerItemViewHolder(private val binding: ItemBeerListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(beer: RepositoriesModelItem) {
            with(binding) {
                val message =
                    "Beer name: ${beer.name}\n\nTagline: ${beer.tagline}\n\nDescription: ${beer.description}"
                if (context != null) {
                    Glide.with(context).load(beer.imageUrl).into(beerIv)
                }
                beerNameTv.text = beer.name
                beerTagTv.text = beer.tagline
                whatsappIv.setOnClickListener {
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.type = "text/plain"
                    intent.setPackage("com.whatsapp")
                    intent.putExtra(
                        Intent.EXTRA_TEXT,
                        message
                    )
                    try {
                        context?.startActivity(intent)
                    } catch (ex: ActivityNotFoundException) {
                        Toast.makeText(
                            context,
                            "Please install whatsapp first.",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@setOnClickListener
                    }
                }
                root.setOnLongClickListener {
                    listener.openBottomSheetDialog(message)
                    return@setOnLongClickListener false
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BeerItemViewHolder(
            ItemBeerListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BeerItemViewHolder -> {
                holder.bind(currentList[position])
            }
        }

    }

    interface BeerActionListener {
        fun openBottomSheetDialog(description: String)
    }
}