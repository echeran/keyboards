(ns abugida-utils.te
  (:require [clj-thamil.format :as f]
            [clojure.set :as set]
            [clojure.string :as string]))

(def vowels ["అ"
             "ఆ"
             "ఇ"
             "ఈ"
             "ఉ"
             "ఊ"
             "ఋ"
             "ౠ"
             ;;"ఌ"
             ;;"ౡ"
             "ఎ"
             "ఏ"
             "ఐ"
             "ఒ"
             "ఓ"
             "ఔ"
             "అఁ"
             "అం"
             "అః"])

(def unicode-consonant-base-characters [
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
                       \u0C3E ;; ◌ా Telugu Vowel Sign Aa
                       \u0C3F ;; ◌ి Telugu Vowel Sign I
                       \u0C40 ;; ◌ీ Telugu Vowel Sign Ii
                       \u0C41 ;; ు Telugu Vowel Sign U
                       \u0C42 ;; ూ Telugu Vowel Sign Uu
                       \u0C43 ;; ృ Telugu Vowel Sign Vocalic R
                       \u0C44 ;; ౄ Telugu Vowel Sign Vocalic Rr
                       \u0C46 ;; ◌ె Telugu Vowel Sign E
                       \u0C47 ;; ◌ే Telugu Vowel Sign Ee
                       \u0C48 ;; ◌ై Telugu Vowel Sign Ai
                       \u0C4A ;; ◌ొ Telugu Vowel Sign O
                       \u0C4B ;; ◌ో Telugu Vowel Sign Oo
                       \u0C4C ;; ◌ౌ Telugu Vowel Sign Au
                       \u0C01 ;; ◌ఁ TELUGU SIGN CANDRABINDU
                       \u0C02 ;; ◌ం TELUGU SIGN ANUSVARA
                       \u0C03 ;; ◌ః TELUGU SIGN VISARGA
                       ])

(def ^:private unicode-virama-sign \u0C4D ;; "◌్"  Telugu Sign Virama
                                          ;; = halant (the preferred name)
  )

(def unicode-anusvara-sign \u0C02 ;; ◌ం TELUGU SIGN ANUSVARA
  )

