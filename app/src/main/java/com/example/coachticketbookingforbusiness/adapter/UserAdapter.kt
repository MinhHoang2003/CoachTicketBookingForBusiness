package com.example.coachticketbookingforbusiness.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coachticketbookingforbusiness.R
import com.example.coachticketbookingforbusiness.model.User
import kotlinx.android.synthetic.main.layout_user_item.view.*

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val mUsers = mutableListOf<User>()
    var onClickUserListener: ((id: String) -> Unit)? = null

    fun setData(users: List<User>) {
        mUsers.clear()
        mUsers.addAll(users)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(
        itemView: View,
        private val listener: ((id: String) -> Unit)? = null
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }

        fun bindView(user: User) {
            itemView.textPhoneNumber.text = String.format("Số điện thoại: %s", user.phoneNumber)
            itemView.textName.text = String.format("Tên : %s", user.name)
            itemView.textEmail.text = String.format("Email : %s", user.email)
            itemView.textAddress.text = String.format("Địa chỉ: %s", user.address)
        }

        override fun onClick(v: View?) {
            listener?.invoke(mUsers[adapterPosition].phoneNumber)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_user_item, parent, false)
        return UserViewHolder(view, onClickUserListener)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bindView(mUsers[position])
    }

    override fun getItemCount(): Int = mUsers.size

}