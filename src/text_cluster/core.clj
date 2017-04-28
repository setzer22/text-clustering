(ns text-cluster.core
  (:require [text-cluster.freeling :as freeling])
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(freeling/analyze :text "The quick brown fox jumped over the lazy dog" :level "dep")

