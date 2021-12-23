package com.qgstudio.anyworkc.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.AbstractExpandableItem
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.qgstudio.anyworkc.App
import com.qgstudio.anyworkc.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.google.gson.Gson
import com.qgstudio.anyworkc.grade.GradeContract
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception
import java.util.*


/**
 * Created by HeYanLe on 2021/11/2 20:22.
 * https://github.com/heyanLE
 */
class StatusActivity: AppCompatActivity() {

    companion object{
        fun start(context: Context){
            context.startActivity(Intent(context, StatusActivity::class.java))
        }
    }

    val list = arrayListOf<MultiItemEntity>()
    val adapter = StatusAdapter(list)
    val map = Collections.synchronizedMap(hashMapOf<Int, S>())

    val jsonArray: JSONArray by lazy {
        JSONArray(StatusForm.s)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status)

        for(i in 0 until jsonArray.length()){
            val o = jsonArray.getJSONObject(i)
            val f = F(o.getString("title"))

            val arr = o.getJSONArray("detail")

            for(j in 0 until  arr.length()){
                val jo = arr.getJSONObject(j)

                val s = S(jo.getString("title"))
                f.addSubItem(s)
                map[jo.getInt("videoId")] = s
            }
            list.add(f)
        }

        findViewById<View>(R.id.back).setOnClickListener {
            onBackPressed()
        }
        val recy = findViewById<RecyclerView>(R.id.recycler)
        recy.adapter = adapter
        recy.layoutManager = LinearLayoutManager(this)
        load()
    }

    fun load(){
        Thread{
            try{
                val number = App.getInstance().user.studentId
                val client = OkHttpClient()
                val str = client.newCall(Request.Builder()
                        .url("http://qgailab.com/classes/user/gid?number=$number")
                        .post(RequestBody.create(null, "")).build()).execute().body()!!.string()
                val o = JSONObject(str)
                if(o.getInt("code") == 1){
                    val id = o.getInt("data")

                    val ss = client.newCall(Request.Builder()
                            .url("http://qgailab.com/classes/rate/sr?userId=$id")
                            .post(RequestBody.create(null, "")).build()).execute().body()!!.string()

                    val oo = JSONObject(ss)
                    if(oo.getInt("code") == 1){

                        oo.getJSONArray("data").apply {
                            val n = length()
                            for(j in 0 until n){
                                try{
                                    val mo = getJSONObject(j)
                                    val id = mo.getInt("videoId")
                                    val s = map[mo.getInt("videoId")]!!
                                    val subItem = arrayListOf<I>()
                                    subItem.add(I("预习情况", if(mo.getBoolean("preview")) "已完成" else "未完成" ))
                                    subItem.add(I("视频学习", "已完成 ${mo.getInt("rate")/60} 分钟"))
                                    subItem.add(I("课后习题",  if(mo.getBoolean("review")) "已完成" else "未完成" ))
                                    s.subItems = subItem
                                }catch (e: Exception){
                                    //e.printStackTrace()
                                }


                            }

                        }

                    }else{
                        runOnUiThread {
                            Toast.makeText(this@StatusActivity, oo.getString("msg"), Toast.LENGTH_SHORT).show()
                        }
                    }


                }else{
                    runOnUiThread {
                        Toast.makeText(this@StatusActivity, "未注册", Toast.LENGTH_SHORT).show()
                    }
                }
                runOnUiThread {
                    Toast.makeText(App.getInstance(), "加载成功", Toast.LENGTH_SHORT).show()
                    adapter.notifyDataSetChanged()
                }
            }catch (e: Exception){
                e.printStackTrace()
                load()
            }



        }.start()
    }

}

class StatusAdapter(
        val list: List<MultiItemEntity>
): BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder>(list){

    init {
        addItemType(0, R.layout.item_status_f)
        addItemType(1, R.layout.item_status_s)
        addItemType(2, R.layout.item_status_i)
        //setOnItemClickListener()
    }

    private fun setOnItemClickListener() {
        val onItemClickListener = OnItemClickListener { adapter, view, position ->
            val item = getItem(position)
            if (item !is AbstractExpandableItem<*>) {
                return@OnItemClickListener
            }
            if ((item as AbstractExpandableItem<*>).isExpanded) {
                // 收起被点击 Item 的子列表
                collapse(position + headerLayoutCount)
            } else {
                // 展开被点击 Item 的子列表
                expand(position + headerLayoutCount)
            }
        }
        setOnItemClickListener(onItemClickListener)
    }
    override fun convert(p0: BaseViewHolder, p1: MultiItemEntity) {
        when(p1.itemType){
            0 -> {
                val f = p1 as F
                p0.setText(R.id.title, f.title)
                if(p1.isExpanded){
                    p0.setImageResource(R.id.img, R.drawable.ic_baseline_keyboard_arrow_down_24)
                }else{
                    p0.setImageResource(R.id.img, R.drawable.ic_baseline_keyboard_arrow_right_24)
                }
                p0.itemView.setOnClickListener {
                    val pos = p0.adapterPosition
                    if(p1.isExpanded){
                        collapse(pos)
                    }else{
                        expand(pos)
                    }
                }

            }
            1 -> {
                val s = p1 as S
                p0.setText(R.id.title, s.title)
                if(p1.isExpanded){
                    p0.setImageResource(R.id.img, R.drawable.ic_baseline_keyboard_arrow_down_24)
                }else{
                    p0.setImageResource(R.id.img, R.drawable.ic_baseline_keyboard_arrow_right_24)
                }
                p0.itemView.setOnClickListener {
                    val pos = p0.adapterPosition
                    if(p1.isExpanded){
                        collapse(pos)
                    }else{
                        expand(pos)
                    }
                }
            }
            2 -> {
                val s = p1 as I
                p0.setText(R.id.title, s.title)
                p0.setText(R.id.msg, s.msg)

            }
        }
    }
}

data class F(
        val title: String
) : AbstractExpandableItem<S>(), MultiItemEntity {
    override fun getLevel(): Int {
        return 0
    }

    override fun getItemType(): Int {
        return 0
    }
}

data class S(
        val title: String
): AbstractExpandableItem<I>(), MultiItemEntity {
    override fun getLevel(): Int {
        return 1
    }

    override fun getItemType(): Int {
        return 1
    }
}

data class I(
        val title: String,
        val msg: String
): MultiItemEntity{
    override fun getItemType(): Int {
        return 2
    }
}