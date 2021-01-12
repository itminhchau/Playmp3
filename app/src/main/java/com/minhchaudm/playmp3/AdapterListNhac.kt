package com.minhchaudm.playmp3

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class AdapterListNhac (var context: Context, var listnhac: ArrayList<BaiNhac>): BaseAdapter() {

    class ViewHolder(row: View) {
        var textviewbaihat: TextView
        var imageviewbaihat: ImageView

        init {
            textviewbaihat = row.findViewById(R.id.txtitem) as TextView
            imageviewbaihat = row.findViewById(R.id.imageitem) as ImageView
        }
    }


    @SuppressLint("ResourceAsColor")
    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        var view: View
        var viewholder: ViewHolder
        if (convertView == null) {
            var layoutInflater: LayoutInflater = LayoutInflater.from(context)
            view = layoutInflater.inflate(R.layout.dong_bainhac, null)
            viewholder = ViewHolder(view)
            view.tag = viewholder
        } else {
            view = convertView
            viewholder = convertView.tag as ViewHolder
        }

        var nhac: BaiNhac = getItem(position) as BaiNhac
        viewholder.textviewbaihat.text = nhac.ten
        viewholder.imageviewbaihat.setImageResource(nhac.hinhbn)

        return view
    }


    override fun getItem(position: Int): Any {
        return listnhac.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return listnhac.size;
    }
}