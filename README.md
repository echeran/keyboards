# Keyboards for Abugida Scripts

This repo contains prototypes of keyboards for abugida scripts using a phoneme-based style of input.

## Benefits of phoneme style for abugidas

This style...

* ...is faster for typing than existing keyboard styles.
* ...is more intuitive than existing keyboard styles.
* ...only requires knowledge of the native script (unlike English-based transliteration schemes).
* ...allows for fewer keys than letter shape-based input schemes.
* ...allows keys to be fixed statically (unlike input schemes with dynamically changing keys).
* ...avoids non-canonical Unicode code point ordering behavior that is possible from shape-based input schemes (bad for search indexing).

<a href="#more-details">See below</a> for more details.

## How to install

The best way to try this style of input is through a smartphone or any other touchscreen mobile device.
However, you can still try out a no-installation desktop based version, assuming you have a US-101 keyboard

### Mobile device installation

The keyboards here are implemented through the free cross-platform app [Keyman](https://keyman.com/).
After installing Keyman, 
you can download and install the keyboard files from this repository.

#### How to install the Keyman app

Keyman is available on [all major desktop and mobile platforms](https://help.keyman.com/products/).

Mobile app download:

* [Keyman for iOS (iPhone / iPad)](https://keyman.com/iphone-and-ipad/)
* [Keyman for Android](https://keyman.com/android/)

#### How to install a keyboard for Keyman

* iOS (iPhone / iPad): <a target="ios" href="https://help.keyman.com/products/iphone-and-ipad/current-version/basic/installing-custom-keyboards-dictionaries">Installing Custom Keyboards/Dictionaries</a>
* Android: <a target="android" href="https://help.keyman.com/products/android/current-version/basic/installing-custom-packages">Installing Custom Keyboards/Dictionaries</a>

#### Guide to keyboards in the repository

The top level folder is named after the 2-letter BCP 47 language identifier.
The folder directly inside might represent different key layouts for that language.
Each key layout will go through successive iterations,
which will be labeled as "v1", "v2", etc.

Languages currently implemented:

BCP 47 identifier | Language | Link to keyboards |
|-----------------|----------|-------------------|
| ta              | Tamil    | [Tamil keyboards](https://github.com/echeran/keyboards/blob/main/ta/layout-e) |


### Web-based desktop version

If you have a "standard" US-101 keyboard on a laptop or desktop computer, then you can try this site without any installation necessary:

https://languagetools-153419.appspot.com/ta/

There are different key layouts provided.
You can select between them using the "Keyboard:" dropdown menu in the upper left side of the page.
The layouts are named "தமிழ் Phonemic A", "தமிழ் Phonemic B", etc.

## More details on benefits

<a id="more-details"></a>

For more background information on the benefits of the phoneme style, 
and what are the problems with existing input methods for abugida scripts,
refer to my previous talks and writings:

* (2022) [Redesigning an Input Method for an Abugida Script](https://elangocheran.com/2022/02/14/redesigning-an-input-method-for-an-abugida-script/)
* (2020) [D-Pub for Keyboards for Agglutinative Languages and Abugidas](https://elangocheran.com/2021/02/11/d-pub-for-keyboards-for-agglutinative-languages-and-abugidas/)
  - (2020) [Deriving Lexical Data for Tamil from Scratch Using Morphology](https://elangocheran.com/2020/11/01/deriving-lexical-data-for-tamil-from-scratch-using-morphology/)
* (2017) [Tamil Internet Conference 2017 – Prefix Trees for Language Processing – slides and paper](https://elangocheran.com/2017/08/29/tamil-internet-conference-2017-prefix-trees-for-language-processing-slides-and-paper/)