(defn- vowel->index
  [vowel]
  (let [vowel-index-entries (map-indexed #(vector %2 %1) (cons nil vowels))
        vowel-index-map (into {} vowel-index-entries)]
    (or (get vowel-index-map (str vowel)) -1)))

(defn- vowel-mapping-ordering-comparator
  [v1 v2]
  (let [v1-idx (vowel->index v1)
        v2-idx (vowel->index v2)]
    (compare v1-idx v2-idx)))

(def
  ^{:doc "a mapping of vowels (includes nil) to their corresponding Unicode combining mark character (or nil if not applicable)"}
  vowel->unicode-vowel-sign
  (into (sorted-map-by vowel-mapping-ordering-comparator)
        (zipmap (cons nil vowels)
                (cons unicode-virama-sign (cons nil unicode-vowel-signs)))))

(def ^:private grid-of-letter-mapping-entries
  (let [vowel-row (for [v vowels]
                    {v [v]})]
    (concat [(cons nil vowel-row)]
            (for [c unicode-consonant-base-characters]
              (into []
                    (for [vvs-entry vowel->unicode-vowel-sign]
                      (let [[vowel vowel-sign] vvs-entry
                            letter (str c vowel-sign)
                            phonemes [(str c unicode-virama-sign)
                                      vowel]
                            phonemes-without-nils (into [] (keep identity phonemes))]
                        {letter phonemes-without-nils})))))))

(def letters
  (into [] (for [row grid-of-letter-mapping-entries]
             (into [] (for [letter-entry row]
                        (when letter-entry
                          (key (first letter-entry))))))))

(def consonants
  (for [c unicode-consonant-base-characters]
    (str c unicode-virama-sign)))

(def phonemes
  (set (concat vowels
               consonants)))

(def ^:private consonant-conjuncts
  (for [c1 unicode-consonant-base-characters]
    (into []
          (for [c2 unicode-consonant-base-characters]
            (str c1
                 unicode-virama-sign
                 c2
                 unicode-virama-sign)))))

(def anusvara-mappings
  {["ఙ్" "క్"] [unicode-anusvara-sign "క్"]
   ["ఙ్" "ఖ్"] [unicode-anusvara-sign "ఖ్"]
   ["ఙ్" "గ్"] [unicode-anusvara-sign "గ్"]
   ["ఙ్" "ఘ్"] [unicode-anusvara-sign "ఘ్"]

   ["ఞ్" "చ్"] [unicode-anusvara-sign "చ్"]
   ["ఞ్" "ఛ్"] [unicode-anusvara-sign "ఛ్"]
   ["ఞ్" "జ్"] [unicode-anusvara-sign "జ్"]
   ["ఞ్" "ఝ్"] [unicode-anusvara-sign "ఝ్"]

   ["ణ్" "ట్"] [unicode-anusvara-sign "ట్"]
   ["ణ్" "ఠ్"] [unicode-anusvara-sign "ఠ్"]
   ["ణ్" "డ్"] [unicode-anusvara-sign "డ్"]
   ["ణ్" "ఢ్"] [unicode-anusvara-sign "ఢ్"]

   ["న్" "త్"] [unicode-anusvara-sign "త్"]
   ["న్" "థ్"] [unicode-anusvara-sign "థ్"]
   ["న్" "ద్"] [unicode-anusvara-sign "ద్"]
   ["న్" "ధ్"] [unicode-anusvara-sign "ధ్"]

   ["య్" "ప్"] [unicode-anusvara-sign "ప్"]
   ["య్" "ఫ్"] [unicode-anusvara-sign "ఫ్"]
   ["య్" "బ్"] [unicode-anusvara-sign "బ్"]
   ["య్" "భ్"] [unicode-anusvara-sign "భ్"]

   })

(def ^:private
  ^{:doc "a map of the substr (starting with the anusvara combining mark character followed by a consonant and vowel sign)
  to the 'normalized' seq of phoneme strs that are implicitly represented by the substr.
  this map is used to form a set of entries in str-to-phonemes-map"}
  get-anusvara-mappings-for-str-to-phonemes
  (let [anusvara-mappings-for-consonants-for-str-to-phonemes (for [entry anusvara-mappings]
                                                               (let [[phonemes str-chars] entry]
                                                                 [(apply str str-chars) phonemes]))
        anusvara-mappings-for-all-letters-for-str-to-phonemes (for [entry anusvara-mappings-for-consonants-for-str-to-phonemes
                                                                    vowel-entry vowel->unicode-vowel-sign]
                                                                (let [[s phonemes] entry
                                                                      [vowel-str combining-mark-char] vowel-entry
                                                                      new-string (->> (-> s
                                                                                          (butlast) ;; truncate the virama combining mark at the end of each consonant
                                                                                          (concat [combining-mark-char]))
                                                                                      (apply str))
                                                                      new-phonemes (->> (conj phonemes vowel-str)
                                                                                        (keep identity)
                                                                                        (into []))]
                                                                  [new-string new-phonemes]))]
    anusvara-mappings-for-all-letters-for-str-to-phonemes))

(def ^:private str-to-phonemes-map
  (let [grid-of-letter-mapping-entries-without-anusvara (for [row grid-of-letter-mapping-entries]
                                                          (let [row-without-anusvara (concat (take 16 row)
                                                                                (drop 17 row))]
                                                            row-without-anusvara))
        final-mapping (-> (into {} (apply concat grid-of-letter-mapping-entries-without-anusvara))
                          (into get-anusvara-mappings-for-str-to-phonemes))]
    final-mapping))

(def ^:private
  ^{:doc "a trie of the individual letters in Telugu, whose terminus-attached values are sequences of each letter's phonemes -- this trie can be used in str->elems for directly splitting a word into its phonemes"}
  phoneme-trie (f/make-trie str-to-phonemes-map))

(defn str->phonemes
  "take a string and split it into its constitutent Telugu phonemes"
  [s]
  (let [phonemes (f/str->elems phoneme-trie s)
        phonemes-without-joiner-chars (remove #{"\u200C" "\u200D"} phonemes)]
    phonemes-without-joiner-chars))

(def inverse-phoneme-map (set/map-invert str-to-phonemes-map))

;; TODO: refactor clj-thamil.format/phonemes->str to parameterize by inverse-phoneme-map

(defn phonemes->str
  "given a seq of phonemes, create a string where the phonemes are combined into their proper letters"
  [phoneme-seq]
  (let [concat-phoneme-str (apply str phoneme-seq)
        inverse-concat-phoneme-map (into {} (for [[k v] inverse-phoneme-map]
                                              [(apply str k) v]))
        inverse-concat-phoneme-trie (f/make-trie inverse-concat-phoneme-map)
        combined-phoneme-str (apply str (f/str->elems inverse-concat-phoneme-trie concat-phoneme-str))]
    combined-phoneme-str))

(def ^:private letter-to-phonemes-map
  (let [entries-flat-coll (apply concat grid-of-letter-mapping-entries)]
    (into {} entries-flat-coll)))

(def ^:private phonemes-to-letter-map (set/map-invert letter-to-phonemes-map))

(defn phonemes->letters
  "given a seq of phonemes, return the seq of letters after the phonemes are combined into a normalized version of the letters"
  [phoneme-seq]
  (let [concat-phoneme-str (apply str phoneme-seq)
        inverse-concat-phoneme-map (into {} (for [[k v] phonemes-to-letter-map]
                                              [(apply str k) v]))
        inverse-concat-phoneme-trie (f/make-trie inverse-concat-phoneme-map)
        letters-seq (f/str->elems inverse-concat-phoneme-trie concat-phoneme-str)]
    letters-seq))

;; TODO: create functions upstream in clj-thamil that accepts a sequence

(defn str->letters
  "Convert a string into a seq of normalized letters"
  [s]
  (-> s
      str->phonemes
      phonemes->letters))

(defn- letter-frequency-grid
  "For a given string, return the frequencies of the normalized letters of the string's text"
  [phonemes]
  (let [letter-seq (phonemes->letters phonemes)
        letter-frequency-map (frequencies letter-seq)]
    (string/join \newline (for [row letters]
                            (->> (map #(get letter-frequency-map % 0) row)
                                 (map (partial format "%4d"))
                                 (string/join \tab))))))

(defn- phoneme-freq-report
  [phonemes]
  (let [phoneme-freqs-map (frequencies phonemes)
        vowel-freqs (map #(get phoneme-freqs-map % 0) vowels)
        consonant-freqs (map #(get phoneme-freqs-map % 0) consonants)]
    (string/join \newline ["Vowel frequencies:"
                           (string/join \tab vowels)
                           (string/join \tab vowel-freqs)
                           ""
                           (str "Vowel sum: " (reduce + vowel-freqs))
                           \newline
                           "Consonant frequencies:"
                           (string/join \tab consonants)
                           (string/join \tab consonant-freqs)
                           ""
                           (str "Consonant sum: " (reduce + consonant-freqs))])))

(defn phoneme-letter-report
  [phonemes]
  (string/join \newline [(letter-frequency-grid phonemes)
                         \newline
                         (phoneme-freq-report phonemes)]))

(defn print-debug-letters
  [letters]
  (run! println (for [row letters]
                  (into [] row))))

(defn print-letters
  [letters]
  (println (string/join \newline (for [row letters]
                                   (string/join \tab row)))))

