package com.minhchaudm.playmp3

import android.Manifest
import android.app.Dialog
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.chootdev.blurimg.BlurImage
import com.gauravk.audiovisualizer.visualizer.CircleLineVisualizer
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    var player: MediaPlayer = MediaPlayer()
    var nhacdangchay : Boolean = false
    var daconhac: Boolean = false
    var thoiGianNhacDungLai: Int = 0;
    var arrayloibaihat: ArrayList<String> = ArrayList()
    var  timeMax: Int = 0
    var vitriloi: Int = 0
    var gocquay: Float = 0.0f
    var arrBaiNhac: ArrayList<BaiNhac> = ArrayList()
    var indexbainhat : Int = 0
    var bannhac: BaiNhac = BaiNhac()
//    var adapter: AdapterListNhac?=null
//    var mvsualizer: CircleLineVisualizer = CircleLineVisualizer(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        listviewnhac.adapter= AdapterListNhac(this,arrBaiNhac)
        listviewnhac.setOnItemClickListener { parent, view, position, id ->
            chonNhac(position)
        }
        setClick()
        setUp()
        listnhac.visibility = View.GONE

    }
    private fun init(){
        val b1: BaiNhac = BaiNhac()
        b1.ten = "Còn Gì Đâu Hơn Chữ Đã từng"
        b1.hinhbn = R.drawable.anhcdquanap
        b1.baiNhac = R.raw.con_gi_dau_hon_chu_da_tung
        b1.arrloibainhac.add("chưa có lời")
        b1.arrloibainhac.add("chưa có lời")
        b1.arrloibainhac.add("chưa có lời")
        arrBaiNhac.add(b1)
        val b2: BaiNhac = BaiNhac()
        b2.ten = "Phụ Tình"
        b2.hinhbn = R.drawable.anhcddinhquan
        b2.baiNhac = R.raw.phu_tinh
        b2.arrloibainhac.add("chưa có lời")
        b2.arrloibainhac.add("chưa có lời")
        b2.arrloibainhac.add("chưa có lời")
        arrBaiNhac.add(b2)
        val b3: BaiNhac = BaiNhac()
        b3.ten = "Như Gió và mây"
        b3.hinhbn = R.drawable.anhcddinhdaivu
        b3.baiNhac = R.raw.nhu_gio_voi_may
        b3.arrloibainhac.add("chưa có lời")
        b3.arrloibainhac.add("chưa có lời")
        b3.arrloibainhac.add("chưa có lời")
        arrBaiNhac.add(b3)

    }

    private fun setUp(){
        chonNhac(0)
        stopMusic()
        setAnhNen(R.drawable.background)
        nhacdangchay = false
        daconhac = false

        object : CountDownTimer(30000, 50) {


            override fun onTick(millisUntilFinished: Long) {
                upDate()
            }
            override fun onFinish() {
                start()
            }
        }.start()
        object : CountDownTimer(2000,400){

            override fun onTick(millisUntilFinished: Long) {

            }
            override fun onFinish() {
              start()
                upDate2()
            }

        }.start()


    }
    private fun setClick(){
       play.setOnClickListener {
           if (daconhac == false){
                startMusic(R.raw.con_gi_dau_hon_chu_da_tung)
           }else{
               if (nhacdangchay == true){
                    stopMusic()
               }else{
                    continueMusic()
               }
           }
       }
        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {

            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {


            }
            override fun onStopTrackingTouch(seekBar: SeekBar) {

                if (daconhac == false || nhacdangchay == false){
                    return
                }
                thoiGianNhacDungLai = seekBar.progress
                player.pause()
                player.seekTo(thoiGianNhacDungLai)
                player.start()
            }
        })
    }




    // hàm set ảnh nền
    private fun setAnhNen(anh: Int){
      BlurImage.withContext(this)
        BlurImage.setBlurRadius(15.5f)
        BlurImage.setBitmapScale(0.1f)
        BlurImage.blurFromResource(anh)
        BlurImage.into(img_backgroud)
    }
    //ham bắt đầu khởi tạo nhạc
    private  fun startMusic(bannhac: Int) {
        if (player == null) {
            player = MediaPlayer.create(this, bannhac)
        } else {
            player.stop()
            player.release()
            player = MediaPlayer.create(this, bannhac)
        }
        player.start()
        play.setImageResource(R.drawable.pause)
        daconhac = true
        nhacdangchay = true
        // cấp request cho Recod_audio
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, Array(1){ Manifest.permission.RECORD_AUDIO}, 0)

        }
        blast.setAudioSessionId(player.audioSessionId)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            seekbar.min = 0
        }
        timeMax = player.duration
        seekbar.max = timeMax
        texttimestop.text = minitoString(timeMax)
        vitriloi = 0
        player.setOnCompletionListener(object : MediaPlayer.OnCompletionListener {
            override fun onCompletion(mp: MediaPlayer?) {
                chayXongNhac()
            }

        })
    }
    //hàm chạy xong nhạc
    fun chayXongNhac(){
        nhacdangchay = false
        daconhac= false
        thoiGianNhacDungLai=0
        vitriloi=0
        gocquay=0.0f
        play.setImageResource(R.drawable.play_button)
    }
    // hàm dừng nhạc
    private fun stopMusic() {
        nhacdangchay = false
        play.setImageResource(R.drawable.play_button)
        player.pause()
        thoiGianNhacDungLai = player.currentPosition
    }
    // hàm chạy tiếp nhạc
    private fun continueMusic() {
        nhacdangchay = true
        play.setImageResource(R.drawable.pause)
        player.seekTo(thoiGianNhacDungLai)
        player.start()
    }
    private  fun   upDate(){
        if (daconhac == false || nhacdangchay == false){
            return
        }
        seekbar.progress = player.currentPosition
        txttimerun.text = minitoString(player.currentPosition)
        gocquay += 0.5f
        if (gocquay==360f){
            gocquay=0f
        }
        img_CD.rotation = gocquay
    }
    private fun minitoString(t: Int):String{
       var k: Int = t/1000
        var p:Int = k/60
        var s:Int = k % 60
        return "${checknum(p)}:${checknum(s)}"
    }
    private fun checknum(i: Int): String {
       if (i<10){
           return "0${i}"
       }
        return  "${i}"
    }
    private  fun upDate2(){
        if (daconhac == false || nhacdangchay == false){
            return
        }
        vitriloi ++
        if (arrayloibaihat.size == vitriloi){
            vitriloi = 0
        }
        loibaihat.startAnimation(AnimationUtils.loadAnimation(this,R.anim.anim_matdi))
        object : CountDownTimer(400,100){


            override fun onTick(millisUntilFinished: Long) {

            }
            override fun onFinish() {
                loibaihat.text = arrayloibaihat[vitriloi]
                loibaihat.startAnimation(AnimationUtils.loadAnimation(this@MainActivity,R.anim.anim_hienlen))
            }

        }.start()
    }
    private  fun chonNhac(i: Int){
        indexbainhat += i
        if (indexbainhat == arrBaiNhac.size){
            indexbainhat =0
        }
        if (indexbainhat == -1){
            indexbainhat = arrBaiNhac.size - 1
        }
        bannhac = arrBaiNhac[indexbainhat]
        arrayloibaihat.clear()
        arrayloibaihat.addAll(bannhac.arrloibainhac)
        tenbaihat.text = bannhac.ten
       img_CD.setImageResource(bannhac.hinhbn)
        startMusic(bannhac.baiNhac)

    }

    fun baiTruoc(view: View) {
        chonNhac(-1)
    }
    fun baiSau(view: View) {
        chonNhac(1)
    }
    var menu : Boolean = false
    private fun setmenu(){
        listnhac.visibility = View.VISIBLE
        menu = true
    }

    fun listnhac(view: View) {
        if (menu == false){

            setmenu()

        }else {
            listnhac.visibility = View.INVISIBLE
            menu= false
        }
    }


}