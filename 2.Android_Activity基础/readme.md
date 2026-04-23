手动创建 Activity 
## Toast
Android 系统提供的一种非常好的提醒方式  
可以使用它将短小的信息通知给用户，这些信息会在一段时间后自动消失，并不会占用任何屏幕空间。  
``` kotlin
Toast.makeText(context, "Hello, Toast!", Toast.LENGTH_SHORT).show()
```
## Menu
菜单
```kotlin
// 在 Activity 中创建选项菜单
override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.example_menu, menu)
    return true
}

// 处理菜单项点击事件
override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
        R.id.menu_item1 -> {
            // 处理菜单项1
            true
        }
        R.id.menu_item2 -> {
            // 处理菜单项2
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}
```

## Intent
Intent 是 Android 程序中各组件之间进行交互的一种重要方式。  

### 显式 Intent  
### 隐式 Intent  
不明确指出想要启动哪一个 Activity ，指定了更为抽象的 action 和 category 等信息。  

## Activity 的生命周期
Android 用任务(task)来管理 Activity 的。一个任务就是一组存放在栈里的 Activity 的集合，这个栈也被称作返回栈(back stack)  

每当我们启动一个新的 Activity，它就会在返回栈中入栈，并处于栈顶的位置，而当我们按下 Back 键或调用 finish() 方法销毁 activity，处于栈顶的 Activity 就会出栈，前一个出栈的 Activity 就会位于栈顶。  
系统显示栈顶 Activity。  



