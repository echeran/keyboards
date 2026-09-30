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

(deftest phonemes->str-test
  (testing "fn that converts a seq of phonemes into a string"
    (are [x y]
      (= x y)
      "బెంగాల్లోని" (phonemes->str ["బ్" "ఎ" "ఙ్" "గ్" "ఆ" "ల్" "ల్" "ఓ" "న్" "ఇ"])
      "వ్యతిరేకత" (phonemes->str ["వ్" "య్" "అ" "త్" "ఇ" "ర్" "ఏ" "క్" "అ" "త్" "అ"])
      "యునైటెడ్ బెంగాల్" (phonemes->str ["య్" "ఉ" "న్" "ఐ" "ట్" "ఎ" "డ్" " " "బ్" "ఎ" "ఙ్" "గ్" "ఆ" "ల్"]))))

(deftest phonemes->letters-test
  (testing "fn that conversta. seq of phonemes into a seq of normalized letters"
    (are [x y]
      (= x y)
      ["బె" "ఙ్" "గా" "ల్" "లో" "ని"] (phonemes->letters ["బ్" "ఎ" "ఙ్" "గ్" "ఆ" "ల్" "ల్" "ఓ" "న్" "ఇ"])
      ["వ్" "య" "తి" "రే" "క" "త"] (phonemes->letters ["వ్" "య్" "అ" "త్" "ఇ" "ర్" "ఏ" "క్" "అ" "త్" "అ"])
      ["యు" "నై" "టె" "డ్" " " "బె" "ఙ్" "గా" "ల్"] (phonemes->letters ["య్" "ఉ" "న్" "ఐ" "ట్" "ఎ" "డ్" " " "బ్" "ఎ" "ఙ్" "గ్" "ఆ" "ల్"]))))

(deftest str->letters-test
  (testing "fn that converts a string into normalized letters"
    (are [x y]
      (= x y)
      ["బె" "ఙ్" "గా" "ల్" "లో" "ని"] (str->letters "బెంగాల్\u200Cలోని")
      ["వ్" "య" "తి" "రే" "క" "త"] (str->letters "వ్యతిరేకత")
      ["యు" "నై" "టె" "డ్" " " "బె" "ఙ్" "గా" "ల్"] (str->letters "యునైటెడ్ బెంగాల్"))))