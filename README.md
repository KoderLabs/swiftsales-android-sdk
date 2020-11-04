# Swift-Chat Andorid SDK

## Get Started

### 1. Gradle Dependency

Add jitpack to your root build.gradle
```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

Add SwiftChat SDK dependecy in app module build.gradle
```gradle
dependencies {
    //Swift Chat SDK
    implementation 'io.swiftsales:swiftchat-android-sdk:1.1.0'
}
```

### 2. Manifest

Add Internet and Read External Storage(For file attachement) Permission on your Manifest.xml file
```Manifest.xml
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.INTERNET" />
```

Add Swift Chat activity on your Manifest.xml file
```Manifest.xml
<application ....>
    <activity android:name="com.swift.chat.library.SwiftChatActivity">
    <meta-data
        android:name="swiftchat.websiteId"
        android:value="{your_website_id}" />
    <meta-data
        android:name="swiftchat.package"
        android:value="{your_package_name}" />
    </activity>
</application>
```

### 3. Add Widget

Add SwiftChatFAB on your layout

``` your_layout.xml
<com.swift.chat.library.SwiftChatFAB
    android:id="@+id/swiftFab"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content" />
```

### 4. Manually

To open chat activity manually

``` Java
SwiftChatActivity.launch(context);
```

``` Kotlin
SwiftChatActivity.launch(context)
```

## License
```
MIT License

Copyright (c) 2020 Koderlabs

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
