(ns text-cluster.clustering
  (:require [clojure.set :as set]
            [clojure.data.priority-map :as prmap]
            [com.rpl.specter :refer :all]
            [clojure.math.combinatorics :as combo]
            [incanter.core :refer [$=]]
            [text-cluster.text-preprocess :as preprocess]
            [text-cluster.wordnet-similarity :as similarity]
            [clustering.core.hierarchical :as hc]
            [text-cluster.dendrogram :as dendogram]
            [clustering.data-viz.image :refer [write-png]]))

(defrecord Tree [left right distance])

(defn tree? [t] (instance? Tree t))

(defn update-distances
  "Updates the distance function by considering that g1 and g2 have been merged"
  [distances g1 g2]
  (assert (and (contains? distances g1) (contains? distances g2)))
  (let [new (->Tree g1 g2 (get-in distances [g1 g2]))]
    (assoc-in
     (reduce
      (fn [distances e]
        (let [avg-dist ($= ((get-in distances [e g1]) + (get-in distances [e g2])) / 2)]
          (-> distances
              (assoc-in [e new] avg-dist)
              (assoc-in [new e] avg-dist))))
      distances
      (keys distances))
     [new new] 1.0)))

(defn hierarchical-clustering
  "Performs a hierarchical clustering. Takes the list of elements and their distances"
  [elements distances]
  (loop [elements  (set elements)
         distances distances]
    (cond (> (count elements) 1)
          (let [[g1 g2] (apply min-key
                               #(get-in distances %)
                               (combo/combinations elements 2))]
            (recur (conj (set/difference elements #{g1 g2})
                         (->Tree g1 g2 (get-in distances [g1 g2])))
                   (update-distances distances g1 g2)))
          :else (first elements))))

(def conj' (fnil conj []))

(defn branch? [tree]
  (:left tree))

(defn cut-tree
  "Cuts the output of a hierarchical clustering in components for
   which their separation is bigger than d. This kind of cut violates
   the scale-invariance property."
  [tree d]
  (let [top first, pop rest, push conj
        trees (loop [components [tree]
                     selected []]
                (let [tree (top components)
                      {:keys [left right distance]} tree]
                  (cond
                    (empty? components) selected
                    (> distance d) (recur (cond-> components
                                            true (pop)
                                            (branch? left) (push left)
                                            (branch? right) (push right))
                                          (cond-> selected
                                            (not (branch? left)) (conj left)
                                            (not (branch? right)) (conj right)))
                    (<= distance d) (recur (pop components)
                                           (conj selected tree)))))]
    (mapv (fn [tree] (->> (tree-seq branch? #(vector (:left %) (:right %)) tree)
                         (remove branch?)
                         (into []))) trees)))

(comment
  (= (cut-tree
     (->Tree
      (->Tree :a :b 10)
      (->Tree :c (->Tree :d :e 0.1) 5)
      10)
     0.2)
    [[:c] [:d :e] [:a] [:b]]))

(defn medoid-partition
  "Classifies each element to its closest medoid"
  [elements distances medoids]
  (reduce
   (fn [clusters element]
     ;; NOTE: The medoids need to be sorted, otherwise ties are not broken
     ;; deterministically and the algorithm won't converge!!
     (let [[_ medoid] (apply min-key #(get-in distances %)
                             (map vector (repeat element) (sort medoids)))]
       (update clusters medoid conj' element)))
   {}
   elements))

(defn compute-medoid
  "Computes the medoid of the elements, given the distances"
  [elements distances]
  (apply min-key
         (fn [element] (reduce + (map #(get-in distances [element %]) elements)))
         elements))



(defn k-medoids
  "Performs a k-medoids clustering given the elements, distances and initial medoids"
  [elements distances medoids]
  (loop [partition    (medoid-partition elements distances medoids)
         medoids      medoids
         last-medoids nil]
    (cond
      (= last-medoids medoids) partition
      :else
      (let [new-medoids (mapv #(compute-medoid % distances) (vals partition))]
        (println (medoid-partition elements distances new-medoids))
        (recur (medoid-partition elements distances new-medoids)
               new-medoids
               medoids)))))

(comment
  "An example of why breaking ties between medoids is important"
  (k-medoids
   [:a :b :c :d :e]
   {:a {:a 0 :b 2 :c 1 :d 1 :e 4}
    :b {:a 2 :b 0 :c 1 :d 4 :e 2}
    :c {:a 1 :b 1 :c 0 :d 1 :e 1}
    :d {:a 1 :b 4 :c 1 :d 0 :e 2}
    :e {:a 4 :b 2 :c 1 :d 2 :e 0}}
   [:a :e]))

(defn cluster-words-in-text [text lang]
  (let [words (preprocess/extract-words-from-text text lang)
        wns (distinct (map :wn words))
        distances (reduce
                   (fn [similarities [i j]]
                     (assoc-in similarities [i j] (- 1 (similarity/wu-palmer i j))))
                   {}
                   (for [i wns, j wns] [i j]))
        wn->lemma (into {} (map #(vector (:wn %) (:lemma %)) words))

        clustering (hierarchical-clustering wns distances)
        ;;TODO: @MagicNumber 0.5
        cut (cut-tree clustering 0.7)
        consolidated (k-medoids wns distances (map #(compute-medoid % distances) cut))]
    (write-png "/tmp/dendogram.png"
               (dendogram/->img
                (transform (walker string?) wn->lemma clustering)))
    (clojure.java.shell/sh "xdg-open" "/tmp/dendogram.png")
    (transform (walker string?) wn->lemma consolidated)))

(comment (cluster-words-in-text
          " Legend speaks of a beast Three hundred miles from its tip to its tail None have seen it, yet all know its name Like the ark of the covenant, or the holy grail We set out on a quest In search of the lair, where the creature doth dwell On a ransom to bring back its head Our journey would take us to the depths of hell His eyes shine like the rays of morning His mouth is as a burning flame Leviathan Cresting the waves Leading us all to the grave Leviathan Slaying all foes Who dare to oppose Tearing bodies limb from limb Eviscerating on a whim The skies turned to black The oceans fell dead, no winds dared to blow Then out the darkness with a thunderous roar Leviathan rose up from the depths below Cannons fired, and swords tasted blood As the beast turned to strike with rage in its eyes From its mouth came a great ball of flame It was then we all knew, that the end was nigh His eyes shine like the rays of morning His mouth is as a burning flame Leviathan Cresting the waves Leading us all to the grave Leviathan Slaying all foes Who dare to oppose Tearing bodies limb from limb Eviscerating on a whim His eyes shine like the rays of morning His mouth is as a burning flame His nostrils seethe with fumes of brimstone He is the beast that can't be tamed Leviathan Cresting the waves Leading us all to the grave Leviathan Slaying all foes Who dare to oppose Leviathan Cresting the waves Leading us all to the grave Leviathan Slaying all foes Who dare to oppose Tearing bodies limb from limb Eviscerating on a whim "
          "en")

         )
