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
                        report (phoneme-letter-report phonemes)
                        parent-dir (-> resc-file
                                       .getParentFile)
                        output-dir (jio/file parent-dir "output" "te")
                        in-file-basename (.getName resc-file)
                        output-file (jio/file output-dir in-file-basename)]
                    (.mkdirs output-dir)
                    ;;(println "output-file" output-file)
                    (.createNewFile output-file)
                    (spit output-file report)))))]
      (let [resc-file-names ["te/wikipedia1.txt"
                             "te/wikipedia2.txt"
                             "te/wikipedia3.txt"]
            resc-objs (map jio/resource resc-file-names)]
        (doseq [resc resc-objs]
          (write-freq-report resc))))))