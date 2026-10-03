(ns abugida-utils.te-keyman-rules-test
  (:require [clojure.test :refer :all]
            [abugida-utils.te-keyman-rules :refer :all]
            [abugida-utils.te :as te]))

(deftest phoneme-keyman-keyid-map-test
  (testing "keys of phoneme-keyman-keyid-map are the set of phonemes"
    (is (= te/phonemes (set (keys phoneme-keyman-keyid-map))))))