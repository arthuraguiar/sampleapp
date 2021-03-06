package com.example.sampleproject.ui.listaeventos

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.sampleproject.R
import com.example.sampleproject.api.EventoResponse
import com.example.sampleproject.databinding.ItemEventoBinding
import com.example.sampleproject.utils.formatToDate

class EventosAdapter(val eventoOnClickListener: EventoOnClickListener) :
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

        init {
            binding.apply {
                root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        eventoOnClickListener.onItemClick(getItem(position), this.imageView)
                    }
                }
            }
        }

        fun bind(evento: EventoResponse) {
            binding.apply {
                textViewTitle.text = evento.title

                textViewPrice.text = String.format("R$ %.2f", evento.price)

                textViewDate.text = evento.date.formatToDate()

                imageView.apply {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        transitionName = evento.title
                    }
                    Glide.with(itemView)
                        .load(evento.image)
                        .error(R.drawable.ic_baseline_error_24)
                        .into(this)
                }
            }
        }
    }


    class DiffCallback() : DiffUtil.ItemCallback<EventoResponse>() {
        override fun areItemsTheSame(oldItem: EventoResponse, newItem: EventoResponse) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: EventoResponse, newItem: EventoResponse) =
            oldItem == newItem
    }

    interface EventoOnClickListener {
        fun onItemClick(evento: EventoResponse, imageView: ImageView)
    }

}