(ns text-cluster.text-preprocess
  (:require [text-cluster.freeling :as freeling]
            [text-cluster.wordnet-similarity :as similarity]
            [com.rpl.specter :refer :all]))


(defn extract-words-from-text [string-text lang]
  (let [text (freeling/analyze :text string-text :lang lang :level "senses")]
    (select [:json :paragraphs ALL :sentences ALL :tokens ALL
             #(#{"noun"} (:pos %)) #(:wn %)
             #(not= (:lemma %) "be")
             #(and (:wn %) (seq (:parents (similarity/sense-info (:wn %)))))] text)))





