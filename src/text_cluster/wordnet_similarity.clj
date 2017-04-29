(ns text-cluster.wordnet-similarity
  (:require [incanter.core :as incanter :refer [$=]]
            [text-cluster.freeling :as freeling]
            [text-cluster.utils :refer :all]
            [clojure.set :as set]))

(defonce semdb (delay (edu.upc.freeling.SemanticDB. "/usr/local/share/freeling/en/semdb.dat")))

(defn sense-info' [wn]
  (let [sense-info (.getSenseInfo (force semdb) wn)]
    {:wn wn
     :parents (StringList->seq (.getParents sense-info))
     :semfile (.getSemfile sense-info)
     :words (StringList->seq (.getWords sense-info))
     :tonto (StringList->seq (.getTonto sense-info))
     :sumo (.getSumo sense-info)
     :cyc (.getCyc sense-info)}))

;; NOTE: Memoization greatly improves performance for our use-case, since we lookup
;;       the same sense several times for a clustering
(def sense-info (memoize sense-info'))

(defn hiperonimy-tree [wn]
  (let [info (sense-info wn)]
    {:node wn
     :parents (mapv hiperonimy-tree (:parents info))}))

(defn tree-depth [t]
  (cond (seq (:parents t)) (inc (tree-depth (-> t :parents first)))
        :else 0))

(defn common-elements [t1 t2]
  (set/intersection
   (set (map :node (tree-seq :parents :parents t1)))
   (set (map :node (tree-seq :parents :parents t2)))))

(defn lowest-common-ancestor [t1 t2]
  (apply max-key
         #(tree-depth (hiperonimy-tree %))
         (common-elements t1 t2)))

(defn wu-palmer [s1 s2]
  (let [t1 (hiperonimy-tree s1), t2 (hiperonimy-tree s2)]
    (cond
     (= s1 s2) 1.0
     (not (and (seq (:parents t1))
               (seq (:parents t2)))) 0.0
     (not (seq (common-elements t1 t2))) 0.0
     :else
     (let [t1 (hiperonimy-tree s1), t2 (hiperonimy-tree s2)
           d1 (tree-depth t1),      d2 (tree-depth t2)
           lca (lowest-common-ancestor t1 t2)
           depth-lca (tree-depth (hiperonimy-tree lca))
           len ($= (d1 - depth-lca) + (d2 - depth-lca)) ]
       (double ($= (2 * depth-lca) / (len + 2 * depth-lca)))))))

(comment
  "Testing some similarity values:"

  "Fox vs Elephant = 0.4545"
  (wu-palmer "02503517-n" "09655569-n")

  "Right to Happiness vs Motorbike = 0.0"
  (wu-palmer "05182240-n" "03769722-n")

  "Motorbike vs Train = 0.6"
  (wu-palmer "03769722-n" "03896233-n")

  (wu-palmer "00594621-v")

  )

