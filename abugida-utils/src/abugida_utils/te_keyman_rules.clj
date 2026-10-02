(ns abugida-utils.te-keyman-rules
  (:require [abugida-utils.te :as te]
            [clojure.java.io :as jio]))

(def ^:priate phoneme-keyman-keyid-map
  {
   ;; vowels
   "అ" "[T_A]"
   "ఆ" "[T_AA]"
   "ఇ" "[T_I]"
   "ఈ" "[T_II]"
   "ఉ" "[T_U]"
   "ఊ" "[T_UU]"
   "ఋ" "[T_VOC_R]"
   "ౠ" "[T_VOC_RR]"
   "ఎ" "[T_E]"
   "ఏ" "[T_EE]"
   "ఐ" "[T_AI]"
   "ఒ" "[T_O]"
   "ఓ" "[T_OO]"
   "ఔ" "[T_AI]"
   "అఁ" "[T_CHAND]"
   "అం" "[T_ANU]"
   "అః" "[T_VISAR]"

   ;; consonants
   "క్" "[T_K]"
   "ఖ్" "[T_KH]"
   "గ్" "[T_G]"
   "ఘ్" "[T_GH]"
   "ఙ్" "[T_NG]"
   "చ్" "[T_C]"
   "ఛ్" "[T_CH]"
   "జ్" "[T_J]"
   "ఝ్" "[T_JH]"
   "ఞ్" "[T_NY]"
   "ట్" "[T_TT]"
   "ఠ్" "[T_TTH]"
   "డ్" "[T_DD]"
   "ఢ్" "[T_DDH]"
   "ణ్" "[T_NN]"
   "త్" "[T_T]"
   "థ్" "[T_TH]"
   "ద్" "[T_D]"
   "ధ్" "[T_DH]"
   "న్" "[T_N]"
   "ప్" "[T_P]"
   "ఫ్" "[T_PH]"
   "బ్" "[T_B]"
   "భ్" "[T_BH]"
   "మ్" "[T_M]"
   "య్" "[T_Y]"
   "ర్" "[T_R]"
   "ఱ్" "[T_RR]"
   "ల్" "[T_L]"
   "ళ్" "[T_LL]"
   "ఴ్" "[T_LLL]"
   "వ్" "[T_V]"
   "శ్" "[T_SH]"
   "ష్" "[T_SS]"
   "స్" "[T_S]"
   "హ్" "[T_H]"
   })

(def cons-vowel-rules
  (for [c te/consonants
        v te/vowels]
    (let [v-key-id (get phoneme-keyman-keyid-map v)
          cv-str (te/phonemes->str [c v])]
      (format "%s + %s > %s"
              (pr-str c)
              v-key-id
              (pr-str cv-str)))))

(def short-long-vowel-map
  (into (sorted-map)
        {"అ" "ఆ"
         "ఇ" "ఈ"
         "ఉ" "ఊ"
         "ఎ" "ఏ"
         "ఒ" "ఓ"}))

(def short-vowel-doubled-rules
  (for [[short-v long-v] short-long-vowel-map]
    (let [v-key-id (get phoneme-keyman-keyid-map short-v)]
      (format "%s + %s > %s"
              (pr-str short-v)
              v-key-id
              (pr-str long-v)))))

(def cons-short-vowel-short-vowel-rules
  (for [c te/consonants
        short-v (keys short-long-vowel-map)]
    (let [c-short-v-str (te/phonemes->str [c short-v])
          v-key-id (get phoneme-keyman-keyid-map short-v)
          long-v (get short-long-vowel-map short-v)
          c-long-v-str (te/phonemes->str [c long-v])]
      (format "%s + %s > %s"
              (pr-str c-short-v-str)
              v-key-id
              (pr-str c-long-v-str)))))

(def grapheme-cluster-bksp-rules
  (for [c te/consonants
        v te/vowels]
    (let [cv-str (te/phonemes->str [c v])]
      (format "%s + [K_BKSP] > %s"
              (pr-str cv-str)
              (pr-str c)))))

(def anusvara-substitution-rules
  (for [[c-c-phonemes _] (sort te/anusvara-mappings)]
    (let [[c-nasal c-plosive] c-c-phonemes
          c-plosive-key-id (get phoneme-keyman-keyid-map c-plosive)]
      (format "%s + %s > %s"
              (pr-str c-nasal)
              c-plosive-key-id
              (pr-str (str te/unicode-anusvara-sign c-plosive))))))

(def anusvara-backspace-rules
  (for [[c-c-phonemes _] (sort te/anusvara-mappings)]
    (let [[c-nasal c-plosive] c-c-phonemes]
      (format "%s + [K_BKSP] > %s"
              (pr-str (str te/unicode-anusvara-sign c-plosive))
              (pr-str c-nasal)))))

(defn print-rules
  []
  (let [keyman-kbd-layout-lines (concat ["c pure consonant followed by short/long vowels"]
                                        cons-vowel-rules
                                        ["\n"]
                                        ["c short vowel x 2"]
                                        short-vowel-doubled-rules
                                        ["\n"]
                                        ["c C+short vowel + short vowel > C+long vowel"]
                                        cons-short-vowel-short-vowel-rules
                                        ["\n"]
                                        ["c multi-code point grapheme cluster + backspace"]
                                        grapheme-cluster-bksp-rules
                                        ["\n"]
                                        ["c anusvara substitutions"]
                                        anusvara-substitution-rules
                                        ["\n"]
                                        ["c grapheme cluster (incl anusvara) + backspace"]
                                        anusvara-backspace-rules)
        outdir (doto (jio/file "output" "te" "rules")
                 (.mkdirs))
        outfile (jio/file outdir "keyman_rules.txt")]
    (with-open [writer (jio/writer outfile)]
      (doseq [line keyman-kbd-layout-lines]
        (. writer write line)
        (. writer write "\n"))
      (.flush writer))))