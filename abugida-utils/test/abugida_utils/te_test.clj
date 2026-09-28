(ns abugida-utils.te-test
  (:require [clojure.test :refer :all]
            [abugida-utils.te :refer :all]))

(deftest consonants-test
  (testing "consonants is same as consonants column of letters (grid)"
    (let [grid-consonants (for [row (drop 1 letters)]
                            (let [consonant (first row)]
                              consonant))]
      (dorun (map
               (fn [grid-c cons-c] (is (= grid-c cons-c)))
               grid-consonants
               consonants)))))

(deftest str->phonemes-test
  (testing "fn that converts a string into its phonemes"
    (are [x y]
      (= x y)
      ["బ్" "ఎ" "ఙ్" "గ్" "ఆ" "ల్" "ల్" "ఓ" "న్" "ఇ"] (str->phonemes "బెంగాల్\u200Cలోని")
      ["వ్" "య్" "అ" "త్" "ఇ" "ర్" "ఏ" "క్" "అ" "త్" "అ"] (str->phonemes "వ్యతిరేకత")
      ["య్" "ఉ" "న్" "ఐ" "ట్" "ఎ" "డ్" " " "బ్" "ఎ" "ఙ్" "గ్" "ఆ" "ల్"] (str->phonemes "యునైటెడ్ బెంగాల్"))))