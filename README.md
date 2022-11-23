# ASCII-Art-Generator

[![Version](https://img.shields.io/badge/Version-0.0.1-brightgreen.svg)](https://github.com/zelin/ASCII-Art-Generator)
[![Platform](https://img.shields.io/badge/Platform-Android-orange.svg)](https://github.com/zelin/ASCII-Art-Generator)
[![License](https://img.shields.io/badge/License-MIT-black.svg)](https://github.com/zelin/ASCII-Art-Generator)
[![Donate Bitcoin](https://img.shields.io/badge/Donate-Bitcoin-green.svg)](http://neberox.tk/donate/?amount=2&currency=USD)

![Screenshot 1](./Screenshots/image_1.jpg)
![Screenshot 2](./Screenshots/image_2.jpg)

## Installation

The easiest way to add the library to your project is by adding it as a dependency to your build.gradle

```ruby
dependencies {
   implementation 'com.neberox.library:asciicreator:0.1.0'
}
```

## Usage

### Using ASCIIConverter class

Create a ASCIIConverter object

```kotlin
var converter: ASCIIConverter = ASCIIConverter(this);
```

Create ASCII from bitmap

```kotlin
val bitmap = BitmapFactory.decodeResource(resources, R.drawable.test_image)
imgView.setImageBitmap(converter.createASCIIImage(bitmap));
```

Convert in the background async task providing a completion block. 
Completion block will be called on the main thread.

```kotlin
converter.createASCIIImage(bitmap, object : OnBitmapTaskListener {
        override fun onTaskCompleted(data: Bitmap?) {
            // Switch to the main thread to update the UI
            lifecycleScope.launch(Dispatchers.Main) {
            binding.imageView.setImageBitmap(data)
        }
    }
})
```

Convert to String
```kotlin
Log.d("ASCII-GENERATOR", converter.createASCIIString(bitmap));
```

Convert in the background async task providing a completion block. 
Completion block will be called on the main thread.

```kotlin
converter.createASCIIString(bitmap, object : OnStringTaskListener {
    override fun onTaskCompleted(data: String?) {
        data?.let {
            Log.d("ASCII-GENERATOR", it)
        }
    }
})
```

#### Options available

```kotlin
converter.setFontSize(18);
converter.setReversedLuminance(false);
converter.setGrayScale(false);
converter.setBackgroundColor(Color.RED);
```

## More Options

By default luminance values are mapped to strings using 

```kotlin
val map: MutableMap<String, Float> = HashMap()
map[" "] = 1.0f
map["`"] = 0.95f
map["."] = 0.92f
map[","] = 0.9f
map["-"] = 0.8f
map["~"] = 0.75f
map["+"] = 0.7f
map["<"] = 0.65f
map[">"] = 0.6f
map["o"] = 0.55f
map["="] = 0.5f
map["*"] = 0.35f
map["%"] = 0.3f
map["X"] = 0.1f
map["@"] = 0.0f
```

You can instantiate a converter with your own map

```kotlin
val map: MutableMap<String, Float> = HashMap()
map[" "] = 1.0f
map["`"] = 0.95f
map[","] = 0.9f
map["-"] = 0.8f
map["+"] = 0.7f
map["<"] = 0.65f
map["o"] = 0.55f
map["="] = 0.5f
map["%"] = 0.3f
map["@"] = 0.0f

converter = ASCIIConverter(Activity.this, map)
```

## Potential Improvements
* Creating an ASCIIImageView to automatically generate ASCII from bitmap
* Implementing more options for creating ASCII

## Author

Muhammad Umar, https://github.com/zelin

## License

ASCII-Art-Generator is available under the MIT license. See the LICENSE file for more info.
