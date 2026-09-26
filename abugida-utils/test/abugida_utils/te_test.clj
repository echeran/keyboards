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