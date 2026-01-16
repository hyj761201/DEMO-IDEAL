package com.example.demo

object ApiConfig {
    // Android 模拟器使用 10.0.2.2 访问本地服务
    const val BASE_URL = "http://10.0.2.2:8080/api/"
    
    // 真机使用：确保手机和电脑在同一WiFi，使用电脑IP地址
    // const val BASE_URL = "http://192.168.1.100:8080/api/"  // 替换为你的电脑IP
}
