(ns abugida-tools.te)


(def vowels ["అ"
             "ఆ"
             "ఇ"
             "ఈ"
             "ఉ"
             "ఊ"
             "ఋ"
             "ౠ"
             "ఌ"
             "ౡ"
             "ఎ"
             "ఏ"
             "ఐ"
             "ఒ"
             "ఓ"
             "ఔ"
             "అఁ"
             "అం"
             "అః"])

(def consonants [
                 \u0C15 ;; క Telugu Letter Ka
                 \u0C16 ;; ఖ Telugu Letter Kha
                 \u0C17 ;; గ Telugu Letter Ga
                 \u0C18 ;; ఘ Telugu Letter Gha
                 \u0C19 ;; ఙ Telugu Letter Nga
                 \u0C1A ;; చ Telugu Letter Ca
                 \u0C1B ;; ఛ Telugu Letter Cha
                 \u0C1C ;; జ Telugu Letter Ja
                 \u0C1D ;; ఝ Telugu Letter Jha
                 \u0C1E ;; ఞ Telugu Letter Nya
                 \u0C1F ;; ట Telugu Letter Tta
                 \u0C20 ;; ఠ Telugu Letter Ttha
                 \u0C21 ;; డ Telugu Letter Dda
                 \u0C22 ;; ఢ Telugu Letter Ddha
                 \u0C23 ;; ణ Telugu Letter Nna
                 \u0C24 ;; త Telugu Letter Ta
                 \u0C25 ;; థ Telugu Letter Tha
                 \u0C26 ;; ద Telugu Letter Da
                 \u0C27 ;; ధ Telugu Letter Dha
                 \u0C28 ;; న Telugu Letter Na
                 \u0C2A ;; ప Telugu Letter Pa
                 \u0C2B ;; ఫ Telugu Letter Pha
                 \u0C2C ;; బ Telugu Letter Ba
                 \u0C2D ;; భ Telugu Letter Bha
                 \u0C2E ;; మ Telugu Letter Ma
                 \u0C2F ;; య Telugu Letter Ya
                 \u0C30 ;; ర Telugu Letter Ra
                 \u0C31 ;; ఱ Telugu Letter Rra
                 \u0C32 ;; ల Telugu Letter La
                 \u0C33 ;; ళ Telugu Letter Lla
                 \u0C34 ;; ఴ Telugu Letter Llla
                 \u0C35 ;; వ Telugu Letter Va
                 \u0C36 ;; శ Telugu Letter Sha
                 \u0C37 ;; ష Telugu Letter Ssa
                 \u0C38 ;; స Telugu Letter Sa
                 \u0C39 ;; హ Telugu Letter Ha
                 ])

(def
  ^{:private true
    :description "Unicode likes to call Indic abugida combining marks that indicate the vowel sound after a consonant as \"dependent vowel signs\"."}
  unicode-vowel-signs [
                       \u0C3E  ;; ◌ా Telugu Vowel Sign Aa
                       \u0C3F  ;; ◌ి Telugu Vowel Sign I
                       \u0C40  ;; ◌ీ Telugu Vowel Sign Ii
                       \u0C41  ;; ు Telugu Vowel Sign U
                       \u0C42  ;; ూ Telugu Vowel Sign Uu
                       \u0C43  ;; ృ Telugu Vowel Sign Vocalic R
                       \u0C44  ;; ౄ Telugu Vowel Sign Vocalic Rr
                       \u0C46  ;; ◌ె Telugu Vowel Sign E
                       \u0C47  ;; ◌ే Telugu Vowel Sign Ee
                       \u0C48  ;; ◌ై Telugu Vowel Sign Ai
                       \u0C4A  ;; ◌ొ Telugu Vowel Sign O
                       \u0C4B  ;; ◌ో Telugu Vowel Sign Oo
                       \u0C4C  ;; ◌ౌ Telugu Vowel Sign Au
                       ])


(def
  ^{:private true}
  unicode-virama-sign \u0C4D ;; "◌్"  Telugu Sign Virama
                             ;; = halant (the preferred name)
  )

(def letters
  (concat [(cons nil vowels)]
          (for [c consonants]
            (into []
                  (for [vs (cons unicode-virama-sign (cons nil unicode-vowel-signs))]
                    (str c vs))))))


(defn print-letters
  [letters]
  (run! println (for [row letters]
                  (into [] row))))
