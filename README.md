[![Release](https://jitpack.io/v/com.kroegerama/barcode-kaiteki.svg)](https://jitpack.io/#com.kroegerama/barcode-kaiteki)
[![Build Status](https://travis-ci.org/kroegerama/barcode-kaiteki.svg?branch=master)](https://travis-ci.org/kroegerama/barcode-kaiteki)

## Barcode-Kaiteki

<img width="200" src="art/qr-code.png">

An easy to use library for barcode detection. Based on the new **AndroidX** **Camera2** api. Uses the **zxing** barcode detection library.

Comes with a **BarcodeView**, which combines a camera preview and an automatic overlay for detected barcodes.

#### Also contains three differend ready to use dialogs:

* **BarcodeDialog** *(DialogFragment)*
* **BarcodeBottomSheet** *(BottomSheetDialogFragment)*
* **BarcodeAlertDialog** *(AlertDialog)*
* **BarcodeFragment** *(Fragment)*

<img width="200" src="art/screen-dialogfragment.png">&emsp;<img width="200" src="art/screen-bottomsheet.png">&emsp;<img width="200" src="art/screen-alertdialog.png">

#### Features

* camera permission handling
* customize the displayed result points
* customize the barcode type (can be a list)
* allows scanning of inverted barcodes (white barcode on black background)

#### Add library dependency

Add jitpack to your toplevel gradle file (if not already present):

```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

Add barcode-kaiteki as dependency:

```gradle
dependencies {
    implementation 'com.github.er-cormant:barcode-kaiteki:2.0.0'
}
```

#### Usage

##### BarcodeDialog

Just let your Activity/Fragment implement **BarcodeResultListener**.

```kotlin
class MainActivity : AppCompatActivity(), BarcodeResultListener {

    //...

    override fun onBarcodeResult(result: Result): Boolean {
        Log.d(TAG, "Result: $result")

        //return false to not automatically close the dialog
        return false
    }
}

```

Then it is as easy as showing one of the provided dialogs.

```kotlin
//show a Barcode FragmentDialog (with swipe to dismiss)
BarcodeDialog.show(
    childFragmentManager,
    barcodeInverted = false
)

//show a Barcode BottomSheet
BarcodeBottomSheet.show(
    childFragmentManager,
    barcodeInverted = false
)

//show an AlertDialog from activity
showBarcodeAlertDialog(
    owner = this,
    listener = this,
    barcodeInverted = false
)
//or from a Fragment
requireContext().showBarcodeAlertDialog(
    owner = this,
    listener = this,
    barcodeInverted = false
)

//show a Barcode Fragment
val barcodeFragment = BarcodeFragment.makeInstance(
    barcodeInverted = false
)
supportFragmentManager.beginTransaction()
    .replace(R.id.container, barcodeFragment)
    .commit()
```

##### BarcodeView

You can also use the **BarcodeView** directly in your Layout.

```xml
<com.kroegerama.kaiteki.bcode.views.BarcodeView
    android:id="@+id/bcode"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:resultPointColor="#09E85E"
    app:resultPointSize="8dp"
    app:showResultPoints="true" />
```

Then add in your *onCreate/onViewCreated*: ```bcode.bindToLifecycle(this)``` and in your *onStop*: ```bcode.unbind()```.

```kotlin
override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    bcode.setBarcodeResultListener(this)
    bcode.bindToLifecycle(this)
}

override fun onStop() {
    super.onStop()
    bcode.unbind()
}
```
