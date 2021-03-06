package com.example.sampleproject.ui.listaeventos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.sampleproject.R
import com.example.sampleproject.api.EventoResponse
import com.example.sampleproject.databinding.ItemEventoBinding
import java.text.DateFormat
import java.util.*


class EventosAdapter() :
    ListAdapter<EventoResponse, EventosAdapter.EventosViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventosViewHolder {
        return EventosViewHolder(
            ItemEventoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: EventosViewHolder, position: Int) =
        holder.bind(getItem(position))

    inner class EventosViewHolder(private val binding: ItemEventoBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(evento: EventoResponse) {
            binding.apply {
                textViewPrice.text = String.format("R$ %.2f", evento.price)
                textViewDate.text = evento.date
                Glide.with(itemView)
                    .load(evento.image)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_baseline_error_24)
                    .into(imageView)
            }
        }
    }


    class DiffCallback() : DiffUtil.ItemCallback<EventoResponse>() {
        override fun areItemsTheSame(oldItem: EventoResponse, newItem: EventoResponse) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: EventoResponse, newItem: EventoResponse) =
            oldItem == newItem
    }


}