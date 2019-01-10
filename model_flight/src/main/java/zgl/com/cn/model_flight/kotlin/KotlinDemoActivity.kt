package zgl.com.cn.model_flight.kotlin

import android.app.Activity
import android.os.Bundle
import zgl.com.cn.model_flight.R

/**
 *  描述：Kotlin  DEMO 练习类
 * @author : jsj_android
 * @date : 2019/1/3
 */
class KotlinDemoActivity : Activity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_flight_main)



    }

    fun sum(a:Int,b:Int):Int{
        return a+b
    }


    val a : Int = 1
    var b : String = "。。。"
    var c = 1



    public fun sum(vararg num:Int){

    }


}