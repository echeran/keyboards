(ns abugida-utils.te-freqs-test
  (:require [clojure.test :refer :all]
            [abugida-utils.te :refer :all]
            [clojure.java.io :as jio]))

(deftest test1
  (testing "something"
    (is true)))

(deftest letter-freq-test
  (testing "print the letter frequency reports for each resource file"
    (letfn [(write-freq-report
              [resc-obj]
              (let [resc-file (jio/file resc-obj)]
                (with-open [rdr (jio/reader resc-file)]
                  (let [s (slurp rdr)
                        phonemes (str->phonemes s)
                        report (phoneme-letter-report phonemes)]
                    (println report)))))]
      (let [file1 "wikipedia1.txt"
            resc1 (jio/resource file1)]
        (write-freq-report resc1)))))